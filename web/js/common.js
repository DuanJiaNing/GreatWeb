//--------------------------------------------------------------------------home_page:data load
var jsonNotes;
var jsonUsers;

// 从后台异步加载数据
function loadData(cate, callback) {

    var xmlRequest = new XMLHttpRequest();
    xmlRequest.open("post", 'notesObtain.do?category=' + cate);
    xmlRequest.send();
    xmlRequest.onreadystatechange = function () {
        if (xmlRequest.readyState === 4) {
            var state = xmlRequest.status;
            switch (state) {
                case 200: // 接收到结果
                    callback(xmlRequest.responseText);
                    break;
                default:
                    break;
            }
        }
    }
}

// 加载所有留言
function loadAllNotes() {
    loadData(1, function (jsonText) {
        jsonNotes = JSON.parse(jsonText);

        // ajax 同时请求时结果会被合并，用这种方式串行获取
        loadAllUsers();
    })
}

// 加载所有用户
function loadAllUsers() {
    loadData(2, function (jsonText) {
        jsonUsers = JSON.parse(jsonText);

        // 显示【全部】
        showAllUsers();
        filterAndShowNotes(-1);
    });
}

// 显示所有用户
function showAllUsers() {

    addRow('全部留言', jsonNotes.length, -1);
    for (var i = 0; i < jsonUsers.length; i++) {
        var name = jsonUsers[i].name;
        var count = getUserNotesCount(jsonUsers[i].id);
        var randomAvatar = getRandomAvatar();

        addRow(name, count, jsonUsers[i].id, randomAvatar);
    }

    function addRow(name, count, userId, avatar) {
        var userTable = document.getElementById("userTable");
        var newRaw = userTable.insertRow(userTable.rows.length);
        var cellAvatar = newRaw.insertCell(0);
        var cellName = newRaw.insertCell(1);
        var cellCount = newRaw.insertCell(2);

        newRaw.onclick = function () {
            filterAndShowNotes(userId);
        };

        cellAvatar.style.verticalAlign = 'center';
        cellName.innerHTML = "<h5><b>" + name + "</b></h5>"
        cellCount.innerHTML = "<h6>" + count + "条</h6>"
        if (!isNull(avatar)) {
            cellAvatar.innerHTML = "<img src='" + avatar + "' class='avatar'>";
        }
    }
}

// 获得指定用户发表的留言条数
function getUserNotesCount(userId) {
    var count = 0;
    for (var i = 0; i < jsonNotes.length; i++) {
        var note = jsonNotes[i];
        if (userId === note.userId) {
            count++;
        }
    }
    return count;
}

// 过滤留言并刷新页面(留言列表)
function filterAndShowNotes(userId) {
    clearTable('notesTable');

    var tit = document.getElementById("notesTitle");
    var des = userId === -1 ? "全部留言" : getUser(userId).name;
    var count = userId === -1 ? jsonNotes.length : getUserNotesCount(userId);
    tit.innerHTML = "<h5><b>&nbsp;&nbsp;" + des + "</b><small>&nbsp;&nbsp;(" + count + "条)</small></h5>";

    for (var i = 0; i < jsonNotes.length; i++) {
        var note = jsonNotes[i];
        if (userId === note.userId || userId === -1) {
            addRow(
                note.title,
                note.content,
                getDate(note.dateTime),
                note.id);
        }
    }

    function addRow(title, content, date, noteID) {
        var table = document.getElementById("notesTable");
        var newRaw = table.insertRow(table.rows.length);
        var cell = newRaw.insertCell(0);

        newRaw.style.paddingLeft = 30;
        newRaw.style.paddingRight = 30;
        newRaw.onclick = function () {
            window.location.href = 'note-os/home_note_detail.jsp?noteID=' + noteID;
        };

        cell.innerHTML = "<h5><b>" + title + "</b></h5>" +
            "<p><h5>&nbsp;&nbsp;&nbsp;&nbsp;" + content + "</h5></p>" +
            "<p class=\"text-right\" style='font-size: 0.65em;color: #bcbcbc'>" + date + "</p>";
    }
}

// 加载所有的留言和用户
function loadAllNotesAndUsers() {
    clearTable('userTable');
    clearTable('notesTable');

    loadAllNotes();
}

//---------------------------------------------------------------------------login & register:verify

