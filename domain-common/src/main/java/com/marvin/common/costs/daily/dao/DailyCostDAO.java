package com.marvin.common.costs.daily.dao;

import com.marvin.common.costs.daily.entity.DailyCostEntity;
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
        log(CrudOperation.READ, DailyCostEntity.class, localDate, LOGGER);
        return entityManager.createNamedQuery(DailyCostEntity.FIND_DAILY_COST_BY_DATE, DailyCostEntity.class)
                .setParameter("date", localDate)
                .getResultList();
    }

    @Override
    public Stream<DailyCostEntity> getAll() {
        log(CrudOperation.READ, DailyCostEntity.class, "ALL", LOGGER);
        return entityManager.createNamedQuery(DailyCostEntity.GET_DAILY_COSTS, DailyCostEntity.class)
                .getResultStream();
    }

    @Override
    public void persist(DailyCostEntity dailyCostEntity) {
        log(CrudOperation.CREATE, DailyCostEntity.class, dailyCostEntity.getCostDate(), LOGGER);
        entityManager.persist(dailyCostEntity);
    }

    @Override
    public void update(DailyCostEntity dailyCostEntity) {
        log(CrudOperation.UPDATE, DailyCostEntity.class, dailyCostEntity.getCostDate(), LOGGER);
        entityManager.merge(dailyCostEntity);
    }
}
