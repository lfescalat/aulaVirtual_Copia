/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.aulavirtual.utilities;

/**
 *
 * @author Maro & Blarru
 */
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import org.json.JSONArray;
import org.json.JSONObject;

public final class DAOHelper {

	//********************************************** STATEMENT **************************************************************
	public static JSONArray queryPS(Connection connection, String query, JSONArray... params) {
		return queryPS(connection, query, false, params);
	}

	public static JSONArray queryPS(Connection connection, String query, boolean mode, JSONArray... params) {
		JSONArray list = new JSONArray();
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			if (params != null && params.length > 0) {
				JSONArray _params = params[0];
				int index = 1;
				for (Object p : _params) {
					setPreparedStatement(ps, index++, p);
				}
			}
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rm = rs.getMetaData();
			int numCols = rm.getColumnCount();
			while (rs.next()) {
				JSONObject obj = new JSONObject();
				for (int i = 1; i <= numCols; i++) {
					setJSONObject(rs, rm, i, obj, mode);
				}
				list.put(obj);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}

	public static JSONObject executePS(Connection connection, String query, JSONArray... params) {
		JSONObject obj = new JSONObject();
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			if (params != null && params.length > 0) {
				JSONArray _params = params[0];
				int index = 1;
				for (Object parametro : _params) {
					setPreparedStatement(ps, index++, parametro);
				}
			}
			int filas = ps.executeUpdate();
			obj.put("status", filas > 0);
			obj.put("message", "OK");
			obj.put("data", filas);
		} catch (Exception ex) {
			obj.put("status", false);
			obj.put("message", ex);
			obj.put("data", JSONObject.NULL);
		}
		return obj;
	}

//*************************************** PROCEDIMIENTOS ALMACENADOS ****************************************************
	public static JSONArray queryProcedure(Connection cn, String query, JSONArray... parameters) throws Exception {
		return queryProcedure(cn, query, false, parameters);
	}

	public static JSONArray queryProcedure(Connection cn, String query, boolean mode, JSONArray... parameters) throws Exception {
		JSONArray result = new JSONArray();
		JSONObject outputParamTypes = new JSONObject();
		JSONArray params = null;
		try {
			CallableStatement cs = cn.prepareCall(query);
			if (parameters != null && parameters.length > 0) {
				params = parameters[0];
				int index = 1;
				for (Object parameter : params) {
					if (parameter instanceof Class) {
						registerOutputParameter(cs, index++, parameter, outputParamTypes);
					} else {
						setPreparedStatement(cs, index++, parameter);
					}
				}
			}
			boolean formFirstResult = cs.execute();
			if (formFirstResult) {
				final ResultSet rs = cs.getResultSet();
				ResultSetMetaData rm = rs.getMetaData();
				int columnCount = rm.getColumnCount();
				while (rs.next()) {
					JSONObject obj = new JSONObject();
					for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
						setJSONObject(rs, rm, columnIndex, obj, mode);
					}
					result.put(obj);
				}
			} else {
				int rowsAffected = cs.getUpdateCount();
				JSONObject obj = new JSONObject();
				obj.put("rowsAffected", rowsAffected);
				result.put(obj);
			}

			if (outputParamTypes.length() > 0) {
				clearJSONArray(params);
			}
			for (String key : outputParamTypes.keySet()) {
				int indexOutputParams = Integer.parseInt(key);
				int type = outputParamTypes.getInt(key);
				getOutputParameter(cs, indexOutputParams, type, params);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	//************************************************************************************************************************
	public static JSONObject executeCS(Connection connection, String query, JSONArray... params) {
		JSONObject obj = new JSONObject();
		try {
			CallableStatement cs = connection.prepareCall(query);
			if (params != null && params.length > 0) {
				JSONArray _params = params[0];
				int index = 1;
				for (Object p : _params) {
					setPreparedStatement(cs, index++, p);
				}
			}
			int filas = cs.executeUpdate();
			obj.put("status", filas > 0);
			obj.put("message", "OK");
			obj.put("data", filas);
		} catch (Exception ex) {
			obj.put("status", false);
			obj.put("message", ex);
			obj.put("data", JSONObject.NULL);
		}
		return obj;
	}

	private static void setJSONObject(ResultSet rs, ResultSetMetaData rm, int i, JSONObject obj, boolean mode) throws SQLException {
		int type = rm.getColumnType(i);
		switch (type) {
			case Types.VARCHAR:
				obj.put(mode ? "" + i : rm.getColumnName(i), rs.getString(i) == null ? JSONObject.NULL : rs.getString(i));
				break;
			case Types.CHAR:
				obj.put(mode ? "" + i : rm.getColumnName(i), rs.getString(i) == null ? JSONObject.NULL : rs.getString(i));
				break;
			case Types.INTEGER:
				obj.put(mode ? "" + i : rm.getColumnName(i), rs.getInt(i));
				break;
			case Types.BIT:
				obj.put(mode ? "" + i : rm.getColumnName(i), rs.getBoolean(i));
				break;
			case Types.BINARY:
				obj.put(mode ? "" + i : rm.getColumnName(i), rs.getBytes(i));
				break;
			default:
				obj.put(mode ? "" + i : rm.getColumnName(i), rs.getString(i) == null ? JSONObject.NULL : rs.getString(i));
		}
	}

	private static void getOutputParameter(CallableStatement cs, int cont, int tipo, JSONArray parameter) throws SQLException {
		switch (tipo) {
			case Types.INTEGER:
				parameter.put(cs.getInt(cont));
				break;
			case Types.VARCHAR:
			case Types.CHAR:
				parameter.put(cs.getString(cont));
				break;
			case Types.BOOLEAN:
				parameter.put(cs.getBoolean(cont));
				break;
			case Types.DOUBLE:
				parameter.put(cs.getDouble(cont));
				break;
			case Types.BINARY:
				parameter.put(cs.getBytes(cont));
				break;
			default:
				parameter.put(cs.getString(cont));
		}
	}

	private static void registerOutputParameter(CallableStatement cs, int index, Object p, JSONObject types) throws SQLException {
		if (p.equals(Integer.class)) {
			cs.registerOutParameter(index, Types.INTEGER);
			types.put(Integer.toString(index), Types.INTEGER);
		} else if (p.equals(String.class)) {
			cs.registerOutParameter(index, Types.VARCHAR);
			types.put(Integer.toString(index), Types.VARCHAR);
		} else if (p.equals(Boolean.class)) {
			cs.registerOutParameter(index, Types.BOOLEAN);
			types.put(Integer.toString(index), Types.BOOLEAN);
		} else if (p.equals(Double.class)) {
			cs.registerOutParameter(index, Types.DOUBLE);
			types.put(Integer.toString(index), Types.DOUBLE);
		}
	}

	private static void setPreparedStatement(PreparedStatement ps, int index, Object p) throws SQLException {
		if (p instanceof Integer) {
			ps.setInt(index, (int) p);
		} else if (p instanceof String) {
			ps.setString(index, p.toString());
		} else if (p instanceof Double) {
			ps.setDouble(index, (double) p);
		} else if (p instanceof Boolean) {
			ps.setBoolean(index, (boolean) p);
		} else if (p instanceof byte[]) {
			ps.setBytes(index, (byte[]) p);
		}
	}

	private static void clearJSONArray(JSONArray jsonArray) {
		while (jsonArray != null && jsonArray.length() > 0) {
			jsonArray.remove(0);
		}
	}
}
