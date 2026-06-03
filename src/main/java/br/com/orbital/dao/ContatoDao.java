package br.com.orbital.dao;

import br.com.orbital.conexoes.ConexaoFactory;
import br.com.orbital.entities.Contato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContatoDao {
    Connection minhaConexao;

    public ContatoDao() throws SQLException, ClassNotFoundException {
        this.minhaConexao = new ConexaoFactory().conexao();
    }

    // Insert / Inserir
    public String inserir(Contato contato) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement("Insert into contato (nome, email, telefone, assunto, mensagem) values (?,?,?,?,?)");
        stmt.setString(1, contato.getNome());
        stmt.setString(2, contato.getEmail());
        stmt.setString(3, contato.getTelefone());
        stmt.setString(4, contato.getAssunto());
        stmt.setString(5, contato.getMensagem());

        stmt.execute();
        stmt.close();
        minhaConexao.close();

        return  "Contato cadastrado com sucesso!";
    }

    // Delete
    public String deletar(String id) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement("Delete From contato where id_contato =?");
        stmt.setString(1, id);

        stmt.execute();
        stmt.close();
        minhaConexao.close();

        return "Contato Deletado com Sucesso!";
    }

    // UpDate
    public String atualizar(Contato contato) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement
                ("update contato set nome = ?, email = ?, telefone = ?, assunto = ?, mensagem = ? where id_contato = ?");
        stmt.setString(1, contato.getNome());
        stmt.setString(2, contato.getEmail());
        stmt.setString(3, contato.getTelefone());
        stmt.setString(4, contato.getAssunto());
        stmt.setString(5, contato.getMensagem());
        stmt.setLong(6, contato.getContatoId());

        stmt.executeUpdate();
        stmt.close();
        minhaConexao.close();

        return "Contato Atualizado com Sucesso!";
    }


    // Select Id
    public Contato selecionarPorId(String id) throws SQLException {
        Contato objContato = null;
        PreparedStatement stmt = minhaConexao.prepareStatement("select * from contato where id_contato = ?");
        stmt.setString(1, id);

        ResultSet rs = stmt.executeQuery();

        if(rs.next()) {
            objContato = new Contato();
            objContato.setContatoId(rs.getLong(1));
            objContato.setNome(rs.getString(2));
            objContato.setEmail(rs.getString(3));
            objContato.setTelefone(rs.getString(4));
            objContato.setAssunto(rs.getString(5));
            objContato.setMensagem(rs.getString(6));
        }
        minhaConexao.close();
        return objContato;
    }

    // Select All
    public List<Contato> listarTodos() throws SQLException {
        List<Contato> lista = new ArrayList<>();

        PreparedStatement stmt = minhaConexao.prepareStatement("SELECT * FROM contato");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Contato objContato = new Contato();
            objContato.setContatoId(rs.getLong(1));
            objContato.setNome(rs.getString(2));
            objContato.setEmail(rs.getString(3));
            objContato.setTelefone(rs.getString(4));
            objContato.setAssunto(rs.getString(5));
            objContato.setMensagem(rs.getString(6));
            lista.add(objContato);
        }

        rs.close();
        stmt.close();
        minhaConexao.close();
        return lista;
    }
}
