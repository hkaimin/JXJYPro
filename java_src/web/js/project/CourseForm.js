/**
 * @author 
 * @createtime 
 * @class DpBdzForm
 * @extends Ext.Window
 * @description DpBdzForm表单
 * @company 宏天软件
 */
CourseForm = Ext.extend(Ext.Window, {
	//构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		//必须先初始化组件
		this.initUIComponents();
		CourseForm.superclass.constructor.call(this, {
			id : 'CourseForm',
			layout : 'fit',
			items : this.formPanel,
			modal : true,
			height : 600,
			width : 800,
			maximizable : true,
			title : '课程详细信息',
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
				name : 'course.ktId',
//				xtype : 'hidden',
				value : this.ktId == null ? '' : this.ktId
			},{
				fieldLabel : '项目id',
				name : 'course.xmId',
				xtype:'textfield',
				value: this.xmId == null ? '' : this.xmId
			},{
				fieldLabel : '课题名称',
				name : 'course.ktmc',
				allowBlank : false,
				maxLength : 100
			},{
				fieldLabel : '讲师名称',
				name : 'course.jsmc',
				allowBlank : false,
				maxLength : 100
			},{
				fieldLabel : '讲师职称',
				name : 'course.jszc'
			},{
				fieldLabel : '学分',
				name : 'course.xf',
				xtype:'numberfield',
				maxLength : 100
			}, {
				fieldLabel : '学时',
				name : 'course.xs',
//				allowBlank : false,
				xtype:'numberfield',
				maxLength : 100
			},{
				fieldLabel : '上课地点',
				name : 'course.skdd'
			}, {
				xtype : 'datefield',
				format : 'Y-m-d',
				fieldLabel : '授课时间',
				name : 'course.sksj'
			}
			]
		});
		
		//加载表单对应的数据	
		if (this.bdzId != null && this.bdzId != 'undefined') {
			
			this.formPanel.loadData( {
				url : __ctxPath + '/dp/getDpBdz.do?bdzId='
						+ this.bdzId,
				root : 'data',
				preName : 'course'
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
			url :  __ctxPath + '/project/saveCourse.do',
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