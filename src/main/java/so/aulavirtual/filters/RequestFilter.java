/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.aulavirtual.filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import so.aulavirtual.config.RequestPath;
import so.aulavirtual.utilities.GeneralMethods;
import so.aulavirtual.utilities.HttpRequest;

/**
 *
 * @author sistem08user
 */
@WebFilter(filterName = "RequestFilter", urlPatterns = {"/*"})
public class RequestFilter implements Filter {

	// The filter configuration object we are associated with.  If
	// this value is null, this filter instance is not currently
	// configured. 
	private FilterConfig filterConfig = null;

	public RequestFilter() {
	}

	private void doBeforeProcessing(ServletRequest req, ServletResponse resp, FilterChain chain)
		throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String uri = request.getRequestURI();
		String url = String.valueOf(request.getRequestURL());
		if (uri.endsWith("aulaVirtual") || uri.endsWith("aulaVirtual/")
			|| uri.endsWith("vistas/red")
			|| uri.contains("vistas/index.jsp")
			|| uri.contains("plantilla/assets") || uri.endsWith(".js") || uri.endsWith(".css")
			|| uri.endsWith(".png") || uri.endsWith(".jpg") || uri.endsWith(".gif")
			|| uri.contains("/login") || uri.contains("vistas/redireccionarServlet")
			|| uri.endsWith("vistas/logout")
			|| uri.contains("vistas/interceptar")) {
			if (uri.contains("vistas/index.jsp")) {
				HttpSession session = request.getSession();
				if (session.getAttribute("codigo") != null && session.getAttribute("Authorization") != null) {
					response.sendRedirect("main.jsp");
				} else {
					chain.doFilter(request, response);
				}
			} else {
				chain.doFilter(request, response);
			}

		} else {
			if (request.getSession().getAttribute("codigo") != null) {
				if (uri.endsWith(".jsp")) {
					HttpSession session = request.getSession();
					HttpRequest httpRequest = new HttpRequest();
					JSONObject valid = null;
					String respuesta = "";
					String auth = "";
					try {
						if (session.getAttribute("Authorization") != null) {
							auth = (String) session.getAttribute("Authorization");
						} else {
							setError(response);
						}
						respuesta = httpRequest.getRespuesta(RequestPath.VERIFICAR_LOGIN, HttpRequest.POST, new JSONObject("{}"), auth);
						valid = new JSONObject(respuesta);
						if (valid.getBoolean("status")) {
							JSONObject menu = new JSONObject(valid.getString("menu"));//Obtiene el menu
							List<Object> vistas = new ArrayList<>();
							JSONObject rolvista = valid.getJSONObject("rolvista");
							JSONArray urls = rolvista.getJSONArray("vistas");
							for (int i = 0; i < urls.length(); i++) {
								vistas.add(urls.get(i));
							}
							vistas.add("main.jsp");//Agregando la vista principal
							vistas.add("mantenimientoCurso.jsp");
//							vistas.add("registroMasivo.jsp");
//							vistas.add("prueba.jsp");
							String ruta = request.getRequestURI();
							int indice = GeneralMethods.obtenerIndex(ruta);
							String rutaJsp = ruta.substring(indice, ruta.length());
							boolean acceso = vistas.contains(rutaJsp);
							if (!acceso) {
								request.getSession().setAttribute("error", "no tiene acceso a la vista solicitada");//Esta session se elimina en el jsp (para que no ocupe memoria)
								request.getRequestDispatcher("/vistas/templates/error.jsp").forward(request, response);
								return;
							}
							session.setAttribute("menu", menu.toString());
						} else {
							request.getSession().setAttribute("error", "no tiene credenciales validas");
							deleteCredenciales(request, response);
							request.getRequestDispatcher("/vistas/templates/error.jsp").forward(request, response);
							return;
						}
					} catch (Exception ex) {
						request.getSession().setAttribute("error", ex.getMessage());
						deleteCredenciales(request, response);
						request.getRequestDispatcher("/vistas/templates/error.jsp").forward(request, response);
						return;
					}
				}
				chain.doFilter(request, response);
			} else {
				response.sendRedirect("../vistas/index.jsp");
			}
		}
	}

	private void deleteCredenciales(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session_actual = request.getSession(true);
		session_actual.invalidate();
		Cookie cookieAuth = new Cookie("Authorization", "");
		cookieAuth.setMaxAge(0);
		response.addCookie(cookieAuth);
	}

	private void sendError(HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.sendError(401);
	}

	public void setError(HttpServletResponse response) throws IOException {
		Response.ResponseBuilder builder = null;
		sendError(response);
		builder = Response.status(Response.Status.UNAUTHORIZED).entity(response);
		throw new WebApplicationException(builder.build());
	}

	private void doAfterProcessing(ServletRequest request, ServletResponse response)
		throws IOException, ServletException {

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
		FilterChain chain)
		throws IOException, ServletException {
		doBeforeProcessing(req, resp, chain);

//        chain.doFilter(req, resp);
		doAfterProcessing(req, resp);

	}

	/**
	 * Return the filter configuration object for this filter.
	 */
	public FilterConfig getFilterConfig() {
		return (this.filterConfig);
	}

	/**
	 * Set the filter configuration object for this filter.
	 *
	 * @param filterConfig The filter configuration object
	 */
	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	/**
	 * Destroy method for this filter
	 */
	public void destroy() {
	}

	/**
	 * Init method for this filter
	 */
	public void init(FilterConfig filterConfig) {

	}

	private void sendProcessingError(Throwable t, ServletResponse response) {
		String stackTrace = getStackTrace(t);

		if (stackTrace != null && !stackTrace.equals("")) {
			try {
				response.setContentType("text/html");
				PrintStream ps = new PrintStream(response.getOutputStream());
				PrintWriter pw = new PrintWriter(ps);
				pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

				// PENDING! Localize this for next official release
				pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
				pw.print(stackTrace);
				pw.print("</pre></body>\n</html>"); //NOI18N
				pw.close();
				ps.close();
				response.getOutputStream().close();
			} catch (Exception ex) {
			}
		} else {
			try {
				PrintStream ps = new PrintStream(response.getOutputStream());
				t.printStackTrace(ps);
				ps.close();
				response.getOutputStream().close();
			} catch (Exception ex) {
			}
		}
	}

	public static String getStackTrace(Throwable t) {
		String stackTrace = null;
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			pw.close();
			sw.close();
			stackTrace = sw.getBuffer().toString();
		} catch (Exception ex) {
		}
		return stackTrace;
	}

	public void log(String msg) {
		filterConfig.getServletContext().log(msg);
	}

}
