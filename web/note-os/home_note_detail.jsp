<%@ page import="com.duan.greatweb.entitly.Note" %>
<%@ page import="com.duan.greatweb.dao.NoteDaoImpl" %>
<%@ page import="com.duan.greatweb.util.Utils" %>
<%@ page import="com.duan.greatweb.entitly.User" %>
<%@ page import="com.duan.greatweb.dao.UserDaoImpl" %><%--
  Created by IntelliJ IDEA.
  User: DuanJiaNing
  Date: 2017/10/10
  Time: 10:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String str = request.getParameter("noteID");
    int noteID = Utils.parseStringToInt(str);

    String title = "标题";
    String content = "留言内容";
    String userName = "发表人";
    String time = "";

    if (noteID != -1){
        Note note = new NoteDaoImpl().query(noteID);
        if (note != null){
            title = note.getTitle();
            content = note.getContent();
            time = Utils.getFormatedTime(note.getDateTime(),"YYYY-MM-dd-HH");

            User user = new UserDaoImpl().query(note.getUserId());
            if (user != null){
                userName = user.getName();
            }
        }
    }

%>
<html>
<head>
    <title>留言详情</title>
    <link rel="stylesheet" href="../css/home_note_detail.css"/>
    <script type="text/javascript" src="../js/common.js"></script>

    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

</head>
<body>
<div class="all">
    <div class="top">
        <img src="../img/header.png" alt=""/>
        <div class="top_buttom">
            <form action="">
                <input type="button" value="留言列表" hidden="hidden"/>
                <input type="button" value="返回" onclick="window.history.back()"/>
            </form>
        </div>
    </div>
    <div class="content">
        <div class="content_top">
            <font style="font-weight: bold;margin-top: 5px">留言内容</font>
            <hr style="border-top:1px solid #C3C3C3;width: auto"/>
        </div>
        <div class="content_bottom">
            <table width="850px">
                <tr align="center"><br><br>
                    <td>
                        <p class="lead"><u><%=title%></u></p>
                    </td>
                </tr>

                <tr align="right">
                    <td>
                    <h4><small>&nbsp;发表时间:<%=time%></small></h4>
                    </td>
                </tr>
            </table>

            <blockquote>
                <p><%=content%></p>
                <footer>发表人: <cite ><%=userName%></cite></footer>
            </blockquote>

        </div>
    </div>
    <div style="height: 10px;width: 100%"></div>
    <div class="foot_right_botttom">
        <p>版权所有&copy;成都青春</p>
        <p>20160913java开发班</p>
    </div>
</div>
</body>
</html>
