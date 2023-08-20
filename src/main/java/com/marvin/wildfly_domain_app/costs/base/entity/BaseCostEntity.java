package com.marvin.wildfly_domain_app.costs.base.entity;

import com.marvin.wildfly_domain_app.configuration.entity.BasicEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "base_cost", schema = "public", catalog = "wildfly_domain")
@NamedQuery(
        name = BaseCostEntity.GET_BASE_COSTS,
        query = "SELECT m FROM BaseCostEntity m ORDER BY m.costDate"
)
public class BaseCostEntity extends BasicEntity {

    public static final String GET_BASE_COSTS = "getBaseCosts";

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "cost_date", nullable = false)
    private LocalDate costDate;

    @Basic
    @Column(name = "value", nullable = false, precision = 2)
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
        BaseCostEntity that = (BaseCostEntity) o;
        return id == that.id && Objects.equals(costDate, that.costDate) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, costDate, value);
    }

    @Override
    public String toString() {
        return "BaseCostEntity{" +
                "id=" + id +
                ", costDate=" + costDate +
                ", value=" + value +
                '}';
    }
}
