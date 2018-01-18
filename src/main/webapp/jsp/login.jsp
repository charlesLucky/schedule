<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>Slick Login</title>
    <meta name="description" content="slick Login">
    <meta name="author" content="Webdesigntuts+">
    <link rel="stylesheet" type="text/css" href="${basePath}/resources/css/style.css" />
    <script type="text/javascript" src="${basePath}/resources/js/jquery-easyui-1.3.5/jquery-1.10.2.min.js"></script>
</head>
<body style="background: url(resources/images/1.jpg)">
    <form id="slick-login" action="${basePath}/loginCheck.do" method="POST">
        <label for="username">username</label><input type="text" name="username" class="placeholder" placeholder="me@tutsplus.com">
        <label for="password">password</label><input type="password" name="password" class="placeholder" placeholder="password">
        <input type="submit" value="登录">
    </form>
</body>
</html>