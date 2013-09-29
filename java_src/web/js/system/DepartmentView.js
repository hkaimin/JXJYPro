Ext.ns('DepartmentView');

var DepartmentView = function() {
	return this.setup();
};

DepartmentView.prototype.setup = function() {
	var selected;
	var store = this.initData();
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm,
			{
					header : "userId",
					dataIndex : 'userId',
					hidden : true
				}, {
					header : '',
					sortable : true,
					width : 5,
					dataIndex : 'sn',
					renderer : function(value, metaData, record, rowIndex,
							colIndex, store) {
						record.data['sn'] = rowIndex + 1;
						store.commitChanges();
						return record.data['sn'];
					}
				}, {
					header : "状态",
					sortable : true,
					dataIndex : 'appUser',
					width : 30,
					renderer : function(appUser) {
						if (appUser) {
							var value = appUser.status;
							var str = '';
							if (value == '1') {// 激活用户
								str += '<img title="激活" src="'
										+ __ctxPath
										+ '/images/flag/customer/effective.png"/>';
							} else {// 禁用用户
								str += '<img title="禁用" src="'
										+ __ctxPath
										+ '/images/flag/customer/invalid.png"/>';
							}
							return str;
						}
					}
				}, {
					header : "账号",
					sortable : true,
					dataIndex : 'appUser',
					width : 60,
					renderer : function(appUser) {
						return appUser == null ? '' : appUser.username;
					}
				}, {
					header : "用户名",
					sortable : true,
					dataIndex : 'appUser',
					width : 60,
					renderer : function(appUser) {
						return appUser != null ? appUser.fullname : '';
					}
				}, {// 先不显示
					header : "所属部门",
					sortable : true,
					dataIndex : 'department',
					width : 60,
					renderer : function(value) {
						return value.depName == null ? '' : value.depName;
					}
				}, {
					header : "主部门(是/否)",
					sortable : true,
					dataIndex : 'isMain',
					width : 60,
					renderer : function(value) {
						return value == '1' ? '是' : '否';
					}
				}, {
					header : '管理',
					dataIndex : 'appUser',
					sortable : true,
					width : 100,
					renderer : function(appUser, metadata, record, rowIndex,
							colIndex, store) {
						if (appUser) {
							var editId = appUser.userId;
							var editName = appUser.username;
							var editdepId = appUser.department.depId;
							var str = '';

							if (isGranted('_AppUserDel') && editId != 1) {
								str += '<button title="删除" value=" " class="btn-del" onclick="DepartmentView.remove('
										+ record.data.depUserId + ')"></button>';
							}else{
								str += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
							}
							if (isGranted('_AppUserEdit') && editId != 1) {
								str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="DepartmentView.edit('
								        + record.data.appUser.userId
								        +',\''
								        + record.data.appUser.username
										+ '\')"></button>';
							}else{
								str += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
							}
							str += '&nbsp;<button title="查看部门信息" value=" " class="btn-showDetail" onclick="DepartmentView.show('
								+ record.data.appUser.userId
								+',\''
								+ record.data.appUser.username
								+ '\')"></button>';
							str += '&nbsp;<button title="添加上下级" value=" " class="btn-relativeJob" onclick="DepartmentView.addRelativeJob('
										+ editId
										+ ',\''
										+ editName
										+ '\',\''
										+ editdepId
										+'\')"></button>';
//							if (rowIndex > 0) {
//								str += '&nbsp;<button title="向上" value=" " class="btn-up" onclick="DepartmentView.sn('
//
//										+ rowIndex + ',' + -1 + ')"></button>';
//							} else {
//								str += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
//							}
//
//							if (rowIndex < (store.getCount() - 1)) {
//								str += '&nbsp;<button title="向下" value=" " class="btn-last" onclick="DepartmentView.sn('
//
//										+ rowIndex + ',' + 1 + ')"></button>';
//							}

							return str;
						}
					}
				}],
		defaults : {
			sortable : true,
			menuDisabled : true,
			width : 100
		},
		listeners : {
			hiddenchange : function(cm, colIndex, hidden) {
				saveConfig(colIndex, hidden);
			}
		}
	});

	var grid = new Ext.grid.GridPanel({
		// TODO grid数据展示
		region : 'center',
		id : 'DepUsersView',
		tbar : new Ext.Toolbar({
			defaultType : 'button',
			items : [{
				text : '添加',
				iconCls : 'add-user',
				handler : function(){
					DepartmentView.add();
				}
			}, '-', {
				text : '删除',
				iconCls : 'btn-del',
				handler : function(){
					DepartmentView.multiRemove();
				}
			}]
		}),
		height : 800,
		title : '科室人员列表',
		store : store,
		shim : true,
		trackMouseOver : true,
		disableSelection : false,
		loadMask : true,
		cm : cm,
		sm : sm,
		viewConfig : {
			forceFit : true,
			enableRowBody : false,
			showPreview : false
		},
		// paging bar on the bottom
		bbar : new HT.PagingBar({
					store : store
				})
	}); // end of this grid
	grid.addListener('rowdblclick', rowdblclickFn);
	function rowdblclickFn(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
			DepartmentView.edit(rec.data.appUser.userId,rec.data.appUser.username);
		});
	}
	store.load({
		params : {
			start : 0,
			limit : 25
		}
	});

	var treePanel = new Ext.tree.TreePanel({
		// TODO treePanel[部门信息列表]
		region : 'west',
		id : 'departmentTreePanel',
		title : '部门信息列表',
		collapsible : true,
		autoScroll : true,
		split : true,
		height : 800,
		width : 180,
		tbar : new Ext.Toolbar({
			items : [{
				xtype : 'button',
				iconCls : 'btn-refresh',
				text : '刷新',
				handler : function() {
					treePanel.root.reload();
				}
			}, '-', {
				xtype : 'button',
				text : '展开',
				iconCls : 'btn-expand',
				handler : function() {
					treePanel.expandAll();
				}
			}, '-', {
				xtype : 'button',
				text : '收起',
				iconCls : 'btn-collapse',
				handler : function() {
					treePanel.collapseAll();
				}
			}]
		}),
		loader : new Ext.tree.TreeLoader({
					url : __ctxPath + '/system/listDepartment.do'
				}),
		root : new Ext.tree.AsyncTreeNode({
					expanded : true
				}),
		rootVisible : false,
		listeners : {
			'click' : DepartmentView.clickNode
		}
	}); // end of this treePanel

	if (isGranted('_DepartmentAdd') || isGranted('_DepartmentEdit')
			|| isGranted('_DepartmentDel')) {
		// 树的右键菜单的
		treePanel.on('contextmenu', contextmenu, treePanel);
	}
	

	function contextmenu(node, e) {
		selected = new Ext.tree.TreeNode({
			id : node.id,
			text : node.text
		});
		// 创建右键菜单
		var treeMenu = new Ext.menu.Menu({
//					id : 'DepartmentTreeMenu',
					items : []
				});
		treeMenu.clearMons();
		if (isGranted('_DepartmentAdd')) {
			treeMenu.add({
						text : '新建部门',
						iconCls : 'btn-add',
						scope : this,
						handler : createNode
					});
		}
		if (isGranted('_DepartmentEdit')) {
			treeMenu.add({
						text : '修改部门信息',
						iconCls : 'btn-edit',
						scope : this,
						handler : editNode
					});
		}
		if (isGranted('_DepartmentDel')) {
			treeMenu.add({
						text : '删除部门',
						iconCls : 'btn-delete',
						scope : this,
						handler : deteleNode
					});
	
		}
		
		treeMenu.showAt(e.getXY());
	}
	/**
	 * 菜单事件
	 */

	function createNode() {
		var nodeId = selected.id;
		var departmentForm = Ext.getCmp('departmentForm');
		if (departmentForm == null) {
			if (nodeId > 0) {
				new DepartmentForm({
							nodeId : nodeId
						}).show();
			} else {
				new DepartmentForm({
							nodeId : 0
						}).show();
			}
			Ext.getCmp('departmentTreePanel').root.reload();
		}

	}
	function deteleNode() {
		var depId = selected.id;
		if (depId > 0) {
			Ext.Msg.confirm('删除操作', '你确定删除部门?', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
						url : __ctxPath + '/system/removeDepartment.do?depId='
								+ depId,
						success : function(result, request) {
							var res = Ext.util.JSON.decode(result.responseText);
							if (res.success == false) {
								Ext.ux.Toast.msg('操作信息', res.message);
							} else {
								Ext.ux.Toast.msg('操作信息', '删除成功!');
							}
							Ext.getCmp('departmentTreePanel').root.reload();
						},
						failure : function(result, request) {
						}
					});
				}
			});
		} else {
			Ext.ux.Toast.msg('警告', "总公司不能被删除");
		}
	}
	function editNode() {
		var depId = selected.id;
		if (depId > 0) {
			var departmentForm = Ext.getCmp('departmentForm');
			if (departmentForm == null) {
				new DepartmentForm().show();
				departmentForm = Ext.getCmp('departmentForm');
			}
			departmentForm.form.load({
				url : __ctxPath + '/system/detailDepartment.do',
				params : {
					depId : depId
				},
				method : 'post',
				deferredRender : true,
				layoutOnTabChange : true,
				success : function() {
					Ext.getCmp('departmentTreePanel').root.reload();
				},
				failure : function() {
					Ext.ux.Toast.msg('编辑', '载入失败');
				}
			});
		} else {
			Ext.ux.Toast.msg('警告', "总公司不能修改！");
		}

	}
	
	//////////////searchPanel[搜索]//////////////////
	var searchPanel = new Ext.FormPanel({
		// TODO searchPanel[搜索]
		id : 'departmentViewSearchPanel',
		height : 40,
		frame : false,
		border : false,
		region : 'north',
		layout : 'form',
		layoutConfig : {
			padding : '5px',
			align : 'middle'
		},
		items : [{
			xtype : 'container',
			layout : 'column',
			border : false,
			fieldLabel : '请输入查询条件',
			style : 'margin-top:8px;',
			defaults : {
				xtype : 'label',
				border : false,
				height : 25
			},
			layoutConfig : {
				align : 'middle'
			},
			items : [{
				style : 'margin:5px 5px 5px 5px;',
				text : '用户账号'
			}, {
				columnWidth : .2,
				xtype : 'textfield',
				name : 'Q_appUser.username_S_LK',
				maxLength : 256
			}, {
				style : 'margin:5px 5px 5px 5px',
				text : '用户名称'
			}, {
				columnWidth : .2,
				xtype : 'textfield',
				name : 'Q_appUser.fullname_S_LK',
				maxLength : 256
			}, {
				style : 'margin: 5px 5px 5px 5px;',
				text : '主部门(是/否)'
			}, {
				columnWidth : .2,
				xtype : 'combo',
				hiddenName : 'Q_isMain_SN_EQ',
				displayField : 'name',
				valueField : 'id',
				mode : 'local',
				triggerAction : 'all',
				editable : false,
				store : [['0','否'],['1','是']],
				emptyText : '全部'
			}, {
				style : 'margin-left: 5px;',
				xtype : 'button',
				text : '搜索',
				scope : this,
				iconCls : 'search',
				handler : DepartmentView.search
			}, {
				xtype : 'button',
				text : '清空',
				scope : this,
				iconCls : 'reset',
				handler : DepartmentView.reset
			}]
		}]
	});
	///////////////////////////////////////////////

	var panel = new Ext.Panel({
		// TODO panel DepartmentView总面板
		id : 'DepartmentView',
		title : '部门人员信息',
		closable : true,
		iconCls : 'menu-department',
		layout : 'border',
		items : [treePanel, searchPanel, grid],
		keys : [{
			key : Ext.EventObject.ESC,
			fn : DepartmentView.reset,
			scope : this
		}, {
			key : Ext.EventObject.ENTER,
			fn : DepartmentView.search,
			scope : this
		}]
	});
	return panel;
};

