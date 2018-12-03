   /* global CONSTANTES, Cookies, capitalizeWords */

   const httpRequestt = {
      getproyectos() {
         return ajaxRequestSendBody({
            url: CONSTANTES.PATH_SERVICIO_REST + 'proyecto/listarProyectoUsuario',
            type: 'POST',
            headers: {
               "Content-type": 'application/json',
               "Accept": 'application/json',
               "Authorization": Cookies.get('Authorization')
            }

         })
      }
   }

   const link = i => `window.location = '${i}'`;

   let setMenu = () => {
      let location = window.location;
      let position = getUrl(location.pathname, "/");
      let path = location.pathname.substring(position + 1);
      let menu = document.getElementById("menu");
      let contenido = window.sidebar.menu;
      let html = "";
      let active = "";
      let banner = `<li><a href="main.jsp"><i class="icon-home2 position-left"></i> Inicio</a></li>`;
      let user = "";
      var datos = window.nombreUsuario;
      user = `<a href="#" class="media-left" style="padding-right: 0px;">
                <img src="../img/${capitalizeWords(datos).charAt(0)}.jpg" style='zoom:180% ;box-shadow: 0px 0px 13px 0px rgba(0,0,0,0.75);' class="img-circle " alt="image">
            </a>
            <div class="media">
                <div class="media-body media-middle">
                    <span class="media-heading text-semibold" style="font-size:15px">${capitalizeWords(datos)} </span>
                </div>               
            </div>`;
      let banner2 = `<a class="dropdown-toggle" data-toggle="dropdown">
                    <img src="../img/${capitalizeWords(datos).charAt(0)}.jpg" alt="">
                    <span>${capitalizeWords(datos)}</span>
                    <i class="caret"></i>
                </a>
                <ul class="dropdown-menu dropdown-menu-left">
                    <li><a class="logOut"><i class="icon icon-switch2"></i> Cerrar Sesión</a></li>
                </ul>`;

      for (var i in contenido) {
         let titulo = contenido[i].titulo;
         html += `<li class="navigation-header"><span><i class="fa ${titulo.ico}" title="Main pages"></i>&nbsp;&nbsp;${titulo.nombre}</span></li>`;
         let modulo = titulo.modulo;
         for (var j in modulo) {
            if (path === modulo[j].url) {
               active = 'active';
               banner += `<li>
                                <i class="fa ${modulo[j].ico} position-left"></i> 
                                ${capitalizeWords(modulo[j].nombre)}
                            </li>`;
            } else {
               active = '';
            }
            if (modulo[j].tipo === "2") {
               html += `<li><a href="${modulo[j].url}"><i class="fa ${modulo[j].ico} fa-lg"></i><span>${capitalizeWords(modulo[j].nombre)}</span></a><ul>`;
               var categoria = modulo[j].categoria;
               for (var k in categoria) {
                  if (path === categoria[k].url) {
                     active = 'active';
                     banner += `<li>
                                                <i class="fa ${modulo[j].ico} position-left"></i> 
                                                ${capitalizeWords(modulo[j].nombre)}
                                            </li>`;
                     banner += ` <li>
                                        ${capitalizeWords(categoria[k].nombre)}
                                    </li>`;
                  } else {
                     active = '';
                  }
                  if (categoria[k].tipo === "2") {
                     html += `<li><a href="${categoria[k].url}"><span>${capitalizeWords(categoria[k].nombre)}</span></a><ul>`;
                     let subcategoria = categoria[k].subCategoria;
                     for (var l in subcategoria) {
                        if (path === subcategoria[l].url) {
                           active = 'active';
                           banner += `<li>
                                                <i class="fa ${modulo[j].iconoModulo} position-left"></i> 
                                                ${capitalizeWords(modulo[j].nombre)}
                                            </li>`;
                           banner += ` <li>
                                        ${capitalizeWords(categoria[k].nombre)}
                                    </li>`;
                           banner += ` <li>
                                                ${capitalizeWords(subcategoria[l].nombre)}
                                            </li>`;
                        } else {
                           active = '';
                        }
                        html += `<li class="${active}"><a href="${subcategoria[l].url}"><span>${capitalizeWords(subcategoria[l].nombre)}</span></a></li>`;
                     }
                     html += `</li></ul>`;
                  } else {
                     html += `<li class="${active}"><a href="${categoria[k].url}"><span>${capitalizeWords(categoria[k].nombre)}</span></a></li>`;
                  }
               }
               html += `</li></ul>`;
            } else {
               html += `<li class="${active}"><a href="${modulo[j].url}"><i class="fa ${modulo[j].ico} fa-lg"></i><span>${capitalizeWords(modulo[j].nombre)}</span></a></li>`;
            }
         }
      }
      menu.innerHTML = html;
      $('#banner').html(banner);
      $('#usuario').html(user);
      $('#banner2').html(banner2);
      logOut();
   };

   let getUrl = (pathname, caracter) => {
      let position = 0;
      for (let i = pathname.length - 1; i > 0; i--) {
         if (pathname[i] === caracter) {
            position = i;
            break;
         }
      }
      return position;
   };

   let DOMNav = {
      init() {
         this.setProyecto();
      },
      setProyecto() {
         httpRequestt.getproyectos()
                 .then(data => {
                    if (data.status) {
                       let html = '';
                       let proyectos = data.proyectos;
                       for (let i in proyectos) {
                          let url = proyectos[i].url;//produccion
                          let cp = proyectos[i].codigoProyecto;//produccion+
                          let servlet = '/vistas/redireccionarServlet&cp=';
                          if (cp === CONSTANTES.CODIGO_PROYECTO_MATRICULA) {
                             continue;
                          }
                          if (url.endsWith('/')) {
                             servlet = 'vistas/redireccionarServlet&cp=';
                          }
                          html += '<li><a target="_blank" href="../vistas/interceptar?p=' + url + servlet + cp + '" class="redirect" > '
                                  + proyectos[i].nombre + '</a></li>';
                       }
                       document.getElementById("proyectos").innerHTML = html;

                    } else {
                       msnError("Ocurrió un error al cargar los proyectos")
                    }
                 })
                 .catch(err => console.log(err))
      }
   }

   setMenu();
   DOMNav.init();

