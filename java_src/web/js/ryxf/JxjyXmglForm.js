/**
 * @author 
 * @createtime 
 * @class JxjyXmglForm
 * @extends Ext.Window
 * @description JxjyXmgl表单
 * @company 宏天软件
 */
JxjyXmglForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				JxjyXmglForm.superclass.constructor.call(this, {
							id : 'JxjyXmglFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[JxjyXmgl]详细信息',
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
							//id : 'JxjyXmglForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'jxjyXmgl.xmId',
								xtype : 'hidden',
								value : this.xmId == null ? '' : this.xmId
							}
																																																	,{
																fieldLabel : 'xflbid',
								 								name : 'jxjyXmgl.xflbid'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : 'mc',
								 								name : 'jxjyXmgl.mc'
								 																 								,maxLength: 50
								 							}
																																										,{
																fieldLabel : 'xmmc',
								 								name : 'jxjyXmgl.xmmc'
								 																 								,maxLength: 20
								 							}
																																										,{
																fieldLabel : 'hdfs',
								 								name : 'jxjyXmgl.hdfs'
								 																 								,maxLength: 20
								 							}
																																										,{
																fieldLabel : 'shfs',
								 								name : 'jxjyXmgl.shfs'
								 																 								,maxLength: 20
								 							}
																																										,{
																fieldLabel : 'xmlb',
								 								name : 'jxjyXmgl.xmlb'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : 'zxf',
								 								name : 'jxjyXmgl.zxf'
								 																 								,maxLength: 10
								 							}
																																										,{
																fieldLabel : 'zxs',
								 								name : 'jxjyXmgl.zxs'
								 																 								,maxLength: 10
								 							}
																																										,{
																fieldLabel : 'jbsj',
								 								name : 'jxjyXmgl.jbsj'
								 																 								,maxLength: 20
								 							}
																																										,{
																fieldLabel : 'tjsj',
								 								name : 'jxjyXmgl.tjsj'
								 																 								,maxLength: 20
								 							}
																																										,{
																fieldLabel : 'xflb',
								 								name : 'jxjyXmgl.xflb'
								 																 								,maxLength: 20
								 							}
																																										,{
																fieldLabel : 'zbdw',
								 								name : 'jxjyXmgl.zbdw'
								 																 								,maxLength: 20
								 							}
																																										,{
																fieldLabel : 'zt',
								 								name : 'jxjyXmgl.zt'
								 																 								,maxLength: 10
								 							}
																																										,{
																fieldLabel : 'zbbwid',
								 								name : 'jxjyXmgl.zbbwid'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : 'yysh',
								 								name : 'jxjyXmgl.yysh'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : 'xmbh',
								 								name : 'jxjyXmgl.xmbh'
								 																,xtype:'numberfield'
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.xmId != null && this.xmId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/ryxf/getJxjyXmgl.do?xmId='+ this.xmId,
								root : 'data',
								preName : 'jxjyXmgl'
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
						url:__ctxPath + '/ryxf/saveJxjyXmgl.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('JxjyXmglGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});