/**
 * Store对象
 */
DepartmentView.prototype.initData = function() {
	var store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : __ctxPath + '/system/listDepUsers.do'
		}),
		reader : new Ext.data.JsonReader({
			root : 'result',
			totalProperty : 'totalCounts',
			fields : [{
					name : 'depUserId',
					type : 'int'
				}, {
					name : 'isMain',
					type : 'int'
				}, {
					name : 'sn',
					type : 'int'
				}, 'department', 'appUser']
		}),
		remoteSort : true
	});
	store.setDefaultSort('id', 'desc');
	return store;
};

/**
 * 删除多条数据
 */
DepartmentView.multiRemove = function(){
	var grid = Ext.getCmp('DepUsersView');
	var rows = grid.getSelectionModel().getSelections();
	if(rows != null && rows.length>0){
		var ids = new Array();
		for(var i=0;i<rows.length;i++)
			ids.push(rows[i].data.depUserId);
		DepartmentView.remove(ids);
	}else
		Ext.ux.Toast.msg('操作提示','对不起，请选择你要删除的数据！');
},

/**
 * 用户删除
 * 
 * @param {}
 *            userId
 */
DepartmentView.remove = function(_ids) {
	Ext.Msg.confirm('删除操作', '你确定要删除该用户吗?', function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url : __ctxPath + '/system/multiDelDepUsers.do',
				method : 'post',
				params : {
					ids : _ids
				},
				success : function(response) {
					Ext.ux.Toast.msg("操作信息", "用户删除成功");
					Ext.getCmp('DepUsersView').getStore().reload();
				},
				failure : function() {
					Ext.ux.Toast.msg("操作信息", "用户删除失败");
				}
			});
		}
	});
};

