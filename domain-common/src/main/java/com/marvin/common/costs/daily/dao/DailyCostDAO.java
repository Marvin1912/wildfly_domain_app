package com.marvin.common.costs.daily.dao;

import com.marvin.common.costs.daily.entity.DailyCostEntity;
import com.marvin.common.costs.monthly.entity.MonthlyCostEntity;
import com.marvin.common.db.infrastructure.BasicDAO;
import com.marvin.common.db.infrastructure.CrudOperation;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Stateless
public class DailyCostDAO extends BasicDAO<DailyCostEntity> {

    private static final Logger LOGGER = Logger.getLogger(DailyCostDAO.class.getName());

    @PersistenceContext(unitName = "wildfly-domain")
    private EntityManager entityManager;

    @Override
    public List<DailyCostEntity> get(LocalDate localDate) {
        return null;
    }

    @Override
    public Stream<DailyCostEntity> getAll() {
        log(CrudOperation.READ, MonthlyCostEntity.class, "ALL", LOGGER);
        return entityManager.createNamedQuery(DailyCostEntity.GET_DAILY_COSTS, DailyCostEntity.class)
                .getResultStream();
    }

    @Override
    public void persistMonthlyCost(DailyCostEntity monthlyCostEntity) {

    }

    @Override
    public void updateMonthlyCost(DailyCostEntity monthlyCostEntity) {

    }
}
