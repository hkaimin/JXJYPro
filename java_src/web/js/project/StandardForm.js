/**
 * @author 
 * @createtime 
 * @class DpBdzForm
 * @extends Ext.Window
 * @description DpBdzForm表单
 * @company 宏天软件
 */
StandardForm = Ext.extend(Ext.Window, {
	//构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		//必须先初始化组件
		this.initUIComponents();
		StandardForm.superclass.constructor.call(this, {
			id : 'StandardForm',
			layout : 'fit',
			items : this.formPanel,
			modal : true,
			height : 600,
			width : 800,
			maximizable : true,
			title : '达标标准信息',
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
		
		this.searchPanel = new HT.SearchPanel( {
			layout : 'hbox',
			region : 'north',
			layoutConfig : {
				align : 'middle',
				padding : '5'
			},
			defaults : {
				xtype : 'label',
				border:false,
				margins:{top:0, right:4, bottom:2, left:4}
			},
			items : [ 
			 {
					text : '条件类型：'
			}, {
				name : 'dpBdz.sbmc',
				xtype : 'combo',
				editable : false,
				emptyText:'请选择',
				triggerAction :'all',
				hiddenName:'project.xmlb',
				store : [
					['1','必要条件'],
					['3','最高分限制']
						]
			} ,{
					name : 'method',
					value: 'search',
					xtype : 'hidden'
			}, {
					name : 'orgId',
					xtype : 'hidden'
			}]
		});// end of searchPanel
		
		var zcStore = new Ext.data.Store({
			id:'zcStore',
			proxy : new Ext.data.HttpProxy({
				url : __ctxPath + "/project/listZcCredit.do"
			}),
			reader : new Ext.data.ArrayReader({},[
				{name:'zcId'},
				{name:'zcm'}
			])
		});
		
		this.flage = "1";
		
		this.formPanel = new Ext.FormPanel( {
			id : 'EmBdzForm_form',
			layout : 'form',
			bodyStyle : 'padding:10px',
			region:'center',
			border : false,
			autoScroll : true,
			//id : 'RmBdzForm',
			defaults : {
				anchor : '96%,96%'
			},
			defaultType : 'textfield',
			items : [
				
			{
				name : 'biaozhun.tjlx',
				fieldLabel : '条件类型',
				xtype : 'combo',
				editable : false,
				triggerAction :'all',
				hiddenName:'biaozhun.tjlx',
				value : '1',
				store : [
					['1','达标条件'],
					['2','最高分限制']
						]
				, listeners:{
						select : function(combo, record, index) {
							var win = Ext.getCmp('StandardForm');
							if(combo.value == "1") {
//								Ext.getCmp('khnr').getEl().up('.x-form-item').setDisplayed(true);
//								Ext.getCmp('khnr').setVisible(true);
//								Ext.getCmp('khnr').setDisabled(false);
//								
//								Ext.getCmp('khnr2').getEl().up('.x-form-item').setDisplayed(false);
//								Ext.getCmp('khnr2').setVisible(false);
//								Ext.getCmp('khnr2').setDisabled(true);
//								
								win.flage = "1";
							} else if(combo.value == "2") {
////								Ext.getCmp('khnr2').hideLabel = false;
//								Ext.getCmp('khnr2').getEl().up('.x-form-item').setDisplayed(true);
//								Ext.getCmp('khnr2').setVisible(true);
//								Ext.getCmp('khnr2').setDisabled(false);
//								
//								Ext.getCmp('khnr').getEl().up('.x-form-item').setDisplayed(false);
//								Ext.getCmp('khnr').setVisible(false);
//								Ext.getCmp('khnr').setDisabled(true);
//								
//								
////								Ext.getCmp('khnr2').setVisible(true);
////								Ext.getCmp('khnr2').setVisible(true);
////								Ext.getCmp('khnr2').setDisabled(false);
//								
								win.flage = "2";
							}
						}
				}
			}, 
			{
				fieldLabel : '医院类型',
				name : 'biaozhun.yylx',
				xtype : 'combo',
				editable : false,
				emptyText:'请选择',
				triggerAction :'all',
				hiddenName:'biaozhun.yylx',
				store : [
					['1','一级及以下医院'],
					['2','二级医院'],
					['3','三级医院']
						]
			},{
				fieldLabel : '职称',
				name : 'biaozhun.zc',
				model:'remote',
				xtype : 'combo',
				editable : false,
				valueField:'zcId',
				displayField:'zcm',
				emptyText:'请选择',
				triggerAction :'all',
				hiddenName:'biaozhun.zc',
				store : zcStore
			}
//			,{
//				id:'khnr',
//				fieldLabel : '考核内容',
//				name : 'project.xmmc',
//				xtype : 'combo',
//				editable : false,
//				emptyText:'请选择',
//				triggerAction :'all',
//				hiddenName:'project.xmlb',
//				hidden : true,
//				hideLabel : true,
//				store : [
//					['1','I类学分'],
//					['2','II类学分']
//						]
//			}
			,{
				id:'khnr2',
				fieldLabel : '考核内容', 
				xtype : 'compositefield',
//				hidden : this.flage2,
//				hideLabel : this.flage2,
//				hidden : true,
//				hideLabel : true,
				items : [
					{	
						xtype : 'textfield',
						name : 'biaozhun.khnr'
					},{
						xtype : 'button',
						iconCls:'menu-flow',
						text : '选择学分类别',
						handler : this.selectLb.createCallback(this)
					}
				]
			}
//			,{
//				fieldLabel : '条件描述',
//				xtype : 'combo',
//				editable : false,
//				emptyText:'请选择',
//				triggerAction :'all',
//				hiddenName:'project.hdfs',
//				store : [
//					['2','审批通过'],
//					['4','数据整理未通过']
//						]
//			} 
			,{
				fieldLabel : '分数',
				name : 'biaozhun.zgf',
				xtype:'numberfield',
				maxLength : 100
			}, {
				name : 'biaozhun.khnrId',
				xtype:'numberfield',
				maxLength : 100
			}, {
				name : 'biaozhun.nf',
				xtype:'numberfield',
				maxLength : 100,
				value : this.nf
			}
			]
		});
		
		this.centerPanel = new Ext.Panel({
//			region:'center'
			layout : 'border',
//			tbar : this.topbar,
			items:[this.searchPanel, this.formPanel]
		});

		
	},//end of the initcomponents

	//选择学分类别
	selectLb : function(fPanel){
		var params = [];
		params.tjlb = fPanel.flage;
//		alert(params.tjlb);
		CreditSelector.getView(
			function(lbId, lbName) {
				fPanel.getCmpByName('biaozhun.khnrId').setValue(lbId);
				fPanel.getCmpByName('biaozhun.khnr').setValue(lbName);
			}
		, true, params).show();//end of selector
	},
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
	//创建记录
	createRs : function() {
		new StandardForm({
			
		}).show();
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
			url :  __ctxPath + '/project/saveBiaozhunDbbzAction.do',
			method : 'post',
			waitMsg : '正在提交数据...',
			success : function(fp, action) {
				Ext.ux.Toast.msg('操作信息', '成功信息保存！');
				
				var gridPanel = Ext.getCmp('bzGrid');
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