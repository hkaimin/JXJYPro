/**
 * @author 
 * @createtime 
 * @class DpBdzForm
 * @extends Ext.Window
 * @description DpBdzForm表单
 * @company 宏天软件
 */
ProjectForm = Ext.extend(Ext.Window, {
	//构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		//必须先初始化组件
		this.initUIComponents();
		ProjectForm.superclass.constructor.call(this, {
			id : 'ProjectForm',
			layout : 'fit',
			items : this.formPanel,
			modal : true,
			height : 400,
			width : 600,
			maximizable : true,
			title : '项目详细信息',
			buttonAlign : 'center',
			buttons : [ {
				text : '保存',
				iconCls : 'btn-save',
				scope : this,
				handler : this.save
//			}, {
//				text : '重置',
//				iconCls : 'btn-reset',
//				scope : this,
//				handler : this.reset
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
				name : 'project.xmId',
				xtype : 'hidden',
				value : this.xmId == null ? '' : this.xmId
			}, 
			{
				fieldLabel : '项目名称',
				name : 'project.mc',
				allowBlank : false,
				maxLength : 100
//			},{
//				fieldLabel : '项目编号',
//				name : 'project.xmbh',
//				allowBlank : false,
//				maxLength : 100
			},{
				fieldLabel : '项目专业',
				name : 'project.xmmc'
			},{
				fieldLabel : '活动方式',
				name : 'project.hdfs',
				xtype : 'combo',
				editable : false,
				emptyText:'请选择',
				triggerAction :'all',
//				hiddenName:'project.hdfs',
				store : [
					['1','其他活动形式'],
					['2','自学笔记'],
					['3','远程教育'],
					['4','学习班'],
					['5','研修班'],
					['6','培训班'],
					['7','学术讲座']
						]
			}, {
				fieldLabel : '审核方式',
				xtype : 'combo',
				name : 'project.shfs',
				editable : false,
				emptyText:'请选择',
				triggerAction :'all',
//				hiddenName:'project.shfs',
				store : [
					['2','考试'],
					['0','考核'],
					['4','其他']
						]
			}, {
				fieldLabel : '项目类别',
				name : 'project.xmlb',
				xtype : 'combo',
				editable : false,
				emptyText:'请选择',
				triggerAction :'all',
				hiddenName:'project.xmlb',
				store : [
					['1','自主项目'],
					['2','科室项目'],
					['3','申报导入项目']
						]
			},{
				fieldLabel : '总学分',
				name : 'project.zxf',
				xtype:'numberfield',
				maxLength : 100
			}
			, {
				fieldLabel : '总学时',
				name : 'project.zxs',
				allowBlank : false,
				xtype:'numberfield',
				maxLength : 100
			}
			, {
				xtype : 'datefield',
				format : 'Y-m-d',
				fieldLabel : '举办时间',
				name : 'project.jbsj',
				maxLength : 25
			}
			, {
				xtype : 'datefield',
				format : 'Y-m-d',
				fieldLabel : '提交时间',
				name : 'project.tjsj',
				maxLength : 25
			}
			 , {
				fieldLabel : '学分类别', 
				xtype : 'compositefield',
//				hidden : this.flage2,
//				hideLabel : this.flage2,
//				hidden : true,
//				hideLabel : true,
				items : [
					{	
						xtype : 'textfield',
						name : 'project.xflb'
					},{	
						xtype : 'textfield',
						name : 'project.xflbid',
						hidden : true
					},{
						xtype : 'button',
						iconCls:'menu-flow',
						text : '选择学分类别',
						handler : this.selectLb.createCallback(this)
					}
				]
			}
			, {
				fieldLabel : '主办单位',
				name : 'project.zbdw',
				xtype:'textfield',
				value: this.orgName == null ? "" : this.orgName
			}, {
				fieldLabel : '主办单位id',
				name : 'project.zbbwid',
				xtype:'textfield',
				hidden:true,
				hideLabel : true,
				value: this.ssdwId == null ? "" : this.ssdwId
			}
			]
		});
		
		//加载表单对应的数据	
		if (this.xmId != null && this.xmId != 'undefined') {
			
			this.formPanel.loadData( {
				url : __ctxPath + '/project/getProject.do?xmId='
						+ this.xmId,
				root : 'data',
				preName : 'project'
			});
			

		}

	},//end of the initcomponents
	//选择学分类别
	selectLb : function(fPanel){
		var params = [];
		params.tjlb = "2";
//		alert(params.tjlb);
		CreditSelector.getView(
			function(lbId, lbName) {
				fPanel.getCmpByName('project.xflbid').setValue(lbId);
				fPanel.getCmpByName('project.xflb').setValue(lbName);
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
				
				var gridPanel = Ext.getCmp('ProjectGrid1');
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