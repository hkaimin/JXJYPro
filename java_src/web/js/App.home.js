// 首页的设置
MessagePanelView = Ext.extend(Ext.ux.Portlet, {
	tools : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initTool();
		MessagePanelView.superclass.constructor.call(this, {
			id : 'MessagePanelView',
			title : '个人消息',
			iconCls:'menu-message',
			tools : this.tools,
			autoLoad:{
				url:__ctxPath+ '/info/displayInMessage.do'
			}
		});
	},
	initTool : function() {
		this.tools = [{
					id : 'refresh',
					handler : function() {
						Ext.getCmp('MessagePanelView').getUpdater().update(__ctxPath+ '/info/displayInMessage.do');
					}
				}];
	}

});

//待办事项
TaskPanelView = Ext.extend(Ext.ux.Portlet, {
	tools : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initTool();
		TaskPanelView.superclass.constructor.call(this, {
			id : 'TaskPanelView',
			title : '待办事项',
			iconCls:'menu-flowWait',
			tools : this.tools,
			autoLoad:{
				url:__ctxPath+ '/flow/displayTask.do',scripts:true
			}
		});
	},
	initTool : function() {
		this.tools = [{
					id : 'refresh',
					handler : function() {
						Ext.getCmp('TaskPanelView').getUpdater().update(__ctxPath+ '/flow/displayTask.do');
					}
				}
				];
	}

});

//个人文档
MyDocumentPanelView= Ext.extend(Ext.ux.Portlet, {
	tools : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initTool();
		MyDocumentPanelView.superclass.constructor.call(this, {
			id : 'MyDocumentPanelView',
			title : '我的文档',
			iconCls:'menu-document',
			tools : this.tools,
			autoLoad:{
				url:__ctxPath+ '/document/displayDocument.do?start=0&limit=8'
			}
		});
	},
	initTool : function() {
		this.tools = [
			{
					id : 'refresh',
					handler : function() {
						Ext.getCmp('MyDocumentPanelView').getUpdater().update(__ctxPath+ '/document/displayDocument.do?start=0&limit=8');
					}
				},
				{
					id : 'close',
					handler : function(e, target, panel) {
						Ext.Msg.confirm('提示信息', '确认删除此模块吧？', function(btn) {
							if (btn == 'yes') {
								panel.ownerCt.remove(panel, true);
							}
						});
					}
				}];
	}

});
//我的邮件
MyMailPanelView= Ext.extend(Ext.ux.Portlet, {
	tools : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initTool();
		MyMailPanelView.superclass.constructor.call(this, {
			id : 'MyMailPanelView',
			title : '我的邮件',
			iconCls:'menu-mail',
			tools : this.tools,
			autoLoad:{
				url:__ctxPath+ '/communicate/displayMail.do?start=0&limit=8'
			}
		});
	},
	initTool : function() {
		this.tools = [
			{
					id : 'refresh',
					handler : function() {
						Ext.getCmp('MyMailPanelView').getUpdater().update(__ctxPath+ '/communicate/displayMail.do?start=0&limit=8');
					}
				},
				{
					id : 'close',
					handler : function(e, target, panel) {
						Ext.Msg.confirm('提示信息', '确认删除此模块吧？', function(btn) {
							if (btn == 'yes') {
								panel.ownerCt.remove(panel, true);
							}
						});
					}
				}];
	}

});

