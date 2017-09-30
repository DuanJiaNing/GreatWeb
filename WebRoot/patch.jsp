<%@page import="com.duan.entitly.Note"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	Object obj = request.getAttribute("notes");
	List<Note> notes = null;
	if (obj != null) {
		notes = (List<Note>) obj;
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>批量导入留言</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="css/common.css">

</head>

<body>
	批量导入留言
	<hr>
	<h3>文件格式要求：</h3>
	<ol>
		<li>文件保存在txt文件中，txt文件编码为 utf-8</li>
		<li>一条数据以 ; (英文)结尾，字段数据以 : (英文)结尾</li>
		<li>示例：title:content:uid;title1:content1:uid1</li>
	</ol>
	<br>
	<form action="patchImportNote.do" enctype="multipart/form-data"
		method="post">
		<center>
			<input type="file" name="file"><input type="submit"
				value="导入">
		</center>
	</form>

	<%
		if (notes != null && notes.size() > 0) {
	%>
	<table class="match">
		<%
			for (Note n : notes) {
		%>
		<tr>
			<td><%=n.toString()%><br></td>
		</tr>
		<%
			}
		%>
	</table>
	<%
		}
	%>

</body>
</html>
