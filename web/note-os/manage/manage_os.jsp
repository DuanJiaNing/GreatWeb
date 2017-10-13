<%--
  Created by IntelliJ IDEA.
  User: DuanJiaNing
  Date: 2017/10/9
  Time: 20:13
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="com.duan.greatweb.entitly.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%

    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

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

<%--模拟的弹出窗口 依赖于 css 和 之后的 contentWithPopupWindow--%>
<div style="width: 100%; height: 100%" id="popupWindowContainer" onclick="hidePopup()">
    <div id="popupContent" class="panel panel-default">
        <div class="panel-heading">
            <h4><b>编辑留言&nbsp;&nbsp;</b>
                <small>只能修改留言标题和内容</small>
            </h4>
        </div>
        <div class="panel-body">
            <iframe width="100%" height="100%" frameborder="0px" id="popupFrame"></iframe>
        </div>
    </div>
</div>

<div class="contentWithPopupWindow">
    <div class="container top">
        <div class="row">
            <div class="col-xs-8 top-sub-container">
                <h3 class="title"><b>管理系统</b>&nbsp;&nbsp;<small><c:out value="${user.name}" default="unknown"></c:out></small>
                </h3>
            </div>

            <div class="col-xs-4 top-sub-container">

                <nav class="non-border" aria-label="...">
                    <ul class="pagination non-border">
                        <li class="active"><a href="#">网站主页 </a></li>
                        <li><a href="#">修改密码 </a></li>
                        <li><a href="#">注销退出 </a></li>
                        <li><a href="#">&nbsp;<span class="glyphicon glyphicon-user"></span></a>&nbsp;</li>
                    </ul>
                </nav>
            </div>

        </div>
    </div>

    <div class="container">
        <div class="row">
            <div class="col-md-2 content">
                <hr>
                <div class="d1">
                    <div class="d11">
                        <p class="text-center"><b class="category">&nbsp;留言管理</b></p>
                    </div>
                    <div class="d12">
                        <ul>
                            <li>
                                <a href="javaScript:switchIframe('manage_note_new.jsp')">新增留言</a>
                            </li>
                            <li>
                                <a href="javaScript:switchIframe('manage_note_query.jsp')">查询留言</a>
                            </li>
                            <li>
                                <a href="javaScript:switchIframe('manage_note_recycle_bin.jsp')">留言回收站</a></li>
                        </ul>
                    </div>
                </div>

                <div class="d2">
                    <div>
                        <p class="text-center"><b class="category">&nbsp;用户管理</b></p>
                    </div>
                    <div class="d22">
                        <ul>
                            <li>
                                <a href="javaScript:switchIframe('manage_user_add.jsp')">用户添加</a>
                            </li>
                            <li><a href="javaScript:switchIframe('manage_user_modify.jsp')">修改密码</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="col-md-10">
                <iframe src="<%=basePath%>/note-os/manage/manage_note_query.jsp" width="100%" height="100%"
                        frameborder="0px" scrolling="no" id="noteOsIframe"></iframe>
            </div>

        </div>
    </div>
</div>
</body>
</html>
