package br.com.orbital.bo;

import br.com.orbital.dao.AlertaDao;
import br.com.orbital.entities.Alerta;

import java.sql.SQLException;
import java.util.List;

public class AlertaBO {

    public List<Alerta> listarAlertas() throws SQLException, ClassNotFoundException {
        AlertaDao alertaDao = new AlertaDao();
        return alertaDao.listarAlertas();
    }
}
