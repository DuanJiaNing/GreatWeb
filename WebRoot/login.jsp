<%@page import="com.duan.entitly.User"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="com.duan.util.Utils"%>
<%@page import="javax.swing.text.Document"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	Object objI = request.getAttribute("info");
	String info = "";
	if (objI != null) {
		info = objI.toString();
	}

	// 检查是否需要输入密码，当用户已经登陆且未注销时可跳过密码输入直接进入主页
	boolean inputRequerCheck = true;

	Object newUser = request.getAttribute("newUser");
	String newUserName = "";
	String newUserPassword = "";
	if (newUser != null && newUser instanceof User) {
		inputRequerCheck = false;
		User u = (User) newUser;
		newUserName = u.getName();
		newUserPassword = u.getPassword();
	}

	if (inputRequerCheck) {
		// 判断用户是否已经登陆，则直接登陆
		session.setMaxInactiveInterval(60 * 5);// 5 分钟
		Object obj = session.getAttribute("user");
		Object rember = session.getAttribute("rember");
		if (rember != null && (boolean) rember && obj != null && obj instanceof User) {
			request.getRequestDispatcher("login.do").forward(request, response);
		}
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>用户登陆</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" href="css/login.css" />
<script src="js/common.js" charset="utf-8">
</script>

</head>

<body>

	<div class="all">
		<div class="top"></div>
		<div class="content">
			<div class="content_left"></div>
			<div class="content_center">
				<form method="post" id="login">
					<table>
						<tr>
							<td>用户</td>
							<td><input type="text" name="username" id="username"
								onkeyup="verifyUsername()" value="<%=newUserName%>" /></td>
						</tr>

						<tr>
							<td>密码</td>
							<td><input type="password" id="password" name="password"
								onkeyup="verifyPassword()" value="<%=newUserPassword%>" /></td>
						</tr>
						<tr>
							<td>验证码</td>
							<td><input type="text" name="identifyCode" id="identifyCode"
								onblur="verifyIdentfyCode()" /></td>
						</tr>

						<tr>
							<td></td>
							<td><img src="identifyCode.do" alt="验证码图片"
								id="identifyCodeImage" onclick="refreshIdentifyCode()" /></td>
						</tr>

						<tr>
							<td align="left">记住我</td>
							<td align="left"><input type="checkbox" id="rember" /></td>
						</tr>

						<tr>
							<td></td>
							<td><input type="reset" name="re" value="重置" class="r" /> <a
								href="register.jsp">新用户注册</a></td>
						</tr>

						<tr>
							<td></td>
							<td></td>
						</tr>
					</table>
				</form>
				<div class="login_button">
					<button onclick="login()" class="transparent">登入</button>
					&nbsp;&nbsp;&nbsp;&nbsp;<span id="info" class="info"><%=info%></span>
				</div>
			</div>
			<div class="content_right"></div>
		</div>
		<div class="bottom"></div>
	</div>
</body>
</html>
