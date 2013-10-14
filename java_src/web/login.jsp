<%@page pageEncoding="UTF-8"%>
<%@page import="com.htsoft.core.util.AppUtil"%>

<%@page import="org.apache.commons.lang.StringUtils"%><html>
	<head>
		<title>欢迎登录继续医学教育管理系统</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ext3/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ext3/resources/css/ext-patch.css" />
		<link href="login/skin/style.css" rel="stylesheet" type="text/css" id="skin"/>
		
		<%
		response.addHeader("__timeout","true");
		String codeEnabled=(String)AppUtil.getSysConfig().get("codeConfig");
		String dyPwdEnabled = (String)AppUtil.getSysConfig().get("dynamicPwd");
		if(StringUtils.isEmpty(codeEnabled)){//若当前数据库没有配置验证码参数
			codeEnabled="close";//代表需要输入
		}
		if(StringUtils.isEmpty(dyPwdEnabled)){//若当前数据库没有配置动态密码参数
			dyPwdEnabled="close";//代表需要输入
		}
		%>
		<script type="text/javascript">
			var __ctxPath="<%=request.getContextPath() %>";
            //%=AppUtil.getCompanyLogo()%
			var __loginImage=__ctxPath+"/images/ahlogo.jpg";
		</script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/adapter/ext/ext-base.gzjs"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ext-all.gzjs"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/App.Login.js"></script>

<script>
Ext.onReady(function(){
if(!Ext.isChrome){
Ext.Msg.show({
   title:'是否改变浏览器',
   msg: '尊敬的用户，为获得最好的用户体验和速度，我们友情提示您使用google的Chrome浏览器，是否马上下载安装?',
   buttons: Ext.Msg.YESNOCANCEL,
   fn:function(btn){  
            //这个会弹出yes、no、cancel，而不是maybe等   
            //Ext.Msg.alert("you clicked :", btn);  
            switch(btn){  
            case "yes":  
                window.location.href='http://www.google.cn/chrome/intl/zh-CN/landing_chrome.html';                   
                break;  
            case "no":
                  
                break;  
            case "cancel": 
               break;  
           };  
        },
   icon: Ext.MessageBox.QUESTION
});
};
});


</script>

<script>

   function login(){
     if(event.keyCode==13){
     App.LoginHandler()
     }
   }
   
 function toRegiste(){
	window.location.href = __ctxPath + '/registe.jsp';
}
   
</script>


        
<!--居中显示end-->
<style>
/*提示信息*/	
#cursorMessageDiv {
	position: absolute;
	z-index: 99999;
	border: solid 1px #cc9933;
	background: #ffffcc;
	padding: 2px;
	margin: 0px;
	display: none;
	line-height:150%;
}
/*提示信息*/
</style>

	</head>
<body onkeydown="login()">
	<div class="login_main">
		<div class="login_top">
			<div class="login_title1"></div> <!-- 管理系统登录 -->
			<div class="login_title"></div>
		</div>
		<div class="login_middle">
			<div class="login_middleleft"></div>
			<div class="login_middlecenter">
					<form id="login_form" action="" class="login_form">
					<div class="login_user"><input id="username" type="text"></div>
					<div class="login_pass"><input id="password" type="password"></div>
					<div class="clear"></div>
					<div class="login_button">
						<div class="login_button_left"><input type="button" value=""  onclick="App.LoginHandler()" onfocus="this.blur()"/></div>
						<div class="login_button_right"><input type="button" value="" onclick="toRegiste()" onfocus="this.blur()"/></div>
						<div class="clear"></div>
					</div>
					</form>
					
			</div>
			<div class="login_middleright"></div>
			<div class="clear"></div>
		</div>
		<div class="login_bottom">
			<!--
			<div class="login_copyright">IISIIP3.0   数据工程一体化平台  COPYRIGHT 2012 @ www.bonait.com</div>
			-->
			<div class="login_copyright">继续医疗教育系统  COPYRIGHT 2013 @ http://tfpc.oldcp.com/ </div>
			
		</div>
	</div>
</body>
</html>