function verifyIdentfyCode() {
    var inputCode = document.getElementById("identifyCode").value;
    var cookie = document.cookie.split(";");
    var code = "";
    for (var i = 0; i < cookie.length; i++) {
        var arr = cookie[i].split("=");
        // 注意 identifyCode 前面的空格
        if ("identifyCode" === arr[0] || " identifyCode" === arr[0]) {
            code = arr[1];
            break;
        }
    }
    var current = true;
    for (var i = 0; i < code.length; i++) {
        var ch = code.charAt(i);
        var chI = inputCode.charAt(i);
        if (ch !== chI.toLowerCase() && ch !== chI.toUpperCase()) {
            current = false;
            break;
        }
    }

    if (current) {
        info("");
        return true;
    } else {
        info("验证码错误");
        return false;
    }
}

function refreshIdentifyCode() {
    // window.location = "login.jsp";
    document.getElementById("identifyCodeImage").src = "identifyCode.do?random=" + new Date().getTime();
}

function verifyUsernameUsed(msgWhenUsed, msgWhenNotUse) {
    var name = document.getElementById("username").value;
    if (isEmpty(name)) {
        return;
    }

    var ajaxRequest = new XMLHttpRequest();
    // 与服务器建立连接
    // var path = document.get;
    // FIXME com.duan.controller.servlet.RegisterServlet 异常 ClassNotFindException
    ajaxRequest.open('post', 'register.do?method=checkNameUsed&username=' + name, true);
    //发送请求
    ajaxRequest.send();
    ajaxRequest.onreadystatechange = function () {
        if (ajaxRequest.readyState === 4) {
            var state = ajaxRequest.status;
            switch (state) {
                case 200: // 接收到结果
                    var result = ajaxRequest.responseText;
                    if (result === 'true') {
                        document.getElementById("info").innerHTML = msgWhenUsed;
                        return false;
                    } else {
                        document.getElementById("info").innerHTML = msgWhenNotUse;
                        return true;
                    }
                    break;
                default:
                    break;
            }
        }
    };
}

function register() {
    var errorInfo = document.getElementById("info").innerHTML;
    if (!isEmpty(errorInfo)) {
        splashInfo(info);
        return;
    }

    if (!verifyPassword() || !verifyAge()) {
        splashInfo(info);
        return;
    } else {
        var register = document.getElementById("register");
        // 用户验证由  LoginFilter 完成
        register.action = "register.do";
        register.submit();
    }
}

function login() {
    if (!verifyIdentfyCode() || !verifyUsername() || !verifyPassword()) {
        splashInfo(info);
        return;
    } else {
        var login = document.getElementById("login");
        // 用户验证由  LoginFilter 完成
        login.action = "login.do";
        login.submit();
    }
}

// FIXME 过滤非数字
function verifyPassword() {
    var text = document.getElementById("password").value;
    if (isEmpty(text)) {
        info("密码不能为空");
        return false;
    } else {
        info("");
    }

    var firstChar = text.charAt(0);
    if (!isNaN(parseInt(firstChar))) {
        info("密码不能以数字开头");
        return false;
    }

    if (text.length < 6) {
        info("密码太短");
        return false;
    }

    var icUpCase = false,
        icLowCase = false,
        icNumber = false;
    for (var i = 0; i < text.length; i++) {
        var ch = text.charAt(i);

        //大写
        if (!icUpCase && isNaN(parseInt(ch)) && ch === ch.toUpperCase()) {
            icUpCase = true;
        }

        //小写
        if (!icLowCase && isNaN(parseInt(ch)) && ch === ch.toLowerCase()) {
            icLowCase = true;
        }

        //数字
        if (!isNaN(parseInt(ch))) {
            icNumber = true;
        }

    }
    var re = icUpCase ? (icLowCase ? (icNumber ? "" : "数字") : "小写字母") : "大写字母";
    if (re !== "") {
        info("密码未包含" + re);
        return false;
    } else {
        info("");
        return true;
    }
}

function verifyUsername() {
    var text = document.getElementById("username").value;
    if (isEmpty(text)) {
        info("用户名不能为空");
        return false;
    } else {
        info("");
        return true;
    }
}

function verifyAge() {
    var text = document.getElementById("age").value;
    if (!isEmpty(text)) {
        var age = parseInt(text);
        if (age < 0) {
            info("年龄不能小余 0 ");
            return false;
        } else if (age > 130) {
            info("年龄不能大于 130 ");
            return false;
        } else {
            info("");
            return true;
        }
    }
}

