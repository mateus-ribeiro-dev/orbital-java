package br.com.orbital.dao;

import br.com.orbital.conexoes.ConexaoFactory;
import br.com.orbital.entities.Conjuncao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConjuncaoDao {

    Connection minhaConexao;

    public ConjuncaoDao() throws SQLException, ClassNotFoundException {
        this.minhaConexao = new ConexaoFactory().conexao();
    }

    // Verifica se um NORAD ID existe na tabela de conjunção
    // Retorna true se encontrar o ID como sat_1_id OU sat_2_id
    public boolean noradIdExiste(Long noradId) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
            "SELECT COUNT(*) FROM conjuncao WHERE sat_1_id = ? OR sat_2_id = ?"
        );
        stmt.setLong(1, noradId);
        stmt.setLong(2, noradId);

        ResultSet rs = stmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        stmt.close();
        minhaConexao.close();

        return count > 0;
    }

    // Busca o evento de maior risco para um NORAD ID
    // Prioridade: danger > warn > ok, e menor miss distance
    public Conjuncao buscarPiorRiscoPorNorad(Long noradId) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
            "SELECT * FROM conjuncao " +
            "WHERE sat_1_id = ? OR sat_2_id = ? " +
            "ORDER BY CASE status_risco " +
            "  WHEN 'danger' THEN 1 " +
            "  WHEN 'warn'   THEN 2 " +
            "  ELSE 3 END, min_rng ASC " +
            "FETCH FIRST 1 ROWS ONLY"
        );
        stmt.setLong(1, noradId);
        stmt.setLong(2, noradId);

        ResultSet rs = stmt.executeQuery();
        Conjuncao conjuncao = null;

        if (rs.next()) {
            conjuncao = mapear(rs);
        }

        rs.close();
        stmt.close();
        minhaConexao.close();

        return conjuncao;
    }

    // Busca todos os eventos de um NORAD ID ordenados por risco
    public List<Conjuncao> listarPorNorad(Long noradId) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
            "SELECT * FROM conjuncao " +
            "WHERE sat_1_id = ? OR sat_2_id = ? " +
            "ORDER BY CASE status_risco " +
            "  WHEN 'danger' THEN 1 " +
            "  WHEN 'warn'   THEN 2 " +
            "  ELSE 3 END, min_rng ASC"
        );
        stmt.setLong(1, noradId);
        stmt.setLong(2, noradId);

        ResultSet rs = stmt.executeQuery();
        List<Conjuncao> lista = new ArrayList<>();

        while (rs.next()) {
            lista.add(mapear(rs));
        }

        rs.close();
        stmt.close();
        minhaConexao.close();

        return lista;
    }

    // Mapeia uma linha do ResultSet para o objeto Conjuncao
    private Conjuncao mapear(ResultSet rs) throws SQLException {
        Conjuncao c = new Conjuncao();
        c.setId(rs.getLong("id_conjuncao"));
        c.setCdmId(rs.getLong("cdm_id"));
        c.setTca(rs.getString("tca"));
        c.setMinRng(rs.getDouble("min_rng"));
        c.setPc(rs.getObject("pc") != null ? rs.getDouble("pc") : null);
        c.setSat1Id(rs.getLong("sat_1_id"));
        c.setSat1Name(rs.getString("sat_1_name"));
        c.setSat1Type(rs.getString("sat1_type"));
        c.setSat2Id(rs.getLong("sat_2_id"));
        c.setSat2Name(rs.getString("sat_2_name"));
        c.setSat2Type(rs.getString("sat2_type"));
        c.setStatusRisco(rs.getString("status_risco"));
        return c;
    }
}
