package com.marvin.wildfly_domain_app.costs.monthly.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvin.wildfly_domain_app.costs.monthly.dao.MonthlyCostDAO;
import com.marvin.wildfly_domain_app.costs.monthly.dto.MonthlyCostDTO;
import com.marvin.wildfly_domain_app.costs.monthly.entity.MonthlyCostEntity;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.EJB;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(
                propertyName = "connectionFactoryLookup",
                propertyValue = "ConnectionFactory"
        ),
        @ActivationConfigProperty(
                propertyName = "destinationType",
                propertyValue = "jakarta.jms.Queue"
        ),
        @ActivationConfigProperty(
                propertyName = "destination",
                propertyValue = "jms/queue/costs/monthly"
        )

})
public class MonthlyCostImportMDB implements MessageListener {

    private static final Logger LOGGER = Logger.getLogger(MonthlyCostImportMDB.class.getName());

    @EJB
    private MonthlyCostDAO monthlyCostDAO;

    @Inject
    private ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message) {
        try {
            final String messageBody = message.getBody(String.class);
            LOGGER.log(Level.INFO, "[" + System.getProperty("server.name") + "] Monthly cost received: " + messageBody);
            final MonthlyCostDTO monthlyCost = objectMapper.readValue(messageBody, MonthlyCostDTO.class);

            final MonthlyCostEntity persistedState = monthlyCostDAO.get(monthlyCost.costDate());
            if (persistedState == null) {
                MonthlyCostEntity monthlyCostEntity = new MonthlyCostEntity(monthlyCost.costDate(), monthlyCost.value());
                monthlyCostDAO.persistMonthlyCost(monthlyCostEntity);
            } else {
                BigDecimal newValue = monthlyCost.value();
                BigDecimal persistedValue = persistedState.getValue();
                if (newValue.compareTo(persistedValue) > 0) {
                    LOGGER.log(Level.INFO, "Updated value of " + persistedState.getCostDate() + " from " + newValue + " to " + persistedValue + "!");
                    persistedState.setValue(newValue);
                    monthlyCostDAO.updateMonthlyCost(persistedState);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "[" + System.getProperty("server.name") + "] Error while trying to consume messages: " + e.getMessage());
        }
    }
}
