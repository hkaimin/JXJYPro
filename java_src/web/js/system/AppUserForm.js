
//

var AppUserForm = function(_title, _userId,_depId,_depName) {
	return this.setup(_title, _userId,_depId,_depName);
};

AppUserForm.prototype.setup = function(_title, userId,_depId,_depName) {
	var depSelectPanel = this.initDepSelectPanel(userId);
	var depGrid = depSelectPanel.findByType('editorgrid')[0];

	var jobSelectPanel = this.initJopSelectPanel(userId);
	var jobGrid = jobSelectPanel.findByType('editorgrid')[0];

	var footToolbar = this.initFooterToolbar(userId, depGrid, jobGrid);

	var dep_url = __ctxPath + '/system/listDepartment.do?opt=appUser';
	var depSelector = new TreeSelector('depTreeSelector', dep_url, null,
			'appUser.depId', false);

	var job_url = __ctxPath + '/system/treeLoadJob.do';

	var jobSelector = new TreeSelector('jobTreeSelector', job_url, null,
			'appUser.jobId', false);
			
	
	
	

	var userform = new Ext.form.FormPanel({
		reader : new Ext.data.JsonReader({
					root : 'data'
				}, [{
							name : 'appUser.userId',
							mapping : 'userId'
						}, {
							name : 'appUser.jobId',
							mapping : 'jobId'
						}, {
							name : 'appUser.jobName',
							mapping : 'jobName'
						}, {
							name : 'appUser.username',
							mapping : 'username'
						}, {
							name : 'appUser.password',
							mapping : 'password'
						}, {
							name : 'appUser.fullname',
							mapping : 'fullname'
						}, {
							name : 'appUser.email',
							mapping : 'email'
						}, {
							name : 'appUser.depName',
							mapping : 'department.depName'
						}, {
							name : 'appUser.accessionTime',
							mapping : 'accessionTime'
						}, {
							name : 'appUserStatus',
							mapping : 'status'
						}, {
							name : 'appUserTitle',
							mapping : 'title'
						}, {
							name : 'appUser.phone',
							mapping : 'phone'
						}, {
							name : 'appUser.mobile',
							mapping : 'mobile'
						}, {
							name : 'appUser.fax',
							mapping : 'fax'
						}, {
							name : 'appUser.address',
							mapping : 'address'
						}, {
							name : 'appUser.zip',
							mapping : 'zip'
						}, {
							name : 'appUser.photo',
							mapping : 'photo'
						}, {
							name : 'appUser.dynamicPwd',
							mapping : 'dynamicpwd'
						}, {
							name : 'appUser.dyPwdStatus',
							mapping : 'dyPwdStatus'
						}]),
		id : 'AppUserForm',
		title : _title,
		iconCls : 'menu-customer',
		border : false,
		autoScroll : true,
		bodyStyle : "background-color: transparent;",
		labelAlign : "right",
		tbar : footToolbar,
		defaults : {
			anchor : '98%,100%',
			xtype : 'panel'
		},
		url : __ctxPath + '/system/saveAppUser.do',
		layout : 'form',
		items : [{
			xtype : 'fieldset',
			title : "基本信息",
			collapsible : true,
			autoHeight : true,
			defaults : {
				anchor : '96%,96%'
			},
			items : {
				xtype : 'panel',
				border : false,
				layout : 'hbox',
				layoutConfig : {
					padding : '5',
					align : 'middle'
				},
				defaults : {
					style : 'padding:0px 5px 0px 5px;',
					anchor : '98%,98%',
					height : 250,
					width : '38%'
				},
				items : [{
					id : 'displayUserPhoto',
					xtype : "panel",
					bodyStyle : 'MARGIN-RIGHT: auto; MARGIN-LEFT: auto;',
					title : "个人照片",
					width : '20%',
					//flex : 1,
					html : '<div style="MARGIN-RIGHT: auto; MARGIN-LEFT: auto;"><img src="'+ __ctxPath + '/images/default_image_male.jpg" style=""/></div>',
					tbar : new Ext.Toolbar({
								height : 30,
								items : [{
											text : '上传',
											iconCls : 'btn-upload',
											handler : function() {
												AppUserForm
														.uploadPhotoBtn(userId);
											}
										}, {
											text : '删除',
											iconCls : 'btn-delete',
											handler : function() {
												AppUserForm
														.deletePhotoBtn(userId);
											}
										}]
							})
				}, {
					id : 'AppUserMustInfo',
					xtype : "panel",
					title : "必填信息",
					labelWidth : 55,
					defaults : {
						anchor : '98%,98%'
					},
					bodyStyle:'padding:5px;',
					layout : 'form',
					defaultType : "textfield",
					labelAlign : "right",
					hideLabels : false,
					//flex : 1.5,
					//padding : '5',
					items : [{
								xtype : 'hidden',
								fieldLabel : '员工ID',
								name : 'appUser.userId',
								id : 'appUser.userId'
							}, {
								fieldLabel : '登录账号',
								name : 'appUser.username',
								id : 'appUser.username',
								allowBlank : false,
								blankText : '登录账号不能为空!'
							}, {
								fieldLabel : '登录密码',
								name : 'appUser.password',
								id : 'appUser.password',
								inputType : 'password',
								allowBlank : false,
								blankText : '登录密码不能为空!'
							}, {
								fieldLabel : '员工姓名',
								name : 'appUser.fullname',
								id : 'appUser.fullname',
								allowBlank : false,
								blankText : '员工姓名不能为空!'
							}, {
								fieldLabel : 'E-mail',
								name : 'appUser.email',
								id : 'appUser.email',
								vtype : 'email',
								allowBlank : false,
								blankText : '邮箱不能为空!',
								vtypeText : '邮箱格式不正确!'
							},

							{
								layout : 'hbox',
								hideLabel : true,
								xtype : 'compositefield',
								width : 300,
								items : [{
											xtype : 'label',
											text : '主部门:',
											style : 'padding:0px 0px 0px 15px;',
											width : 55
										}, depSelector, {
											xtype : 'button',
											text : '更多部门',
											handler : function(b, e) {
												var form = Ext
														.getCmp('AppUserForm');
												var sets = form
														.findByType('fieldset');
												for (i = 0; i < sets.length; i++) {
													if (sets[i].getId() != 'depFieldset') {
														sets[i].collapse();

													}
												}

												Ext.getCmp('depFieldset')
														.expand();

												// if (!b.expand) {
												// b.expand = 1;
												// Ext.getCmp('depFieldset')
												// .expand();
												// } else if (b.expand == -1) {
												//
												// b.expand = 1;
												// Ext.getCmp('depFieldset')
												// .expand();
												//
												// } else {
												// b.expand = -1;
												// Ext.getCmp('depFieldset')
												// .collapse();
												// }
											}
										}]

							}, {
								layout : 'hbox',
								hideLabel : true,
								xtype : 'compositefield',
								width : 300,
								items : [{
											xtype : 'label',
											text : '主职位:',
											style : 'padding:0px 0px 0px 15px;',
											width : 55
										}, jobSelector, {
											xtype : 'button',
											text : '更多职位',
											handler : function(b, e) {
												var form = Ext
														.getCmp('AppUserForm');
												var sets = form
														.findByType('fieldset');
												for (i = 0; i < sets.length; i++) {
													if (sets[i].getId() != 'jobFieldset') {
														sets[i].collapse();

													}
												}
												Ext.getCmp('jobFieldset')
														.expand();

												// if (!b.expand) {
												// b.expand = 1;
												// Ext.getCmp('jobFieldset')
												// .expand();
												// } else if (b.expand == -1) {
												//
												// b.expand = 1;
												// Ext.getCmp('jobFieldset')
												// .expand();
												//
												// } else {
												// b.expand = -1;
												// Ext.getCmp('jobFieldset')
												// .collapse();
												// }
											}
										}]

							},
							// depSelector, // 主部门
							// jobSelector,// 主职位

							{
								fieldLabel : '入职时间',
								xtype : 'datefield',
								format : 'Y-m-d',
								name : 'appUser.accessionTime',
								id : 'appUser.accessionTime',
								allowBlank : false,
								blankText : '入职时间不能为空!',
								length : 50
							}, {
								fieldLabel : '状态',
								id : 'appUserStatus',
								hiddenName : 'appUser.status',
								xtype : 'combo',
								mode : 'local',
								editable : false,
								triggerAction : 'all',
								store : [['1', '激活'], ['0', '禁用']],
								value : 1
							}, {
								xtype : 'hidden',
								name : 'appUser.department.depId',
								id : 'appUser.depId'
							}

							, {
								xtype : 'hidden',
								name : 'appUser.jobId',
								id : 'appUser.jobId'
							}]
				}, {
					xtype : "panel",
					title : "选填信息",
					//padding : '5',
					layout : 'form',
					defaultType : 'textfield',
					labelWidth : 55,
					defaults : {
						anchor : '98%,98%'
					},
					hideLabel : false,
					labelAlign : "right",
					//flex : 1.5,
					items : [{
						fieldLabel : '性别',
						xtype : 'combo',
						hiddenName : 'appUser.title',
						id : 'appUserTitle',
						mode : 'local',
						editable : false,
						triggerAction : 'all',
						store : [['1', '先生'], ['0', '女士']],
						value : '1',
						listeners : {
							select : function(combo, record, index) {
								var photo = Ext.getCmp('appUser.photo');
								if (photo.value == ''
										|| photo.value == 'undefined'
										|| photo.value == null) {
									var display = Ext
											.getCmp('displayUserPhoto');
									if (combo.value == '0') {
										display.body
												.update('<img src="'
														+ __ctxPath
														+ '/images/default_image_female.jpg"/>');
									} else {
										display.body
												.update('<img src="'
														+ __ctxPath
														+ '/images/default_image_male.jpg"/>');
									}
								}
							}
						}
					}, {
						fieldLabel : '家庭电话',
						name : 'appUser.phone',
						id : 'appUser.phone'
					}, {
						fieldLabel : '移动电话',
						xtype : 'numberfield',
						name : 'appUser.mobile',
						id : 'appUser.mobile'
					}, {
						fieldLabel : '传真',
						name : 'appUser.fax',
						id : 'appUser.fax'
					}, {
						fieldLabel : '家庭住址',
						name : 'appUser.address',
						id : 'appUser.address'
					}, {
						fieldLabel : '邮编',
						xtype : 'numberfield',
						name : 'appUser.zip',
						id : 'appUser.zip'
					}, {
						filedLabel : '照片',
						xtype : 'hidden',
						id : 'appUser.photo',
						name : 'appUser.photo'
					}]
				}]

			}
		}, {
			xtype : 'fieldset',
			id : 'depFieldset',
			columnWidth : 0.5,
			title : "部门信息",
			collapsed : true,
			collapsible : true,
			autoHeight : true,
			defaults : {
				anchor : '96%,96%'
			},
			items : depSelectPanel
		}, {
			xtype : 'fieldset',
			id : 'jobFieldset',
			columnWidth : 0.5,
			title : "职位信息",
			collapsed : true,
			collapsible : true,
			autoHeight : true,
			defaults : {
				anchor : '96%,96%'
			},
			items : jobSelectPanel
		}, {
			xtype : 'fieldset',
			id : 'rolesFieldset',
			columnWidth : 0.5,
			title : "角色信息",
			collapsed : true,
			collapsible : true,
			autoHeight : true,
			defaults : {
				anchor : '96%,96%'
			},
			items : {
				xtype : 'panel',
				height : 220,
				border : false,

				items : [{
					xtype : 'itemselector',
					id : 'AppUserRoles',
					name : 'AppUserRoles',
					fromLegend : '',
					flex : 1,
					imagePath : __ctxPath + '/ext3/ux/images/',
					defaults : {
						anchor : '100%,100%'
					},
					layout : {
						type : 'hbox',
						align : 'stretch'
					},
					multiselects : [{
						id : 'chooseRoles',
						title : '可选角色',
						height : 220,
						width : Ext.getCmp('centerTabPanel').getInnerWidth()
								/ 2,
						autoWidth : true,
						store : new Ext.data.SimpleStore({
									autoLoad : true,
									baseParams : {
										userId : userId
									},
									url : __ctxPath
											+ '/system/chooseRolesAppUser.do',
									fields : ['roleId', 'roleName']
								}),
						displayField : 'roleName',
						valueField : 'roleId'
					}, {
						id : 'selectedRoles',
						name : 'selectedRoles',
						title : '已有角色',
						height : 220,
						width : Ext.getCmp('centerTabPanel').getInnerWidth()
								/ 2,
						store : new Ext.data.SimpleStore({
							autoLoad : true,
							baseParams : {
								userId : userId
							},
							url : __ctxPath + '/system/selectedRolesAppUser.do',
							fields : ['roleId', 'roleName']
						}),
						tbar : [{
							text : '清除所选',
							handler : function() {
								Ext.getCmp('AppUserForm').getForm()
										.findField('AppUserRoles').reset();
							}
						}],
						displayField : 'roleName',
						valueField : 'roleId'
					}]

				}]

			}
		}
		/*
		 * , { xtype : 'fieldset', columnWidth : 0.5, title : "其它信息", collapsed :
		 * true, collapsible : true, autoHeight : true, defaults : { anchor :
		 * '100%,100%' }, items : { xtype : 'panel',
		 * 
		 * height : 220, flex : 1, items : [] } }
		 */
		]
	});
if(_depId){
	Ext.getCmp('appUser.depId').setValue(_depId);
	Ext.getCmp('depTreeSelector').setValue(_depName);
}	
		
	
	
	return userform;
};

