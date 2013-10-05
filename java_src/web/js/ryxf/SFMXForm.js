/**
 * @author huangkaimin
 * @createtime
 *
 */
SFMXForm = Ext.extend(Ext.Window, {
	constructor : function(config) {
		Ext.applyIf(this, config);
		this.initUIComponents();
		SFMXForm.superclass.constructor.call(this, {
			id : 'SFMXForm',
			title : '授分标准明细管理',
			iconCls : 'menu-flowNew',
			modal : true,
			width : 1000,
			height : 600,
			layout : 'border',
			autoScroll : true
		});
	},

	initUIComponents : function() {

		this.initSearchPanel();
		this.initGridPanel();
		this.items = [  this.gridPanel ];

	}
});

/**
 * 初始化SearchPanel
 */
SFMXForm.prototype.initSearchPanel = function() {
	this.searchPanel = new Ext.FormPanel( {
		region : 'north',
		height : 35,
		frame : false,
		border : false,
		id : 'EquipAttributeSearchForm',
		layout : 'hbox',
		layoutConfig : {
			padding : '5',
			align : 'middle'
		},
		defaults : {
			xtype : 'label',
			border : false,
			margins : {
				top : 0,
				right : 4,
				bottom : 4,
				left : 4
			}
		},

		items : [ {
			text : '属性名称'
		}, {
			id : 'textone',
			xtype : 'textfield',
			name : 'attriLabel'
		}, {
			text : '字段名称'
		}, {
			id : 'texttwo',
			xtype : 'textfield',
			name : 'attriField'
		}, {
			xtype : 'button',
			text : '查询',
			iconCls : 'search',
			scope : this,
			handler : function() {

				var attriLabel = Ext.getCmp("textone").getValue();
				var attriField = Ext.getCmp("texttwo").getValue();
				if (attriLabel != "" || attriField != "") {
					Ext.getCmp("movetop").disabled = true;
					Ext.getCmp("moveupp").disabled = true;
					Ext.getCmp("movedown").disabled = true;
					Ext.getCmp("movebottom").disabled = true;
				} else {
					Ext.getCmp("movetop").disabled = false;
					Ext.getCmp("moveupp").disabled = false;
					Ext.getCmp("movedown").disabled = false;
					Ext.getCmp("movebottom").disabled = false;
				}

				var equipmentFno = this.equipmentFno;
				var grid = Ext.getCmp("EquipAttributeGrid");

				Ext.Ajax.request( {
					url : __ctxPath + '/equipment/listEquipmentAttribute.do',
					params : {
						attriLabel : attriLabel,
						attriField : attriField,
						equipmentFno : equipmentFno
					},
					method : 'post',
					success : function() {
						grid.getStore().reload( {
							params : {
								attriLabel : attriLabel,
								attriField : attriField,
								equipmentFno : equipmentFno,
								start : 0,
								limit : 25

							}
						});
					}
				});

			}
		}, {
			xtype : 'button',
			text : '重置',
			iconCls : 'btn-refresh',
			scope : this,
			handler : function() {

				Ext.getCmp('textone').reset();
				Ext.getCmp('texttwo').reset();
			}
		} ]
	});//end of search panel

};

