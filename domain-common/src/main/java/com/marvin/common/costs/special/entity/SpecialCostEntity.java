package com.marvin.common.costs.special.entity;

import com.marvin.common.db.entity.BasicEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "special_cost", schema = "public", catalog = "wildfly_domain")
public class SpecialCostEntity extends BasicEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "cost_date", nullable = false)
    private LocalDate costDate;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SpecialCostEntity that = (SpecialCostEntity) o;
        return id == that.id && Objects.equals(costDate, that.costDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, costDate);
    }

    @Override
    public String toString() {
        return "SpecialCostEntity{" +
                "id=" + id +
                ", costDate=" + costDate +
                '}';
    }
}
