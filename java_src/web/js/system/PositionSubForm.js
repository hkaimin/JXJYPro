/**
 * @author 
 * @createtime 
 * @class PositionSubForm
 * @extends Ext.Window
 * @description PositionSub表单
 * @company 宏天软件
 */
PositionSubForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				PositionSubForm.superclass.constructor.call(this, {
							id : 'PositionSubFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[PositionSub]详细信息',
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
							//id : 'PositionSubForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'positionSub.mainpositionid',
								xtype : 'hidden',
								value : this.mainpositionid == null ? '' : this.mainpositionid
							}
																																																								]
						});
				//加载表单对应的数据	
				if (this.mainpositionid != null && this.mainpositionid != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/system/getPositionSub.do?mainpositionid='+ this.mainpositionid,
								root : 'data',
								preName : 'positionSub'
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
						url:__ctxPath + '/system/savePositionSub.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('PositionSubGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});