// 初始化操作菜单
AppUserForm.prototype.initFooterToolbar = function(userId, depGrid, jobGrid) {

	var toolbar = new Ext.Toolbar({
		id : 'AppUserFormToolbar',
		height : 30,
		items : [{
			text : '保存',
			iconCls : 'btn-save',
			handler : function() {
				var userform = Ext.getCmp('AppUserForm');
				if (userform.getForm().isValid()) {

					var depStore = depGrid.getStore();
					var depParams = [];
					var cnt = depStore.getCount();
					var mainDepId = userform.getForm()
							.findField('appUser.depId').getValue();
					var insertDep = true;
					for (i = 0; i < cnt; i++) {
						var rec = depStore.getAt(i);
						if ((rec.data.department.depId * 1) == (mainDepId * 1)) {
							insertDep = false;
							rec.data.ismain = 1;
						}
						depParams.push(rec.data);

					}
					
					if (insertDep==true) {
						var department = {};
						Ext.apply(department, {
									depId : userform.getForm()
											.findField('appUser.depId')
											.getValue()
								});

						var recrod = new depStore.recordType();

						recrod.data = {};
						recrod.data.ismain = 1;
						recrod.data.sn = 0;

						Ext.apply(recrod.data, {
									department : department
								});
						depParams.push(recrod.data);
					}

					var jobStore = jobGrid.getStore();
					var jobParams = [];
					var jobcnt = jobStore.getCount();
					var mainJobId = userform.getForm()
							.findField('appUser.jobId').getValue();
					var insertJob = true;
					for (i = 0; i < jobcnt; i++) {
						var rec = jobStore.getAt(i);
						if ((rec.data.job.jobId * 1) == (mainJobId * 1)) {
							insertJob = false;
							rec.data.isMain = 1;
						}
						jobParams.push(rec.data);

					}

					if (insertJob==true) {
						var job = {};
						Ext.apply(job, {
									jobId : userform.getForm()
											.findField('appUser.jobId')
											.getValue()
								});

						var recrod = new jobStore.recordType();

						recrod.data = {};
						recrod.data.isMain = 1;

						Ext.apply(recrod.data, {
									job : job
								});

						jobParams.push(recrod.data);
					}

					userform.getForm().submit({
								waitMsg : '正在提交用户信息',
								params : {
									depParams : Ext.encode(depParams),
									jobParams : Ext.encode(jobParams)
								},
								success : function(userform, o) {
									Ext.ux.Toast.msg('操作信息', '保存成功！');
									var userview = Ext.getCmp('AppUserGrid');
									if (userview != null) {// 假如员工列表不为空,则重载数据
										userview.getStore().reload({
													start : 0,
													limit : 25
												});
									}
									AppUtil.removeTab('AppUserForm');
								},
								failure : function(userform, o) {
									Ext.ux.Toast.msg('错误信息', o.result.msg);
									Ext.getCmp('appUser.username').setValue('');
								}
							});
				}
			}

		}, {
			text : '取消',
			iconCls : 'reset',
			handler : function() {
				var tabs = Ext.getCmp('centerTabPanel');
				tabs.remove('AppUserForm');
			}
		}, {
			text : '修改密码',
			id : 'resetPassword',
			iconCls : 'btn-password',
			hidden : true,
			handler : function() {
				new ResetPasswordForm(userId);
			}
		}, {
			text : '令牌设置',
			iconCls : 'btn-dynamic-pwd',
			hidden : sysConfigInfo.dynamicPwd=='2'?true:false,
			handler : function() {
				new DynamicPwdForm({
							userId : userId
						}).show();
			}
		}]
	});
	return toolbar;
};
/**
 * 上传员工图片按钮动作
 * 
 * @param {}
 *            userId
 */
