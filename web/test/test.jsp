<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>My JSP 'test.jsp' starting page</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
</head>

<body>

<c:set value="101" var="num"/>
<c:choose>
    <c:when test="${num >= 100}">
        <c:out value="正确"></c:out>
        aaaa
    </c:when>
    <c:otherwise>
        <c:out value="错误"></c:out>
    </c:otherwise>
</c:choose>


</body>
</html>
