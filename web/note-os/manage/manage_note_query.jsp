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
    <title>查询留言</title>
    <script type="text/javascript" src="../../js/common.js"></script>
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <style>
        button {
            background-color: #F1F8B4;
        }
    </style>
</head>
<body style="padding: 5px">

<div class="btn-group" role="group" style="float: right">
    <button type="button" class="btn btn-default" disabled="disabled">添加文档</button>
    <button type="button" class="btn btn-default" disabled="disabled">我的文档</button>
    <button type="button" class="btn btn-default" disabled="disabled">稿件审核</button>
    <button type="button" class="btn btn-default" disabled="disabled">栏目管理</button>
    <button type="button" class="btn btn-default" disabled="disabled">更新列表</button>
    <button type="button" class="btn btn-default" disabled="disabled">更新文档</button>
    <button type="button" class="btn btn-default" disabled="disabled">文章回收站</button>
</div>

<div class="page-header">
    <h4><b>留言列表</b>
        <small>&nbsp;&nbsp;(<span id="notesCount">20</span>条)</small>
    </h4>
</div>

<table class="table table-bordered table-hover" id="pageNotesTable">
    <tr style="background-color: #efefef">
        <th style="text-align: center;">编号</th>
        <th>文章标题</th>
        <th>录入时间</th>
        <th>发布人</th>
        <th style="text-align: center;">操作</th>
    </tr>
</table>

<center>
    <nav>
        <ul class="pagination">
            <li>
                <a href="javaScript:prePage()" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <li id="pageIndexFirst" class="active"><a href="javaScript:">1</a></li>
            <li id="pageIndex2"><a href="javaScript:">2</a></li>
            <li id="pageIndex3"><a href="javaScript:">3</a></li>
            <li id="pageIndex4"><a href="javaScript:">4</a></li>
            <li id="pageIndexLast"><a href="javaScript:">5</a></li>
            <li>
                <a href="javaScript:nextPage()" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>
</center>

<div class="btn-group" role="group">
    <button type="button" class="btn btn-default">全选</button>
    <button type="button" class="btn btn-default">全不选</button>
    <button type="button" class="btn btn-default" disabled="disabled">更新</button>
    <button type="button" class="btn btn-default">批量删除</button>
</div>
<br>
<br>

<p class="text-center" style="background-color: #F5FFD8">
    搜索条件：
    <select>
        <option>选择类型</option>
    </select>

    关键字：
    <input type="text">

    <select>
        <option>排序</option>
    </select>
    <button type="button" class="btn btn-default"><span class="glyphicon glyphicon-search"></span></button>
</p>

<script type="text/javascript">
    loadAllNotesAndUsersWithPage(true);
</script>

</body>
</html>
