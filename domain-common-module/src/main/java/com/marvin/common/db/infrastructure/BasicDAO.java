package com.marvin.common.db.infrastructure;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public abstract class BasicDAO<T> implements CrudDAO<T> {

    @PersistenceContext(unitName = "wildfly-domain")
    protected EntityManager entityManager;

}
