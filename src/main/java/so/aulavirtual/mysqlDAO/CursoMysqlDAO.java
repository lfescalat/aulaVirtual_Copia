/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.aulavirtual.mysqlDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.json.JSONArray;
import org.json.JSONObject;
import so.aulavirtual.dao.CursoDAO;
import so.aulavirtual.utilities.GeneralVariables;
import so.aulavirtual.utilities.ResponseHelper;

/**
 *
 * @author sistem17user
 */
public class CursoMysqlDAO implements CursoDAO {
	
	@Override
	public JSONObject listarCursos(JSONObject datos) throws Exception {
		JSONObject jreturn = new JSONObject();
		JSONArray lista = new JSONArray();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rSet = null;
		int cantidadRegistros = 0;
		String sql;
		try {
			con = MysqlDAOFactory.obtenerConexion(GeneralVariables.nameDB);
			sql = " select me.id, mcc.name, mc.fullname from mdl_user as mu "
				+ " inner join mdl_user_enrolments as me "
				+ " on mu.id = me.userid "
				+ " inner join mdl_enrol as mr "
				+ " on mr.id = me.enrolid "
				+ " inner join mdl_course as mc "
				+ " on mr.courseid = mc.id "
				+ " inner join mdl_course_categories mcc "
				+ " on mcc.id = mc.category "
				+ " where mu.id = ? "
				+ " order by mcc.name, mc.fullname "
				+ " limit ?, ? ";
			pst = con.prepareStatement(sql);
			pst.setInt(1, datos.getInt("codigoUsuario"));
			pst.setInt(2, datos.getInt("start"));
			pst.setInt(3, datos.getInt("length"));
			rSet = pst.executeQuery();
			int conta = datos.getInt("start") + 1;
			while (rSet.next()) {
				JSONObject obj = new JSONObject();
				obj.put("numeral", conta++);
				obj.put("codigoCurso", rSet.getInt(1));
				obj.put("nombreGrado", rSet.getString(2));
				obj.put("nombreCurso", rSet.getString(3));
				lista.put(obj);
			}
			sql = " select count(*) from mdl_user as mu "
				+ " inner join mdl_user_enrolments as me "
				+ " on mu.id = me.userid "
				+ " inner join mdl_enrol as mr "
				+ " on mr.id = me.enrolid "
				+ " inner join mdl_course as mc "
				+ " on mr.courseid = mc.id "
				+ " inner join  mdl_course_categories mcc "
				+ " on mcc.id = mc.category "
				+ " where mu.id = ? ";
			pst = con.prepareStatement(sql);
			pst.setInt(1, datos.getInt("codigoUsuario"));
			rSet = pst.executeQuery();
			rSet.next();
			cantidadRegistros = rSet.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rSet != null) {
					rSet.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		jreturn.put("data", lista);
		jreturn.put("recordsFiltered", cantidadRegistros);
		jreturn.put("recordsTotal", cantidadRegistros);
		jreturn.put("draw", datos.getInt("draw"));
		return jreturn;
	}
	
	@Override
	public JSONObject listarCurso(JSONObject datos) throws Exception {
		JSONObject jreturn;
		JSONArray lista = new JSONArray();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rSet = null;
		String sql;
		ResponseHelper response = new ResponseHelper();
		try {
			con = MysqlDAOFactory.obtenerConexion(GeneralVariables.nameDB);
			sql = " select id, fullname from mdl_course "
				+ " where category = ? "
				+ " order by fullname asc ";
			pst = con.prepareStatement(sql);
			pst.setInt(1, datos.getInt("parent"));
			rSet = pst.executeQuery();
			while (rSet.next()) {
				JSONObject obj = new JSONObject();
				obj.put("codigoCurso", rSet.getInt(1));
				obj.put("nombreCurso", rSet.getString(2));
				lista.put(obj);
			}
			if (lista.length() >= 1) {
				response.setStatus(true);
				response.setResults(lista);
				response.setMessage("Consulta correcta");
			} else {
				response.setStatus(false);
				response.setMessage("No hay registros");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(false);
			response.setMessage("Error --->" + e.getMessage());
		} finally {
			try {
				if (rSet != null) {
					rSet.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(false);
				response.setMessage("Error --->" + e.getMessage());
			}
		}
		jreturn = new JSONObject(response);
		return jreturn;
	}
	
	@Override
	public JSONObject eliminarCurso(JSONObject datos) throws Exception {
		JSONObject jreturn;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rSet = null;
		int rs;
		String sql;
		ResponseHelper response = new ResponseHelper();
		try {
			con = MysqlDAOFactory.obtenerConexion(GeneralVariables.nameDB);
			con.setAutoCommit(false);
			sql = " delete from mdl_user_enrolments "
				+ " where id = ? ";
			pst = con.prepareStatement(sql);
			pst.setInt(1, datos.getInt("codigoCurso"));
			rs = pst.executeUpdate();
			if (rs == 1) {
				con.commit();
				response.setStatus(true);
				response.setMessage("Curso eliminado correctamente");
			} else {
				response.setStatus(false);
				response.setMessage("Error al eliminar curso");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(false);
			response.setMessage("Error --->" + e.getMessage());
		} finally {
			try {
				if (rSet != null) {
					rSet.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(false);
				response.setMessage("Error --->" + e.getMessage());
			}
		}
		jreturn = new JSONObject(response);
		return jreturn;
	}
	
	@Override
	public JSONObject asignarCurso(JSONObject datos) throws Exception {
		JSONObject jreturn;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rSet = null;
		int rs;
		String sql;
		ResponseHelper response = new ResponseHelper();
		try {
			con = MysqlDAOFactory.obtenerConexion(GeneralVariables.nameDB);
			con.setAutoCommit(false);
			String condicion = datos.getInt("codigoCurso") == 0 ? " and category = ? " : " and me.courseid = ? ";
			sql = " insert ignore into mdl_user_enrolments (enrolid, userid, timeend) "
				+ " select me.id, ?, 0 from mdl_course as mc "
				+ " inner join mdl_enrol as me "
				+ " on me.courseid = mc.id "
				+ " where me.enrol = 'manual' "
				+ condicion;
			pst = con.prepareStatement(sql);
			pst.setInt(1, datos.getInt("codigoUsuario"));
			if (datos.getInt("codigoCurso") == 0) {
				pst.setInt(2, datos.getInt("codigoGrado"));
			} else {
				pst.setInt(2, datos.getInt("codigoCurso"));
			}
			rs = pst.executeUpdate();
			if (rs >= 1) {
				con.commit();
				response.setStatus(true);
				response.setMessage("Curso(s) correctamente registrado(s)");
			} else {
				response.setStatus(false);
				response.setMessage("Ocurrió un error al insertar curso(s)");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(false);
			response.setMessage("Error --->" + e.getMessage());
		} finally {
			try {
				if (rSet != null) {
					rSet.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(false);
				response.setMessage("Error --->" + e.getMessage());
			}
		}
		jreturn = new JSONObject(response);
		return jreturn;
	}
	
	@Override
	public JSONObject eliminarCursos(JSONObject datos) throws Exception {
		JSONObject jreturn;
		JSONArray lista;
		JSONObject data = new JSONObject();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rSet = null;
		int rs = 0;
		String sql;
		ResponseHelper response = new ResponseHelper();
		try {
			con = MysqlDAOFactory.obtenerConexion(GeneralVariables.nameDB);
			con.setAutoCommit(false);
			JSONArray deletedItems = datos.getJSONArray("lista");
			sql = " delete from mdl_user_enrolments "
				+ " where id = ? ";
			pst = con.prepareStatement(sql);
			for (int i = 0; i < deletedItems.length(); i++) {
				pst.setInt(1, deletedItems.getInt(i));
				pst.addBatch();
			}
			lista = new JSONArray(pst.executeBatch());
			if (lista.length() > 0) {
				con.commit();
				response.setStatus(true);
				response.setMessage("Curso eliminado correctamente");
			} else {
				response.setStatus(false);
				response.setMessage("Error al eliminar cursos");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(false);
			response.setMessage("Error --->" + e.getMessage());
		} finally {
			try {
				if (rSet != null) {
					rSet.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(false);
				response.setMessage("Error --->" + e.getMessage());
			}
		}
		jreturn = new JSONObject(response);
		return jreturn;
	}
}
