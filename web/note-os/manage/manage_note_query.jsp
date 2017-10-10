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
    <link rel="stylesheet" href="../../css/manage/manage_note_query.css"/>
    <script type="text/javascript" src="../../js/common.js"></script>
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

</head>
<body>

<form>
    <table id="table1">
        <tr id="tr1">
            <td class="tdt">
                <input type="submit" value="添加文档">
                <input type="submit" value="我的文档">
                <input type="submit" value="稿件审核">
                <input type="submit" value="栏目管理">
                <input type="submit" value="更新列表">
                <input type="submit" value="更新文档">
                <input type="submit" value="文章回收站">
            </td>
        </tr>
    </table>

    <table class="table table-striped">
        <tr id="tr2">
            <td colspan="6" class="tdd"><span>文档列表</span></td>
        </tr>
        <tr>
            <th class="tdt"><span>ID</span></th>
            <th class="tdt"><span>选择</span></th>
            <th class="tdt"><span>文章标题</span></th>
            <th class="tdt"><span>录入时间</span></th>
            <th class="tdt"><span>发布人</span></th>
            <th class="tdt"><span>操作</span></th>
        </tr>
        <tr id="tr3">
            <td colspan="6" class="tdd">
                <input type="submit" value="全选">
                <input type="submit" value="取消">
                <input type="submit" value="更新">
                <input type="submit" value="批量删除">
            </td>
        </tr>
        <tr id="tr4">
            <td colspan="6"></td>
        </tr>
    </table>
    <table id="table3">
        <tr id="tr5">
            <td class="tdt">
                <span>搜索条件：</span>
                <select>
                    <option>选择类型...</option>
                </select>
                <span>关键字：</span>
                <input type="text" >
                <select>
                    <option>排序...</option>
                </select>
                <input type="button" value="搜索" disabled="disabled">
            </td>
        </tr>
    </table>
</form>

</body>
</html>
