package dev.jotxee.cron;

import dev.jotxee.service.DuckDnsUpdater;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Singleton
public class Tasks {
    private static final Logger LOG = LoggerFactory.getLogger(Tasks.class);
    private final DuckDnsUpdater duckDnsUpdater;
    public Tasks(DuckDnsUpdater duckDnsUpdater) {
        this.duckDnsUpdater = duckDnsUpdater;
    }

    @Scheduled(cron = "0/5 * * * * ")
    void execute() {
        final LocalDateTime instant = LocalDateTime.now();
        final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/M/yyyy hh:mm:ss");
        LOG.debug("Starting call to Duckdns.org: {}", instant.format(format));
        duckDnsUpdater.update(instant);
    }
}
