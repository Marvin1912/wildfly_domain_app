package com.marvin.jms.costs.special.service;

import com.marvin.common.costs.special.dao.SpecialCostEntryDAO;
import com.marvin.common.costs.special.dto.SpecialCostDTO;
import com.marvin.common.costs.special.dto.SpecialCostEntryDTO;
import com.marvin.common.costs.special.entity.SpecialCostEntity;
import com.marvin.common.costs.special.entity.SpecialCostEntryEntity;
import com.marvin.jms.costs.AbstractCostImportMDB;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.EJB;
import jakarta.ejb.MessageDriven;
import jakarta.jms.Message;

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
                propertyValue = "/jms/queue/costs/import/special"
        )
})
public class SpecialCostImportMDB extends AbstractCostImportMDB<SpecialCostDTO> {

    private static final Logger LOGGER = Logger.getLogger(SpecialCostImportMDB.class.getName());

    @EJB
    private SpecialCostEntryDAO specialCostEntryDAO;

    @Override
    public void onMessage(Message message) {
        try {
            final String messageBody = message.getBody(String.class);
            LOGGER.log(Level.INFO, "[" + HOST_NAME + "] Special cost received: " + messageBody);
            final SpecialCostDTO specialCost = objectMapper.readValue(messageBody, SpecialCostDTO.class);

            persist(specialCost);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "[" + HOST_NAME + "] Error while trying to consume messages: " + e.getMessage());
        }
    }

    @Override
    protected boolean persist(SpecialCostDTO specialCost) {

        final List<SpecialCostEntryEntity> specialCostEntryEntities = specialCostEntryDAO.get(specialCost.costDate());

        final List<SpecialCostEntryDTO> newEntries = specialCost.entries();

        // This means no special costs exist, so new ones need to be created
        if (specialCostEntryEntities.isEmpty()) {
            createAndPersistNewEntries(specialCost);
            return true;
        }

        if (specialCostEntryEntities.size() < newEntries.size()) {
            createAndPersistNewEntries(specialCost, specialCostEntryEntities);
            return true;
        }

        return false;
    }

    private void createAndPersistNewEntries(SpecialCostDTO specialCost) {
        final SpecialCostEntity specialCostEntity = new SpecialCostEntity();
        specialCostEntity.setCostDate(specialCost.costDate());

        for (SpecialCostEntryDTO newEntry : specialCost.entries()) {
            createAndPersist(specialCostEntity, newEntry);
        }
    }

    private void createAndPersistNewEntries(SpecialCostDTO specialCost, List<SpecialCostEntryEntity> existingEntities) {
        for (SpecialCostEntryDTO newEntry : specialCost.entries()) {
            boolean isDuplicate = false;

            for (SpecialCostEntryEntity specialCostEntryEntity : existingEntities) {
                if (newEntry.description().equals(specialCostEntryEntity.getDescription())) {
                    isDuplicate = true;
                    break;
                }
            }

            if (!isDuplicate) {
                createAndPersist(existingEntities.get(0).getSpecialCost(), newEntry);
            }
        }
    }

    private void createAndPersist(SpecialCostEntity specialCostEntity, SpecialCostEntryDTO newEntry) {
        final SpecialCostEntryEntity specialCostEntryEntity = new SpecialCostEntryEntity();
        specialCostEntryEntity.setDescription(newEntry.description());
        specialCostEntryEntity.setValue(newEntry.value());
        specialCostEntryEntity.setSpecialCost(specialCostEntity);

        specialCostEntryDAO.persist(specialCostEntryEntity);
    }
}
