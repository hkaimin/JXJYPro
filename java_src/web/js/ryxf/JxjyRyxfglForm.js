/**
 * @author 
 * @createtime 
 * @class JxjyRyxfglForm
 * @extends Ext.Window
 * @description JxjyRyxfgl表单
 * @company 宏天软件
 */
JxjyRyxfglForm = Ext.extend(Ext.Window, {
	//构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		//必须先初始化组件
		this.initUIComponents();
		JxjyRyxfglForm.superclass.constructor.call(this, {
			id : 'JxjyRyxfglFormWin',
			layout : 'fit',
			items : this.formPanel,
			modal : true,
			height : 400,
			width : 500,
			maximizable : true,
			title : '[JxjyRyxfgl]详细信息',
			buttonAlign : 'center',
			buttons : [ {
				text : '保存',
				iconCls : 'btn-save',
				scope : this,
				handler : this.save
			}, {
				text : '重置',
				iconCls : 'btn-reset',
				scope : this,
				handler : this.reset
			}, {
				text : '取消',
				iconCls : 'btn-cancel',
				scope : this,
				handler : this.cancel
			} ]
		});
	},//end of the constructor
	//初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel( {
			layout : 'form',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			//id : 'JxjyRyxfglForm',
			defaults : {
				anchor : '96%,96%'
			},
			defaultType : 'textfield',
			items : [ {
				name : 'jxjyRyxfgl.id',
				xtype : 'hidden',
				value : this.id == null ? '' : this.id
			},
			//			{
					//				fieldLabel : '人员编号',
					//				name : 'jxjyRyxfgl.rybh',
					//				allowBlank : false,
					//				xtype : 'numberfield'
					//			},
					//			{
					//				fieldLabel : '课题ID',
					//				name : 'jxjyRyxfgl.ktId',
					//				xtype : 'numberfield'
					//			},
//					{
//						fieldLabel : '姓名',
//						name : 'jxjyRyxfgl.xm',
//						maxLength : 10
//					},
					{
						fieldLabel : '日期',
						name : 'jxjyRyxfgl.rq',
						xtype : 'datefield',
						format : 'Y-m-d',
						maxLength : 20
					}, {
						fieldLabel : '课题',
						name : 'jxjyRyxfgl.kt',
						maxLength : 20
					}, {
						name : 'jxjyRyxfgl.xflx',
						xtype : 'hidden',
						value : '2',
						maxLength : 10
					}, {
						fieldLabel : '学分类型',
						value : '非项目学分',
						readOnly:true,
						name : 'xflxvo',
						maxLength : 10
					},{
	                    fieldLabel : '学分类别',
						xtype : 'combo',
                        hiddenName:'jxjyRyxfgl.xflb',
						mode : 'local',
						maxLength : 10,
						editable : false,
						triggerAction : 'all',
						store : new Ext.data.SimpleStore({
									autoLoad : true,
									url : __ctxPath
											+ '/ryxf/getXflbJxjyRyxfgl.do',
									fields : ['id', 'xflbmc']
								}),
						displayField : 'xflbmc',
						valueField : 'id',
						hiddenValue:'1',
						value:'国家级'
							},
//							{
//						fieldLabel : '学分类别',
//						name : 'jxjyRyxfgl.xflb',
//						maxLength : 10
//					},
//					{
//						fieldLabel : '学科',
//						name : 'jxjyRyxfgl.xk',
//						maxLength : 20
//					}, 
					{
						fieldLabel : '活动形式',
						name : 'jxjyRyxfgl.hdxs',
						maxLength : 10
					}, {
						fieldLabel : '学分',
						name : 'jxjyRyxfgl.xf',
						maxLength : 10
					}, {
						fieldLabel : '学时',
						name : 'jxjyRyxfgl.xs',
						maxLength : 10
					}, {
						fieldLabel : '授分单位',
						name : 'jxjyRyxfgl.sfdw',
						maxLength : 20
					}, {
						fieldLabel : '卫生厅审核状态',
						name : 'jxjyRyxfgl.shzt',
						maxLength : 10
					}, {
						fieldLabel : '备注',
						name : 'jxjyRyxfgl.bz',
						maxLength : 100
					}, {
						fieldLabel : '人社局审核状态',
						name : 'jxjyRyxfgl.rsjsh',
						maxLength : 10
					}, {
						fieldLabel : '职称',
						name : 'jxjyRyxfgl.zc',
						maxLength : 20
					}, {
						fieldLabel : '学位',
						name : 'jxjyRyxfgl.xw',
						maxLength : 20
					} ]
		});
		//加载表单对应的数据	
		if (this.id != null && this.id != 'undefined') {
			this.formPanel.loadData( {
				url : __ctxPath + '/ryxf/getJxjyRyxfgl.do?id=' + this.id,
				root : 'data',
				preName : 'jxjyRyxfgl'
			});
		}

	},//end of the initcomponents

	/**
	 * 重置
	 * @param {} formPanel
	 */
	reset : function() {
		this.formPanel.getForm().reset();
	},
	/**
	 * 取消
	 * @param {} window
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
			url : __ctxPath + '/ryxf/saveJxjyRyxfgl.do',
			callback : function(fp, action) {
				var gridPanel = Ext.getCmp('JxjyRyxfglGrid');
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}
				this.close();
			}
		});
	}//end of save

});