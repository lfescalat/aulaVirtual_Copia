   /* global loader, dataRequest, elementLoader */

   let tableCursos
   let deletedItem = []

   const helpers = {
      tblCursosCharge() {
         loader.iniciarLoader()
         tableCursos.destroy()
         initComponents.listarCursos()
      },
      tblCursosRefresh() {
         loader.iniciarLoader()
         tableCursos.ajax.reload(null, false);
      }
   }

   const httpRequest = {
      listarCurso(json) {
         return new Promise((resolve, reject) => {
            $.ajax({
               type: 'POST',
               url: "../CursoServlet?accion=listarCurso",
               dataType: "json",
               data: {
                  json: JSON.stringify(json)
               },
               success: function (data, textStatus, jqXHR) {
                  elementLoader.terminarLoader('cboCurso')
                  resolve(data)
               }, error: function (jqXHR, textStatus, errorThrown) {
                  elementLoader.terminarLoader('cboCurso')
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
                  resolve(data)
               }, error: function (jqXHR, textStatus, errorThrown) {
                  elementLoader.terminarLoader('cboGrado')
                  reject('Error en la petición')
               }
            })
         })
      },
      eliminarCurso(json) {
         return new Promise((resolve, reject) => {
            $.ajax({
               type: 'POST',
               url: "../CursoServlet?accion=eliminarCurso",
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
      asignarCurso(json) {
         return new Promise((resolve, reject) => {
            $.ajax({
               type: 'POST',
               url: "../CursoServlet?accion=asignarCurso",
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
      eliminarCursos(json) {
         return new Promise((resolve, reject) => {
            $.ajax({
               type: 'POST',
               url: "../CursoServlet?accion=eliminarCursos",
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
         this.checkSelects()
         this.tblCursosEliminar()
         this.registrarCurso()
         this.limpiarRegistro()
         this.eliminarCursos()
      },
      checkSelects() {
         $('#cboNivel').change(() => {
            var cboNivel = $('#cboNivel').val()
            if (cboNivel !== '') {
               if (cboNivel !== '3') {
                  $('#cboGrado').attr('disabled', false)
                  elementLoader.iniciarLoader('cboGrado')
                  initRequest.cargarGrado()
               } else {
                  $("#cboGrado").attr('disabled', true)
                  $('#cboGrado').html('<option value="3" selected=true>Academias</option>')
                  $("#cboGrado").change()
               }
            } else {
               $("#cboGrado").attr('disabled', true)
               $('#cboGrado').val('')
            }
         })
         $('#cboGrado').change(() => {
            var cboGrado = $('#cboGrado').val()
            if (cboGrado !== '') {
               $('#cboCurso').attr('disabled', false)
               elementLoader.iniciarLoader('cboCurso')
               initRequest.cargarCurso()
            } else {
               $("#cboCurso").attr('disabled', true)
               $('#cboCurso').val('')
            }
         })
      },
      tblCursosEliminar() {
         $('#tblCursos tbody').on('click', '.eliminar', (e) => {
            let thisNode = e.currentTarget;
            var data = tableCursos.row($(thisNode).parents('tr')).data();
            msnConfirm('¿Está seguro de eliminar este curso?', (option) => {
               if (option) {
                  loader.iniciarLoader()
                  httpRequest.eliminarCurso(data)
                          .then((data) => {
                             if (data.status) {
                                helpers.tblCursosRefresh()
                                msnSuccess("Curso correctamente eliminado")
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
         })
      },
      registrarCurso() {
         $('#btnRegistrar').click(() => {
            var valRegCurso = $("#frmBusqRequ").validate({
               rules: {
                  cboNivel: {required: true},
                  cboGrado: {required: true},
                  cboCurso: {required: true}
               },
               messages: {
                  cboNivel: {required: "<b>&times;</b>&nbsp;Este campo es requerido"},
                  cboGrado: {required: "<b>&times;</b>&nbsp;Este campo es requerido"},
                  cboCurso: {required: "<b>&times;</b>&nbsp;Este campo es requerido"}
               }
            });
            if (valRegCurso.form()) {
               let json = {
                  codigoUsuario: dataRequest.codigoUsuario,
                  codigoGrado: $('#cboGrado').val(),
                  codigoCurso: $('#cboCurso').val()
               }
               loader.iniciarLoader()
               httpRequest.asignarCurso(json)
                       .then((data) => {
                          if (data.status) {
                             helpers.tblCursosRefresh()
                             msnSuccess("Curso(s) correctamente agregado(s)")
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
      },
      limpiarRegistro() {
         $('#btnLimpiar').click(() => {
            $('#cboNivel').val('')
            $('#cboGrado').val('')
            $('#cboCurso').val('')
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
         $('.ckItem').click((e) => {
            let thisNode = e.currentTarget
            let codigoItem = $(thisNode).val();
            if (thisNode.checked) {
               deletedItem.push(codigoItem);
            } else {
               let itemChecked = $('.ckItem').filter(':checked').length
               if (itemChecked > 0 && $('.ckTitle').prop('checked') || $('.ckTitle').parent().hasClass('checked')) {
                  $('.ckTitle').parent().removeClass('checked')
               }
               deletedItem.forEach((v, i) => {
                  if (v === codigoItem) {
                     deletedItem.splice(i, 1);
                  }
               })
            }
            if (deletedItem.length > 0) {
               $('#btnEliminar').removeClass('hide');
            } else {
               $('#btnEliminar').addClass('hide');
            }
         })
      },
      eliminarCursos() {
         $('#btnEliminar').click(() => {
            let json = {
               lista: deletedItem
            }
            msnConfirm(`¿Seguro de eliminar ${deletedItem.length} cursos?`, (option) => {
               if (option) {
                  loader.iniciarLoader()
                  httpRequest.eliminarCursos(json)
                          .then((data) => {
                             if (data.status) {
                                msnSuccess(`Cursos correctamente eliminados`, () => {
                                   helpers.tblCursosRefresh()
                                   deletedItem = []
                                })
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
         })
      }
   }

   const initRequest = {
      cargarNivel() {
         httpRequest.listarNivel()
                 .then((data) => {
                    if (data.status) {
                       var print = " <option value=''>[SELECCIONE]</option> "
                       var lista = data.results
                       for (var i in lista) {
                          print += " <option value ='" + lista[i].codigoNivel + "' " + "" + ">" + lista[i].nombreNivel + "</option> "
                       }
                       $("#cboNivel").html(print)
                    } else {
                       var print = " <option  value=''>No hay contenido</option> "
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
                       var print = " <option value=''>[SELECCIONE]</option> "
                       var lista = data.results
                       for (var i in lista) {
                          print += " <option value ='" + lista[i].codigoGrado + "' " + "" + ">" + lista[i].nombreGrado + "</option> "
                       }
                       $("#cboGrado").html(print)
                    } else {
                       var print = " <option  value=''>No hay contenido</option> "
                       $("#cboGrado").html(print)
                    }
                 })
                 .catch(err => console.log(err))
      },
      cargarCurso() {
         let json = {
            parent: $("#cboGrado").val()
         }
         httpRequest.listarCurso(json)
                 .then((data) => {
                    if (data.status) {
                       var print = " <option value=''>[SELECCIONE]</option> "
                       print += " <option  value='0' class='text-semibold'>TODOS</option> "
                       var lista = data.results
                       for (var i in lista) {
                          print += " <option value ='" + lista[i].codigoCurso + "' " + "" + ">" + lista[i].nombreCurso + "</option> "
                       }
                       $("#cboCurso").html(print)
                    } else {
                       var print = " <option  value=''>No hay contenido</option> "
                       $("#cboCurso").html(print)
                    }
                 })
                 .catch(err => console.log(err))
      }
   }

   const initComponents = {
      init() {
         this.listarCursos()
         this.initTableComponents()
         this.chargeDataUser()
      },
      listarCursos() {
         let checks = 0
         json = {
            codigoUsuario: dataRequest.codigoUsuario
         }
         tableCursos = $("#tblCursos").DataTable({
            "language": {
               "lengthMenu": "Mostrar: _MENU_",
               "zeroRecords": "&nbsp;&nbsp;&nbsp; No se encontraron resultados",
               "info": "&nbsp;&nbsp;&nbsp; Mostrando del _START_ al _END_ de un total de _TOTAL_ registros",
               "infoEmpty": "&nbsp;&nbsp;&nbsp; Mostrando 0 de 0 registros",
               "search": "Filtrar:",
               "loadingRecords": "Cargando...",
               "processing": '<span style="width:100%;"><img src="http://www.snacklocal.com/images/ajaxload.gif"></span>',
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
               url: "../CursoServlet?accion=listarCursos",
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
            "iDisplayLength": 15,
            "columnDefs": [
               {targets: 0, orderable: false, width: "5%", className: "text-center"},
               {targets: 1, orderable: false, width: "5%", className: "text-center"},
               {targets: 2, orderable: false, width: "15%"},
               {targets: 3, orderable: false, width: "15%"},
               {targets: 4, orderable: false, width: "7%", className: "text-center"}
            ],
            "columns": [
               {'data': null, 'render': (data, type, row) => {
                     let checked = ''
                     if (deletedItem.includes(row.codigoCurso.toString())) {
                        checked = 'checked'
                        checks++
                     }
                     return `<input type="checkbox" class="ckItem" value="${row.codigoCurso}" ${checked}>`
                  }
               },
               {"data": "numeral"},
               {"data": "nombreGrado", "render": (data, type, row) => {
                     return capitalizeWords(data);
                  }
               },
               {"data": "nombreCurso", "render": (data, type, row) => {
                     return capitalizeWords(data)
                  }
               },
               {"data": null, "render": (data, type, row) => {
                     var actions = "";
                     actions += `<span data-toggle="tooltip" data-placement="left" title="Eliminar" style="cursor: pointer"> <i class='eliminar glyphicon glyphicon-trash text-danger-700'></i></span>`
                     return actions;
                  }
               }
            ],
            fnDrawCallback: () => {
               loader.terminarLoader()
            }
         });
      },
      initTableComponents() {
         $('#tblCursos').on('draw.dt', () => {
            $('[data-toggle="tooltip"]').tooltip();
            $('.ckItem').uniform()
            DOMEvents.checkedAll()
            DOMEvents.pushCheckedItem()
         })
      },
      chargeDataUser() {
         $('#dataUser').text(`${dataRequest.apellidosUsuario !== '' ? dataRequest.apellidosUsuario + ',' : ''} ${dataRequest.nombreUsuario !== '' ? dataRequest.nombreUsuario + ' /' : ''} ${dataRequest.dniUsuario !== '' ? dataRequest.dniUsuario : ''}`)
      }
   }

   initialize = () => {
      loader.iniciarLoader()
      initComponents.init()
      elementLoader.iniciarLoader('cboNivel')
      initRequest.cargarNivel()
      DOMEvents.init()
      $('.ckTitle').uniform()
   }

   initialize()