AppUserForm.uploadPhotoBtn = function(userId) {
	var photo = Ext.getCmp('appUser.photo');
	var dialog = App.createUploadDialog({
				file_cat : 'system/appUser',
				callback : uploadUserPhoto,
				permitted_extensions : ['jpg']
			});
	if (photo.value != '' && photo.value != null && photo.value != 'undefined') {
		var msg = '再次上传需要先删除原有图片,';
		Ext.Msg.confirm('信息确认', msg + '是否删除？', function(btn) {
			if (btn == 'yes') {
				// 删除图片
				Ext.Ajax.request({
					url : __ctxPath + '/system/deleteFileAttach.do',
					method : 'post',
					params : {
						filePath : photo.value
					},
					success : function() {
						if (userId != '' && userId != null
								&& userId != 'undefined') {
							Ext.Ajax.request({
								url : __ctxPath + '/system/photoAppUser.do',
								method : 'post',
								params : {
									userId : userId
								},
								success : function() {
									photo.setValue('');
									var appUserTitle = Ext
											.getCmp('appUserTitle');
									var display = Ext
											.getCmp('displayUserPhoto');
									if (appUserTitle.value == 1) {
										display.body
												.update('<img src="'
														+ __ctxPath
														+ '/images/default_image_male.jpg"  width="100%" height="100%"/>');
									} else {
										display.body
												.update('<img src="'
														+ __ctxPath
														+ '/images/default_image_female.jpg"  width="100%" height="100%"/>');
									}
									dialog.show('queryBtn');
								}
							});
						} else {
							photo.setValue('');
							var appUserTitle = Ext.getCmp('appUserTitle');
							var display = Ext.getCmp('displayUserPhoto');
							if (appUserTitle.value == 1) {
								display.body
										.update('<img src="' + __ctxPath
												+ '/images/default_image_male.jpg"  width="100%" height="100%"/>');
							} else {
								display.body
										.update('<img src="' + __ctxPath + '/images/default_image_female.jpg"  width="100%" height="100%"/>');
							}
							dialog.show('queryBtn');
						}
					}
				});
			}
		});
	} else {
		dialog.show('queryBtn');
	}
};

