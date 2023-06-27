package com.marvin.wildfly_domain_app.costs.daily.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvin.wildfly_domain_app.costs.daily.service.DailyCostSessionBean;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/daily_costs")
public class DailyCostController {

    @EJB
    private DailyCostSessionBean dailyCostSessionBean;

    @Inject
    private ObjectMapper objectMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDailyCosts(
            @QueryParam("year") int year,
            @QueryParam("month") int month
    ) throws Exception {
        return objectMapper.writeValueAsString(dailyCostSessionBean.getDailyCosts(year, month));
    }

}
