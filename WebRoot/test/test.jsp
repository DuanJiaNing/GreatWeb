<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'test.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" href="css/common.css" />
<script type="text/javascript">
	function verifyUsername() {
		var name = document.getElementById("username").value;
		var ajaxRequest = new XMLHttpRequest();
		// 与服务器建立连接
		ajaxRequest.open('get', 'testServlet.do?method=checkName&name=' + name, true);
		//发送请求
		ajaxRequest.send();
		ajaxRequest.onreadystatechange = function() {
			if (ajaxRequest.readyState == 4) {
				var state = ajaxRequest.status;
				switch (state) {
				case 200: // 接收到结果
					var result = ajaxRequest.responseText;
					document.getElementById("info").innerHTML = result + " stats text : " + ajaxRequest.statusText;
					break;
				default:
					break;
				}
			}
		};
	}
</script>

</head>

<body>
	<div class="content">

		<form method="post" id="testServlet.do">
			<table>
				<tr>
					<td>用户名：</td>
					<td><input type="input" id="username" name="username"
						onkeyup="verifyUsername()"></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input type="input" id="password" name="password"
						onkeyup="verifyPassword()"></td>
				</tr>
				<tr>
					<td>年龄：</td>
					<td><input type="input" id="age" name="age"
						onkeyup="verifyAge()"></td>
				</tr>
				<tr>
					<td>手机号码：</td>
					<td><input type="input" id="phone" name="phone"
						onkeyup="verifyPhone()"></td>
				</tr>
				<tr>
					<td>邮箱：</td>
					<td><input type="input" id="email" name="email"
						onkeyup="verifyEmail()"></td>
				</tr>
				<tr>
					<td></td>
					<td align="left"><button onclick="login('signin')"
							class="transparent">登入</button>
						<button onclick="login('signup')" class="transparent">注册</button></td>
				</tr>
			</table>
			<br> <span id="info" class="red"></span>
		</form>
	</div>

</body>
</html>
