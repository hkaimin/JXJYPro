/**
 * @author 
 * @createtime 
 * @class JxjySfbzszForm
 * @extends Ext.Window
 * @description JxjySfbzsz表单
 * @company 宏天软件
 */
JxjySfbzszForm = Ext.extend(Ext.Window, {
	//构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		//必须先初始化组件
		this.initUIComponents();
		JxjySfbzszForm.superclass.constructor.call(this, {
			id : 'JxjySfbzszFormWin',
			layout : 'fit',
			items : this.formPanel,
			modal : true,
			height : 400,
			width : 500,
			maximizable : true,
			title : '[JxjySfbzsz]详细信息',
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
			//id : 'JxjySfbzszForm',
			defaults : {
				anchor : '96%,96%'
			},
			defaultType : 'textfield',
			items : [ {
				name : 'jxjySfbzsz.id',
				xtype : 'hidden',
				value : this.id == null ? '' : this.id
			}, {
				fieldLabel : '项目名称'+"<font color='red'>*</font>",
				name : 'jxjySfbzsz.xmmc',
                allowBlank : false,
				maxLength : 100
			},{
	                    fieldLabel : '学分类别',
						xtype : 'combo',
                        hiddenName:'jxjySfbzsz.xflbid',
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
			{
				fieldLabel : '描述',
				name : 'jxjySfbzsz.mx',
				xtype : 'textarea',
				maxLength : 100
			} ]
		});
		//加载表单对应的数据	
		if (this.id != null && this.id != 'undefined') {
			this.formPanel.loadData( {
				url : __ctxPath + '/ryxf/getJxjySfbzsz.do?id=' + this.id,
				root : 'data',
				preName : 'jxjySfbzsz'
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
			url : __ctxPath + '/ryxf/saveJxjySfbzsz.do',
			callback : function(fp, action) {
				var gridPanel = Ext.getCmp('JxjySfbzszGrid');
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}
				this.close();
			}
		});
	}//end of save

});