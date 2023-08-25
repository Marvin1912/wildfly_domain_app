package com.marvin.web.costs.salary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvin.common.costs.salary.dao.SalaryDAO;
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

@Path("/salaries")
public class SalaryController {

    @EJB
    private SalaryDAO salaryDAO;

    @Inject
    @JacksonMapperQualifier
    private ObjectMapper objectMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSalaries(
            @QueryParam("year") int year,
            @QueryParam("month") int month
    ) throws Exception {
        return Response.ok(
                objectMapper.writeValueAsString(salaryDAO.get(LocalDate.of(year, month, 1)))
        ).build();
    }
}
