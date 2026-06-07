package br.com.orbital.bo;

import br.com.orbital.dao.ConjuncaoDao;
import br.com.orbital.dao.SateliteDao;
import br.com.orbital.entities.Conjuncao;
import br.com.orbital.entities.Satelite;

import java.sql.SQLException;
import java.util.List;

public class SateliteBO {

    SateliteDao sateliteDao;

    public Satelite cadastrar(Satelite satelite) throws SQLException, ClassNotFoundException {

        Long noradId = Long.parseLong(satelite.getNoradId());

        // 1. Valida se o NORAD ID existe na base do Space-Track
        ConjuncaoDao conjuncaoDao = new ConjuncaoDao();
        boolean existe = conjuncaoDao.noradIdExiste(noradId);

        if (!existe) {
            throw new IllegalArgumentException(
                "NORAD ID " + noradId + " não encontrado na base do Space-Track. " +
                "Verifique o ID e tente novamente."
            );
        }

        // 2. Busca o evento de maior risco para esse satélite
        ConjuncaoDao conjuncaoDao2 = new ConjuncaoDao();
        Conjuncao piorRisco = conjuncaoDao2.buscarPiorRiscoPorNorad(noradId);

        if (piorRisco != null) {
            // Preenche automaticamente com os dados do Space-Track
            satelite.setProbColisao(piorRisco.getPc() != null ? piorRisco.getPc() : 0.0);
            satelite.setStatusRisco(piorRisco.getStatusRisco());
            satelite.setProximaJanela(piorRisco.getTca());
        }

        // 3. Salva no banco
        sateliteDao = new SateliteDao();
        sateliteDao.inserir(satelite);

        return satelite;
    }

    // Busca por ID
    public Satelite buscarPorId(String id) throws SQLException, ClassNotFoundException {
        sateliteDao = new SateliteDao();
        return sateliteDao.selecionarPorId(id);
    }

    // Atualizar
    public void atualizarBo(Satelite satelite) throws SQLException, ClassNotFoundException {
        sateliteDao = new SateliteDao();
        sateliteDao.atualizar(satelite);
    }

    // Deletar
    public void deletarBo(String id) throws SQLException, ClassNotFoundException {
        sateliteDao = new SateliteDao();
        sateliteDao.deletar(id);
    }

    // Listar todos
    public List<Satelite> listarTodosBo() throws SQLException, ClassNotFoundException {
        sateliteDao = new SateliteDao();
        return sateliteDao.listarTodos();
    }

    // buscar por noradId — chamado pelo Resource no GET /satelites/:noradId
    public Satelite buscarPorNoradId(String noradId) throws SQLException, ClassNotFoundException {
        sateliteDao = new SateliteDao();
        return sateliteDao.buscarPorNoradId(noradId);
    }

    // atualizar parcial — chamado pelo Resource no PUT /satelites/:noradId
// recebe só os campos que o front envia: nome, cosparId, orbita, altitude
    public void atualizarParcialBo(String noradId, String nome, String cosparId,
                                   String orbita, double altitude)
            throws SQLException, ClassNotFoundException {
        sateliteDao = new SateliteDao();
        sateliteDao.atualizarParcial(noradId, nome, cosparId, orbita, altitude);
    }
}
