package dev.jotxee.repository.entity;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.GenerationType.AUTO;

@Serdeable
@Entity
@Table(name = "ip_entity")
public class IPEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String ip;
    private LocalDateTime instant;

    public IPEntity(String ip, LocalDateTime instant) {
        this.ip = ip;
        this.instant = instant;
    }

    public IPEntity() { }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public LocalDateTime getInstant() {
        return instant;
    }

    public void setInstant(LocalDateTime instant) {
        this.instant = instant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IPEntity ipEntity)) return false;
        return Objects.equals(getInstant(), ipEntity.getInstant());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInstant());
    }
}
