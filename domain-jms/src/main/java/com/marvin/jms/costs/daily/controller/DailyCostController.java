package com.marvin.jms.costs.daily.controller;

import com.marvin.jms.costs.daily.service.DailyCostExportService;
import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/daily_costs")
public class DailyCostController {

    @Resource(name = "DefaultManagedExecutorService")
    private ManagedExecutorService executorService;

    @EJB
    private DailyCostExportService dailyCostExportService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response triggerJmsExport() {
        executorService.execute(() -> dailyCostExportService.doExport());
        return Response.noContent().build();
    }
}
