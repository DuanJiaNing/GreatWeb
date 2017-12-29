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
    <title>留言详情</title>
    <link rel="stylesheet" href="../css/home_note_detail.css"/>
    <script type="text/javascript" src="../js/common.js"></script>
    <script type="text/javascript" src="../js/jQuery/jquery-1.11.0.js"></script>

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
                        <p class="lead"><u>
                        </u></p>
                    </td>
                </tr>

                <tr align="right">
                    <td>
                        <h4>
                            <small>&nbsp;发表时间:<span id="time"></span>
                            </small>
                        </h4>
                    </td>
                </tr>
            </table>

            <blockquote>
                <p id="content">
                </p>
                <footer>发表人: <cite id="user"> </cite></footer>
            </blockquote>

        </div>
    </div>
    <div style="height: 10px;width: 100%"></div>
    <div class="foot_right_botttom">
        <p>版权所有&copy;成都青春</p>
        <p>20160913java开发班</p>
    </div>

    <script type="text/javascript">
        $().ready(function () {
            $.ajax({
                type: 'post',
                url: 'dataObtain.do?category=3&noteId=' +${param.noteId},
                dataType: 'json',
                success: function (json) {
                    $('p.lead').html(json.title);
                    $('#content').html(json.content);
                    $('#time').html(getDate(json.dateTime));
                    loadUser(json.userId);
                },
                error: function () {
                    $('p.lead').html('获取内容出错');
                    $('#content').html('获取内容出错');
                }
            });
        })

        function loadUser(userId) {
            $.getJSON('dataObtain.do?category=4&userId=' + userId, function (json) {
                $('#user').html(json.name);
            })
        }

    </script>
</div>
</body>
</html>