/**
 * 删除员工照片按钮动作
 * 
 * @param {}
 *            userId
 */
AppUserForm.deletePhotoBtn = function(userId) {
	var photo = Ext.getCmp('appUser.photo');
	if (photo.value != null && photo.value != '' && photo.value != 'undefined') {
		Ext.Msg.confirm('确认信息', '照片一旦删除将不可恢复, 是否删除?', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : __ctxPath + '/system/deleteFileAttach.do',
					method : 'post',
					params : {
						filePath : photo.value
					},
					success : function() {
						if (userId != '' && userId != null
								&& userId != 'undefined') {
							Ext.Ajax.request({
								url : __ctxPath + '/system/photoAppUser.do',
								method : 'post',
								params : {
									userId : userId
								},
								success : function() {
									photo.setValue('');
									var appUserTitle = Ext
											.getCmp('appUserTitle');
									var display = Ext
											.getCmp('displayUserPhoto');
									if (appUserTitle.value == 1) {
										display.body
												.update('<img src="'
														+ __ctxPath
														+ '/images/default_image_male.jpg"  width="100%" height="100%"/>');
									} else {
										display.body
												.update('<img src="'
														+ __ctxPath
														+ '/images/default_image_female.jpg"  width="100%" height="100%"/>');
									}
								}
							});
						} else {
							photo.setValue('');
							var appUserTitle = Ext.getCmp('appUserTitle');
							var display = Ext.getCmp('displayUserPhoto');
							if (appUserTitle.value == 1) {
								display.body
										.update('<img src="'
												+ __ctxPath
												+ '/images/default_image_male.jpg"  width="100%" height="100%"/>');
							} else {
								display.body
										.update('<img src="'
												+ __ctxPath
												+ '/images/default_image_female.jpg"  width="100%" height="100%"/>');
							}
						}
					}
				});
			}
		});
	}// end if
	else {
		Ext.ux.Toast.msg('提示信息', '您还未增加照片.');
	}
};
/**
 * 上传照片
 * 
 * @param {}
 *            data
 */
function uploadUserPhoto(data) {
	var photo = Ext.getCmp('appUser.photo');
	var display = Ext.getCmp('displayUserPhoto');
	photo.setValue(data[0].filePath);
	display.body.update('<img src="' + __ctxPath + '/attachFiles/'
			+ data[0].filePath + '"  width="100%" height="100%"/>');
};