//---------------------------------------------------------------------------common

function splashInfo(elementId) {
    var infoSpan = document.getElementById(elementId);
    infoSpan.style.backgroundColor = 'red';
    infoSpan.style.color = 'white';
    setTimeout(function endSplash() {
        infoSpan.style.backgroundColor = 'transparent'
        infoSpan.style.color = 'red';
    }, 200);
}

function info(info) {
    var e = document.getElementById("info");
    e.innerHTML = info;
}

function deleteRow(index) {
    var tab = document.getElementById("table_book");
    tab.deleteRow(index);
}


function addRowClass(index, name) {
    var tab = document.getElementById("table_book");
    tab.rows[index].className = name;
}

function isEmpty(str) {
    return str === null || str === undefined || str === '' || str === 'null';
}

function isNull(obj) {
    return obj === null || obj === undefined;
}

function clearTable(tableId) {
    var table = document.getElementById(tableId);
    if (isNull(table)) {
        return
    }

    while (table.rows.length > 0) {
        table.deleteRow(0);
    }
}

// 根据用户id获得用户信息
function getUser(userId) {
    for (var i = 0; i < jsonUsers.length; i++) {
        var user = jsonUsers[i];
        if (userId === user.id) {
            return {
                id: user.id,
                name: user.name,
                state: user.state,
                age: user.age,
                password: user.password
            };
        }
    }

    return "";
}

// long 转为 yyyy/mm/dd
function getDate(data) {
    var date = new Date(data);
    return date.getFullYear() + "/" + date.getMonth() + "/" + date.getDay();
}

// 获得一个随机头像（图片）
function getRandomAvatar() {
    var rootPath = 'img/avatar/';
    var avatars = [
        'avatar01.png',
        'avatar02.png',
        'avatar03.png',
        'avatar04.png',
        'avatar05.png',
        'avatar06.png',
        'avatar07.png',
        'avatar08.png',
        'avatar09.png',
        'avatar10.png',
        'avatar11.png',
        'avatar12.png',
        'avatar13.png',
        'avatar14.png'
    ];

    var random = Math.round(Math.random() * (avatars.length - 1));
    return rootPath + avatars[random];
}

//---------------------------------------------------------------------------note-os: manage_os

function switchIframe(des) {
    var iframe = document.getElementById('noteOsIframe');
    iframe.src = 'note-os/manage/' + des;
}

var pageRate = 5; // 一页5条笔记
var currentPageIndex;// 页下标 如：第2页，数组的下标应为 [5 - 9]
var pageCount;

function loadAllNotesAndUsersWithPage(initPage) {
    loadData(1, function (jsonText) { // 异步获取留言
        jsonNotes = JSON.parse(jsonText);

        loadData(2, function (jsonText) { // 异步获取用户
            jsonUsers = JSON.parse(jsonText);

            if (initPage) {
                initNotesPageData();
            }
            updateNotesCount(jsonNotes.length);
            showNotesWithPage(0);
        });
    })
}

function initNotesPageData() {
    currentPageIndex = 0;
    pageCount = Math.ceil(jsonNotes.length / pageRate);

    var count = 0;
    while (count < pageRate) {
        addRow();
        count++;
    }

    function addRow() {
        var table = document.getElementById('pageNotesTable');
        var newRow = table.insertRow(table.rows.length);
        newRow.style.height = '30px';

        newRow.insertCell(0);
        newRow.insertCell(1);
        newRow.insertCell(2);
        newRow.insertCell(3);
        newRow.insertCell(4);
    }
}

function updateNotesCount(count) {
    var ele = document.getElementById('notesCount');
    ele.innerHTML = count;
}

