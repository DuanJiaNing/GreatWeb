<%--
  Created by IntelliJ IDEA.
  User: DuanJiaNing
  Date: 2017/10/9
  Time: 20:13
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="com.duan.greatweb.entitly.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Object obj = session.getAttribute("user");
    String name = "用户名获取出错";
    if (obj != null && obj instanceof User) {
        name = ((User) obj).getName();
    }

%>

<html>
<head>
    <title>留言内容管理系统</title>
    <link rel="stylesheet" href="../../css/manage/manage_os.css"/>
    <script type="text/javascript" src="../../js/common.js"></script>
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

</head>
<body>
<div id="page">
    <div id="top">
        <div id="top_left">
            <h2 style="margin-left: 8px; font-style: normal">内容管理系统</h2>
        </div>
        <div id="top_right">
        <span>您好：<%=name%>,&nbsp;欢迎使用内容管理系统！[<a href="#">网站主页</a>]&nbsp;[<a href="#">修改密码</a>]&nbsp;[<a
                href="#">注销退出unknown</a>]</span>
        </div>
    </div>

    <div class="clear"></div>
    <div id="center"></div>

    <div id="bottom">
        <div id="bottom_left">
            <div class="d1">
                <div class="d11">
                    <span>留言管理</span>
                    <img src="../../img/0.png">
                </div>
                <div class="d12">
                    <ul>
                        <li><a href="javaScript:switchIframe('manage_note_new.jsp')">新增留言</a><img src="../../img/1.png" class="img1"></li>
                        <li><a href="javaScript:switchIframe('manage_note_query.jsp')">查询留言</a></li>
                        <li><a href="javaScript:switchIframe('manage_note_recycle_bin.jsp')">留言回收站</a><img src="../../img/2.png" class="img2"></li>
                    </ul>
                </div>
            </div>

            <div class="d2">
                <div class="d21">
                    <span>用户管理</span>
                    <img src="../../img/0.png">
                </div>
                <div class="d22">
                    <ul>
                        <li><a href="javaScript:switchIframe('manage_user_add.jsp')">用户添加</a></li>
                        <li><a href="javaScript:switchIframe('manage_user_modify.jsp')">修改密码</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div id="bottom_right">
            <div id="bottom_right_content">
                <iframe src="note-os/manage/manage_note_home.jsp" width="100%" height="100%" id="noteOsIframe"></iframe>
            </div>
        </div>
    </div>
</div>
</body>
</html>
