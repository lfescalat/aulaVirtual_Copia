/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.aulavirtual.servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
import so.aulavirtual.utilities.ResponseHelper;

/**
 *
 * @author Percy Oliver Quispe Huarcaya
 */
public class LoginServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String body = request.getParameter("body");
		if (body != null) {
			ResponseHelper responseHelper = new ResponseHelper();
			JSONObject salida = null; //Json para enviar al browser
			JSONObject obj = new JSONObject(body);
			JSONObject respuesta = null;//Adquiere los datos de la respuesta del servidor
			HttpRequest httpRequest = new HttpRequest();
			try {
				String r = httpRequest.getRespuesta(RequestPath.LOGIN, HttpRequest.POST, obj, "");//Respuesta del server
				respuesta = new JSONObject(r);
				boolean status = respuesta.getBoolean("status");
				responseHelper.setStatus(status);

				if (status) {
					JSONObject data = new JSONObject(respuesta.get("data").toString());
					JSONObject dataPersonal = new JSONObject(respuesta.get("dataPersonal").toString());
					String nombre = data.getString("nombrePersona") + " " + data.getString("apellidoPaterno");
					JSONArray datosUsuario = dataPersonal.getJSONArray("datosUsuario");
					String roles = "";
					String codigo = "";
					for (int i = 0; i < datosUsuario.length(); i++) {
						JSONObject usuario = (JSONObject) datosUsuario.get(i);
						roles += usuario.getString("nombreTipoUsuario") + " - ";
						codigo = usuario.getString("codigoUsuario");
					}
					/*Sesionando credenciales requeridas*/
					HttpSession session = request.getSession();
					session.setAttribute("usuario", obj.getString("usuario"));
					session.setAttribute("codigo", codigo);
					session.setAttribute("nombre", nombre);
					session.setAttribute("roles", roles.substring(0, roles.length() - 2));
					session.setAttribute("Authorization", "Bearer " + respuesta.getString("token"));
					session.setMaxInactiveInterval(120 * 60);

					Cookie cookieAuth = new Cookie("Authorization", "Bearer " + respuesta.getString("token"));
					cookieAuth.setMaxAge(36000);//10horas
					response.addCookie(cookieAuth);
				} else {
					responseHelper.setMessage(respuesta.getString("message"));
				}
				salida = new JSONObject(responseHelper);
				response.setContentType("application/json");
				PrintWriter pw = response.getWriter();
				pw.print(salida);
			} catch (Exception ex) {
				responseHelper.setStatus(false);
				responseHelper.setMessage("Error " + ex.getMessage());
			}
		}
	}

}
