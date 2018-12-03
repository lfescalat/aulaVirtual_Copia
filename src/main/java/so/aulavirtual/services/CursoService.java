/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.aulavirtual.services;

import org.json.JSONObject;
import so.aulavirtual.dao.CursoDAO;
import so.aulavirtual.dao.DAOFactory;

/**
 *
 * @author sistem17user
 */
public class CursoService {

	DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	CursoDAO dao = factory.getCursoDAO();

	public JSONObject listarCursos(JSONObject datos) {
		JSONObject obj = null;
		try {
			obj = dao.listarCursos(datos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public JSONObject listarCurso(JSONObject datos) {
		JSONObject obj = null;
		try {
			obj = dao.listarCurso(datos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public JSONObject eliminarCurso(JSONObject datos) {
		JSONObject obj = null;
		try {
			obj = dao.eliminarCurso(datos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public JSONObject asignarCurso(JSONObject datos) {
		JSONObject obj = null;
		try {
			obj = dao.asignarCurso(datos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public JSONObject eliminarCursos(JSONObject datos) {
		JSONObject obj = null;
		try {
			obj = dao.eliminarCursos(datos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

}
