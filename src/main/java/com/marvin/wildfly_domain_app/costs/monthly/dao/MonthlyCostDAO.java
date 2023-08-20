package com.marvin.wildfly_domain_app.costs.monthly.dao;

import com.marvin.wildfly_domain_app.configuration.database.BasicDAO;
import com.marvin.wildfly_domain_app.configuration.database.CrudOperation;
import com.marvin.wildfly_domain_app.costs.monthly.entity.MonthlyCostEntity;
import jakarta.ejb.Singleton;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@Singleton
public class MonthlyCostDAO extends BasicDAO<MonthlyCostEntity> {

    private static final Logger LOGGER = Logger.getLogger(MonthlyCostDAO.class.getName());

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public MonthlyCostEntity get(LocalDate localDate) {
        log(CrudOperation.READ, MonthlyCostEntity.class, localDate, LOGGER);
        List<MonthlyCostEntity> resultList = entityManager.createNamedQuery(MonthlyCostEntity.FIND_MONTHLY_COST_BY_DATE, MonthlyCostEntity.class)
                .setParameter("date", localDate)
                .getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<MonthlyCostEntity> getAll() {
        log(CrudOperation.READ, MonthlyCostEntity.class, "ALL", LOGGER);
        return entityManager.createNamedQuery(MonthlyCostEntity.GET_MONTHLY_COSTS, MonthlyCostEntity.class)
                .getResultList();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void persistMonthlyCost(MonthlyCostEntity monthlyCostEntity) {
        log(CrudOperation.CREATE, MonthlyCostEntity.class, monthlyCostEntity.getCostDate(), LOGGER);
        entityManager.persist(monthlyCostEntity);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateMonthlyCost(MonthlyCostEntity monthlyCostEntity) {
        log(CrudOperation.UPDATE, MonthlyCostEntity.class, monthlyCostEntity.getCostDate(), LOGGER);
        entityManager.merge(monthlyCostEntity);
    }
}
