/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.aulavirtual.utilities;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import so.aulavirtual.mysqlDAO.MysqlDAOFactory;

public class GeneralMethods {

	public static int getMaxSize(String tabla, String campo) {
		int cant = 0;
		Connection con = null;
		try {
			con = MysqlDAOFactory.obtenerConexion(GeneralVariables.nameDB);
			DatabaseMetaData metadata = con.getMetaData();
			ResultSet rst = metadata.getColumns(null, null, tabla, campo);
			rst.next();
			cant = rst.getInt("COLUMN_SIZE");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cant;
	}

	public static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static void clearSession(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookie.setValue(null);
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
	}

	public static int obtenerIndex(String ruta) {
		char[] c = ruta.toCharArray();
		int salida = 0;
		for (int i = c.length - 1; i >= 0; i--) {
			String help = c[i] + "";
			if (!help.equals("/")) {
				salida = i;
			} else {
				break;
			}
		}
		return salida;
	}

}
