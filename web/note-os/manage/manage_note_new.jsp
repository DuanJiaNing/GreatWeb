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
    <title>新增留言</title>
    <script type="text/javascript" src="../../js/common.js"></script>
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">


</head>
<body>

<form action="#" method="post">
    <table class="table table-bordered">
        <tr >
            <td colspan="2">
                <b>留言内容</b>
            </td>
        </tr>
        <tr>
            <td><span>标题</span></td>
            <td><input type="text" class="form-control" name="title" style="width: 50%"></td>
        </tr>
        <tr>
            <td><span>内容</span></td>
            <td><textarea name="content" class="form-control" style="width: 90%;height: 100px;"></textarea></td>
        </tr>
        <tr>
            <td><span>日期</span></td>
            <td></td>
        </tr>
        <tr>
            <td><span>留言人</span></td>
            <td>zy</td>
        </tr>
        <tr>
            <td></td>
            <td><input type="reset" value="取消填写">&nbsp;<input type="submit" value="保存填写"></td>
        </tr>
    </table>
</form>
</div>
</div>
</body>
</html>
