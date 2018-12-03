<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>AULAVIRTUAL</title>
<link rel="icon" type="image/png" sizes="25x25" href="../img/av.png" />

<!-- Global stylesheets -->
<link href="https://fonts.googleapis.com/css?family=Roboto:400,300,100,500,700,900" rel="stylesheet" type="text/css">
<link href="../plantilla/assets/css/icons/icomoon/styles.css" rel="stylesheet" type="text/css">
<link href="../plantilla/assets/css/icons/fontawesome/styles.min.css" rel="stylesheet" type="text/css">
<link href="../plantilla/assets/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="../plantilla/assets/css/core.css" rel="stylesheet" type="text/css">
<link href="../plantilla/assets/css/components.css" rel="stylesheet" type="text/css">
<link href="../plantilla/assets/css/colors.css" rel="stylesheet" type="text/css">
<!-- /global stylesheets -->
<script src="../js/lib/cookies.js" type="text/javascript"></script>
<script>
       var sidebar = <%=(String) request.getSession().getAttribute("menu")%>;

       var nombreUsuario = "<%=(String) request.getSession().getAttribute("nombre")%>";

       var rolesUsuario = "<%=(String) request.getSession().getAttribute("roles")%>";
</script>