/**
 * @author 
 * @createtime 
 * @class JxjySfbzmxForm
 * @extends Ext.Window
 * @description JxjySfbzmx表单
 * @company 宏天软件
 */
JxjySfbzmxForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				JxjySfbzmxForm.superclass.constructor.call(this, {
							id : 'JxjySfbzmxFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[JxjySfbzmx]详细信息',
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
							//id : 'JxjySfbzmxForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'jxjySfbzmx.id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							}
																																																	,{
																fieldLabel : 'xmId',
								 								name : 'jxjySfbzmx.xmId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : 'xmmc',
								 								name : 'jxjySfbzmx.xmmc'
								 																 								,maxLength: 50
								 							}
																																										,{
																fieldLabel : 'sz',
								 								name : 'jxjySfbzmx.sz'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : 'dw',
								 								name : 'jxjySfbzmx.dw'
								 																 								,maxLength: 10
								 							}
																																										,{
																fieldLabel : 'xf',
								 								name : 'jxjySfbzmx.xf'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : 'zgz',
								 								name : 'jxjySfbzmx.zgz'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : 'sfbzszid',
								 								name : 'jxjySfbzmx.sfbzszid'
								 																,xtype:'numberfield'
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.id != null && this.id != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/ryxf/getJxjySfbzmx.do?id='+ this.id,
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
				$postForm({
						formPanel:this.formPanel,
						scope:this,
						url:__ctxPath + '/ryxf/saveJxjySfbzmx.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('JxjySfbzmxGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});