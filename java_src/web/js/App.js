Ext.ns("App");

//首页PORTAL元素
var PortalItem=function(panelId,column,row){
   this.panelId=panelId;
   this.column=column;
   this.row=row;
};
//栏目PROTAL元素
var SectionItem = function(sectionId,colNumber,rowNumber){
	this.sectionId=sectionId;
	this.colNumber=colNumber;
	this.rowNumber=rowNumber;
};
//用户信息
var UserInfo=function(user){
	this.userId=user.userId;
	this.username=user.username;
	this.fullname=user.fullname;
	this.depId=user.depId;
	this.depName=user.depName;
	this.rights=user.rights;
	this.portalConfig=user.items;
	this.topModules=user.topModules;
};
//系统配置
var SysConfig=function(sysConfigs){
	this.dynamicPwd=sysConfigs.dynamicPwd;
};

//当前登录用户
var curUserInfo=null;
//取得当前系统配置
var sysConfigInfo=null

//检查当前用户有权访问funKey对应的功能
function isGranted(funKey){
	if(curUserInfo.rights.indexOf('__ALL')!=-1){
		return true;
	}
	if(curUserInfo.rights.indexOf(funKey)!=-1){
		return true;
	}
	return false;
}

App.init = function() {
	Ext.QuickTips.init();//这句为表单验证必需的代码
	Ext.BLANK_IMAGE_URL=__ctxPath+'/ext3/resources/images/default/s.gif';
	setTimeout(function() {
				Ext.get('loading').remove();
				Ext.get('loading-mask').fadeOut({remove:true});
				document.getElementById('app-header').style.display='block';
			}, 1000); 
	
	Ext.util.Observable.observeClass(Ext.data.Connection);
	Ext.data.Connection.on('requestcomplete', function(conn, resp,options ){
		if (resp && resp.getResponseHeader){
		    if(resp.getResponseHeader('__timeout')) {
		    	Ext.ux.Toast.msg('操作提示：','操作已经超时，请重新登录!');
	        	window.location.href=__ctxPath+'/index.jsp?randId=' + parseInt(1000*Math.random());
	    	}else if(resp.getResponseHeader('__403_error')){
	    		Ext.ux.Toast.msg('系统访问权限提示：','你目前没有权限访问：{0}',options.url);
	    	}
		}
	});
	Ext.data.Connection.on('requestexception',function(conn,resp,options){
		if (resp && resp.getResponseHeader){
			if(resp.getResponseHeader('__500_error')){
	    		Ext.ux.Toast.msg('后台出错','您访问的URL:{0}出错了，具体原因请联系管理员。',options.url);
	    	}else if(resp.getResponseHeader('__404_error')){
	    		Ext.ux.Toast.msg('后台出错','您访问的URL:{0}对应的页面不存在，具体原因请联系管理员。',options.url);
	    	}
		}
	});
	
	//userinfo 变量来自index.jsp
	var object=Ext.util.JSON.decode(userInfo);
	//取得当前登录用户的相关信息，包括权限
	var user=object.user;
	var conf=user.items;
	curUserInfo=new UserInfo(user);
	var sysConfigs=object.sysConfigs;
	sysConfigInfo=new SysConfig(sysConfigs);
	//显示主页
	var indexPage=new IndexPage();
	//显示应用程序首页
	//App.clickTopTab('ComIndexPage');
	App.clickTopTab('AppHome');//个人桌面
	
};

/**
 * 
 * @param {} id
 * @param {} callback 回调函数
 */
