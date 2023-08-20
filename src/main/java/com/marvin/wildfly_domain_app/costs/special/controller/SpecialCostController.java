package com.marvin.wildfly_domain_app.costs.special.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvin.wildfly_domain_app.costs.special.dao.SpecialCostEntryDAO;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/special_costs")
public class SpecialCostController {

    @EJB
    private SpecialCostEntryDAO specialCostEntryDAO;

    @Inject
    private ObjectMapper objectMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSpecialCosts() throws Exception {
        return objectMapper.writeValueAsString(specialCostEntryDAO.getAll());
    }

}
