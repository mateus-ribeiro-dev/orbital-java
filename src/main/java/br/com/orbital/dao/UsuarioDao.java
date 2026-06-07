package br.com.orbital.dao;

import br.com.orbital.conexoes.ConexaoFactory;
import br.com.orbital.entities.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    Connection minhaConexao;

    public UsuarioDao() throws SQLException, ClassNotFoundException {
        this.minhaConexao = new ConexaoFactory().conexao();
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getLong("id_usuario"));
        u.setNome(rs.getString("nome"));
        u.setEmail(rs.getString("email"));
        u.setOrganizacao(rs.getString("organizacao"));
        u.setSenha(rs.getString("senha"));
        return u;
    }

    // INSERT — cadastra um novo usuário
    public String inserir(Usuario usuario) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
            "INSERT INTO usuario (nome, email, organizacao, senha) VALUES (?, ?, ?, ?)"
        );
        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getEmail());
        stmt.setString(3, usuario.getOrganizacao());
        stmt.setString(4, usuario.getSenha());
        stmt.execute();
        stmt.close();
        minhaConexao.close();
        return "Usuário cadastrado com sucesso!";
    }

    // SELECT por ID
    public Usuario buscarPorId(Long id) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
            "SELECT * FROM usuario WHERE id_usuario = ?"
        );
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();
        Usuario usuario = null;
        if (rs.next()) usuario = mapear(rs);
        rs.close(); stmt.close(); minhaConexao.close();
        return usuario;
    }

    // SELECT por email — usado para verificar duplicidade no cadastro
    public Usuario buscarPorEmail(String email) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
            "SELECT * FROM usuario WHERE email = ?"
        );
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        Usuario usuario = null;
        if (rs.next()) usuario = mapear(rs);
        rs.close(); stmt.close(); minhaConexao.close();
        return usuario;
    }

    // SELECT ALL
    public List<Usuario> listarTodos() throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
            "SELECT * FROM usuario"
        );
        ResultSet rs = stmt.executeQuery();
        List<Usuario> lista = new ArrayList<>();
        while (rs.next()) lista.add(mapear(rs));
        rs.close(); stmt.close(); minhaConexao.close();
        return lista;
    }

    // UPDATE
    public String atualizar(Usuario usuario) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
            "UPDATE usuario SET nome=?, email=?, organizacao=?, senha=? WHERE id_usuario=?"
        );
        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getEmail());
        stmt.setString(3, usuario.getOrganizacao());
        stmt.setString(4, usuario.getSenha());
        stmt.setLong(5, usuario.getId());
        stmt.executeUpdate();
        stmt.close(); minhaConexao.close();
        return "Usuário atualizado com sucesso!";
    }

    // DELETE
    public String deletar(Long id) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
            "DELETE FROM usuario WHERE id_usuario = ?"
        );
        stmt.setLong(1, id);
        stmt.execute();
        stmt.close(); minhaConexao.close();
        return "Usuário deletado com sucesso!";
    }
}
