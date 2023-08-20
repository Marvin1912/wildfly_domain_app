package com.marvin.wildfly_domain_app.costs.base.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvin.wildfly_domain_app.costs.base.dao.BaseCostDAO;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/base_costs")
public class BaseCostController {

    @EJB
    private BaseCostDAO baseCostDAO;

    @Inject
    private ObjectMapper objectMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getBaseCosts() throws Exception {
        return objectMapper.writeValueAsString(baseCostDAO.getAll());
    }

}
