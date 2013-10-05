/**
 * @author:
 * @class JxjyXfshRsjView
 * @extends Ext.Panel
 * @description [JxjyRyxfgl]管理
 * @company 广州宏天软件有限公司
 * @createtime:
 */
JxjyXfshRsjView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents(); 
		// 调用父类构造
		JxjyXfshRsjView.superclass.constructor.call(this, {
			id : 'JxjyXfshRsjView',
			title : '学分审核',
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
			//																																												 								{
					//									fieldLabel:'人员编号',
					//									name : 'Q_rybh_L_EQ',
					//									flex:1,
					//																		xtype:'numberfield'
					//																	}
					//																,
					//															 							 																																					 								{
					//									fieldLabel:'课程ID',
					//									name : 'Q_ktId_L_EQ',
					//									flex:1,
					//																		xtype:'numberfield'
					//																	}
					//																,
					//					{
					//						fieldLabel : '姓名',
					//						name : 'Q_xm_S_EQ',
					//						flex : 1,
					//						xtype : 'textfield'
					//					}, 
					{
						fieldLabel : '日期',
						name : 'Q_rq_S_EQ',
						xtype : 'datefield',
						format : 'Y-m-d',
						flex : 1

					}, {
						fieldLabel : '课题',
						name : 'Q_kt_S_EQ',
						flex : 1,
						xtype : 'textfield'
					}, {
						fieldLabel : '学分类型',
						name : 'Q_xflx_S_EQ',
						flex : 1,
						xtype : 'textfield'
					}, {
						fieldLabel : '学分类别',
						name : 'Q_xflb_S_EQ',
						flex : 1,
						xtype : 'textfield'
					},
					//					{
					//						fieldLabel : '学科',
					//						name : 'Q_xk_S_EQ',
					//						flex : 1,
					//						xtype : 'textfield'
					//					},
					//					{
					//						fieldLabel : '活动形式',
					//						name : 'Q_hdxs_S_EQ',
					//						flex : 1,
					//						xtype : 'textfield'
					//					},
					{
						fieldLabel : '学分',
						name : 'Q_xf_S_EQ',
						flex : 1,
						xtype : 'textfield'
					}, {
						fieldLabel : '学时',
						name : 'Q_xs_S_EQ',
						flex : 1,
						xtype : 'textfield'
					}, {
						fieldLabel : '授分单位',
						name : 'Q_sfdw_S_EQ',
						flex : 1,
						xtype : 'textfield'
					}, {
						fieldLabel : '卫生厅审核',
						name : 'Q_shzt_S_EQ',
						flex : 1,
						xtype : 'textfield'
					},
					//					{
					//						fieldLabel : '备注',
					//						name : 'Q_bz_S_EQ',
					//						flex : 1,
					//						xtype : 'textfield'
					//					},
					{
						fieldLabel : '人社局审核',
						name : 'Q_rsjsh_S_EQ',
						flex : 1,
						xtype : 'textfield'
					}
			//					, {
			//						fieldLabel : '职称',
			//						name : 'Q_zc_S_EQ',
			//						flex : 1,
			//						xtype : 'textfield'
			//					}, {
			//						fieldLabel : '学位',
			//						name : 'Q_xw_S_EQ',
			//						flex : 1,
			//						xtype : 'textfield'
			//					}
			],
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
				iconCls : 'btn-update',
				text : '学分审核',
				xtype : 'button',
				scope : this,
				handler : this.xfCommit
			}]
		});

		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			tbar : this.topbar,
			//使用RowActions
			rowActions : true,
			id : 'JxjyRSJRyxfglGrid',
			url : __ctxPath + "/ryxf/listJxjyRyxfgl.do",
			fields : [ {
				name : 'id',
				type : 'int'
			}, 'rybh', 'ktId', 'xm', 'rq', 'kt', 'xflx', 'xflb', 'xk', 'hdxs',
					'xf', 'xs', 'sfdw', 'shzt', 'bz', 'rsjsh', 'zc', 'xw',
					'is_commit' ],
			columns : [ {
				header : 'id',
				dataIndex : 'id',
				hidden : true
			}, {
				header : '人员编号',
				hidden : true,
				dataIndex : 'rybh'
			}, {
				header : '课题ID',
				hidden : true,
				dataIndex : 'ktId'
			}, {
				header : '姓名',
				dataIndex : 'xm'
			}, {
				header : '日期',
				dataIndex : 'rq'
			}, {
				header : '课题',
				dataIndex : 'kt'
			}, {
				header : '学分类型',
				dataIndex : 'xflx'
			}, {
				header : '学分类别',
				dataIndex : 'xflb'
			}, {
				header : '学科',
				hidden : true,
				dataIndex : 'xk'
			}, {
				header : '活动形式',
				dataIndex : 'hdxs'
			}, {
				header : '学分',
				dataIndex : 'xf'
			}, {
				header : '学时',
				dataIndex : 'xs'
			}, {
				header : '授分单位',
				dataIndex : 'sfdw'
			}, {
				header : '卫生厅审核状态',
				dataIndex : 'shzt',
				renderer : function(value) {
				    if(value=='0'){
				    	return '审核通过';
				    }else if(value=='1'){
				    	return '审核不通过';
				    }
					return '待审核';
				}
			}, {
				header : '备注',
				hidden : true,
				dataIndex : 'bz'
			}, {
				header : '人社局审核状态',
				dataIndex : 'rsjsh',
				renderer : function(value) {
				    if(value=='0'){
				    	return '审核通过';
				    }else if(value=='1'){
				    	return '审核不通过';
				    }
					return '待审核';
				}
			}, 
//			{
//				header : '职称',
//				dataIndex : 'zc'
//			}, {
//				header : '学位',
//				dataIndex : 'xw'
//			},
//			{
//				header : '学分提交状态',
//				dataIndex : 'is_commit',
//				renderer : function(value) {
//				    if(value=='0'){
//				    	return '未提交';
//				    }
//					return '已提交';
//				}
//			}, 
			new Ext.ux.grid.RowActions( {
				header : '-',
				width : 100,
				actions : [ 
//					{
//					iconCls : 'btn-del',
//					qtip : '删除',
//					style : 'margin:0 3px 0 3px'
//				}, {
//					iconCls : 'btn-edit',
//					qtip : '编辑',
//					style : 'margin:0 3px 0 3px'
//				} 
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
			new JxjyRyxfglForm( {
				id : rec.data.id
			}).show();
		});
	},
	//创建记录
	createRs : function() {
		new JxjyRyxfglForm().show();
	},
	//按ID删除记录
	removeRs : function(id) {
		$postDel( {
			url : __ctxPath + '/ryxf/multiDelJxjyRyxfgl.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	//把选中ID删除
	removeSelRs : function() {
		$delGridRs( {
			url : __ctxPath + '/ryxf/multiDelJxjyRyxfgl.do',
			grid : this.gridPanel,
			idName : 'id'
		});
	},
	xfCommit : function() {

		var grid = Ext.getCmp("JxjyRSJRyxfglGrid");
		var selectRecord = grid.getSelectionModel().getSelections();
		if (selectRecord.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要审核的学分记录！");
			return;
		}
//		if (selectRecord.length > 1) {
//			Ext.ux.Toast.msg("信息", "只能选择一条记录！");
//			return;
//		}
		
						var ids="" ;	
						for (var i = 0; i < selectRecord.length; i++) {
							
							if(i==selectRecord.length-1){
								ids+=selectRecord[i].data.id;
							}else{
								ids+=selectRecord[i].data.id+",";
							}						
						}
             new RSJXFSHForm({
            	 ids:ids
             }).show();
		

	},
	xfReview : function() {

		var grid = Ext.getCmp("JxjyRyxfglGrid");
		var selectRecord = grid.getSelectionModel().getSelections();
		if (selectRecord.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要审核的学分记录！");
			return;
		}
		if (selectRecord.length > 1) {
			Ext.ux.Toast.msg("信息", "只能选择一条记录！");
			return;
		}

		Ext.Ajax.request( {
			url : __ctxPath + '/ryxf/commitXFJxjyRyxfgl.do',
							params : {
			                      id:selectRecord[0].data.id
							},
			method : 'post',
			success : function(response) {
				Ext.ux.Toast.msg("信息", "成功提交学分审核！");
                grid.getStore().reload();
			},
			failure : function() {
			}
		});
		

	},
	//编辑Rs
	editRs : function(record) {
		new JxjyRyxfglForm( {
			id : record.data.id
		}).show();
	},
	//行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
		case 'btn-del':
			this.removeRs.call(this, record.data.id);
			break;
		case 'btn-edit':
			this.editRs.call(this, record);
			break;
		default:
			break;
		}
	}
});
