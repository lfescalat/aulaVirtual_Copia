/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.aulavirtual.mysqlDAO;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import so.aulavirtual.dao.CursoDAO;
import so.aulavirtual.dao.DAOFactory;
import so.aulavirtual.dao.UsuarioDAO;
import so.aulavirtual.utilities.OsUtils;

/**
 *
 * @author sistem17user
 */
public class MysqlDAOFactory extends DAOFactory {

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection obtenerConexion(String base) {
		Dotenv dotenv = Dotenv
			.configure()
			.directory(OsUtils.getDotEnvPath())
			.load();
		Connection connection = null;
		String host = dotenv.get("DB_HOST");
		String port = dotenv.get("DB_PORT");
		String databaseName = dotenv.get("DB_NAME");
		String userSgbd = dotenv.get("DB_USER");
		String passwordSgbd = dotenv.get("DB_PASS");

		if (base.equals("moodle")) {
			try {
				connection = DriverManager.getConnection(
					"jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false",
					userSgbd,
					passwordSgbd);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}

	@Override
	public UsuarioDAO getUsuarioDAO() {
		return new UsuarioMysqlDAO();
	}

	@Override
	public CursoDAO getCursoDAO() {
		return new CursoMysqlDAO();
	}
}
