<%--
  Created by IntelliJ IDEA.
  Creater: xp
  Date: 2019/3/2
  Time: 10:19
  Function:
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>
</head>
<body>
    <h1>Login Page</h1>
    <form action="shiro/login" method="post">
        username: <input type="text" name="username"/>
        <br><br>
        password:<input type="password" name="password"/>
        <br><br>
        <input type="submit" value="登陆"/>
    </form>
</body>
</html>
