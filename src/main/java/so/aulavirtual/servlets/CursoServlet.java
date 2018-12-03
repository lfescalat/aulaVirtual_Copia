/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.aulavirtual.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import so.aulavirtual.services.CursoService;

/**
 *
 * @author sistem17user
 */
public class CursoServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String accion = request.getParameter("accion");
		switch (accion) {
			case "listarCursos":
				listarCursos(request, response);
				break;
			case "listarCurso":
				listarCurso(request, response);
				break;
			case "eliminarCurso":
				eliminarCurso(request, response);
				break;
			case "asignarCurso":
				asignarCurso(request, response);
				break;
			case "eliminarCursos":
				eliminarCursos(request, response);
				break;
			default:
				break;
		}
	}

	private void listarCursos(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		CursoService srv = new CursoService();
		String jsonString = request.getParameter("json");
		JSONObject json = new JSONObject(jsonString);
		json.put("draw", Integer.parseInt(request.getParameter("draw")));
		json.put("length", Integer.parseInt(request.getParameter("length")));
		json.put("start", Integer.parseInt(request.getParameter("start")));
		JSONObject rs = srv.listarCursos(json);
		out.println(rs);
	}

	private void listarCurso(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		CursoService srv = new CursoService();
		String jsonString = request.getParameter("json");
		JSONObject json = new JSONObject(jsonString);
		JSONObject rs = srv.listarCurso(json);
		out.println(rs);
	}

	private void eliminarCurso(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		CursoService srv = new CursoService();
		String jsonString = request.getParameter("json");
		JSONObject json = new JSONObject(jsonString);
		JSONObject rs = srv.eliminarCurso(json);
		out.println(rs);
	}

	private void asignarCurso(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		CursoService srv = new CursoService();
		String jsonString = request.getParameter("json");
		JSONObject json = new JSONObject(jsonString);
		JSONObject rs = srv.asignarCurso(json);
		out.println(rs);
	}

	private void eliminarCursos(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		CursoService srv = new CursoService();
		String jsonString = request.getParameter("json");
		JSONObject json = new JSONObject(jsonString);
		JSONObject rs = srv.eliminarCursos(json);
		out.println(rs);
	}

}
