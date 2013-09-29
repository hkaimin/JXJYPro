/**
 * @description TaskSign表单
 * @class TaskSignForm
 * @extends Ext.Window
 * @author YHZ
 * @company 宏天软件
 * @createtime 2011-1-5PM,2011-2-15PM[修改]
 */
TaskSignForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		TaskSignForm.superclass.constructor.call(this, {
			id : 'TaskSignFormWin',
			layout : 'fit',
			iconCls : 'menu-taskSign',
			items : this.formPanel,
			modal : true,
			height : 250,
			minHeight : 250,
			width : 500,
			minWidth : 500,
			maximizable : true,
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.save,
				scope : this
			},
			title : '查看/编辑任务[' + this.activityName + ']会签信息',
			buttonAlign : 'center',
			buttons : [ {
				text : '保存',
				iconCls : 'btn-save',
				scope : this,
				handler : this.save
			}, {
				text : '取消',
				iconCls : 'btn-cancel',
				scope : this,
				handler : this.cancel
			} ]
		});
	},// end of the constructor

	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel( {
			id : 'taskSignFormPanel',
			layout : 'form',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			// id : 'TaskSignForm',
			defaults : {
				anchor : '96%,96%'
			},
			defaultType : 'textfield',
			items : [
					{
						name : 'taskSign.signId',
						xtype : 'hidden',
						value : this.signId == null ? '' : this.signId
					},
					{
						name : 'assignId',
						xtype : 'hidden',
						value : this.assignId == null ? '' : this.assignId
					},
					{
						fieldLabel : '任务名称',
						name : 'taskSign.assignName',
						allowBlank : false,
						xtype : 'textfield',
						readOnly : true,
						value : this.activityName == null ? ''
								: this.activityName
					},
					{
						xtype : 'radiogroup',
						fieldLabel : '票数类型',
						items : [ {
							boxLabel : '绝对票数',
							name : 'taskSignType',
							inputValue : 1,
							checked : true
						}, {
							boxLabel : '百分比票数',
							name : 'taskSignType',
							inputValue : 2
						} ],
						listeners : {
							'change' : function(obj, newV, oldV) {
								var fm = Ext.getCmp('taskSignFormPanel');
								var isTrue = fm.getCmpByName('taskSignType').getValue();
								var count = fm.getCmpByName('taskSign.voteCounts');
								var percent = fm.getCmpByName('taskSign.votePercents');
								if (isTrue) {
									count.enable();
									percent.disable();
								} else {
									count.disable();
									percent.enable();
								}
							}
						}
					}, {
						fieldLabel : '绝对票数',
						name : 'taskSign.voteCounts',
						xtype : 'numberfield',
						maxLength : 11,
						minValue : 0,
						//regex : /^\d*$/,
						regexText : '绝对票数只能输入数字！'
					}, {
						fieldLabel : '百分比票数',
						name : 'taskSign.votePercents',
						xtype : 'numberfield',
						minValue : 0,
						maxValue : 100,
						maxLength : 11,
						disabled : true
					}, {
						fieldLabel : '决策方式',
						hiddenName : 'taskSign.decideType',
						allowBlank : false,
						valueField : 'id',
						displayField : 'name',
						xtype : 'combo',
						store : [ [ '2', '拒绝' ], [ '1', '通过' ] ],
						emptyText : '--请选择决策方式--',
						triggerAction : 'all',
						editable : false,
						width : ' 96%'
					} ]
		});
		// 加载表单对应的数据
		if (this.assignId != null && this.assignId != 'undefined') {
			this.formPanel.loadData( {
				url : __ctxPath + '/flow/findTaskSign.do?assignId=' + this.assignId,
				root : 'data',
				preName : 'taskSign'
			});
		}

	},// end of the initcomponents

	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function() {
		this.close();
	},

	/**
	 * 保存记录
	 */
	save : function() {
		$postForm( {
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath + '/flow/saveTaskSign.do',
			callback : function(fp, action) {
				var gridPanel = Ext.getCmp('TaskSignGrid');
				if (gridPanel != null)
					gridPanel.getStore().reload();
				this.close();
			}
		});
	}// end of save

});