<%@page import="com.duan.dao.UserDaoImpl"%>
<%@page import="com.duan.util.Utils"%>
<%@page import="com.duan.other.UserTable"%>
<%@page import="javax.swing.text.Document"%>
<%@page import="com.duan.entitly.User"%>
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

<title>用户信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="css/common.css">


<script type="text/javascript">
	function checkScore(userId) {
		document.location = "user/user_score_check.jsp?userId=" + userId;
	}

	function modifyScore(userId) {
		document.location = "user/user_score_modify.jsp?userId=" + userId;
	}
</script>

</head>

<body>

	<h2>用户信息</h2>
	<hr>
	<br>

	<div class="content">

		<%
			List<User> users = new UserDaoImpl().queryAll(0);
			if (!Utils.isListEmpty(users)) {
		%>
		<table>
			<%
				for (int i = 0; i < users.size(); i++) {
			%>
			<tr class="line">
				<td class="warp"><%=i + 1%></td>
				<td class="match"><%=users.get(i).getName()%></td>
				<td class="warp"><button class="transparent_primary"
						onclick="checkScore(<%=i%>)">查看成绩</button></td>
				<td class="warp"><button class="transparent_modify"
						onclick="modifyScore(<%=i%>)">修改成绩</button></td>
			</tr>
			<%
				}
			%>
		</table>
		<%
			} else {
		%>
		<h4>没有用户</h4>
		<%
			}
		%>
	</div>
</body>

</html>
