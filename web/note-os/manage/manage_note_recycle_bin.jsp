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
    <title>留言回收站</title>
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
    <button type="button" class="btn btn-default">添加文档</button>
    <button type="button" class="btn btn-default">我的文档</button>
    <button type="button" class="btn btn-default">稿件审核</button>
    <button type="button" class="btn btn-default">栏目管理</button>
    <button type="button" class="btn btn-default">更新列表</button>
    <button type="button" class="btn btn-default">更新文档</button>
    <button type="button" class="btn btn-default">文章回收站</button>
</div>

<div class="page-header">
    <h4><b>留言列表</b><small>&nbsp;&nbsp;(20条)</small></h4>
</div>

<table class="table table-bordered table-hover" id="">
    // 一页5条
    <tr>
        <th>编号</th>
        <th>文章标题</th>
        <th>录入时间</th>
        <th>发布人</th>
        <th>操作</th>
    </tr>
</table>

<center>
    <nav>
        <ul class="pagination">
            <li>
                <a href="#" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <li><a href="#">1</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li>
                <a href="#" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>
</center>

<div class="btn-group" role="group">
    <button type="button" class="btn btn-default">全选</button>
    <button type="button" class="btn btn-default">取消</button>
    <button type="button" class="btn btn-default">更新</button>
    <button type="button" class="btn btn-default">审核</button>
    <button type="button" class="btn btn-default">推荐</button>
    <button type="button" class="btn btn-default">移动</button>
    <button type="button" class="btn btn-default">删除</button>
</div>
<br>
<br>

<p class="text-center" style="background-color: #F5FFD8">

    搜索条件： <select>
    <option>选择类型...</option>
</select>
    关键字：
    <input type="text">
    <select>
        <option>排序...</option>
    </select>
    <button type="button" class="btn btn-default"><span class="glyphicon glyphicon-search"></span></button>
</p>
</body>
</html>
