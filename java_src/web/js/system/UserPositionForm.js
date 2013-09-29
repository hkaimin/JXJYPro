/**
 * @author 
 * @createtime 
 * @class UserPositionForm
 * @extends Ext.Window
 * @description UserPosition表单
 * @company 宏天软件
 */
UserPositionForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				UserPositionForm.superclass.constructor.call(this, {
							id : 'UserPositionFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[UserPosition]详细信息',
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
							//id : 'UserPositionForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'userPosition.userPosId',
								xtype : 'hidden',
								value : this.userPosId == null ? '' : this.userPosId
							}
																																																	,{
																fieldLabel : 'posId',
								 								name : 'userPosition.posId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : 'userid',
								 								name : 'userPosition.userid'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : 'isprimary',
								 								name : 'userPosition.isprimary'
								 																,xtype:'numberfield'
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.userPosId != null && this.userPosId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/system/getUserPosition.do?userPosId='+ this.userPosId,
								root : 'data',
								preName : 'userPosition'
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
						url:__ctxPath + '/system/saveUserPosition.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('UserPositionGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});