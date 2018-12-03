<%-- 
    Document   : registroMaviso
    Created on : 21-ago-2018, 10:33:41
    Author     : Felipe Escala
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
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

    <div class="row">
      <div class="col-sm-12 col-md-12 col-lg-4 col-centered">
        <div class="panel panel-primary card-3" style="margin-top: 30px"> 
          <div class="panel-heading" style="padding: 8px 15px">
            <h6 class="panel-title" style="font-size: 15px; font-family: inherit"><i class="icon   icon-folder-search"></i>&nbsp; REGISTRO MASIVO </h6>
            <div class="heading-elements">
              <ul class="icons-list">
                <li><a data-action="collapse"></a></li>
              </ul>
            </div>
          </div>
          <div class="panel-body" id="panelSearch">
            <form id="frmBusqRequ" onsubmit="return false" autocomplete="off">
              <div class="row">
                <div class="form-group">                                     
                  <input class="form-control" type="file" accept=".csv" id="filePrueba">
                </div>
              </div>
            </form>
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
                  <button class="btn bg-success border-success-600 btn-xs" type="button" id="btnCargar"><i class="fa fa-upload"></i> Cargar</button>
                  <button class="btn bg-orange border-orange-600 btn-xs" type="button" id="btnLimpiar"><i class="fa fa-refresh"></i> Limpiar</button>
                </div>
              </div>
            </div>
          </div>
        </div> 
      </div>
      <!--          <div class="col-sm-12 col-md-12 col-lg-10 col-lg-offset-1">
                  <div class="panel panel-primary card-3" style="margin-top: 30px"> 
                    <div class="panel-heading" style="padding: 8px 15px">
                      <h6 class="panel-title" style="font-size: 15px; font-family: inherit"><i class="fa fa-list"></i>&nbsp; LISTADO DE USUARIOS</h6>
                      <div class="heading-elements">                                                                
                        <button type="button" id="btnEliminar" class="btn bg-danger-700 border-danger-800 btn-xs hide"><i class="glyphicon glyphicon-trash"></i>&nbsp; Eliminar</button>
                      </div>
                    </div>
                    <div class="table-responsive">
                      <table class="table display dataTable table-striped table-hover table-sm table-bordered" id="tblCursos">                                                 
                        <thead>
                          <tr>
                            <th class="text-center">GRADO</th> 
                            <th class="text-center">CURSO</th>                                        
                            <th class="text-center">ACCIONES</th>    
                          </tr>
                        </thead>
                      </table>
                    </div>
                  </div>
                </div>-->
    </div>
<!--    <pre id="txtJson">

    </pre>-->
    <%@include file="templates/footer-body.jsp" %>
    <script src="../plantilla/assets/js/plugins/papaparse/papaparse.js" type="text/javascript"></script>
    <script src="../js/pages/registroMasivo.js" type="text/javascript"></script>
    <script type="text/javascript">


    </script>
  </body>
</html>

