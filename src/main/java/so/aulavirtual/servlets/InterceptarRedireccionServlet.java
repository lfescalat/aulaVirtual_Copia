/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.aulavirtual.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Percy Oliver Quispe Huarcaya Este servlet está encargado de
 * interceptar la petición de redirección El objetivo de este servlet es comprar
 * sí tiene una session vigente, de tener una session
 */
public class InterceptarRedireccionServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("codigo") != null && session.getAttribute("Authorization") != null) {
			String token = (String) session.getAttribute("Authorization");
			String codigoProyecto = request.getParameter("cp");
			String page = request.getParameter("p");
			String ruta = page + "?cp=" + codigoProyecto + "&t=" + token;
			response.sendRedirect(ruta);
		} else {
			request.getRequestDispatcher("/vistas/index.jsp").forward(request, response);
		}
	}

}
