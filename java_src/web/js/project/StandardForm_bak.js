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
			items : this.centerPanel,
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
					['2','充分条件'],
					['3','最高分限制'],
					['4','替换条件']
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
		
		this.gridTopbar = new Ext.Toolbar( {
			buttonAlign : 'center',
			items : [ 
			{
				text : '所选学分类别'
			}, {
				name : 'dpBdz.sbmc',
				xtype : 'combo',
				editable : false,
				emptyText:'请选择',
				triggerAction :'all',
				hiddenName:'project.xmlb',
				store : [
					['1','>='],
					['2','<=']
						]
			}, {
				name : 'dpBdz.sbmc',
				xtype : 'textfield'				
			}, {
				text : '分'
			}]
		});
		
		this.gridPanel = new HT.GridPanel( {
			tbar : this.gridTopbar,
			title : '学分类别',
			//使用RowActions
			rowActions : false,
			id : 'creditGrid',
			url : __ctxPath + "/project/listCredit.do",
			fields : [ 'xflbid', 'id', 'mc', 'xfmc', 'xfmcid', 'sfsh', 'xfbz'],
			columns : [ {
				header : '学分名称',
				dataIndex : 'mc',
				renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var xflbid = record.data.xflbid;
						if(xflbid != null && xflbid != "") {
							return "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" + value;
						} else {
							return value;
						}
					}
			}]
		//end of columns
		});
		
		this.zcGridPanel = new HT.GridPanel( {
			title : '职称范围',
			//使用RowActions
			rowActions : false,
			id : 'zcGrid',
			url : __ctxPath + "/project/listZcCredit.do",
			fields : [ 'zcId', 'zcm', 'fqZcId', 'sd'],
			columns : [ {
				header : '职称名',
				dataIndex : 'zcm',
				renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var sd = record.data.sd;
						var str = "";
						for(var i=0 ; i<sd ; i++) {
							str += "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp";
						}
						return str + value;
					}
			}]
		//end of columns
		});
		
		this.yyGridPanel = new HT.GridPanel( {
			title : '医院类型',
			//使用RowActions
			rowActions : false,
			id : 'yyGrid',
			url : __ctxPath + "/project/listYyCredit.do",
			fields : [ 'typeId', 'mc'],
			columns : [ {
				header : '类型名称',
				dataIndex : 'mc'
			}]
		//end of columns
		});
		
		this.centerTabPanel = new Ext.TabPanel({
			id:'tabPanel',
			region:'center',
//			tbar : this.topbar,
			activeTab: 0,
			items:[this.gridPanel, this.yyGridPanel, this.zcGridPanel]
		});
		
		this.centerPanel = new Ext.Panel({
//			region:'center'
			layout : 'border',
//			tbar : this.topbar,
			items:[this.searchPanel, this.centerTabPanel]
		});

		
		this.gridPanel.getSelectionModel().addListener('beforerowselect', this.gridSelect);
		
	},//end of the initcomponents

	//列表选择事件
	gridSelect : function(model, rowIndex, flage, record) {
//		alert(Ext.encode(record.data));
		var xflbid = record.data.xflbid;
		if(xflbid == null || xflbid == "") {
			Ext.ux.Toast.msg("提示信息", "具体学分类别不用选择");
			return false;
		}
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
			url :  __ctxPath + '/project/saveProject.do',
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