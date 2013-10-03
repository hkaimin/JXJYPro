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
		JxjyXmglView.superclass.constructor.call(this, {
			id : 'JxjyXmglView',
			title : '课题报名',
			region : 'center',
			layout : 'border',
			items : [ this.searchPanel, this.gridPanel ]
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
				text : '报名',
				xtype : 'button',
				scope : this,
				handler : this.createRs
			}, {
				iconCls : 'btn-del',
				text : '取消报名',
				xtype : 'button',
				scope : this,
				handler : this.removeSelRs
			} ]
		});

		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			tbar : this.topbar,
			//使用RowActions
			rowActions : true,
			id : 'JxjyXmglGrid',
			url : __ctxPath + "/ryxf/listJxjyXmgl.do",
			fields : [ {
				name : 'xmId',
				type : 'int'
			}, 'xflbid', 'mc', 'xmmc', 'hdfs', 'shfs', 'xmlb', 'zxf', 'zxs',
					'jbsj', 'tjsj', 'xflb', 'zbdw', 'zt', 'zbbwid', 'yysh',
					'xmbh','ktidVo','ktmcVo','xfVo','xsVo','skddVo','sksjVo','bmqkVo' ],
			columns : [ {
				header : '项目id',
				dataIndex : 'xmId',
				hidden : true
			}, {
				header : '学分类别id',
				dataIndex : 'xflbid',
				hidden : true
			}, {
				header : 'mc',
				dataIndex : 'mc',
				hidden : true
			}, {
				header : '项目名称',
				dataIndex : 'xmmc'
			}, {
				header : '活动方式',
				dataIndex : 'hdfs'
			}, {
				header : '审核方式',
				dataIndex : 'shfs',
				hidden : true
			}, {
				header : '项目类别',
				dataIndex : 'xmlb',
				hidden : true
			}, {
				header : '总学分',
				dataIndex : 'zxf',
				hidden : true
			}, {
				header : '总学时',
				dataIndex : 'zxs',
				hidden : true
			}, {
				header : '举办时间',
				dataIndex : 'jbsj',
				hidden : true
			}, {
				header : '提交时间',
				dataIndex : 'tjsj',
				hidden : true
			}, {
				header : '学分类别',
				dataIndex : 'xflb'
			}, {
				header : '主办单位',
				dataIndex : 'zbdw'
			}, {
				header : '审核状态',
				dataIndex : 'zt',
				hidden : true
			}, {
				header : '主办单位id',
				dataIndex : 'zbbwid',
				hidden : true
			},
			{
				header : '医院审核',
				dataIndex : 'yysh',
				hidden : true
			}, {
				header : '项目编号',
				dataIndex : 'xmbh',
				hidden : true
			}, {
				header : '课题名称',
				dataIndex : 'ktmcVo'
			},{
				header : '学分',
				dataIndex : 'xfVo'
			},{
				header : '学时',
				dataIndex : 'xsVo'
			},{
				header : '上课地点',
				dataIndex : 'skddVo'
			},{
				header : '授课时间',
				dataIndex : 'sksjVo'
			},{
				header : '报名情况',
				dataIndex : 'bmqkVo'
			},
			new Ext.ux.grid.RowActions( {
				header : '管理',
				width : 100,
				actions : [ {
					iconCls : 'btn-del',
					qtip : '取消报名',
					style : 'margin:0 3px 0 3px'
				}, {
					iconCls : 'btn-edit',
					qtip : '报名',
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

	},// end of the initComponents()
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
		new JxjyXmglForm().show();
	},
	//按ID删除记录
	removeRs : function(id) {
		$postDel( {
			url : __ctxPath + '/ryxf/multiDelJxjyXmgl.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	//把选中ID删除
	removeSelRs : function() {
		$delGridRs( {
			url : __ctxPath + '/ryxf/multiDelJxjyXmgl.do',
			grid : this.gridPanel,
			idName : 'xmId'
		});
	},
	//编辑Rs
	editRs : function(record) {
		new JxjyXmglForm( {
			xmId : record.data.xmId
		}).show();
	},
	//行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
		case 'btn-del':
			this.removeRs.call(this, record.data.xmId);
			break;
		case 'btn-edit':
			this.editRs.call(this, record);
			break;
		default:
			break;
		}
	}
});
