/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.aulavirtual.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;
import so.aulavirtual.config.RequestPath;
import so.aulavirtual.utilities.HttpRequest;

/**
 *
 * @author Percy
 */
public class RedireccionarServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (request.getParameter("t") != null && request.getParameter("cp") != null) {
			String auth = request.getParameter("t");
			if (auth.startsWith("Bearer ")) {
				String codigoProyecto = request.getParameter("cp");
				HttpRequest httpRequest = new HttpRequest();
				String r = httpRequest.getRespuesta(RequestPath.ENVIAR_NUEVO_TOKEN, HttpRequest.POST, new JSONObject("{codigoProyecto:" + codigoProyecto + "}"), auth);
				JSONObject respuesta = new JSONObject(r);
				if (respuesta.getBoolean("status")) {
					HttpSession session = request.getSession();
					Cookie cookieAuth = null;
					JSONObject data = new JSONObject(respuesta.get("data").toString());
					JSONObject dataPersonal = new JSONObject(respuesta.get("dataPersonal").toString());
					String nombre = data.getString("apellidoPaterno") + " " + data.getString("apellidoMaterno") + " "
						+ data.getString("nombrePersona");
					JSONArray datosUsuario = dataPersonal.getJSONArray("datosUsuario");
					String roles = "";
					String codigo = "";
					String user = "";
					for (int i = 0; i < datosUsuario.length(); i++) {
						JSONObject usuario = (JSONObject) datosUsuario.get(i);
						roles += usuario.getString("nombreTipoUsuario") + " - ";
						codigo = usuario.getString("codigoUsuario");
						user = usuario.getString("usuario");
					}
					session.setAttribute("usuario", user);
					session.setAttribute("codigo", codigo);
					session.setAttribute("nombre", nombre);
					session.setAttribute("roles", roles.substring(0, roles.length() - 2));
					session.setAttribute("Authorization", "Bearer " + respuesta.getString("token"));
					session.setMaxInactiveInterval(120 * 60);
					cookieAuth = new Cookie("Authorization", "Bearer " + respuesta.getString("token"));
					cookieAuth.setMaxAge(36000);//10horas
					response.addCookie(cookieAuth);
					response.sendRedirect("main.jsp");
					return;
				}
			}
		}
		Cookie cookieAuth = new Cookie("Authorization", "");
		cookieAuth.setMaxAge(0);
		response.addCookie(cookieAuth);
		request.getRequestDispatcher("/vistas/index.jsp").forward(request, response);
	}
}
