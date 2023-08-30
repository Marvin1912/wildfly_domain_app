package com.marvin.common.costs.salary.entity;

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
@Table(name = "salary", schema = "public", catalog = "wildfly_domain")
@NamedQueries({
        @NamedQuery(
                name = SalaryEntity.FIND_SALARIES_BY_DATE,
                query = "SELECT m FROM SalaryEntity m WHERE m.salaryDate = :date"
        ),
        @NamedQuery(
                name = SalaryEntity.GET_SALARIES,
                query = "SELECT m FROM SalaryEntity m ORDER BY m.salaryDate"
        )
})
public class SalaryEntity extends BasicEntity {

    public static final String GET_SALARIES = "getSalaries";
    public static final String FIND_SALARIES_BY_DATE = "findSalariesByDate";

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "salary_date", nullable = false)
    private LocalDate salaryDate;

    @Basic
    @Column(name = "value", nullable = false, precision = 2)
    private BigDecimal value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getSalaryDate() {
        return salaryDate;
    }

    public void setSalaryDate(LocalDate salaryDate) {
        this.salaryDate = salaryDate;
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
        SalaryEntity that = (SalaryEntity) o;
        return id == that.id && Objects.equals(salaryDate, that.salaryDate) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, salaryDate, value);
    }

    @Override
    public String toString() {
        return "SalaryEntity{" +
                "id=" + id +
                ", salaryDate=" + salaryDate +
                ", value=" + value +
                '}';
    }
}