// 部门选择
AppUserForm.prototype.initDepSelectPanel = function(userId) {
	Ext.QuickTips.init();
	/* 部门树 */
	var selectNode = null;
	var dep_url = __ctxPath + '/system/listDepartment.do';
	var depTree = new Ext.tree.TreePanel({
				height : 220,
				flex : 7,
				useArrows : false,
				autoScroll : true,
				animate : false,
				enableDD : false,
				containerScroll : true,
				border : true,
				dataUrl : dep_url,
				rootVisible : false,
				root : {
					nodeType : 'async',
					text : 'Ext JS',
					draggable : false,
					id : 'source'
				},
				listeners : {
					'click' : function(node) {
						selectNode = node;
					},
					'beforeload' : function(node) {
						// node.setText('<font
						// qtip="双击添加">'+node.text+'</font>');
						// node.eachChild( function(args){
						//							
						// }, this);
						node.attributes.qtip = '双击添加';
						node.attributes.description = '双击添加';
						// alert(Ext.encode(node.attributes));
					}

				}
			});

	var dellTrue = function(ids) {

		Ext.Ajax.request({
					url : __ctxPath + '/system/multiDelDepUsers.do',
					params : {
						ids : ids
					},
					method : 'POST',
					success : function(response, options) {
						Ext.ux.Toast.msg('操作信息', '成功删除该明细！');

					},
					failure : function(response, options) {
						Ext.ux.Toast.msg('操作信息', '操作出错，请联系管理员！');
					}
				});

	};
	/* 选择按钮 */
	var add = function() {
		if (selectNode == null) {
			Ext.ux.Toast.msg("信息", "请选择要添加的部门！");
			return;
		}

		var isRe = false;
		for (i = 0; i < gridStore.getCount(); i++) {
			var r = gridStore.getAt(i);
			if (r.data.department.depId == selectNode.id) {
				isRe = true;
			}
		}
		if (isRe == true) {

			Ext.ux.Toast.msg("信息", "部门重复，请选择其它部门！");
			return;

		}

		var department = {};
		Ext.apply(department, {
					depId : selectNode.id,
					depName : selectNode.text
				});

		var recrod = new gridStore.recordType();

		recrod.data = {};
		recrod.data.ismain = 0;
		recrod.data.sn = 0;

		Ext.apply(recrod.data, {
					department : department
				});

		gridStore.insert(0, recrod);
		gridStore.commitChanges();
		selectNode = null;
	};
	var del = function() {

		var selectRecords = depGrid.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}

		if (selectRecords[0].data.depuserid != null
				&& selectRecords[0].data.depuserid != '') {
			dellTrue(selectRecords[0].data.depuserid);
		}
		gridStore.remove(selectRecords[0]);

		gridStore.commitChanges();

	};
	var selectPanel = new Ext.Panel({
				frame : false,
				border : false,
				hideBorders : true,
				height : 220,
				flex : 0.4,
				layout : {
					type : 'vbox',
					pack : 'center',
					align : 'stretch'
				},
				defaults : {
					anchor : '96%,96%'
				},
				defaults : {
					margins : '0 3 0 0'
				},
				items : [{
							xtype : 'button',
							iconCls : 'btn-right',
							scope : this,
							handler : add
						}, {
							xtype : 'button',
							iconCls : 'btn-left',
							scope : this,
							handler : del
						}]
			});
	/* 部门grid */
	var dellAll = function() {
		// gridStore.removeAll();
		var ids = Array();
		for (i = 0; i < gridStore.getCount(); i++) {
			var r = gridStore.getAt(i);
			if (r.data.depuserid != null && r.data.depuserid != '') {
				ids.push(r.data.depuserid);
			};

		}
		if (ids.length > 0) {
			dellTrue(ids);
		}
		gridStore.removeAll();
		gridStore.commitChanges();
	};
	var gridTopbar = new Ext.Toolbar({
				items : [{
							text : '清除所选',
							scope : this,
							handler : dellAll
						}]
			});

	var gridRecord = Ext.data.Record.create([{
				name : 'depUserId',
				type : 'int'
			}, 'department', 'appUser', 'isMain', 'sn']);

	var gridMemoryProxy = new Ext.data.HttpProxy({
				url : __ctxPath + "/system/listDepUsers.do"
			});

	var gridJsonReader = new Ext.data.JsonReader({
				root : 'result',
				totalProperty : 'totalCounts',
				idProperty : "depuserid"
			}, gridRecord);

	var gridStore = new Ext.data.Store({
				id : 'AppUserForm.depStore',
				proxy : gridMemoryProxy,
				reader : gridJsonReader
			});

	gridStore.on('beforeload', function(store) {
				store.baseParams = {
					start : 0,
					limit : 10000,
					'Q_appUser.userId_L_EQ' : userId
				};
			});
	gridStore.setDefaultSort('sn');

	if (userId != '' && userId != null && userId != 'undefined') {

		gridStore.load();
	}

	var sm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true

			});
	var depGrid = new Ext.grid.EditorGridPanel({
				frame : false,
				border : true,
				flex : 6,
				height : 220,
				tbar : gridTopbar,
				store : gridStore,
				clicksToEdit : 1,
				sm : sm,
				viewConfig : {
					forceFit : true,
					autoFill : true
				},
				columns : [{
							header : 'depuserid',
							dataIndex : 'depuserid',
							sortable : true,
							hidden : true
						}, {
							header : '部门',
							sortable : true,

							dataIndex : 'department',
							renderer : function(department) {
								if (department)
									return '<font qtip="双击删除">' + department.depName + '</font>';
							}
						}, {
							header : '是否主部门',
							sortable : true,
							width : 55,
							dataIndex : 'ismain',
							editable : true,
							renderer : function(ismain) {
								return ismain == 1 ? '是': '否';
							}

						}
				]
			});
	Ext.QuickTips.init();

	depGrid.on('dblclick', function(e) {

				var selectRecords = depGrid.getSelectionModel().getSelections();
				if (selectRecords.length == 0) {
					Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
					return;
				}

				if (selectRecords[0].data.depuserid != null
						&& selectRecords[0].data.depuserid != '') {
					dellTrue(selectRecords[0].data.depuserid);
				}
				gridStore.remove(selectRecords[0]);

				gridStore.commitChanges();

			}, this);
	depTree.on('dblclick', function(selectNode) {

				if (selectNode == null) {
					Ext.ux.Toast.msg("信息", "请选择要添加的部门！");
					return;
				}

				var isRe = false;
				for (i = 0; i < gridStore.getCount(); i++) {
					var r = gridStore.getAt(i);
					if (r.data.department.depId == selectNode.id) {
						isRe = true;
					}
				}
				if (isRe == true) {

					Ext.ux.Toast.msg("信息", "部门重复，请选择其它部门！");
					return;

				}

				var department = {};
				Ext.apply(department, {
							depId : selectNode.id,
							depName : selectNode.text
						});

				var recrod = new gridStore.recordType();

				recrod.data = {};
				recrod.data.ismain = 0;
				recrod.data.sn = 0;

				Ext.apply(recrod.data, {
							department : department
						});

				gridStore.insert(0, recrod);
				gridStore.commitChanges();
				selectNode = null;

			}, this);

	/* 总容器 */

	var panel = new Ext.Panel({
				xtype : 'panel',
				height : 220,
				border : false,
				layout : {
					type : 'hbox',
					align : 'stretch'
				},
				height : 220,
				items : [depTree, selectPanel, depGrid]
			});

	return panel;
};

