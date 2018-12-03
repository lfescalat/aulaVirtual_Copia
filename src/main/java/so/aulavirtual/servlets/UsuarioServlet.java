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
import org.json.JSONArray;
import org.json.JSONObject;
import so.aulavirtual.services.UsuarioService;

/**
 *
 * @author sistem17user
 */
public class UsuarioServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String accion = request.getParameter("accion");
		switch (accion) {
			case "listarUsuarios":
				listarUsuarios(request, response);
				break;
			case "listarSede":
				listarSede(request, response);
				break;
			case "listarNivel":
				listarNivel(request, response);
				break;
			case "listarGrado":
				listarGrado(request, response);
				break;
			case "eliminarUsuario":
				eliminarUsuario(request, response);
				break;
			case "nuevoUsuario":
				nuevoUsuario(request, response);
				break;
			case "editarUsuarios":
				editarUsuarios(request, response);
				break;
			case "validarUsuario":
				validarUsuario(request, response);
				break;
			case "editarGradoMasivo":
				editarGradoMasivo(request, response);
				break;
			case "listarDetalle":
				listarDetalle(request, response);
				break;
			case "cambiarEstado":
				cambiarEstado(request, response);
				break;
			case "registroMasivo":
				registroMasivo(request, response);
				break;
			default:
				break;
		}
	}

	private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		UsuarioService srv = new UsuarioService();
		String jsonString = request.getParameter("json");
		JSONObject json = new JSONObject(jsonString);
		json.put("draw", Integer.parseInt(request.getParameter("draw")));
		json.put("length", Integer.parseInt(request.getParameter("length")));
		json.put("start", Integer.parseInt(request.getParameter("start")));
		JSONObject rs = srv.listarUsuarios(json);
		out.println(rs);
	}

	private void listarSede(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		UsuarioService srv = new UsuarioService();
		JSONObject rs = srv.listarSede(new JSONObject());
		out.println(rs);
	}

	private void listarNivel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		UsuarioService srv = new UsuarioService();
		JSONObject rs = srv.listarNivel(new JSONObject());
		out.println(rs);
	}

	private void listarGrado(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		UsuarioService srv = new UsuarioService();
		String jsonString = request.getParameter("json");
		JSONObject json = new JSONObject(jsonString);
		JSONObject rs = srv.listarGrado(json);
		out.println(rs);
	}

	private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		UsuarioService srv = new UsuarioService();
		String jsonString = request.getParameter("json");
		JSONObject json = new JSONObject(jsonString);
		JSONObject rs = srv.eliminarUsuario(json);
		out.println(rs);
	}

	private void nuevoUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		UsuarioService srv = new UsuarioService();
		String jsonString = request.getParameter("json");
		JSONObject json = new JSONObject(jsonString);
		JSONObject rs = srv.nuevoUsuario(json);
		out.println(rs);
	}

	private void editarUsuarios(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		UsuarioService srv = new UsuarioService();
		String jsonString = request.getParameter("json");
		JSONObject json = new JSONObject(jsonString);
		JSONObject rs = srv.editarUsuarios(json);
		out.println(rs);
	}

	private void validarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		UsuarioService srv = new UsuarioService();
		String jsonString = request.getParameter("json");
		JSONObject json = new JSONObject(jsonString);
		JSONObject rs = srv.validarUsuario(json);
		out.println(rs);
	}

	private void editarGradoMasivo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		UsuarioService srv = new UsuarioService();
		String jsonString = request.getParameter("json");
		JSONObject json = new JSONObject(jsonString);
		JSONObject rs = srv.editarGradoMasivo(json);
		out.println(rs);
	}

	private void listarDetalle(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		UsuarioService srv = new UsuarioService();
		String jsonString = request.getParameter("json");
		JSONObject json = new JSONObject(jsonString);
		json.put("draw", Integer.parseInt(request.getParameter("draw")));
		json.put("length", Integer.parseInt(request.getParameter("length")));
		json.put("start", Integer.parseInt(request.getParameter("start")));
		JSONObject rs = srv.listarDetalle(json);
		out.println(rs);
	}

	private void cambiarEstado(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		UsuarioService srv = new UsuarioService();
		String jsonString = request.getParameter("json");
		JSONObject json = new JSONObject(jsonString);
		JSONObject rs = srv.cambiarEstado(json);
		out.println(rs);
	}

	private void registroMasivo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		UsuarioService srv = new UsuarioService();
		String jsonString = request.getParameter("json");
		JSONArray json = new JSONArray(jsonString);
		JSONObject rs = srv.registroMasivo(json);
		out.println(rs);
	}
}