App.clickTopTab=function(id,params,precall,callback){
	if(precall!=null){
		precall.call(this);
	}
	var importId=id;
	if(id.indexOf('_')!=-1){//当传入的id为ABC_1;则会加载名为ABC的类，但其id却为ABC_1;
		importId=id.split('_')[0];
	}
	var tabs = Ext.getCmp('centerTabPanel');
	var tabItem = tabs.getItem(id);
	
	if (tabItem == null) {
		$ImportJs(importId, function(view) {
			tabItem = tabs.add(view);
			tabs.activate(tabItem);
		},params);
	}else {
		if(callback!=null){
			callback.call(this);
		}
		tabs.activate(tabItem);
	}
};
App.clickTopTabIframe = function(node){
	if (node.id == null || node.id == '' || node.id.indexOf('xnode') != -1) {
		return;
	}
	// alert(node.id);
	var tabs = Ext.getCmp('centerTabPanel');
	var tabItem = tabs.getItem(node.id);
	if (tabItem == null) {
		tabItem = tabs.add( {
			xtype : 'iframepanel',
			title : node.text,
			id : node.id,
			loadMask : {
				msg : '正在加载...,请稍等...'
			},
			iconCls : node.attributes.iconCls,
			defaultSrc : __ctxPath + '/pages/iframe/'+node.attributes.model+'/' + node.id + '.jsp?id='
					+ Math.random(),
			listeners : {
				domready : function(iframe) {
				}
			}
		});
	}
	tabs.activate(tabItem);
};
App.clickTopTabUrl = function(node){
	if (node.id == null || node.id == '' || node.id.indexOf('xnode') != -1) {
		return;
	}
	// alert(node.id);
	var tabs = Ext.getCmp('centerTabPanel');
	var _url = node.attributes.url;
	if(!(_url.substring(0,5)=="http:")){
		_url = __ctxPath + _url;
	}
	var tabItem = tabs.getItem(node.id);
	if (tabItem == null) {
		tabItem = tabs.add( {
			xtype : 'iframepanel',
			title : node.text,
			id : node.id,
			loadMask : {
				msg : '正在加载...,请稍等...'
			},
			iconCls : node.attributes.iconCls,
			defaultSrc : _url,
			listeners : {
				domready : function(iframe) {
				}
			}
		});
	}
	tabs.activate(tabItem);
};
/**
 * 菜单启动流程
 * @param {} node
 */
App.clickStartFlow = function(node){
	var contentPanel = App.getContentPanel();
	var startForm = contentPanel.getItem('ProcessRunStart' + node.attributes.defId);

	if (startForm == null) {
		startForm = new ProcessRunStart({
					id : 'ProcessRunStart' + node.attributes.defId,
					defId : node.attributes.defId,
					flowName : node.attributes.flowName
				});
		contentPanel.add(startForm);
	}
	contentPanel.activate(startForm);
};
/**
 * 点击了公文流程的结点
 */
App.clickFLowNode = function(node){
	
	var jsArr=[
	           __ctxPath+'/js/archive/ArchivesDetailWin.js',
	           __ctxPath+'/js/archive/ArchHastenForm.js',
	           __ctxPath+'/js/flow/ProcessNextForm.js',
	           __ctxPath+'/js/flow/ProcessRunDetail.js'];
	$ImportSimpleJs(jsArr,null);
	
	var contentPanel = App.getContentPanel();
	var nodePanel = contentPanel.getItem(node.id);

	if (nodePanel == null) {
		nodePanel = new ArchivesNode({
					id : 'ProcessRunStart' + node.id,
					title : node.attributes.text
				});
		contentPanel.add(nodePanel);
	}
	contentPanel.activate(nodePanel);
};

App.ClickFlowByKey = function(node){
	var contentPanel = App.getContentPanel();
	Ext.Ajax.request({
			url:__ctxPath+'/setting/getFlowCommonFlowConf.do?flowType='+node.attributes.flowKey,
			method:'POST',
			success:function(response,options){
				var object=Ext.util.JSON.decode(response.responseText);
				if(object.defId==null){
					//Ext.Msg.alert("提示","系统没有配置本流程，请联系管理员！");
					Ext.ux.Toast.msg('提示信息', '系统没有配置本流程，请联系管理员！');
					return;
				}
				var startForm = contentPanel.getItem('ProcessRunStart' + object.defId);

				if (startForm == null) {
					startForm = new ProcessRunStart({
								id : 'ProcessRunStart' + object.defId,
								defId : object.defId,
								flowName : object.flowName
							});
					contentPanel.add(startForm);
				}
				contentPanel.activate(startForm);
			},
			failure : function(response,options){
			
			}
	});
	
}

