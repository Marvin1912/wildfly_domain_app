package com.marvin.jms.costs.daily.service;

import com.marvin.common.costs.daily.dao.DailyCostDAO;
import com.marvin.common.costs.daily.dto.DailyCostDTO;
import com.marvin.jms.costs.AbstractCostImportMDB;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.EJB;
import jakarta.ejb.MessageDriven;
import jakarta.jms.Message;

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
                propertyValue = "/jms/queue/costs/import/daily"
        )
})
public class DailyCostImportMDB extends AbstractCostImportMDB<DailyCostDTO> {

    private static final Logger LOGGER = Logger.getLogger(DailyCostImportMDB.class.getName());

    @EJB
    private DailyCostDAO dailyCostDAO;

    @EJB
    private DailyCostImportService dailyCostImportService;

    @EJB
    private DailyCostExportService dailyCostExportService;

    @Override
    public void onMessage(Message message) {
        try {
            final String messageBody = message.getBody(String.class);
            LOGGER.log(Level.INFO, "[" + HOST_NAME + "] Daily cost received: " + messageBody);
            final DailyCostDTO dailyCost = objectMapper.readValue(messageBody, DailyCostDTO.class);

            boolean update = persist(dailyCost);
            if (update) {
                dailyCostExportService.doExport(dailyCost);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "[" + HOST_NAME + "] Error while trying to consume messages: " + e.getMessage());
        }
    }

    @Override
    protected boolean persist(DailyCostDTO dailyCost) {
        return dailyCostImportService.importDailyCost(dailyCost);
    }
}