/**
 * 节点单击事件
 * @param node
 */
DepartmentView.clickNode = function(node) {
	DepartmentView.select(node);
};

/**
 * 清空
 */
DepartmentView.reset = function(){
	var searchPanel = Ext.getCmp('departmentViewSearchPanel');
	searchPanel.getForm().reset();
};

/**
 * 搜索
 */
DepartmentView.search = function(){
	DepartmentView.select(null);
};

/**
 * 条件查询
 * @param node
 */
DepartmentView.select = function(node){
	var fm = Ext.getCmp('departmentViewSearchPanel');
	var username = fm.getCmpByName('Q_appUser.username_S_LK').getValue();
	var fullname = fm.getCmpByName('Q_appUser.fullname_S_LK').getValue();
	var isMain = fm.getCmpByName('Q_isMain_SN_EQ').getValue();
	var users = Ext.getCmp('DepUsersView');
	var store = users.getStore();
	store.url = __ctxPath + '/system/listDepUsers.do';
	var paramObj = {
		start : 0,
		limit : 25,
		'Q_appUser.username_S_LK' : username,
		'Q_appUser.fullname_S_LK' : fullname
	};
	if(isMain == '0'){ // 副
		paramObj['Q_isMain_SN_EQ'] = '';
	} else if(isMain == '1'){ // 主
		paramObj['Q_isMain_SN_EQ'] = '1';
	}
	if(node != null && node.id > 0){
		paramObj["depId"] = node.id;
	}
	store.reload({
		params : paramObj
	});
};

