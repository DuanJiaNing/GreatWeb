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

</head>
<body>
<form>
    <br>
    <p class="text-center">
        <input type="submit" value="添加文档">
        <input type="submit" value="我的文档">
        <input type="submit" value="稿件审核">
        <input type="submit" value="栏目管理">
        <input type="submit" value="更新列表">
        <input type="submit" value="更新文档">
        <input type="submit" value="文章回收站">
    </p>

    <table class="table table-striped">
        <tr>
            <td colspan="6"><span>文档列表</span></td>
        </tr>
        <tr>
            <th>ID</th>
            <th>选择</th>
            <th>文章标题</th>
            <th>录入时间</th>
            <th>发布人</th>
            <th>操作</th>
        </tr>
        <tr>
            <td colspan="6">
                <input type="submit" value="全选">
                <input type="submit" value="取消">
                <input type="submit" value="更新">
                <input type="submit" value="审核">
                <input type="submit" value="推荐">
                <input type="submit" value="移动">
                <input type="submit" value="删除">
            </td>
        </tr>
        <tr>
            <td colspan="6"></td>
        </tr>
    </table>

    <p class="text-center">
        搜索条件： <select>
        <option>选择类型...</option>
    </select>
        关键字：
        <input type="text" >
        <select>
            <option>排序...</option>
        </select>
        <input type="button" value="搜索" disabled="disabled">
    </p>
</form>
</body>
</html>
