package com.marvin.jms.costs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvin.common.jackson.JacksonMapperQualifier;
import jakarta.inject.Inject;
import jakarta.jms.MessageListener;

public abstract class AbstractCostImportMDB<ENTITY> implements MessageListener {

    protected static final String HOST_NAME = System.getProperty("jboss.host.name");

    @Inject
    @JacksonMapperQualifier
    protected ObjectMapper objectMapper;

    protected abstract void persist(ENTITY entity);

}
