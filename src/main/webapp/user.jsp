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
    <h1>user</h1>

</body>
</html>
