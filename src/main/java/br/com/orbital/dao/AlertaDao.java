package br.com.orbital.dao;

import br.com.orbital.conexoes.ConexaoFactory;
import br.com.orbital.entities.Alerta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertaDao {

    Connection minhaConexao;

    public AlertaDao() throws SQLException, ClassNotFoundException {
        this.minhaConexao = new ConexaoFactory().conexao();
    }

    // Busca um alerta por satélite — pega o pior evento de conjunção de cada um
    // O FETCH FIRST 1 ROWS ONLY dentro do subquery garante que cada satélite
    // aparece uma única vez, com o evento de maior risco
    public List<Alerta> listarAlertas() throws SQLException {

        PreparedStatement stmt = minhaConexao.prepareStatement(
                "SELECT " +
                        "  s.id_satelite, " +
                        "  s.nome_satelite, " +
                        "  s.noradId, " +
                        "  s.status_risco, " +
                        "  s.prob_colisao, " +
                        "  c.min_rng, " +
                        "  c.tca " +
                        "FROM satelite s " +
                        "LEFT JOIN ( " +
                        "  SELECT norad_id, min_rng, tca " +
                        "  FROM ( " +
                        "    SELECT sat_1_id AS norad_id, min_rng, tca, " +
                        "           ROW_NUMBER() OVER (PARTITION BY sat_1_id ORDER BY " +
                        "             CASE status_risco WHEN 'danger' THEN 1 ELSE 2 END, min_rng ASC) AS rn " +
                        "    FROM conjuncao " +
                        "    WHERE status_risco IN ('warn','danger') " +
                        "    UNION ALL " +
                        "    SELECT sat_2_id, min_rng, tca, " +
                        "           ROW_NUMBER() OVER (PARTITION BY sat_2_id ORDER BY " +
                        "             CASE status_risco WHEN 'danger' THEN 1 ELSE 2 END, min_rng ASC) AS rn " +
                        "    FROM conjuncao " +
                        "    WHERE status_risco IN ('warn','danger') " +
                        "  ) WHERE rn = 1 " +
                        ") c ON TO_NUMBER(s.noradId) = c.norad_id " +
                        "WHERE s.status_risco IN ('warn', 'danger') " +
                        "ORDER BY CASE s.status_risco WHEN 'danger' THEN 1 ELSE 2 END"
        );

        ResultSet rs = stmt.executeQuery();
        List<Alerta> lista = new ArrayList<>();
        int contador = 1;

        while (rs.next()) {
            String nome        = rs.getString("nome_satelite");
            String noradId     = rs.getString("noradId");
            String risco       = rs.getString("status_risco");
            double probColisao = rs.getDouble("prob_colisao");
            double minRng      = rs.getDouble("min_rng");
            String tca         = rs.getString("tca");

            String nivel = "danger".equals(risco) ? "CRITICO" : "ATENCAO";

            String distancia = minRng > 0
                    ? String.format("%.3f km", minRng / 1000.0)
                    : "—";

            String descricao;
            if ("CRITICO".equals(nivel)) {
                descricao = String.format(
                        "Objeto a %s em trajetória de colisão. " +
                                "Probabilidade: %.2f%%. Manobra de desvio recomendada imediatamente.",
                        distancia, probColisao
                );
            } else {
                descricao = String.format(
                        "Debris detectado a %s. " +
                                "Probabilidade de colisão: %.2f%%. Monitoramento intensificado.",
                        distancia, probColisao
                );
            }

            String tempo = tca != null
                    ? "TCA: " + tca.substring(0, 16).replace("T", " ")
                    : "agora";

            lista.add(new Alerta(
                    (long) contador++,
                    nome,
                    noradId,
                    nivel,
                    descricao,
                    probColisao,
                    distancia,
                    tempo
            ));
        }

        rs.close();
        stmt.close();
        minhaConexao.close();

        return lista;
    }
}