/**
 * @author:
 * @class DpBdzView
 * @extends Ext.Panel
 * @description [DpBdz]管理
 * @company 广州宏天软件有限公司
 * @createtime:
 */
CheckView = Ext.extend(Ext.Panel,{
	constructor:function(conf){
		Ext.applyIf(this,conf);
		this.initUI();
		CheckView.superclass.constructor.call(this,{
			id:'CheckView',
			title:'达标计算（已通过学分）',
//			iconCls:'menu-RmBdzView',
			layout:'border',
			items:[
				this.centerTabPanel
			]
		});
	},
	//初始化UI
	initUI:function(){
		//属性
		this._param = new Object(
			{
				orgName:'',
				orgId:0,
				demId:1,
				nodePath:'/0'
			});
		
		//根据Json数据建立树
		var buildTree = function(root, data) {
			var child = null;
			for ( var i = 0; i < data.length; i++) {
				if(data[i].children == null ){
					if(data[i].leaf != null && data[i].leaf == true){
						child = new Ext.tree.TreeNode(data[i]);
					}else{
						child = new Ext.tree.AsyncTreeNode(data[i]);
					}
					root.appendChild(child);
					
				}else if (data[i].children.length > 0){
					child = new Ext.tree.TreeNode(data[i]);
					root.appendChild(child);
					buildTree(child, data[i].children);
				}
				
				
			}
		}
		
			var setTreeRoot = function(root) {
				Ext.Ajax.request( {
						url : __ctxPath + '/project/treeRootProject.do?',
						params : '',
						method : 'POST',
						success : function(response, options) {
							var rsp = Ext.decode(response.responseText);
		
							if (rsp.success) {
								var treeData = eval(rsp.data);
								root.removeAll();
								buildTree(root, treeData);								
							} else {
								Ext.ux.Toast.msg("提示信息", rsp.message);
							}	
						},
						failure : function() {
							Ext.ux.Toast.msg("提示信息", "组织树加载失败,请稍后重试！");
						}
					});
		};
		
		
		var root=new Ext.tree.TreeNode({
			id:"0",
			text:'全部',
			animate : true,
			autoScroll : true,
			containerScroll : true,
			treeNodeType : 'dem'
		});
		
		var treeLoad = new Ext.tree.TreeLoader();
		
		this.tree=new Ext.tree.TreePanel({
		id:'bdzManageViewTreePanel',
		region:'center',
		root:root,
		rootVisible: false,
		loader: treeLoad,
		width:100,			
		autoScroll : true,
		//plugins: ['multifilter'],
		tbar:new Ext.Toolbar()
		});
		
		var sorter=new Ext.tree.TreeSorter(this.tree, {folderSort:true});
		this.tree.on('afterrender', function(){
			new Ext.Toolbar( {
				renderTo : this.tree.tbar,
				defaultType : 'button',
				items : [ {
					text : '刷新',
					iconCls : 'btn-refresh',
					handler : function() {
						setTreeRoot(Ext.getCmp('bdzManageViewTreePanel').root);
					}
				}, {
					text : '展开',
					iconCls : 'btn-expand',
					handler : function() {
						Ext.getCmp('bdzManageViewTreePanel').expandAll();
					}
				}, {
					text : '收起',
					iconCls : 'btn-collapse',
					handler : function() {
						Ext.getCmp('bdzManageViewTreePanel').collapseAll();
					}
				} ]
			});
		}, this);
		
		
		
		
		setTreeRoot(this.tree.root);
		
		this.tree.on("beforeload",function(node){
			var CheckView = Ext.getCmp("CheckView");
			var demId = CheckView._param.demId;
			treeLoad.dataUrl=__ctxPath + '/project/treeProject.do?treeNodeId='+node.attributes.resId 
							+ '&treeNodeType='+ node.attributes.treeNodeType 
							+ '&demensionId='+ demId;
			
		});
		
		this.tree.on("click",function(node){	//树点击事件
			if(node.leaf){					
				var CheckView = Ext.getCmp("CheckView");
				CheckView._param.orgName = node.text;
				CheckView._param.orgId = node.attributes.resId;
				CheckView._param.nodePath = node.getPath();
				
				//设置右面板中标题
				var centerPanel = Ext.getCmp("BdzViewCenterPanel");
				centerPanel.setTitle(node.text + " - 项目信息");
				
				//重新加载列表
				var grid = Ext.getCmp("EmBdzGrid");
					if (grid != null) {
							var store = grid.getStore();
							var limit = grid.getBottomToolbar().pageSize;
							store.url = __ctxPath + "/project/listProject.do";
							store.baseParams = {
								orgId : node.attributes.resId,
								method : 'treeClick',
								limit : limit
							};
						store.load();
				}			
					
			}
			
		});
		

		
		this.leftPanel=new Ext.Panel({
			collapsible : true,
			split : true,
			region:'west',
			width:180,
			title:'组织栏目树',
			layout:'fit',
			items:[{
					xtype:'panel',
					layout:'border',
					border:false,
					items:[
						this.tree
					]
				}
				
			]
		});
		
	
		var zcStore = new Ext.data.Store({
			id:'zcStore',
			proxy : new Ext.data.HttpProxy({
				url : __ctxPath + "/project/listZcCredit.do"
			}),
			reader : new Ext.data.ArrayReader({},[
				{name:'zcId'},
				{name:'zcm'}
			])
		});
				
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel( {
			id:'RmBdz_SearchPanel',
			region : 'center',
			layout : 'hbox',
			layoutConfig : {
				align : 'left',
				padding : '5'
			},
			defaults : {
				xtype : 'label',
				border:false,
				margins:{top:10, right:4, bottom:2, left:4}
			},
			items : [ 
			{
				text : '职称'
			},{
				name : 'biaozhun.zc',
				model:'remote',
				xtype : 'combo',
				editable : false,
				valueField:'zcId',
				displayField:'zcm',
				emptyText:'请选择',
				triggerAction :'all',
				hiddenName:'biaozhun.zc',
				store : zcStore
			},{
				text : '年度'
			},{
				xtype : 'combo',
				editable : false,
				emptyText:'请选择',
				triggerAction :'all',
				hiddenName:'biaozhun.nf',
				value : '2013',
				store : [
					['2013','2013'],
					['2012','2012'],
					['2011','2011']
						]
			},{
				iconCls : 'btn-search',
				text : '单位考核',
				xtype : 'button',
				scope : this,
				handler : this.removeSelRs
			}]
		});// end of searchPanel
		
		// 初始化搜索条件Panel
		this.searchPanel2 = new Ext.FormPanel( {
			id:'RmBdz_SearchPanel2',
			region : 'center',
			layout : 'hbox',
			layoutConfig : {
				align : 'left',
				padding : '5'
			},
			defaults : {
				xtype : 'label',
				border:false,
				margins:{top:10, right:4, bottom:2, left:4}
			},
			items : [ 
			{
				text : '人员编号'
			},{
				xtype : 'textfield'
			}, {
				iconCls : 'btn-search',
				text : '查询',
				xtype : 'button',
				scope : this,
				handler : this.removeSelRs
			},{
				xtype : 'textfield' 
			},{
				text : '年度'
			},{
				xtype : 'combo',
				editable : false,
				emptyText:'请选择',
				triggerAction :'all',
				hiddenName:'biaozhun.nf',
				value : '2013',
				store : [
					['2013','2013'],
					['2012','2012'],
					['2011','2011']
						]
			},{
				iconCls : 'btn-search',
				text : '个人考核',
				xtype : 'button',
				scope : this,
				handler : this.removeSelRs
			}]
		});// end of searchPanel

		this.topbar = new Ext.Toolbar( {
			items : [ {
				iconCls : 'btn-add',
				text : '添加',
				xtype : 'button',
				scope : this,
				handler : this.createRs
			}, {
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				scope : this,
				handler : this.removeSelRs
			}
//			, {
//				iconCls : 'menu-sync',
//				text : '同步GIS',
//				xtype : 'button',
//				scope : this,
//				handler : function(){
//					new GisBdzSyncWin(function(){						
//						Ext.getCmp("EmBdzGrid").getStore().reload();
//					}, {
//							qjId : this._param.orgId,
//							qjName : this._param.orgName,
//							nodePath : this._param.nodePath
//						}).show();//end of selector
//				}
//			}, {
//				iconCls : 'btn-shared',
//				text : '共享到',
//				xtype : 'button',
//				scope : this,
//				handler : this.shareSelRs
//			}
			]
		});

		
		this.topbar2 = new Ext.Toolbar( {
			items : [ {
				text : '人员编号'
			},{
				xtype : 'textfield'
			}, {
				iconCls : 'btn-search',
				text : '查询',
				xtype : 'button',
				scope : this,
				handler : this.removeSelRs
			},{
				xtype : 'textfield' 
			},{
				text : '年度'
			},{
				xtype : 'combo',
				editable : false,
				emptyText:'请选择',
				triggerAction :'all',
				hiddenName:'biaozhun.nf',
				value : '2013',
				store : [
					['2013','2013'],
					['2012','2012'],
					['2011','2011']
						]
			},{
				iconCls : 'btn-search',
				text : '个人考核',
				xtype : 'button',
				scope : this,
				handler : this.removeSelRs
			}
			]
		});
		

//		//右边面板
//		this.centerPanel=new Ext.Panel({
//			id:'BdzViewCenterPanel',
//			region:'center',
//			layout : 'border',
//			title:'项目信息',
//			items:[this.searchPanel,this.gridPanel]
//		});
		
		//右边面板
		this.onePanel=new Ext.Panel({
			region:'center',
			layout : 'border',
			title:'单位考核',
			items:[this.leftPanel, this.searchPanel]
		});
		
		//右边面板
		this.personPanel=new Ext.Panel({
			region:'center',
			layout : 'border',
			title:'个人考核',
			items:[this.searchPanel2]
		});
		
		this.centerTabPanel = new Ext.TabPanel({
			id:'tabPanel',
			region:'center',
//			tbar : this.topbar,
			activeTab: 0,
			items:[this.personPanel, this.onePanel]
		});
	},
	//end of initUI
	
	//重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	//按条件搜索
	search : function() {
		if(this._param.orgId <=0 ){
			Ext.ux.Toast.msg("操作信息","请选择区局");
			return;
		}
		
		//修改查询面板中所属单位			
		var searchPanel = Ext.getCmp("RmBdz_SearchPanel");
		searchPanel.form.findField('orgId').setValue(this._param.orgId);
		$search( {
			searchPanel : this.searchPanel,
			gridPanel : this.gridPanel
		});
	},
	//GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
//			new RmBdzForm( {
//				bdzId : rec.data.bdzId
//			}).show();
		});
	},
	//创建记录
	createRs : function() {
		if(this._param.orgId <=0 ){
			Ext.ux.Toast.msg("操作信息","请选择区局");
			return;
		}
		new ProjectForm({
			ssdwId : this._param.orgId,
			orgName : this._param.orgName
		}).show();
		
	},
	//按ID删除记录
	removeRs : function(id) {
		var records = this.gridPanel.getSelectionModel().getSelections();
		if(records.length <=0 ) {
			Ext.ux.Toast.msg("操作信息","请选择需要删除的记录");
			return;
		}
		
		var ids = [id];
		
		var gridPanel = this.gridPanel;
		Ext.Msg.confirm("信息确认", "删除变电站，请确认变电站下无任何馈线，继续？", function(btn){
		            if (btn == "yes") {
		    				Ext.Ajax.request({
								url : __ctxPath + '/dp/multiDelDpBdz.do',
								method : 'POST',
								params : {
									ids : ids
								},
								success : function(form, action) {
									
									var result = Ext.util.JSON.decode(form.responseText);
									if (result.success == true) {
										Ext.ux.Toast.msg("操作信息", "删除变电站操作成功");
																				
									} else {
										
										Ext.MessageBox.show({
											title : '操作信息',
											msg : result.message,
											buttons : Ext.MessageBox.OK,
											icon : 'ext-mb-error'
										});
									}
									gridPanel.getStore().reload();

								},
								failure : function(response, options) {
									Ext.ux.Toast.msg("温馨提示", "系统错误，请联系管理员！");
								}
						   });
		            }
			 });	
	},
	//把选中ID删除
	removeSelRs : function() {

		var records = this.gridPanel.getSelectionModel().getSelections();
		if(records.length <=0 ) {
			Ext.ux.Toast.msg("操作信息","请选择需要删除的记录");
			return;
		}
		
		var ids = [];
		for(var i=0;i<records.length;i++) {
			ids.push(records[i].data.bdzId);	
		}
		var gridPanel = this.gridPanel;
		Ext.Msg.confirm("信息确认", "删除变电站，请确认变电站下无任何馈线，继续？", function(btn){
		            if (btn == "yes") {
		    				Ext.Ajax.request({
								url : __ctxPath + '/dp/multiDelDpBdz.do',
								method : 'POST',
								params : {
									ids : ids
								},
								success : function(form, action) {
									
									var result = Ext.util.JSON.decode(form.responseText);
									if (result.success == true) {
										Ext.ux.Toast.msg("操作信息", "删除变电站操作成功");
																				
									} else {
										Ext.MessageBox.show({
											title : '操作信息',
											msg : result.message,
											buttons : Ext.MessageBox.OK,
											icon : 'ext-mb-error'
										});
									}
									gridPanel.getStore().reload();

								},
								failure : function(response, options) {
									Ext.ux.Toast.msg("温馨提示", "系统错误，请联系管理员！");
								}
						   });
		            }
			 });	
		
	},
	//编辑Rs
	editRs : function(record) {
		new DpBdzForm( {
			bdzId : record.data.bdzId,
			edit:true
		}).show();
	},
	//上报医院同意
	reportRs : function(record) {
		var gridPanel = this.gridPanel;
		Ext.Msg.confirm("信息确认", "是否上报医院？", function(btn){
            if (btn == "yes") {
            	var xmId = 	record.data.xmId;
				Ext.Ajax.request({
					url : __ctxPath + '/project/reportToYyProject.do',
					method : 'POST',
					params : {
						xmId : xmId
					},
					success : function(form, action) {
						
						var result = Ext.util.JSON.decode(form.responseText);
						if (result.success == true) {
							Ext.ux.Toast.msg("操作信息", "项目上报医院成功！");
																	
						} else {
							Ext.MessageBox.show({
								title : '操作信息',
								msg : result.message,
								buttons : Ext.MessageBox.OK,
								icon : 'ext-mb-error'
							});
						}
						gridPanel.getStore().reload();
					},
					failure : function(response, options) {
						Ext.ux.Toast.msg("温馨提示", "系统错误，请联系管理员！");
					}
			   });
            }
	 	});	
	},
	//卫生局审核
	checkRs : function(record) {
		var gridPanel = this.gridPanel;
		Ext.Msg.confirm("信息确认", "此项目是否通过？", function(btn){ 
			if(btn == "yes") {
				Ext.MessageBox.prompt('提示信息', '请输入此项目编号。', function(btn, text) {
		            if (btn == "ok") {
		            	var xmId = 	record.data.xmId;
						Ext.Ajax.request({
							url : __ctxPath + '/project/checkProProject.do',
							method : 'POST',
							params : {
								xmId : xmId,
								xmbh : text,
								isCheck : '1'
							},
							success : function(form, action) {
								
								var result = Ext.util.JSON.decode(form.responseText);
								if (result.success == true) {
									Ext.ux.Toast.msg("操作信息", "信息保存成功！");
																			
								} else {
									Ext.MessageBox.show({
										title : '操作信息',
										msg : result.message,
										buttons : Ext.MessageBox.OK,
										icon : 'ext-mb-error'
									});
								}
								gridPanel.getStore().reload();
							},
							failure : function(response, options) {
								Ext.ux.Toast.msg("温馨提示", "系统错误，请联系管理员！");
							}
					   });
		            }
	 			});	
			} else if(btn == "no"){
				var xmId = 	record.data.xmId;
				Ext.Ajax.request({
					url : __ctxPath + '/project/checkProProject.do',
					method : 'POST',
					params : {
						xmId : xmId,
						isCheck : '0'
					},
					success : function(form, action) {
						
						var result = Ext.util.JSON.decode(form.responseText);
						if (result.success == true) {
							Ext.ux.Toast.msg("操作信息", "信息保存成功！");
																	
						} else {
							Ext.MessageBox.show({
								title : '操作信息',
								msg : result.message,
								buttons : Ext.MessageBox.OK,
								icon : 'ext-mb-error'
							});
						}
						gridPanel.getStore().reload();
					},
					failure : function(response, options) {
						Ext.ux.Toast.msg("温馨提示", "系统错误，请联系管理员！");
					}
			   });
			}
		});
		
	},
	//医院审核
	checkYyRs : function(record) {
		var gridPanel = this.gridPanel;
		Ext.Msg.confirm("信息确认", "此项目是否通过？", function(btn){ 
			var flage = "";
			if(btn == "yes") {
				flage = "1";
			} else if(btn == "no"){
				flage = "0";
			}
			var xmId = 	record.data.xmId;
			Ext.Ajax.request({
				url : __ctxPath + '/project/checkProYyProject.do',
				method : 'POST',
				params : {
					xmId : xmId,
					isCheck : flage
				},
				success : function(form, action) {
					
					var result = Ext.util.JSON.decode(form.responseText);
					if (result.success == true) {
						Ext.ux.Toast.msg("操作信息", "信息保存成功！");
																
					} else {
						Ext.MessageBox.show({
							title : '操作信息',
							msg : result.message,
							buttons : Ext.MessageBox.OK,
							icon : 'ext-mb-error'
						});
					}
					gridPanel.getStore().reload();
				},
				failure : function(response, options) {
					Ext.ux.Toast.msg("温馨提示", "系统错误，请联系管理员！");
				}
		   });
		});
		
	},
	
	//行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
		case 'btn-del':
			this.removeRs.call(this, record.data.bdzId);
			break;
		case 'btn-edit':
			this.editRs.call(this, record);
			break;
		case 'btn-report':
			this.reportRs.call(this, record);
			break;
		case 'btn-check':
			this.checkRs.call(this, record);
			break;
		case 'btn-checkYy':
			this.checkYyRs.call(this, record);
			break;
		default:
			break;
		}
	}
});



