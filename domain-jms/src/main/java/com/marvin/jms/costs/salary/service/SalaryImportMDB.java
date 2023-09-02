package com.marvin.jms.costs.salary.service;

import com.marvin.common.costs.salary.dao.SalaryDAO;
import com.marvin.common.costs.salary.dto.SalaryDTO;
import com.marvin.common.costs.salary.entity.SalaryEntity;
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
                propertyValue = "/jms/queue/costs/import/salary"
        )
})
public class SalaryImportMDB extends AbstractCostImportMDB<SalaryDTO> {

    private static final Logger LOGGER = Logger.getLogger(SalaryImportMDB.class.getName());

    @EJB
    private SalaryDAO salaryDAO;

    @EJB
    private SalaryExportService salaryExportService;

    @Override
    public void onMessage(Message message) {
        try {
            final String messageBody = message.getBody(String.class);
            LOGGER.log(Level.INFO, "[" + HOST_NAME + "] Salary received: " + messageBody);
            final SalaryDTO salary = objectMapper.readValue(messageBody, SalaryDTO.class);

            persist(salary);

            salaryExportService.doExport(salary);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "[" + HOST_NAME + "] Error while trying to consume messages: " + e.getMessage());
        }
    }

    @Override
    protected void persist(SalaryDTO salary) {
        final List<SalaryEntity> persistedStateList = salaryDAO.get(salary.salaryDate());
        if (persistedStateList.isEmpty()) {
            SalaryEntity salaryEntity = new SalaryEntity(salary.salaryDate(), salary.value());
            salaryDAO.persist(salaryEntity);
        }
    }
}
