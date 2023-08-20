package com.marvin.wildfly_domain_app.costs.special.dao;

import com.marvin.wildfly_domain_app.configuration.database.BasicDAO;
import com.marvin.wildfly_domain_app.configuration.database.CrudOperation;
import com.marvin.wildfly_domain_app.costs.special.entity.SpecialCostEntryEntity;
import jakarta.ejb.Singleton;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@Singleton
public class SpecialCostEntryDAO extends BasicDAO<SpecialCostEntryEntity> {

    private static final Logger LOGGER = Logger.getLogger(SpecialCostEntryDAO.class.getName());

    @Override
    public SpecialCostEntryEntity get(LocalDate localDate) {
        return null;
    }

    @Override
    public List<SpecialCostEntryEntity> getAll() {
        log(CrudOperation.READ, SpecialCostEntryDAO.class, "ALL", LOGGER);
        return entityManager.createNamedQuery(SpecialCostEntryEntity.GET_SPECIAL_COSTS, SpecialCostEntryEntity.class)
                .getResultList();
    }

    @Override
    public void persistMonthlyCost(SpecialCostEntryEntity specialCostEntity) {

    }

    @Override
    public void updateMonthlyCost(SpecialCostEntryEntity specialCostEntity) {

    }
}
