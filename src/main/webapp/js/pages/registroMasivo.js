   /* global Papa, loader, bootbox, elementLoader */

   const helpers = {
      processList(list) {
         for (let index in list) {
            let params = list[index]
            let arrayCourses = []
            for (let key in params) {
               if (params.hasOwnProperty(key)) {
                  if (params[key] !== "") {
                     if (key.search(`course`) !== -1) {
                        arrayCourses.push(params[key])
                        delete params[key]
                     }
                  } else {
                     delete params[key]
                  }
               }
            }
            if (arrayCourses.length > 0) {
               params.courses = arrayCourses
            }
            if (Object.keys(params).length <= 0) {
               list.splice(index, index)
            }
         }
         return list
      }
   }

   const httpRequest = {
      registroMasivo(json) {
         return new Promise((resolve, reject) => {
            $.ajax({
               type: 'POST',
               url: "../UsuarioServlet?accion=registroMasivo",
               dataType: "json",
               data: {
                  json: JSON.stringify(json)
               },
               beforeSend: function (xhr) {
                  loader.iniciarLoader()
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
         this.clearPage()
         this.loadFile()
      },
      removeHideMsn() {
         $('.bootbox-close-button').click(() => {
            $('.modal-bodys').toggleClass('modal-body')
            $('.dialogExtraSmall').removeClass('hide')
            $('.bootbox-close-button').addClass('hide')
         })
      },
      loadFile() {
         $('#btnCargar').click(function () {
            let archivo;
            $('#filePrueba').parse({
               header: true,
               dynamicTyping: false,
               encoding: "ISO-8859-1",
               before: function (file) {
                  archivo = file;
               }
            });
            Papa.parse(archivo, {
               header: true,
               dynamicTyping: false,
               encoding: "ISO-8859-1",
               complete: function (results) {
                  let processed = helpers.processList(results.data)
                  httpRequest.registroMasivo(processed)
                          .then(data => {
                             let report = data.data
//                             $('#txtJson').html(JSON.stringify(results.data, undefined, 2))
                             msnSuccess(`<div class="row" style="margin-bottom: 8px;">
                                                                        <div class="col-md-12">
                                                                                <div>La operación fue procesada satisfactoriamente: </div>
                                                                        </div>
                                                                  </div>
                                                                  <div class="row" style="margin-bottom: 5px;">
                                                                        <div class="col-md-2">
                                                                              <span class="label bg-slate border-slate-600" >${report.corrects.length}</span>
                                                                        </div>
                                                                        <div class="col-md-5">
                                                                              <span class="label bg-success border-success-600" style="color: white">CORRECTOS</span>
                                                                        </div>
                                                                        <div class="col-md-5 ${report.corrects.length === 0 ? 'hide' : ''}">
                                                                              <a class="text-size-small pull-right" id="detallesCorrectos"><i class="fa fa-angle-double-right">Ver detalles</i></a>
                                                                        </div>
                                                                  </div>      
                                                                  <div class="row" style="margin-bottom: 0px;">
                                                                        <div class="col-md-2">
                                                                              <span class="label bg-slate border-slate-600">${report.errors.length} </span>
                                                                        </div>
                                                                        <div class="col-md-5">
                                                                              <span class="label bg-danger border-danger-600" style="color: white">INCORRECTOS</span>
                                                                        </div>
                                                                        <div class="col-md-5 ${report.errors.length === 0 ? 'hide' : ''}">
                                                                              <a class="text-size-small pull-right" id="detallesIncorrectos"><i class="fa fa-angle-double-right">Ver detalles</i></a>
                                                                        </div>
                                                                  </div>`,
                                     () => {
                                selectedItem = []
                             }, () => {
                                $('#detallesCorrectos').click(() => {
                                   initRequest.showDetail('CORRECTOS', report.corrects)
                                })
                                $('#detallesIncorrectos').click(() => {
                                   initRequest.showDetail('INCORRECTOS', report.errors)
                                })
                             })
                          })
                          .catch(err => console.log(err))
               }
            })
         })
      }
      ,
      clearPage() {
         $('#btnLimpiar').click(() => {
            $('#filePrueba').val('')
            $('#txtJson').html('')
         })
      }
   }

   const initRequest = {
      init() {

      },
      showDetail(type, data) {
         $('.dialogExtraSmall').addClass('hide')
         let listaDetalle = bootbox.dialog({
            title: "<b><i class='ace-icon fa fa-tags'></i> <span class=''>&nbsp; DETALLE DE OPERACIÓN</span></b>",
            headerClass: `modal-bg-${type === 'INCORRECTOS' ? 'danger' : 'success'}`,
            size: "medium",
            className: 'bbxDetalle',
            message: `
                                <table class="table display dataTable table-striped table-hover table-sm table-bordered" id="tblDetalle">                                                 
                                    <thead class="">
                                        <tr>
                                            <th class="text-center">Nº</th>
                                            <th class="text-center">USUARIO</th>                                                       
                                            <th class="text-center">MENSAJE</th>     
                                        </tr>
                                    </thead>
                                </table>`
         })
         listaDetalle.init(() => {
            $('.bootbox-close-button').removeClass('hide')
            $('.modal-body').addClass('modal-bodys')
            $('.modal-bodys').removeClass('modal-body')
            DOMEvents.removeHideMsn()
            initComponents.listarDetalle(data)
         })
      }
   }

   const initComponents = {
      init() {
      },
      listarDetalle(json) {
         let aux = 1
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
            dom: '<"datatable-scroll"t><"datatable-footers"ip>',
            data: json,
            "bSort": false,
            "bFilter": false,
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
               {targets: 2, orderable: false, width: "15%"}
            ],
            "columns": [
               {'data': null, render: () => {
                     return aux++
                  }
               },
               {'data': 'username'},
               {'data': 'message'}
            ],
            fnDrawCallback: () => {
               elementLoader.terminarLoader('bbxDetalle')
            }
         });
      }
   }

   init = () => {
      DOMEvents.init()
      initComponents.init()
      initRequest.init()
   }

   init()

   