package br.com.orbital;

import br.com.orbital.bo.ContatoBO;
import br.com.orbital.entities.Contato;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.sql.SQLException;
import java.util.List;

@Path("/contato")
public class ContatoResource {

    private ContatoBO contatoBO = new ContatoBO();

    // Inserir
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserirRs(Contato contato, @Context UriInfo uriInfo ) throws ClassNotFoundException, SQLException {
        ContatoBO.insertBo(contato);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path((contato.getNome()));
        return Response.created(builder.build()).build();
    }

    // Listar todos
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Contato> listarTodosRs() throws ClassNotFoundException, SQLException {
        return contatoBO.listarTodosBo();
    }

    // Buscar por ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Contato buscarId(@PathParam("id") String id) throws ClassNotFoundException, SQLException, SQLException {
        return  (Contato)  contatoBO.buscarPorId(id);
    }

    // Atualizar
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarRs(Contato contato, @PathParam("id") Long id) throws ClassNotFoundException, SQLException {
        contato.setContatoId(id);
        contatoBO.atualizarBo(contato);
        return Response.ok().build();
    }

    // Deletar
    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deletarRs(@PathParam("id") String id) throws ClassNotFoundException, SQLException {
        contatoBO.deletarBo(id);
        return Response.ok().build();
    }

}
