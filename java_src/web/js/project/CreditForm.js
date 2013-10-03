/**
 * @author 
 * @createtime 
 * @class DpBdzForm
 * @extends Ext.Window
 * @description DpBdzForm表单
 * @company 宏天软件
 */
CreditForm = Ext.extend(Ext.Window, {
	//构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		//必须先初始化组件
		this.initUIComponents();
		CreditForm.superclass.constructor.call(this, {
			id : 'CreditForm',
			layout : 'fit',
			items : this.formPanel,
			modal : true,
			height : 300,
			width : 400,
			maximizable : true,
			title : '添加学分类别信息',
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
			id : 'EmBdzForm_form',
			layout : 'form',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			//id : 'RmBdzForm',
			defaults : {
				anchor : '96%,96%'
			},
			defaultType : 'textfield',
			items : [ {
				name : 'credit.xflbid',
				xtype : 'hidden',
				value : this.xflbid == null ? '' : this.xflbid
			},{
				fieldLabel : '学分名称',
				name : 'credit.mc'
			},{
				fieldLabel : '审核方式',
				name : 'credit.sfsh',
				xtype : 'combo',
				editable : false,
				emptyText:'请选择',
				hiddenName : 'credit.sfsh',
				triggerAction :'all',
				store : [
					['0','审核学分'],
					['1','审核项目'],
					['2','审核项目及学分'],
					['3','无需审核']
						]
			},{
				fieldLabel : '学分类别',
				name : 'credit.xfbz',
				xtype : 'combo',
				editable : false,
				emptyText:'请选择',
				hiddenName : 'credit.xfbz',
				triggerAction :'all',
				store : [
					['0','I类学分'],
					['1','IIl类学分']
						]
			}
			]
		});
		
		//加载表单对应的数据	
		if (this.bdzId != null && this.bdzId != 'undefined') {
			
			this.formPanel.loadData( {
				url : __ctxPath + '/dp/getDpBdz.do?bdzId='
						+ this.bdzId,
				root : 'data',
				preName : 'credit'
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
//		$postForm( {
//			formPanel : this.formPanel,
//			scope : this,
//			url : __ctxPath + '/dataManagement/saveRmBdz.do',
//			callback : function(fp, action) {
//				var gridPanel = Ext.getCmp('RmBdzGrid');
//				if (gridPanel != null) {
//					gridPanel.getStore().reload();
//				}
//				this.close();
//			}
//		});
		
		this.formPanel.getForm().submit({
			scope:this,
			url :  __ctxPath + '/project/saveCredit.do',
			method : 'post',
			waitMsg : '正在提交数据...',
			success : function(fp, action) {
				Ext.ux.Toast.msg('操作信息', '成功信息保存！');
				
				var gridPanel = Ext.getCmp('creditGrid');
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}
				this.close();
			},
			failure : function(fp, action) {
				var msg ;
				if(action.result.message != null){
					msg = action.result.message;
				}else{
					msg = "信息保存出错，请联系管理员！";
				}
				
				Ext.MessageBox.show({
							title : '操作信息',
							msg : msg,
							buttons : Ext.MessageBox.OK,
							icon : 'ext-mb-error'
						});
			}
		});
		
	}//end of save

});