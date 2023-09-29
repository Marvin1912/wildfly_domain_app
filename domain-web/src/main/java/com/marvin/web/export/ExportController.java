package com.marvin.web.export;

import com.marvin.domain.backup.export.CostsExporterLocal;
import jakarta.ejb.EJB;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/export_costs")
public class ExportController {

    @EJB
    private CostsExporterLocal costsExporter;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response exportCosts() {
        try {
            costsExporter.exportCosts();
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
}
