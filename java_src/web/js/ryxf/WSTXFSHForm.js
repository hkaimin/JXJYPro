/**
 * @author huangkaimin
 * @createtime
 *
 */
WSTXFSHForm = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		WSTXFSHForm.superclass.constructor.call(this, {
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					id : 'WSTXFSHFormWin',
					title : '审核情况',
					iconCls : 'menu-role',
					width : 400,
					height : 200,
					buttonAlign : 'center',
					buttons : this.buttons
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {

		this.formPanel = new Ext.FormPanel({
					url : __ctxPath + '/ryxf/commitXFJxjyRyxfgl.do',
					layout : 'form',
					id : 'WSTXFSHForm',
					border:false,
					bodyStyle : 'padding:35px;',
					defaults : {
						anchor : '96%,96%'
					},
					formId : 'WSTXFSHFormId',
					defaultType : 'textfield',
					items : [{
								id:'ids',
						        name : 'ids',
								xtype : 'hidden',
								value : this.ids == null ? '' : this.ids
							}, {
	                    fieldLabel : '审核情况',
	                    id:'shzt',
						xtype : 'combo',
                        hiddenName:'shzt',
						mode : 'local',
						editable : false,
						triggerAction : 'all',
						store : new Ext.data.SimpleStore({
							      data : [
							    	  ['0','审核通过'],['1','审核不通过']
							    	  ],
                                    autoLoad:true,
									fields :['id', 'value']

								}),
						displayField : 'value',
						valueField : 'id',
						value:'0'
							}
							]
				});

		this.buttons = [{
			text : '确定审核',
			iconCls : 'btn-save',
			scope:this,
			handler : this.save.createCallback(this)
		}, {
			text : '取消',
			iconCls : 'btn-cancel',
			scope:this,
			handler : function() {
					this.close();
			}
		}]
	},
	save:function(){

		var ids=Ext.getCmp('ids').getValue();
		var shzt=Ext.getCmp('shzt').getValue();
        
        	Ext.Ajax.request( {
			url : __ctxPath + '/ryxf/wstSHXFJxjyRyxfgl.do',
							params : {
			                      ids:ids,
			                      shzt:shzt
							},
			method : 'post',
			success : function(response) {
				if(shzt=='0'){
					Ext.ux.Toast.msg("信息", "学分审核通过，已提交至人社局审核！");
				}else{
					Ext.ux.Toast.msg("信息", "学分审核不通过！");
				}
				
			    Ext.getCmp('JxjyWSTRyxfglGrid').getStore().reload();
			    Ext.getCmp('WSTXFSHFormWin').close();

			},
			failure : function() {
			}
		});

	}//end of save
});