SFMXForm.prototype.initGridPanel = function() {
	var szid = this.id;
	var xmmc = this.xmmc;
	this.toolbar = new Ext.Toolbar( {
		height : 30,
		items : []
	});

	this.toolbar.add(new Ext.Button( {
		text : '添加',
		iconCls : 'btn-add',
		handler : function() {

			//App.clickTopTab('EquipmentEditPanel');

		new SFMXSAVEForm({
			szid : szid,
			xmmc : xmmc
		}).show();
	}
	}));
	
	this.toolbar.add(new Ext.Button( {
		text : '编辑',
		iconCls : 'btn-edit',
		handler : function() {

			var grid = Ext.getCmp("SFMXGrid");
			var selectRecord = grid.getSelectionModel().getSelections();
			if (selectRecord.length == 0) {
				Ext.ux.Toast.msg("信息", "请选择记录！");
				return;
			}
			if (selectRecord.length > 1) {
				Ext.ux.Toast.msg("信息", "只能选择一条记录！");
				return;
			}

		new SFMXSAVEForm({
			szid : szid,
			xmmc : xmmc,
			id : selectRecord[0].data.id
		}).show();
	}
	}));

//	this.toolbar.add(new Ext.Button( {
//		text : '删除',
//		iconCls : 'btn-del',
//		handler : function() {
//
//			var grid = Ext.getCmp("SFMXGrid");
//			var selectRecord = grid.getSelectionModel().getSelections();
//			if (selectRecord.length == 0) {
//				Ext.ux.Toast.msg("信息", "请选择属性！");
//				return;
//			}
//			if (selectRecord.length > 1) {
//				Ext.ux.Toast.msg("信息", "只能选择一条记录！");
//				return;
//			}
//			var attributeAno = selectRecord[0].data.attributeAno;
//			var label = selectRecord[0].data.label;
//			var isNull = selectRecord[0].data.isNull;
//			var defaultValue = selectRecord[0].data.defaultValue;
//			var dataSourceContent = selectRecord[0].data.dataSourceContent;
//			new EquipAttributeEditForm( {
//				attributeAno : attributeAno,
//				label : label,
//				isNull : isNull,
//				defaultValue : defaultValue,
//				dataSourceContent : dataSourceContent,
//				flag : flag
//			}).show();
//		}
//	}));

	var store
= new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/ryxf/listSFMXJxjySfbzmx.do?id='+this.id,
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							fields : [{
										name : 'id',
										type : 'int'
									}, 'xmmc', 'sz','dw',
									'xf','zgz']
						}),
				remoteSort : true
			});
		store.setDefaultSort('attributeAno', 'desc');
		
		store.load({
					params : {
						start : 0,
						limit : 25
					}
		});
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel({
			columns : [sm, new Ext.grid.RowNumberer(), {
						header : 'ID',
						dataIndex : 'id',
						hidden : true
					}
			          ,
					{
						header : '项目名称',
						dataIndex : 'xmmc',
						width : 100
					},
					{
						header : '值',
						dataIndex : 'sz',
						width : 100
					}, 
					{
						header : '值单位',
						dataIndex : 'dw',
						width : 100,
						isExp: true
					},{
						header : '学分',
						dataIndex : 'xf',
						width : 100
					}, {
						header : '最高值',
						dataIndex : 'zgz',
						width : 100
					}
],
			defaults : {
				sortable : true,
				menuDisabled : true,
				width : 100
			}
		});
	
		this.gridPanel = new Ext.grid.GridPanel({
					id : 'SFMXGrid',
					tbar : this.toolbar,
					store : store,
					region:'center',
					//autoHeight:true,
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
					bbar : new HT.PagingBar({store : store,exportable: true})
				});
			// 为Grid增加双击事件,双击行可编辑
		this.gridPanel.addListener('rowdblclick', rowdblclickFn);
	    var panel =	this.gridPanel;
		function rowdblclickFn(panel, rowindex, e) {
			
									panel
									.getSelectionModel()
									.each(
											function(rec) {
												var grid = Ext.getCmp("EquipAttributeGrid");
												var selectRecord = grid
														.getSelectionModel()
														.getSelections();
												if (selectRecord.length == 0) {
													Ext.ux.Toast.msg("信息",
															"请选择属性！");
													return;
												}
												if (selectRecord.length > 1) {
													Ext.ux.Toast.msg("信息",
															"只能选择一条记录！");
													return;
												}
			var attributeAno = selectRecord[0].data.attributeAno;
			var label = selectRecord[0].data.label;
			var isNull = selectRecord[0].data.isNull;
			var defaultValue = selectRecord[0].data.defaultValue;
			var dataSourceContent = selectRecord[0].data.dataSourceContent;

			new EquipAttributeEditForm( {
				attributeAno : attributeAno,
				label : label,
				isNull : isNull,
				defaultValue : defaultValue,
				dataSourceContent : dataSourceContent
			}).show();
											});
									
		}
		
		
};//end of the init GridPanel

SFMXForm.remove = function(id,tableName,field) {
	var grid = Ext.getCmp("EquipmentGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath + '/equipment/multiDelEquipmentAttribute.do',
								params : {
									ids : id,
									tableNames:tableName,
									fields:field
								},
								method : 'post',
								success : function() {
									Ext.ux.Toast.msg("信息", "成功删除所选记录！");
									grid.getStore().reload({
												params : {
													start : 0,
													limit : 25
												}
											});
								}
							});
				}
			});
};
