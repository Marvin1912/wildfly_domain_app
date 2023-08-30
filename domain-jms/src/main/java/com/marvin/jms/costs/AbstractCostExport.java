package com.marvin.jms.costs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvin.common.db.infrastructure.CrudDAO;
import com.marvin.common.jackson.JacksonMapperQualifier;
import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import jakarta.jms.Queue;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractCostExport<ENTITY, DTO> {

    private static final Logger LOGGER = Logger.getLogger(AbstractCostExport.class.getName());

    @Inject
    @JacksonMapperQualifier
    protected ObjectMapper objectMapper;

    protected Queue queue;
    protected ConnectionFactory connectionFactory;
    protected CrudDAO<ENTITY> dataAccessObject;

    public abstract void init();

    protected abstract DTO map(ENTITY e);

    public void doExport() {
        try (JMSContext jmsContext = connectionFactory.createContext()) {
            JMSProducer producer = jmsContext.createProducer();
            dataAccessObject.getAll()
                    .map(this::map)
                    .forEach(dto -> send(producer, dto));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Could not create JMSContext!", e);
        }
    }

    public void doExport(DTO dto) {
        try (JMSContext jmsContext = connectionFactory.createContext()) {
            JMSProducer producer = jmsContext.createProducer();
            send(producer, dto);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Could not create JMSContext!", e);
        }
    }

    private void send(JMSProducer producer, DTO dto) {
        try {
            producer.send(queue, objectMapper.writeValueAsString(dto));
            LOGGER.log(Level.INFO, "Sent " + dto + "!");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Could not marshal " + dto + "!", e);
        }
    }
}
