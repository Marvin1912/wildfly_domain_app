package com.marvin.jms.costs.monthly.service;

import com.marvin.common.costs.monthly.dao.MonthlyCostDAO;
import com.marvin.common.costs.monthly.dto.MonthlyCostDTO;
import com.marvin.common.costs.monthly.entity.MonthlyCostEntity;
import com.marvin.jms.costs.AbstractCostImportMDB;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.EJB;
import jakarta.ejb.MessageDriven;
import jakarta.jms.Message;

import java.math.BigDecimal;
import java.util.List;
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
                propertyValue = "/jms/queue/costs/import/monthly"
        )
})
public class MonthlyCostImportMDB extends AbstractCostImportMDB<MonthlyCostDTO> {

    private static final Logger LOGGER = Logger.getLogger(MonthlyCostImportMDB.class.getName());

    @EJB
    private MonthlyCostDAO monthlyCostDAO;

    @EJB
    private MonthlyCostExportService monthlyCostExportService;

    @Override
    public void onMessage(Message message) {
        try {
            final String messageBody = message.getBody(String.class);
            LOGGER.log(Level.INFO, "[" + HOST_NAME + "] Monthly cost received: " + messageBody);
            final MonthlyCostDTO monthlyCost = objectMapper.readValue(messageBody, MonthlyCostDTO.class);

            persist(monthlyCost);

            monthlyCostExportService.doExport(monthlyCost);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "[" + HOST_NAME + "] Error while trying to consume messages: " + e.getMessage());
        }
    }

    @Override
    protected void persist(MonthlyCostDTO monthlyCost) {
        final List<MonthlyCostEntity> persistedStateList = monthlyCostDAO.get(monthlyCost.costDate());
        if (persistedStateList.isEmpty()) {
            MonthlyCostEntity monthlyCostEntity = new MonthlyCostEntity(monthlyCost.costDate(), monthlyCost.value());
            monthlyCostDAO.persistMonthlyCost(monthlyCostEntity);
        } else {
            BigDecimal newValue = monthlyCost.value();
            MonthlyCostEntity persistedState = persistedStateList.get(0);
            BigDecimal persistedValue = persistedState.getValue();
            if (newValue.compareTo(persistedValue) > 0) {
                LOGGER.log(Level.INFO, "Updated value of " + persistedState.getCostDate() + " from " + newValue + " to " + persistedValue + "!");
                persistedState.setValue(newValue);
                monthlyCostDAO.updateMonthlyCost(persistedState);
            }
        }
    }
}
