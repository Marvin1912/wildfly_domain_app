package com.marvin.wildfly_domain_app.costs.salary.dao;

import com.marvin.wildfly_domain_app.configuration.database.BasicDAO;
import com.marvin.wildfly_domain_app.configuration.database.CrudOperation;
import com.marvin.wildfly_domain_app.costs.monthly.entity.MonthlyCostEntity;
import com.marvin.wildfly_domain_app.costs.salary.entity.SalaryEntity;
import jakarta.ejb.Singleton;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@Singleton
public class SalaryDAO extends BasicDAO<SalaryEntity> {

    private static final Logger LOGGER = Logger.getLogger(SalaryDAO.class.getName());

    @Override
    public SalaryEntity get(LocalDate localDate) {
        log(CrudOperation.READ, SalaryEntity.class, localDate, LOGGER);
        List<SalaryEntity> resultList = entityManager.createNamedQuery(SalaryEntity.FIND_SALARIES_BY_DATE, SalaryEntity.class)
                .setParameter("date", localDate)
                .getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<SalaryEntity> getAll() {
        log(CrudOperation.READ, SalaryEntity.class, "ALL", LOGGER);
        return entityManager.createNamedQuery(SalaryEntity.GET_SALARIES, SalaryEntity.class)
                .getResultList();
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
