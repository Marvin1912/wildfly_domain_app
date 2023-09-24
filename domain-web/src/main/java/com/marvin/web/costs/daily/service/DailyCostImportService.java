package com.marvin.web.costs.daily.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvin.common.costs.daily.dto.DailyCostDTO;
import com.marvin.common.jackson.JacksonMapperQualifier;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import org.apache.activemq.artemis.jms.client.ActiveMQJMSConnectionFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class DailyCostImportService {

    private static final Logger LOGGER = Logger.getLogger(DailyCostImportService.class.getName());

    private static final DailyCostDTO EMPTY_COST = new DailyCostDTO(null, null);

    private static InitialContext getInitialContext() throws NamingException {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        props.put(Context.PROVIDER_URL, "remote+https://host-3:8446");
        props.put(Context.SECURITY_PRINCIPAL, "jms-import");
        props.put(Context.SECURITY_CREDENTIALS, "password");
        return new InitialContext(props);
    }

    @Inject
    @JacksonMapperQualifier
    private ObjectMapper objectMapper;

    private ConnectionFactory connectionFactory;
    private Destination destination;

    @PostConstruct
    public void init() {
        try {
            final InitialContext context = getInitialContext();

            final ActiveMQJMSConnectionFactory connectionFactory = (ActiveMQJMSConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
            connectionFactory.setEnable1xPrefixes(false);
            connectionFactory.setUser("jms-import");
            connectionFactory.setPassword("password");
            this.connectionFactory = connectionFactory;

            this.destination = (Destination) context.lookup("/jms/queue/costs/import/daily");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void importDailyCost(DailyCostDTO dailyCost) {

        if (EMPTY_COST.equals(dailyCost)) {
            LOGGER.log(Level.WARNING, "Daily cost DTO is empty!!!");
            return;
        }

        try (Connection connection = connectionFactory.createConnection()) {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(destination);

            TextMessage message = session.createTextMessage(objectMapper.writeValueAsString(dailyCost));
            producer.send(message);
            LOGGER.log(Level.INFO, "Sent daily cost " + dailyCost + "!");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Could not sent daily cost for " + dailyCost + "!", e);
        }
    }
}
