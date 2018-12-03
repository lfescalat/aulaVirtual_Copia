   /* global bootbox, Cookies */

   const CONSTANTES = Object.freeze({
      PATH_GENERAL: 'http://localhost:8080/aulaVirtual/',
      PATH_IP: 'http://172.16.2.40:8080/aulaVirtual/',
      CODIGO_PROYECTO_MATRICULA: '18',
      PATH_SERVICIO_REST: 'http://app9.sacooliveros.edu.pe:8080/security-rest/api/'
   })

   let getYearFooter = (footer) => {
      var date = new Date();
      var year = date.getFullYear();
      $(footer).append(year);
   };

   getYearFooter('#footerDate');

   const capitalizeWords = str => str.split(' ').map((word, i, arr) => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase()).join(' ')

   function msnSuccess(message, action, charge) {
      var final = action || function () {};
      let bbxSuccess = bootbox.dialog({
         className: "dialogExtraSmall",
         title: "<i class='ace-icon fa fa-tags white'></i> <span class='white'>Notificación</span>",
         headerClass: "modal-bg-success",
         message: message,
         buttons: {
            si: {
               label: "<i class='fa fa-check bigger-110'></i>&nbsp; Aceptar",
               className: "btn bg-green border-green-600 btn-xs",
               callback: function () {
                  final();
               }
            }
         }
      });
      if (charge !== undefined) {
         bbxSuccess.init(() => {
            charge()
         })
      }
   }

   function msnError(message, action) {
      var final = action || function () {};
      bootbox.dialog({
         className: "dialogExtraSmall",
         title: "<i class='ace-icon fa fa-exclamation-circle white'></i> <span class='white'>Advertencia</span>",
         headerClass: "modal-bg-warning",
         message: message,
         buttons: {
            si: {
               label: "<i class='fa fa-check bigger-110'></i>&nbsp; Aceptar",
               className: "btn bg-orange border-orange-600 btn-xs",
               callback: function () {
                  final();
               }
            }
         }
      });
   }

   function msnConfirm(message, action) {
      bootbox.dialog({
         className: "dialogExtraSmall",
         headerClass: "modal-bg-primary",
         title: "<i class='ace-icon fa fa-check-circle white'></i> <span class='white'>Conformidad</span>",
         message: message,
         buttons: {
            si: {
               label: "<i class='fa fa-check bigger-110'></i>&nbsp; Si",
               className: "btn-primary",
               callback: function () {
                  return action(true);
               }
            }, no: {
               label: "<i class='fa fa-times bigger-110'></i>&nbsp; No",
               className: "btn-danger",
               callback: function () {
                  return action(false);
               }
            }
         }
      });
   }


   function deleteAllCookies() {
      var cookies = document.cookie.split(";");
      for (var i = 0; i < cookies.length; i++) {
         var cookie = cookies[i];
         var eqPos = cookie.indexOf("=");
         var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
         document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
      }
   }

   function initTable(len) {
      var showChar = len || 25;
      var ellipsestext = "...";
      var moretext = " <i class='fa fa-plus text-success-700'></i> ";
      var lesstext = " <i class='fa fa-minus text-danger-700'></i> ";

      $('.more').each(function () {
         var content = $(this).html();
         if (content.length > showChar) {
            var c = content.substr(0, showChar);
            var h = content.substr(showChar, content.length - showChar);
            var html = c + '<span class="moreellipses">' + ellipsestext + '&nbsp;</span><span class="morecontent"><span>' + h + '</span>&nbsp;&nbsp;<a href="" class="morelink text-size-large">' + moretext + '</a></span>';
            $(this).html(html);
         }
      });
      $(".morelink").click(function () {
         if ($(this).hasClass("less")) {
            $(this).removeClass("less");
            $(this).html(moretext);
         } else {
            $(this).addClass("less");
            $(this).html(lesstext);
         }
         $(this).parent().prev().toggle();
         $(this).prev().toggle();
         return false;
      });
   }

   const ajaxRequestSendBody = obj => {
      let body = JSON.stringify(obj.body);
      return new Promise((resolve, reject) => {
         $.ajax({
            url: obj.url,
            type: obj.type,
            headers: obj.headers,
            data: {body: body},
            beforeSend: (xhr, settings) => {
            }, success: (response, textStatus, jqXHR) => {
               resolve(response)
            }, error: (jqXHR, textStatus, errorThrown) => {
               reject({
                  status: jqXHR.status,
                  throw: errorThrown || {},
                  jqXHR: jqXHR,
                  request: obj
               })
            }
         })
      })
   }

   let logOut = () => {
      $('.logOut').click(function () {
         window.location.href = '../vistas/logout';
      })
   }

   var elementLoader = {
      iniciarLoader: function (id) {
         $("#" + id).parent().block({
            message: '<i class="icon-spinner9 spinner position-left"></i>',
            overlayCSS: {
               backgroundColor: '#fff',
               opacity: 0.8,
               cursor: 'wait'
            },
            css: {
               border: 0,
               padding: 0,
               backgroundColor: 'transparent'
            }
         });
      },
      terminarLoader: function (id) {
         $("#" + id).parent().unblock();
      }
   };
   var loader = {
      iniciarLoader: function () {
         $.blockUI({
            message: '<i class="icon-spinner9 spinner position-left" style="font-size:25px"></i>',
            overlayCSS: {
               backgroundColor: '#1b2024',
               opacity: 0.95,
               cursor: 'wait'
            },
            css: {
               border: 0,
               color: '#fff',
               padding: 0,
               backgroundColor: 'transparent'
            }
         });
      },
      terminarLoader: function () {
         $.unblockUI();
      }
   };

   function load(msg) {
      $.blockUI({
         message: '<span style= "font-size:17px"><i class="icon-spinner9 spinner position-left" style="zoom:2"></i>&nbsp; ' + msg + '...</span>',
         overlayCSS: {
            backgroundColor: '#1b2024',
            opacity: 0.7,
            zIndex: 1200,
            cursor: 'wait'
         },
         css: {
            border: 0,
            color: '#fff',
            padding: 0,
            zIndex: 1201,
            backgroundColor: 'transparent'
         }
      });
   }
   function unload() {
      $.unblockUI();
   }

   $(window).resize(function () {
      const width = $(window).width();
      if (width < 1589 && width > 1199) {
         $('.col-centered').each(function (i, obj) {
            if ($(this).hasClass('col-lg-4')) {
               $(this).removeClass('col-lg-4')
               $(this).addClass('col-lg-6')
            } else if ($(this).hasClass('col-lg-8')) {
               $(this).removeClass('col-lg-8')
               $(this).addClass('col-lg-10')
            }
         });
      } else {
         $('.col-centered').each(function (i, obj) {
            if ($(this).hasClass('col-lg-6')) {
               $(this).removeClass('col-lg-6')
               $(this).addClass('col-lg-4')
            } else if ($(this).hasClass('col-lg-10')) {
               $(this).removeClass('col-lg-10')
               $(this).addClass('col-lg-8')
            }
         });
      }
      if (width < 959) {
         $('.pace-done').addClass('sidebar-xs')
      } else {
         $('.pace-done').removeClass('sidebar-xs')
      }
   });

   jQuery.expr[':'].regex = function (elem, index, match) {
      var matchParams = match[3].split(','),
              validLabels = /^(data|css):/,
              attr = {
                 method: matchParams[0].match(validLabels) ?
                         matchParams[0].split(':')[0] : 'attr',
                 property: matchParams.shift().replace(validLabels, '')
              },
              regexFlags = 'ig',
              regex = new RegExp(matchParams.join('').replace(/^\s+|\s+$/g, ''), regexFlags);
      return regex.test(jQuery(elem)[attr.method](attr.property));
   }