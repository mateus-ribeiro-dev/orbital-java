package br.com.orbital.dao;

import br.com.orbital.conexoes.ConexaoFactory;
import br.com.orbital.entities.Manobra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManobraDao {

    Connection minhaConexao;

    public ManobraDao() throws SQLException, ClassNotFoundException {
        this.minhaConexao = new ConexaoFactory().conexao();
    }

    // Busca o evento de maior risco na tabela conjuncao para um noradId
    // Prioridade: danger > warn > ok, desempate pela menor miss distance
    // Usado para calcular a manobra necessária
    public Manobra calcularManobra(String noradId) throws SQLException {

        PreparedStatement stmt = minhaConexao.prepareStatement(
            "SELECT tca, min_rng, pc, sat_1_name, sat_2_name, sat_1_id, sat_2_id, status_risco " +
            "FROM conjuncao " +
            "WHERE sat_1_id = ? OR sat_2_id = ? " +
            "ORDER BY CASE status_risco " +
            "  WHEN 'danger' THEN 1 " +
            "  WHEN 'warn'   THEN 2 " +
            "  ELSE 3 END, min_rng ASC " +
            "FETCH FIRST 1 ROWS ONLY"
        );

        stmt.setString(1, noradId);
        stmt.setString(2, noradId);

        ResultSet rs = stmt.executeQuery();
        Manobra manobra = null;

        if (rs.next()) {
            String tca       = rs.getString("tca");
            double minRng    = rs.getDouble("min_rng");
            double pc        = rs.getDouble("pc");
            String sat1Name  = rs.getString("sat_1_name");
            String sat2Name  = rs.getString("sat_2_name");
            long   sat1Id    = rs.getLong("sat_1_id");
            String risco     = rs.getString("status_risco");

            // objeto de risco é o que NÃO é o satélite consultado
            String objetoRisco = String.valueOf(sat1Id).equals(noradId) ? sat2Name : sat1Name;

            // deltaV estimado: quanto menor a miss distance, maior o impulso necessário
            // fórmula simplificada baseada na miss distance em km
            double missKm  = minRng / 1000.0;
            double deltaV  = Math.round((1.0 / (missKm + 0.01)) * 10.0) / 10.0;
            deltaV = Math.min(deltaV, 50.0); // limite máximo de 50 m/s

            // janela de execução baseada no TCA
            // exibe o TCA diretamente pois já está no formato legível
            String janela = tca != null ? tca.substring(0, 16).replace("T", " ") : "—";

            // descrição varia conforme o nível de risco
            String descricao;
            if ("danger".equals(risco)) {
                descricao = String.format(
                    "MANOBRA EMERGENCIAL: miss distance de %.3f km com Pc de %.4f%%. " +
                    "Execute o impulso de %.1f m/s dentro da janela indicada. " +
                    "Atraso pode resultar em colisão.",
                    missKm, pc * 100, deltaV
                );
            } else {
                descricao = String.format(
                    "Manobra de desvio recomendada: miss distance de %.3f km com Pc de %.4f%%. " +
                    "Um impulso de %.1f m/s dentro da janela indicada garante a segurança orbital.",
                    missKm, pc * 100, deltaV
                );
            }

            manobra = new Manobra(janela, deltaV, objetoRisco, descricao);
        }

        rs.close();
        stmt.close();
        minhaConexao.close();

        return manobra;
    }
}
