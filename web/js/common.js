//--------------------------------------------------------------------------home_page:data load
var Note = {
    id: -1,
    title: '',
    content: '',
    dateTime: '',
    userId: -1
}

var User = {
    id: -1,
    name: '',
    password: '',
    state: -1,
    age: -1
}

var notes;
var users;

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
        var json = JSON.parse(jsonText);

    })
}

// 加载所有用户
function loadAllUsers() {
    loadData(2, function (jsonText) {
        var json = JSON.parse(jsonText);

    });
}

// 加载所有的留言和用户
function loadAllNotesAndUsers() {
    loadAllNotes();
    loadAllUsers();
}

// 获得指定用户的所有留言
function getUserNotes(uid) {

}

// 将指定留言显示到列表中
function loadNotesTable(notes) {

}

// 清空表格
function clearNotesTable() {

}

// 过滤留言并刷新页面(留言列表)
function filterNotes(uid) {
    clearNotesTable();

    if (uid === -1) { // 显示所有留言
        loadNotesTable(notes);
    } else {
        var ns = getUserNotes(uid);
        loadNotesTable(ns);
    }
}

// 修改选中的用户(参与人员)
function setUserSelected(uid) {
    if (uid === -1) { // 全部
    } else {
    }
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
