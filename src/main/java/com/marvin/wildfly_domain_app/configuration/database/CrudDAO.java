package com.marvin.wildfly_domain_app.configuration.database;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface CrudDAO<T> {

    T get(LocalDate localDate);

    List<T> getAll();

    void persistMonthlyCost(T t);

    void updateMonthlyCost(T t);

    default void log(CrudOperation operation, Class<?> c, Object key, Logger logger) {
        logger.log(Level.FINE, "[" + operation + "] Entity: [" + c.getName() + "], Key: [" + key + "]");
    }

}
