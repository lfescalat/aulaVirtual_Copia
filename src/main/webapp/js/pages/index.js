   /* global CONSTANTES */

   const httpRequest = {
      login(params) {
         return ajaxRequestSendBody({
            url: '../vistas/login',
            type: 'POST',
            headers: {
               "Accept": 'application/json'
            },
            body: params
         })
      }
   }

   const DOMEvents = {
      init() {
         this.signIn()
      },
      signIn() {
         document.querySelector('#logIn').addEventListener('click', (e) => {
            validarBusq = $("#formLogin").validate({
               rules: {
                  user:
                          {required: true, maxlength: 50},
                  password:
                          {required: true, maxlength: 50}
               },
               messages: {
                  user:
                          {required: "&times; Ingrese su Usuario", maxlength: "&times; Máximo 50 letras"},
                  password:
                          {required: "&times; Ingrese su Contraseña", maxlength: "&times; Máximo 50 letras"}
               },
               errorPlacement:
                       function (error, element) {
                          if (element.attr("id") === "user") {
                             error.insertAfter(".userError");
                          } else if (element.attr("id") === "password") {
                             error.insertAfter(".passwordError");
                          } else {
                             error.insertAfter(element);
                          }
                       }
            });
            if (validarBusq.form()) {
               load("Procesando");
               let params = {
                  usuario: document.querySelector('#user').value.trim(),
                  pass: document.querySelector('#password').value,
                  codigoProyecto: CONSTANTES.CODIGO_PROYECTO_MATRICULA
               }
               httpRequest.login(params)
                       .then(data => {
                          if (data.status) {
                             window.location.href = "../vistas/main.jsp";
                          } else {
                             $('#password').val("");
                             $('#password').focus();
                             unload();
                             $('#msnErrorLogin').fadeOut();
                             $('#msnErrorLogin').fadeIn();
                          }
                       })
                       .catch(err => console.log(err))
            }
         })
      }
   }
   DOMEvents.init()

   $(window).bind('keypress', function (e) {
      if (e.charCode === 13 || e.keyCode === 13) {
         $('#logIn').click();
      }
   });