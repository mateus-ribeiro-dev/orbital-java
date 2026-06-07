package br.com.orbital.bo;

import br.com.orbital.dao.UsuarioDao;
import br.com.orbital.entities.Usuario;

import java.sql.SQLException;
import java.util.List;

public class UsuarioBO {

    // Cadastra um novo usuário.
    // Valida se o email já está em uso antes de inserir.
    public Usuario cadastrar(Usuario usuario) throws SQLException, ClassNotFoundException {
        UsuarioDao dao = new UsuarioDao();
        Usuario existente = dao.buscarPorEmail(usuario.getEmail());
        if (existente != null) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }
        UsuarioDao dao2 = new UsuarioDao();
        dao2.inserir(usuario);
        return usuario;
    }

    public Usuario buscarPorId(Long id) throws SQLException, ClassNotFoundException {
        return new UsuarioDao().buscarPorId(id);
    }

    public List<Usuario> listarTodos() throws SQLException, ClassNotFoundException {
        return new UsuarioDao().listarTodos();
    }

    public void atualizar(Usuario usuario) throws SQLException, ClassNotFoundException {
        new UsuarioDao().atualizar(usuario);
    }

    public void deletar(Long id) throws SQLException, ClassNotFoundException {
        new UsuarioDao().deletar(id);
    }
}
