function verifyIdentfyCode() {
	var inputCode = document.getElementById("identifyCode").value;
	var cookie = document.cookie.split(";");
	var code = "";
	for (var i = 0; i < cookie.length; i++) {
		var arr = cookie[i].split("=");
		//找到名称为userId的cookie，并返回它的值
		if ("identifyCode" == arr[0] || " identifyCode" == arr[0]) {
			code = arr[1];
			break;
		}
	}
	var current = true;
	for (var i = 0; i < code.length; i++) {
		var ch = code.charAt(i);
		var chI = inputCode.charAt(i);
		if (ch != chI.toLowerCase() && ch != chI.toUpperCase()) {
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
	window.location = "login.jsp";
//	document.getElementById("identifyCode").src = "identifyCode.do?random=" + new Date().getTime();
}

function verifyUsernameUsed() {
	var name = document.getElementById("username").value;
	var ajaxRequest = new XMLHttpRequest();
	// 与服务器建立连接
	var path = document.get
	// FIXME com.duan.controller.servlet.RegisterServlet 异常 ClassNotFindException
	ajaxRequest.open('post', 'register.do?method=checkNameUsed&username=' + name, true);
	//发送请求
	ajaxRequest.send();
	ajaxRequest.onreadystatechange = function() {
		if (ajaxRequest.readyState == 4) {
			var state = ajaxRequest.status;
			switch (state) {
			case 200: // 接收到结果
				var result = ajaxRequest.responseText;
				if (result === 'true') {
					document.getElementById("info").innerHTML = "用户名已经被使用";
					return false;
				} else {
					document.getElementById("info").innerHTML = "";
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
	var errorInfo = document.getElementById("info").value;
	if (!isEmpty(errorInfo)) {
		return;
	}

	if (!verifyPassword() || !verifyAge()) {
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

function deleteRow(index) {
	var tab = document.getElementById("table_book");
	tab.deleteRow(index);
}


function addRowClass(index, name) {
	var tab = document.getElementById("table_book");
	tab.rows[index].className = name;
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

function isEmpty(str) {
	if (str !== null && str !== undefined && str !== '') {
		return false;
	} else {
		return true;
	}
}

function info(info) {
	var e = document.getElementById("info");
	e.innerHTML = info;
}