package br.com.orbital;

import br.com.orbital.bo.SateliteBO;
import br.com.orbital.entities.Satelite;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Path("/satelites")
public class SateliteResource {

    private SateliteBO sateliteBO = new SateliteBO();

    // POST /satelites
    // Cadastra um satélite novo.
    // Valida o noradId na tabela conjuncao antes de salvar.
    // Se inválido: retorna 400. Se válido: preenche statusRisco e probColisao
    // automaticamente com os dados do Space-Track e retorna 201.
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
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    // GET /satelites
    // Lista todos os satélites cadastrados no banco
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Satelite> listarTodos()
            throws ClassNotFoundException, SQLException {
        return sateliteBO.listarTodosBo();
    }

    // GET /satelites/{noradId}
    // Busca um satélite pelo noradId — chamado pelo SateliteDetalhe.tsx
    // O front passa o noradId na URL (ex: /satelites/28057)
    @GET
    @Path("/{noradId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorNoradId(@PathParam("noradId") String noradId)
            throws ClassNotFoundException, SQLException {
        Satelite satelite = sateliteBO.buscarPorNoradId(noradId);
        if (satelite == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"Satélite não encontrado\"}")
                    .build();
        }
        return Response.ok(satelite).build();
    }

    // PUT /satelites/{noradId}
    // Atualiza parcialmente um satélite — chamado pelo ModalEditar no SateliteDetalhe.tsx
    // O front manda apenas: { nome, cosparId, orbita, altitude }
    @PUT
    @Path("/{noradId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarParcial(
            Map<String, Object> body,
            @PathParam("noradId") String noradId)
            throws ClassNotFoundException, SQLException {

        String nome     = (String) body.get("nome");
        String cosparId = (String) body.getOrDefault("cosparId", "");
        String orbita   = (String) body.getOrDefault("orbita", "LEO");
        double altitude = body.get("altitude") != null
                ? ((Number) body.get("altitude")).doubleValue()
                : 0.0;

        sateliteBO.atualizarParcialBo(noradId, nome, cosparId, orbita, altitude);

        // busca o satélite atualizado para devolver ao front
        Satelite atualizado = sateliteBO.buscarPorNoradId(noradId);
        return Response.ok(atualizado).build();
    }

    // DELETE /satelites/{id}
    // Remove um satélite pelo id_satelite (número sequencial do banco)
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") String id)
            throws ClassNotFoundException, SQLException {
        sateliteBO.deletarBo(id);
        return Response.ok().build();
    }
}