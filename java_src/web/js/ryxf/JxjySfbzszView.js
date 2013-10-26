/**
 * @author:
 * @class JxjySfbzszView
 * @extends Ext.Panel
 * @description [JxjySfbzsz]管理
 * @company 广州宏天软件有限公司
 * @createtime:
 */
JxjySfbzszView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		JxjySfbzszView.superclass.constructor.call(this, {
			id : 'JxjySfbzszView',
			title : '授分标准设置',
			region : 'center',
			layout : 'border',
			items : [  this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel( {
			layout : 'form',
			region : 'north',
			colNums : 3,
			items : [ {
				fieldLabel : 'xmId',
				name : 'Q_xmId_L_EQ',
				flex : 1,
				//xtype : 'numberfield'
				xtype : 'hidden'
			}, {
				fieldLabel : '项目名称',
				name : 'Q_xmmcVo_S_EQ',
				width:300,
				flex : 1,
				xtype : 'textfield'
			}, {
				fieldLabel : '学分类别',
				name : 'Q_xflbVo_S_EQ',
				width:300,
				flex : 1,
				xtype : 'textfield'
			}],
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
				text : '添加标准',
				xtype : 'button',
				scope : this,
				handler : this.createRs
			}
//			, {
//				iconCls : 'btn-del',
//				text : '删除标准',
//				xtype : 'button',
//				scope : this,
//				handler : this.removeSelRs
//			}
			]
		});

		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			tbar : this.topbar,
			//使用RowActions
			rowActions : true,
			id : 'JxjySfbzszGrid',
			url : __ctxPath + "/ryxf/listJxjySfbzsz.do",
			fields : [ {
				name : 'id',
				type : 'int'
			}, 'mx','xmmc','lbmcVo' ],
			columns : [ {
				header : 'id',
				dataIndex : 'id',
				hidden : true
			}, {
				header : '项目名称',
				dataIndex : 'xmmc'
			}, {
				header : '学分类别',
				dataIndex : 'lbmcVo'
			}, {
				header : '描述',
				dataIndex : 'mx'
			}, new Ext.ux.grid.RowActions( {
				header : '管理',
				width : 100,
				actions : [ {
					//iconCls : 'btn-del',
					iconCls : 'btn-update',
					text : '授分标准明细',
					qtip : '授分标准明细',
					style : 'margin:0 3px 0 3px'
				}
				, {
					iconCls : 'btn-edit',
					text : '编辑',
					qtip : '编辑',
					style : 'margin:0 3px 0 3px'
				} 
				],
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
			new JxjySfbzszForm( {
				id : rec.data.id
			}).show();
		});
	},
	//创建记录
	createRs : function() {
		new JxjySfbzszForm().show();
	},
	//按ID删除记录
	removeRs : function(id) {
		$postDel( {
			url : __ctxPath + '/ryxf/multiDelJxjySfbzsz.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	
	//把选中ID删除
	removeSelRs : function() {
		$delGridRs( {
			url : __ctxPath + '/ryxf/multiDelJxjySfbzsz.do',
			grid : this.gridPanel,
			idName : 'id'
		});
	},
	//编辑Rs
	editRs : function(record) {
		new JxjySfbzszForm( {
			id : record.data.id
		}).show();
	},
	sfmx : function(record){
				new SFMXForm({
					id:record.data.id,
					xmmc:record.data.xmmc
				}).show();
	},
	//行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
		case 'btn-update':
			//this.removeRs.call(this, record.data.id);
			this.sfmx.call(this, record);
			break;
		case 'btn-edit':
			this.editRs.call(this, record);
			break;
		default:
			break;
		}
	}
});
