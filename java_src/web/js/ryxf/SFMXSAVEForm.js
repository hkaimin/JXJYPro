/**
 * @author 
 * @createtime 
 * @class SFMXSAVEForm
 * @extends Ext.Window
 * @description JxjyRyxfgl表单
 * @company 宏天软件
 */
SFMXSAVEForm = Ext.extend(Ext.Window, {
	//构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		//必须先初始化组件
		this.initUIComponents(); 
		SFMXSAVEForm.superclass.constructor.call(this, {
			id : 'SFMXSAVEFormWin',
			layout : 'fit',
			items : this.formPanel,
			modal : true,
			height : 400,
			width : 500,
			maximizable : true,
			title : '授分标准明细操作',
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
			//id : 'SFMXSAVEForm',
			defaults : {
				anchor : '96%,96%'
			},
			defaultType : 'textfield',
			items : [ {
				name : 'jxjySfbzmx.id',
				xtype : 'hidden',
				value : this.id == null ? '' : this.id
			},{
				name : 'jxjySfbzmx.sfbzszid',
				xtype : 'hidden',
				value : this.szid == null ? '' : this.szid
			},{
				fieldLabel : '项目名称',
				name : 'jxjySfbzmx.xmmc',
				readOnly : true,
				//xtype : 'hidden',
				value : this.xmmc == null ? '' : this.xmmc
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
						fieldLabel : '值',
						name : 'jxjySfbzmx.sz',
						maxLength : 20
					},
					{
						fieldLabel : '值单位',
						name : 'jxjySfbzmx.dw',
						maxLength : 20
					},
					{
						fieldLabel : '学分',
						name : 'jxjySfbzmx.xf',
						maxLength : 20
					},
					{
						fieldLabel : '最高值',
						name : 'jxjySfbzmx.zgz',
						maxLength : 20
					}

					]
		});
		//加载表单对应的数据	
		if (this.id != null && this.id != 'undefined') {
			this.formPanel.loadData( {
				url : __ctxPath + '/ryxf/getJxjySfbzmx.do?id=' + this.id,
				root : 'data',
				preName : 'jxjySfbzmx'
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
			url : __ctxPath + '/ryxf/saveJxjySfbzmx.do',
			callback : function(fp, action) {
				var gridPanel = Ext.getCmp('SFMXGrid');
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}
				this.close();
			}
		});
	}//end of save

});