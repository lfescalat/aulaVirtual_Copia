/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.aulavirtual.mysqlDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import so.aulavirtual.dao.UsuarioDAO;
import so.aulavirtual.utilities.GeneralMethods;
import so.aulavirtual.utilities.GeneralVariables;
import so.aulavirtual.utilities.ResponseHelper;

/**
 *
 * @author sistem17user
 */
public class UsuarioMysqlDAO implements UsuarioDAO {

	@Override
	public JSONObject listarUsuarios(JSONObject datos) throws Exception {
		JSONObject jreturn = new JSONObject();
		JSONArray lista = new JSONArray();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rSet = null;
		int cantidadRegistros = 0;
		String sql;
		try {
			String condicion = "";
			int s = 0;
			String gh = "";
			String gf = "";
			con = MysqlDAOFactory.obtenerConexion(GeneralVariables.nameDB);
			if (datos.has("codigoGrado")) {
				condicion += "INNER JOIN ( "
					+ "	SELECT mue.userid AS id "
					+ "	FROM mdl_user_enrolments AS mue "
					+ "	INNER JOIN mdl_enrol AS me ON me.id = mue.enrolid "
					+ "	INNER JOIN mdl_course AS mc ON mc.id = me.courseid "
					+ "	INNER JOIN mdl_course_categories AS mcc ON mcc.id = mc.category "
					+ "	WHERE mc.category = ? "
					+ "	AND mcc.parent != 0 "
					+ ") AS ts ON ts.id = mu.id "
					+ "WHERE	mu.username LIKE 'e%' ";
				s = 1;
				gh = " SELECT count(*) FROM ( ";
				gf = "	) AS t ";
			}
			if (datos.has("dniUsuario")) {
				condicion += " and mu.username like ? ";
			}
			if (datos.has("nombreUsuario")) {
				condicion += " and mu.firstname like ? ";
			}
			if (datos.has("apellidoUsuario")) {
				condicion += " and mu.lastname like ? ";
			}
			if (datos.has("estadoUsuario")) {
				condicion += " and mu.estado_registro = ? ";
			}
			if (datos.has("rolUsuario")) {
				condicion += " and uid.`data` = ? ";
			}
			String where = "";
			String group = "";
			if (s != 1) {
				where = " where 1 = 1 ";
			} else {
				group = "GROUP BY mu.id, uid.id ";
			}
			sql = " SELECT "
				+ "	mu.id, "
				+ "	IFNULL(mu.firstname, ''), "
				+ "	IFNULL(mu.lastname, ''), "
				+ "	IFNULL(mu.apePat, ''), "
				+ "	IFNULL(mu.apeMat, ''), "
				+ "	IFNULL(mu.auth, ''), "
				+ "	IFNULL(mu.username, ''), "
				+ "	IFNULL(IF(mu.username like 'e________@sistemahelicoidal.edu.pe' or mu.username like 'd________@sistemahelicoidal.edu.pe', SUBSTR(mu.username, 2, 8) , mu.idnumber), ''), "
				+ "	IFNULL(uid.`data`, ''), "
				+ "	IFNULL(mu.desSede, ''), "
				+ "	IFNULL(mu.desNiv, ''), "
				+ "	IFNULL(mu.desGra, ''), "
				+ "	IFNULL(mu.conTemp, ''), "
				+ "	IFNULL(mu.estado_registro, '') "
				+ " FROM "
				+ "	mdl_user mu "
				+ " left JOIN mdl_user_info_data uid  "
				+ " ON uid.userid = mu.id "
				+ " and  uid.fieldid = 6 "
				+ where
				+ condicion
				+ group
				+ " ORDER BY mu.id DESC "
				+ " LIMIT " + datos.getInt("start") + ", " + datos.getInt("length");
			pst = con.prepareStatement(sql);
			int c = 1;
			if (datos.has("codigoGrado")) {
				pst.setInt(c++, datos.getInt("codigoGrado"));
			}
			if (datos.has("dniUsuario")) {
				pst.setString(c++, "%" + datos.getInt("dniUsuario") + "%");
			}
			if (datos.has("nombreUsuario")) {
				pst.setString(c++, "%" + datos.getString("nombreUsuario") + "%");
			}
			if (datos.has("apellidoUsuario")) {
				pst.setString(c++, "%" + datos.getString("apellidoUsuario") + "%");
			}
			if (datos.has("estadoUsuario")) {
				pst.setInt(c++, datos.getInt("estadoUsuario"));
			}
			if (datos.has("rolUsuario")) {
				pst.setString(c++, datos.getString("rolUsuario"));
			}
			rSet = pst.executeQuery();
			int conta = datos.getInt("start") + 1;
			while (rSet.next()) {
				JSONObject obj = new JSONObject();
				c = 1;
				obj.put("numeral", conta);
				obj.put("codigoUsuario", rSet.getInt(c++));
				obj.put("nombreUsuario", rSet.getString(c++));
				obj.put("apellidosUsuario", rSet.getString(c++));
				obj.put("apepatUsuario", rSet.getString(c++));
				obj.put("apematUsuario", rSet.getString(c++));
				obj.put("tipoUsuario", rSet.getString(c++));
				obj.put("correoUsuario", rSet.getString(c++));
				obj.put("dniUsuario", rSet.getString(c++));
				obj.put("rolUsuario", rSet.getString(c++));
				obj.put("sedeUsuario", rSet.getString(c++));
				obj.put("nivelUsuario", rSet.getString(c++));
				obj.put("gradoUsuario", rSet.getString(c++));
				obj.put("claveUsuario", rSet.getString(c++));
				obj.put("estadoUsuario", rSet.getBigDecimal(c) == null ? JSONObject.NULL : rSet.getBigDecimal(c++));
				lista.put(obj);
				conta++;
			}
			sql = gh
				+ "SELECT "
				+ "	count(*) "
				+ " FROM "
				+ "	mdl_user mu "
				+ " left JOIN mdl_user_info_data uid  "
				+ " ON uid.userid = mu.id "
				+ " and  uid.fieldid = 6 "
				+ where
				+ condicion
				+ group
				+ gf;
			pst = con.prepareStatement(sql);
			c = 1;
			if (datos.has("codigoGrado")) {
				pst.setInt(c++, datos.getInt("codigoGrado"));
			}
			if (datos.has("dniUsuario")) {
				pst.setString(c++, "%" + datos.getInt("dniUsuario") + "%");
			}
			if (datos.has("nombreUsuario")) {
				pst.setString(c++, "%" + datos.getString("nombreUsuario") + "%");
			}
			if (datos.has("apellidoUsuario")) {
				pst.setString(c++, "%" + datos.getString("apellidoUsuario") + "%");
			}
			if (datos.has("estadoUsuario")) {
				pst.setInt(c++, datos.getInt("estadoUsuario"));
			}
			if (datos.has("rolUsuario")) {
				pst.setString(c++, datos.getString("rolUsuario"));
			}
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
	public JSONObject listarSede(JSONObject datos) throws Exception {
		JSONObject jreturn;
		JSONArray lista = new JSONArray();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rSet = null;
		String sql;
		ResponseHelper response = new ResponseHelper();
		try {
			con = MysqlDAOFactory.obtenerConexion(GeneralVariables.nameDB);
			sql = " SELECT "
				+ "	u.cod_usu, "
				+ "	u.sede "
				+ "FROM "
				+ "	usuario AS u "
				+ "WHERE "
				+ "	est_usu = 1 "
				+ " order by u.sede asc ";
			pst = con.prepareStatement(sql);
			rSet = pst.executeQuery();
			while (rSet.next()) {
				JSONObject obj = new JSONObject();
				obj.put("codigoSede", rSet.getInt(1));
				obj.put("nombreSede", rSet.getString(2));
				lista.put(obj);
			}
			if (lista.length() >= 1) {
				response.setStatus(true);
				response.setResults(lista);
			} else {
				response.setStatus(false);
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
	public JSONObject listarNivel(JSONObject datos) throws Exception {
		JSONObject jreturn;
		JSONArray lista = new JSONArray();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rSet = null;
		String sql;
		ResponseHelper response = new ResponseHelper();
		try {
			con = MysqlDAOFactory.obtenerConexion(GeneralVariables.nameDB);
			sql = " SELECT "
				+ "	mdc.id, "
				+ "	mdc.`name` "
				+ " FROM "
				+ "	mdl_course_categories AS mdc "
				+ " WHERE "
				+ "	mdc.parent = 0 "
				+ " AND mdc.id NOT IN (2, 4) "
				+ " and mdc.visible = 1 ";
			pst = con.prepareStatement(sql);
			rSet = pst.executeQuery();
			while (rSet.next()) {
				JSONObject obj = new JSONObject();
				obj.put("codigoNivel", rSet.getInt(1));
				obj.put("nombreNivel", rSet.getString(2));
				lista.put(obj);
			}
			if (lista.length() >= 1) {
				response.setStatus(true);
				response.setResults(lista);
			} else {
				response.setStatus(false);
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
	public JSONObject listarGrado(JSONObject datos) throws Exception {
		JSONObject jreturn;
		JSONArray lista = new JSONArray();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rSet = null;
		String sql;
		ResponseHelper response = new ResponseHelper();
		try {
			con = MysqlDAOFactory.obtenerConexion(GeneralVariables.nameDB);
			sql = " SELECT "
				+ "	mdc.id, "
				+ "	mdc. NAME "
				+ " FROM "
				+ "	mdl_course_categories AS mdc "
				+ " WHERE "
				+ "	mdc.parent = ? "
				+ " and mdc.visible = 1 ";
			pst = con.prepareStatement(sql);
			pst.setInt(1, datos.getInt("parent"));
			rSet = pst.executeQuery();
			while (rSet.next()) {
				JSONObject obj = new JSONObject();
				obj.put("codigoGrado", rSet.getInt(1));
				obj.put("nombreGrado", rSet.getString(2));
				lista.put(obj);
			}
			if (lista.length() >= 1) {
				response.setStatus(true);
				response.setResults(lista);
			} else {
				response.setStatus(false);
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
	public JSONObject eliminarUsuario(JSONObject datos) throws Exception {
		JSONObject jreturn;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rSet = null;
		int rs = 0;
		String sql;
		ResponseHelper response = new ResponseHelper();
		try {
			con = MysqlDAOFactory.obtenerConexion(GeneralVariables.nameDB);
			con.setAutoCommit(false);
			sql = " delete from mdl_user_enrolments "
				+ " where userid = ? ";
			pst = con.prepareStatement(sql);
			pst.setInt(1, datos.getInt("codigoUsuario"));
			rs = pst.executeUpdate();
			if (rs >= 0) {
				sql = " delete from mdl_user_info_data "
					+ " where userid = ? ";
				pst = con.prepareStatement(sql);
				pst.setInt(1, datos.getInt("codigoUsuario"));
				rs = pst.executeUpdate();
				if (rs >= 0) {
					sql = " delete from mdl_user "
						+ " where id = ? ";
					pst = con.prepareStatement(sql);
					pst.setInt(1, datos.getInt("codigoUsuario"));
					rs = pst.executeUpdate();
					if (rs >= 0) {
						con.commit();
						response.setStatus(true);
						response.setMessage("Usuario eliminado correctamente");
					} else {
						response.setStatus(false);
						response.setMessage("Ocurrio un error al eliminar Usuario");
					}
				} else {
					response.setStatus(false);
					response.setMessage("Ocurrio un error al eliminar Usuario");
				}
			} else {
				response.setStatus(false);
				response.setMessage("Ocurrio un error al eliminar Usuario");
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
	public JSONObject nuevoUsuario(JSONObject datos) throws Exception {
		JSONObject jreturn;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rSet = null;
		int rs = 0;
		String sql;
		ResponseHelper response = new ResponseHelper();
		try {
			String param;
			String data;
			int tipoUsuario;
			switch (datos.getString("rolUsuario")) {
				case "DOCENTE":
					param = "";
					data = "";
					tipoUsuario = 0;
					break;
				case "ESTUDIANTE":
					param = " desNiv,  desGra,  desSede, ";
					data = " UPPER(?), UPPER(?), UPPER(?),	 ";
					tipoUsuario = 1;
					break;
				default:
					param = " desSede, ";
					data = " ?, ";
					tipoUsuario = 2;
			}
			con = MysqlDAOFactory.obtenerConexion(GeneralVariables.nameDB);
			con.setAutoCommit(false);
			sql = " insert into mdl_user "
				+ "  ( "
				+ "  auth, "
				+ "  confirmed, "
				+ "  mnethostid, "
				+ "  username, "
				+ "  password, "
				+ "  idnumber, "
				+ "  firstname, "
				+ "  lastname, "
				+ "  email, "
				+ "  city, "
				+ "  country, "
				+ "  lang, "
				+ "  calendartype, "
				+ "  timezone, "
				+ "  apePat, "
				+ "  apeMat, "
				+ param
				+ "  conTemp"
				+ "  )"
				+ "  values ( LOWER(?), ?, ?, ?, ?, ?, UPPER(?), UPPER(?), ?, ?,"
				+ "  ?, ?, ?, ?, UPPER(?), UPPER(?), " + data + " ? ) ";
			pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int c = 1;
			pst.setString(c++, datos.getString("tipoUsuario"));
			pst.setInt(c++, 1);
			pst.setInt(c++, 1);
			pst.setString(c++, datos.getString("correoUsuario"));
			pst.setString(c++, "not cached");
			pst.setString(c++, datos.getString("dniUsuario"));
			pst.setString(c++, datos.getString("nombreUsuario"));
			pst.setString(c++, datos.getString("apepatUsuario") + " " + datos.getString("apematUsuario"));
			pst.setString(c++, datos.getString("correoUsuario"));
			pst.setString(c++, "Lima");
			pst.setString(c++, "PE");
			pst.setString(c++, "en");
			pst.setString(c++, "gregorian");
			pst.setString(c++, "America/Lima");
			pst.setString(c++, datos.getString("apepatUsuario"));
			pst.setString(c++, datos.getString("apematUsuario"));
			if (tipoUsuario == 1) {
				pst.setString(c++, datos.getString("nivelUsuario"));
				pst.setString(c++, datos.getString("gradoUsuario"));
				pst.setString(c++, datos.getString("sedeUsuario"));
			} else if (tipoUsuario == 2) {
				pst.setString(c++, datos.getString("sedeUsuario"));
			}
			pst.setString(c++, datos.getString("claveUsuario").equals("") ? null : datos.getString("claveUsuario"));
			rs = pst.executeUpdate();
			if (rs == 1) {
				rSet = pst.getGeneratedKeys();
				rSet.next();
				int codigoUsuario = rSet.getInt(1);
				sql = " insert into mdl_user_info_data(userid, fieldid, data) "
					+ " values ( ?, 6, ? ) ";
				pst = con.prepareStatement(sql);
				pst.setInt(1, codigoUsuario);
				pst.setString(2, datos.getString("rolUsuario"));
				rs = pst.executeUpdate();
				if (rs == 1) {
					if (datos.getString("rolUsuario").equals("ESTUDIANTE")) {
						sql = " insert into mdl_user_enrolments (enrolid, userid, timeend) "
							+ " select me.id, ?, 0 from mdl_course as mc "
							+ " inner join mdl_enrol as me "
							+ " on me.courseid = mc.id "
							+ " where me.enrol = 'manual' "
							+ " and category = ? ";
						pst = con.prepareStatement(sql);
						pst.setInt(1, codigoUsuario);
						pst.setInt(2, datos.getInt("codigoGrado"));
						rs = pst.executeUpdate();
						if (rs >= 1) {
							con.commit();
							response.setStatus(true);
							response.setMessage("Usuario correctamente registrado");
						} else {
							response.setStatus(false);
							response.setMessage("Ocurrió un error al registrar cursos");
						}
					} else {
						con.commit();
						response.setStatus(true);
						response.setMessage("Usuario correctamente registrado");
					}
				} else {
					response.setStatus(false);
					response.setMessage("Ocurrió un error al registrar rol");
				}
			} else {
				response.setStatus(false);
				response.setMessage("Ocurrió un error al registrar usuario");
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
	public JSONObject editarUsuarios(JSONObject datos) throws Exception {
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
			sql = " update mdl_user  "
				+ " set  "
				+ " auth = ?, "
				+ " username = ?, "
				+ " idnumber = ?, "
				+ " firstname = ?, "
				+ " lastname = ?, "
				+ " email = ?, "
				+ " apePat = ?, "
				+ " apeMat = ?, "
				+ " desNiv = ?, "
				+ " desGra = ?, "
				+ " desSede = ?,  "
				+ " conTemp = ? "
				+ " where id = ? ";
			pst = con.prepareStatement(sql);
			int c = 1;
			pst.setString(c++, datos.getString("tipoUsuario"));
			pst.setString(c++, datos.getString("correoUsuario"));
			pst.setString(c++, datos.getString("dniUsuario"));
			pst.setString(c++, datos.getString("nombreUsuario"));
			pst.setString(c++, datos.getString("apepatUsuario") + " " + datos.getString("apematUsuario"));
			pst.setString(c++, datos.getString("correoUsuario"));
			pst.setString(c++, datos.getString("apepatUsuario"));
			pst.setString(c++, datos.getString("apematUsuario"));
			pst.setString(c++, datos.getString("nivelUsuario"));
			pst.setString(c++, datos.getString("gradoUsuario"));
			pst.setString(c++, datos.getString("sedeUsuario"));
			pst.setString(c++, datos.getString("claveUsuario").equals("") ? null : datos.getString("claveUsuario"));
			pst.setInt(c++, datos.getInt("codigoUsuario"));
			rs = pst.executeUpdate();
			if (rs == 1) {
				sql = " select count(*) from mdl_user_info_data"
					+ " where  userid = ? and fieldid = 6 ";
				pst = con.prepareStatement(sql);
				pst.setInt(1, datos.getInt("codigoUsuario"));
				rSet = pst.executeQuery();
				if (rSet.next()) {
					int cantidad = rSet.getInt(1);
					if (cantidad <= 0) {
						sql = " insert into mdl_user_info_data(data, fieldid, userid) "
							+ " values ( ?, 6, ? ) ";
					} else {
						sql = " update mdl_user_info_data "
							+ " set data = ? "
							+ " where userid = ? ";
					}
				}
				pst = con.prepareStatement(sql);
				pst.setString(1, datos.getString("rolUsuario"));
				pst.setInt(2, datos.getInt("codigoUsuario"));
				rs = pst.executeUpdate();
				if (rs == 1) {
					if (datos.getString("rolUsuario").equals("ESTUDIANTE")) {
						sql = " insert into mdl_user_enrolments (enrolid, userid, timeend) "
							+ " select me.id, ?, 0 from mdl_course as mc "
							+ " inner join mdl_enrol as me "
							+ " on me.courseid = mc.id "
							+ " where me.enrol = 'manual' "
							+ " and category = ? ";
						pst = con.prepareStatement(sql);
						pst.setInt(1, datos.getInt("codigoUsuario"));
						pst.setInt(2, datos.getInt("codigoGrado"));
						rs = pst.executeUpdate();
						if (rs >= 1) {
							con.commit();
							response.setStatus(true);
							response.setMessage("Usuario correctamente modificado");
						} else {
							response.setStatus(false);
							response.setMessage("Ocurrió un error al registrar cursos");
						}
					} else {
						con.commit();
						response.setStatus(true);
						response.setMessage("Usuario correctamente modificado");
					}
				} else {
					response.setStatus(false);
					response.setMessage("Error al modificar Rol");
				}
			} else {
				response.setStatus(false);
				response.setMessage("Error al modificar Usuario");
			}
		} catch (SQLException | JSONException e) {
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
			} catch (SQLException e) {
				e.printStackTrace();
				response.setStatus(false);
				response.setMessage("Error --->" + e.getMessage());
			}
		}
		jreturn = new JSONObject(response);
		return jreturn;
	}

	@Override
	public JSONObject validarUsuario(JSONObject datos) throws Exception {
		JSONObject jreturn;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rSet = null;
		String sql;
		ResponseHelper response = new ResponseHelper();
		try {
			con = MysqlDAOFactory.obtenerConexion(GeneralVariables.nameDB);
			String edit = datos.has("codigoUsuario") ? " and id != ?  " : " ";
			sql = " select count(*) from mdl_user "
				+ " where username = ? "
				+ edit;
			pst = con.prepareStatement(sql);
			pst.setString(1, datos.getString("correoUsuario"));
			if (datos.has("codigoUsuario")) {
				pst.setInt(2, datos.getInt("codigoUsuario"));
			}
			rSet = pst.executeQuery();
			rSet.next();
			int cant = rSet.getInt(1);
			if (cant != 0) {
				response.setStatus(false);
				response.setMessage("Este usuario ya existe");
			} else {
				response.setStatus(true);
				response.setMessage("Correcto");
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
	public JSONObject validarCursos(JSONObject datos) throws Exception {
		JSONObject jreturn;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rSet = null;
		int rs;
		String sql;
		ResponseHelper response = new ResponseHelper();
		try {
			con = MysqlDAOFactory.obtenerConexion(GeneralVariables.nameDB);
			sql = " select count(*) from mdl_user_enrolments "
				+ " where userid = ? ";
			pst = con.prepareStatement(sql);
			pst.setInt(1, datos.getInt("codigoUsuario"));
			rSet = pst.executeQuery();
			rSet.next();
			rs = rSet.getInt(1);
			if (rs > 0) {
				response.setStatus(true);
				response.setMessage("Tiene cursos asignados");
			} else {
				response.setStatus(false);
				response.setMessage("No tiene cursos asignados");
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
	public JSONObject editarGradoMasivo(JSONObject datos) throws Exception {
		JSONObject jreturn;
		JSONArray listaCorrecto = new JSONArray();
		JSONArray listaIncorrecto = new JSONArray();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rSet = null;
		int rs;
		String sql;
		ResponseHelper response = new ResponseHelper();
		try {
			con = MysqlDAOFactory.obtenerConexion(GeneralVariables.nameDB);
			JSONArray estudiantes = datos.getJSONArray("listaEstudiantes");
			for (int i = 0; i < estudiantes.length(); i++) {
				con.setAutoCommit(false);
				sql = " DELETE FROM mdl_user_enrolments "
					+ " WHERE userid = ? "
					+ " AND enrolid IN ( "
					+ "				SELECT me.id "
					+ "				FROM mdl_course AS mc "
					+ "				INNER JOIN mdl_enrol AS me ON me.courseid = mc.id "
					+ "				WHERE me.enrol = 'manual' "
					+ "				AND category = ? "
					+ "	) ";
				pst = con.prepareStatement(sql);
				pst.setInt(1, estudiantes.getInt(i));
				pst.setInt(2, datos.getInt("codigoGradoInicial"));
				rs = pst.executeUpdate();
				if (rs < 1 || rs == -1) {
					listaIncorrecto.put(estudiantes.get(i));
					con.rollback();
				} else {
					sql = " insert into mdl_user_enrolments (enrolid, userid, timeend) "
						+ " select me.id, ?, 0 from mdl_course as mc "
						+ " inner join mdl_enrol as me "
						+ " on me.courseid = mc.id "
						+ " where me.enrol = 'manual' "
						+ " and category = ? ";
					pst = con.prepareStatement(sql);
					pst.setInt(1, estudiantes.getInt(i));
					pst.setInt(2, datos.getInt("codigoGradoFinal"));
					rs = pst.executeUpdate();
					if (rs < 1 || rs == -1) {
						listaIncorrecto.put(estudiantes.get(i));
						con.rollback();
					} else {
						con.commit();
						listaCorrecto.put(estudiantes.get(i));
					}
				}
			}
			if (listaCorrecto.length() > 0) {
				response.setData(new JSONObject()
					.put("incorrectos", listaIncorrecto)
					.put("correctos", listaCorrecto));
				response.setStatus(true);
			} else {
				response.setMessage("No se pudo procesar la operación");
				response.setStatus(false);
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
	public JSONObject listarDetalle(JSONObject datos) throws Exception {
		JSONObject jreturn = new JSONObject();
		JSONArray lista = new JSONArray();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rSet = null;
		int cantidadRegistros = 0;
		String sql;
		ResponseHelper response = new ResponseHelper();
		try {
			con = MysqlDAOFactory.obtenerConexion(GeneralVariables.nameDB);
			sql = " select firstname, lastname, SUBSTR(username, 2, 8) from mdl_user "
				+ " where id in (" + datos.getString("codigosUsuarios") + ") "
				+ " LIMIT " + datos.getInt("start") + ", " + datos.getInt("length");
			pst = con.prepareStatement(sql);
			rSet = pst.executeQuery();
			int conta = datos.getInt("start") + 1;
			while (rSet.next()) {
				JSONObject obj = new JSONObject();
				obj.put("numeral", conta);
				obj.put("nombreUsuario", rSet.getString(1));
				obj.put("apellidoUsuario", rSet.getString(2));
				obj.put("correoUsuario", rSet.getString(3));
				lista.put(obj);
				conta++;
			}
			sql = " select count(*) from mdl_user "
				+ " where id in (" + datos.getString("codigosUsuarios") + ") ";
			pst = con.prepareStatement(sql);
			rSet = pst.executeQuery();
			rSet.next();
			cantidadRegistros = rSet.getInt(1);
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
		jreturn.put("data", lista);
		jreturn.put("recordsFiltered", cantidadRegistros);
		jreturn.put("recordsTotal", cantidadRegistros);
		jreturn.put("draw", datos.getInt("draw"));
		return jreturn;
	}

	@Override
	public JSONObject cambiarEstado(JSONObject datos) throws Exception {
		JSONObject jreturn;
		JSONArray lista = new JSONArray();
		JSONObject data = new JSONObject();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rSet = null;
		int rs = 0;
		String sql = "";
		ResponseHelper response = new ResponseHelper();
		try {
			con = MysqlDAOFactory.obtenerConexion(GeneralVariables.nameDB);
			con.setAutoCommit(false);
			sql = " update mdl_user set suspended = 1,"
				+ "	estado_registro  =  "
				+ "	( "
				+ "		select "
				+ "			t.*  "
				+ "		from "
				+ "		( "
				+ "			select "
				+ "				if(estado_registro = 1, 0, 1) "
				+ "			from mdl_user  "
				+ "		where id = ? "
				+ "		) as t "
				+ "	) "
				+ " where id = ? ";
			pst = con.prepareStatement(sql);
			pst.setInt(1, datos.getInt("codigoUsuario"));
			pst.setInt(2, datos.getInt("codigoUsuario"));
			rs = pst.executeUpdate();
			if (rs == 1) {
				con.commit();
				response.setMessage("Estado correctamente actualizado");
				response.setStatus(true);
			} else {
				response.setMessage("Ocurrió un error al actualizar cursos");
				response.setStatus(false);
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
	public JSONObject registroMasivo(JSONArray datos) throws Exception {
		JSONObject jreturn;
		JSONArray correct = new JSONArray();
		JSONArray error = new JSONArray();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rSet = null;
		int conta = 1;
		int rs = 0;
		String sql = "";
		ResponseHelper response = new ResponseHelper();
		try {
			con = MysqlDAOFactory.obtenerConexion(GeneralVariables.nameDB);
			con.setAutoCommit(false);
			for (int i = 0; i < datos.length(); i++) {
				JSONObject obj = datos.getJSONObject(i);
				sql = "SELECT IFNULL( "
					+ "	( "
					+ "	SELECT id "
					+ "	FROM mdl_user "
					+ "	WHERE	username = ? "
					+ "	), -1 "
					+ ")";
				pst = con.prepareStatement(sql);
				pst.setString(1, obj.getString("username"));
				rSet = pst.executeQuery();
				int userId;
				if (rSet.next()) {
					JSONObject data;
					if (rSet.getInt(1) != -1) {
						userId = rSet.getInt(1);
						sql = " update mdl_user "
							+ " set firstname = ?,"
							+ " lastname = ?,"
							+ " email = ?, "
							+ " idnumber = ?, "
							+ " apePat = ?, "
							+ " apeMat = ? "
							+ " where id = ? ";
						pst = con.prepareStatement(sql);
						conta = 1;
						pst.setObject(conta++, GeneralMethods.isNullOrEmpty(obj.getString("firstname")) ? null : obj.getString("firstname"));
						pst.setObject(conta++, obj.getString("apePat") + " " + obj.getString("apeMat"));
						pst.setObject(conta++, GeneralMethods.isNullOrEmpty(obj.getString("email")) ? null : obj.getString("email"));
						pst.setObject(conta++, GeneralMethods.isNullOrEmpty(obj.getString("idnumber")) ? null : obj.getString("idnumber"));
						pst.setObject(conta++, GeneralMethods.isNullOrEmpty(obj.getString("apePat")) ? null : obj.getString("apePat"));
						pst.setObject(conta++, GeneralMethods.isNullOrEmpty(obj.getString("apeMat")) ? null : obj.getString("apeMat"));
						//pst.setObject(conta++, apets.length > 2 ? String.join(" ", Arrays.copyOfRange(apets, 1, apets.length)) : apets[1]);
						pst.setObject(conta++, userId);
						rs = pst.executeUpdate();
						if (rs == 1) {
							data = registrarCursosMasivo(obj.put("userId", userId), con, pst, rSet);
							if (data.getBoolean("status")) {
								correct.put(data.getJSONObject("data")
									.put("message", "Correcto")
								);
								con.commit();
							} else {
								error.put(data.getJSONObject("data")
									.put("message", data.has("message") ? data.getString("message") : "No se completo la carga de cursos")
								);
							}
						} else {
							error.put(obj.put("message", "Se ingresaron datos idénticos"));
						}
					} else {
						sql = " insert into mdl_user "
							+ "  ( "
							+ "  auth, "
							+ "  confirmed, "
							+ "  mnethostid, "
							+ "  username, "
							+ "  password, "
							+ "  idnumber, "
							+ "  firstname, "
							+ "  lastname, "
							+ "  email, "
							+ "  city, "
							+ "  country, "
							+ "  lang, "
							+ "  calendartype, "
							+ "  timezone, "
							+ "  apePat, "
							+ "  apeMat "
							+ "  )"
							+ "  values ( LOWER('oidc'), ?, ?, ?, ?, ?, UPPER(?), UPPER(?), ?, ?,"
							+ "  ?, ?, ?, ?, UPPER(?), UPPER(?)) ";
						pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
						int c = 1;
						pst.setInt(c++, 1);
						pst.setInt(c++, 1);
						pst.setString(c++, GeneralMethods.isNullOrEmpty(obj.getString("username")) ? null : obj.getString("username"));
						pst.setString(c++, "not cached");
						pst.setString(c++, GeneralMethods.isNullOrEmpty(obj.getString("idnumber")) ? null : obj.getString("idnumber"));
						pst.setString(c++, GeneralMethods.isNullOrEmpty(obj.getString("firstname")) ? null : obj.getString("firstname"));
						pst.setString(c++, obj.getString("apePat") + " " + obj.getString("apeMat"));
						pst.setString(c++, GeneralMethods.isNullOrEmpty(obj.getString("email")) ? null : obj.getString("email"));
						pst.setString(c++, "Lima");
						pst.setString(c++, "PE");
						pst.setString(c++, "en");
						pst.setString(c++, "gregorian");
						pst.setString(c++, "America/Lima");
						pst.setString(c++, GeneralMethods.isNullOrEmpty(obj.getString("apePat")) ? null : obj.getString("apePat"));
						pst.setString(c++, GeneralMethods.isNullOrEmpty(obj.getString("apeMat")) ? null : obj.getString("apeMat"));
						rs = pst.executeUpdate();
						if (rs == 1) {
							rSet = pst.getGeneratedKeys();
							rSet.next();
							userId = rSet.getInt(1);
							data = registrarCursosMasivo(obj.put("userId", userId), con, pst, rSet);
							if (data.getBoolean("status")) {
								correct.put(data.getJSONObject("data")
									.put("message", "Correcto")
								);
								con.commit();
							} else {
								System.out.println("DATA" + data);
								error.put(data.getJSONObject("data")
									.put("message", data.has("message") ? data.getString("message") : "No se completo la carga de cursos")
								);
							}
						} else {
							error.put(obj.put("message", "No se pudo crea el usuario"));
						}
					}
				} else {
					error.put(obj);
				}
			}
			response.setData(new JSONObject()
				.put("corrects", correct)
				.put("errors", error)
			);
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

	private JSONObject registrarCursosMasivo(JSONObject datos, Connection con, PreparedStatement pst, ResultSet rSet) {
		JSONObject jreturn;
		int rs = 1;
		String sql;
		ResponseHelper response = new ResponseHelper();
		try {
			con = MysqlDAOFactory.obtenerConexion(GeneralVariables.nameDB);
			if (datos.has("rol")) {
				sql = " select count(*) from mdl_user_info_data"
					+ " where  userid = ? and fieldid = 6 ";
				pst = con.prepareStatement(sql);
				pst.setInt(1, datos.getInt("userId"));
				rSet = pst.executeQuery();
				if (rSet.next()) {
					int cantidad = rSet.getInt(1);
					if (cantidad <= 0) {
						sql = " insert into mdl_user_info_data(data, fieldid, userid) "
							+ " values ( ?, 6, ? ) ";
					} else {
						sql = " update mdl_user_info_data "
							+ " set data = ? "
							+ " where userid = ? ";
					}
					pst = con.prepareStatement(sql);
					pst.setString(1, datos.getString("rol"));
					pst.setInt(2, datos.getInt("userId"));
					rs = pst.executeUpdate();
				}
			}
			if (rs == 1) {
				if (datos.has("reset")) {
					sql = " DELETE FROM mdl_user_enrolments "
						+ " WHERE userid = ? ";
					pst = con.prepareStatement(sql);
					pst.setInt(1, datos.getInt("userId"));
					pst.executeUpdate();
				}
				if (datos.has("courses")) {
					JSONArray jList = datos.getJSONArray("courses");
					// Convertirt JsonArray en cadena formato --> ("___" , "___", "___")
					List<String> list = (List<String>) (Object) jList.toList();
					String sList = list.stream().map(cadena -> "'" + cadena + "'").collect(Collectors.joining(","));
					sql = " insert ignore into mdl_user_enrolments (enrolid, userid, timeend) "
						+ " select me.id, ?, 0 from mdl_course as mc "
						+ " inner join mdl_enrol as me "
						+ " on mc.id = me.courseid "
						+ " and me.enrol = 'manual' "
						+ " where mc.shortname in (" + sList + ") ";
					pst = con.prepareStatement(sql);
					pst.setInt(1, datos.getInt("userId"));
					pst.executeUpdate();
					response.setStatus(true);
					response.setData(datos);
				} else {
					response.setStatus(true);
					response.setData(datos);
				}
			} else {
				response.setStatus(false);
				response.setMessage("No se pudo crear/actualizar el usuario[rol]");
				response.setData(datos);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(false);
			response.setData(datos);
			response.setMessage("Error --->" + e.getMessage());
		}
		jreturn = new JSONObject(response);
		return jreturn;
	}
}
