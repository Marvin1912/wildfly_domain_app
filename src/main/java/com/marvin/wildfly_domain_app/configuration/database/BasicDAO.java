package com.marvin.wildfly_domain_app.configuration.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public abstract class BasicDAO<T> implements CrudDAO<T> {

    @PersistenceContext(unitName = "wildfly-domain")
    protected EntityManager entityManager;

}
