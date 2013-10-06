/**
 * @author:
 * @class CreditSettingView
 * @extends Ext.Panel
 * @description [JxjyXmgl]管理
 * @company 广州宏天软件有限公司
 * @createtime:
 */
CreditSettingView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		CreditSettingView.superclass.constructor.call(this, {
			id : 'CreditSettingView',
			title : '审核学分设置',
			region : 'center',
			layout : 'border',
			items : [ this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel( {
			layout : 'form',
			region : 'north',
			colNums : 3,
			items : [ 
//				{
//				fieldLabel : '学分类别id',
//				name : 'Q_xflbid_L_EQ',
//				flex : 1,
//				xtype : 'numberfield'
//			},
//			{
//				fieldLabel : 'mc',
//				name : 'Q_mc_S_EQ',
//				flex : 1,
//				xtype : 'textfield'
//			}, 
			{
				fieldLabel : '项目名称',
				name : 'Q_xmmc_S_EQ',
				flex : 1,
				xtype : 'textfield'
			},
//			{
//				fieldLabel : '活动方式',
//				name : 'Q_hdfs_S_EQ',
//				flex : 1,
//				xtype : 'textfield'
//			},
//			{
//				fieldLabel : '审核方式',
//				name : 'Q_shfs_S_EQ',
//				flex : 1,
//				xtype : 'textfield'
//			}, 
//			{
//				fieldLabel : '项目类别',
//				name : 'Q_xmlb_L_EQ',
//				flex : 1,
//				xtype : 'numberfield'
//			}, 
//			{
//				fieldLabel : '总学分',
//				name : 'Q_zxf_S_EQ',
//				flex : 1,
//				xtype : 'textfield'
//			}, {
//				fieldLabel : '总学时',
//				name : 'Q_zxs_S_EQ',
//				flex : 1,
//				xtype : 'textfield'
//			},
			{
				fieldLabel : '举办时间',
				name : 'Q_jbsj_S_EQ',
				flex : 1,
				xtype : 'textfield'
			}, 
//			{
//				fieldLabel : '提交时间',
//				name : 'Q_tjsj_S_EQ',
//				flex : 1,
//				xtype : 'textfield'
//			}, 
			{
				fieldLabel : '学分类别',
				name : 'Q_xflb_S_EQ',
				flex : 1,
				xtype : 'textfield'
			}, {
				fieldLabel : '主办单位',
				name : 'Q_zbdw_S_EQ',
				flex : 1,
				xtype : 'textfield'
			},
//			{
//				fieldLabel : '审核状态',
//				name : 'Q_zt_S_EQ',
//				flex : 1,
//				xtype : 'textfield'
//			}, 
//			{
//				fieldLabel : '主办单位id',
//				name : 'Q_zbbwid_L_EQ',
//				flex : 1,
//				xtype : 'numberfield'
//			},
			{
				fieldLabel : '医院审核',
				name : 'Q_yysh_L_EQ',
				flex : 1,
				xtype : 'numberfield'
			}, {
				fieldLabel : '项目编号',
				name : 'Q_xmbh_L_EQ',
				flex : 1,
				xtype : 'numberfield'
			} ],
			buttons : [ {
				text : '查询',
				scope : this,
				iconCls : 'btn-search',
				handler : this.search
			}, {
				text : '重置',
				scope : this,
				iconCls : 'btn-reset',
				handler : this.reset
			} ]
		});// end of searchPanel

		this.topbar = new Ext.Toolbar( {
			items : [ {
				iconCls : 'btn-add',
				text : '添加学分类别',
				xtype : 'button',
				scope : this,
				handler : this.createRs
			}, '->', {
				iconCls : 'btn-search',
				text : '审核学分',
				xtype : 'button',
				scope : this,
				handler : this.checkCredit
			}, '-', {
				iconCls : 'btn-search',
				text : '审核项目',
				xtype : 'button',
				scope : this,
				handler : this.checkProject
			}, '-', {
				iconCls : 'btn-search',
				text : '审核项目及学分',
				xtype : 'button',
				scope : this,
				handler : this.checkProjectCredit
			}, '-', {
				iconCls : 'btn-search',
				text : '取消审核',
				xtype : 'button',
				scope : this,
				handler : this.notCheck
			}]
		});

		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			tbar : this.topbar,
			title : '审核学分设置',
			//使用RowActions
			rowActions : true,
			id : 'creditGrid',
			url : __ctxPath + "/project/listCredit.do",
			fields : [ 'xflbid', 'id', 'mc', 'xfmc', 'xfmcid', 'sfsh', 'xfbz'],
			columns : [ {
				header : '学分名称',
				dataIndex : 'mc',
				renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var xfbz = record.data.xfbz;
						if(xfbz != null && xfbz != "" || xfbz == "0") {
							return "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" + value;
						} else {
							return value;
						}
					}
			}, {
				header : '审核否',
				dataIndex : 'sfsh',
				renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var xflbid = record.data.xflbid;
						if(xflbid != null && xflbid != "") {
							if(value == '0') {
								return "审核学分";
							} else if(value == "1") {
								return "审核项目"
							} else if(value == "2") {
								return "审核项目及学分";
							} else if(value == "3") {
								return "不审核";
							}else {
								return "无状态";
							}
						} else {
							return "";
						}
						
					}
			},
			new Ext.ux.grid.RowActions( {
				header : '管理',
				width : 100,
				actions : [ {
					iconCls : 'btn-del',
					qtip : '删除',
					style : 'margin:0 3px 0 3px'
				}, {
					iconCls : 'btn-edit',
					qtip : '编辑',
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
		this.gridPanel.getSelectionModel().addListener('beforerowselect', this.gridSelect);

	},// end of the initComponents()
	
	//列表选择事件
	gridSelect : function(model, rowIndex, flage, record) {
//		alert(Ext.encode(record.data));
		var xflbid = record.data.xflbid;
		if(xflbid == null || xflbid == "") {
			Ext.ux.Toast.msg("提示信息", "具体学分类别不用选择");
			return false;
		}
	},
	//重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	//按条件搜索
	search : function() {
		$search( {
			searchPanel : this.searchPanel,
			gridPanel : this.gridPanel
		});
	},
	//GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
			new JxjyXmglForm( {
				xmId : rec.data.xmId
			}).show();
		});
	},
	//创建记录
	createRs : function() {
		new CreditForm({
			
		}).show();
	},
	//按ID删除记录
	removeRs : function(id) {
		if(id == "" || id == null) {
//			Ext.ux.Toast.msg("操作信息","学分大类别不能修改");
			return;
		}
		$postDel( {
			url : __ctxPath + '/project/multiDelCredit.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	//把选中ID删除
	removeSelRs : function() {
		$delGridRs( {
			url : __ctxPath + '/project/multiDelCredit.do',
			grid : this.gridPanel,
			idName : 'xflbid'
		});
	},
	//编辑Rs
	editRs : function(record) {
		var xflbid = record.data.xflbid;
		if(xflbid == "" || xflbid == null) {
//			Ext.ux.Toast.msg("操作信息","学分大类别不能修改");
			return;
		}
		new CreditForm({
			xflbid : record.data.xflbid
		}).show();
	},
	//审核学分
	checkCredit : function() {
		var flage = "0"
		var records = this.gridPanel.getSelectionModel().getSelections();
		if(records.length <=0 ) {
			Ext.ux.Toast.msg("操作信息","请选择需要操作的记录");
			return;
		}
		
		var ids = [];
		for(var i=0;i<records.length;i++) {
			ids.push(records[i].data.xflbid);	
		}
		var gridPanel = this.gridPanel;
		Ext.Msg.confirm("信息确认", "是否修改学分类别的审核状态？", function(btn){
            if (btn == "yes") {
    				Ext.Ajax.request({
						url : __ctxPath + '/project/modifiedCredit.do',
						method : 'POST',
						params : {
							ids : ids,
							flage : flage
						},
						success : function(form, action) {
							
							var result = Ext.util.JSON.decode(form.responseText);
							if (result.success == true) {
								Ext.ux.Toast.msg("操作信息", "信息修改成功");
																		
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
	//审核项目
	checkProject : function() {
		var flage = "1"
		var records = this.gridPanel.getSelectionModel().getSelections();
		if(records.length <=0 ) {
			Ext.ux.Toast.msg("操作信息","请选择需要操作的记录");
			return;
		}
		
		var ids = [];
		for(var i=0;i<records.length;i++) {
			ids.push(records[i].data.xflbid);	
		}
		var gridPanel = this.gridPanel;
		Ext.Msg.confirm("信息确认", "是否修改学分类别的审核状态？", function(btn){
            if (btn == "yes") {
    				Ext.Ajax.request({
						url : __ctxPath + '/project/modifiedCredit.do',
						method : 'POST',
						params : {
							ids : ids,
							flage : flage
						},
						success : function(form, action) {
							
							var result = Ext.util.JSON.decode(form.responseText);
							if (result.success == true) {
								Ext.ux.Toast.msg("操作信息", "信息修改成功");
																		
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
	//审核项目及学分
	checkProjectCredit : function(record) {
		var flage = "2"
		var records = this.gridPanel.getSelectionModel().getSelections();
		if(records.length <=0 ) {
			Ext.ux.Toast.msg("操作信息","请选择需要操作的记录");
			return;
		}
		
		var ids = [];
		for(var i=0;i<records.length;i++) {
			ids.push(records[i].data.xflbid);	
		}
		var gridPanel = this.gridPanel;
		Ext.Msg.confirm("信息确认", "是否修改学分类别的审核状态？", function(btn){
            if (btn == "yes") {
    				Ext.Ajax.request({
						url : __ctxPath + '/project/modifiedCredit.do',
						method : 'POST',
						params : {
							ids : ids,
							flage : flage
						},
						success : function(form, action) {
							
							var result = Ext.util.JSON.decode(form.responseText);
							if (result.success == true) {
								Ext.ux.Toast.msg("操作信息", "信息修改成功");
																		
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
	//不审核
	notCheck : function(record) {
		var flage = "3"
		var records = this.gridPanel.getSelectionModel().getSelections();
		if(records.length <=0 ) {
			Ext.ux.Toast.msg("操作信息","请选择需要操作的记录");
			return;
		}
		
		var ids = [];
		for(var i=0;i<records.length;i++) {
			ids.push(records[i].data.xflbid);	
		}
		var gridPanel = this.gridPanel;
		Ext.Msg.confirm("信息确认", "是否修改学分类别的审核状态？", function(btn){
            if (btn == "yes") {
    				Ext.Ajax.request({
						url : __ctxPath + '/project/modifiedCredit.do',
						method : 'POST',
						params : {
							ids : ids,
							flage : flage
						},
						success : function(form, action) {
							
							var result = Ext.util.JSON.decode(form.responseText);
							if (result.success == true) {
								Ext.ux.Toast.msg("操作信息", "信息修改成功");
																		
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
	//行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
		case 'btn-del':
			this.removeRs.call(this, record.data.xflbid);
			break;
		case 'btn-edit':
			this.editRs.call(this, record);
			break;
		default:
			break;
		}
	}
});
