/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.aulavirtual.dao;

import org.json.JSONObject;

/**
 *
 * @author sistem17user
 */
public interface CursoDAO {

	public JSONObject listarCursos(JSONObject datos) throws Exception;

	public JSONObject listarCurso(JSONObject datos) throws Exception;

	public JSONObject eliminarCurso(JSONObject datos) throws Exception;

	public JSONObject asignarCurso(JSONObject datos) throws Exception;

	public JSONObject eliminarCursos(JSONObject datos) throws Exception;
}
