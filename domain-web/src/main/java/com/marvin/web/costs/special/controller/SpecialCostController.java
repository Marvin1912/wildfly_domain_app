package com.marvin.web.costs.special.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvin.common.costs.special.dao.SpecialCostEntryDAO;
import com.marvin.common.jackson.JacksonMapperQualifier;
import com.marvin.web.costs.special.model.SpecialCostEntryExportDTO;
import com.marvin.web.costs.special.model.SpecialCostExportDTO;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.stream.Collectors;

@Path("/special_costs")
public class SpecialCostController {

    @EJB
    private SpecialCostEntryDAO specialCostEntryDAO;

    @Inject
    @JacksonMapperQualifier
    private ObjectMapper objectMapper;

    @GET
    @Path("/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSpecialCost(@PathParam("date") String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            return Response.ok(
                    objectMapper.writeValueAsString(
                            specialCostEntryDAO.get(localDate).stream()
                                    .map(s -> new SimpleImmutableEntry<>(
                                                    s.getSpecialCost().getCostDate(),
                                                    new SpecialCostEntryExportDTO(s.getValue(), s.getDescription())
                                            )
                                    )
                                    .collect(Collectors.groupingBy(
                                                    SimpleImmutableEntry::getKey,
                                                    Collectors.mapping(SimpleImmutableEntry::getValue, Collectors.toList())
                                            )
                                    )
                                    .entrySet().stream()
                                    .map(e -> new SpecialCostExportDTO(e.getKey(), e.getValue()))
                    )).build();
        } catch (Exception e) {
            return Response.status(
                    Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()
            ).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSpecialCosts(
            @QueryParam("year") int year,
            @QueryParam("month") int month
    ) throws Exception {
        return Response.ok(
                objectMapper.writeValueAsString(specialCostEntryDAO.get(LocalDate.of(year, month, 1)))
        ).build();
    }
}
