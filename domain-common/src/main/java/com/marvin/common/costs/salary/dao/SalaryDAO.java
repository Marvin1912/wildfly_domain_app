package com.marvin.common.costs.salary.dao;

import com.marvin.common.db.infrastructure.BasicDAO;
import com.marvin.common.db.infrastructure.CrudOperation;
import com.marvin.common.costs.monthly.entity.MonthlyCostEntity;
import com.marvin.common.costs.salary.entity.SalaryEntity;
import jakarta.ejb.Singleton;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Singleton
public class SalaryDAO extends BasicDAO<SalaryEntity> {

    private static final Logger LOGGER = Logger.getLogger(SalaryDAO.class.getName());

    @Override
    public List<SalaryEntity> get(LocalDate localDate) {
        log(CrudOperation.READ, SalaryEntity.class, localDate, LOGGER);
        return entityManager.createNamedQuery(SalaryEntity.FIND_SALARIES_BY_DATE, SalaryEntity.class)
                .setParameter("date", localDate)
                .getResultList();
    }

    @Override
    public Stream<SalaryEntity> getAll() {
        log(CrudOperation.READ, SalaryEntity.class, "ALL", LOGGER);
        return entityManager.createNamedQuery(SalaryEntity.GET_SALARIES, SalaryEntity.class)
                .getResultStream();
    }

    @Override
    public void persistMonthlyCost(SalaryEntity salaryEntity) {
        log(CrudOperation.CREATE, SalaryEntity.class, salaryEntity.getSalaryDate(), LOGGER);
        entityManager.persist(salaryEntity);
    }

    @Override
    public void updateMonthlyCost(SalaryEntity salaryEntity) {
        log(CrudOperation.UPDATE, MonthlyCostEntity.class, salaryEntity.getSalaryDate(), LOGGER);
        entityManager.merge(salaryEntity);
    }
}
