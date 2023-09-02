package com.marvin.common.costs.base.dao;

import com.marvin.common.costs.base.entity.BaseCostEntity;
import com.marvin.common.db.infrastructure.BasicDAO;
import com.marvin.common.db.infrastructure.CrudOperation;
import jakarta.ejb.Singleton;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Singleton
public class BaseCostDAO extends BasicDAO<BaseCostEntity> {

    private static final Logger LOGGER = Logger.getLogger(BaseCostDAO.class.getName());

    @Override
    public List<BaseCostEntity> get(LocalDate localDate) {
        return null;
    }

    @Override
    public Stream<BaseCostEntity> getAll() {
        log(CrudOperation.READ, BaseCostEntity.class, "ALL", LOGGER);
        return entityManager.createNamedQuery(BaseCostEntity.GET_BASE_COSTS, BaseCostEntity.class)
                .getResultStream();
    }

    @Override
    public void persist(BaseCostEntity baseCostEntity) {

    }

    @Override
    public void update(BaseCostEntity baseCostEntity) {

    }
}
