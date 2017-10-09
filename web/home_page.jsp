<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head lang="en">
    <base href="<%=basePath%>">

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">

    <link rel="stylesheet" type="text/css" href="css/home_page.css">
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <script type="text/javascript" charset="UTF-8" src="js/common.js"></script>
    <title>首页</title>
</head>

<body>

<div class="all">
    <div class="top">
        <img src="img\header.png" alt=""/>
        <div class="top_bottom">
            <form action="">
                <input type="button" value="刷新列表" onclick="loadAllNotesAndUsers()"/>
                <input type="button" value="后台管理" onclick="javascript:document.location='login.jsp'"/>
            </form>
        </div>
    </div>
    <div class="foot">
        <div class="foot_left">
            <div class="a">
                <b>参与人员</b>
            </div>
            <div class="b">
                <br/>
                <hr style="border-top:1px solid #C3C3C3;width: auto;margin-top: -2px"/>
                <table class="table table-hover" id="userTable">
                    <!--用户名 留言条数 3条留言的标题 -->
                </table>
            </div>
            <div class="c">
                <!--<img src="img\left_bottom.gif" alt="bottom"/>-->
            </div>
        </div>
        <div class="foot_right">
            <div style="height: 20px;padding-top: 20px" id="notesTitle">
            </div>
            <hr>

            <div style="width:100%;height: 305px;overflow-y: scroll">
                <table id="notesTable" class="table table-hover">
                    <%-- 标题 内容 时间--%>
                    <tr style="padding-left: 30px;padding-right: 30px">
                        <td>
                            <h4>h4. Bootstrap heading
                                <small>2016/03/05</small>
                            </h4>
                            <p>
                            <h5>&nbsp;&nbsp;&nbsp;&nbsp;中国中产阶层壮大 人均财富排名升至全球第27位 undefined
                                陆军司令员韩卫国给新兵写信 自述曾因吃罐头被批 熊猫滚滚寄出2.1万份邀请函 邀请重庆西安市民来过节
                            </h5>
                            </p>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <div style="height: 20px;width: 73%;float: right"></div>
        <div class="foot_right_bottom">
            <br>
            <p>版权所有&copy;成都青春</p>
            <p>20160913java开发班</p>
        </div>
    </div>
</div>
<script type="text/javascript">
    loadAllNotesAndUsers();//加载数据
    filterNotes(-1); //显示所有留言
</script>
</body>
</html>