/**
 * 添加部门人员信息
 */
DepartmentView.add = function(){
	
	var node = Ext.getCmp('departmentTreePanel').getSelectionModel().getSelectedNode();
	/**
	 * 
	 * 此处修改成在员工列表添加员工方式
	 * 
	if(node != null && node.id > 0){		
		new DepUsersForm({
			depId : node.id,
			depName : node.text
		}).show();
	}else{
		new DepUsersForm().show();
	}
	**/
	var tabs = Ext.getCmp('centerTabPanel');
	var addUser = Ext.getCmp('AppUserForm');
	var dId,dName;
	if(node != null && node.id > 0){
		dId=node.id;
		dName=node.text;
	}
	if (addUser == null) {
		addUser = new AppUserForm('增加员工',null,dId,dName);
		tabs.add(addUser);
	} else {
		tabs.remove(addUser)
		addUser = new AppUserForm('增加员工',null,dId,dName);
		tabs.add(addUser);
	}
	tabs.activate(addUser);
};

/**
 * 编辑
 */
DepartmentView.edit = function(userId,userName){//depUserId
	var node = Ext.getCmp('departmentTreePanel').getSelectionModel().getSelectedNode();
	/**
	 * 
	 *
	if(node != null && node.id > 0){		
		new DepUsersForm({
			depUserId : depUserId,
			depId : node.id,
			depName : node.text
		}).show();
	}else{
		new DepUsersForm({
			depUserId : depUserId
		}).show();
	}
	**/
	/**
	 * 直接引用APPUSERVIEW里面的方法 修改BY LYY
	 */
	AppUserView.edit(userId,userName);
};

/**
 * 查询该员工的所有部门信息
 */
DepartmentView.show = function(userId,username){
	DepUsersDetailForm.show(userId, username);
};

/**
 * 添加上下级别
 */
DepartmentView.addRelativeJob = function(userId,username,depId){
	new RelativeUserView({
		userId : userId, //用户编号
		username : username,
		depId : depId //部门编号
	}).show();
},

DepartmentView.sn = function(rowIndex, sn) {
	var users = Ext.getCmp('DepUsersView');
	var store = users.getStore();
	var thisIndex = rowIndex;
	var thatIndex = rowIndex + sn;
	
	var this_R = store.getAt(thisIndex);
	var that_R = store.getAt(thatIndex);
	
	var this_sn=this_R.get('sn');
	var that_sn=that_R.get('sn');
	
	this_R.data.sn = that_sn;
	that_R.data.sn = this_sn;

	var depParams=new Array();
	for(i=0;i<store.getCount();i++){
		var r=store.getAt(i);
		depParams.push(r.data);
	}
	
	Ext.Ajax.request({
		url : __ctxPath + '/system/snDepUsers.do',
		method : 'POST',
		//async : true,
		success : function(response, opts) {
			store.reload();
		},
		failure : function(response, opts) {
		},
		params : {
			depParams:Ext.encode(depParams)
		}
	});

};
