/**
 * 流程执行下一步总入口,需要传入任务id，当前节点名称 
 * @class ProcessNextForm
 * @extends Ext.Panel
 */
ProcessNextForm=Ext.extend(Ext.Panel,{
	constructor:function(config){
		//若当前任务尚未分配该用户，则该用户进入该任务时，
		//需要弹出一个对话框架告诉他正在锁定该任务执行
		Ext.applyIf(this,config);
		var flag=false;
		Ext.Ajax.request({
			params:{
				taskId:this.taskId
			},
			async : false,
			scope:this,
			url:__ctxPath+ "/flow/checkTask.do",
			success:function(response,options){
				//若当前任务已经被其他人员执行或已经执行完成等
				var result=Ext.util.JSON.decode(response.responseText);
				if(!this.agentTask){//若该任务没有被代理
					if(result.assigned!=undefined){
						if(!result.assigned){
							Ext.ux.Toast.msg('操作信息','该任务已经被其他用户锁定执行！');
							flag=true;
						}else if(result.assigned){
							Ext.ux.Toast.msg('操作信息','该任务已经成功锁定!');
						}
					}
				}
				this.isSubFlow=result.isSubFlow?result.isSubFlow:false;
			}
		});
		
		if(flag){
		  var taskPanel=Ext.getCmp('TaskPanelView');
		  if(taskPanel!=null&&taskPanel!=undefined){
		       taskPanel.getUpdater().update(__ctxPath+ '/flow/displayTask.do');
		  }
		  //ProcessNextForm.superclass.constructor.call(null);
		  return;
		}
		
		
		//下一授予任务名
		this.assignTasks=new Array();
		//下一任务用户名
		this.assignUserIds=new Array();
		
		this.formPanel=new Ext.FormPanel({
			//layout:'table',
			border:false,
			bodyStyle:'padding:8px',
			autoHeight:true,
			autoLoad:{
				url:__ctxPath+ "/flow/getProcessActivity.do?taskId="+this.taskId,
				nocache: true,
				params:{activityName:this.activityName},
				scope:this,
				callback:this.getFormHtmlCallback
			}
		});
		
		// 显示流程审批的表单
		this.flowdetailPanel=new Ext.Panel({
			border:false,
			autoHeight:true,
			autoLoad:{
				url:__ctxPath+'/flow/processRunDetail.do?taskId='+this.taskId,
				nocache: true
			}
		});
				
		//用户选择的Panel，为下一节点进行人员选择
		this.userJumpPanel=new Ext.form.FieldSet({
					title:'选择下一任务执行人',
					collapsed: true,
					autoHeight:true,
					collapsed: false,
					collapsible : true,
					layout:'form'
		});
		
		//加载跳转的按钮
		this.jumpPanel=new Ext.Panel({
			border:false,
			bodyStyle:'padding:8px',
			layout:'form',
			items:[
				this.userJumpPanel
			]
		});
		
		this.toolbar=new Ext.Toolbar({
			items:[' ',' ',{
				xtype:'checkbox',
				boxLabel:'自由跳转',
				scope:this,
				handler:this.freeJump
			},'-',{
				xtype:'button',
				text:'执行下一步',
				scope:this,
				iconCls:'btn-transition',
				handler:this.nextStep
			},'-',{
				xtype:'checkbox',
				boxLabel:'发送邮件',
				scope:this,
				handler:function(ck,checked){
					if(checked){
						this.sendMail=true;
					}else{
						this.sendMail=false;
					}
				}
			},{
				xtype:'checkbox',
				boxLabel:'发送短信',
				scope:this,
				handler:function(ck,checked){
					if(checked){
						this.sendMsg=true;
					}else{
						this.sendMsg=false;
					}
				}
			},'->','-',{
				text:'打印',
				iconCls:'btn-print',
				scope:this,
				handler: function(){
    				gpObj = document.getElementById('ProcessNextForm_' + this.taskId);
    				printFlag = true;
    				
    				fckeditorIframe = null;
    				var iframes = gpObj.getElementsByTagName("iframe");
    				for(var i=0;i<iframes.length;i++){
    					if(iframes[i].id.indexOf("fckeditor")!=-1){
    						fckeditorIframe = iframes[i];
    					}
    				}
    				
		  			window.open(__ctxPath+'/js/printer/Print.jsp');
				}
			},'->','-',{
				text:'流程示意图',
				iconCls:'btn-flow-chart',
				scope:this,
				handler:this.showFlowImage
			}
		]});		
		
		this.loadJumpTrans.call(this);
		
		//调用构造函数
		ProcessNextForm.superclass.constructor.call(this, {
			tbar:this.toolbar,
			id : 'ProcessNextForm_' + this.taskId,
			iconCls : 'btn-approvalTask',
			title : this.activityName+'--待办事项',
			layout:'form',
			bodyStyle:'padding:5px',
			items : [this.jumpPanel,this.formPanel,this.flowdetailPanel]
		});	
	},
	//加载当前任务节点的跳转路径
	loadJumpTrans:function(){
		//加载审批表单的功能按钮
	 	Ext.Ajax.request({
			url:__ctxPath+ "/flow/transProcessActivity.do",
			params:{taskId:this.taskId},
			scope:this,
			success:function(response,options){
				var object=Ext.decode(response.responseText);
				if(object.preTaskName!=undefined && object.preTaskName!=''){
					//加上驳回按钮
					if(!this.jumpBackButton){
						this.jumpBackButton=new Ext.Button({
								text:'驳回',
								iconCls:'btn-back',
								scope:this,
								handler:this.backFlow});
						this.toolbar.insert(3,new Ext.Toolbar.Separator());
						this.toolbar.insert(3,this.jumpBackButton);
						this.unAddBack=true;
						this.preTaskName=object.preTaskName;
					}
				}
				var radioItems=[];
				for(var i=0;i<object.data.length;i++){
					radioItems.push({
						boxLabel:object.data[i].destination,
						name:'jumpPath_'+this.taskId,
						inputValue:object.data[i].name,
						destType:object.data[i].destType,
						destName:object.data[i].destination,
						checked:i==0?true:false//缺省第一个选中
					});
				}
				this.jumpRadioGroup = new Ext.form.RadioGroup({
					listeners:{
						scope:this,
						'change':this.jumpRadioCheck
					},
					fieldLabel : '执行路径',
					items : radioItems
				});

				this.jumpPanel.insert(0,this.jumpRadioGroup);
				this.jumpPanel.doLayout();
				//以加载相应的人员
				this.jumpRadioCheck.call(this);
				//加上会签的投票
				
				if(object.isSignTask){
					this.addSignVoteOpinion.call(this);
				}
			}
	   });
	},
	/**
	 * 加上会签意见
	 */
	addSignVoteOpinion:function(){
		
		this.voteRadioGroup = new Ext.form.RadioGroup({
			columns: [100, 100,100],
            vertical: true,
			items : [
			{
				xtype:'radio',
				name:'signVoteType',
				boxLabel:'同意',
				inputValue:1,
				checked:true
			},{
				name:'signVoteType',
				xtype:'radio',
				boxLabel:'拒绝',
				inputValue:2
			},{
				xtype:'radio',
				name:'signVoteType',
				boxLabel:'弃权',
				inputValue:0
			}
			]
		});
		this.voteCmpField=new Ext.form.CompositeField({
			width:540,
			autoHeight:true,
			fieldLabel:'会签投票意见',
			items:[
				this.voteRadioGroup
			]
		});
		//显示会签情况
		this.voteListPanel=new Ext.Panel({
			border:false,
			autoHeight:true,
			autoLoad:{
					url:__ctxPath+ "/flow/signListProcessActivity.do?taskId="+this.taskId,
					nocache: true
			}
		});
		
		this.voteSignFieldSet=new Ext.form.FieldSet({
			title:'会签情况',
			autoHeight:true,
			layout:'form',
			items:[
				this.voteListPanel,
				this.voteCmpField
			]
		});
	},
	
	/**
	 * 单选项按钮点击
	 * @param {} rd
	 * @param {} ckradio
	 */
	jumpRadioCheck:function(radioGp,radio){
		
		if(!radioGp){
			radioGp=this.jumpRadioGroup;
		}
		if(!radio){
			radio=radioGp.getValue();
		}
		//加上下一任务执行人
		this.getTaskUsers.call(this,radio.destName,radio.destType);
		//加上下一任务限定时间
		this.getDueDatePanel.call(this,radio.destType);
	},
	
	//到期设置日期
	getDueDatePanel:function(destType){
		//设置默认设置为4h
		if(!this.dueDatePanel){
			var curDate=new Date();
			curDate.setHours(curDate.getHours()+4);
			this.dueDatePanel=new Ext.Panel({
				layout:'form',
				width:400,
				border:false,
				items:[
					{
						xtype:'datetimefield',
						fieldLabel:'完成限定时间',
						name:'dueDate',
						width:240,
						format: 'Y-m-d H:i:s',
						value:curDate
					}
				]
			});
			this.jumpPanel.add(this.dueDatePanel);
			this.jumpPanel.doLayout();
		}
		
		if(destType=='task'){
			this.dueDatePanel.show();
		}else{
			this.dueDatePanel.hide();
		}
		
	},
	/**
	 * 取得下一节点对应的处理人员
	 * @param {} destName 目标节点的名称
	 * @param {} destType 目标节点的类型
	 */
	getTaskUsers:function(destName,destType){
		
		//下一节点为分支及fork节点
		if('decision'==destType || 'fork'==destType || 'join'==destType){
			this.userJumpPanel.removeAll();
			this.userJumpPanel.show();
			this.genForkDecUserAssign.call(this,destName);
		}else if(destType.indexOf('end')!=-1 ){//下一节点为结束节点，需要隐藏下一步的执行人	
			
			//若为结束节点，需要考虑父流程的处理方式，所以在这里，需要判断一下，当前流程是否为子流程，若是的话，则需要加载其跳回父流程的路径
			if(this.isSubFlow){
				this.getOutToParentFlow.call(this);
			}
			this.userJumpPanel.removeAll();
			this.userJumpPanel.hide();
		}else{//下一节点为普通任务节点
			this.userJumpPanel.removeAll();
			this.userJumpPanel.show();
			this.userJumpPanel.add(this.getSingleUserPanel.call(this,destName));
		}
		this.jumpPanel.doLayout();

	},
	
	getParentFlowTaskUsers:function(destName,destType){
		//下一节点为分支及fork节点
		if('decision'==destType || 'fork'==destType || 'join'==destType){
			this.userJumpPanel.removeAll();
			this.userJumpPanel.show();
			this.genForkDecUserAssign.call(this,destName,true);
		}else if(destType.indexOf('end')!=-1 ){//下一节点为结束节点，需要隐藏下一步的执行人	
			//TODO
			this.userJumpPanel.removeAll();
			this.userJumpPanel.hide();
			
		}else{//下一节点为普通任务节点
			this.userJumpPanel.removeAll();
			this.userJumpPanel.show();
			this.userJumpPanel.add(this.getSingleUserPanel.call(this,destName,true));
			
		}
	},
	
	/**
	 * 
	 */
	parentJumpPathComboSelect:function(combo,record,index){
		var  destName=record.data.destination;
		var destType=record.data.destType;
		
		 this.getParentFlowTaskUsers.call(this,destName,destType);
		 this.userJumpPanel.doLayout();
	},
	
	/**
	 * 
	 */
	getOutToParentFlow:function(){
		this.parentJumpPathCombo=new Ext.form.ComboBox({
			allowBlank:true,
			store:new Ext.data.JsonStore({
				autoLoad:true,
				url:__ctxPath+'/flow/parentTransProcessActivity.do?taskId='+this.taskId,
				fields:['destType','destination','name'],
				root:'data'
			}),
			fieldLabel:'跳至父流程路径',
			mode : 'local',
			editable : false,
			triggerAction : 'all',
			displayField:'destination',
			valueField:'name',
			listeners:{
				scope:this,
				'select':this.parentJumpPathComboSelect
			}
		});
		this.jumpPanel.insert(1,this.parentJumpPathCombo);
		this.jumpPanel.doLayout();
	},
	/**
	 * 下一步仅有一个任务，即仅需要为一个任务赋予人员
	 * @param {} isParentFlow 是否取得该任务对应的外围父流程的跳转分支的人员指派
	 * @return {}
	 */
	getSingleUserPanel:function(destName,isParentFlow){
		this.flowAssignName=new Ext.form.TextArea({
								width:400,
								height:40,
								name:'flowAssignName'
							});
		
		var singleUserField=new Ext.form.CompositeField({
					xtype:'compositefield',
					fieldLabel:'执行人',
					anchor:'92%,92%',
					items:[
							this.flowAssignName,
							{
								xtype:'button',
								scope:this,
								text:'...',
								iconCls : 'btn-users',
								handler:function(){
									var formPanel=this.flowUserFieldPanel;
									UserSelector.getView(
										{
											callback:function(uId,uname){
												this.flowAssignName.setValue(uname);
												this.assignTasks=[destName];
												this.assignUserIds=[uId];
											},
											scope:this
										}).show();
								}
							}
					]
		});
		Ext.Ajax.request({
			url:__ctxPath+'/flow/usersProcessActivity.do',
			scope:this,
			params:{
				taskId:this.taskId,
				activityName:destName,
				isParentFlow:isParentFlow
			},
			success:function(response,options){
				var result=Ext.decode(response.responseText);
				this.flowAssignName.setValue(result.userNames);
				this.assignTasks=[destName];
				this.assignUserIds=[result.userIds];
			}
		});
		
		return singleUserField;
	},
	
	//为汇集或分支节点产生自由跳转的人员选择
	/**
	 * 
	 * @param {} destName
	 * @param {} isParentFlow 是否取得该任务对应的外围父流程的跳转分支的人员指派
	 */
	genForkDecUserAssign:function(destName,isParentFlow){
		Ext.Ajax.request({
			url:__ctxPath+'/flow/outerTransProcessActivity.do?taskId='+this.taskId,
			params:{
				nodeName:destName,
				isParentFlow:isParentFlow
			},
			scope:this,
			success:function(resp,options){
				//outers数据格式为[{singalName,activityName,destType},...]
				//如：[["to 总经理审阅","总经理审阅","task","1,2","张三,李四"],["to 财务审核","财务审核","task","1,2","张三,李五"]]
				var outers=Ext.decode(resp.responseText);
				
				for(var i=0;i<outers.length;i++){
					this.userJumpPanel.add(this.genUserFieldSel.call(this,outers[i]),i);
				}
				this.userJumpPanel.doLayout();
			}
		});
	},
	//产生用户选择
	genUserFieldSel:function(outers,idx){
		//目标节点名称
		var destName=outers[1];
		this.assignTasks[idx]=destName;
		this.assignUserIds[idx]=outers[3];
		
		var flowAssignUserName=new Ext.form.TextArea({allowBlank:false,width:400,height:40,value:outers[4]});
		var cmpField=new Ext.form.CompositeField({
			anchor:'92%,92%',
			bodyStyle: "background-color:white;padding:0 0 0 0",
			fieldLabel:destName,
			items:[
				flowAssignUserName,{
					xtype:'button',
					text:'...',
					iconCls : 'btn-users',
					scope:this,
					handler:function(){
						UserSelector.getView({
							scope:this,
							callback:function(uIds, uNames) {
								flowAssignUserName.setValue(uNames);
								//查找该数组中是否已经存在这个目标节点，若存在，则找到其坐标
								var index=this.assignTasks.length;
								for(var i=index-1;i>=0;i--){
									if(this.assignTasks[i]==destName){
										index=i;
										break;
									}
								}
								this.assignTasks[index]=destName;
								this.assignUserIds[index]=uIds;
							}
						}).show();
					}//end of handler
				}
			]
		});

		return cmpField;
								
	},
	/**
	 * 取得下一任务及其对应的人员
	 */
	getFlowAssignId:function(){
			//返回其格式如下：领导审批:财务审核:...|1,2:3,4:...),也只可为1,2,3(当下一步仅有一任务时）
			var flowAssignId='';
			var destTasks='';
			var destUserIds='';
			for(var i=0;i<this.assignTasks.length;i++){
				if(i>0){
					destTasks+=':';
					destUserIds+=':';
				}
				destTasks+=this.assignTasks[i];
				destUserIds+=this.assignUserIds[i];
			}
			if(destTasks!=''){
				flowAssignId=destTasks+'|'+destUserIds;
			}
			return flowAssignId;
	},
	
	/**
	 * 执行下一步
	 */
	nextStep:function(){
			var isValid=true;
			//加上对于表单的前置验证，允许在模板中加上自己的验证
			if(this.formExtPanel!=null&& this.formExtPanel.validate){
				isValid=this.formExtPanel.validate.call(this.formExtPanel,this);
			}else{
				isValid=$validForm.call(this);
			}
			
			if(!isValid) return;
			
			var signalName='';
			var destName='';
			var signVoteType=null;
			if(this.isFreeJump){//若为自由跳转
				signalName=this.freeTransCombo.getValue();
				var store=this.freeTransCombo.getStore();
				for(var i=0;i<store.getCount();i++){
					var data=store.getAt(i).data;
					if(data.signalName==signalName){
						destName=data.destName;
						break;
					}
				}
			}else{//正规按流程线路跳转
				var selectedItem=this.jumpRadioGroup.getValue();
				signalName=selectedItem.getGroupValue();
				destName=selectedItem.destName;
			}
			
			if(destName==''){
				Ext.ux.Toast.msg('操作信息','请选择要跳转的目标任务！');
				return;
			}
			//是为会签
			if(this.voteRadioGroup){
				signVoteType=this.voteRadioGroup.getValue().getGroupValue();
			}
			var form=this.formPanel.getForm();
			//modify by lyy start
			/**
			 * @author lyy
			 * @description
			 *  取得表单里面的OFFICE控件面板，保存文档，再把文档ID返回给表单字段
			 */
			var officePanel=this.officePanel;
			if(officePanel){
			   var obj=null;
			   if(this.fileId!=''&&this.fileId!=undefined){
	     	 	    obj=officePanel.saveDoc({docName:'ProcessDocument',fileId:this.fileId,doctype:'doc'});
	     	   }else{
	     	        obj=officePanel.saveDoc({docName:'ProcessDocument',doctype:'doc'});
	     	   }
	     	   if(obj&&obj.success){
					var fileId=obj.fileId;
					this.hiddenF.setValue(fileId);
			   }
			}

			//设置flowAssignId,用于指定下一任务的执行人
			var flowAssignId='';
			if(this.formExtPanel!=null && this.formExtPanel.getFlowAssignId){//若在模板中指定了下一步的执行人员
				flowAssignId=this.formExtPanel.getFlowAssignId.call(this.formExtPanel,this);
			}else{
				flowAssignId=this.getFlowAssignId.call(this);
			}
			
			var baseParams={
					useTemplate:this.useTemplate,
					signVoteType:signVoteType,
					flowAssignId:flowAssignId,
					taskId:this.taskId,
					signalName:signalName,
					destName:destName,
					sendMsg:this.sendMsg,
					sendMail:this.sendMail
			};
			//设置下一任务执行的限定完成时间
			if(this.dueDatePanel.show()){
				var dueDate=this.dueDatePanel.getCmpByName('dueDate').getValue();
				baseParams.dueDate=$formatDate(dueDate);
			}
			//设置跳回父流程的路径
			if(this.parentJumpPathCombo){
				if(this.parentJumpPathCombo.getValue()==''){
					Ext.ux.Toast.msg('操作信息','请选择回跳至父流程的节点!');
					return;
				}
				baseParams.toParentPath=this.parentJumpPathCombo.getValue();
			}		
			
			if(this.detailGrids){//适用于多个GRID的
				var grids=this.detailGrids.keys;
				for(var j=0;j<grids.length;j++){
					var details=[];
					var detailPanel=this.detailGrids.get(grids[j]);
				    var store=detailPanel.getStore();
					for(var i=0;i<store.getCount();i++){
						var record=store.getAt(i);
						var d=HT.encode(record.data);
						details.push(d);
					}
					baseParams[grids[j]+'details']=Ext.encode(details);
				}
			}
            var dom=form.getEl().dom;
            //取得表单里面的子表单
            var forms=dom.getElementsByTagName('form');
            var dv=[];
            var detailsMap=new Ext.util.MixedCollection();
            for(var i=0;i<forms.length;i++){
            	 var belongName=forms[i].getAttribute('belongName');
            	 var pkName=forms[i].getAttribute('pkName');
            	 var pkValue=forms[i].getAttribute('pkValue');
	             var baseParam2 = Ext.Ajax.serializeForm(forms[i]);
	             var deParams = Ext.urlDecode(baseParam2);//取得了从表里面的数据
	             //进行数据组装
	             if(pkName&&pkValue){
	                deParams[pkName]=pkValue;
	             }
	             var dd=HT.encode(deParams);
	             
	             var tt=detailsMap.get(belongName);
	             if(!tt){
	             	 var details=[];
	             	 details.push(dd);
	                 detailsMap.add(belongName,details);
	             }else{
	                 tt.push(dd);
	             }
	        }
	        
	        for(var i=0;i<detailsMap.keys.length;i++){
	        	var keyName=detailsMap.keys[i];
	            baseParams[keyName+'details']=Ext.encode(detailsMap.get(keyName));
	        }
            
			if(form.isValid()){//是合法有效
				form.submit({
					url:__ctxPath+ "/flow/nextProcessActivity.do",
					method:'post',
					waitMsg:'正在提交处理，请稍等',
					scope:this,
					params:baseParams,
					success : function(fp, action) {
						Ext.ux.Toast.msg('操作信息','成功保存！');
						AppUtil.removeTab('ProcessNextForm_'+this.taskId);
						var myTaskGrid=Ext.getCmp("MyTaskGrid");
						var appHomeTaskGrid=Ext.getCmp('TaskPanelView');
						if(appHomeTaskGrid!=null){
							appHomeTaskGrid.getUpdater().update(__ctxPath+ '/flow/displayTask.do');
						}
						if(myTaskGrid!=null){
							myTaskGrid.getStore().reload();
						}
						if(officePanel){
						   officePanel.closeDoc();
						}
					},
					failure : function(fp, action) {
						Ext.ux.Toast.msg('操作信息','操作出错，请联系管理员！');
					}
				});
			}// 
		
			
	},//end of next step
	
	/**
	 * 选中或未选中自由跳转选项
	 * @param {} ck
	 * @param {} checked
	 */
	freeJump:function(ck,checked){
		if(checked){
			//自由跳转
			this.isFreeJump=true;
			this.jumpPanel.remove(this.jumpRadioGroup);
			this.freeTransCombo=new Ext.form.ComboBox({
						fieldLabel:'跳转任务',
						xtype:'combo',
						allowBlank:false,
						editable : false,
						lazyInit: false,
						//anchor:'96%,96%',
						triggerAction : 'all',
						listeners:{
								scope:this,
								select : function(combo,record,index){
									var destName=record.data.destName;
									var destType=record.data.destType;
									this.getTaskUsers.call(this,destName,destType);
								} 
						},
						store : new Ext.data.ArrayStore({
								autoLoad : true,
								url : __ctxPath + '/flow/freeTransProcessActivity.do?taskId='+this.taskId,
								fields : ['signalName', 'destName','destType']
						}),
						displayField : 'destName',
						valueField : 'signalName'
			});
			this.jumpPanel.insert(0,this.freeTransCombo);
			this.jumpPanel.doLayout();
		}else{
			//非自由跳转
			this.isFreeJump=false;
			this.jumpPanel.remove(this.freeTransCombo);
			this.loadJumpTrans.call(this);
		}
	},
	//流程退回
	backFlow:function(){
		Ext.Msg.confirm('信息确认', '您确认要回退所选记录吗？', function(btn) {
			if (btn == 'yes') {
					this.formPanel.getForm().submit({
							url:__ctxPath+ "/flow/nextProcessActivity.do",
							scope:this,
							params:{
								useTemplate:this.useTemplate,
								taskId:this.taskId,
								destName:this.preTaskName,
								//回退标志
								back:'true'
							},
							success:function(response,options){
								Ext.ux.Toast.msg('操作信息','成功回退！');
								AppUtil.removeTab('ProcessNextForm_'+this.taskId);
							},
							failture:function(response,options){
								Ext.ux.Toast.msg('操作信息','回退失败！');
							}	
						}
					);
				}//end if
			},//end of function
			this
		);
	},
	//加载流程业务表单回调函数，用于业务数据回调，加上验证，业务字段的权限控制
	getFormHtmlCallback:function(){
		
		//加上投票的意见
		if(this.voteSignFieldSet){
			this.formPanel.add(this.voteSignFieldSet);
		}
		//加上可以审批的意见
		this.formPanel.add(new Ext.form.TextArea({
			name:'comments',
			anchor:'70%,70%',
			fieldLabel:'审批意见'
		}));
		//使用自定义Ext模板表单
		var formExt=document.getElementById('formTaskExt'+this.taskId);
		if(formExt!=null){
			//加上标识，表示是使用EXT模板进行
			this.useTemplate=true;
			
			var valExt=formExt.value;
			
			valExt=valExt.replace('Ext.form.FormPanel','Ext.Panel');
			
			var vmParams='{taskId:'+this.taskId+'}';
			
			this.formExtPanel=eval('new ('+valExt+')('+vmParams+');');
			
			if(this.formExtPanel.afterLoad){
				this.formExtPanel.afterLoad.call(this.formExtPanel,this);
			}
			
			this.formExtPanel.outerFormPanel=this.formPanel;
			this.formPanel.add(this.formExtPanel);
			this.formPanel.doLayout();
			
			return;
		}
		this.formPanel.doLayout();

		try{
			var json=document.getElementById('entity_'+this.taskId);
			var rights=document.getElementById('rightstask_'+this.taskId);
			var name,type,value,xtype;
		    //加载JS回调函数
			var callback=function(){
				    var entityJson=null;
					if(json!=null&&json.value){
						entityJson=Ext.decode(json.value);
					}
	        	    var rightJson=null;
					if(rights!=null){
					    rightJson=Ext.decode(rights.value);
					}
			        $converDetail.call(this,entityJson,rightJson);
	        	};
	        //后加载文档的JS
        	$ImportSimpleJs([
        	     __ctxPath+'/js/core/ntkoffice/NtkOfficePanel.js',
        	     __ctxPath + '/js/selector/SealSelector.js',
	             __ctxPath + '/js/selector/PaintTemplateSelector.js'
        	 ],callback,this);
			
		}catch(e){
		}
	},
	
	/**
	 * 显示流程图
	 */
	showFlowImage:function(){
		
		
		var flowImagePanel=new Ext.Panel({
			autoHeight:true,
			border:false,
			html:'<img src="'+__ctxPath+ '/jbpmImage?taskId='+this.taskId+ '&rand=' + Math.random()+ '"/>'
		});
		
		var panel=new Ext.Panel({
				autoHeight:true,
				layout:'form',
				border:false,
				items:[
					flowImagePanel
				]
			}
		);
		
		if(this.isSubFlow){//若当前为子流程，则显示子流程
			panel.add({
				xtype:'panel',
				autoHeight:true,
				border:false,
				html:'<img src="'+__ctxPath+ '/jbpmImage?taskId='+this.taskId+ '&isSubFlow=true&rand=' + Math.random()+ '"/>'
			});
			panel.doLayout();
		}
		
		new Ext.Window({
			autoScroll:true,
				iconCls:'btn-flow-chart',
				bodyStyle:'background-color:white',
				maximizable : true,
				title:'流程示意图',
				width:800,
				height:600,
				modal:true,
				layout:'fit',
				items:panel
		}).show();
	}
});
