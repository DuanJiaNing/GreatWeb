//--------------------------------------------------------------------------home_page:data load
var jsonNotes;
var jsonUsers;

// 从后台异步加载数据
function manipulateDataAsyn(url, data, callback, asyn) {

    var xmlRequest = new XMLHttpRequest();
    xmlRequest.open("post", url,asyn);
    if (isNull(data)) {
        xmlRequest.send();
    } else {
        xmlRequest.send(data);
    }
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
    manipulateDataAsyn('notesObtain.do?category=1', null, function (jsonText) {
        jsonNotes = JSON.parse(jsonText);

        // ajax 同时请求时结果会被合并，用这种方式串行获取
        loadAllUsers();
    }, true)
}

// 加载所有用户
function loadAllUsers() {
    manipulateDataAsyn('notesObtain.do?category=2', null, function (jsonText) {
        jsonUsers = JSON.parse(jsonText);

        // 显示【全部】
        showAllUsers();
        filterAndShowNotes(-1);
    }, true);
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
        splashInfo('info');
        return;
    }

    if (!verifyPassword() || !verifyAge()) {
        splashInfo('info');
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
        splashInfo('info');
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
    }, 400);
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
    if (isEmpty(jsonUsers)) {
        return null;
    }

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

function getNote(noteId) {
    if (isEmpty(jsonNotes)) {
        return null;
    }

    for (var i = 0; i < jsonNotes.length; i++) {
        var note = jsonNotes[i];
        if (noteId === note.id) {
            return {
                id: note.id,
                title: note.title,
                content: note.content,
                dateTime: note.dateTime,
                userId: note.userId
            }
        }
    }
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

// 获得项目路径
function getRootPath() {
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName = window.document.location.pathname;

    var pos = curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPath = curWwwPath.substring(0, pos);

    //获取带"/"的项目名，如：/uimcardprj
    // var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

    return localhostPath+'/';
}

//---------------------------------------------------------------------------note-os: manage_os

function switchIframe(des) {
    var basePath = getRootPath();
    var iframe = document.getElementById('noteOsIframe');

    iframe.src = basePath + 'note-os/manage/' + des;
}

var pageRate = 7; // 一页5条留言
var currentPageIndex;// 页下标 如：第2页，数组的下标应为 [5 - 9]
var pageCount;

/**
 * 加载（刷新）所有笔记和用户(可选)到客户端
 * @param initPage boolean 初始化表格（仅第一次调用为 true）
 * @param loadUser boolean 加载用户
 * @param show boolean 是否显示（false 时仅加载数数据）
 * @param pageIndex 显示的页下标（只在 show 为 true 时被使用）
 */
function loadAllNotesAndUsersWithPage(initPage, loadUser, show, pageIndex) {
    manipulateDataAsyn('notesObtain.do?category=1', null, function (jsonText) { // 异步获取留言
        jsonNotes = JSON.parse(jsonText);
        updateNotesCount(jsonNotes.length);

        var alreadyShow = false;
        if (loadUser) {
            manipulateDataAsyn('notesObtain.do?category=2', null, function (jsonText) { // 异步获取用户
                jsonUsers = JSON.parse(jsonText);

                if (initPage) {
                    initNotesPageData();
                }

                if (show) {
                    currentPageIndex = pageIndex;
                    pageCount = Math.ceil(jsonNotes.length / pageRate);

                    showNotesWithPage(pageIndex);
                    alreadyShow = true;
                }
            },true);
        }

        if (show && !alreadyShow) {
            currentPageIndex = pageIndex;
            pageCount = Math.ceil(jsonNotes.length / pageRate);

            showNotesWithPage(pageIndex);
        }
    }, true)
}

function initNotesPageData() {
    var count = 0;
    while (count < pageRate) {
        addRow(count + 1);
        count++;
    }

    function addRow(rowIndex) {
        var table = document.getElementById('pageNotesTable');
        var newRow = table.insertRow(table.rows.length);
        newRow.style.height = '40px';
        newRow.dataset.checked = '0'; // 0 false ,1 true
        newRow.dataset.noteId = -1;
        newRow.onclick = function () {
            switchCheckState(rowIndex);
        }

        newRow.insertCell(0);
        newRow.insertCell(1);
        newRow.insertCell(2);
        newRow.insertCell(3);
        newRow.insertCell(4);
    }
}

// 更新留言条数
function updateNotesCount(count) {
    var ele = document.getElementById('notesCount');
    ele.innerHTML = count;
}

// 页指示点具体页点击时定位到该页
function locationAtPage(token) {
    var ele = document.getElementById('pageIndex' + token);
    currentPageIndex = ele.firstChild.innerHTML - 1;
    showNotesWithPage(currentPageIndex);
}

var indicatorCount = 5;// 指示点一次显示5个页标(只能是 5 个，不能修改。要修改需要先修改 .jsp)
// 更新页指示点上的数字（页码）
function updatePageIndicator() {
    var indicatorLast = document.getElementById('pageIndexLast');
    var indicatorFirst = document.getElementById('pageIndexFirst');

    var first = indicatorFirst.firstChild.innerHTML - 1;
    var last = indicatorLast.firstChild.innerHTML - 1;
    var indicatorFirstIndex = 0;
    var indicatorLastIndex = indicatorCount - 1;

    if (currentPageIndex === last + 1 && currentPageIndex <= pageCount - 1) { // 重新为指示点赋值(增加)
        modify(first + 2, indicatorLastIndex);
    } else if (currentPageIndex === first - 1 && currentPageIndex >= 0) {// 重新为指示点赋值(减少)
        modify(first, indicatorFirstIndex)
    } else { // 在中间，直接移动即可
        modify(first + 1, currentPageIndex - first);
    }

    function modify(first, activeIndex) {
        var indicators = [
            document.getElementById('pageIndexFirst'),
            document.getElementById('pageIndex2'),
            document.getElementById('pageIndex3'),
            document.getElementById('pageIndex4'),
            document.getElementById('pageIndexLast')
        ];

        // 只有在页数小余 5 时，lastEnableIndex 才会小余 lastPage - 1，否则都为 lastPage - 1,
        // TODO 待测试
        var len = indicators.length;
        var lastPage = first + len - 1;
        var lastEnableIndex = lastPage > pageCount ?
            len - (lastPage - pageCount) - 1 : lastPage - 1;

        for (var i = 0; i < indicators.length; i++) {
            indicators[i].firstChild.innerHTML = first + i;
            if (activeIndex === i) {
                indicators[i].className = 'active'
            } else if (i > lastEnableIndex) {
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

    // FIXME 翻页是选中状态将被清空
    cancelAllCheck();

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
            var user = getUser(jsonNotes[jsonIndex].userId);
            if (user === null) {
                return;
            }

            name = user.name;

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

// 修改留言列表的具体行，rowIndex: 1 ~ pageRate (0 行被表头占据)
function modifyRow(rowIndex, No, title, time, userName, noteId) {
    // 第一行被表头占据
    if (rowIndex < 1 || rowIndex > pageRate) {
        return;
    }

    var table = document.getElementById('pageNotesTable');
    var row = table.rows[rowIndex];
    row.dataset.noteId = noteId;

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
        cellOpt.innerHTML = "<a href='javaScript:modifyNote(" + noteId + ")'>编辑</a> | <a href='javaScript:deleteNote(" + noteId + ")'>删除</a>"
    }

}

function deleteNote(noteId){
    if(confirm('确认删除')){
        // 要传数组
        deleteNoteAsyn([noteId],true);
    }
}

// 上一页
function prePage() {
    if (currentPageIndex > 0) {
        showNotesWithPage(currentPageIndex - 1);
    }
}

// 下一页
function nextPage() {
    if (currentPageIndex < pageCount - 1) {
        showNotesWithPage(currentPageIndex + 1);
    }
}

function switchCheckState(rowIndex) {
    var row = document.getElementById('pageNotesTable').rows[rowIndex];
    var checked = row.dataset.checked;
    if (checked === '1') {
        row.style.backgroundColor = rowNotCheckColor;
        row.dataset.checked = '0';
    } else {
        // row.style.backgroundColor = "#17bce8";
        row.style.backgroundColor = rowCheckColor;
        row.dataset.checked = '1';
    }
}

var rowCheckColor = 'rgba(23, 188, 232, 0.24)';
var rowNotCheckColor = 'transparent';

function checkAll() {
    changeAllRowCheckState(true);
}

function checkRow(rowIndex) {
    if(rowIndex < 1 || rowIndex > pageRate - 1){
        return;
    }

    var table = document.getElementById('pageNotesTable');
    var row = table.rows[rowIndex];
    row.style.backgroundColor = rowCheckColor;
    row.dataset.checked = '1';
}

function cancelAllCheck() {
    changeAllRowCheckState(false);
}

function changeAllRowCheckState(check) {
    var table = document.getElementById('pageNotesTable');
    for (var i = 1; i < table.rows.length; i++) {
        var row = table.rows[i];

        if (check && row.dataset.noteId !== -1) {
            row.style.backgroundColor = rowCheckColor;
            row.dataset.checked = '1';
        } else {
            row.style.backgroundColor = rowNotCheckColor;
            row.dataset.checked = '0';
        }
    }
}

function batchDelete() {
    var table = document.getElementById('pageNotesTable');

    var count = 0;
    var deleteNoteIds = new Array();
    for (var i = 1; i < table.rows.length; i++) {
        var row = table.rows[i];
        var checked = row.dataset.checked === '1';
        if (checked) {
            var noteId = row.dataset.noteId;
            deleteNoteIds[count] = noteId;
            count++;
        }
    }

    if (count > 0) {
        if (confirm('确认删除选中的' + count + '条留言')) {
            deleteNoteAsyn(deleteNoteIds, true);
        }
    } else {
        alert('请先选中要删除的留言');
    }

}

// TODO
function modifyNote(noteId) {

}

function deleteNoteAsyn(noteIds, reloadAfterDone) {
    if (isNull(noteIds) || noteIds.length === 0) {
        return;
    }

    var param = '';
    for (var i = 0; i < noteIds.length; i++) {
        var id = noteIds[i];
        if (i === 0) {
            param += id;
        } else {
            param += '*' + id;
        }
    }

    manipulateDataAsyn('noteControl.do?category=1&noteIds=' + param, null, function (responseData) {
        var json = JSON.parse(responseData);
        if (json.code === 1) {
            // alert('删除成功');
            cancelAllCheck();
        } else {
            alert('删除失败：' + json.result);
        }

        if (reloadAfterDone) {
            loadAllNotesAndUsersWithPage(false, false, true, currentPageIndex);
        }
    },true);
}

function refreshData() {
    loadAllNotesAndUsersWithPage(false, false, true, 0);
}

//---------------------------------------------------------------------------note-os: manage_note_new.jsp

// 异步添加留言
function addNote(userId) {
    var tiEl = document.getElementById('noteTitle');
    var coEl = document.getElementById('noteContent');

    var title = tiEl.value;
    var content = coEl.value;
    var dateTime = new Date().getTime();

    // 字符串于数字比较
    if (isEmpty(title) || isEmpty(content) || userId == -1) {
        // TODO 输入不规范或用户未正确登陆
        alert('输入不规范或用户未正确登陆');
    } else {
        manipulateDataAsyn('noteControl.do?category=2',
            '{"title": "' + encodeURIComponent(title) + '","content": "' + encodeURIComponent(content) + '","dateTime": "' + dateTime + '","userId": "' + userId + '"}',
            function (responseData) {
                var json = JSON.parse(responseData);
                if (json.code === 1) {
                    alert('添加成功');
                    // switchIframe('manage_note_query.jsp');
                } else {
                    alert('添加失败: ' + json.result);
                }
            },true);
    }
}

