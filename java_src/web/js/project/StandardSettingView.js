/**
 * @author:
 * @class StandardSettingView
 * @extends Ext.Panel
 * @description [JxjyXmgl]管理
 * @company 广州宏天软件有限公司
 * @createtime:
 */
StandardSettingView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		StandardSettingView.superclass.constructor.call(this, {
			id : 'StandardSettingView',
			title : '达标标准设置',
			region : 'center',
			layout : 'border',
			items : [this.searchPanel, this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel( {
			layout : 'hbox',
			region : 'north',
			layoutConfig : {
				align : 'middle',
				padding : '5'
			},
			defaults : {
				xtype : 'label',
				border:false,
				margins:{top:0, right:4, bottom:2, left:4}
			},
			items : [ 
			 {
					text : '年份'
			}, {
				id:'biaozhun.nf',
				name : 'nf',
				xtype : 'combo',
				editable : false,
				emptyText:'请选择',
				triggerAction :'all',
				hiddenName:'nf',
				value : '2013',
				store : [
					['2013','2013'],
					['2012','2012'],
					['2011','2011']
						]
			} , {
				xtype : 'button',
				text : '查询',
				scope : this,
				iconCls : 'btn-search',
				handler : this.search
			} ,{
					name : 'method',
					value: 'search',
					xtype : 'hidden'
			}, {
					name : 'orgId',
					xtype : 'hidden'
			}]
		});// end of searchPanel

		this.topbar = new Ext.Toolbar( {
			items : [ {
				iconCls : 'btn-add',
				text : '新增达标标准',
				xtype : 'button',
				scope : this,
				handler : this.createRs
			}]
		});

		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			tbar : this.topbar,
//			title : '审核学分设置',
			//使用RowActions
			rowActions : true,
			id : 'bzGrid',
			url : __ctxPath + "/project/listDbbzAction.do",
			fields : [ 'id', 'yylx', 'zc', 'khnr', 'xfz', 'zgf', 'tjms', 'khnrId', 'tjlx', 'nf'],
			columns : [ {
				header : '医院类型',
				dataIndex : 'yylx',
				renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						if(value == "3") {
							return "一级及以下医院";
						} else if(value == "4") {
							return "二级医院";
						} else if(value == "5") {
							return "三级医院";
						} else {
							return "类型错误";
						}
					}
			}, {
				header : '职称',
				dataIndex : 'zc'
			}, {
				header : '考核内容',
				dataIndex : 'khnr'
			}, {
				header : '条件类型',
				dataIndex : 'tjlx',
				renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						if(value == "1") {
							return "达标条件";
						} else if(value == "2") {
							return "最高分限制";
						} else{
							return "类型错误";
						}
					}
			}, {
				header : '条件描述',
				dataIndex : 'tjms'
			},{
				header : '分数值',
				dataIndex : 'zgf'
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
		var nf = Ext.getCmp('biaozhun.nf').value;
		new StandardForm({
			nf : nf
		}).show();
	},
	//按ID删除记录
	removeRs : function(id) {
		$postDel( {
			url : __ctxPath + '/project/multiDelDbbzAction.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	//把选中ID删除
	removeSelRs : function() {
		$delGridRs( {
			url : __ctxPath + '/project/multiDelDbbzAction.do',
			grid : this.gridPanel,
			idName : 'id'
		});
	},
	//编辑Rs
	editRs : function(record) {
		new StandardForm({
			id : record.data.id
		}).show();
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
