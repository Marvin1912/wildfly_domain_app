package com.marvin.wildfly_domain_app.costs.salary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvin.wildfly_domain_app.costs.salary.dao.SalaryDAO;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/salaries")
public class SalaryController {

    @EJB
    private SalaryDAO salaryDAO;

    @Inject
    private ObjectMapper objectMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSalaries() throws Exception {
        return objectMapper.writeValueAsString(salaryDAO.getAll());
    }

}
