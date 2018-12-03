/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.aulavirtual.config;

/**
 *
 * @author Percy Oliver Quispe Huarcaya Clase encargada de almacenar las Url
 * para enviar las solicitudes al servicio rest
 */
public final class RequestPath {

	/*REST PRODUCCION*/
	private static final String BASE = "http://app9.sacooliveros.edu.pe:8080/security-rest/api/";  //base path;
	/*Rest seguridad (security-rest)*/
	public static final String LOG_OUT = BASE + "user/logout";
	public static final String LOGIN = BASE + "user/login";
	public static final String ENVIAR_NUEVO_TOKEN = BASE + "proyecto/enviarNuevoToken";
	public static final String VERIFICAR_LOGIN = BASE + "user/verificarLogin";
	/*Rest Matricula*/
	private static final String BASE_MATRICULA = "http://172.16.2.53:8080/servicios-matricula/api/";
	public static final String PERIODO_SERVLET = BASE_MATRICULA + "periodo/periodoServlet";
}
