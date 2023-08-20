package com.marvin.wildfly_domain_app.configuration.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
public class BasicEntity {

    @Basic
    @Column(name = "creation_date", nullable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Basic
    @Column(name = "last_modified", nullable = false)
    @UpdateTimestamp
    private LocalDateTime lastModified;

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicEntity that = (BasicEntity) o;
        return Objects.equals(creationDate, that.creationDate) && Objects.equals(lastModified, that.lastModified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creationDate, lastModified);
    }

    @Override
    public String toString() {
        return "BasicEntity{" +
                "creationDate=" + creationDate +
                ", lastModified=" + lastModified +
                '}';
    }
}
