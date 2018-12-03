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
        <script src="../js/pages/checkDataRequest.js" type="text/javascript"></script>
        <!--css-->
        <link href="../css/general.css" rel="stylesheet" type="text/css"/>
        <!--css-->
    </head>
    <body>
        <%@include file="templates/header-body.jsp" %>
        <!--  content -->
        <div class="row">
            <div class="col-sm-12 col-md-12 col-lg-7 col-centered">
                <div class="panel panel-primary card-3" style="margin-top: 30px"> 
                    <div class="panel-heading" style="padding: 8px 15px">
                        <h6 class="panel-title" style="font-size: 15px; font-family: inherit"><i class="icon  icon-books"></i>&nbsp; REGISTRO DE CURSOS &nbsp;<b class="fa fa-arrow-right"></b>&nbsp;<i id="dataUser">&nbsp; </i></h6>
                        <div class="heading-elements">
                            <ul class="icons-list">
                                <li><a data-action="collapse"></a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="panel-body" id="panelSearch">
                        <form id="frmBusqRequ" onsubmit="return false" autocomplete="off">
                            <div class="row">
                                <div class='col-md-4'>
                                    <div class="form-group">
                                        <label>
                                            NIVEL:
                                        </label>
                                        <span class="asterisk">(*)</span>
                                        <select class="form-control" id="cboNivel" name="cboNivel">
                                            <option value="">[SELECCIONE]</option>
                                        </select>
                                    </div>
                                </div>
                                <div class='col-md-4'>
                                    <div class="form-group">
                                        <label>
                                            GRADO:
                                        </label>
                                        <span class="asterisk">(*)</span>
                                        <select class="form-control" id="cboGrado" name="cboGrado" disabled="">
                                            <option value="">[SELECCIONE]</option>
                                        </select>
                                    </div>
                                </div>
                                <div class='col-md-4'>
                                    <div class="form-group">
                                        <label>
                                            CURSO:
                                        </label>
                                        <span class="asterisk">(*)</span>
                                        <select class="form-control" id="cboCurso" name="cboCurso" disabled="">
                                            <option value="">[SELECCIONE]</option>
                                        </select>
                                    </div>
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
                                    <button class="btn bg-success border-success-600 btn-xs" type="button" id="btnRegistrar"><i class="fa fa-search"></i> Registrar</button>
                                    <button class="btn bg-orange border-orange-600 btn-xs" type="button" id="btnLimpiar"><i class="fa fa-refresh"></i> Limpiar</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div> 
            </div>


            <div class="col-sm-12 col-md-12 col-lg-6 col-lg-offset-3">
                <div class="panel panel-primary card-3" style="margin-top: 30px"> 
                    <div class="panel-heading" style="padding: 8px 15px">
                        <h6 class="panel-title" style="font-size: 15px; font-family: inherit"><i class="fa fa-list"></i>&nbsp; LISTADO DE CURSOS</h6>
                        <div class="heading-elements">                                                                
                            <button type="button" id="btnEliminar" class="btn bg-danger-700 border-danger-800 btn-xs hide"><i class="glyphicon glyphicon-trash"></i>&nbsp; Eliminar</button>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table display dataTable table-striped table-hover table-sm table-bordered" id="tblCursos">                                                 
                            <thead>
                                <tr>
                                    <th class="text-center"><input type="checkbox" class="ckTitle"></th>
                                    <th class="text-center">Nº</th>
                                    <th class="text-center">GRADO</th> 
                                    <th class="text-center">CURSO</th>                                        
                                    <th class="text-center">ACCIONES</th>    
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <!-- / content -->
        <%@include file="templates/footer-body.jsp" %>
        <!--js-->
        <script src="../js/pages/mantenimientoCurso.js" type="text/javascript"></script>
        <!--js--> 
    </body>
</html>
