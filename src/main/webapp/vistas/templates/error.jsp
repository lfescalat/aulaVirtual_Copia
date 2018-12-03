<!DOCTYPE html>
<html lang="en" >

    <head>
        <meta charset="UTF-8">
        <title>AULAVIRTUAL</title>
        <link rel="icon" type="image/png" sizes="25x25" href="../img/av.png" />

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
        <link href="../../css/pages/error.css" rel="stylesheet" type="text/css"/>
        <style>
            @import "https://fonts.googleapis.com/css?family=Inconsolata";
            html {
                min-height: 100%;
            }

            body {
                box-sizing: border-box;
                height: 100%;
                background-color: #000000;
                background-image: radial-gradient(#11581E, #041607);
                font-family: 'Inconsolata', Helvetica, sans-serif;
                font-size: 1.5rem;
                color: rgba(128, 255, 128, 0.8);
                text-shadow: 0 0 1ex #33ff33, 0 0 2px rgba(255, 255, 255, 0.8);
            }

            .overlay {
                pointer-events: none;
                position: absolute;
                width: 100%;
                height: 100%;
                background: repeating-linear-gradient(180deg, rgba(0, 0, 0, 0) 0, rgba(0, 0, 0, 0.3) 50%, rgba(0, 0, 0, 0) 100%);
                background-size: auto 4px;
                z-index: 99;
            }

            .overlay::before {
                content: "";
                pointer-events: none;
                position: absolute;
                display: block;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                width: 100%;
                height: 100%;
                background-image: linear-gradient(0deg, transparent 0%, rgba(32, 128, 32, 0.2) 2%, rgba(32, 128, 32, 0.8) 3%, rgba(32, 128, 32, 0.2) 3%, transparent 100%);
                background-repeat: no-repeat;
                -webkit-animation: scan 7.5s linear 0s infinite;
                animation: scan 7.5s linear 0s infinite;
            }

            @-webkit-keyframes scan {
                0% {
                    background-position: 0 -100vh;
                }
                35%, 100% {
                    background-position: 0 100vh;
                }
            }

            @keyframes scan {
                0% {
                    background-position: 0 -100vh;
                }
                35%, 100% {
                    background-position: 0 100vh;
                }
            }
            .terminal {
                box-sizing: inherit;
                position: absolute;
                left:0;
                right:0;
                top:0;
                bottom:0;
                margin:auto;
                width: 1000px;
                max-width: 100%;
                padding: 10rem;
                text-transform: uppercase;
                max-height:100%;
                overflow:auto;
            }

            .output {
                color: rgba(128, 255, 128, 0.8);
                text-shadow: 0 0 1px rgba(51, 255, 51, 0.4), 0 0 2px rgba(255, 255, 255, 0.8);
            }

            .output::before {
                content: "> ";
            }

            a {
                color: #fff;
                text-decoration: none;
            }

            a::before {
                content: "[";
            }

            a::after {
                content: "]";
            }

            .errorcode {
                color: white;
            }
        </style>

    </head>

    <body>

        <div class="overlay"></div>
        <div class="terminal content">
            <h1>Error <span class="errorcode"></span></h1>
            <p class="output">Se ha producido un error. 
                <%
                                                                        if (request.getSession().getAttribute("error") != null) {
                                                                                out.print(request.getSession().getAttribute("error"));
                                                                                request.getSession().removeAttribute("error");
									}%> 
            </p>
            <p class="output">Haga click aquí para  <a href="../vistas/index.jsp">volver a la página principal</a></p>
        </div>



    </body>

</html>

