/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.aulavirtual.dao;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author sistem17user
 */
public interface UsuarioDAO {

	public JSONObject listarUsuarios(JSONObject datos) throws Exception;

	public JSONObject listarSede(JSONObject datos) throws Exception;

	public JSONObject listarNivel(JSONObject datos) throws Exception;

	public JSONObject listarGrado(JSONObject datos) throws Exception;

	public JSONObject eliminarUsuario(JSONObject datos) throws Exception;

	public JSONObject nuevoUsuario(JSONObject datos) throws Exception;

	public JSONObject editarUsuarios(JSONObject datos) throws Exception;

	public JSONObject validarUsuario(JSONObject datos) throws Exception;

	public JSONObject validarCursos(JSONObject datos) throws Exception;

	public JSONObject editarGradoMasivo(JSONObject datos) throws Exception;

	public JSONObject listarDetalle(JSONObject datos) throws Exception;

	public JSONObject cambiarEstado(JSONObject datos) throws Exception;

	public JSONObject registroMasivo(JSONArray datos) throws Exception;
}
