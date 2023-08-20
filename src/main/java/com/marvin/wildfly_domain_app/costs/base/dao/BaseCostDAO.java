package com.marvin.wildfly_domain_app.costs.base.dao;

import com.marvin.wildfly_domain_app.configuration.database.BasicDAO;
import com.marvin.wildfly_domain_app.configuration.database.CrudOperation;
import com.marvin.wildfly_domain_app.costs.base.entity.BaseCostEntity;
import jakarta.ejb.Singleton;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@Singleton
public class BaseCostDAO extends BasicDAO<BaseCostEntity> {

    private static final Logger LOGGER = Logger.getLogger(BaseCostDAO.class.getName());

    @Override
    public BaseCostEntity get(LocalDate localDate) {
        return null;
    }

    @Override
    public List<BaseCostEntity> getAll() {
        log(CrudOperation.READ, BaseCostEntity.class, "ALL", LOGGER);
        return entityManager.createNamedQuery(BaseCostEntity.GET_BASE_COSTS, BaseCostEntity.class)
                .getResultList();
    }

    @Override
    public void persistMonthlyCost(BaseCostEntity baseCostEntity) {

    }

    @Override
    public void updateMonthlyCost(BaseCostEntity baseCostEntity) {

    }
}
