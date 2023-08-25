package com.marvin.jms.costs.monthly.controller;

import com.marvin.jms.costs.monthly.service.MonthlyCostExportService;
import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/monthly_costs")
public class MonthlyCostController {

    @Resource(name = "DefaultManagedExecutorService")
    private ManagedExecutorService executorService;

    @EJB
    private MonthlyCostExportService monthlyCostExportService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response triggerJmsExport() {
        executorService.execute(() -> monthlyCostExportService.doExport());
        return Response.noContent().build();
    }
}
