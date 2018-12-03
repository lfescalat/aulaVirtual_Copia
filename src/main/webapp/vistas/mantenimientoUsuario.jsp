<%--
    Document   : index
    Created on : 05-ene-2018, 20:49:32
    Author     : Felipe Escala
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>        
    <!--template-core-->
    <%@include file="templates/header.jsp" %>
    <!--template-core-->
    <!--css-->
    <link href="../css/general.css" rel="stylesheet" type="text/css"/>
    <!--css-->
  </head>
  <body>
    <%@include file="templates/header-body.jsp" %>
    <!--  content -->
    <div class="row">
      <div class="col-sm-12 col-md-12 col-lg-5 col-lg-offset-3">                
        <div class="panel panel-primary card-3" style="margin-top: 30px"> 
          <div class="panel-heading" style="padding: 8px 15px">
            <h6 class="panel-title" style="font-size: 15px; font-family: inherit"><i class="icon icon-search4"></i>&nbsp; BÚSQUEDAS DE USUARIOS</h6>
            <div class="heading-elements">
              <ul class="icons-list">
                <li><a data-action="collapse"></a></li>
              </ul>
            </div>
          </div>
          <div class="panel-body" id="panelSearch">
            <form id="frmBusqRequ" onsubmit="return false" autocomplete="off">
              <div class="row">
                <div class="col-md-9" style="border-right: 1px inset;"> 

                  <div class='col-md-12 hide' id="div-busq-dni">
                    <div class="form-group">
                      <label>
                        DNI:
                      </label>
                      <span class="asterisk">(*)</span>
                      <input class="form-control" id="txtDniBusqueda" name="txtDni" placeholder="Ingrese DNI" maxlength="8" autofocus="">
                    </div>
                  </div>

                  <div class='col-md-12 hide' style="padding-left: 0px; padding-right: 0px;" id="div-busq-grado">
                    <div class='col-md-6'>
                      <div class="form-group">
                        <label>
                          NIVEL:
                        </label>
                        <span class="asterisk">(*)</span>
                        <select class="form-control" id="cboNivelBusqueda" name="cboNivelBusqueda">
                          <option value="">[SELECCIONE]</option>
                        </select>
                      </div>
                    </div>
                    <div class='col-md-6'>
                      <div class="form-group">
                        <label>
                          GRADO:
                        </label>
                        <span class="asterisk">(*)</span>
                        <select class="form-control" id="cboGradoBusqueda" name="cboGradoBusqueda" disabled="">
                          <option value="">[SELECCIONE]</option>
                        </select>
                      </div>
                    </div>
                  </div>

                  <div class='col-md-12 hide' id="div-busq-nombre">
                    <div class="form-group">
                      <label>
                        NOMBRE:
                      </label>
                      <span class="asterisk">(*)</span>
                      <input class="form-control" id="txtNombreBusqueda" name="txtNombre" placeholder="Ingrese nombre">
                    </div>
                  </div>

                  <div class='col-md-12 hide' id="div-busq-apellido">
                    <div class="form-group">
                      <label>
                        APELLIDO:
                      </label>
                      <span class="asterisk">(*)</span>
                      <input class="form-control" id="txtApellidoBusqueda" name="txtApellido" placeholder="Ingrese apellido">
                    </div>
                  </div>

                  <div class='col-md-12' id="div-busq-estado">
                    <div class="form-group">
                      <label>
                        ESTADO:
                      </label>
                      <span class="asterisk">(*)</span>
                      <select name="cboEstado" id="cboEstadoBusqueda" class="form-control">
                        <option value="">[SELECCIONE]</option>
                        <option value="0">INACTIVO</option>
                        <option value="1" selected="selected">ACTIVO</option>
                      </select>
                    </div>
                  </div>

                  <div class='col-md-12 hide' id="div-busq-rol">
                    <div class="form-group">
                      <label>
                        ROL:
                      </label>
                      <span class="asterisk">(*)</span>
                      <select name="cboRol" id="cboRolBusqueda" class="form-control">
                        <option value="">[SELECCIONE]</option>
                        <option value="ESTUDIANTE">ESTUDIANTE</option>
                        <option value="DOCENTE">DOCENTE</option>
                        <option value="DIRECTOR PRIMARIA">DIRECTOR PRIMARIA</option>
                        <option value="DIRECTOR SECUNDARIA">DIRECTOR SECUNDARIA</option>
                      </select>
                    </div>
                  </div>

                  <div class="row">
                    <div class="col-md-12">
                      <div class="form-group">
                        <div class="text-right"> 
                          <span class="text-danger">(*) </span>  Campo obligatorio
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-12">
                      <div class="text-right">
                        <button class="btn bg-primary border-primary-600 btn-xs" type="button" id="btnBuscar"><i class="fa fa-search"></i> Buscar</button>
                        <button class="btn bg-orange border-orange-600 btn-xs" type="button" id="btnLimpiar"><i class="fa fa-refresh"></i> Limpiar</button>
                      </div>
                    </div>
                  </div>

                </div>
                <div class="col-md-3"> 
                  <div class="col-md-12">
                    <div class="form-group">
                      <label>
                        AGREGAR FILTRO:
                      </label> 
                      <div class="multi-select-full">
                        <select class="form-control"  tabindex="1" id="cboTipoBusqueda" multiple="">
                          <option value="div-busq-dni">DNI</option>
                          <option value="div-busq-grado">GRADO</option>
                          <option value="div-busq-nombre">NOMBRES</option>
                          <option value="div-busq-apellido">APELLIDOS</option>
                          <option value="div-busq-estado" selected="">ESTADO</option>
                          <option value="div-busq-rol">ROL</option>
                        </select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </form>

          </div>
        </div> 
      </div>       

      <!--BUSQUEDA-->

      <div class="col-lg-12 col-centered">
        <div class="col-sm-12 col-md-12 col-lg-12">
          <div class="panel panel-primary card-3" style="margin-top: 30px"> 
            <div class="panel-heading" style="padding: 8px 15px">
              <h6 class="panel-title" style="font-size: 15px; font-family: inherit"><i class="fa fa-list"></i>&nbsp; LISTADO DE USUARIOS</h6>
              <div class="heading-elements">                                                                
                <button type="button" id="btnNuevo" class="btn bg-slate border-slate-600 btn-xs"><i class="glyphicon glyphicon-plus"></i>&nbsp; Nuevo Usuario</button>
                <button type="button" id="btnModificar" class="btn bg-teal-600 border-teal-700 btn-xs hide"><i class="glyphicon glyphicon-pencil"></i>&nbsp; Modificar Usuarios</button>
              </div>
            </div>
            <div class="table-responsive">
              <table class="table display dataTable table-striped table-hover table-sm table-bordered" id="tblUsuarios">                                                 
                <thead>
                  <tr>
                    <th class="text-center"><input type="checkbox" class="ckTitle"></th>
                    <th class="text-center">Nº</th>
                    <th class="text-center">USUARIO</th> 
                    <th class="text-center">DNI</th>
                    <th class="text-center">CORREO</th>
                    <th class="text-center">ROL</th>                                                       
                    <th class="text-center">ESTADO</th>                                                       
                    <th class="text-center">ACCIONES</th>    
                  </tr>
                </thead>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- / content -->
    <%@include file="templates/footer-body.jsp" %>
    <!--js-->
    <script src="../plantilla/assets/js/plugins/forms/selects/bootstrap_multiselect.js" type="text/javascript"></script>
    <script src="../js/pages/mantenimientoUsuario.js" type="text/javascript"></script>
    <!--js--> 
  </body>
</html>
