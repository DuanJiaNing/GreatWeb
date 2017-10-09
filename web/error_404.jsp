<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	isErrorPage="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'error_404.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<h4>404 错误页面</h4>
	<hr>
	<%=exception == null ? "null exception" : exception.getMessage()%>
	<br>

	<hr>

	<%=pageContext.getClass().getName()%><br>
	<%=session.getClass().getName()%><br>
	<%=application.getClass().getName()%><br>
	<%=config.getClass().getName()%><br>
	<%=out.getClass().getName()%><br>
	<%=page.getClass().getName()%><br>
	<hr>
	<%=request.getClass().getName()%><br>
	<%=response.getClass().getName()%><br>
	<hr>
	<%
		pageContext.setAttribute("pageToSession", "save page to session", PageContext.SESSION_SCOPE);
		pageContext.findAttribute("");
		Exception ex = pageContext.getException();
		application.log("log massage from error_404.jsp" + this.getClass().getName());
		session.setMaxInactiveInterval(5);
	%>

	page out:<%=pageContext.getOut().toString()%><br> response out:<%=response.getWriter().toString()%><br>
	scope：<%=session.getAttribute("pageToSession")%>

	<hr>
	<%@include file="welcome.html"%>

	<jsp:include page="note-os/login.jsp">
	<jsp:param value="param test" name="jspParam" />
	</jsp:include>

	<%=session.getAttribute("onlineNum")%><hr>

	<%
		request.setAttribute("lRequest", "lRequest");
		request.removeAttribute("lRequest");
		request.setAttribute("lRequest", "change");
	%>

	<%
		Cookie cookie = new Cookie("name", "this_is_name_test_for_Cookie");
		cookie.setMaxAge(24 * 60 * 60);
		cookie.setPath(application.getContextPath());
		// 把Cookie返回给浏览器
		response.addCookie(cookie);
	%>

	<hr>
	<%
		Cookie[] cs = request.getCookies();
		for (Cookie c : cs) {
	%>
	<%=c.getName()%>:
	<%=c.getValue()%>
	<br>
	<%
		}
	%>

</body>
</html>