//模板选择器
PanelSelectorWin=Ext.extend(Ext.Window,{
   formPanel:null,
   buttons:null,
   constructor:function(_cfg){
      Ext.applyIf(this,_cfg);
      this.initUI();
      PanelSelectorWin.superclass.constructor.call(this,{
          id:'PanelSelectorWin',
          title:'选择显示模块',
          layout:'fit',
          height:220,
          width:300,
          modal:true,
          defaults:{
             padding:'5'
          },
          buttons:this.buttons,
          buttonAlign : 'center',
          items:this.formPanel
      });
   },
   initUI:function(){
       this.formPanel=new Ext.FormPanel({
          id:'PanelSelectorForm',
          layout:'column',
          items : [{
								layout : 'form',
								columnWidth:.5,
								border:false,
								items : [{
											xtype : 'checkbox',
											boxLabel : '待办事项',
											hideLabel:true,
											id : 'TaskPanelViewCheckBox',
											name : 'TaskPanelView'
										}
										]
							},{
							layout:'form',
							columnWidth:.5,
							border:false,
							items:[{
							   xtype:'checkbox',
							   boxLabel:'我的文档',
							   hideLabel:true,
							   id:'MyDocumentPanelViewCheckBox',
							   name:'MyDocumentPanelView'
							},{
							   xtype:'checkbox',
							   boxLabel:'我的邮件',
							   hideLabel:true,
							   id:'MyMailPanelViewCheckBox',
							   name:'MyMailPanelView'
							}
							]
							}
         ]
       });
       //将已经显示的PORTALITEM勾上
        var portal = Ext.getCmp('Portal');
		curUserInfo.portalConfig = [];
		var items = portal.items;
		for (var i = 0; i < items.length; i++) {
			var v = items.itemAt(i);
			for (var j = 0; j < v.items.getCount(); j++) {
				var m = v.items.itemAt(j);
				var portalItem = new PortalItem(m.id, i, j);
				curUserInfo.portalConfig.push(portalItem);
			}
		}
       var confs=curUserInfo.portalConfig;
       for(var i=0;i<confs.length;i++){
       	  var panelView=confs[i].panelId;
          var panelCheck=Ext.getCmp(panelView+'CheckBox');
          if(panelCheck!=null){
            panelCheck.setValue(true);
            panelCheck.disable();
          }
       }
       
       this.buttons=[
       {
         xtype:'button',
       	text:'确定',
       	iconCls:'btn-save',
       	handler:function(){
				var fd = Ext.getCmp('PortalItem');
				var portal = Ext.getCmp('Portal');
				var array=['TaskPanelView','MyDocumentPanelView','MyMailPanelView'];
				for(var v=0;v<array.length;v++){
				   var check=Ext.getCmp(array[v]+'CheckBox');
				   if(check!=null){
				      if(check.getValue()&&Ext.getCmp(array[v])==null){
				          var panel = eval('new ' + array[v] + '()');
						  fd.add(panel);
				      }
				   }
				}
				fd.doLayout();
				portal.doLayout();
				Ext.getCmp('PanelSelectorWin').close();
       	}
       },{
         xtype:'button',
         text:'取消',
         iconCls:'btn-cancel',
         handler:function(){
            Ext.getCmp('PanelSelectorWin').close();
         }
       }
       ]
   }
});

AppHome = Ext.extend(Ext.Panel, {
	portalPanel : null,
	toolbar : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		AppHome.superclass.constructor.call(this, {
					title : '个人桌面',
					closable : false,
					id : 'AppHome',
					border : false,
					iconCls : 'menu-desktop',
					layout : 'fit',
					defaults:{
					    padding:'0 5 0 0'
					},
					tbar : this.toolbar,
					items : this.portalPanel
				});
	},
	initUIComponents : function() {
		this.toolbar = new Ext.Toolbar({
					height : 30,
					items : ['->',{
						xtype : 'button',
						text : '添加模块',
						iconCls:'btn-add',
						handler : function() {
							new PanelSelectorWin().show();
						}
					}, {
						xtype : 'button',
						text : '保存',
						iconCls:'btn-save',
						handler : function() {
							var portal = Ext.getCmp('Portal');
							curUserInfo.portalConfig = [];
							var items = portal.items;
							for (var i = 0; i < items.length; i++) {
								var v = items.itemAt(i);
								for (var j = 0; j < v.items.getCount(); j++) {
									var m = v.items.itemAt(j);
									var portalItem = new PortalItem(m.id, i, j);
									curUserInfo.portalConfig.push(portalItem);
								}
							}
							Ext.Ajax.request({
										method : 'post',
										url : __ctxPath
												+ '/system/saveIndexDisplay.do',
										success : function(request) {
											Ext.ux.Toast
													.msg('操作信息', '保存成功');
										},
										failure : function(request) {
											Ext.MessageBox.show({
														title : '操作信息',
														msg : '信息保存出错，请联系管理员！',
														buttons : Ext.MessageBox.OK,
														icon : 'ext-mb-error'
													});
										},
										params : {
											items : Ext.encode(curUserInfo.portalConfig)
										}
									});

						}
					}]

				});

		var tools = [{
					id : 'gear',
					handler : function() {
						Ext.Msg.alert('Message',
								'The Settings tool was clicked.');
					}
				}, {
					id : 'close',
					handler : function(e, target, panel) {
						panel.ownerCt.remove(panel, true);
					}
				}];

		var confs=curUserInfo.portalConfig;
		
		if(confs==null|| confs==undefined||confs.length<1){
			confs=new Array();
			var p1={panelId:'MessagePanelView',column:1,row:0};
			var p2={panelId:'TaskPanelView',column:0,row:1};
			
			confs.push(p2);
			confs.push(p1);
		}
		var column0=[];
		var column1=[];
		for(var v=0;v<confs.length;v++){
		   if(confs[v].column==0){
		      column0.push(eval('new ' + confs[v].panelId + '()'));
		   }else{
		      column1.push(eval('new ' + confs[v].panelId + '()'));
		   }
		}
		this.portalPanel = {
			id : 'Portal',
			xtype : 'portal',
			border : false,
			region : 'center',
			margins : '35 5 5 0',
			items : [{
				columnWidth : .65,
				style : 'padding:10px 0 10px 10px',
				id : 'PortalItem',
				items : column0
			}, {
				columnWidth : .35,
				style : 'padding:10px 10px 10px 10px',
				items : column1
			}]
		};
   
	}

});
