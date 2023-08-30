package com.marvin.common.costs.special.entity;

import com.marvin.common.db.entity.BasicEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "special_cost_entry", schema = "public", catalog = "wildfly_domain")
@NamedQueries({
        @NamedQuery(
                name = SpecialCostEntryEntity.GET_SPECIAL_COSTS,
                query = "SELECT m FROM SpecialCostEntryEntity m ORDER BY m.specialCost.costDate"
        ),
        @NamedQuery(
                name = SpecialCostEntryEntity.GET_SPECIAL_COST_BY_DATE,
                query = "SELECT m FROM SpecialCostEntryEntity m WHERE m.specialCost.costDate = :date"
        )
})
public class SpecialCostEntryEntity extends BasicEntity {

    public static final String GET_SPECIAL_COSTS = "getSpecialCosts";
    public static final String GET_SPECIAL_COST_BY_DATE = "getSpecialCostByDate";

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "description", nullable = false, length = 2048)
    private String description;

    @Basic
    @Column(name = "value", nullable = false, precision = 2)
    private BigDecimal value;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "special_cost_id")
    private SpecialCostEntity specialCost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public SpecialCostEntity getSpecialCost() {
        return specialCost;
    }

    public void setSpecialCost(SpecialCostEntity specialCost) {
        this.specialCost = specialCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SpecialCostEntryEntity that = (SpecialCostEntryEntity) o;
        return id == that.id
                && Objects.equals(description, that.description)
                && Objects.equals(value, that.value)
                && Objects.equals(specialCost, that.specialCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, description, value, specialCost);
    }

    @Override
    public String toString() {
        return "SpecialCostEntryEntity{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", value=" + value +
                ", specialCost=" + specialCost +
                '}';
    }
}
