package com.marvin.common.costs.special.dao;

import com.marvin.common.db.infrastructure.BasicDAO;
import com.marvin.common.db.infrastructure.CrudOperation;
import com.marvin.common.costs.special.entity.SpecialCostEntryEntity;
import jakarta.ejb.Singleton;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Singleton
public class SpecialCostEntryDAO extends BasicDAO<SpecialCostEntryEntity> {

    private static final Logger LOGGER = Logger.getLogger(SpecialCostEntryDAO.class.getName());

    @Override
    public List<SpecialCostEntryEntity> get(LocalDate localDate) {
        log(CrudOperation.READ, SpecialCostEntryDAO.class, localDate, LOGGER);
        return entityManager.createNamedQuery(SpecialCostEntryEntity.GET_SPECIAL_COST_BY_DATE, SpecialCostEntryEntity.class)
                .setParameter("date", localDate)
                .getResultList();
    }

    @Override
    public Stream<SpecialCostEntryEntity> getAll() {
        log(CrudOperation.READ, SpecialCostEntryDAO.class, "ALL", LOGGER);
        return entityManager.createNamedQuery(SpecialCostEntryEntity.GET_SPECIAL_COSTS, SpecialCostEntryEntity.class)
                .getResultStream();
    }

    @Override
    public void persistMonthlyCost(SpecialCostEntryEntity specialCostEntity) {

    }

    @Override
    public void updateMonthlyCost(SpecialCostEntryEntity specialCostEntity) {

    }
}