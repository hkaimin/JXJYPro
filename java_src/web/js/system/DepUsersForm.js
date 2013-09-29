/**
 * @description DepUsers表单
 * @class DepUsersForm
 * @extends Ext.Window
 * @author 宏天软件
 * @company www.jee-soft.cn
 * @createtime 2011-1-14PM
 */
DepUsersForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		DepUsersForm.superclass.constructor.call(this, {
			id : 'DepUsersFormWin',
			iconCls : 'menu-department',
			layout : 'fit',
			items : this.formPanel,
			modal : true,
			height : 180,
			minHeight : 180,
			width : 500,
			minWidth : 500,
			maximizable : true,
			title : this.depName == null ? '新增/编辑部门员工信息' : '新增/编辑部门['+this.depName+']员工信息',
			buttonAlign : 'center',
			buttons : [ {
				text : '保存',
				iconCls : 'btn-save',
				scope : this,
				handler : this.save
			}, {
				text : '取消',
				iconCls : 'btn-cancel',
				scope : this,
				handler : this.cancel
			}],
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.save,
				scope : this
			}
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel( {
			id : 'DepUsersForm',
			layout : 'form',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			defaults : {
				anchor : '96%,96%'
			},
			defaultType : 'textfield',
			items : [ {
				name : 'depUsers.depUserId',
				xtype : 'hidden',
				value : this.depUserId == null ? '' : this.depUserId
			}, {
				name : 'depUsers.appUser.userId',
				xtype : 'hidden'
			}, {
				xtype : 'container',
				layout : 'column',
				fieldLabel : '员工名称',
				style : 'margin-bottom:4px;',
				defaultType : 'label',
				items : [{
					columnWidth : .99,
					xtype : 'textfield',
					name : 'depUsers.appUser.username',
					readOnly : true,
					allowBlank : false,
					maxLength : 128
				},{
					xtype : 'button',
					iconCls : 'btn-user-sel',
					text : '请选择',
					handler : function(){
						UserSelector.getView(function(userIds,usernames){
							var fm = Ext.getCmp('DepUsersForm');
							fm.getCmpByName('depUsers.appUser.userId').setValue(userIds);
							fm.getCmpByName('depUsers.appUser.username').setValue(usernames);
						},true).show();
					},
					disabled : this.depUserId == null ? false : true
				}]
			}, {
				name : 'depUsers.department.depId',
				xtype : 'hidden',
				value : this.depId == null ? '' : this.depId
			}, {
				xtype : 'container',
				layout : 'column',
				fieldLabel : '员工部门',
				style : 'margin-bottom:2px;',
				defaultType : 'label',
				items : [{
					columnWidth : .99,
					xtype : 'textfield',
					name : 'depUsers.department.depName',
					readOnly : true,
					allowBlank : false,
					maxLength : 128,
					value : this.depName == null ? '' : this.depName
				},{
					xtype : 'button',
					iconCls : 'btn-department-sel',
					text : '请选择',
					handler : function(){
						DepSelector.getView(function(depIds,depNames){
							var fm = Ext.getCmp('DepUsersForm');
							fm.getCmpByName('depUsers.department.depId').setValue(depIds);
							fm.getCmpByName('depUsers.department.depName').setValue(depNames);
						}).show();
					}
				}]
			}, {
				fieldLabel : '主部门(是/否)',
				hiddenName : 'depUsers.isMain',
				xtype : 'combo',
				triggerAction : 'all',
				editable : false,
				mode : 'local',
				store : [['0','否'],['1','是']],
				value : 0,
				allowBlank : false,
				anchor : '99%'
			}, {
				name : 'depUsers.sn',
				xtype : 'hidden'
			} ]
		});
		// 加载表单对应的数据
		if (this.depUserId != null && this.depUserId != 'undefined') {
			this.formPanel.loadData( {
				url : __ctxPath + '/system/getDepUsers.do?depUserId='
						+ this.depUserId,
				root : 'data',
				preName : 'depUsers'
			});
		}

	},// end of the initcomponents

	/**
	 * 取消
	 */
	cancel : function() {
		this.close();
	},
	/**
	 * 保存记录
	 */
	save : function() {
		var fm = Ext.getCmp('DepUsersForm');
		if(fm.getForm().isValid()){
			fm.getForm().submit({
				url : __ctxPath + '/system/saveDepUsers.do',
				waitMsg : '数据正在提交，请稍后...',
				method : 'post',
				success : function(){
					Ext.getCmp('DepUsersView').getStore().reload();
					Ext.ux.Toast.msg('操作提示','数据操作成功！');
					Ext.getCmp('DepUsersFormWin').close();
				},
				failure : function(fp,action){
					var res = Ext.util.JSON.decode(action.response.responseText);
					Ext.ux.Toast.msg('操作提示',res.msg);
				}
			});
		}
	}// end of save

});