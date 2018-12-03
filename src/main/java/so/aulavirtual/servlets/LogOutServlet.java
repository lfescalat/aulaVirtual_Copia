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
import org.json.JSONObject;
import so.aulavirtual.config.RequestPath;
import so.aulavirtual.utilities.GeneralMethods;
import so.aulavirtual.utilities.HttpRequest;

/**
 *
 * @author sistem08user
 */
public class LogOutServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("codigo") != null && !request.getSession().getAttribute("codigo").toString().trim().equals("")) {
			HttpSession session = request.getSession();
			String authorization = (String) session.getAttribute("Authorization");
			HttpRequest postRequest = new HttpRequest();
			String respuesta = postRequest.getRespuesta(RequestPath.LOG_OUT, HttpRequest.POST, new JSONObject("{}"), authorization);
			GeneralMethods.clearSession(request, response);
			request.getSession().removeAttribute("codigo");//
			request.getSession().removeAttribute("Authorization");
			request.getSession().removeAttribute("menu");
			request.getRequestDispatcher("/vistas/index.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("/vistas/index.jsp").forward(request, response);
		}
	}

}
