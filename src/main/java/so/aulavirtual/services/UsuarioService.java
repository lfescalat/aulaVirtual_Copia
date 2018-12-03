/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.aulavirtual.services;

import org.json.JSONArray;
import org.json.JSONObject;
import so.aulavirtual.dao.DAOFactory;
import so.aulavirtual.dao.UsuarioDAO;

/**
 *
 * @author Felipe Escala
 */
public class UsuarioService {

	DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	UsuarioDAO dao = factory.getUsuarioDAO();

	public JSONObject listarUsuarios(JSONObject datos) {
		JSONObject obj = null;
		try {
			obj = dao.listarUsuarios(datos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public JSONObject listarSede(JSONObject datos) {
		JSONObject obj = null;
		try {
			obj = dao.listarSede(datos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public JSONObject listarNivel(JSONObject datos) {
		JSONObject obj = null;
		try {
			obj = dao.listarNivel(datos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public JSONObject listarGrado(JSONObject datos) {
		JSONObject obj = null;
		try {
			obj = dao.listarGrado(datos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public JSONObject eliminarUsuario(JSONObject datos) {
		JSONObject obj = null;
		try {
			obj = dao.eliminarUsuario(datos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public JSONObject nuevoUsuario(JSONObject datos) {
		JSONObject obj = null;
		try {
			obj = dao.nuevoUsuario(datos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public JSONObject editarUsuarios(JSONObject datos) {
		JSONObject obj = null;
		try {
			obj = dao.editarUsuarios(datos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public JSONObject validarUsuario(JSONObject datos) {
		JSONObject obj = null;
		try {
			obj = dao.validarUsuario(datos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public JSONObject editarGradoMasivo(JSONObject datos) {
		JSONObject obj = null;
		try {
			obj = dao.editarGradoMasivo(datos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public JSONObject listarDetalle(JSONObject datos) {
		JSONObject obj = null;
		try {
			obj = dao.listarDetalle(datos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public JSONObject cambiarEstado(JSONObject datos) {
		JSONObject obj = null;
		try {
			obj = dao.cambiarEstado(datos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public JSONObject registroMasivo(JSONArray datos) {
		JSONObject obj = null;
		try {
			obj = dao.registroMasivo(datos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

}
