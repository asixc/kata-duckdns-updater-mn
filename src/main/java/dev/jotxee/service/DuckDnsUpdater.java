package dev.jotxee.service;

import dev.jotxee.repository.IPRepository;
import dev.jotxee.repository.entity.IPEntity;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Singleton
public class DuckDnsUpdater {
    private static final Logger LOG = LoggerFactory.getLogger(DuckDnsUpdater.class);
    private static final String DUCK_DNS_URL = "https://www.duckdns.org/update?domains=vpn-jotxee&token=";
    private static final OkHttpClient client = new OkHttpClient();

    @Value("${duckdns.token}")
    private String token;

    @Inject
    private IPRepository repository;

    public void update(final LocalDateTime instant) {
        LOG.debug("Token [{}]",  this.token);
        final Request request = new Request.Builder()
                .url(DUCK_DNS_URL+token)
                .build();
        try {
            final String publicIP = getPublicIP();
            final Optional<IPEntity> lastRecord = repository.findLastRecord();

            if (lastRecord.isPresent() && lastRecord.get().getIp().equals(publicIP)) {
                LOG.debug("La última ip publicada [{}] sigue siendo la misma [{}]", lastRecord.get().getIp(), publicIP);
                return;
            }
            // Realizar la solicitud y obtener la respuesta
            final Response response = client.newCall(request).execute();

            // Obtener el cuerpo de la respuesta
            final String responseBodyText = response.body() != null ? response.body().string() : null;

            // Obtener el código de respuesta
            final int statusCode = response.code();
            LOG.debug("Código de respuesta: " + statusCode);

            if (statusCode == 200 && "OK".equals(responseBodyText)) {
                // Leer y mostrar la respuesta del servidor
                repository.save(new IPEntity(publicIP, instant));
                LOG.debug("Respuesta del servidor DUCKDNS: {}, con IP [{}]", responseBodyText, publicIP);
            } else {
                LOG.debug("La solicitud no fue exitosa.");
            }
        } catch (Exception e) {
            LOG.error("An exception has been thrown {} cause: [{}]",
                    new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()),
                    e.getCause() + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getPublicIP() {
        // Crear una solicitud GET con la URL
        final Request request = new Request.Builder()
                .url("https://ifconfig.me/ip")
                .build();

        try (final Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                LOG.debug("La solicitud no fue exitosa. Código de respuesta: " + response.code());
            }
            assert response.body() != null;
            final String responseBody = response.body().string();
            LOG.debug("Dirección IP: " + responseBody);

            return responseBody;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "NO IP";
    }
}
