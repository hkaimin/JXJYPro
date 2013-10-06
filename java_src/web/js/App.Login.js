Ext.ns("App");
/**
 * jsp页面用户登录信息验证处理方法
 */
App.LoginHandler = function() {
	var username = document.getElementById("username");
	var password = document.getElementById("password");
	var checkCode = document.getElementById("checkCode");
	if(username.value=="") {
		Ext.Msg.alert("操作信息", "请输入登录用户名！");
		return;
	} else if(password.value=="") {
		Ext.Msg.alert("操作信息", "请输入登录用户密码！");
		return;
	}
	var params = "username="+username.value+"&password="+password.value;
		if(checkCode!=null && checkCode.value==""){
			Ext.Msg.alert("操作信息", "请输入验证码！");
			return;
		}
		//params += "&checkCode="+checkCode.value;
	
	var extLoadObj = new Ext.LoadMask(Ext.getBody(), {msg:'正在验证登录信息，请稍后......'});
    extLoadObj.show();
    
	Ext.Ajax.request( {
		url : __ctxPath + "/login.do",
		params : params,
		method : "POST",
		success : function(response, action) {
			var result = Ext.util.JSON.decode(response.responseText);
			handleLoginResult(result);
			extLoadObj.hide();
            extLoadObj = null;
		},
		failure : function(response, action) {
			var result = Ext.util.JSON.decode(response.responseText);
			handleLoginResult(result);
			document.getElementById("password").value="";
			document.getElementById("username").focus();
			ExtLoadObj.hide();
            extLoadObj = null;
		}
	});
}

/**
 * 处理登录结果
 * @param {} result
 */
function handleLoginResult(result) {
	if (result.success) {
		window.location.href = __ctxPath + '/index.jsp'
	} else {
		//if(__codeEnabled=="1") 
			//refeshCode();//刷新验证码
		Ext.MessageBox.show({
			title : '操作信息',
			msg : result.msg,
			buttons : Ext.MessageBox.OK,
			icon : Ext.MessageBox.ERROR
		});
	}
}
/**
 * 刷新验证码
 */
function refeshCode() {
	var loginCode = document.getElementById('loginCode');
	loginCode.innerHTML = '<img align="middle" border="0" height="23" width="100" onclick="refeshCode()" class="codeImg" alt="点击切换图片" src="'
			+ __ctxPath + '/CaptchaImg?rand=' + Math.random() + '"/>';
}

function resetForm(){
    document.getElementById("username").value = "";
	document.getElementById("password").value = "";
	document.getElementById("checkCode").value = "";
}
