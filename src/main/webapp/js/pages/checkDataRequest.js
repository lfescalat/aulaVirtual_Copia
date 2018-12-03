   /* global Cookies */
   let dataRequest
   ((Cookies) => {
      if (Cookies.get('dataUsuario') !== undefined) {
         dataRequest = JSON.parse(Cookies.get("dataUsuario"))
      } else {
         window.location = 'mantenimientoUsuario.jsp'
      }
   })(Cookies);