<%@ page import="com.duan.greatweb.util.Utils" %>
<%@ page import="com.duan.greatweb.entitly.User" %>
<%@ page import="com.duan.greatweb.entitly.Note" %>
<%@ page import="com.duan.greatweb.dao.NoteDaoImpl" %>
<%@ page import="com.duan.greatweb.dao.UserDaoImpl" %><%--
  Created by IntelliJ IDEA.
  User: DuanJiaNing
  Date: 2017/10/9
  Time: 20:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    String id = request.getParameter("noteId");
    Note note = null;
    User user = null;
    if (Utils.isReal(id)) {
        int noteId = Integer.valueOf(id);
        note = new NoteDaoImpl().query(noteId);
        if (note != null) {
            user = new UserDaoImpl().query(note.getUserId());
        }
    }

%>
<html>
<head>
    <title>添加用户</title>
    <script type="text/javascript" src="../../js/common.js"></script>
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

</head>
<body>
<table class="table table-bordered">
    <tr>
        <td style="text-align: center;vertical-align: middle"><span>标题</span></td>
        <td><input type="text" class="form-control" name="title" id="noteTitle" style="width: 100%"
                   onfocus="clearInfo()" value="<%=note == null ? "标题获取出错" : note.getTitle()%>"></td>
    </tr>
    <tr>
        <td style="text-align: center;vertical-align: middle"><span>内容</span></td>
        <td><textarea name="content" class="form-control" id="noteContent"
                      onfocus="clearInfo()" style="width: 100%;height: 130px;"><%=note == null ? "留言内容获取出错" : note.getContent()%></textarea>
        </td>
    </tr>
    <tr>
        <td style="text-align: center;vertical-align: middle"><span>日期</span></td>
        <td><span style="color: #7c7c7c;">
            <%=note == null ? "" : Utils.getFormatedTime(note.getDateTime(), "YYYY-MM-dd-HH")%></span>
        </td>
    </tr>
    <tr>
        <td style="text-align: center;vertical-align: middle"><span>留言人</span></td>
        <td><span style="color: #7c7c7c;"><%=user == null ? "用户名获取出错" : user.getName()%></span>
        </td>
    </tr>
    <tr>
        <td></td>
        <td align="right">
            <button class="btn btn-default" style="border: 0px;outline: 0px" onclick="cancelModify()"><b>取消</b></button>
            <button class="btn btn-default" style="border: 0px;outline: 0px" onclick="resetInput()"><b>清空</b></button>
            <button class="btn btn-default" style="border: 0px;outline: 0px" onclick="saveModify()"><b>保存</b></button>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="left"><span id="info" style="color: red;">&nbsp;</span></td>
    </tr>
</table>

<script type="text/javascript">
    function cancelModify() {
        resetInput();
        hidePopup();
        clearInfo();
    }

    function resetInput() {
        var noteTitle = document.getElementById('noteTitle');
        var noteContent = document.getElementById('noteContent');
        noteTitle.value = '';
        noteContent.value = '';
    }

    function saveModify() {

        var noteTitle = document.getElementById('noteTitle');
        var noteContent = document.getElementById('noteContent');
        var title = noteTitle.value;
        var content = noteContent.value;

        if (verifyContent(title, content)) {
            // TODO 修改成功后关闭弹出框，刷新页面。
            cancelModify();
        }
    }

    function verifyContent(title, content) {
        var info = document.getElementById('info');
        if (!isEmpty(title)) {
            info.innerHTML = '标题不能为空';
            return false;
        }

        if (!isEmpty(content)) {
            info.innerHTML = '内容不能为空';
            return false;
        }
        return true;
    }

    function clearInfo() {
        var info = document.getElementById('info');
        info.innerHTML = '&nbsp;';
    }

</script>

</body>
</html>
