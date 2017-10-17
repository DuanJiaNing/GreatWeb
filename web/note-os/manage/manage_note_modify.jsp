<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: DuanJiaNing
  Date: 2017/10/9
  Time: 20:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加用户</title>
    <script type="text/javascript" src="../../js/common.js"></script>
    <script type="text/javascript" src="../../js/jQuery/jquery-1.11.0.js"></script>

    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

</head>
<body>
<table class="table table-bordered">
    <tr>
        <td style="text-align: center;vertical-align: middle"><span>标题</span></td>
        <td><input type="text" class="form-control" name="title" id="noteTitle" style="width: 100%"
                   onfocus="clearInfo()">
        </td>
    </tr>
    <tr>
        <td style="text-align: center;vertical-align: middle"><span>内容</span></td>
        <td><textarea name="content" class="form-control" id="noteContent"
                      onfocus="clearInfo()"
                      style="width: 100%;height: 130px;"></textarea>
        </td>
    </tr>
    <tr>
        <td style="text-align: center;vertical-align: middle"><span>日期</span></td>
        <td><span style="color: #7c7c7c;" id="dateTime">
        </span>
        </td>
    </tr>
    <tr>
        <td style="text-align: center;vertical-align: middle"><span>留言人</span></td>
        <td><span style="color: #7c7c7c;" id="user"></span>
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
        <td colspan="2" align="left"><span id="info" style="color: red;">&nbsp;加载中...</span></td>
    </tr>
</table>

<script type="text/javascript">

    loadData();

    var noteTitle;
    var noteContent;
    var dateTime;
    var user;
    var info;

    window.onload = function () {
        noteTitle = document.getElementById('noteTitle');
        noteContent = document.getElementById('noteContent');
        dateTime = document.getElementById('dateTime');
        user = document.getElementById('user');
        info = document.getElementById('info');
    }

    function loadData() {
        manipulateDataAsyn('dataObtain.do?category=3&noteId=' +${param.noteId}, null, function (data) {
            if (data === null) {
                info.innerHTML = '留言数据获取失败';
            } else {
                clearInfo();
                var note = JSON.parse(data);
                noteTitle.value = note.title;
                noteContent.value = note.content;
                dateTime.innerHTML = getDate(note.dateTime);

                manipulateDataAsyn('dataObtain.do?category=4&userId=' + note.userId, null, function (data) {
                    if (data === null) {
                        info.innerHTML = '留言人获取失败';
                    } else {
                        clearInfo();
                        var user_ = JSON.parse(data);
                        user.innerHTML = user_.name;
                    }
                }, true);
            }
        }, true);
    }

    function cancelModify() {
        hidePopup();
        clearInfo();
    }

    function resetInput() {
        noteTitle.value = '';
        noteContent.value = '';
    }

    function saveModify() {

        var title = noteTitle.value;
        var content = noteContent.value;

        var info = '';

        if (verifyContent(title, content)) {
            manipulateDataAsyn('noteControl.do?category=5&noteId=' + ${param.noteId},
                '{' +
                '"title": "' + encodeURIComponent(title) +
                '","content": "' + encodeURIComponent(content) + '"}',

                function (data) {
                    var json = JSON.parse(data);
                    if (json.code != 1) {
                        info = '修改失败：' + json.result;
                        alert(info);

                    } else {
                        cancelModify();

                        // 刷新页面，无法通过调用 refreshData 方法完成，因为当前 window 和 查询页面所在的
                        // window 不是同一个 window，无法找到表格
                        var win = getWindow('queryWindow');
                        win.location.href = win.location.href;
                    }

                }, true);
        }
    }

    function verifyContent(title, content) {
        if (isEmpty(title)) {
            info.innerHTML = '标题不能为空';
            splashInfo("info");
            return false;
        }

        if (isEmpty(content)) {
            info.innerHTML = '内容不能为空';
            splashInfo("info");
            return false;
        }
        return true;
    }

    function clearInfo() {
        info.innerHTML = '&nbsp;';
    }

</script>

</body>
</html>
