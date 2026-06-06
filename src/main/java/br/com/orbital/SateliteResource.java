package br.com.orbital;

import br.com.orbital.bo.SateliteBO;
import br.com.orbital.entities.Satelite;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.sql.SQLException;
import java.util.List;

@Path("/satelites")
public class SateliteResource {

    private SateliteBO sateliteBO = new SateliteBO();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response inserir(Satelite satelite, @Context UriInfo uriInfo)
            throws ClassNotFoundException, SQLException {
        try {
            Satelite salvo = sateliteBO.cadastrar(satelite);
            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.path(String.valueOf(salvo.getId()));
            return Response.created(builder.build()).entity(salvo).build();

        } catch (IllegalArgumentException e) {
            // NORAD ID inválido — retorna 400 com mensagem clara
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    // GET /satelites
    // Lista todos os satélites cadastrados
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Satelite> listarTodos() throws ClassNotFoundException, SQLException {
        return sateliteBO.listarTodosBo();
    }

    // GET /satelites/{id}
    // Busca um satélite pelo ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Satelite buscarPorId(@PathParam("id") String id)
            throws ClassNotFoundException, SQLException {
        return sateliteBO.buscarPorId(id);
    }

    // PUT /satelites/{id}
    // Atualiza um satélite
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizar(Satelite satelite, @PathParam("id") Long id)
            throws ClassNotFoundException, SQLException {
        satelite.setId(id);
        sateliteBO.atualizarBo(satelite);
        return Response.ok().build();
    }

    // DELETE /satelites/{id}
    // Remove um satélite
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") String id)
            throws ClassNotFoundException, SQLException {
        sateliteBO.deletarBo(id);
        return Response.ok().build();
    }
}
