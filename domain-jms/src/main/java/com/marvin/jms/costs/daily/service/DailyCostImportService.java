package com.marvin.jms.costs.daily.service;

import com.marvin.common.costs.daily.dao.DailyCostDAO;
import com.marvin.common.costs.daily.dto.DailyCostDTO;
import com.marvin.common.costs.daily.entity.DailyCostEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class DailyCostImportService {

    private static final Logger LOGGER = Logger.getLogger(DailyCostImportService.class.getName());

    @EJB
    private DailyCostDAO dailyCostDAO;

    public boolean importDailyCost(DailyCostDTO dailyCost) {
        return persist(dailyCost);
    }

    private boolean persist(DailyCostDTO dailyCost) {
        final List<DailyCostEntity> persistedStateList = dailyCostDAO.get(dailyCost.costDate());
        if (persistedStateList.isEmpty()) {
            DailyCostEntity monthlyCostEntity = new DailyCostEntity(dailyCost.costDate(), dailyCost.value());
            dailyCostDAO.persist(monthlyCostEntity);
            return true;
        } else {
            BigDecimal newValue = dailyCost.value();
            DailyCostEntity persistedState = persistedStateList.get(0);
            BigDecimal persistedValue = persistedState.getValue();
            if (newValue.compareTo(persistedValue) > 0) {
                LOGGER.log(Level.INFO, "Updated value of " + persistedState.getCostDate() + " from " + newValue + " to " + persistedValue + "!");
                persistedState.setValue(newValue);
                dailyCostDAO.update(persistedState);
                return true;
            }
        }
        return false;
    }
}
