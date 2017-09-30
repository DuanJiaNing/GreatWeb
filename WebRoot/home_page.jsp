<%@page import="com.duan.entitly.Note"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head lang="en">
<base href="<%=basePath%>">

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="css/home_page.css">
<script type="text/javascript" src="js/common.js"></script>
<title>首页</title>
</head>

<body>
	<div class="all">
		<div class="top">
			<img src="img\header.png" alt="" />
			<div class="top_buttom">
				<form action="">
					<input type="button" value="留言列表" /> <input type="button"
						value="后台管理" onclick="javascript:document.location='login.jsp'" />
				</form>
			</div>
		</div>
		<div class="foot">
			<div class="foot_left">

				<div class="a">
					<div style="height: 15px"></div>
					<font style="font-weight: bold">参与人员</font><br />
				</div>
				<div class="b">
					<br />
					<hr
						style="border-top:1px solid #C3C3C3;width: auto;margin-top: -2px" />
				</div>
				<div class="c">
					<!--<img src="img\left_bottom.gif" alt="bottom"/>-->
				</div>
			</div>
			<div class="foot_right">
				<div style="height: 20px;padding-top: 5px">
					<font style="font-weight: bold;margin-left: 10px">留言列表</font><br />
				</div>
				<hr style="border-top: 1px solid #C3C3C3" />
				<table id="notes">
				</table>
			</div>
			<div style="height: 20px;width: 73%;float: right"></div>
			<div class="foot_right_botttom">
				<p>版权所有&copy;成都青春</p>
				<p>20160913java开发班</p>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		loadNotes('1');
	</script>
</body>
</html>
