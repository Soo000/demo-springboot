<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta charset="UTF-8">
        <title>登录</title>
    </head>
    <body>
        <form id="loginForm" action="/dologin" method="post">
            <input type="text" name="username" placeholder="用户名/手机号"/>
            <br />
            <input type="password" name="password" placeholder="密码"/>
            <br />
            <input type="button" value="登录" id="loginButton"/>
        </form>
       
        <script type="text/javascript" src="/res/js/jquery/jquery-1.12.0.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $("#loginButton").on("click", function(evt) {
                    $("#loginForm").submit();
                });
            });
        </script>
    </body>
</html>