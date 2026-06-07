package br.com.orbital;

import br.com.orbital.bo.ManobraBO;
import br.com.orbital.entities.Manobra;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.Map;

@Path("/manobra")
public class ManobraResource {

    private ManobraBO manobraBO = new ManobraBO();

    // POST /manobra
    // Chamado pelo front em SateliteDetalhe.tsx quando o usuário clica em
    // "Calcular Manobra" ou "Manobra Emergencial"
    //
    // Body JSON: { "noradId": "28057" }
    //
    // Retorna: { janelaExecucao, deltaV, objetoRisco, descricao }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response calcular(Map<String, String> body)
            throws ClassNotFoundException, SQLException {

        String noradId = body.get("noradId");

        try {
            Manobra manobra = manobraBO.calcularManobra(noradId);
            return Response.ok(manobra).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}
