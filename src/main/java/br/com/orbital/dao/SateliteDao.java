package br.com.orbital.dao;

import br.com.orbital.conexoes.ConexaoFactory;
import br.com.orbital.entities.Satelite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SateliteDao {

    Connection minhaConexao;

    public SateliteDao() throws SQLException, ClassNotFoundException {
        this.minhaConexao = new ConexaoFactory().conexao();
    }

    // Insert
    public String inserir(Satelite satelite) throws SQLException {

        PreparedStatement stmt = minhaConexao.prepareStatement(
                "INSERT INTO satelite " +
                        "(nome_satelite, noradId, cosparId, orbita, altitude, combustivel, inclinacao, proxima_janela, prob_colisao, delta_v, status_risco) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        stmt.setString(1, satelite.getNomeSatelite());
        stmt.setString(2, satelite.getNoradId());
        stmt.setString(3, satelite.getCosparId());
        stmt.setString(4, satelite.getOrbita());
        stmt.setDouble(5, satelite.getAltitude());
        stmt.setDouble(6, satelite.getCombustivel());
        stmt.setDouble(7, satelite.getInclinacao());
        stmt.setString(8, satelite.getProximaJanela());
        stmt.setDouble(9, satelite.getProbColisao());
        stmt.setDouble(10, satelite.getDeltaV());
        stmt.setString(11, satelite.getStatusRisco());

        stmt.execute();
        stmt.close();
        minhaConexao.close();

        return "Satélite cadastrado com sucesso!";
    }

    // Delete by ID
    public String deletar(String codigo) throws SQLException {

        PreparedStatement stmt =
                minhaConexao.prepareStatement(
                        "DELETE FROM satelite WHERE id_satelite = ?");

        stmt.setString(1, codigo);

        stmt.execute();
        stmt.close();
        minhaConexao.close();

        return "Satélite deletado com sucesso!";
    }

    // Update by ID
    public String atualizar(Satelite satelite) throws SQLException {

        PreparedStatement stmt = minhaConexao.prepareStatement(
                "UPDATE satelite SET " +
                        "nome_satelite = ?, " +
                        "noradId = ?, " +
                        "cosparId = ?, " +
                        "orbita = ?, " +
                        "altitude = ?, " +
                        "combustivel = ?, " +
                        "inclinacao = ?, " +
                        "proxima_janela = ?, " +
                        "prob_colisao = ?, " +
                        "delta_v = ?, " +
                        "status_risco = ? " +
                        "WHERE id_satelite = ?");

        stmt.setString(1, satelite.getNomeSatelite());
        stmt.setString(2, satelite.getNoradId());
        stmt.setString(3, satelite.getCosparId());
        stmt.setString(4, satelite.getOrbita());
        stmt.setDouble(5, satelite.getAltitude());
        stmt.setDouble(6, satelite.getCombustivel());
        stmt.setDouble(7, satelite.getInclinacao());
        stmt.setString(8, satelite.getProximaJanela());
        stmt.setDouble(9, satelite.getProbColisao());
        stmt.setDouble(10, satelite.getDeltaV());
        stmt.setString(11, satelite.getStatusRisco());
        stmt.setLong(12, satelite.getId());

        stmt.executeUpdate();
        stmt.close();
        minhaConexao.close();

        return "Satélite atualizado com sucesso!";
    }

    // Select by ID
    public Satelite selecionarPorId(String codigo) throws SQLException {

        Satelite satelite = null;

        PreparedStatement stmt =
                minhaConexao.prepareStatement(
                        "SELECT * FROM satelite WHERE id_satelite = ?");

        stmt.setString(1, codigo);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            satelite = new Satelite();

            satelite.setId(rs.getLong(1));
            satelite.setNomeSatelite(rs.getString(2));
            satelite.setNoradId(rs.getString(3));
            satelite.setCosparId(rs.getString(4));
            satelite.setOrbita(rs.getString(5));
            satelite.setAltitude(rs.getDouble(6));
            satelite.setCombustivel(rs.getDouble(7));
            satelite.setInclinacao(rs.getDouble(8));
            satelite.setProximaJanela(rs.getString(9));
            satelite.setProbColisao(rs.getDouble(10));
            satelite.setDeltaV(rs.getDouble(11));
            satelite.setStatusRisco(rs.getString(12));
        }

        minhaConexao.close();
        return satelite;
    }

    // Select All
    public List<Satelite> listarTodos() throws SQLException {

        List<Satelite> lista = new ArrayList<>();

        PreparedStatement stmt =
                minhaConexao.prepareStatement(
                        "SELECT * FROM satelite");

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            Satelite satelite = new Satelite();

            satelite.setId(rs.getLong(1));
            satelite.setNomeSatelite(rs.getString(2));
            satelite.setNoradId(rs.getString(3));
            satelite.setCosparId(rs.getString(4));
            satelite.setOrbita(rs.getString(5));
            satelite.setAltitude(rs.getDouble(6));
            satelite.setCombustivel(rs.getDouble(7));
            satelite.setInclinacao(rs.getDouble(8));
            satelite.setProximaJanela(rs.getString(9));
            satelite.setProbColisao(rs.getDouble(10));
            satelite.setDeltaV(rs.getDouble(11));
            satelite.setStatusRisco(rs.getString(12));

            lista.add(satelite);
        }

        rs.close();
        stmt.close();
        minhaConexao.close();

        return lista;
    }
}