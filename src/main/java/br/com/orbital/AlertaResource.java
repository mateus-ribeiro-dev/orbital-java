package br.com.orbital;

import br.com.orbital.bo.AlertaBO;
import br.com.orbital.entities.Alerta;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/alertas")
public class AlertaResource {

    private AlertaBO alertaBO = new AlertaBO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarAlertas() throws ClassNotFoundException, SQLException {
        List<Alerta> alertas = alertaBO.listarAlertas();
        return Response.ok(alertas).build();
    }
}
