package so.aulavirtual.utilities;

import java.io.Serializable;
import org.json.JSONArray;
import org.json.JSONObject;

public class ResponseHelper implements Serializable {

    private JSONArray results;
    private JSONObject data;
    private boolean status;
    private String message;

    public ResponseHelper() {

    }

    public JSONArray getResults() {
	return results;
    }

    public void setResults(JSONArray results) {
	this.results = results;
    }

    public JSONObject getData() {
	return data;
    }

    public boolean isStatus() {
	return status;
    }

    public String getMessage() {
	return message;
    }

    public void setData(JSONObject data) {
	this.data = data;
    }

    public void setStatus(boolean status) {
	this.status = status;
    }

    public void setMessage(String message) {
	this.message = message;
    }
}
