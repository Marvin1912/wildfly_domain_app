package com.marvin.jms.costs.salary.controller;

import com.marvin.jms.costs.salary.service.SalaryExportService;
import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/salaries")
public class SalaryController {

    @Resource(name = "DefaultManagedExecutorService")
    private ManagedExecutorService executorService;

    @EJB
    private SalaryExportService salaryExportService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response triggerJmsExport() {
        executorService.execute(() -> salaryExportService.doExport());
        return Response.noContent().build();
    }
}
