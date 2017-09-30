<%@page import="com.duan.util.Utils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	Object obj = request.getAttribute("info");
	String info = "";
	if (obj != null) {
		info = obj.toString();
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>新用户注册</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" href="css/common.css" />
<script type="text/javascript" src="js/common.js">
</script>

</head>

<body>
	<div class="content">

		<form method="post" id="register">
			<table>
				<tr>
					<td>用户名：</td>
					<td><input type="text" id="username" name="username"
						onblur="verifyUsernameUsed()"></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input type="text" id="password" name="password"
						onkeyup="verifyPassword()"></td>
					<!--当使用自动填充时会键盘时事件会失效 -->
				</tr>
				<tr>
					<td>年龄：</td>
					<td><input type="text" id="age" name="age"
						onkeyup="verifyAge()"></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
			</table>
		</form>
		<button class="primary" onclick="register()">注册</button>
		&nbsp;&nbsp;&nbsp;<span id="info" class="red"><%=info%></span>
	</div>

</body>
</html>
