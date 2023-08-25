package com.marvin.jms.costs.monthly.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvin.common.costs.monthly.dao.MonthlyCostDAO;
import com.marvin.common.costs.monthly.dto.MonthlyCostDTO;
import com.marvin.common.jackson.JacksonMapperQualifier;
import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import jakarta.jms.Queue;

import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class MonthlyCostExportService {

    private static final Logger LOGGER = Logger.getLogger(MonthlyCostExportService.class.getName());

    @Resource(lookup = "java:/jms/queue/costs/export/monthly")
    private Queue queue;

    @Resource(lookup = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @EJB
    private MonthlyCostDAO monthlyCostDAO;

    @Inject
    @JacksonMapperQualifier
    private ObjectMapper objectMapper;

    public void doExport() {
        try (JMSContext jmsContext = connectionFactory.createContext()) {

            JMSProducer producer = jmsContext.createProducer();

            monthlyCostDAO.getAll()
                    .map(entity -> new MonthlyCostDTO(entity.getCostDate(), entity.getValue()))
                    .forEach(dto -> {
                try {
                    String body = objectMapper.writeValueAsString(dto);
                    producer.send(queue, body);
                    LOGGER.log(Level.INFO, "Sent monthly cost " + dto + "!");
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Could not marshal monthly cost " + dto + "!", e);
                }
            });
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Could not create JMSContext!", e);
        }
    }
}