App.clickNode = function(node) {
	//if(node.id==null || node.id=='' || node.id.indexOf('xnode')!=-1){
		//return ;
	//}
	var nodeid = node.id;
	if(nodeid==null || nodeid=='' || nodeid.indexOf('xnode')!=-1){
		return ;
	}
	//判断如果id中最后一个字符为"_"时，则截取掉。目的是防止menu-mian.xml等菜单中访问id一样时。(汪贵州 2011-2-28修改)
	if(nodeid.substring(nodeid.length-1,nodeid.length)=="_"){
		nodeid=nodeid.substring(0,nodeid.length-1);
	}
	//报表,id带参的情况解析
	var id = nodeid;
	var title=node.text;
	if(id.indexOf('?')>0){
		var str=id.split('?');
		var paramsString="";
		if(str.length>0){
			id=str[0];
			var paramsStr=str[1];
			var paramArray=paramsStr.split('&');
			for(i=0;i<paramArray.length;i++){
				var pstr=paramArray[i];
				var parr=pstr.split('=');
				var p=parr[0];
				var v=parr[1];
				paramsString+=p+':\''+v+'\',';
			}
			paramsString+='title:\''+title+'\'';
			paramsString="{"+paramsString+"}";
		}
		if(node.attributes.url){
			App.clickTopTabUrl(node);
		}else if(node.attributes.iframe){
			App.clickTopTabIframe(node);
		}else if(node.attributes.defId){//启动工作流
			App.clickStartFlow(node);
		}else if (node.attributes.flowNode){//启动工作流
			App.clickFLowNode(node);
		}else if (node.attributes.flowKey){//根据流程KEY启动流程
			App.ClickFlowByKey(node);
		}else{
			App.clickTopTab(id,Ext.decode(paramsString));
		}
		
	}else{
		if(node.attributes.url){
			App.clickTopTabUrl(node);
		}else if(node.attributes.iframe){
			App.clickTopTabIframe(node);
		}else if(node.attributes.defId){
			App.clickStartFlow(node);
		}else if (node.attributes.flowNode){
			App.clickFLowNode(node);
		}else if (node.attributes.flowKey){//根据流程KEY启动流程
			App.ClickFlowByKey(node);
		}else{
			App.clickTopTab(nodeid,Ext.decode(node.attributes.params));
		}
	}
};
/**
 * 桌面点击
 */
App.MyDesktopClick=function(){
	var desktopPanel=Ext.getCmp("MyDesktop");
	if(desktopPanel!=null){
		desktopPanel.expand(true);
	}
	App.clickTopTab('ComIndexPage');
};
/**
 * 点击个人主页
 */
App.MyDesktopClickTopTab=function(id,params,precall,callback){
	if(precall!=null){
		precall.call(this);
	}
	var tabs = Ext.getCmp('centerTabPanel');
	var tabItem = tabs.getItem(id);
	
	if (tabItem == null) {
		$ImportJs(id, function(view) {
			tabItem = tabs.add(view);
			tabs.activate(tabItem);
		},params);
	}else {
		tabs.remove(tabItem);
		var str='new ' + id ;
		if(params!=null){
			str+='(params);';
		}else{
			str+='();';
		}
		var view= eval(str);
		tabItem = tabs.add(view);
		tabs.activate(tabItem);
	}
};
/**
 * 退出系统
 */
App.Logout = function() {
	window.location.href=__ctxPath+"/j_logout.do";
//	Ext.Ajax.request({
//				url : __ctxPath + '/j_logout.do',
//				success : function() {
//					deleteCookie("jforumSSOCookieNameUser","/",0);
//					window.location.href = __ctxPath + '/login.jsp';
//				}
//	});
};
	
//应用程序总入口
Ext.onReady(App.init);
