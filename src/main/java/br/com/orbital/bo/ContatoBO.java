package br.com.orbital.bo;

import br.com.orbital.dao.ContatoDao;
import br.com.orbital.entities.Contato;

import java.sql.SQLException;
import java.util.List;

public class ContatoBO {

    ContatoDao contatoDao;

    //Selecionar
    public Contato buscarPorId(String id) throws SQLException, ClassNotFoundException {
        contatoDao = new ContatoDao();
        // regra de negócios
        return contatoDao.selecionarPorId(id);
    }

    //inserir
    public static void insertBo(Contato contato) throws SQLException, ClassNotFoundException {
        ContatoDao contatoDao = new ContatoDao();
        // regra de negócios
        contatoDao.inserir(contato);
    }

    //atualizar
    public void atualizarBo(Contato contato) throws SQLException, ClassNotFoundException {
        contatoDao = new ContatoDao();
        // regra de negócios
        contatoDao.atualizar(contato);
    }

    //Deletar
    public void deletarBo(String id) throws SQLException, ClassNotFoundException {
        contatoDao = new ContatoDao();
        // regra de negócios
        contatoDao.deletar(id);
    }

    // selecionar todos
    public List<Contato> listarTodosBo() throws SQLException, ClassNotFoundException {
        contatoDao = new ContatoDao();
        return contatoDao.listarTodos();
    }
}
