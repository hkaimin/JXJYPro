Ext.ns('ProcessRunView');
/**
 * 申请列表
 */
var ProcessRunView = function() {
	return new Ext.Panel({
		id : 'ProcessRunView',
		title : '我的申请流程列表',
		iconCls:'menu-flowMine',
		layout:'border',
		region : 'center',
		autoScroll : true,
		items : [new Ext.FormPanel({
			id : 'ProcessRunSearchForm',
			region:'north',
			height : 40,
			frame : false,
			border : false,
			layout : 'hbox',
			layoutConfig : {
				padding : '5',
				align : 'middle'
			},
			defaults : {
				xtype : 'label',
				margins : {
					top : 0,
					right : 4,
					bottom : 4,
					left : 4
				}
			},
			items : [{
						text : '请输入查询条件:'
					}, {
						text : '标题'
					}, {
						xtype : 'textfield',
						name : 'Q_subject_S_LK'
					}, {text:'时间 从'},
					   {xtype:'datefield',name:'Q_createtime_D_GT',format:'Y-m-d'},
					   {text:' 至 '},
					   {xtype:'datefield',name:'Q_createtime_D_LT',format:'Y-m-d'},
					   {text:'状态'},
					   {
						   	xtype:'combo',
						   	width:80,
						   	hiddenName:'Q_runStatus_SN_EQ',
						   	editable:false,
						   	mode:'local',
						   	triggerAction : 'all',
						   	store :[['1','正在运行'],['2','结束']]
					   	}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext
									.getCmp('ProcessRunSearchForm');
							var gridPanel = Ext.getCmp('ProcessRunGrid');
							if (searchPanel.getForm().isValid()) {
								$search({
									searchPanel :searchPanel,
									gridPanel : gridPanel
								});
							}

						}
					}]
		}), this.setup()]
	});
};
/**
 * 建立视图
 */
ProcessRunView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
ProcessRunView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'runId',
					dataIndex : 'runId',
					hidden : true
				}, {
					header : '标题',
					dataIndex : 'subject'
				},{
					header : '时间',
					dataIndex : 'createtime',
					width:60
				},  {
					header:'流程状态',
					dataIndex:'runStatus',
					renderer:function(val){
						if(val==0){
							return '<font color="red">草稿</font>';
						}else if(val==1){
							return '<font color="green">正在运行</font>';
						}else if(val==2){
							return '<font color="gray">结束</font>';
						}
					}
				},{
					header : '管理',
					dataIndex : 'runId',
					width : 50,
					renderer : function(value, metadata, record, rowIndex,colIndex) {
						var runId = record.data.runId;
						var defId=record.data.defId;
						var piId=record.data.piId;
						var subject=record.data.subject;
						var runStatus=record.data.runStatus
						var str="";
						//runId,defId,piId,name
						if(piId!=null && piId!=undefined && piId!=''){		
							str += '&nbsp;<button type="button" title="审批明细" value=" " class="btn-flowView" onclick="ProcessRunView.detail('+
							runId+','+defId+',\''+ piId + '\',\''+ subject + '\')"></button>';
						}
						//若流程尚未结束，可进行编辑,及删除
						if(runStatus==0){
							str+='&nbsp;<button title="编辑" class="btn-edit" onclick="ProcessRunView.edit('+runId+',\''+subject+'\')"></button>';
							str+= '&nbsp;<button title="删除" value=" " class="btn-del" onclick="ProcessRunView.remove('+ runId + ')"></button>';
						}
						return str;
					}
				}],
		defaults : {
			sortable : true,
			menuDisabled : false,
			width : 100
		}
	});

	var store = this.store();
	store.load({
				params : {
					start : 0,
					limit : 25
				}
			});
	var grid = new Ext.grid.GridPanel({
				id : 'ProcessRunGrid',
				store : store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				region : 'center',
				tbar:new Ext.Toolbar(),
				cm : cm,
				sm : sm,
				viewConfig : {
					forceFit : true,
					enableRowBody : false,
					showPreview : false
				},
				bbar : new HT.PagingBar({store : store})
			});
			
			grid.addListener('rowdblclick', function(gridPanel,
						rowindex, e) {
					gridPanel.getSelectionModel().each(function(rec) {
							ProcessRunView.detail(rec.data.runId,rec.data.defId,rec.data.piId,rec.data.subject);
					});
			});
	return grid;

};

/**
 * 初始化数据
 */
ProcessRunView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/flow/listProcessRun.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'runId',
										type : 'int'
									}, 'subject','createtime',
									'defId', 'piId','runStatus']
						}),
				remoteSort : true
			});
	store.setDefaultSort('runId', 'desc');
	return store;
};

/**
 * 删除单个记录
 */
ProcessRunView.remove = function(id) {
	var grid = Ext.getCmp("ProcessRunGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath + '/flow/multiDelProcessRun.do',
								params : {
									ids : id
								},
								method : 'post',
								success : function() {
									Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
									grid.getStore().reload({params : {start : 0,limit : 25}});
								}
							});
				}
			});
};

/**
 * 
 */
ProcessRunView.edit = function(runId,name) {
	var contentPanel=App.getContentPanel();
	var startForm=contentPanel.getItem('ProcessRunStart'+runId);
	if(startForm==null){
		startForm=new ProcessRunStart(null,name,runId);
		contentPanel.add(startForm);
	}
	contentPanel.activate(startForm);
}
/**
 * 显示明细
 * @param {} runId
 * @param {} name
 */
ProcessRunView.detail=function(runId,defId,piId,name){
	var contentPanel=App.getContentPanel();
	var detailView=contentPanel.getItem('ProcessRunDetail'+runId);
	
	if(detailView==null){
		detailView=new ProcessRunDetail(runId,defId,piId,name);
		contentPanel.add(detailView);
	}
	contentPanel.activate(detailView);
};
