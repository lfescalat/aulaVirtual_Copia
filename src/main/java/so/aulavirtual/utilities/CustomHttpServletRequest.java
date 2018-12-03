/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.aulavirtual.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;

/**
 *
 * @author sistem17user
 */
public class CustomHttpServletRequest {

    public static JSONObject getBodyJsonObject(HttpServletRequest req) throws IOException {
	String body = "";
	if (req.getMethod().equals("POST")) {
	    StringBuilder sb = new StringBuilder();
	    BufferedReader bufferedReader = null;

	    bufferedReader = req.getReader(); // swallow silently -- can't get body, won't
	    char[] charBuffer = new char[128];
	    int bytesRead;
	    while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
		sb.append(charBuffer, 0, bytesRead);
	    }
	    if (bufferedReader != null) {
		bufferedReader.close(); // swallow silently -- can't get body, won't
	    }
	    body = sb.toString();
	}
	JSONObject respuesta = new JSONObject(body);
	return respuesta;
    }
}
