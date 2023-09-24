package com.marvin.web.costs.daily.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvin.common.costs.daily.dao.DailyCostDAO;
import com.marvin.common.costs.daily.dto.DailyCostDTO;
import com.marvin.common.jackson.JacksonMapperQualifier;
import com.marvin.web.costs.daily.service.DailyCostImportService;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/daily_costs")
public class DailyCostController {

    @EJB
    private DailyCostDAO dailyCostDAO;

    @EJB
    private DailyCostImportService dailyCostImportService;

    @Inject
    @JacksonMapperQualifier
    private ObjectMapper objectMapper;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response importDailyCost(String dailyCostValue) {
        try {
            DailyCostDTO dailyCost = objectMapper.readValue(dailyCostValue, DailyCostDTO.class);
            dailyCostImportService.importDailyCost(dailyCost);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
}
