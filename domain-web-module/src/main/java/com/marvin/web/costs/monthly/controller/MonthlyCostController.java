package com.marvin.web.costs.monthly.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvin.common.costs.monthly.dao.MonthlyCostDAO;
import com.marvin.common.jackson.JacksonMapperQualifier;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;

@Path("/monthly_costs")
public class MonthlyCostController {

    @EJB
    private MonthlyCostDAO monthlyCostDAO;

    @Inject
    @JacksonMapperQualifier
    private ObjectMapper objectMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMonthlyCosts(
            @QueryParam("year") int year,
            @QueryParam("month") int month
    ) throws Exception {
        return Response.ok(
                objectMapper.writeValueAsString(monthlyCostDAO.get(LocalDate.of(year, month, 1)))
        ).build();
    }
}
