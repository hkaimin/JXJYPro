/**
 * @author:
 * @class DpBdzView
 * @extends Ext.Panel
 * @description [DpBdz]管理
 * @company 广州宏天软件有限公司
 * @createtime:
 */
CourseView = Ext.extend(Ext.Panel,{
	constructor:function(conf){
		Ext.applyIf(this,conf);
		this.initUI();
		CourseView.superclass.constructor.call(this,{
			id:'CourseView',
			title:'课题管理',
//			iconCls:'menu-RmBdzView',
			layout:'border',
			items:[
				this.leftPanel,
				this.centerPanel
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
						url : __ctxPath + '/project/treeRootCourse.do?',
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
		id:'courseTreePanel',
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
						setTreeRoot(Ext.getCmp('courseTreePanel').root);
					}
				}, {
					text : '展开',
					iconCls : 'btn-expand',
					handler : function() {
						Ext.getCmp('courseTreePanel').expandAll();
					}
				}, {
					text : '收起',
					iconCls : 'btn-collapse',
					handler : function() {
						Ext.getCmp('courseTreePanel').collapseAll();
					}
				} ]
			});
		}, this);
		
		
		
		
		setTreeRoot(this.tree.root);
		
		this.tree.on("beforeload",function(node){
			var CourseView = Ext.getCmp("CourseView");
			var demId = CourseView._param.demId;
			treeLoad.dataUrl=__ctxPath + '/project/treeCourse.do?treeNodeId='+node.attributes.resId 
							+ '&treeNodeType='+ node.attributes.treeNodeType 
							+ '&orgType='+ node.attributes.orgType 
							+ '&demensionId='+ demId;
			
		});
		
		this.tree.on("click",function(node){	//树点击事件
			if(node.leaf){					
				var CourseView = Ext.getCmp("CourseView");
				CourseView._param.orgName = node.text;
				CourseView._param.orgId = node.attributes.resId;
				CourseView._param.nodePath = node.getPath();
				
				//设置右面板中标题
				var centerPanel = Ext.getCmp("BdzViewCenterPanel");
				centerPanel.setTitle(node.text + " - 课题信息");
				
				//重新加载列表
				var grid = Ext.getCmp("EmBdzGrid");
					if (grid != null) {
							var store = grid.getStore();
							var limit = grid.getBottomToolbar().pageSize;
							store.url = __ctxPath + "/project/listCourse.do";
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
		
	
				
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel( {
			id:'RmBdz_SearchPanel',
			region : 'north',
			height : 60,
			width : '100%',
//			keys : [{
//				key : Ext.EventObject.ENTER,
//				fn : this.search,
//				scope : this
//			}, {
//				key : Ext.EventObject.ESC,
//				fn : this.reset,
//				scope : this
//			}],
			items : [{
				border : false,
				layout : 'column',
				style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
				layoutConfig : {
					align : 'middle',
					padding : '5px'
				},
				defaults : {
					xtype : 'label'
				},
				items : [{
					columnWidth : .3,
					xtype : 'container',
					layout : 'form',
					items : [{
						width : '95%',
						fieldLabel : '课题名称',
						name : 'Q_modulename_S_LK',
						xtype : 'textfield',
						maxLength : 125
					}, {
						width : '95%',
						fieldLabel : '讲师名称',
						name : 'Q_modulekey_S_LK',
						xtype : 'textfield',
						maxLength : 125
					}]
				}, {
					columnWidth : .3,
					xtype : 'container',
					layout : 'form',
					items : [{
						width : '95%',
						fieldLabel : '讲师职称',
						name : 'Q_processkey_S_LK',
						xtype : 'textfield',
						maxLength : 125
					}, {
						xtype : 'container',
						layout : 'column',
						border : false,
						fieldLabel : '授课时间',
						items : [{
							columnWidth : .49,
							name : 'Q_createtime_D_GE',
							xtype : 'datefield',
							format : 'Y-m-d'
						}, {
							xtype : 'label',
							text : '至',
							style : 'padding-top:3px'
						}, {
							columnWidth : .49,
							name : 'Q_createtime_D_LE',
							xtype : 'datefield',
							format : 'Y-m-d'
						}]
					}]
				}, {
					columnWidth : .3,
					xtype : 'container',
					layout : 'form',
					items : [{
						width : '95%',
						fieldLabel : '上课地点',
						name : 'Q_creator_S_LK',
						xtype : 'textfield',
						maxLength : 125
					}]
				}, {
					columnWidth : .1,
					xtype : 'container',
					layout : 'form',
					defaults : {
						xtype : 'button'
					},
					items : [{
						text : '查询',
						scope : this,
						iconCls : 'btn-search',
						handler : this.search
					}, {
						style : 'padding-top:3px;',
						text : '清空',
						scope : this,
						iconCls : 'reset',
						handler : this.reset
					}]
				}]
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

		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			tbar : this.topbar,
			//使用RowActions
			rowActions : true,
			id : 'EmBdzGrid',
			url : __ctxPath + "/project/listCourse.do",
			fields : [ 'ktId', 'xmId', 'ktmc', 'jsmc', 'jszc', 'xf', 'xs', 'skdd', 'sksj'],
			columns : [ {
				header : '课题名称',
				dataIndex : 'ktmc'
//				hidden : true
//				sortable: true
			}, {
				header : '讲师名称',
				dataIndex : 'jsmc',
				width:180,
				sortable: true
			}, {
				header : '讲师职称',
				dataIndex : 'jszc',
				sortable: true
//				hidden : true
			}, {
				header : '学分',
				dataIndex : 'xf',
				sortable: true
			}, {
				header : '学时',
				dataIndex : 'xs',
				sortable: true
//				hidden : true
			}
			, {
				header : '上课地点',
				dataIndex : 'skdd',
				sortable: true
//				hidden : true
			}
			, {
				header : '授课时间',
				dataIndex : 'sksj',
				sortable: true
			}, new Ext.ux.grid.RowActions( {
				header : '管理',
				width : 100,
				actions : [ {
					text:'<a href="#">删除</a>',
					iconCls : 'btn-del',
					qtip : '删除变电站',
					style : 'margin:0 3px 0 3px'
				},{
					text:'<a href="#">编辑</a>',
					iconCls : 'btn-edit',
					qtip : '编辑变电站',
					style : 'margin:0 3px 0 3px'
				} ],
				listeners : {
					scope : this,
					'action' : this.onRowAction
				}
			}) ]
		//end of columns
		});

		this.gridPanel.addListener('rowdblclick', this.rowClick);

		//右边面板
		this.centerPanel=new Ext.Panel({
			id:'BdzViewCenterPanel',
			region:'center',
			layout : 'border',
			title:'课题信息',
			items:[this.searchPanel,this.gridPanel]
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
		new CourseForm({
			xmId : this._param.orgId
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
	
	//把选中共享
	shareSelRs : function() {

		var records = this.gridPanel.getSelectionModel().getSelections();
		if(records.length != 1 ) {
			Ext.ux.Toast.msg("操作信息","请选择一个变电站！");
			return;
		}
		
		var bdzId = records[0].data.bdzId;
//		QJSelector.getView(function(qjIds){
//			Ext.Ajax.request({
//					url : __ctxPath + '/dataManagement/shareRmBdz.do',
//					method : 'POST',
//					params : {
//						'qjIds' : qjIds,
//						'bdzId' : bdzId
//					},
//					success : function(form, action) {
//						
//						var result = Ext.util.JSON.decode(form.responseText);
//						if (result.success == true) {
//							Ext.ux.Toast.msg("操作信息", "共享变电站操作成功");
//																	
//						} else {
//							Ext.ux.Toast.msg("操作信息", result.msg);
//						}
//
//					},
//					failure : function(response, options) {
//						Ext.ux.Toast.msg("温馨提示", "系统错误，请联系管理员！");
//					}
//			   });
//			
//		},{
//			excludeIds:this._param.orgId,
//			bdzId : bdzId
//			}).show();		
		
		
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
		default:
			break;
		}
	}
});



