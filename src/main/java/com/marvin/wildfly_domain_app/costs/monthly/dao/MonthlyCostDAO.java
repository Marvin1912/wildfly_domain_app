package com.marvin.wildfly_domain_app.costs.monthly.dao;

import com.marvin.wildfly_domain_app.configuration.database.CrudOperation;
import com.marvin.wildfly_domain_app.costs.monthly.entity.MonthlyCostEntity;
import jakarta.ejb.Singleton;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class MonthlyCostDAO {

    private static final Logger LOGGER = Logger.getLogger(MonthlyCostDAO.class.getName());

    private static void log(CrudOperation operation, Class<?> c, Object key) {
        LOGGER.log(Level.FINE, "[" + operation + "] Entity: [" + c.getName() + "], Key: [" + key + "]");
    }

    @PersistenceContext(unitName = "wildfly-domain")
    private EntityManager entityManager;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<MonthlyCostEntity> getMonthlyCosts() {
        log(CrudOperation.READ, MonthlyCostEntity.class, "ALL");
        return entityManager.createNamedQuery(MonthlyCostEntity.GET_MONTHLY_COSTS, MonthlyCostEntity.class)
                .getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public MonthlyCostEntity getMonthlyCost(LocalDate localDate) {
        log(CrudOperation.READ, MonthlyCostEntity.class, localDate);
        List<MonthlyCostEntity> resultList = entityManager.createNamedQuery(MonthlyCostEntity.FIND_MONTHLY_COST_BY_YEAR_AND_MONTH, MonthlyCostEntity.class)
                .setParameter("date", localDate)
                .getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void persistMonthlyCost(MonthlyCostEntity monthlyCostEntity) {
        log(CrudOperation.CREAT, MonthlyCostEntity.class, monthlyCostEntity.getCostDate());
        entityManager.persist(monthlyCostEntity);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateMonthlyCost(MonthlyCostEntity monthlyCostEntity) {
        log(CrudOperation.UPDATE, MonthlyCostEntity.class, monthlyCostEntity.getCostDate());
        entityManager.merge(monthlyCostEntity);
    }
}