var indicatorCount = 5;// 指示点一次显示5个页标
function updatePageIndicator() {
    var indicatorLast = document.getElementById('pageIndexLast');
    var indicatorFirst = document.getElementById('pageIndexFirst');

    var first = indicatorFirst.firstChild.innerHTML - 1;
    var last = indicatorLast.firstChild.innerHTML - 1;
    var indicatorFirstIndex = 0;
    var indicatorLastIndex = indicatorCount - 1;

    var lastIn; // afterLast - pageCount
    var afterLast;
    var lastEnableIndex;

    if (currentPageIndex === last + 1 && currentPageIndex <= pageCount - 1) { // 重新为指示点赋值(增加)
        afterLast = currentPageIndex + 1 + indicatorLastIndex;
        lastIn = afterLast - pageCount;

        lastEnableIndex = lastIn  > 0 ? (pageCount - lastIn) - 1 :afterLast;
        modify(first + 2,indicatorLastIndex,lastEnableIndex);
    } else if (currentPageIndex === first - 1 && currentPageIndex >= 0) {// 重新为指示点赋值(减少)
        afterLast = currentPageIndex - 1 + indicatorLastIndex;
        lastIn = afterLast - pageCount;

        lastEnableIndex = lastIn  > 0 ? (pageCount - lastIn) - 1 :afterLast;
        modify(first,indicatorFirstIndex,lastEnableIndex)
    } else { // 在中间，直接移动即可
        afterLast = last;
        lastIn = afterLast - pageCount;

        lastEnableIndex = lastIn  > 0 ? (pageCount - lastIn) - 1 :afterLast;
        modify(first + 1,currentPageIndex - first,lastEnableIndex);
    }

    function modify(first,activeIndex,lastEnableIndex) {
        var indicators = [
            document.getElementById('pageIndexFirst'),
            document.getElementById('pageIndex2'),
            document.getElementById('pageIndex3'),
            document.getElementById('pageIndex4'),
            document.getElementById('pageIndexLast')
        ];

        for (var i = 0; i < indicators.length; i++) {
            indicators[i].firstChild.innerHTML = first + i;
            if(activeIndex === i){
                indicators[i].className = 'active'
            } else if (i > lastEnableIndex){
                indicators[i].className = 'disabled';
            } else {
                indicators[i].className = '';
            }
        }
    }

}

// 显示指定页
function showNotesWithPage(pageIndex) {
    if (pageIndex >= pageCount) {
        return;
    }

    currentPageIndex = pageIndex;
    updatePageIndicator();

    var index = currentPageIndex * pageRate;
    var sum = index + pageRate;
    for (var i = 0; i < pageRate; i++) {
        var rowIndex = i + 1;
        var jsonIndex = index;

        if (index < jsonNotes.length && index < sum) {
            var time = getDate(jsonNotes[jsonIndex].dateTime);
            var name = "未知用户";
            name = getUser(jsonNotes[jsonIndex].userId).name;

            modifyRow(
                rowIndex,
                jsonIndex + 1,
                jsonNotes[jsonIndex].title,
                time,
                name,
                jsonNotes[jsonIndex].id);

            index++;
        } else {
            modifyRow(rowIndex, '', '', '', '', -1);
        }
    }
}

// 第一行被表头占据
function modifyRow(rowIndex, No, title, time, userName, noteId) {
    if (rowIndex < 1 || rowIndex > pageRate) {
        return;
    }

    var table = document.getElementById('pageNotesTable');
    var row = table.rows[rowIndex];

    var cellNo_ = row.cells[0];
    var cellTitle = row.cells[1];
    var cellTime = row.cells[2];
    var cellUserName = row.cells[3];
    var cellOpt = row.cells[4];

    cellNo_.align = 'center';
    cellOpt.align = 'center';
    cellNo_.style.color = '#7c7c7c';
    cellTime.style.color = '#7c7c7c';

    cellNo_.style.width = '5%';
    cellTitle.style.width = '55%';
    cellTime.style.width = '13%';
    cellUserName.style.width = '15%';
    cellOpt.style.width = '12%';

    cellNo_.innerHTML = No;
    cellTitle.innerHTML = title;
    cellTime.innerHTML = time;
    cellUserName.innerHTML = userName;

    if (noteId === -1) {
        cellOpt.innerHTML = '';
    } else {
        cellOpt.innerHTML = "<a href='modifyNote(" + noteId + ")'>编辑</a> | <a href='deleteNote(" + noteId + ")'>删除</a>"
    }

}

function prePage() {
    if (currentPageIndex > 0) {
        showNotesWithPage(currentPageIndex - 1);
    }
}

function nextPage() {
    if (currentPageIndex < pageCount - 1) {
        showNotesWithPage(currentPageIndex + 1);
    }
}

function modifyNote(noteId) {

}

function deleteNote(noteId) {

}