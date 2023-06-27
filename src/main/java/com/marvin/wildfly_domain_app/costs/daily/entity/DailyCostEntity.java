package com.marvin.wildfly_domain_app.costs.daily.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "daily_cost", schema = "public", catalog = "wildfly_domain")
@NamedQueries(
        @NamedQuery(
                name = "findByYearAndMonth",
                query = "SELECT d FROM DailyCostEntity d " +
                        "WHERE EXTRACT(YEAR FROM d.costDate) = :year " +
                        "AND EXTRACT(MONTH FROM d.costDate) = :month"
        )
)
public class DailyCostEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "cost_date")
    private LocalDate costDate;

    @Basic
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Basic
    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    @Basic
    @Column(name = "value")
    private BigDecimal value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getCostDate() {
        return costDate;
    }

    public void setCostDate(LocalDate costDate) {
        this.costDate = costDate;
    }

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

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyCostEntity that = (DailyCostEntity) o;
        return id == that.id && Objects.equals(costDate, that.costDate) && Objects.equals(creationDate, that.creationDate) && Objects.equals(lastModified, that.lastModified) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, costDate, creationDate, lastModified, value);
    }

    @Override
    public String toString() {
        return "DailyCostEntity{" +
                "id=" + id +
                ", costDate=" + costDate +
                ", creationDate=" + creationDate +
                ", lastModified=" + lastModified +
                ", value=" + value +
                '}';
    }
}
