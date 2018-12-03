   /* global loader, bootbox, Cookies, elementLoader */

   //  VARIABLES GLOBALES
   let tableUsuarios;
   let selectedItem = [];
   let checks;
   let selectedGroup = {};
   //  VARIABLES GLOBALES 

   const helpers = {
      tblUsuariosCharge() {
         tableUsuarios.destroy()
         initComponents.listarUsuarios()
      },
      tblUsuariosRefresh() {
         loader.iniciarLoader()
         tableUsuarios.ajax.reload(null, false);
      }
   }

   const httpRequest = {
      listarSede() {
         return new Promise((resolve, reject) => {
            $.ajax({
               type: 'POST',
               url: "../UsuarioServlet?accion=listarSede",
               dataType: "json",
               success: function (data, textStatus, jqXHR) {
                  elementLoader.terminarLoader('cboSede')
                  resolve(data)
               }, error: function (jqXHR, textStatus, errorThrown) {
                  elementLoader.terminarLoader('cboSede')
                  reject('Error en la petición')
               }
            })
         })
      },
      validarUsuario(json) {
         return new Promise((resolve, reject) => {
            $.ajax({
               type: 'POST',
               url: "../UsuarioServlet?accion=validarUsuario",
               dataType: "json",
               data: {
                  json: JSON.stringify(json)
               },
               success: function (data, textStatus, jqXHR) {
                  loader.terminarLoader()
                  resolve(data)
               }, error: function (jqXHR, textStatus, errorThrown) {
                  loader.terminarLoader()
                  reject('Error en la petición')
               }
            })
         })
      },
      listarNivel() {
         return new Promise((resolve, reject) => {
            $.ajax({
               type: 'POST',
               url: "../UsuarioServlet?accion=listarNivel",
               dataType: "json",
               success: function (data, textStatus, jqXHR) {
                  elementLoader.terminarLoader('cboNivel')
                  elementLoader.terminarLoader('cboNivelBusqueda')
                  resolve(data)
               }, error: function (jqXHR, textStatus, errorThrown) {
                  elementLoader.terminarLoader('cboNivel')
                  reject('Error en la petición')
               }
            })
         })
      },
      listarGrado(json) {
         return new Promise((resolve, reject) => {
            $.ajax({
               type: 'POST',
               url: "../UsuarioServlet?accion=listarGrado",
               dataType: "json",
               data: {
                  json: JSON.stringify(json)
               },
               success: function (data, textStatus, jqXHR) {
                  elementLoader.terminarLoader('cboGrado')
                  elementLoader.terminarLoader('cboGradoBusqueda')
                  resolve(data)
               }, error: function (jqXHR, textStatus, errorThrown) {
                  elementLoader.terminarLoader('cboGrado')
                  reject('Error en la petición')
               }
            })
         })
      },
      eliminarUsuario(json) {
         return new Promise((resolve, reject) => {
            $.ajax({
               type: 'POST',
               url: "../UsuarioServlet?accion=eliminarUsuario",
               dataType: "json",
               data: {
                  json: JSON.stringify(json)
               },
               success: function (data, textStatus, jqXHR) {
                  loader.terminarLoader()
                  resolve(data)
               }, error: function (jqXHR, textStatus, errorThrown) {
                  loader.terminarLoader()
                  reject('Error en la petición')
               }
            })
         })
      },
      nuevoUsuario(json) {
         return new Promise((resolve, reject) => {
            $.ajax({
               type: 'POST',
               url: "../UsuarioServlet?accion=nuevoUsuario",
               dataType: "json",
               data: {
                  json: JSON.stringify(json)
               },
               success: function (data, textStatus, jqXHR) {
                  loader.terminarLoader()
                  resolve(data)
               }, error: function (jqXHR, textStatus, errorThrown) {
                  loader.terminarLoader()
                  reject('Error en la petición')
               }
            })
         })
      },
      editarUsuario(json) {
         return new Promise((resolve, reject) => {
            $.ajax({
               type: 'POST',
               url: "../UsuarioServlet?accion=editarUsuarios",
               dataType: "json",
               data: {
                  json: JSON.stringify(json)
               },
               success: function (data, textStatus, jqXHR) {
                  loader.terminarLoader()
                  resolve(data)
               }, error: function (jqXHR, textStatus, errorThrown) {
                  loader.terminarLoader()
                  reject('Error en la petición')
               }
            })
         })
      },
      editarGradoMasivo(json) {
         return new Promise((resolve, reject) => {
            $.ajax({
               type: 'POST',
               url: "../UsuarioServlet?accion=editarGradoMasivo",
               dataType: "json",
               data: {
                  json: JSON.stringify(json)
               },
               success: function (data, textStatus, jqXHR) {
                  loader.terminarLoader()
                  resolve(data)
               }, error: function (jqXHR, textStatus, errorThrown) {
                  loader.terminarLoader()
                  reject('Error en la petición')
               }
            })
         })
      },
      cambiarEstado(json) {
         return new Promise((resolve, reject) => {
            $.ajax({
               type: 'POST',
               url: "../UsuarioServlet?accion=cambiarEstado",
               dataType: "json",
               data: {
                  json: JSON.stringify(json)
               },
               success: function (data, textStatus, jqXHR) {
                  loader.terminarLoader()
                  resolve(data)
               }, error: function (jqXHR, textStatus, errorThrown) {
                  loader.terminarLoader()
                  reject('Error en la petición')
               }
            })
         })
      }
   }

   const DOMEvents = {
      init() {
         this.nuevoUsuario()
         this.tblUsuariosEditar()
         this.tblUsuariosAsignar()
         this.tblUsuarioEliminar()
         this.tblUsuariosBuscar()
         this.tblUsuariosLimpiar()
         this.autoClickSearch()
//         this.checkTipoBusqueda()
         this.checkSelectsBusqueda()
         this.editarUsuarios()
         this.pushCheckedItem()
         this.tblUsuarioEstado()
      },
      tblUsuariosEditar() {
         $('#tblUsuarios tbody').on('click', '.editar', (e) => {
            let thisNode = e.currentTarget;
            let data = tableUsuarios.row($(thisNode).parents('tr')).data();
            let bbxEditarUsuario = bootbox.dialog({
               title: "<span>EDITAR USUARIO</span>",
               headerClass: "modal-bg-primary",
               size: "large",
               message: `<form id="formCrearUsuario" onsubmit="return false" autocomplete="off"> 
                              <div class="row">
                                  <div class="col-md-4"> 
                                       <div class="form-group">
                                          <label>Rol: <span class="asterisk">(*)</span></label>
                                          <select class="form-control" id="cboRol" name="cboRol">
                                             <option value="">[SELECCIONE]</option>
                                             <option value="ESTUDIANTE">ESTUDIANTE</option>
                                             <option value="DOCENTE">DOCENTE</option>
                                             <option value="DIRECTOR PRIMARIA">DIRECTOR PRIMARIA</option>
                                             <option value="DIRECTOR SECUNDARIA">DIRECTOR SECUNDARIA</option>
                                          </select>
                                       </div>
                                  </div>                                  
                                  <div class="col-md-4"> 
                                       <div class="form-group">
                                          <label>Tipo: <span class="asterisk">(*)</span></label>
                                          <select class="form-control" id="cboTipo" name="cboTipo">
                                             <option value="">[SELECCIONE]</option>
                                             <option value="OIDC">OIDC</option>
                                             <option value="MANUAL">MANUAL</option>
                                          </select>
                                       </div>
                                  </div>          
                                  <div class="col-md-4"> 
                                       <div class="form-group">
                                          <label>Dni: <span class="asterisk">(*)</span></label>
                                          <input type="text" class="form-control" id="txtDni" name="txtDni" placeholder="Ingrese DNI" maxlength="8">
                                       </div>
                                  </div>         
                              </div> 
                              <div class="row">
                                 <div class="col-md-4"> 
                                       <div class="form-group">
                                          <label>Apellido Paterno: <span class="asterisk">(*)</span></label>
                                          <input type="text" class="form-control" id="txtApepat" name="txtApepat" placeholder="Ingrese apellido paterno" style="text-transform:uppercase">
                                       </div>
                                  </div>      
                                  <div class="col-md-4"> 
                                       <div class="form-group">
                                          <label>Apellido Materno: <span class="asterisk">(*)</span></label>
                                          <input type="text" class="form-control" id="txApemat" name="txApemat" placeholder="Ingrese apellido materno" style="text-transform:uppercase">
                                       </div>
                                  </div>                                           
                                  <div class="col-md-4"> 
                                       <div class="form-group">
                                          <label>Nombre(s): <span class="asterisk">(*)</span></label>                                       
                                          <input type="text" class="form-control" id="txtNombre" name="txtNombre" placeholder="Ingrese nombre(s)" style="text-transform:uppercase">
                                       </div>
                                  </div>        
                              </div>
                              <div class="row">
                                  <div class="col-md-6"> 
                                       <div class="form-group">
                                          <label>Correo: <span class="asterisk">(*)</span></label>
                                          <input type="email" class="form-control" id="txtCorreo" name="txtCorreo" disabled="" placeholder="Correo Autogenerado">
                                       </div>
                                  </div>                                          
                                  <div class="col-md-6"> 
                                       <div class="form-group">
                                          <label>Contraseña temporal: </label>
                                          <input type="text" class="form-control" id="txtClave" name="txtClave" placeholder="Ingrese contraseña temporal">
                                       </div>
                                  </div>                                          
                              </div>
                              <div class="row rowCbo">
                                 <div class="colSede col-md-4">
                                       <div class="form-group">
                                          <label>Sede: <span class="asterisk">(*)</span></label>
                                          <select class="form-control" id="cboSede" name="cboSede">
                                             <option value="">[SELECCIONE]</option>
                                          </select>
                                       </div>
                                 </div>
                                 <div class="colNivel col-md-4">
                                       <div class="form-group">                                          
                                          <label>Nivel: <span class="asterisk">(*)</span></label>
                                          <select class="form-control" id="cboNivel" name="cboNivel">
                                             <option value="">[SELECCIONE]</option>
                                          </select>
                                       </div>
                                 </div>
                                 <div class="colGrado col-md-4 hide">
                                       <div class="form-group">
                                          <label>Grado: <span class="asterisk">(*)</span></label>
                                          <select class="form-control" id="cboGrado" name="cboGrado">
                                             <option value="">[SELECCIONE]</option>
                                          </select>
                                       </div>
                                 </div>
                              </div>
                          </form>
                        <div class="row">
                           <div class="col-md-12">
                               <div class="form-group" style="margin-bottom: 0px;">
                                   <div class="text-right"> 
                                       <span class="asterisk">(*)</span>  Campo obligatorio
                                   </div>
                               </div>
                           </div>
                        </div>`,
               buttons: {
                  si: {
                     label: "<i class='fa fa-check bigger-110'></i>&nbsp; Editar ",
                     className: "btn bg-success border-success-600 btn-xs",
                     callback: () => {
                        let valCrearUsuario = $("#formCrearUsuario").validate({
                           rules: {
                              cboRol: {required: true},
                              cboTipo: {required: true},
                              txtDni: {
                                 required: true,
                                 maxlength: 8,
                                 minlength: 8
                              },
                              txtCorreo: {required: true},
                              txtApepat: {required: true},
                              txApemat: {required: true},
                              txtNombre: {required: true},
                              cboSede: {required: true},
                              cboNivel: {required: true},
                              cboGrado: {required: true}
                           },
                           messages: {
                              cboRol: {required: "<b>&times;</b>&nbsp;Este campo es requerido"},
                              cboTipo: {required: "<b>&times;</b>&nbsp;Este campo es requerido"},
                              txtDni: {
                                 required: "<b>&times;</b>&nbsp;Este campo es requerido",
                                 maxlength: "<b>&times;</b>&nbsp;Debe ingresar 8 dígitos",
                                 minlength: "<b>&times;</b>&nbsp;Debe ingresar 8 dígitos"
                              },
                              txtCorreo: {required: "<b>&times;</b>&nbsp;Este campo es requerido", email: "<b>&times;</b>&nbsp;Ingrese un correo válido"},
                              txtApepat: {required: "<b>&times;</b>&nbsp;Este campo es requerido"},
                              txApemat: {required: "<b>&times;</b>&nbsp;Este campo es requerido"},
                              txtNombre: {required: "<b>&times;</b>&nbsp;Este campo es requerido"},
                              cboSede: {required: "<b>&times;</b>&nbsp;Este campo es requerido"},
                              cboNivel: {required: "<b>&times;</b>&nbsp;Este campo es requerido"},
                              cboGrado: {required: "<b>&times;</b>&nbsp;Este campo es requerido"}
                           }
                        });
                        if (valCrearUsuario.form()) {
                           let json = {
                              tipoUsuario: $('#cboTipo').val(),
                              correoUsuario: $('#txtCorreo').val(),
                              dniUsuario: $('#txtDni').val(),
                              nombreUsuario: $('#txtNombre').val(),
                              apepatUsuario: $('#txtApepat').val(),
                              apematUsuario: $('#txApemat').val(),
                              nivelUsuario: $("#cboNivel option:selected").text(),
                              gradoUsuario: $("#cboGrado option:selected").text(),
                              codigoGrado: $('#cboGrado').val(),
                              sedeUsuario: $("#cboSede option:selected").text(),
                              claveUsuario: $('#txtClave').val() === undefined ? '' : $('#txtClave').val(),
                              rolUsuario: $('#cboRol').val(),
                              codigoUsuario: data.codigoUsuario
                           }
                           loader.iniciarLoader()
                           httpRequest.validarUsuario(json)
                                   .then((rs) => {
                                      if (!rs.status) {
                                         msnError('El usuario ingresado ya existe', () => {
                                            $(`#tblUsuarios tbody .click${data.numeral}`).click()
                                         })
                                      } else {
//                                         if (json.rolUsuario === 'ESTUDIANTE') {
//                                            if (json.gradoUsuario !== data.gradoUsuario) {
//
//                                            }
//                                         } else {
//
//                                         }
                                         msnConfirm(" ¿Está seguro de modifcar este usuario? ", (option) => {
                                            if (option) {
                                               loader.iniciarLoader()
                                               httpRequest.editarUsuario(json)
                                                       .then((data) => {
                                                          if (data.status) {
                                                             helpers.tblUsuariosRefresh()
                                                             msnSuccess("Usuario correctamente modificado")
                                                          } else {
                                                             if ((data.message).includes('--->')) {
                                                                msnError("Ha ocurrido un error")
                                                             } else {
                                                                msnError(data.message)
                                                             }
                                                          }
                                                       })
                                                       .catch(err => console.log(err))
                                            }
                                         })
                                      }
                                   })
                                   .catch(err => console.log(err))
                        } else {
                           return false
                        }
                     }
                  },
                  no: {
                     label: "<i class='fa fa-times bigger-110'></i>&nbsp; Cancelar",
                     className: "btn bg-danger border-danger-600 btn-xs",
                     callback: () => {
                     }
                  }
               }
            })
            bbxEditarUsuario.init(() => {
               elementLoader.iniciarLoader('cboSede')
               elementLoader.iniciarLoader('cboNivel')
               initRequest.cargarSedeData(data.sedeUsuario)
               initRequest.cargarNivelData(data.nivelUsuario)
               this.generarCorreo()
               initComponents.initAlfiValidate()
               this.checkSelectsData(data.gradoUsuario)
               initComponents.chargeDataBootbox(data)
            })
         })
      },
      tblUsuariosAsignar() {
         $('#tblUsuarios tbody').on('click', '.asignar', (e) => {
            loader.iniciarLoader()
            let thisNode = e.currentTarget;
            let data = tableUsuarios.row($(thisNode).parents('tr')).data();
            Cookies.set("dataUsuario", JSON.stringify(data), {expiry: 600})
            window.location = 'mantenimientoCurso.jsp'
         })
      },
      tblUsuarioEliminar() {
         $('#tblUsuarios tbody').on('click', '.eliminar', (e) => {
            let thisNode = e.currentTarget;
            let data = tableUsuarios.row($(thisNode).parents('tr')).data();
            msnConfirm(`<label>¿Seguro de eliminar este Usuario?</label>`, (option) => {
               if (option) {
                  loader.iniciarLoader()
                  httpRequest.eliminarUsuario(data)
                          .then((data) => {
                             if (data.status) {
                                helpers.tblUsuariosRefresh()
                                msnSuccess("Usuario correctamente eliminado")
                             } else {
                                if ((data.message).includes('--->')) {
                                   msnError("Ha ocurrido un error")
                                } else {
                                   msnError(data.message)
                                }
                             }
                          })
                          .catch(err => console.log(err))
               }
            });
         })
      },
      tblUsuarioEstado() {
         $('#tblUsuarios tbody').on('click', '.estado', (e) => {
            let thisNode = e.currentTarget;
            let data = tableUsuarios.row($(thisNode).parents('tr')).data();
            msnConfirm(`<label>¿Seguro de modificar este Usuario?</label>`, (option) => {
               if (option) {
                  httpRequest.cambiarEstado(data)
                          .then((data) => {
                             if (data.status) {
                                helpers.tblUsuariosRefresh()
                                msnSuccess("Usuario correctamente modificado")
                             } else {
                                if ((data.message).includes('--->')) {
                                   msnError("Ha ocurrido un error")
                                } else {
                                   msnError(data.message)
                                }
                             }
                          })
                          .catch(err => console.log(err))
               }
            });
         })
      },
      tblUsuariosBuscar() {
         $('#btnBuscar').click(() => {
            let valBusqUsuario = $("#frmBusqRequ").validate({
               rules: {
                  txtDni: {
                     required: true,
                     maxlength: 8,
                     minlength: 8
                  },
                  cboNivelBusqueda: {
                     required: true
                  },
                  cboGradoBusqueda: {
                     required: true
                  },
                  txtNombre: {
                     required: true
                  },
                  txtApellido: {
                     required: true
                  },
                  cboEstado: {
                     required: true
                  },
                  cboRol: {
                     required: true
                  }
               },
               messages: {
                  txtDni: {
                     required: "<b>&times;</b>&nbsp;Este campo es requerido",
                     maxlength: "<b>&times;</b>&nbsp;Debe ingresar 8 dígitos",
                     minlength: "<b>&times;</b>&nbsp;Debe ingresar 8 dígitos"
                  },
                  cboNivelBusqueda: {
                     required: "<b>&times;</b>&nbsp;Este campo es requerido"
                  },
                  cboGradoBusqueda: {
                     required: "<b>&times;</b>&nbsp;Este campo es requerido"
                  },
                  txtNombre: {
                     required: "<b>&times;</b>&nbsp;Este campo es requerido"
                  },
                  txtApellido: {
                     required: "<b>&times;</b>&nbsp;Este campo es requerido"
                  },
                  cboEstado: {
                     required: "<b>&times;</b>&nbsp;Este campo es requerido"
                  },
                  cboRol: {
                     required: "<b>&times;</b>&nbsp;Este campo es requerido"
                  }
               }
            });
            if (valBusqUsuario.form()) {
               loader.iniciarLoader()
               helpers.tblUsuariosCharge()
            }
         })
      },
      tblUsuariosLimpiar() {
         $('#btnLimpiar').click(() => {
            loader.iniciarLoader()
            $(":checkbox:regex(value, ^div-busq)").each(function (i, e) {
               if (i !== 4) {
                  $(e).parent().removeClass('checked')
                  $("option[value='" + $(e).val() + "']").prop("selected", false)
               } else {
                  $(e).parent().addClass('checked')
                  $("option[value='" + $(e).val() + "']").prop("selected", true)
               }
            });
            $("#cboTipoBusqueda").multiselect("refresh");
            $(':checkbox:regex(value, ^div-busq)').change()
            $('#txtDni').val('')
            $('#cboNivelBusqueda').val('')
            $('#cboGradoBusqueda').val('')
            $('#txtNombre').val('')
            $('#txtApellido').val('')
            $('#cboEstado').val('1')
            $('#cboRol').val('')
            helpers.tblUsuariosCharge()
         })
      },
      checkSelects() {
         $('#cboNivel').change(() => {
            let cboNivel = $('#cboNivel').val()
            if (cboNivel !== '') {
               if (cboNivel !== '3') {
                  $(".colGrado").removeClass('hide')
                  elementLoader.iniciarLoader('cboGrado')
                  initRequest.cargarGrado()
               } else {
                  $(".colGrado").addClass('hide')
                  $('#cboGrado').html('<option value="3" selected=true>Academias</option>')
               }
            } else {
               $(".colGrado").addClass('hide')
            }
         })
      },
      checkSelectsBusqueda() {
         $('#cboNivelBusqueda').change(() => {
            let cboNivel = $('#cboNivelBusqueda').val()
            if (cboNivel !== '') {
               $("#cboGradoBusqueda").prop('disabled', false)
               elementLoader.iniciarLoader('cboGradoBusqueda')
               initRequest.cargarGradoBusqueda()
            } else {
               $('#cboGradoBusqueda').html('<option>[SELECCIONE]</option>')
               $("#cboGradoBusqueda").prop('disabled', true)
            }
         })
      },
      checkSelectsData(nombre) {
         $('#cboNivel').change(() => {
            let cboNivel = $('#cboNivel').val()
            if (cboNivel !== '') {
               if (cboNivel !== '3') {
                  $(".colGrado").removeClass('hide')
                  elementLoader.iniciarLoader('cboGrado')
                  initRequest.cargarGradoData(nombre)
               } else {
                  $(".colGrado").addClass('hide')
                  $('#cboGrado').html('<option value="3" selected=true>Academias</option>')
               }
            } else {
               $(".colGrado").addClass('hide')
            }
         })
      },
      checkSelectsDataMasivo(codigo) {
         $('#cboNivel').change(() => {
            let cboNivel = $('#cboNivel').val()
            if (cboNivel !== '') {
               if (cboNivel !== '3') {
                  $("#cboGrado").attr('disabled', false)
                  elementLoader.iniciarLoader('cboGrado')
                  initRequest.cargarGradoDataMasivo(codigo)
               } else {
                  $("#cboGrado").attr('disabled', true)
                  $('#cboGrado').html('<option value="3" selected=true>Academias</option>')
               }
            } else {
               $("#cboGrado").attr('disabled', true)
            }
         })
      },
      nuevoUsuario() {
         $('#btnNuevo').click(() => {
            let bbxCrearUsuario = bootbox.dialog({
               title: "<span>CREAR USUARIO</span>",
               headerClass: "modal-bg-primary",
               className: "bbxCrearUsuario",
               size: "large",
               message: `<form id="formCrearUsuario" onsubmit="return false" autocomplete="off"> 
                              <div class="row">
                                  <div class="col-md-4"> 
                                       <div class="form-group">
                                          <label>Rol: <span class="asterisk">(*)</span></label>
                                          <select class="form-control" id="cboRol" name="cboRol">
                                             <option value="">[SELECCIONE]</option>
                                             <option value="ESTUDIANTE">ESTUDIANTE</option>
                                             <option value="DOCENTE">DOCENTE</option>
                                             <option value="DIRECTOR PRIMARIA">DIRECTOR PRIMARIA</option>
                                             <option value="DIRECTOR SECUNDARIA">DIRECTOR SECUNDARIA</option>
                                          </select>
                                       </div>
                                  </div>                                  
                                  <div class="col-md-4"> 
                                       <div class="form-group">
                                          <label>Tipo: <span class="asterisk">(*)</span></label>
                                          <select class="form-control" id="cboTipo" name="cboTipo">
                                             <option value="">[SELECCIONE]</option>
                                             <option value="OIDC">OIDC</option>
                                             <option value="MANUAL">MANUAL</option>
                                          </select>
                                       </div>
                                  </div>          
                                  <div class="col-md-4"> 
                                       <div class="form-group">
                                          <label>Dni: <span class="asterisk">(*)</span></label>
                                          <input type="text" class="form-control" id="txtDni" name="txtDni" placeholder="Ingrese DNI" maxlength="8">
                                       </div>
                                  </div>         
                              </div> 
                              <div class="row">
                                 <div class="col-md-4"> 
                                       <div class="form-group">
                                          <label>Apellido Paterno: <span class="asterisk">(*)</span></label>
                                          <input type="text" class="form-control" id="txtApepat" name="txtApepat" placeholder="Ingrese apellido paterno" style="text-transform:uppercase">
                                       </div>
                                  </div>      
                                  <div class="col-md-4"> 
                                       <div class="form-group">
                                          <label>Apellido Materno: <span class="asterisk">(*)</span></label>
                                          <input type="text" class="form-control" id="txApemat" name="txApemat" placeholder="Ingrese apellido materno" style="text-transform:uppercase">
                                       </div>
                                  </div>                                           
                                  <div class="col-md-4"> 
                                       <div class="form-group">
                                          <label>Nombre(s): <span class="asterisk">(*)</span></label>                                       
                                          <input type="text" class="form-control" id="txtNombre" name="txtNombre" placeholder="Ingrese nombre(s)" style="text-transform:uppercase">
                                       </div>
                                  </div>        
                              </div>
                              <div class="row">
                                  <div class="col-md-6"> 
                                       <div class="form-group">
                                          <label>Correo: <span class="asterisk">(*)</span></label>
                                          <input type="email" class="form-control" id="txtCorreo" name="txtCorreo" disabled="" placeholder="Correo Autogenerado">
                                       </div>
                                  </div>                                          
                                  <div class="col-md-6"> 
                                       <div class="form-group">
                                          <label>Contraseña temporal: </label>
                                          <input type="text" class="form-control" id="txtClave" name="txtClave" placeholder="Ingrese contraseña temporal">
                                       </div>
                                  </div>                                          
                              </div>
                              <div class="row rowCbo hide">
                                 <div class="colSede col-md-4">
                                       <div class="form-group">
                                          <label>Sede: <span class="asterisk">(*)</span></label>
                                          <select class="form-control" id="cboSede" name="cboSede">
                                             <option value="">[SELECCIONE]</option>
                                          </select>
                                       </div>
                                 </div>
                                 <div class="colNivel col-md-4">
                                       <div class="form-group">                                          
                                          <label>Nivel: <span class="asterisk">(*)</span></label>
                                          <select class="form-control" id="cboNivel" name="cboNivel">
                                             <option value="">[SELECCIONE]</option>
                                          </select>
                                       </div>
                                 </div>
                                 <div class="colGrado col-md-4 hide">
                                       <div class="form-group">
                                          <label>Grado: <span class="asterisk">(*)</span></label>
                                          <select class="form-control" id="cboGrado" name="cboGrado">
                                             <option value="">[SELECCIONE]</option>
                                          </select>
                                       </div>
                                 </div>
                              </div>
                          </form>
                        <div class="row">
                           <div class="col-md-12">
                               <div class="form-group" style="margin-bottom: 0px;">
                                   <div class="text-right"> 
                                       <span class="asterisk">(*)</span>  Campo obligatorio
                                   </div>
                               </div>
                           </div>
                        </div>`,
               buttons: {
                  si: {
                     label: "<i class='fa fa-check bigger-110'></i>&nbsp; Crear",
                     className: "btn bg-success border-success-600 btn-xs",
                     callback: () => {
                        let valCrearUsuario = $("#formCrearUsuario").validate({
                           rules: {
                              cboRol: {required: true},
                              cboTipo: {required: true},
                              txtDni: {
                                 required: true,
                                 maxlength: 8,
                                 minlength: 8
                              },
                              txtCorreo: {required: true},
                              txtApepat: {required: true},
                              txApemat: {required: true},
                              txtNombre: {required: true},
                              cboSede: {required: true},
                              cboNivel: {required: true},
                              cboGrado: {required: true}
                           },
                           messages: {
                              cboRol: {required: "<b>&times;</b>&nbsp;Este campo es requerido"},
                              cboTipo: {required: "<b>&times;</b>&nbsp;Este campo es requerido"},
                              txtDni: {
                                 required: "<b>&times;</b>&nbsp;Este campo es requerido",
                                 maxlength: "<b>&times;</b>&nbsp;Debe ingresar 8 dígitos",
                                 minlength: "<b>&times;</b>&nbsp;Debe ingresar 8 dígitos"
                              },
                              txtCorreo: {required: "<b>&times;</b>&nbsp;Este campo es requerido", email: "<b>&times;</b>&nbsp;Ingrese un correo válido"},
                              txtApepat: {required: "<b>&times;</b>&nbsp;Este campo es requerido"},
                              txApemat: {required: "<b>&times;</b>&nbsp;Este campo es requerido"},
                              txtNombre: {required: "<b>&times;</b>&nbsp;Este campo es requerido"},
                              cboSede: {required: "<b>&times;</b>&nbsp;Este campo es requerido"},
                              cboNivel: {required: "<b>&times;</b>&nbsp;Este campo es requerido"},
                              cboGrado: {required: "<b>&times;</b>&nbsp;Este campo es requerido"}
                           }
                        });
                        if (valCrearUsuario.form()) {
                           let json = {
                              tipoUsuario: $('#cboTipo').val(),
                              correoUsuario: $('#txtCorreo').val(),
                              dniUsuario: $('#txtDni').val(),
                              nombreUsuario: $('#txtNombre').val(),
                              apepatUsuario: $('#txtApepat').val(),
                              apematUsuario: $('#txApemat').val(),
                              nivelUsuario: $("#cboNivel option:selected").text(),
                              gradoUsuario: $("#cboGrado option:selected").text(),
                              codigoGrado: $('#cboGrado').val(),
                              sedeUsuario: $("#cboSede option:selected").text(),
                              claveUsuario: $('#txtClave').val() === undefined ? '' : $('#txtClave').val(),
                              rolUsuario: $('#cboRol').val()
                           }
                           loader.iniciarLoader()
                           httpRequest.validarUsuario(json)
                                   .then((data) => {
                                      if (!data.status) {
                                         msnError('El usuario ingresado ya existe', () => {
                                            $('#btnNuevo').click()
                                         })
                                      } else {
                                         msnConfirm(" ¿Está seguro de registrar este usuario? ", (option) => {
                                            if (option) {
                                               loader.iniciarLoader()
                                               httpRequest.nuevoUsuario(json)
                                                       .then((data) => {
                                                          if (data.status) {
                                                             helpers.tblUsuariosRefresh()
                                                             msnSuccess("Usuario correctamente registrado")
                                                          } else {
                                                             if ((data.message).includes('--->')) {
                                                                msnError("Ha ocurrido un error")
                                                             } else {
                                                                msnError(data.message)
                                                             }
                                                          }
                                                       })
                                                       .catch(err => console.log(err))
                                            }
                                         })
                                      }
                                   })
                                   .catch(err => console.log(err))
                        } else {
                           return false
                        }
                     }
                  },
                  no: {
                     label: "<i class='fa fa-times bigger-110'></i>&nbsp; Cancelar",
                     className: "btn bg-danger border-danger-600 btn-xs",
                     callback: () => {
                     }
                  }
               }
            })
            bbxCrearUsuario.init(() => {
               this.generarCorreo()
               initComponents.initAlfiValidate()
               elementLoader.iniciarLoader('cboSede')
               initRequest.cargarSede()
               elementLoader.iniciarLoader('cboNivel')
               initRequest.cargarNivel()
               this.checkSelects()
            })
         })
      },
      generarCorreo() {
         $('#cboRol').on('change', () => {
            $('#cboSede').val('')
            $('#cboNivel').val('')
            $('#cboGrado').val('')
            let rol = $('#cboRol').val();
            if (rol === '') {
               $('.rowCbo').addClass('hide')
               $('#txtDni').val("");
               $('#txtCorreo').val("");
               $('#txtCorreo').attr('disabled', true);
               $('#txtCorreo').attr('placeholder', 'Correo Autogenerado');
               correo = 'Correo Autogenerado';
            } else if (rol === 'DIRECTOR PRIMARIA' || rol === 'DIRECTOR SECUNDARIA') {
               $('.colSede').removeClass('hide')
               $('.colNivel').addClass('hide')
               $('.colGrado').addClass('hide')
               $('.rowCbo').removeClass('hide')
               $('#txtCorreo').val("");
               $('#txtDni').val("");
               $('#txtCorreo').attr('disabled', false);
               $('#txtCorreo').attr('placeholder', 'Ingrese Correo');
            } else if (rol === 'ESTUDIANTE' || rol === 'DOCENTE') {
               if (rol === 'DOCENTE') {
                  $('.rowCbo').addClass('hide')
               } else {
                  $('.colSede').removeClass('hide')
                  $('.colNivel').removeClass('hide')
                  $('.rowCbo').removeClass('hide')
               }
               $('#txtDni').val("");
               $('#txtCorreo').val("");
               $('#txtCorreo').attr('disabled', true);
               $('#txtCorreo').attr('placeholder', 'Correo Autogenerado');
            }
         });
         $('#txtDni').keydown(() => {
            let dni = $('#txtDni').val();
            let rol = $('#cboRol').val();
            if (rol === 'ESTUDIANTE' || rol === 'DOCENTE') {
               if (dni.length >= 1) {
                  let letraRol = $("#cboRol option:selected").text().substring(1, 0).toLowerCase();
                  correo = letraRol + dni + "@sistemahelicoidal.edu.pe";
                  $('#txtCorreo').val(correo);
               } else {
                  $('#txtCorreo').val("");
               }
            }
         });
         $('#txtDni').keyup(() => {
            let dni = $('#txtDni').val();
            let rol = $('#cboRol').val();
            if (rol === 'ESTUDIANTE' || rol === 'DOCENTE') {
               if (dni.length >= 1) {
                  let letraRol = $("#cboRol option:selected").text().substring(1, 0).toLowerCase();
                  correo = letraRol + dni + "@sistemahelicoidal.edu.pe";
                  $('#txtCorreo').val(correo);
               } else {
                  $('#txtCorreo').val("");
               }
            }
         });
      },
      autoClickSearch() {
         $('#txtDni').keypress((e) => {
            if (e.keyCode === 13) {
               $('#btnBuscar').click()
            }
         })
      },
      checkedAll() {
         $('.ckTitle').click((e) => {
            let thisNode = e.currentTarget;
            if (thisNode.checked) {
               $('.ckItem').each((i, v) => {
                  if (!v.checked) {
                     v.click()
                  }
               })
            } else {
               $('.ckItem').each((i, v) => {
                  if (v.checked) {
                     v.click()
                  }
               })
            }
         })
      },
      pushCheckedItem() {
         $('#tblUsuarios tbody').on('click', '.ckItem', (e) => {
            let thisNode = e.currentTarget
            let codigoItem = $(thisNode).val()
            if (thisNode.checked) {
               selectedItem.push(codigoItem);
            } else {
               let itemChecked = $('.ckItem').filter(':checked').length
               if (itemChecked > 0 && $('.ckTitle').prop('checked') || $('.ckTitle').parent().hasClass('checked')) {
                  $('.ckTitle').parent().removeClass('checked')
               }
               selectedItem.forEach((v, i) => {
                  if (v === codigoItem) {
                     selectedItem.splice(i, 1);
                  }
               })
            }
            if (selectedItem.length > 0) {
               $('#btnModificar').removeClass('hide');
               $('#btnNuevo').addClass('hide');
            } else {
               $('#btnModificar').addClass('hide');
               $('#btnNuevo').removeClass('hide');
            }
         })
      },
      editarUsuarios() {
         $('#btnModificar').click(() => {
            let bbxEditarMasivo = bootbox.dialog({
               title: "<span>EDITAR USUARIOS</span>",
               headerClass: "modal-bg-primary",
               className: "bbxEditarUsuarios",
               size: "medium",
               message: `<form id="formEditarMasivo" onsubmit="return false" autocomplete="off">
                                    <div class="row">
                                        <div class="colNivel col-md-6">
                                            <div class="form-group">                                          
                                                <label>Nivel: <span class="asterisk">(*)</span></label>
                                                <select class="form-control" id="cboNivel" name="cboNivel">
                                                    <option value="">[SELECCIONE]</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="colGrado col-md-6">
                                            <div class="form-group">
                                                <label>Grado: <span class="asterisk">(*)</span></label>
                                                <select class="form-control" id="cboGrado" name="cboGrado">
                                                    <option value="">[SELECCIONE]</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group" style="margin-bottom: 0px;">
                                            <div class="text-right"> 
                                                <span class="asterisk">(*)</span>  Campo obligatorio
                                            </div>
                                        </div>
                                    </div>
                                </div>`,
               buttons: {
                  si: {
                     label: "<i class='fa fa-check bigger-110'></i>&nbsp; Editar",
                     className: "btn bg-success border-success-600 btn-xs",
                     callback: () => {
                        let valEditarMasivo = $("#formEditarMasivo").validate({
                           rules: {
                              cboNivel: {required: true},
                              cboGrado: {
                                 required: true,
                                 selectnic: true
                              }
                           },
                           messages: {
                              cboNivel: {required: "<b>&times;</b>&nbsp;Este campo es requerido"},
                              cboGrado: {required: "<b>&times;</b>&nbsp;Este campo es requerido"}
                           }
                        });
                        if (valEditarMasivo.form()) {
                           let json = {
                              listaEstudiantes: selectedItem,
                              codigoGradoFinal: $('#cboGrado').val(),
                              codigoGradoInicial: selectedGroup.codGradoUsuario
                           }
                           msnConfirm(`¿Está seguro de editar estos ${selectedItem.length} estudiantes?`, (option) => {
                              if (option) {
                                 loader.iniciarLoader()
                                 httpRequest.editarGradoMasivo(json)
                                         .then((data) => {
                                            if (data.status) {
                                               helpers.tblUsuariosRefresh()
                                               msnSuccess(`<div class="row" style="margin-bottom: 8px;">
                                                                        <div class="col-md-12">
                                                                                <div>La operación fue procesada satisfactoriamente: </div>
                                                                        </div>
                                                                  </div>
                                                                  <div class="row" style="margin-bottom: 5px;">
                                                                        <div class="col-md-2">
                                                                              <span class="label bg-slate border-slate-600" >${data.data.correctos.length}</span>
                                                                        </div>
                                                                        <div class="col-md-5">
                                                                              <span class="label bg-success border-success-600" style="color: white">CORRECTOS</span>
                                                                        </div>
                                                                        <div class="col-md-5">
                                                                              <a class="text-size-small pull-right" id="detallesCorrectos"><i class="fa fa-angle-double-right">Ver detalles</i></a>
                                                                        </div>
                                                                  </div>      
                                                                  <div class="row" style="margin-bottom: 0px;">
                                                                        <div class="col-md-2">
                                                                              <span class="label bg-slate border-slate-600">${data.data.incorrectos.length} </span>
                                                                        </div>
                                                                        <div class="col-md-5">
                                                                              <span class="label bg-danger border-danger-600" style="color: white">INCORRECTOS</span>
                                                                        </div>
                                                                        <div class="col-md-5 ${data.data.incorrectos.length === 0 ? 'hide' : ''}">
                                                                              <a class="text-size-small pull-right" id="detallesIncorrectos"><i class="fa fa-angle-double-right">Ver detalles</i></a>
                                                                        </div>
                                                                  </div>`,
                                                       () => {
                                                  selectedItem = []
                                               }, () => {
                                                  $('#detallesCorrectos').click(() => {
                                                     this.showDetail('CORRECTOS', data.data.correctos)
                                                  })
                                                  $('#detallesIncorrectos').click(() => {
                                                     this.showDetail('INCORRECTOS', data.data.incorrectos)
                                                  })
                                               })
                                            } else {
                                               msnError(`${data.message}`)
                                            }
                                         })
                                         .catch(err => console.log(err))
                              } else {
                                 $('#btnModificar').click()
                              }
                           })
                        } else {
                           return false
                        }
                     }
                  },
                  no: {
                     label: '<i class="fa fa-times" bigger-110></i>&nbsp; Cancelar',
                     className: 'btn bg-danger border-danger-600 btn-xs',
                     callback: () => {

                     }
                  }
               }
            })
            bbxEditarMasivo.init(() => {
               elementLoader.iniciarLoader('cboNivel')
               initRequest.cargarNivelDataMasivo(selectedGroup.nivelUsuario)
               DOMEvents.checkSelectsDataMasivo(selectedGroup.codGradoUsuario)
            })
         })
      },
      showDetail(type, data) {
         $('.dialogExtraSmall').addClass('hide')
         let listaDetalle = bootbox.dialog({
            title: "<b><i class='ace-icon fa fa-tags'></i> <span class=''>&nbsp; DETALLE DE OPERACIÓN</span></b>",
            headerClass: `modal-bg-${type === 'INCORRECTOS' ? 'danger' : 'success'}`,
            size: "medium",
            className: 'bbxDetalle',
            message: `<div class="panel panel-${type === 'INCORRECTOS' ? 'danger' : 'success'}">
                                    <div class="panel-heading" style="padding: 8px 15px">
                                          <h6 class="panel-title" style="font-size: 15px; font-family: inherit"><i class="fa fa-list"></i>&nbsp; REGISTROS ${type}</h6>                    
                                      </div>                         
                            <div class="table-responsive">
                                <table class="table display dataTable table-striped table-hover table-sm table-bordered" id="tblDetalle">                                                 
                                    <thead class="">
                                        <tr>
                                            <th class="text-center">Nº</th>
                                            <th class="text-center">NOMBRE</th>
                                            <th class="text-center">APELLIDOS</th>                                                     
                                            <th class="text-center">DNI</th>                                                     
                                        </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>`
         })
         listaDetalle.init(() => {
            let json = {
               codigosUsuarios: data.toString()
            }
            $('.bootbox-close-button').removeClass('hide')
            this.removeHideMsn()
            initComponents.listarDetalle(json)
         })
      },
      removeHideMsn() {
         $('.bootbox-close-button').click(() => {
            $('.dialogExtraSmall').removeClass('hide')
            $('.bootbox-close-button').addClass('hide')
         })
      }
   }

   const initRequest = {
      cargarSede() {
         httpRequest.listarSede()
                 .then((data) => {
                    if (data.status) {
                       let print = " <option value=''>[SELECCIONE]</option> "
                       let lista = data.results
                       for (let i in lista) {
                          print += " <option value ='" + lista[i].codigoSede + "' " + "" + ">" + lista[i].nombreSede + "</option> "
                       }
                       $("#cboSede").html(print)
                    } else {
                       let print = " <option  value=''>No hay contenido</option> "
                       $("#cboSede").html(print)
                    }
                 })
                 .catch(err => console.log(err))
      },
      cargarNivel() {
         httpRequest.listarNivel()
                 .then((data) => {
                    if (data.status) {
                       let print = " <option value=''>[SELECCIONE]</option> "
                       let lista = data.results
                       for (let i in lista) {
                          print += " <option value ='" + lista[i].codigoNivel + "' " + "" + ">" + lista[i].nombreNivel + "</option> "
                       }
                       $("#cboNivel").html(print)
                    } else {
                       let print = " <option  value=''>No hay contenido</option> "
                       $("#cboNivel").html(print)
                    }
                 })
                 .catch(err => console.log(err))
      },
      cargarGrado() {
         let json = {
            parent: $("#cboNivel").val()
         }
         httpRequest.listarGrado(json)
                 .then((data) => {
                    if (data.status) {
                       let print = " <option value=''>[SELECCIONE]</option> "
                       let lista = data.results
                       for (let i in lista) {
                          print += " <option value ='" + lista[i].codigoGrado + "' " + "" + ">" + lista[i].nombreGrado + "</option> "
                       }
                       $("#cboGrado").html(print)
                    } else {
                       let print = " <option  value=''>No hay contenido</option> "
                       $("#cboGrado").html(print)
                    }
                 })
                 .catch(err => console.log(err))
      },
      cargarNivelBusqueda() {
         httpRequest.listarNivel()
                 .then((data) => {
                    if (data.status) {
                       let print = " <option value=''>[SELECCIONE]</option> "
                       let lista = data.results
                       for (let i in lista) {
                          if (lista[i].codigoNivel !== 3) {
                             print += " <option value ='" + lista[i].codigoNivel + "' " + "" + ">" + lista[i].nombreNivel + "</option> "
                          }
                       }
                       $("#cboNivelBusqueda").html(print)
                    } else {
                       let print = " <option  value=''>No hay contenido</option> "
                       $("#cboNivelBusqueda").html(print)
                    }
                 })
                 .catch(err => console.log(err))
      },
      cargarGradoBusqueda() {
         let json = {
            parent: $("#cboNivelBusqueda").val()
         }
         httpRequest.listarGrado(json)
                 .then((data) => {
                    if (data.status) {
                       let print = " <option value=''>[SELECCIONE]</option> "
                       let lista = data.results
                       for (let i in lista) {
                          print += " <option value ='" + lista[i].codigoGrado + "' " + "" + ">" + lista[i].nombreGrado + "</option> "
                       }
                       $("#cboGradoBusqueda").html(print)
                    } else {
                       let print = " <option  value=''>No hay contenido</option> "
                       $("#cboGradoBusqueda").html(print)
                    }
                 })
                 .catch(err => console.log(err))
      },
      cargarSedeData(nombre) {
         httpRequest.listarSede()
                 .then((data) => {
                    if (data.status) {
                       let print = " <option value=''>[SELECCIONE]</option> "
                       let lista = data.results
                       for (let i in lista) {
                          let selected = nombre === lista[i].nombreSede ? "selected=true" : ""
                          print += " <option value ='" + lista[i].codigoSede + "' " + " " + selected + " >" + lista[i].nombreSede + "</option> "
                       }
                       $("#cboSede").html(print)
                    } else {
                       let print = " <option  value=''>No hay contenido</option> "
                       $("#cboSede").html(print)
                    }
                 })
                 .catch(err => console.log(err))
      },
      cargarNivelData(nombre) {
         httpRequest.listarNivel()
                 .then((data) => {
                    if (data.status) {
                       let print = " <option value=''>[SELECCIONE]</option> "
                       let lista = data.results
                       for (let i in lista) {
                          let selected = capitalizeWords(nombre) === lista[i].nombreNivel ? "selected=true" : ""
                          print += " <option value ='" + lista[i].codigoNivel + "' " + " " + selected + " >" + lista[i].nombreNivel + "</option> "
                       }
                       $("#cboNivel").html(print)
                       $("#cboNivel").change()
                    } else {
                       let print = " <option  value=''>No hay contenido</option> "
                       $("#cboNivel").html(print)
                    }
                 })
                 .catch(err => console.log(err))
      },
      cargarNivelDataMasivo(nombre) {
         httpRequest.listarNivel()
                 .then((data) => {
                    if (data.status) {
                       let print = " <option value=''>[SELECCIONE]</option> "
                       let lista = data.results
                       for (let i in lista) {
                          if (lista[i].codigoNivel !== 3) {
                             let selected = capitalizeWords(nombre) === lista[i].nombreNivel ? "selected=true" : ""
                             print += " <option value ='" + lista[i].codigoNivel + "' " + " " + selected + " >" + lista[i].nombreNivel + "</option> "
                          }
                       }
                       $("#cboNivel").html(print)
                       $("#cboNivel").change()
                    } else {
                       let print = " <option  value=''>No hay contenido</option> "
                       $("#cboNivel").html(print)
                    }
                 })
                 .catch(err => console.log(err))
      },
      cargarGradoDataMasivo(codigo) {
         let json = {
            parent: $("#cboNivel").val()
         }
         httpRequest.listarGrado(json)
                 .then((data) => {
                    if (data.status) {
                       let print = " <option value=''>[SELECCIONE]</option> "
                       let lista = data.results
                       for (let i in lista) {
                          let selected = capitalizeWords(codigo) === lista[i].codigoGrado.toString() ? "selected=true" : ""
                          print += " <option value ='" + lista[i].codigoGrado + "' " + " " + selected + " >" + lista[i].nombreGrado + "</option> "
                       }
                       $("#cboGrado").html(print)
                    } else {
                       let print = " <option  value=''>No hay contenido</option> "
                       $("#cboGrado").html(print)
                    }
                 })
                 .catch(err => console.log(err))
      },
      cargarGradoData(nombre) {
         let json = {
            parent: $("#cboNivel").val()
         }
         httpRequest.listarGrado(json)
                 .then((data) => {
                    if (data.status) {
                       let print = " <option value=''>[SELECCIONE]</option> "
                       let lista = data.results
                       for (let i in lista) {
                          let selected = capitalizeWords(nombre) === lista[i].nombreGrado ? "selected=true" : ""
                          print += " <option value ='" + lista[i].codigoGrado + "' " + " " + selected + " >" + lista[i].nombreGrado + "</option> "
                       }
                       $("#cboGrado").html(print)
                    } else {
                       let print = " <option  value=''>No hay contenido</option> "
                       $("#cboGrado").html(print)
                    }
                 })
                 .catch(err => console.log(err))
      }
   }

   const initComponents = {
      init() {
         this.listarUsuarios()
         this.initTableComponents()
         this.initAlfiValidate()
         this.addMethodVal()
      },
      listarUsuarios() {
         let visible = false
         json = {}
         if ($('#txtDniBusqueda').val() !== "" && $('#cboTipoBusqueda').val().includes('div-busq-dni')) {
            json.dniUsuario = $('#txtDniBusqueda').val()
         }
         if ($('#cboGradoBusqueda').val() !== "" && $('#cboTipoBusqueda').val().includes('div-busq-grado')) {
            json.codigoGrado = $('#cboGradoBusqueda').val()
            selectedGroup.nivelUsuario = $("#cboNivelBusqueda option:selected").text()
            selectedGroup.gradoUsuario = $("#cboGradoBusqueda option:selected").text()
            selectedGroup.codGradoUsuario = $("#cboGradoBusqueda").val()
            visible = true
         }
         if ($('#txtNombreBusqueda').val() !== "" && $('#cboTipoBusqueda').val().includes('div-busq-nombre')) {
            json.nombreUsuario = $('#txtNombreBusqueda').val()
         }
         if ($('#txtApellidoBusqueda').val() !== "" && $('#cboTipoBusqueda').val().includes('div-busq-apellido')) {
            json.apellidoUsuario = $('#txtApellidoBusqueda').val()
         }
         if ($('#cboEstadoBusqueda').val() !== "" && $('#cboTipoBusqueda').val().includes('div-busq-estado')) {
            json.estadoUsuario = $('#cboEstadoBusqueda').val()
         }
         if ($('#cboRolBusqueda').val() !== "" && $('#cboTipoBusqueda').val().includes('div-busq-rol')) {
            json.rolUsuario = $('#cboRolBusqueda').val()
         }
         tableUsuarios = $("#tblUsuarios").DataTable({
            "language": {
               "lengthMenu": "Mostrar: _MENU_",
               "zeroRecords": "&nbsp;&nbsp;&nbsp; No se encontraron resultados",
               "info": "&nbsp;&nbsp;&nbsp; Mostrando del _START_ al _END_ de un total de _TOTAL_ registros",
               "infoEmpty": "&nbsp;&nbsp;&nbsp; Mostrando 0 de 0 registros",
               "search": "Filtrar:",
               "loadingRecords": "Cargando...",
               "processing": '<span style="width:100%;"><img src="../../img/ajax-loader-img.gif" alt=""/></span>',
               "paginate": {
                  "first": "First",
                  "last": "Last",
                  "next": "Siguiente",
                  "previous": "Anterior"
               }
            },
            dom: '<"datatable-scroll"t>r<"datatable-footers"ip>',
            "ajax": {
               type: 'POST',
               url: "../UsuarioServlet?accion=listarUsuarios",
               data: {
                  json: JSON.stringify(json)
               }
            },
            "bSort": false,
            "bFilter": false,
            "serverSide": true,
            "processing": true,
            "aaSorting": [],
            "ordering": false,
            "bLengthChange": false,
            "bInfo": true,
            "paging": true,
            "iDisplayLength": visible ? 50 : 20,
            "columnDefs": [
               {targets: 0, orderable: false, width: "5%", className: "text-center", "visible": visible},
               {targets: 1, orderable: false, width: "5%", className: "text-center", "visible": !visible},
               {targets: 2, orderable: false, width: "20%"},
               {targets: 3, orderable: false, width: "15%", className: "text-center"},
               {targets: 4, orderable: false, width: "15%"},
               {targets: 5, orderable: false, width: "10%", className: "text-center"},
               {targets: 6, orderable: false, width: "10%", className: "text-center"},
               {targets: 7, orderable: false, width: "10%", className: "text-center"}
            ],
            "columns": [
               {'data': null, 'render': (data, type, row) => {
                     let checked = ''
                     if (selectedItem.includes(row.codigoUsuario.toString())) {
                        checked = 'checked'
                        checks++
                     }
                     return `<input type="checkbox" class="ckItem" value="${row.codigoUsuario}" ${checked}>`
                  }
               },
               {"data": "numeral"},
               {"data": null, "render": (data, type, row) => {
                     return capitalizeWords(row.nombreUsuario + ' ' + row.apellidosUsuario);
                  }
               },
               {"data": "dniUsuario"},
               {"data": "correoUsuario"},
               {"data": "rolUsuario", "render": (data, type, row) => {
                     return capitalizeWords(data);
                  }
               },
               {"data": "estadoUsuario", "render": (data, type, row) => {
                     if (data === 1) {
                        return `<span class="label label-success" style="color: white">ACTIVO</span>`
                     }
                     return `<span class="label label-danger" style="color: white">INACTIVO</span>`
                  }
               },
               {"data": "estadoUsuario", "render": (data, type, row) => {
                     let actions = "";
                     actions += `<span data-toggle="tooltip" data-placement="left" title="Editar" style="cursor: pointer"> <i class='editar click${row.numeral} icon-pencil text-blue-800'></i>&nbsp;&nbsp; </span>`
                     actions += `<span data-toggle="tooltip" data-placement="left" title="${data === 1 ? 'Desactivar' : 'Activar'}" style="cursor: pointer"> <i class='estado icon-switch2 text-${data === 1 ? 'danger' : 'success'}-800'></i>&nbsp;&nbsp; </span>`
                     actions += `<span data-toggle="tooltip" data-placement="left" title="Asignar" style="cursor: pointer"> <i class='asignar  icon-cog2 text-slate-700'></i>&nbsp;&nbsp; </span>`
                     actions += `<span data-toggle="tooltip" data-placement="left" title="Eliminar" style="cursor: pointer"> <i class='eliminar glyphicon glyphicon-trash text-danger-700'></i>&nbsp;&nbsp; </span>`
                     return actions;
                  }
               }
            ],
            fnDrawCallback: () => {
               loader.terminarLoader();
            }
         });
      },
      listarDetalle(json) {
         tblDetalle = $("#tblDetalle").DataTable({
            "language": {
               "lengthMenu": "Mostrar: _MENU_",
               "zeroRecords": "&nbsp;&nbsp;&nbsp; No se encontraron resultados",
               "info": "&nbsp;&nbsp;&nbsp; Mostrando del _START_ al _END_ de un total de _TOTAL_ registros",
               "infoEmpty": "&nbsp;&nbsp;&nbsp; Mostrando 0 de 0 registros",
               "search": "Filtrar:",
               "loadingRecords": "Cargando...",
               "processing": '<span style="width:100%;"><img src="../../img/ajax-loader-img.gif"></span>',
               "paginate": {
                  "first": "First",
                  "last": "Last",
                  "next": "Siguiente",
                  "previous": "Anterior"
               }
            },
            dom: '<"datatable-scroll"t>r<"datatable-footers"ip>',
            "ajax": {
               type: 'POST',
               url: "../UsuarioServlet?accion=listarDetalle",
               data: {
                  json: JSON.stringify(json)
               }, beforeSend: (xhr) => {
                  elementLoader.iniciarLoader('bbxDetalle')
               }
            },
            "bSort": false,
            "bFilter": false,
            "serverSide": true,
            "processing": true,
            "aaSorting": [],
            "ordering": false,
            "bLengthChange": false,
            "bInfo": true,
            "paging": true,
            "iDisplayLength": 10,
            "columnDefs": [
               {targets: 0, orderable: false, width: "5%", className: "text-center"},
               {targets: 1, orderable: false, width: "15%"},
               {targets: 2, orderable: false, width: "15%"},
               {targets: 3, orderable: false, width: "25%", className: "text-center"}
            ],
            "columns": [
               {"data": "numeral"},
               {'data': 'nombreUsuario', render: (data, type, row) => {
                     return capitalizeWords(data)
                  }
               },
               {'data': 'apellidoUsuario', render: (data, type, row) => {
                     return capitalizeWords(data)
                  }
               },
               {'data': 'correoUsuario'}
            ],
            fnDrawCallback: () => {
               elementLoader.terminarLoader('bbxDetalle')
            }
         });
      },
      initAlfiValidate() {
         new Input({el: '#txtDni', param: 'numbers'}).validate()
         new Input({el: '#txtApepat', param: 'letters'}).validate()
         new Input({el: '#txApemat', param: 'letters'}).validate()
         new Input({el: '#txtNombre', param: 'letters'}).validate()
         new Input({el: '#txtCorreo', param: 'email'}).validate()
         new Input({el: '#txtDni', param: 'numbers'}).validate()
      },
      initTableComponents() {
         $('#tblUsuarios').on('draw.dt', () => {
            $('[data-toggle="tooltip"]').tooltip();
            $('.ckItem, .ckTitle').uniform()
            DOMEvents.checkedAll()
         })
      },
      chargeDataBootbox(data) {
         let apellidos = data.apellidosUsuario.split(' ')
         $('#cboRol').val(data.rolUsuario)
         $('#cboTipo').val((data.tipoUsuario).toUpperCase())
         $('#cboRol').change()
         $('#txtDni').val(data.dniUsuario)
         $('#txtCorreo').val(data.correoUsuario)
         $('#txtClave').val(data.claveUsuario)
         $('#txtApepat').val(capitalizeWords(apellidos[0]))
         $('#txApemat').val(capitalizeWords(apellidos.length > 2 ? apellidos.slice(1, apellidos.length).join(' ') : apellidos[1]))
         $('#txtNombre').val(capitalizeWords(data.nombreUsuario))
      },
      addMethodVal() {
         jQuery.validator.addMethod("selectnic", (value, element) => {
            if (value === selectedGroup.codGradoUsuario) {
               return false
            }
            return true
         }, "<b>&times;</b>&nbsp; No puede seleccionar el mismo grado")
      }
   }

   initialize = () => {
      initRequest.cargarNivelBusqueda()
      loader.iniciarLoader()
      initComponents.init()
      DOMEvents.init()
   }

   initialize()

   $('#cboTipoBusqueda').multiselect({
      numberDisplayed: 1
   });
   $(".styled, .multiselect-container input").uniform({radioClass: 'choice'})
   $(":checkbox:regex(value, ^div-busq)").change((e) => {
      let target = e.currentTarget
      let options = $('#cboTipoBusqueda').val()
      let length = $('#cboTipoBusqueda option:selected').length
      if (length >= 1) {
         $('div:regex(id, ^div-busq)').addClass('hide')
         for (let i in options) {
            $('#' + options[i]).removeClass('hide')
         }
      } else {
         $(target).parent().addClass('checked')
         $("option[value='" + $(target).val() + "']").prop("selected", true)
         $("#cboTipoBusqueda").multiselect("refresh");
      }
   })
