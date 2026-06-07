package br.com.orbital.bo;

import br.com.orbital.dao.ManobraDao;
import br.com.orbital.entities.Manobra;

import java.sql.SQLException;

public class ManobraBO {

    // Calcula a manobra de desvio para um satélite pelo noradId
    // Busca o evento de maior risco na tabela conjuncao e monta a resposta
    public Manobra calcularManobra(String noradId) throws SQLException, ClassNotFoundException {

        if (noradId == null || noradId.isBlank()) {
            throw new IllegalArgumentException("noradId é obrigatório");
        }

        ManobraDao manobraDao = new ManobraDao();
        Manobra manobra = manobraDao.calcularManobra(noradId);

        // se não encontrou nenhum evento para esse noradId, retorna manobra padrão
        // para não quebrar o front (que tem fallback mas é melhor retornar algo)
        if (manobra == null) {
            manobra = new Manobra(
                "Sem eventos registrados",
                0.0,
                "Nenhum objeto de risco identificado",
                "Não foram encontrados eventos de conjunção para este satélite na base do Space-Track."
            );
        }

        return manobra;
    }
}
