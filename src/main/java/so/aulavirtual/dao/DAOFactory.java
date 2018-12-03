/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.aulavirtual.dao;

import so.aulavirtual.mysqlDAO.MysqlDAOFactory;

/**
 *
 * @author FelipeEscala
 */
public abstract class DAOFactory {

	public static final int SQL_SERVER = 1;
	public static final int MYSQL = 2;

	public static DAOFactory getDAOFactory(int whichFactory) {
		switch (whichFactory) {
			case MYSQL:
				return new MysqlDAOFactory();
			default:
				return null;
		}
	}

	public abstract UsuarioDAO getUsuarioDAO();

	public abstract CursoDAO getCursoDAO();

}
