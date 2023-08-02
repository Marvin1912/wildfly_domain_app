package com.marvin.wildfly_domain_app.costs.monthly.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvin.wildfly_domain_app.costs.monthly.dao.MonthlyCostDAO;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/monthly_costs")
public class MonthlyCostController {

    @EJB
    private MonthlyCostDAO monthlyCostDAO;

    @Inject
    private ObjectMapper objectMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getMonthlyCosts() throws Exception {
        return objectMapper.writeValueAsString(monthlyCostDAO.getMonthlyCosts());
    }

}
