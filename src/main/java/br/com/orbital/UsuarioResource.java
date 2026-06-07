package br.com.orbital;

import br.com.orbital.bo.UsuarioBO;
import br.com.orbital.entities.Usuario;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.sql.SQLException;
import java.util.List;

@Path("/auth")
public class UsuarioResource {

    private UsuarioBO usuarioBO = new UsuarioBO();

    @POST
    @Path("/cadastro")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrar(Usuario usuario, @Context UriInfo uriInfo)
            throws ClassNotFoundException, SQLException {
        try {
            Usuario salvo = usuarioBO.cadastrar(usuario);
            // não devolve a senha na resposta
            salvo.setSenha(null);
            return Response.status(Response.Status.CREATED).entity(salvo).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    // GET /auth/usuarios
    // Lista todos os usuários cadastrados
    @GET
    @Path("/usuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> listarTodos() throws ClassNotFoundException, SQLException {
        return usuarioBO.listarTodos();
    }

    // GET /auth/usuarios/{id}
    // Busca um usuário pelo ID
    @GET
    @Path("/usuarios/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") Long id)
            throws ClassNotFoundException, SQLException {
        Usuario usuario = usuarioBO.buscarPorId(id);
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"Usuário não encontrado\"}")
                    .build();
        }
        usuario.setSenha(null);
        return Response.ok(usuario).build();
    }

    // PUT /auth/usuarios/{id}
    // Atualiza os dados de um usuário
    @PUT
    @Path("/usuarios/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizar(Usuario usuario, @PathParam("id") Long id)
            throws ClassNotFoundException, SQLException {
        usuario.setId(id);
        usuarioBO.atualizar(usuario);
        return Response.ok().build();
    }

    // DELETE /auth/usuarios/{id}
    // Remove um usuário
    @DELETE
    @Path("/usuarios/{id}")
    public Response deletar(@PathParam("id") Long id)
            throws ClassNotFoundException, SQLException {
        usuarioBO.deletar(id);
        return Response.ok().build();
    }
}
