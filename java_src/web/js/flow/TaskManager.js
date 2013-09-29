TaskManager = Ext.extend(Ext.Panel, {
	constructor : function(conf) {
		Ext.applyIf(this, conf);
		this.initUIComponents();
		TaskManager.superclass.constructor.call(this, {
					id:'TaskManager',
					title : '任务管理',
					iconCls:'menu-task-manage',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},
	initUIComponents : function() {
		this.searchPanel = new Ext.form.FormPanel({
					autoHeight : true,
					border:false,
					region : 'north',
					layout : 'hbox',
					layoutConfig:{
						align:'middle',
						pack:'left'
					},
					style:'background-color:white;padding:5px;',
					defaults:{
						margins:'0px 8px 0px 4px'
					},
					items : [
							{
								xtype:'label',
								text:'任务名称:'
							},
							{
								xtype:'textfield',
								name:'taskName',
								width:300
							},
							{
								xtype:'button',
								text : '查询',
								iconCls:'btn-search',
								scope : this,
								handler : this.search
							}
					]
		});

		this.store = new Ext.data.JsonStore({
					baseParams:{
						start:0,
						limit:25
					},
					url : __ctxPath + '/flow/allTask.do',
					root : 'result',
					totalProperty : 'totalCounts',
					fields : ['taskName', 'activityName', 'assignee',
							'createTime', 'dueDate', 'executionId', 'pdId',
							'taskId', 'isMultipleTask']
				});
		this.store.load();
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = new Ext.grid.ColumnModel({
					columns : [sm,new Ext.grid.RowNumberer(), {
								header : "taskId",
								dataIndex : 'taskId',
								hidden:true
							}, {
								header : '任务名称',
								dataIndex : 'taskName',
								width:350
							}, {
								header : '执行人',
								dataIndex : 'assignee',
								renderer : function(value, metadata, record,
										rowIndex, colIndex) {
									var assignee = record.data.assignee;
									if (assignee == null || assignee == '') {
										return '<font color="red">暂无执行人</font>';
									} else {
										return assignee;
									}
								},
								width:100
							}, {
								header : '开始时间',
								dataIndex : 'createTime',
								width : 150
							}, {
								header : '到期时间',
								dataIndex : 'dueDate',
								width : 150,
								renderer : function(value) {
									if (value == '') {
										return '无限制';
									} else {
										return value;
									}
								}
							}
//							, {
//								header : '管理',
//								dataIndex : 'taskdbid',
//								width : 80,
//								renderer : function(value, metadata, record,rowIndex, colIndex) {
//								}
//							}
							],
					defaults : {
						sortable : false,
						menuDisabled : true,
						width : 150
					}
				});
		
		this.gridPanel = new Ext.grid.GridPanel({
					id:'allTaskGrid',
					region : 'center',
					store : this.store,
					shim : true,
					trackMouseOver : true,
					loadMask : true,
					tbar : new Ext.Toolbar({
								items : [{
											text : '刷新',
											iconCls : 'btn-refresh',
											scope : this,
											handler : this.refresh
										}, '-', {
											text : '设置到期时间',
											scope : this,
											handler : this.setDueDate
										},'-',{
											text:'更改待办人',
											scope:this,
											handler:this.setHandler
										},'-',{
											text:'更改执行路径',
											scope:this,
											handler:this.setPath
										},'-',{
										    text:'任务代办',
										    scope:this,
										    handler:this.handlerTask
											
										}]
							}),
					cm : cm,
					bbar : new HT.PagingBar({
								store : this.store
							})
				});
		
		
			
	},//end of initUIComponents
	search : function() {
		
		var taskName=this.searchPanel.getCmpByName('taskName');
		
		this.store.baseParams={
			start:0,
			limit:this.store.baseParams.limit,
			taskName:taskName.getValue()
		};
		this.store.reload();
	},
	refresh : function() {
		this.store.reload();
	},
	//为任务设置过期时间
	setDueDate : function() {
		var taskGrid=Ext.getCmp('allTaskGrid');
		var rs = taskGrid.getSelectionModel().getSelections();
		if(rs.length==0){
			Ext.ux.Toast.msg('操作信息','请选择任务记录!');
			return;
		}
		new TaskDueDateWindow().show();
	},
	//为任务设置待办人
	setHandler:function(){
		var taskGrid=Ext.getCmp('allTaskGrid');
		var rs = taskGrid.getSelectionModel().getSelections();
		if(rs.length==0){
			Ext.ux.Toast.msg('操作信息','请选择任务记录!');
			return;
		}
		new TaskHandlerWindow().show();
	},
	
	setPath:function(){
		var taskGrid=Ext.getCmp('allTaskGrid');
		var rs = taskGrid.getSelectionModel().getSelections();
		if(rs.length==0){
			Ext.ux.Toast.msg('操作信息','请选择任务记录!');
			return;
		}
		new PathChangeWindow({taskId:rs[0].data.taskId,taskGrid:taskGrid}).show();
	},
	handlerTask:function(){
		var taskGrid=Ext.getCmp('allTaskGrid');
	    var rs = taskGrid.getSelectionModel().getSelections();
		if(rs.length==0){
			Ext.ux.Toast.msg('操作信息','请选择任务记录!');
			return;
		}
		if(rs.length>1){
			Ext.ux.Toast.msg('操作信息','只能选择一条任务记录!');
			return;
		}
		var record=rs[0];
		var contentPanel=App.getContentPanel();
		var formView=contentPanel.getItem('ProcessNextForm'+record.data.taskId);
		if(formView==null){
			formView=new ProcessNextForm({taskId:record.data.taskId,activityName:record.data.activityName,agentTask:true});
			contentPanel.add(formView);
		}
		contentPanel.activate(formView);
	}

});
