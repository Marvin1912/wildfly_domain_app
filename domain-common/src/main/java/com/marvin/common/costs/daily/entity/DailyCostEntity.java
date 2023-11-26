package com.marvin.common.costs.daily.entity;

import com.marvin.common.db.entity.BasicEntity;
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
import java.util.Objects;

@Entity
@Table(name = "daily_cost", schema = "public", catalog = "wildfly_domain")
@NamedQueries({
        @NamedQuery(
                name = DailyCostEntity.FIND_DAILY_COST_BY_DATE,
                query = "SELECT d FROM DailyCostEntity d WHERE d.costDate = :date ORDER BY d.costDate"
        ),
        @NamedQuery(
                name = DailyCostEntity.FIND_DAILY_COST_BY_DATE_AFTER,
                query = "SELECT d FROM DailyCostEntity d " +
                        "WHERE d.costDate >= :date " +
                        "ORDER BY d.costDate"
        ),
        @NamedQuery(
                name = DailyCostEntity.GET_DAILY_COSTS,
                query = "SELECT d FROM DailyCostEntity d ORDER BY d.costDate"
        )
})
public class DailyCostEntity extends BasicEntity {

    public static final String GET_DAILY_COSTS = "findDailyCosts";
    public static final String FIND_DAILY_COST_BY_DATE = "findDailyCostByDate";
    public static final String FIND_DAILY_COST_BY_DATE_AFTER = "findDailyCostByDateAfter";

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "cost_date")
    private LocalDate costDate;

    @Basic
    @Column(name = "value")
    private BigDecimal value;

    public DailyCostEntity() {
        // NOOP
    }

    public DailyCostEntity(LocalDate costDate, BigDecimal value) {
        this.costDate = costDate;
        this.value = value;
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
        if (!super.equals(o)) return false;
        DailyCostEntity that = (DailyCostEntity) o;
        return id == that.id && Objects.equals(costDate, that.costDate) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, costDate, value);
    }

    @Override
    public String toString() {
        return "DailyCostEntity{" +
                "id=" + id +
                ", costDate=" + costDate +
                ", value=" + value +
                '}';
    }
}
