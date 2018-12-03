<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Global stylesheets -->
        <title>AULAVIRTUAL</title>
        <link rel="icon" type="image/png" sizes="25x25" href="../img/av.png" />
        <link href="https://fonts.googleapis.com/css?family=Roboto:400,300,100,500,700,900" rel="stylesheet" type="text/css">
        <link href="../plantilla/assets/css/icons/icomoon/styles.css" rel="stylesheet" type="text/css">
        <link href="../plantilla/assets/css/bootstrap.css" rel="stylesheet" type="text/css">
        <link href="../plantilla/assets/css/core.css" rel="stylesheet" type="text/css">
        <link href="../plantilla/assets/css/components.css" rel="stylesheet" type="text/css">
        <link href="../plantilla/assets/css/colors.css" rel="stylesheet" type="text/css">        
        <link href="../css/general.css" rel="stylesheet" type="text/css"/>
        <link href="../css/pages/index.css" rel="stylesheet" type="text/css"/>
        <!-- /global stylesheets -->

    </head>
    <body class="login-container centralize">
        <!-- Page container -->
        <div class="page-container">

            <!-- Page content -->
            <div class="page-content">

                <!-- Main content -->
                <div class="content-wrapper">

                    <!-- Content area -->
                    <div class="content">

                        <!-- Advanced login -->
                        <form action="#" id="formLogin" >
                            <div class="login-form containerMe panel-zoom">
                                <div class="translucentMe card-3"></div>
                                <div class="contentMe">
                                    <div class="text-center">
                                        <div class="icon-object border-white text-white"><i class="icon  icon-users4" style="font-size: 50px;   line-height: 1.37;"></i></div>
                                        <h5 class="content-group text-white">REGISTRO AULAVIRTUAL<small class="display-block" style="color: white">Login</small></h5>
                                    </div>

                                    <div class="form-group has-feedback has-feedback-left">
                                        <div class="userError">
                                            <input type="text" id="user" name="user" class="form-control so-input-login" placeholder="Usuario" autofocus="">
                                            <div class="form-control-feedback">
                                                <i class="icon-user text-muted"></i>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group has-feedback has-feedback-left">
                                        <div class="passwordError">
                                            <input type="password" id="password" name="password" class="form-control so-input-login" placeholder="Contraseña">
                                            <div class="form-control-feedback">
                                                <i class="icon-lock2 text-muted"></i>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group text-center">
                                        <button type="button" id="logIn" class="btn bg-slate-800 border-white btn-xs" >INGRESAR <i class=""></i></button>
                                    </div>
                                </div>                                
                            </div>
                        </form>
                        <!-- /advanced login -->
                        <div id="msnErrorLogin"  style="display: none; padding: 8px;" class="login-form containerMe panel-zoom">
                            <div class="msgTranslucent card-3" style="background-color: #821C25"></div>
                            <div class="contentMe text-center">
                                <label class="text-white" style="margin-bottom: 0px; font-size: smaller; text-align: center"><b>&times;</b> USUARIO O CONTRASEÑA INVÁLIDOS</label>  
                            </div>
                        </div>


                        <!-- Footer -->
                        <div class="footer text-muted text-center footerText">
                            <label id="footerDate">&copy; </label><a href="#">&nbsp; Registro de usuarios Aulavirtual</a> por <a href="#">Área Sistemas TIC</a>
                        </div>
                        <!-- /footer -->

                    </div>
                    <!-- /content area -->

                </div>
                <!-- /main content -->

            </div>
            <!-- /page content -->

        </div>
        <!-- /page container -->

        <!--javascript template-->
        <!-- Core JS files -->
        <script type="text/javascript" src="../plantilla/assets/js/plugins/loaders/pace.min.js"></script>
        <script type="text/javascript" src="../plantilla/assets/js/core/libraries/jquery.min.js"></script>
        <script type="text/javascript" src="../plantilla/assets/js/core/libraries/bootstrap.min.js"></script>
        <script type="text/javascript" src="../plantilla/assets/js/plugins/loaders/blockui.min.js"></script>
        <!-- /core JS files -->

        <!-- Theme JS files -->
        <script type="text/javascript" src="../plantilla/assets/js/plugins/forms/styling/uniform.min.js"></script>

        <script type="text/javascript" src="../plantilla/assets/js/core/app.js"></script>
        <script type="text/javascript" src="../plantilla/assets/js/pages/login.js"></script>
        <script src="../plantilla/assets/js/plugins/forms/validation/validate.min.js" type="text/javascript"></script>
        <!-- /theme JS files -->
        <!--javascript template-->
        <script src="../js/lib/cookies.js" type="text/javascript"></script>
        <script src="../js/general.js" type="text/javascript"></script>
        <script src="../js/pages/index.js" type="text/javascript"></script>
        <script>
               ((window) => {
                  window.history.replaceState({}, '', 'index.jsp');
               })(window);
        </script>
    </body>
</html>
