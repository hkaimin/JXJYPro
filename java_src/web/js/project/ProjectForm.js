/**
 * @author 
 * @createtime 
 * @class DpBdzForm
 * @extends Ext.Window
 * @description DpBdzForm表单
 * @company 宏天软件
 */
ProjectForm = Ext.extend(Ext.Window, {
	//构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		//必须先初始化组件
		this.initUIComponents();
		ProjectForm.superclass.constructor.call(this, {
			id : 'ProjectForm',
			layout : 'fit',
			items : this.formPanel,
			modal : true,
			height : 600,
			width : 800,
			maximizable : true,
			title : '项目详细信息',
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
				name : 'dpBdz.bdzId',
				xtype : 'hidden',
				value : this.bdzId == null ? '' : this.bdzId
			}, 
			{
				name : 'dpBdz.ssdwId',
				value:this.ssdwId == null ? '' : this.ssdwId,
				xtype : 'hidden'
			}, 
			{
				fieldLabel : '项目名称<font color=red>*</font>',
				name : 'dpBdz.sbmc',
				allowBlank : false,
				maxLength : 100
			}, {
				fieldLabel : '项目专业',
				name : 'ssdw',
				value: this.orgName == null ? "" : this.orgName
			},{
				fieldLabel : '活动方式',
				xtype : 'combo',
				editable : false,
				emptyText:'请选择',
				triggerAction :'all',
				hiddenName:'rmBdz.sbbm',
				store : [
					['2','审批通过'],
					['4','数据整理未通过']
						]
			}, {
				fieldLabel : '审核方式',
				xtype : 'combo',
				editable : false,
				emptyText:'请选择',
				triggerAction :'all',
				hiddenName:'rmBdz.sbbm',
				store : [
					['2','审批通过'],
					['0','外业采集未审批'],
					['1','数据整理未审批'],
					['3','外业采集未通过'],
					['4','数据整理未通过']
						]
			}, {
				fieldLabel : '项目类别',
				xtype : 'combo',
				editable : false,
				emptyText:'请选择',
				triggerAction :'all',
				hiddenName:'rmBdz.sbbm',
				store : [
					['2','审批通过'],
					['0','外业采集未审批'],
					['1','数据整理未审批'],
					['3','外业采集未通过'],
					['4','数据整理未通过']
						]
			},{
				fieldLabel : '总学分',
				name : 'dpBdz.bz',
				xtype:'numberfield',
				maxLength : 100
			}
			, {
				fieldLabel : '总学时',
				name : 'dpBdz.sbbm',
				allowBlank : false,
				xtype:'numberfield',
				maxLength : 100
			}
			, {
				xtype : 'datefield',
				format : 'Y-m-d',
				fieldLabel : '举办时间',
				name : 'dpBdz.tyrq',
				maxLength : 25
			}
			, {
				xtype : 'datefield',
				format : 'Y-m-d',
				fieldLabel : '提交时间',
				name : 'dpBdz.tyrq',
				maxLength : 25
			}
			, {
				fieldLabel : '学分类别',
				name : 'dpBdz.sbbm',
				allowBlank : false,
				xtype:'numberfield',
				maxLength : 100
			}
			, {
				fieldLabel : '主办单位',
				name : 'dpBdz.sbbm',
				allowBlank : false,
				xtype:'textfield',
				maxLength : 100
			}
			]
		});
		
		//加载表单对应的数据	
		if (this.bdzId != null && this.bdzId != 'undefined') {
			
			this.formPanel.loadData( {
				url : __ctxPath + '/dp/getDpBdz.do?bdzId='
						+ this.bdzId,
				root : 'data',
				preName : 'dpBdz'
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
			url :  __ctxPath + '/dp/saveDpBdz.do',
			method : 'post',
			waitMsg : '正在提交数据...',
			success : function(fp, action) {
				Ext.ux.Toast.msg('操作信息', '成功信息保存！');
				
				var gridPanel = Ext.getCmp('EmBdzGrid');
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