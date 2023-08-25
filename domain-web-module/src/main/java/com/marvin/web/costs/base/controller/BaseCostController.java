package com.marvin.web.costs.base.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvin.common.costs.base.dao.BaseCostDAO;
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

@Path("/base_costs")
public class BaseCostController {

    @EJB
    private BaseCostDAO baseCostDAO;

    @Inject
    @JacksonMapperQualifier
    private ObjectMapper objectMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBaseCosts(
            @QueryParam("year") int year,
            @QueryParam("month") int month
    ) throws Exception {
        return Response.ok(
                objectMapper.writeValueAsString(baseCostDAO.get(LocalDate.of(year, month, 1)))
        ).build();
    }
}
