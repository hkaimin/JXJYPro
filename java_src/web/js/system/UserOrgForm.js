/**
 * @author 
 * @createtime 
 * @class UserOrgForm
 * @extends Ext.Window
 * @description UserOrg表单
 * @company 广州粤能信息技术有限公司
 */
UserOrgForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				UserOrgForm.superclass.constructor.call(this, {
							id : 'UserOrgFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[UserOrg]详细信息',
							buttonAlign : 'center',
							buttons : [
										{
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
										}
							         ]
				});
			},//end of the constructor
			//初始化组件
			initUIComponents : function() {
				this.formPanel = new Ext.FormPanel({
							layout : 'form',
							bodyStyle : 'padding:10px',
							border : false,
							autoScroll:true,
							//id : 'UserOrgForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'userOrg.userOrgId',
								xtype : 'hidden',
								value : this.userOrgId == null ? '' : this.userOrgId
							}
																																																	,{
																fieldLabel : 'userid',
								 								name : 'userOrg.userid'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : 'orgId',
								 								name : 'userOrg.orgId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : 'isPrimary',
								 								name : 'userOrg.isPrimary'
																,allowBlank:false
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : 'isCharge',
								 								name : 'userOrg.isCharge'
								 																,xtype:'numberfield'
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.userOrgId != null && this.userOrgId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/system/getUserOrg.do?userOrgId='+ this.userOrgId,
								root : 'data',
								preName : 'userOrg'
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
				$postForm({
						formPanel:this.formPanel,
						scope:this,
						url:__ctxPath + '/system/saveUserOrg.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('UserOrgGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});