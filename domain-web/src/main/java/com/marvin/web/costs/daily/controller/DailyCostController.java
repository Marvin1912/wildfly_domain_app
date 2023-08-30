package com.marvin.web.costs.daily.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvin.common.costs.daily.dao.DailyCostDAO;
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

@Path("/daily_costs")
public class DailyCostController {

    @EJB
    private DailyCostDAO dailyCostDAO;

    @Inject
    @JacksonMapperQualifier
    private ObjectMapper objectMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDailyCosts(
            @QueryParam("year") int year,
            @QueryParam("month") int month,
            @QueryParam("day") int day
    ) throws Exception {
        return Response.ok(
                objectMapper.writeValueAsString(dailyCostDAO.get(LocalDate.of(year, month, day)))
        ).build();
    }
}
