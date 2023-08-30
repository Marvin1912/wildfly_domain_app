package com.marvin.common.db.infrastructure;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public interface CrudDAO<T> {

    List<T> get(LocalDate localDate);

    Stream<T> getAll();

    void persistMonthlyCost(T t);

    void updateMonthlyCost(T t);

    default void log(CrudOperation operation, Class<?> c, Object key, Logger logger) {
        logger.log(Level.FINE, "[" + operation + "] Entity: [" + c.getName() + "], Key: [" + key + "]");
    }
}