// 职位选择
AppUserForm.prototype.initJopSelectPanel = function(userId) {
	Ext.QuickTips.init();
	/* 职位树 */
	var selectNode = null;
	var Jop_url = __ctxPath + '/system/treeLoadJob.do';
	var JopTree = new Ext.tree.TreePanel({
				height : 220,
				flex : 7,
				useArrows : false,
				autoScroll : true,
				animate : false,
				enableDD : false,
				containerScroll : true,
				border : true,
				dataUrl : Jop_url,
				rootVisible : false,
				root : {
					nodeType : 'async',
					text : 'Ext JS',
					draggable : false

				},
				listeners : {
					'click' : function(node) {
						selectNode = node;
					},
					'beforeload' : function(node) {
						// node.setText('<font
						// qtip="双击添加">'+node.text+'</font>');
						// node.eachChild( function(args){
						//							
						// }, this);
						node.attributes.qtip = '双击添加';
						node.attributes.description = '双击添加';
						// alert(Ext.encode(node.attributes));
					}

				}
			});

	var dellTrue = function(ids) {

		Ext.Ajax.request({
					url : __ctxPath + '/system/multiDelUserJob.do',
					params : {
						ids : ids
					},
					method : 'POST',
					success : function(response, options) {
						Ext.ux.Toast.msg('操作信息', '成功删除该明细！');

					},
					failure : function(response, options) {
						Ext.ux.Toast.msg('操作信息', '操作出错，请联系管理员！');
					}
				});

	};
	/* 选择按钮 */
	var add = function() {
		if (selectNode == null) {
			Ext.ux.Toast.msg("信息", "请选择要添加的部门！");
			return;
		}

		var isRe = false;
		for (i = 0; i < jobStore.getCount(); i++) {
			var r = jobStore.getAt(i);
			if (r.data.job.jobId == selectNode.id) {
				isRe = true;
			}
		}
		if (isRe == true) {

			Ext.ux.Toast.msg("信息", "部门重复，请选择其它部门！");
			return;

		}

		var job = {};
		Ext.apply(job, {
					jobId : selectNode.id,
					jobName : selectNode.text
				});

		var recrod = new jobStore.recordType();

		recrod.data = {};
		recrod.data.ismain = 0;

		Ext.apply(recrod.data, {
					job : job
				});

		jobStore.insert(0, recrod);

		jobStore.commitChanges();
		selectNode = null;

	};
	var del = function() {

		var selectRecords = JopGrid.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}

		if (selectRecords[0].data.userJobId != null
				&& selectRecords[0].data.userJobId != '') {
			dellTrue(selectRecords[0].data.userJobId);
		}

		jobStore.remove(selectRecords[0]);
		jobStore.commitChanges();

	};
	var selectPanel = new Ext.Panel({
				frame : false,
				border : false,
				hideBorders : true,
				height : 220,
				flex : 0.4,
				layout : {
					type : 'vbox',
					pack : 'center',
					align : 'stretch'
				},
				defaults : {
					anchor : '96%,96%'
				},
				defaults : {
					margins : '0 3 0 0'
				},
				items : [{
							xtype : 'button',
							iconCls : 'btn-right',
							scope : this,
							handler : add
						}, {
							xtype : 'button',
							iconCls : 'btn-left',
							scope : this,
							handler : del
						}]
			});
	/* 职位grid */
	var dellAll = function() {

		// jobStore.removeAll();
		var ids = Array();
		for (i = 0; i < jobStore.getCount(); i++) {
			var r = jobStore.getAt(i);
			if (r.data.userJobId != null && r.data.userJobId != '') {
				ids.push(r.data.userJobId);
			};

		}
		if (ids.length > 0) {
			dellTrue(ids);

		}
		jobStore.removeAll();
		jobStore.commitChanges();

	};
	var gridTopbar = new Ext.Toolbar({
				items : [{
							text : '清除所选',
							scope : this,
							handler : dellAll
						}]
			});

	var jobRecord = Ext.data.Record.create([{
				name : 'userJobId',
				type : 'int'
			}, 'isMain', 'job', 'appUser']);

	var gridMemoryProxy = new Ext.data.HttpProxy({
				url : __ctxPath + "/system/listUserJob.do"
			});

	var gridJsonReader = new Ext.data.JsonReader({
				root : 'result',
				totalProperty : 'totalCounts',
				idProperty : "userJobId"
			}, jobRecord);

	var jobStore = new Ext.data.Store({
				id : 'AppUserForm.JopStore',
				proxy : gridMemoryProxy,
				reader : gridJsonReader
			});

	jobStore.on('beforeload', function(store) {
				store.baseParams = {
					start : 0,
					limit : 10000,
					'Q_appUser.userId_L_EQ' : userId
				};
			});
	jobStore.setDefaultSort('userJobId');

	if (userId != '' && userId != null && userId != 'undefined') {
		jobStore.load();
	}

	var sm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true

			});
	var JopGrid = new Ext.grid.EditorGridPanel({
				frame : false,
				border : true,
				flex : 6,
				height : 220,
				tbar : gridTopbar,
				store : jobStore,
				clicksToEdit : 1,
				sm : sm,
				viewConfig : {
					forceFit : true,
					autoFill : true
				},
				columns : [{
							header : 'userJobId',
							dataIndex : 'userJobId',
							sortable : true,
							hidden : true
						}, {
							header : '职位',
							sortable : true,
							dataIndex : 'job',
							renderer : function(job) {
								if (job)
									return '<font qtip="双击删除">' + job.jobName + '</font>';
							}
						}, {
							header : '是否主职位',
							sortable : true,
							width : 55,
							dataIndex : 'isMain',
							editable : true,
							renderer : function(isMain) {
								return isMain == 1 ? '是' : '否';
							}
						}
				]
			});
	Ext.QuickTips.init();

	JopGrid.on('dblclick', function(e) {

				var selectRecords = JopGrid.getSelectionModel().getSelections();
				if (selectRecords.length == 0) {
					Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
					return;
				}

				if (selectRecords[0].data.userJobId != null
						&& selectRecords[0].data.userJobId != '') {
					dellTrue(selectRecords[0].data.userJobId);
				}

				jobStore.remove(selectRecords[0]);
				jobStore.commitChanges();

			}, this);
	JopTree.on('dblclick', function(selectNode) {
				if (selectNode == null) {
					Ext.ux.Toast.msg("信息", "请选择要添加的部门！");
					return;
				}

				var isRe = false;
				for (i = 0; i < jobStore.getCount(); i++) {
					var r = jobStore.getAt(i);
					if (r.data.job.jobId == selectNode.id) {
						isRe = true;
					}
				}
				if (isRe == true) {

					Ext.ux.Toast.msg("信息", "部门重复，请选择其它部门！");
					return;

				}

				var job = {};
				Ext.apply(job, {
							jobId : selectNode.id,
							jobName : selectNode.text
						});

				var recrod = new jobStore.recordType();

				recrod.data = {};
				recrod.data.ismain = 0;

				Ext.apply(recrod.data, {
							job : job
						});

				jobStore.insert(0, recrod);
				jobStore.commitChanges();
				selectNode = null;
			}, this);

	/* 总容器 */

	var panel = new Ext.Panel({
				xtype : 'panel',
				height : 220,
				border : false,
				layout : {
					type : 'hbox',
					align : 'stretch'
				},
				height : 220,
				items : [JopTree, selectPanel, JopGrid]
			});

	return panel;
};

