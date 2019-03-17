<%--
  Created by IntelliJ IDEA.
  Creater: xp
  Date: 2019/3/2
  Time: 10:20
  Function:
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- 导入shiro标签 --%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>
</head>
<body>
    <h1>SUCCESSFUL!</h1>

    Welcome ！<shiro:principal/><br><hr>

    <shiro:hasRole name="admin">
        <%-- 有admin角色,显示超链接 --%>
        <a href="admin.jsp">admin</a><br>
    </shiro:hasRole>
    <shiro:hasRole name="user">
        <%-- 有user角色,显示超链接 --%>
        <a href="user.jsp">user</a><br>
    </shiro:hasRole>

    <%-- 经过认证才显示的链接 --%>
    <shiro:authenticated>
        <a href=""></a>
    </shiro:authenticated>

    <a href="shiro/logout">logout</a><br>

    <a href="shiro/testShiroAnnotation">Test annotaion</a>
</body>
</html>
