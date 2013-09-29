/**
 * 
 * @class UserFormPanel
 * @extends Ext.FormPanel
 * @description 用于窗口的信息编辑管理
 */
UserFormPanel=Ext.extend(Ext.FormPanel,{
	constructor:function(conf){
		Ext.applyIf(this,conf);
		this.userId=this.userId?this.userId:'';
		this.initUI();
		UserFormPanel.superclass.constructor.call(this,{
			id:'UserFormPanel_'+this.userId,
			title:(this.username?this.username:'')+'员工信息编辑',
			autoScroll:true,
			width:1000,
			tbar:[
			{
				text:'保存',
				iconCls:'btn-save',
				scope:this,
				handler:this.save
			},
			''
			
			],
			layout:'form',
			items:[
				this.basePanel,
				this.posPanel,
				this.depPanel,
				this.rolePanel
			]
		});
		
		if(this.userId!=''){
			this.requiredPanel.loadData({
				url:__ctxPath+'/system/loadAppUser.do?userId='+this.userId,
				root:'data',
				preName:'appUser'
			});
			
			this.getTopToolbar().insert(2,{
				text:'重设密码',
				iconCls:'',
				scope:this,
				handler:function(){
					new setPasswordForm(this.userId);
				}
			});
		}
	},
	//==================functions start===============
	/**
	 * 上传图片
	 */
	uploadPhoto:function(){
			var dialog = App.createUploadDialog({
				file_cat : 'system/appUser',
				scope:this,
				callback : function(data){
					var photo = this.requiredPanel.getCmpByName('appUser.photo');
					photo.setValue(data[0].filePath);
					this.photoPanel.body.update('<img src="' + __ctxPath + '/attachFiles/' + data[0].filePath + '"  width="100%" height="100%"/>');
				},
				permitted_extensions : ['jpg']
			}).show();
	},
	/**
	 * 删除图片
	 */
	deletePhoto:function(){
		
	},
	/**
	 * 选择Title时，进行图片切换
	 * @param {} combo
	 * @param {} record
	 * @param {} index
	 */
	selectedTitle:function(combo,record,index){				
		if (combo.value == '0') {
			this.photoPanel.body.update('<img src="'+ __ctxPath	+ '/images/default_image_female.jpg"/>');
		} else {
			this.photoPanel.body.update('<img src="'+ __ctxPath+ '/images/default_image_male.jpg"/>');
		}				
	},
	//==================function end==================
	
	//初始化UI
	initUI:function(){
		this.photoPanel = new Ext.Panel({
							tbar : [{
										text : '上传',
										iconCls : 'btn-upload',
										scope:this,
										handler : this.uploadPhoto
									}, {
										text : '删除',
										iconCls : 'btn-delete',
										handler : this.deletePhoto
									}],
							title : '个人照片',
							width : 230,
							height : 380,
							html:'<img src="'+ __ctxPath+ '/images/default_image_male.jpg"/>'
		});
		
		
		
		
		this.requiredPanel=new Ext.Panel({
			title:'基本信息',
			width:400,
			height:450,
			layout:'form',
			defaultType:'textfield',
			bodyStyle:'padding:6px',
			defaults : {
				anchor : '98%,98%'
			},
			items:[
							{
								xtype : 'hidden',
								name : 'appUser.userId'
							}, {
								fieldLabel : '登录账号'+'<font color="red">*</font>',
								name : 'appUser.username',
								allowBlank : false
							},  {
								fieldLabel : '姓名'+'<font color="red">*</font>',
								name : 'appUser.fullname',
								allowBlank : false
							}, {
								fieldLabel : '性别',
								xtype : 'combo',
								hiddenName : 'appUser.title',
								mode : 'local',
								editable : false,
								triggerAction : 'all',
								store : [['1', '先生'], ['0', '女士']],
								value : '1',
								listeners : {
									scope:this,
									'select':this.selectedTitle
								}
							},{
								fieldLabel : 'E-mail'+'<font color="red">*</font>',
								name : 'appUser.email',
								vtype : 'email',
								allowBlank : false,
								blankText : '邮箱不能为空!',
								vtypeText : '邮箱格式不正确!'
							},
							{
								fieldLabel : '出生时间'+'<font color="red">*</font>',  //入职时间
								xtype : 'datefield',
								format : 'Y-m-d',
								name : 'appUser.accessionTime',
								allowBlank : false,
								length : 50
							},  {
								fieldLabel : '是否可用',
								hiddenName : 'appUser.status',
								xtype : 'combo',
								mode : 'local',
								editable : false,
								triggerAction : 'all',
								store : [['1', '激活'], ['0', '禁用']],
								value : 1
							},
								{
								fieldLabel : '身份证号码'+'<font color="red">*</font>', //家庭电话
								name : 'appUser.phone'
							},			{
								fieldLabel : '学历',  
								hiddenName : 'appUser.education',
								xtype : 'combo',
								mode : 'local',
								editable : false,							
								triggerAction : 'all',
								store : [['0', '其他学历'], ['1', '博士'], ['2', '硕士'], ['3', '本科'], ['4', '大专'], ['5', '中专'], ['6', '高中以下']],
								value : 0
							},							{
								fieldLabel : '学位', //新增
								hiddenName : 'appUser.degree',
								xtype : 'combo',
								mode : 'local',
								editable : false,							
								triggerAction : 'all',
								store : [['0', '无学位'], ['1', '博士学位'], ['2', '硕士学位'], ['3', '学士学位']],
								value : 0
							},						{
								fieldLabel : '当前职称', //新增
								hiddenName : 'appUser.tinfo',
								xtype : 'combo',
								mode : 'local',
								editable : false,							
								triggerAction : 'all',
								store : [['0', '初级职称'], ['1', '中级职称'], ['2', '副高级职称'], ['3', '正高级职称']],
								value : 0
							}, {
								fieldLabel : '联系电话',//移动电话
								xtype : 'numberfield',
								name : 'appUser.mobile'
							}, {
								fieldLabel : '内部编号',//传真
								name : 'appUser.fax'
							}, {
								fieldLabel : '资格证书号',//家庭住址
								name : 'appUser.address'
							}, {
								fieldLabel : '注册证书号',//邮编
								xtype : 'numberfield',
								name : 'appUser.zip'
							}, {
								xtype : 'hidden',
								name : 'appUser.photo'
							}
			]
		});
		
		if(this.userId==''){
			this.requiredPanel.insert(2,new Ext.form.TextField({
								fieldLabel : '登录密码',
								name : 'appUser.password',
								inputType : 'password',
								allowBlank : false
			}));
			this.requiredPanel.doLayout();
		}
		
		//可选择的部门树
		this.depTreePanel=new htsoft.ux.TreePanelEditor({
			title:'部门',
			url : __ctxPath+'/system/treeOrganization.do?demId=1',//1代表行政维度
			scope:this,
			autoScroll:true,
			showContextMenu:false,
			height:250,
			width:230,
			dblclick:this.depTreePanelDblClick
		});
		
		this.depGridPanel=new HT.EditorGridPanel({
			clicksToEdit:1,
			isShowTbar:false,
			showPaging:false,
			title:'已选部门',
			width:400,
			height:250,
			url:__ctxPath+'/system/findUserOrg.do?userId='+this.userId,
			fields:['userOrgId','orgId','orgName','isPrimary'],
			columns:[{
					header:'单位名',
					dataIndex:'orgName'
				},
				{
					header:'是否主单位',
					dataIndex:'isPrimary',
					renderer:function(val){
						if(val==1){
							return '是';
						}else{
							return '否';
						}
					},
					editor:{
								xtype : 'combo',
								mode : 'local',
								editable : false,
								triggerAction : 'all',
								store : [['1', '是'], ['0', '否']],
								value : 0,
								listeners:{
									scope:this,
									'select':this.setPrimary
								}
							}
				}
			],
			listeners:{
				scope:this,
				'rowdblclick':this.depGridPanelDblClick
			}
		});
		
		//部门选择Panel
		this.depPanel=new HT.HBoxPanel({
			items:[
				this.depTreePanel,
				this.depGridPanel
			]
		});
		
		
		this.roleForSelPanel=new HT.GridPanel({
			width:230,
			height:250,
			isShowTbar:false,
			showPaging:false,
			autoScroll:true,
			title:'可选角色(双击选择)',
			url:__ctxPath+'/system/getRolesAppUser.do?userId='+this.userId,
			fields:[
				'roleId',
				'roleName'
			],
			columns:[{
					header:'角色名',
					dataIndex:'roleName'
				}
			],
			listeners:{
				scope:this,
				'rowdblclick':this.roleForSelPanelDblClick
			}
		});
		
	
		this.roleSelectedPanel=new HT.GridPanel({
			width:400,
			height:250,
			isShowTbar:false,
			showPaging:false,
			autoScroll:true,
			title:'已选角色(双击删除)',
			url:__ctxPath+'/system/getSelRolesAppUser.do?userId='+this.userId,
			fields:['roleId','roleName'],
			columns:[{
					header:'角色名',
					dataIndex:'roleName'
				}
			],
			listeners:{
				scope:this,
				'rowdblclick':this.roleSelectedPanelDblClick
			}
		});
		
		//岗位树Panel
		this.posTreePanel=new htsoft.ux.TreePanelEditor({
			height:250,
			width:230,
			title:'岗位(双击添加)',
			url : __ctxPath+'/system/treePosition.do',
			scope : this,
			autoScroll:true,
			dblclick:this.posTreePanelDblClick
		});
		
		this.posGridPanel=new HT.EditorGridPanel({
			clicksToEdit:1,
			title:'已有岗位(双击删除)',
			height:250,
			width:400,
			isShowTbar:false,
			showPaging:false,
			url:__ctxPath+'/system/findUserPosition.do?userId='+this.userId,
			fields:['userPosId','posId','posName','isPrimary'],
			columns:[
				{
					header:'岗位名称',
					dataIndex:'posName'
				},
				{
					header:'是否主岗位',
					dataIndex:'isPrimary',
					renderer:function(val){
						if(val==1){
							return '是'
						}else{
							return '否'
						}
					},
					editor:{
								xtype : 'combo',
								mode : 'local',
								editable : false,
								triggerAction : 'all',
								store : [['1', '是'], ['0', '否']],
								value : 0
//								listeners:{
//									scope:this,
//									'select':this.setPrimary
//								}
					}
				}
				],
				listeners:{
					scope:this,
					'rowdblclick':this.posGridPanelDblClick
				}
		});
		
		//角色选择Panel
		this.rolePanel=new HT.HBoxPanel({
			items:[
				this.roleForSelPanel,
				this.roleSelectedPanel
			]	
		});
		
		//职位Panel
		this.posPanel=new HT.HBoxPanel({
			items:[
				this.posTreePanel,
				this.posGridPanel
			]
		});
		
		//创建基本信息的FieldSet
		this.basePanel=new HT.HBoxPanel({
			items:[
				this.photoPanel,
				this.requiredPanel
			]
		});
		
		
	},
	
	//双击已选择的部门
	posGridPanelDblClick:function(grid,rowIndex,e){
		var userPosId=grid.getStore().getAt(rowIndex).data.userPosId;
		if(userPosId){
			Ext.Ajax.request({
				url:__ctxPath+'/system/delUserPosition.do?userPosId='+userPosId,
				method:'POST',
				scope:this,
				success:function(response,options){
					Ext.ux.Toast.msg('操作','成功删除所选择角色！');
					this.posGridPanel.getStore().removeAt(rowIndex);
				}
			});
		}else{
			this.posGridPanel.getStore().removeAt(rowIndex);
		}
	},
	
	//双击职位树
	posTreePanelDblClick:function(node){
		if(node.id==0) return;
		var store=this.posGridPanel.getStore();
		for(var i=0;i<store.getCount();i++){
			if(store.getAt(i).data.posId==node.id){
				return;
			}
		}
		var recordType=store.recordType;
		store.add(new recordType({
			posId:node.id,
			posName:node.text,
			isPrimary:store.getCount()>0?0:1
		}));
	},
	
	//删除已选择的角色
	roleSelectedPanelDblClick:function(grid,rowIndex,e){
		if(this.userId!=''){
			var roleId=grid.getStore().getAt(rowIndex).data.roleId;
			Ext.Ajax.request({
				url:__ctxPath+'/system/delRoleAppUser.do?userId='+this.userId,
				params:{
					roleId:roleId
				},
				method:'POST',
				scope:this,
				success:function(response,options){
					Ext.ux.Toast.msg('操作','成功删除角色！');
					this.roleSelectedPanel.getStore().removeAt(rowIndex);
				}
			});
		}else{
			this.roleSelectedPanel.getStore().removeAt(rowIndex);
		}
	},
	
	//删除已选部门数据
	depGridPanelDblClick:function(grid,rowIndex,e){
		var userOrgId=grid.getStore().getAt(rowIndex).data.userOrgId;
		if(userOrgId){
			Ext.Ajax.request({
				url:__ctxPath+'/system/delUserOrg.do?userOrgId='+userOrgId,
				method:'Post',
				scope:this,
				success:function(response,options){
					Ext.ux.Toast.msg('操作信息','成功删除所属部门');
					this.depGridPanel.getStore().removeAt(rowIndex);
				}
			});
		}else{
			this.depGridPanel.getStore().removeAt(rowIndex);
		}
	},
	//角色选择器单击
	roleForSelPanelDblClick:function(grid,rowIndex,e){
		var store=grid.getStore();
		var record=store.getAt(rowIndex);
		var selStore=this.roleSelectedPanel.getStore();
		for(var i=0;i<selStore.getCount();i++){
			if(selStore.getAt(i).data.roleId==record.data.roleId) return;
		}
		var recordType=selStore.recordType;
		selStore.add(new recordType(record.data));
	},
	//设置为主部门
	setPrimary:function(combo,record,index){
		var isPrimary=record.data.field1;
		if(isPrimary==1){
			var store=this.depGridPanel.getStore();
			for(var i=0;i<store.getCount();i++){
				var record=store.getAt(i);
				record.data['field0']=0;
				record.commit();
			}
			this.depGridPanel.stopEditing();
		}
	},
	//部门树的双击
	depTreePanelDblClick:function(node){
		if(node.id==0) return;
		//检查该nodeid是否已经存在于depGrid用户
		var store=this.depGridPanel.getStore();
		var nodeId=node.id;
		for(var i=0;i<store.getCount();i++){
			if(store.getAt(i).data.orgId==nodeId){
				return;
			}
		}
		var recordType=store.recordType;
		store.add(new recordType({
			orgId:node.id,
			orgName:node.text,
			isPrimary:store.getCount()>0?0:1
		}));
	},
	//保存
	save:function(){
		//取得选择的角色
		var roleIds='';
		
		//取得选择的部门
		var orgIds=[];
		 //取得选择的职位
		var posIds=[];
		
		for(var i=0;i<this.roleSelectedPanel.getStore().getCount();i++){
			if(i>0) roleIds+=',';
			roleIds+=this.roleSelectedPanel.getStore().getAt(i).data.roleId;
		}
		
		for(var i=0;i<this.depGridPanel.getStore().getCount();i++){
			orgIds.push(this.depGridPanel.getStore().getAt(i).data);
		}
		
		for(var i=0;i<this.posGridPanel.getStore().getCount();i++){
			posIds.push(this.posGridPanel.getStore().getAt(i).data);
		}

		$postForm({
			url:__ctxPath+'/system/saveOrUpdateAppUser.do',
			formPanel:this,
			params:{
				roleIds:roleIds,
				orgIds:Ext.encode(orgIds),
				posIds:Ext.encode(posIds)
			},
			scope:this,
			callback:function(){
				App.getContentPanel().remove(this,true);
			}
		});
		
		
	}
});