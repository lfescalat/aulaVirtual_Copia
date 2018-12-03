/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.aulavirtual.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONObject;

/**
 *
 * @author Percy Oliver Quispe Huarcaya
 */
public final class HttpRequest {

	public static final String POST = "POST";
	public static final String GET = "GET";
	public static final String PUT = "PUT";

	private URL getConn(String path) {
		URL url = null;
		try {
			url = new URL(path);
		} catch (MalformedURLException ex) {
			System.out.println("Error :  HttpRequest : " + ex.getMessage());
		}
		return url;
	}

	private HttpURLConnection setDefaultValues(URL url, String requestMethod, String authorization) throws IOException {
		HttpURLConnection conn = null;
		conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		conn.setRequestProperty("Authorization", authorization);
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod(requestMethod);
		return conn;
	}

	public String getRespuesta(String path, String requestMethod, JSONObject obj, String authorization) throws IOException {
		String respuesta = "";
		URL url = getConn(path);
		HttpURLConnection conn = setDefaultValues(url, requestMethod, authorization);
		OutputStream os = conn.getOutputStream();
		os.write(obj.toString().getBytes("UTF-8"));
		os.close();
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String linea;
		while ((linea = rd.readLine()) != null) {
			respuesta += linea;
		}
		rd.close();
		conn.disconnect();
		return respuesta;
	}
}
