/**
 * @author:
 * @class CheckSearchView
 * @extends Ext.Panel
 * @description [DpBdz]管理
 * @company 广州宏天软件有限公司
 * @createtime:
 */
CheckSearchView = Ext.extend(Ext.Panel,{
	constructor:function(conf){
		Ext.applyIf(this,conf);
		this.initUI();
		CheckSearchView.superclass.constructor.call(this,{
			id:'CheckSearchView',
			title:'达标查询',
//			iconCls:'menu-RmBdzView',
			layout:'border',
			items:[
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
		
		
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel( {
			id:'RmBdz_SearchPanel',
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
					text : '人员编号'
			}, {
				name : 'dbry.rybh',
				xtype : 'textfield'
			}, {
					text : '人员名称'
			}, {
				name : 'dbry.xm',
				xtype : 'textfield'
			} , {
				xtype : 'button',
				text : '查询',
				scope : this,
				iconCls : 'btn-search',
				handler : this.search
			}, {
				xtype : 'button',
				text : '重置',
				scope : this,
				iconCls : 'btn-reset',
				handler : this.reset
			} ]
		});// end of searchPanel


		this.gridPanel = new HT.GridPanel( {
			region : 'center',
//			tbar : this.topbar,
			//使用RowActions
			title : '考核信息列表',
			rowActions : false,
			id : 'checkSearch',
			url : __ctxPath + "/project/listCheckDbbzAction.do",
			fields : [ 'rybh','xm', 'db', 'nf', 'dbId'],
			columns : [ 
			{
				header : '人员编号',
				dataIndex : 'rybh'
//				sortable: true
			}, {
				header : '人员名',
				dataIndex : 'xm',
				width:180
			}, {
				header : '考核年份',
				dataIndex : 'nf',
				sortable: true
			}, {
				header : '是否达标',
				dataIndex : 'db'
			}]
		//end of columns
		});

//		this.gridPanel.addListener('rowdblclick', this.rowClick);

		//右边面板
		this.centerPanel=new Ext.Panel({
			id:'BdzViewCenterPanel',
			region:'center',
			layout : 'border',
			title:'考核信息',
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
		new DpBdzForm({
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