AppUserForm.prototype.initRoleSelectPanel = function(userId) {
	Ext.QuickTips.init();
	/* 职位树 */
	var selectNode = null;
	var Role_url = __ctxPath+ '/system/chooseRolesAppUser.do';
	var RoleTree = new Ext.tree.TreePanel({
				height : 220,
				flex : 7,
				useArrows : false,
				autoScroll : true,
				animate : false,
				enableDD : false,
				containerScroll : true,
				border : true,
				dataUrl : Role_url,
				rootVisible : false,
				root : {
					nodeType : 'async',
					text : 'Ext JS',
					draggable : false

				},
				listeners : {
					'click' : function(node) {
						selectNode = node;
					},
					'beforeload' : function(node) {
						// node.setText('<font
						// qtip="双击添加">'+node.text+'</font>');
						// node.eachChild( function(args){
						//							
						// }, this);
						node.attributes.qtip = '双击添加';
						node.attributes.description = '双击添加';
						// alert(Ext.encode(node.attributes));
					}

				}
			});

	var dellTrue = function(ids) {

		Ext.Ajax.request({
					url : __ctxPath + '/system/multiDelRoleAppUser.do',//删除角色的action路径
					params : {
						ids : ids,
						userId : userId
					},
					method : 'POST',
					success : function(response, options) {
						Ext.ux.Toast.msg('操作信息', '成功删除该明细！');

					},
					failure : function(response, options) {
						Ext.ux.Toast.msg('操作信息', '操作出错，请联系管理员！');
					}
				});

	};
	/* 选择按钮 */
	var add = function() {
		if (selectNode == null) {
			Ext.ux.Toast.msg("信息", "请选择要添加的角色！");
			return;
		}

		var isRe = false;
		for (i = 0; i < roleStore.getCount(); i++) {
			var r = roleStore.getAt(i);
			if (r.data.role.roleId == selectNode.id) {
				isRe = true;
			}
		}
		if (isRe == true) {

			Ext.ux.Toast.msg("信息", "部门重复，请选择其它部门！");
			return;

		}


		var recrod = new roleStore.recordType();

		recrod.data = {};
		recrod.data.isMain = 0;

		Ext.apply(recrod.data, {
					roleId : selectNode.id,
					roleName : selectNode.text
				});

		roleStore.insert(0, recrod);

		roleStore.commitChanges();
		selectNode = null;

	};
	var del = function() {

		var selectRecords = RoleGrid.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}

		if (selectRecords[0].data.roleId != null
				&& selectRecords[0].data.roleId != '') {
			dellTrue(selectRecords[0].data.roleId);
		}

		roleStore.remove(selectRecords[0]);
		roleStore.commitChanges();

	};
	var selectPanel = new Ext.Panel({
				frame : false,
				border : false,
				hideBorders : true,
				height : 220,
				flex : 0.4,
				layout : {
					type : 'vbox',
					pack : 'center',
					align : 'stretch'
				},
				defaults : {
					anchor : '96%,96%'
				},
				defaults : {
					margins : '0 3 0 0'
				},
				items : [{
							xtype : 'button',
							iconCls : 'btn-right',
							scope : this,
							handler : add
						}, {
							xtype : 'button',
							iconCls : 'btn-left',
							scope : this,
							handler : del
						}]
			});
	/* 职位grid */
	var dellAll = function() {

		// jobStore.removeAll();
		var ids = Array();
		for (i = 0; i < roleStore.getCount(); i++) {
			var r = roleStore.getAt(i);
			if (r.data.roleId != null && r.data.roleId != '') {
				ids.push(r.data.roleId);
			};

		}
		if (ids.length > 0) {
			dellTrue(ids);

		}
		roleStore.removeAll();
		roleStore.commitChanges();

	};
	var gridTopbar = new Ext.Toolbar({
				items : [{
							text : '清除所选',
							scope : this,
							handler : dellAll
						}]
			});

	var roleRecord = Ext.data.Record.create([{
				name : 'roleId',
				type : 'int'
			}, 'roleName', 'appUser']);

	var gridMemoryProxy = new Ext.data.HttpProxy({
				url : __ctxPath + '/system/selectedRolesAppUser.do'
			});

	var gridJsonReader = new Ext.data.JsonReader({
				root : 'result',
				totalProperty : 'totalCounts',
				idProperty : "roleId"
			}, roleRecord);

	var roleStore = new Ext.data.Store({
				id : 'AppUserForm.RoleStore',
				proxy : gridMemoryProxy,
				reader : gridJsonReader
			});

	roleStore.on('beforeload', function(store) {
				store.baseParams = {
					start : 0,
					limit : 10000,
					userId : userId
				};
			});
	roleStore.setDefaultSort('roleId');

	if (userId != '' && userId != null && userId != 'undefined') {
		roleStore.load();
	}

	var sm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true

			});
	var RoleGrid = new Ext.grid.EditorGridPanel({
				frame : false,
				border : true,
				flex : 6,
				height : 220,
				tbar : gridTopbar,
				store : roleStore,
				clicksToEdit : 1,
				sm : sm,
				viewConfig : {
					forceFit : true,
					autoFill : true
				},
				columns : [{
							header : 'roleId',
							dataIndex : 'roleId',
							sortable : true,
							hidden : true
						}, {
							header : '角色',
							sortable : true,
							dataIndex : 'roleName'
						}
				]
			});
	Ext.QuickTips.init();

	RoleGrid.on('dblclick', function(e) {

				var selectRecords = RoleGrid.getSelectionModel().getSelections();
				if (selectRecords.length == 0) {
					Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
					return;
				}

				if (selectRecords[0].data.roleId != null
						&& selectRecords[0].data.roleId != '') {
					dellTrue(selectRecords[0].data.roleId);
				}

				roleStore.remove(selectRecords[0]);
				roleStore.commitChanges();

			}, this);
	RoleTree.on('dblclick', function(selectNode) {
				if (selectNode == null) {
					Ext.ux.Toast.msg("信息", "请选择要添加的部门！");
					return;
				}

				var isRe = false;
				for (i = 0; i < roleStore.getCount(); i++) {
					var r = roleStore.getAt(i);
					if (r.data.roleId == selectNode.id) {
						isRe = true;
					}
				}
				if (isRe == true) {

					Ext.ux.Toast.msg("信息", "角色重复，请选择其它角色！");
					return;

				}

				var recrod = new roleStore.recordType();

				recrod.data = {};

				Ext.apply(recrod.data, {
							roleId : selectNode.id,
							roleName : selectNode.text
						});

				roleStore.insert(0, recrod);
				roleStore.commitChanges();
				selectNode = null;
			}, this);

	
	/* 总容器 */

	var panel = new Ext.Panel({
				xtype : 'panel',
				height : 220,
				border : false,
				layout : {
					type : 'hbox',
					align : 'stretch'
				},
				height : 220,
				items : [RoleTree, selectPanel, RoleGrid
				]
			});

	return panel;
}
