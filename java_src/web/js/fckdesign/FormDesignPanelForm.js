/** 
 * 
 * @class FormDesignPanelForm
 * @extends Ext.Panel   表单制作向导
 */

Ext.ns('Ht');
Ht.detailGrid = Ext.extend(Ext.grid.EditorGridPanel,{
        constructor:function(_cfg){
           Ext.applyIf(this,_cfg);
           this.parentConf=this.parentConf;
           this.initUI();
           Ht.detailGrid.superclass.constructor.call(this,{
                stripeRows : true,
                tbar:this.toolbar,
				store : this.store,
				width:800,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				clicksToEdit : 1,
				anchor: '100% -53',
				autoScroll:true,
				cm : this.cm,
				sm:this.sm,
				viewConfig : {
					autoFill : true
				},
				listeners:{
					scope:this,
					'render':function(grid){
						grid.dragZone=new Ext.ux.dd.GridDragZone(grid,{});
            	        grid.dropZone=new Ext.ux.dd.GridDropZone(grid,{});
					},
				    'rowclick':function(grid,index,e){
				          this.clickRow=index;
				    },
				    'beforeedit' :function(obj){
				       var record=obj.record;
				       if(record.data.isDesignShow!=3){
				          if(obj.field=='fieldName'||obj.field=='fieldLabel'||
				          obj.field=='fieldSize'||obj.field=='fieldType'||
				          obj.field=='isRequired'||obj.field=='showFormat'||obj.field=='isPrimary'){
				              return false;
				          }
				       }
				    }
				}
           
           });
        },
        initUI:function(){
           var fields=[{
						name : 'fieldId',
						type : 'int'
					},'fieldName','fieldType','fieldLabel','isRequired','fieldSize','fieldDscp','isPrimary','foreignKey','foreignTable','isList','isQuery','isFlowTitle','showFormat','isDesignShow'];
           if(this.isMainTable){
               fields=[{
					name : 'fieldId',
					type : 'int'
				},'fieldName','fieldType','fieldLabel','isRequired','fieldSize','fieldDscp','isPrimary','isList','isQuery','isFlowTitle','showFormat','isDesignShow'];
           }
           this.store = new Ext.data.JsonStore({
					  fields : fields,
		              remoteSort : false
			});
			this.store.setDefaultSort('fieldId', 'asc');
			this.toolbar = new Ext.Toolbar({
			    items:[{
			       xtype:'button',
			       text:'添加字段',
			       iconCls:'btn-add',
			       handler:this.addField,
			       scope:this
			    },{
			       xtype:'button',
			       text:'删除字段',
			       iconCls:'btn-del',
			       handler:this.delField,
			       scope:this
			    
			    }]
			});
			
			this.sm = new Ext.grid.CheckboxSelectionModel();
			var columns=[this.sm,
				new Ext.grid.RowNumberer(), 
					{
							header : 'fieldId',
							dataIndex : 'fieldId',
							hidden : true
					},{
					       header : '字段KEY',
					       width:100,
						   dataIndex : 'fieldName',
					       editor:new Ext.form.TextField({
					           listeners:{
							   	  scope:this,
							      'change':this.changeForeignKey
							   }
					       })
					},{
					       header : '字段标签',
					       width:100,
						   dataIndex : 'fieldLabel',
					       editor:new Ext.form.TextField()
					},{
					      header:'字段长度',
					      width:100,
					      dataIndex:'fieldSize',
					      xtype:'numbercolumn',
					      format:'0',
					      editor:new Ext.form.TextField()
					},{
					      header:'字段类型',
					      width:100,
					      dataIndex:'fieldType',
					      editor:new Ext.form.ComboBox({
					      	  typeAhead: true,
							  triggerAction: 'all',
							  lazyRender:true,
							  mode: 'local',
							  store : ['varchar', 'bigint','smallint','int','text','date','datetime','double','float','char','decimal','file'],
							  listeners:{
							   	  scope:this,
							      'change':this.changeMainTablePrimaryKey
							  }
					      })
					},{
					      header:'显示格式',
					      width:100,
					      dataIndex:'showFormat',
					      editor:new Ext.form.TextField()
					},{
					      header:'是否主键',
					      width:100,
					      dataIndex:'isPrimary',
					      xtype:'templatecolumn',
					      tpl:new Ext.XTemplate(
					          '<p><input type="checkbox" <tpl if="isPrimary ==1">checked="checked"</tpl> onclick="return false;" class="x-form-checkbox x-form-field"/></p>'
					      ),
					      editor:{
					         xtype:'checkbox',
					         listeners:{
					         	scope:this,
					            'change' : function(comp,checked){
					            	this.fireEvent('clickPrimary',{
					            	    //mainGrid:this.editGrid1,
					            	    isMainTable:this.isMainTable==true?true:false,
					            	    curGrid:this,
					            	    clickIndex:this.clickRow,
					            	    checked:checked
					            	});
					            }
					         
					         }
					      }
					
					}
				];
			if(this.isMainTable==true){
			    columns.push({
					      header:'是否为节点标题',
					      width:100,
					      dataIndex:'isFlowTitle',
					      xtype:'templatecolumn',
					      tpl:new Ext.XTemplate(
					          '<p><input type="checkbox" <tpl if="isFlowTitle ==1">checked="checked"</tpl> onclick="return false;" class="x-form-checkbox x-form-field"/></p>'
					      ),
					      editor:{
					         xtype:'checkbox',
					         listeners:{
					            scope:this,
					            'change':this.changeTitle
					         }
					      }
					});
            }
			columns.push({
					      header:'是否必填',
					      width:100,
					      dataIndex:'isRequired',
					      xtype:'templatecolumn',
					      tpl:new Ext.XTemplate(
					          '<p><input type="checkbox" <tpl if="isRequired ==1">checked="checked"</tpl> onclick="return false;" class="x-form-checkbox x-form-field"/></p>'
					      ),
					      editor:{
					         xtype:'checkbox',
					         inputValue:1
					      }
					
					});
			columns.push({
					      header:'是否显示',
					      width:100,
					      dataIndex:'isList',
					      xtype:'templatecolumn',
					      tpl:new Ext.XTemplate(
					          '<p><input type="checkbox" <tpl if="isList ==1">checked="checked"</tpl> onclick="return false;" class="x-form-checkbox x-form-field"/></p>'
					      ),
					      editor:{
					         xtype:'checkbox',
					         inputValue:1
					      }
					
					});
			columns.push({
					      header:'是否查询',
					      width:100,
					      dataIndex:'isQuery',
					      xtype:'templatecolumn',
					      tpl:new Ext.XTemplate(
					          '<p><input type="checkbox" <tpl if="isQuery ==1">checked="checked"</tpl> onclick="return false;" class="x-form-checkbox x-form-field"/></p>'
					      ),
					      editor:{
					         xtype:'checkbox',
					         inputValue:1
					      }
					});
			if(this.isMainTable != true){
				columns.push({
						      header:'外键字段',
						      width:100,
						      dataIndex:'foreignKey',
						      editor:new Ext.form.ComboBox({
							      	typeAhead: true,
								    triggerAction: 'all',
								    mode: 'local',
								    store:new Ext.data.JsonStore({
					            	      fields : ['fieldName']
									}),
								    valueField: 'fieldName',
								    displayField: 'fieldName',
						      	    listeners:{
							      	 	 scope:this,
							      	     'focus':function(field){
							      	     	 var store=this.parentConf.editGrid1.getStore();
							      	     	 var store2=this.getStore();
							      	     	 var records=[];
							      	     	 for(var i=0;i<store.getCount();i++){
							      	     	     var rec=store.getAt(i);
							      	     	     var rec2=store2.getAt(this.clickRow);
							      	     	     if(rec.data.fieldType==rec2.data.fieldType){
							      	     	     	records.push(rec.data);
							      	     	     }
							      	     	 }
							      	     	 field.getStore().loadData(records);
							      	     },
							      	     'select':function(comb,record,index){
							      	         var store=this.getStore();
							      	         var rec=store.getAt(this.clickRow);
											 if(rec){
											 	var key=this.configPanel.getCmpByName('formTable.tableKey').getValue();
												rec.set('foreignTable',key);
											 }
							      	     	
							      	     }
						      	    }
								})
						});
				columns.push({
						      header:'关联表',
						      width:100,
						      dataIndex:'foreignTable'
						      ,
						      editor:new Ext.form.TextField()
						});
			}
		    this.cm = new Ext.grid.ColumnModel({
				columns : columns,
				defaults : {
					sortable : false,
					width: 100,
					fixed :true,
					menuDisabled : true
				}
			});
        },
        changeTitle:function(comp,checked){
        	if(this.isMainTable){
        		var checkindex=this.clickRow;
	        	var store=this.getStore();
	            if(checked){
		        	for(var i=0;i<store.getCount();i++){
		            	   var rec=store.getAt(i);
		            	   if(i!=checkindex){
			            	   if(rec.data.isFlowTitle==1||rec.data.isFlowTitle==true||rec.data.isFlowTitle=='1'||rec.data.isFlowTitle=='true'){
			            	   	  rec.set('isFlowTitle',0);
			            	   }
		            	   }else{
		            	       rec.set('isFlowTitle',1);
		            	   }
		        	}
		       }else{
		            var rec=store.getAt(checkindex);
		            rec.set('isFlowTitle',0);
		       }
        	}
        },
        changeMainTablePrimaryKey:function(field){
            if(this.isMainTable==true&&this.parentConf){
            	var primaryRecord=this.parentConf.primarykeyRecord;
            	if(primaryRecord){
            		for(var i=0;i<this.parentConf.SubTables.keys.length;i++){
		        	    var key=this.parentConf.SubTables.keys[i];
		        	    var grid=this.parentConf.dataStore.get(key+'-grid');
		        	    var store=grid.getStore();
		        	    var foreignKey=this.parentConf.findForeignKey(store,primaryRecord.data.fieldName);
		                if(foreignKey){foreignKey.set('fieldType',field.getValue());foreignKey.commit();}
		        	}
            	}
            }
        },
        changeForeignKey:function(field,newValue,oldValue){
           if(this.isMainTable==true&&this.parentConf){
	           for(var i=0;i<this.parentConf.SubTables.keys.length;i++){
	        	    var key=this.parentConf.SubTables.keys[i];
	        	    var grid=this.parentConf.dataStore.get(key+'-grid');
	        	    var store=grid.getStore();
	        	    var foreignKey=this.parentConf.findForeignKey(store,oldValue);
	                if(foreignKey){foreignKey.set('foreignKey',newValue);foreignKey.commit();}
	        	}
           }
        },
        addField:function(){//添加字段
            var store =this.store;
		    var Type = store.recordType;
		    var record = new Type();
		    record.set('isPrimary',0);
		    record.set('isList',0);
		    record.set('isQuery',0);
			record.set('isRequired',0);
			record.set('isDesignShow',3);
			record.set('isFlowTitle',0);
			store.insert(store.getCount(), record);
			this.getView().refresh();
        },
        delField:function(){//删除字段
            var selRs = this.getSelectionModel().getSelections();
            if(selRs.length==0){
            	Ext.ux.Toast.msg('操作信息','请选择要删除的字段！');
                return;
            }
            var store=this.getStore();
            for(var i=0;i<selRs.length;i++){
                var record=selRs[i];
                if(this.isMainTable&&record.data.isPrimary==1){
                   this.primarykeyRecord=null;
                   store.fireEvent('removePrimaryKey',{grid:this,record:record});
                }
            	store.remove(record);
            }
            this.getView().refresh();
        }

});
Ht.DetailFormPanel = Ext.extend(Ext.form.FieldSet,{
      constructor:function(_cfg){
         Ext.applyIf(this,_cfg);
         this.initUI();
         Ht.DetailFormPanel.superclass.constructor.call(this,{
             layout:'form',
             collapsible:true,
             collapsed:false,
//             minHeight:300,
        	 title:this.title,
        	 flex:1,
        	 items:[{
             	xtype:'hidden',
             	name:this.paramRoot+'.isMain',
             	value:0
             },{
                xtype:'hidden',
                name:this.paramRoot+'.tableId'
             },{
                xtype:'textfield',
                name:this.paramRoot+'.tableName',
                anchor: '100%',
                allowBlank:false,
                fieldLabel:'表名'
             },{
                 xtype:'textfield',
                 name:this.paramRoot+'.tableKey',
                 anchor: '100%',
                 value:this.paramRoot,
                 readOnly:true,
                 allowBlank:false,
                 fieldLabel:'表KEY'
             },this.editGrid
             ]
         
         });
      },
      initUI:function(){
      	 if(this.defaultGrid){
      	 	this.editGrid=this.defaultGrid;
      	 }else{
            this.editGrid=new Ht.detailGrid({
            	parentConf:this.parentConf
				});
      	 }
      	 
      	 this.editGrid.on('render',function(grid){
                grid.dragZone=new Ext.ux.dd.GridDragZone(grid,{});
            	grid.dropZone=new Ext.ux.dd.GridDropZone(grid,{});
         },this);
      },
      getEditGrid:function(){
         return this.editGrid;
      }

});
FormDesignPanelForm=Ext.extend(Ext.Window,{
        designPanel:null,
        showPanel:null,
        step:0,//执行步
        activePanel:null,//激活界面
        SubTables:new Ext.util.MixedCollection(),//保存HTML里面的明细表MAP
        MainTable:null,//主表
        editGrid1:null,//主表列表
        clickRow:-1,//点击的行数
        havadetailNo:0,//有子表的数目
        formTables:[],//表单对应的数据库表信息
        primarykeyRecord:null,//主表主键记录
        caStore:null,//新增字段缓存
        cacheTable:[],
        haveSub:false,//是否有从表
        subTableKeys:[],//子表的key
        isOldPublish:'',
        constructor:function(_cfg){
            Ext.applyIf(this,_cfg);
            this.initUI();
            FormDesignPanelForm.superclass.constructor.call(this,{
                  id:'FormDesignPanelForm',
	              title:'表单设计',
		          iconCls:'menu-form',
		          width : 850,
				  height : 500,
				  modal : true,
				  maximizable:true,
				  layout : 'vbox',
				  defaults:{
				     padding:'6'
				  },
				  layoutConfig:{
            	    align:'stretch'
            	  },
		          items:[this.designPanel,this.configPanel],
		          buttonAlign : 'center',
			      buttons:this.buttons
            });
        },
        initUI:function(){
        	/**
        	 * 用于保存每次新增字段的缓存
        	 */
        	this.caStore = new Ext.util.MixedCollection();
        	/**
        	 * 用于储存数据
        	 */
        	this.dataStore = new Ext.util.MixedCollection();
        	/**
        	 * 表单设计界面
        	 */
        	
        	this.radioGroup=new Ext.form.RadioGroup({
        	         fieldLabel:'表模式',
                     allowBlank:false,
                     anchor:'94%',
                     name:'formDef.formType',
                     items:[
	                     {
	                         name:'formDef.formType',
	                         boxLabel:'单表',
	                         inputValue:0
	                     }
//	                     ,{
//	                         name:'formDef.formType',
//	                         boxLabel:'主/从表',
//	                         inputValue:1
//	                     }
	                     ,{
	                         name:'formDef.formType',
	                         boxLabel:'一对多表',
	                         inputValue:1
	                     }
                     ]
        	});
            this.designPanel=new Ext.FormPanel({
            	 url:__ctxPath+'/flow/saveFormDef.do',
            	 layout:'form',
            	 region:'north',
            	 defaults:{
            	    //padding:'5 5 5 5',
            	    //anchor:'100%'
            	 },
            	 flex:1,
            	 autoScroll:true,
                 items:[{
                     xtype:'hidden',
                     name:'formDef.formDefId',
                     value:this.formDefId?this.formDefId:''
                 },{
                     xtype:'textfield',
                     name:'formDef.formTitle',
                     allowBlank:false,
                     fieldLabel:'表单名称',
                     anchor:'96%'
                 },{
                     xtype:'textarea',
                     name:'formDef.formDesp',
                     fieldLabel:'表单描述',
                     anchor:'96%'
                 }
                 ,{
                     xtype:'hidden',
                     name:'formDef.status',
                     fieldLabel:'表单状态',
                     value:0
                 },{
                 	xtype:'hidden',
                 	name:'formDef.isGen',
                 	value:0
                 }
                 ,this.radioGroup ,{
//                 	 fieldLabel:'设计区域',
                 	 hideLabel:true,
                 	 anchor:'96%',
                 	 height:496,
                     xtype:'fckdesigner',
                     allowBlank:false,
                     blankText:'请设计好表单!',
                     name:'formDef.defHtml'
                 }]
            });
            /**
             *激活的界面
             */
            this.activePanel=this.designPanel;
		    /**
		     * 主表的STORE
		     */
            
            this.editGrid2=new Ht.detailGrid({parentConf:this});
            this.editGrid1=new Ht.detailGrid({isMainTable:true,parentConf:this});
            this.editGrid1.on('clickPrimary',this.addPrimaryKey,this);
            this.mainTable=new Ext.form.FieldSet({
            	 layout:'form',
            	 title:'主表',
            	 border:true,
            	 flex:1,
                 items:[{
                 	xtype:'hidden',
                 	name:'formTable.isMain',
                 	value:1
                 },{
                    xtype:'hidden',
                    name:'formTable.tableId'
                 },{
                    xtype:'textfield',
                    allowBlank:false,
                    name:'formTable.tableName',
                    anchor: '100%',
                    fieldLabel:'表名'
                 
                 },{
                     xtype:'textfield',
                     name:'formTable.tableKey',
                     anchor: '100%',
                     allowBlank:false,
                     fieldLabel:'表KEY',
                     listeners:{
                     	'change':this.changeForeignTable,
                     	scope:this
                     }
                 },this.editGrid1]
            });
            this.belongTable = new Ext.form.FieldSet({
                 flex:1,
                 title:'子表',
                 layout:'form',
//                 layout:'vbox',
//            	 layoutConfig:{
//            	 	padding:'3 3 3 3'
//            	 	,
//            	    align:'stretch'
//            	 },
                 defaults:{
                 	anchor:'96% 100%'
//                 	padding:'8 20 8 8'
                 },
            	 autoScroll:true,
                 items:[]
            });
            
            this.configPanel=new Ext.FormPanel({
            	 hidden:true,
            	 flex:1,
            	 region:'center',
            	 layout:'hbox',
            	 layoutConfig:{
            	 	padding:'3 3 3 3',
            	    align:'stretch'
            	 },
                 items:[
                   this.mainTable,this.belongTable
                 ]
            });
            this.seebutton=new Ext.Button({
			    text:'预览',
			    iconCls:'btn-detail',
			    handler:this.checkDesign,
			    scope:this
		   });
        	this.savebutton=new Ext.Button({
        		hidden:true,
			    text:'保存',
			    iconCls:'btn-save',
			    handler:this.saveDrafRecord,
			    scope:this
		   });
		   this.publishbutton=new Ext.Button({
        		hidden:true,
			    text:'发布',
			    iconCls:'btn-save',
			    handler:this.publishRecord,
			    scope:this
		   });
    	   this.upbutton=new Ext.Button({
    	        hidden:true,
			    text:'上一步',
			    iconCls:'btn-save',
			    handler:this.upStep,
			    scope:this
    	   });
    	   this.nextbutton=new Ext.Button({
			    text:'下一步',
			    iconCls:'btn-save',
			    handler:this.nextStep,
			    scope:this
        	});
        	
        	this.buttons=[this.seebutton,this.savebutton,this.publishbutton,this.upbutton,this.nextbutton,{
			    xtype:'button',
			    text:'关闭',
			    iconCls:'btn-cancel',
			    scope:this,
			    handler:function(){
			        this.close();
			    }
			}];
			
			/**
			 * 这里在多表里面可以抽到下一步里面做，根据有多少个表，把这些数据储存起来
			 */
			 this.dataStore.add('mainGrid',this.editGrid1);
			 var mainstore=this.editGrid1.getStore();
        	 this.dataStore.add('mainGrid-store',mainstore);
//        	 mainstore.on('change',this.changeMainTablePrimaryKey,this);
			if (this.formDefId != null && this.formDefId != 'undefined') {
				    var formPanel=this.designPanel;
				    var self=this;
					this.designPanel.loadData({
								url : __ctxPath
										+ '/flow/getFormDef.do?formDefId='
										+ this.formDefId,
								root : 'data',
								preName : 'formDef',
								success:function(response,options){
								   var obj=Ext.util.JSON.decode(response.responseText).data;
								   formPanel.getCmpByName('formDef.defHtml').setValue(obj.defHtml);
								   self.formTables=obj.formTables;
								}
							});
			}else{
			    this.radioGroup.setValue(0);
			}
        },
        changeForeignTable:function(field,newValue,oldValue){
        	for(var i=0;i<this.SubTables.keys.length;i++){
        	    var key=this.SubTables.keys[i];
        	    var grid=this.dataStore.get(key+'-grid');
        	    var store=grid.getStore();
        	    var foreignKey=this.findForeignKey(store,this.primarykeyRecord.data.fieldName);
                if(foreignKey){foreignKey.set('foreignTable',newValue);foreignKey.commit();}
        	}
        },
//        changeForeignKey:function(field,newValue,oldValue){
//           for(var i=0;i<this.SubTables.keys.length;i++){
//        	    var key=this.SubTables.keys[i];
//        	    var grid=this.dataStore.get(key+'-grid');
//        	    var store=grid.getStore();
//        	    var foreignKey=this.findForeignKey(store,oldValue);
//                if(foreignKey){foreignKey.set('foreignKey',newValue);foreignKey.commit();}
//        	}
//        },
        checkDesign:function(){//预览效果
        	var html=this.designPanel.getCmpByName('formDef.defHtml').getValue();
            new DesignerWin({defHtml:html}).show();
        },
        findForeignKey:function(store,foreignKey){
        	if(foreignKey){
	            for(var i=0;i<store.getCount();i++){
	               var rec=store.getAt(i);
	               if(rec.data.foreignKey==foreignKey){
	                  return rec;
	               }
	            }
        	}
            return null;
        },
        findPrimaryKey:function(store){
            for(var i=0;i<store.getCount();i++){
               var rec=store.getAt(i);
               if(rec.data.isPrimary==1){
                  return rec;
               }
            }     
            return null;
        },
        addPrimaryKey:function(conf){
           /**
            * conf对象类型的属性包括：
            * 1、mainGrid主表列表
            * 2、subGrid从表列表,
            * 3、checked是否选择
            * 4、clickIndex主键所在的行数，
	        * 5、isMainTable是否为主表
            */
           var grid=conf.curGrid;//取得当前选中的grid
           var store=grid.getStore();
           var checkindex=conf.clickIndex;
           var orgKey,newKey;
           /**
            * 当未选上任何主键时，则orgKey为undefined,newKey为主键的记录
            * 当已经存在一个主键，则orgKey为原来主键的记录，newKey为新的主键记录。
            * 如果只是取消主键，则newKey为undefind,而orgKey为原主键的记录
            */
	       if(conf.checked){
	       	    /**
	       	     * 选上主键记录，并改变不是主键的记录
	       	     */
	        	for(var i=0;i<store.getCount();i++){
	            	   var rec=store.getAt(i);
	            	   if(i!=checkindex){
		            	   if(rec.data.isPrimary==1||rec.data.isPrimary==true||rec.data.isPrimary=='1'||rec.data.isPrimary=='true'){
		            	   	  rec.set('isPrimary',0);
		            	   	  orgKey=rec;
		            	   }
	            	   }else{
	            	       rec.set('isPrimary',1);
	            	       newKey=rec;
	            	       this.primarykeyRecord=rec;
	            	   }
	        	}
	       }else{
	            orgKey=store.getAt(checkindex);
	            orgKey.set('isPrimary',0);
	       }
	       if(this.haveSub&&conf.isMainTable){
		       for(var i=0;i<this.subTableKeys.length;i++){
		           var name=this.subTableKeys[i];
		           var subGrid=this.dataStore.get(name+'-grid');
		           this.addForeignKey(subGrid,newKey,orgKey);
		       }
	       }
        },
        addForeignKey:function(subGrid,newKey,orgKey){
           /**
	        * 给从表加上外键，如果存在相同的外键，则把外键修改,不存在，则把外键加上
	        * 影响的字段:fieldType,foreignKey,foreignTable
	        * 
	        */
	           var store=subGrid.getStore();
	           var mainTableName=this.configPanel.getCmpByName('formTable.tableKey').getValue();
	           for(var i=0;i<store.getCount();i++){
	              var r1=store.getAt(i);
	              if(orgKey&&r1.data.foreignKey&&r1.data.foreignKey==orgKey.data.fieldName){//当已经存在主键时，匹配一下外键
	                 if(r1.data.foreignTable==mainTableName||r1.data.foreignTable==''||r1.data.foreignTable==null){
		                 if(newKey){//之前已经存在主键，而选上另外的记录作为主键
		                 	r1.set('fieldType',newKey.data.fieldType);
		                 	r1.set('foreignKey',newKey.data.fieldName);
		                 	r1.set('fieldLabel','Foreign_Key');
		                 	if(mainTableName!=r1.data.foreignTable){
	                         	r1.set('foreignTable',mainTableName);
	                        }
	                       return;
		                 }else{//取消主表的主键
		                    store.remove(r1);
		                    subGrid.getView().refresh();
		                    return;
		                 }
	                 }
	              }
	           }
	           if(!orgKey&&newKey){//不存在主键，在从表插入一条外键记录
	                  this.addForeignKeyRecord(subGrid,newKey);
	                  return;
	           }
	           
        },
        addForeignKeyRecord:function(grid,rec){
            var store = grid.getStore();
            store.commitChanges();
            var fieldName=rec.data.fieldName;
            rec=rec.copy();
            rec.store=null;
            Ext.data.Record.id(rec);
            rec.set('fieldId',null);
            rec.set('fieldName','f'+fieldName);
            rec.set('foreignKey',fieldName);
            rec.set('fieldLabel','Foreign_Key');
            rec.set('isPrimary',0);
            rec.set('foreignTable',this.configPanel.getCmpByName('formTable.tableKey').getValue());
            rec.commit();
            try{
            	store.add(rec);
            }catch(e){}
            try{
            grid.getView().refresh();
            }catch(e){}
        },
        addPrimaryKeyRecord:function(grid,strKey){
            var store = grid.getStore();
		    var Type = store.recordType;
		    var record = new Type();
		    record.set('fieldName',strKey);
		    record.set('fieldType','bigint');
		    record.set('fieldLabel','Primary_Key');
		    record.set('isPrimary',1);
		    record.set('isList',0);
		    record.set('isQuery',0);
			record.set('isRequired',0);
			record.set('isDesignShow',3);
			record.set('isFlowTitle',0);
			store.insert(store.getCount(), record);
			try{
			grid.getView().refresh();
			}catch(e){}
			return record;
        },
        upStep:function(){//上一步
        	 this.activePanel.hide();
             this.activePanel=this.designPanel;
             this.activePanel.show();
             this.upbutton.hide();
             this.doLayout(true);
             this.step=0;
             this.nextbutton.show();
             this.publishbutton.hide();
             this.savebutton.hide();
        },
        nextStep:function(){//下一步
        	var html=this.designPanel.getCmpByName('formDef.defHtml').getValue();
        	if(this.checkIfHasSameElement(this.getFormElements(this.getHtmlForm(html)))){
        	    Ext.ux.Toast.msg('信息提示','字段名称不能重复,请检查重新命名！');
        	    return;
        	}
        	if(this.step==0 &&this.activePanel.getForm().isValid()){
	        	 var type=this.radioGroup.getValue();
	        	 if(type.inputValue==0){
	        	 	this.haveSub=false;
	        	    this.belongTable.hide();
	        	    if(html){
	        	    	var msg=this.loadFields(html,true);
		        	 if(msg!=false){
		        	 	Ext.ux.Toast.msg('信息提示',msg);
		        	    return;
		        	 }
	        	    }
	        	 }else{
	        	 	this.haveSub=true;
	        	 	if(html){
	        	 		var msg=this.loadFields(html,false);
		        	  if(msg!=false){
		        	  	 Ext.ux.Toast.msg('信息提示',msg);
		        	     return;
		        	  }
	        	    }
	        	 	this.belongTable.show();
	        	 }
	        	 this.activePanel.hide();
	             this.activePanel=this.configPanel;
	             
	             this.activePanel.show();
	             this.upbutton.show();
	             this.savebutton.show();
	             this.publishbutton.show();
	             this.nextbutton.hide();
	             
	        	 
	        	 this.configPanel.doLayout(true);
	             this.doLayout(true);
	             this.step=1;
	            
        	}else{
        	    Ext.ux.Toast.msg('信息提示','没填写表单信息或未设计表单！');
        	    return; 
        	}
        },
        getHtmlForm:function(html){
             var form2=document.createElement('form');//create a form to contain the html,for get the elements of html;
        	 var p=document.createElement('p'); 
        	 var div2=document.createElement('div');
        	 var els=Ext.DomHelper.insertHtml('afterBegin',div2,html);
        	 p.appendChild(div2);
        	 form2.appendChild(p);
        	 return form2;
        },
        getFormElements:function(form){
           return form.elements;
        },
        checkIfHasSameElement:function(elements){//检查是否存在名称相同的组件
             for(var i=0;i<elements.length;i++){
                  for(var j=i+1;j<elements.length;j++){
                     if(elements[i].name==elements[j].name&&elements[i].type!='radio'&&elements[i].type!='checkbox'){
                     	return true;
                     }
                  }
             }
             return false;
        },
        enHtml:function(html){
             /**
        	  * become a object of element
        	  */
        	 var beRecord=function(el){//'fieldName','fieldType','isRequired','fieldSize','fieldDscp','isPrimary','foreignKey','foreignTable','isList','isQuery','isFlowTitle','showFormat'
        	 	 var obj=new Object();
        	     obj.fieldName=el.name;
        	     var type=el.getAttribute('txtvaluetype');
        	     obj.fieldType=type?type:'varchar';//默认为varchar类型
        	     var isPrimary=el.getAttribute('txtisprimary');
        	     obj.isPrimary=isPrimary==1?1:0;
        	     var label=el.getAttribute('txtlabel');
        	     obj.fieldLabel=label?label:el.name;
        	     var isq=el.getAttribute('txtisnotnull');
        	     obj.isRequired=isq==1?1:0;
        	     var size=el.getAttribute('txtsize');
        	     obj.fieldSize=size?size:null;
        	     var showFormat=el.getAttribute('dataformat');
        	     obj.showFormat=showFormat?showFormat:'';
        	     obj.isList=1;
        	     obj.isQuery=1;
        	     obj.isDesignShow=el.type=='hidden'?2:1;
        	     obj.isisFlowTitle=0;
        	     return obj;
        	 };
        	 /**
        	  * 将相同名字的radio合并
        	  */
        	 var mergeRadios=function(els){
        	 	 var dd=[];
        	     for(var i=0;i<els.length;i++){
        	        var el=els[i];
        	        var flag=true;
        	        for(var j=0;j<dd.length;j++){
                       if(dd[j].name==el.name){
                         flag=false;
                       }         	        	
        	        }
        	        if(flag){
        	        	dd.push(el);
        	        }
        	     }
        	     return dd;
        	 };
        	 /**
        	  * push elements into array 
        	  */
        	 var findEl=function(els){
        	     var objs=[];
        	     var radios=[];
        	     Ext.each(els, function(el) {
        	     	if(el.type=='button'){
        	     	    return;
        	     	}
        	     	if(el.type=='radio'||el.type=='checkbox'){
        	     		radios.push(el);
                        return;        	     		
        	     	}
        	        objs.push(beRecord(el));
        	     });
        	     var ras=mergeRadios(radios);
        	     for(var d=0;d<ras.length;d++){
        	        objs.push(beRecord(ras[d]));
        	     }
        	     return objs;
        	 };
             
             var form2=this.getHtmlForm(html);
        	 var tables=form2.getElementsByTagName('TABLE');//get tags of table; 
        	 this.havadetailNo=0;
        	 this.SubTables.clear();
        	 this.MainTable=null;
        	 var subTs=new Ext.util.MixedCollection();
        	 var subTable=[];
        	 var tableNames=[];
        	 /**
        	  * find the subtable
        	  */
        	 if(this.haveSub){
	        	 for(var i=0;i<tables.length;i++){
		        	 if(tables[i]&&tables[i].getAttribute('isdetail')){
		        	 	 var subTableName=tables[i].getAttribute('txtname');
		        	 	 if(!subTableName){
		        	 	     return '明细表格未填写名字!';
		        	 	 }
		        	 	 for(var j=0;j<tableNames.length;j++){
		        	 	    if(tableNames[j]==subTableName){
		        	 	       return '子表存在重复表名，请保证表名不相同！';
		        	 	    }
		        	 	 }
		        	 	 subTs.add(subTableName,tables[i]);
		        	     tableNames.push(subTableName);
		        	     this.subTableKeys.push(subTableName);
//		        	     var parentNode=tables[i].parentNode;
		        	     subTable.push(tables[i]);
		        	     this.havadetailNo++;
//		        	     if(parentNode){
//		        	        parentNode.removeChild(tables[i]);
//		        	     }
		        	 }
	        	 }
	        	 if(subTable&&subTable.length>0){
	        	     for(var i=0;i<subTable.length;i++){
	        	     	 var parentNode=subTable[i].parentNode;
		        	     if(parentNode){
			        	        parentNode.removeChild(subTable[i]);
			        	 }
	        	     }
	        	 }
        	 }

        	 var objs1=findEl(form2.elements);
        	 if(objs1.length>0){
        	    this.MainTable= objs1;
        	 }
        	 if(tableNames&&tableNames.length>0){//if subtable exit
		        	 for(var k=0;k<tableNames.length;k++){
			        	 var form3=document.createElement('form');
			        	 var subT=subTs.get(tableNames[k]);
			        	 form3.appendChild(subT);
			        	 var objs2=findEl(form3.elements);
			        	 if(objs2.length>0){
			        	   this.SubTables.add(tableNames[k],objs2);
			        	 }
		        	 }
        	 }
        	 return true;
        },
        loaddata:function(dd){
        	if(!dd.data){
        	   dd.data=new Array();
        	}
        	var store=this.dataStore.get(dd.gridKey+'-store');
            var d=this.caStore.get(dd.gridKey);
	    	if(d&&d.length>0){
	    		for(var i=0;i<d.length;i++){
	    			dd.data.push(d[i]);
	    		}
	    	}
	    	var vv=[];
	    	if(dd.isMainTable){
           	    var m=this.dataStore.get('mainTable-database');
           	    if(m){
	           	    this.setValue(this.mainTable,'formTable',m);
	           	    vv=m.formFields;
           	    }
	    	}
	    	var storedata=this.mergeDate(dd.data,vv);
	    	store.loadData(storedata);
	    	store.fireEvent('checkloadevent',{gridKey:dd.gridKey,isMainTable:dd.isMainTable});
            this.loadsubtabledata();
        
        },
        mergeDate:function(arr1,arr2){//arr1为页面解析的字段，arr2为数据库的数据
        	var k=0;
        	var storedata=[];
	    	if(arr2&&arr2.length>0){
		    	for(var i=0;i<arr1.length;i++){
		    	   var flag=false;
		    	   for(var j=0;j<arr2.length;j++){
			    	   if(arr1[i].fieldName==arr2[j].fieldName){
//			    	      var obj1=arr1[i];
//			    	      var obj2=arr2[j];
			    	      var oldA=arr2[j];
                     	  var obj1=arr1[i];
                     	  obj1.fieldId=oldA.fieldId;
                     	  obj1.isPrimary=oldA.isPrimary;
                     	  obj1.isFlowTitle=oldA.isFlowTitle;
                     	  obj1.isQuery=oldA.isQuery;
                     	  obj1.isList=oldA.isList;
                     	  obj1.foreignKey=oldA.foreignKey;
                     	  obj1.foreignTable=oldA.foreignTable;
                     	  storedata.push(obj1);
                     	  flag=true;
			    	   }else if(k==0&&arr2[j].isDesignShow==3){//添加手工添加的
			    	      storedata.push(arr2[j]);
			    	      flag=true;
			    	   }
		    	   }
		    	   if(!flag){
		    	      storedata.push(arr1[i]);
		    	   }
		    	   k++;
		    	}
	    	}else{
	    	    storedata=arr1;
	    	}
	    	
	    	return storedata;
        },
        loadsubtabledata:function(){
            for(var i=0;i<this.subTableKeys.length;i++){
//               var grid=this.dataStore.get(this.SubTables.keys[i]+'-grid');
               var pageList=this.SubTables.get(this.subTableKeys[i]);
               var d=this.caStore.get(this.subTableKeys[i]);
		       if(d&&d.length>0){
		    		for(var i=0;i<d.length;i++){
		    			pageList.push(d[i]);
		    		}
		       }
               var m=this.dataStore.get(this.subTableKeys[i]+'-database');
               var fields=[];
               if(m){
               		this.setValue(this.belongTable,this.subTableKeys[i],m);
               		fields=m.formFields;
               }
               var datas=this.mergeDate(pageList,fields);
//               var store=grid.getStore();
               var store=this.dataStore.get(this.subTableKeys[i]+'-store');
               store.loadData(datas);
//               store.fireEvent('checkloadevent',{gridKey:this.SubTables.keys[i]+'-grid',isMainTable:false});
               this.checkdata({gridKey:this.subTableKeys[i],isMainTable:false});   
            }
        },
        checkdata:function(d){
            var store=this.dataStore.get(d.gridKey+'-store');
            if(d.isMainTable){
            	this.primarykeyRecord=this.findPrimaryKey(store);
            }else{
                var pk=this.findPrimaryKey(store);
                var grid=this.dataStore.get(d.gridKey+'-grid');
                if(grid){
	                if(this.haveSub&&pk==null){
		               this.addPrimaryKeyRecord(grid,'subId');
		               var fk=this.findForeignKey(this.dataStore.get(d.gridKey+'-store'),this.primarykeyRecord.data.fieldName);
		               this.addForeignKey(grid,this.primarykeyRecord,fk,true);
		            }
	            }
            }
            if(!this.primarykeyRecord){//主表不存在主键
               this.primarykeyRecord=this.addPrimaryKeyRecord(this.dataStore.get(d.gridKey),'mainId');    
            }
        },
        setCacheFields:function(mtable){
        	var array=[mtable];
//        	if(subtables.length>0){
//        	   array=subtables.push(mtable);
//        	}
        	Ext.each(array,function(gName){
        		var arr=new Array();
        		var store=this.dataStore.get(gName+'-store');
        		if(store){
	        	    for(var i=0;i<store.getCount();i++){
	        	        var record=store.getAt(i);
	        	        if(record.data.isDesignShow==3&&(record.data.fieldId==null||record.data.fieldId==undefined||record.data.fieldId=='')){
	        	            arr.push(record.data);
	        	        }
	        	    }
	        	    if(arr.length>0){
	        		    this.caStore.add(gName,arr);
	        	    }
	        	    store.on('loaddataevent',this.loaddata,this);
	        	    store.on('checkloadevent',this.checkdata,this);//检测是否存在主键，如果没有，则加上主键
	        	    store.on('removePrimaryKey',this.removePrimaryKey,this);//移除主表主键
        		}
        	},this);
        },
        removePrimaryKey:function(dd){
            this.primarykeyRecord=null;
            for(var i=0;i<this.subTableKeys.length;i++){//移除所有从表的相关外键
            	var name=this.subTableKeys[i];
                var grid2=this.dataStore.get(name+'-grid');
                if(grid2){
	                var store=grid2.getStore();
		            //移除所有从表相应的外键
		            var fk=this.findForeignKey(store,dd.record.data.fieldName);
		            if(fk!=null){
		               store.remove(fk);
		            }
                }
            }
        },
        loadFields:function(html,isSingle){//加载字段和表单数据
        	/**
        	 * 动态创建列表，并把相应的数据加载进入对应的表里面
        	 */
        	 var tables=this.formTables;
        	 var grid1=this.editGrid1;
        	 var store=grid1.getStore();
        	 this.caStore.clear();//清除store缓存的内容
        	 var cacheTables=[];
        	 for(var y=0;y<this.subTableKeys.length;y++){//保存上一次进入的表名
        	    cacheTables.push(this.subTableKeys[y]);
        	 }
        	 this.setCacheFields('mainGrid',cacheTables);
        	 this.subTableKeys.length=0;//清空子表的KEY
        	 if(tables){
        	 	var flag=this.enHtml(html);
        	 	if(flag!=true){
                   return flag;        	 	
        	 	}
        	 	for(var y=0;y<this.subTableKeys.length;y++){
        	 		var name=this.subTableKeys[y];
        	 		cacheTables.remove(name);
        	 	    var grid=this.dataStore.get(name+'-grid');
        	 	    if(grid==null||grid==undefined){
	        	 	    var f =new Ht.DetailFormPanel({height:300,padding:'16 16 16 16',title:name+'-表',paramRoot:name,parentConf:this});
	                    var g=f.getEditGrid();
	                    if(g){
	                       this.dataStore.add(name+'-grid',g);
	                       g.on('clickPrimary',this.addPrimaryKey,this);
	                       this.dataStore.add(name+'-store',g.getStore());
	                    }
	                    this.dataStore.add(name+'-fieldset',f);
	                    this.belongTable.add(f);
        	 	    }
        	 	}
        	 	for(var v=0;v<cacheTables.length;v++ ){
        	 		var key=cacheTables[v];
        	 	    var f=this.dataStore.get(key+'-fieldset');
        	 	    if(f){
        	 	       this.dataStore.removeKey(key+'-fieldset');
        	 	       this.dataStore.removeKey(key+'-grid');
        	 	       this.dataStore.removeKey(key+'-store');
        	 	       this.subTableKeys.remove(key);
        	 	       f.disable();
        	 	       f.hide();
        	 	    }
        	 	}
        	 	this.belongTable.doLayout(true);
                this.configPanel.doLayout(true);
                this.doLayout(true);
	        	if(tables.length>0){
	        		var mainFields,subFields,mfields,sfields;
	    	 	    for(var i=0;i<tables.length;i++){
	    	 	        if(tables[i].isMain==1){
	    	 	           mainFields=tables[i];
	    	 	           this.dataStore.add('mainTable-database',mainFields);
	    	 	        }else{
	    	 	           if(!isSingle){
	    	 	           	   subFields=tables[i];
	    	 	           	   for(var y=0;y<this.subTableKeys.length;y++){
	    	 	           	       if(subFields.tableKey==this.subTableKeys[y]){
	    	 	           	           this.dataStore.add(this.subTableKeys[y]+'-database',subFields);
	    	 	           	       }
	    	 	           	   }
	    	 	           }
	    	 	        }
	    	 	    }
	        	}
	        	store.fireEvent('loaddataevent',{gridKey:'mainGrid',data:this.MainTable,isMainTable:true});
        	 }
        	 
        	 return false;
        },
        setValue:function(tablePanel,preName,data){//赋值
        	   var items=tablePanel.items;
        	   if(items!=null){
				   for(var i=0;i<items.getCount();i++){
						var comp=items.get(i);
						var xtype=comp.getXType();
						try{
							if(xtype=='hidden' || xtype=='textfield'){
								var name=comp.getName();
								if(name){
									if(preName){
										if(name.indexOf(preName)!=-1){
											name=name.substring(preName.length+1);
										}
									}
									var val=eval('data.'+name);
									if(val!=null && val!=undefined){
										comp.setValue(val);
										if(name.indexOf('Key')!=-1){
										   comp.setReadOnly(true);
										}
									}
								}
							}else if(comp.items){
							    this.setValue(comp,preName,data);
							    continue;
							}
						}catch(e){
							//alert(e);
						}
				   }
        	   }
        },
        validRecord:function(store,record,cnt,i){//验证数据
        	if(record.data.fieldType==''||record.data.fieldType==null){
        	     Ext.ux.Toast.msg('信息提示','字段数据类型不能为空！');
			     return null;
        	}
			if(record.data.fieldName==''||record.data.fieldName==null){
				 Ext.ux.Toast.msg('信息提示','字段KEY不能为空！');
			     return null;
			}
			for(var j=i+1;j<cnt;j++){
			    var rec2=store.getAt(j);
			    if(record.data.fieldName==rec2.data.fieldName){
			    	Ext.ux.Toast.msg('信息提示','字段KEY不能重复！');
			        return null;
			    }
			}
			return record;//this.changeData(record)
        },
        saveRecord:function(){//保存数据
        	try{
        	 if(this.activePanel.getForm().isValid()){
	             this.storeParam1=[];
	    		 this.storeParam2=new Ext.util.MixedCollection();
	        	 var store1=this.editGrid1.getStore();
	        	 var isCount=0;
	        	 var haveMainPrimaryKey=0;
	        	 var primaryKey;
	        	 
	        	 
//	        	 var validArray=[];
	        	 for (var i = 0, cnt = store1.getCount(); i < cnt; i += 1) {
					var record1 = store1.getAt(i);
					if(record1){
						if(record1.data.isFlowTitle==1){
						    isCount++;
						}
						record1=this.validRecord(store1,record1,cnt,i);
						if(!record1){
						   return;
						}
						record1=this.changeData(record1);
//						validArray.push(record1.data.fieldName);
						this.storeParam1.push(record1.data);
					}
					if(record1.data.isPrimary==1){
					    haveMainPrimaryKey++;
					    primaryKey=record1.data.fieldName;
					}
				 }
				 if(store1.getCount()==0){
	        	       Ext.ux.Toast.msg('信息提示','主表不能没有字段！');
	        	       return;
	        	 }
				 if(haveMainPrimaryKey==0){
				    Ext.ux.Toast.msg('信息提示','主表缺少主键，请选择主键！');
	        	    return;
				 }
				 if(haveMainPrimaryKey>1){
				    Ext.ux.Toast.msg('信息提示','主表只能有一个主键！');
	        	    return;
				 }
				 if(isCount==0){
	        	    Ext.ux.Toast.msg('信息提示','必需有一个字段用于显示任务节点名称！');
	        	    return;
	        	 }
	        	 if(isCount>1){
	        	    Ext.ux.Toast.msg('信息提示','只能有一个字段用于显示任务节点名称！');
	        	    return;
	        	 }
				 for(var i=0;i<this.subTableKeys.length;i++){
				 	var key=this.subTableKeys[i];
				    var grid=this.dataStore.get(key+'-grid');
				    var store=grid.getStore();
				    if(!this.validStore(store,key,primaryKey)){
				       return;
				    }
				 }
				 
				 
				 /**
				  * 此时工作，只要把子表里面的列表数据组装起来，再分别插入对象中，在后台便可以用json来转化得到表和字段的信息
				  * 便可以把信息保存到数据库里面，
				  * 同时，如果表被删除了，或者不存在 ，也把数据的表删除
				  * over 
				  */
	             var baseParam2 = Ext.Ajax.serializeForm(this.configPanel.getForm().getEl());
	             var deParams = Ext.urlDecode(baseParam2);
	             var objs=[];
	             objs.push(this.convertObj(deParams,'formTable',Ext.encode(this.storeParam1)));
	             for(var j=0;j<this.subTableKeys.length;j++){
	                var key=this.subTableKeys[j];
	                objs.push(this.convertObj(deParams,key,this.storeParam2.get(key+'ad')));
	             }
				 var formpanel=this;
	             this.designPanel.getForm().submit({
	             	            params:{'objs':Ext.encode(objs)},
								method : 'post',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									var data=action.result;
									if(!data.success){
										alert(data.msg);
									    Ext.ux.Toast.msg('信息提示','已经存在相同的表名'+data.msg+'！');
									}else{
										if(formpanel.callback){
											formpanel.callback.call(formpanel);
										}
										formpanel.close();
									}
								},
								failure : function(form, action) {
									var data=action.result;
									if(!data.success){
									    Ext.ux.Toast.msg('信息提示','已经存在相同的表名'+data.msg+'！');
									}else{
										Ext.MessageBox.show({
								           title: '操作信息',
								           msg: '信息保存出错，请联系管理员！',
								           buttons: Ext.MessageBox.OK,
								           icon:'ext-mb-error' 
								       });
									}
								}});
            }
           }catch(e){alert(e);}
        },
        convertObj:function(deParams,key,fields){
            var obj={};
            obj.tableId=deParams[key+'.tableId']?deParams[key+'.tableId']:null;
            obj.tableName=deParams[key+'.tableName']?deParams[key+'.tableName']:null;
            obj.tableKey=deParams[key+'.tableKey']?deParams[key+'.tableKey']:null;
            obj.isMain=deParams[key+'.isMain']?deParams[key+'.isMain']:null;  
            obj.fields=fields?fields:null;
            return obj;
        },
        validStore:function(store2,key1,primaryKey){
        	     var storeParam=[];
        	     var e=0;
        	     var haveSubPrimaryKey=0;
				 for (var i = 0, cnt = store2.getCount(); i < cnt; i += 1) {
						var record2 = store2.getAt(i);
						if(record2){
							record2=this.validRecord(store2,record2,cnt,i);
							if(!record2){
							   return false;
							}
							if(record2.data.foreignKey!=''&&record2.data.foreignKey!=undefined){
							   if(record2.data.foreignKey!=primaryKey){
							   	  Ext.ux.Toast.msg('信息提示','从表的外键所对应主表的主键不一致，请重新选择！');
							      return false;
							   }
							   e++;
							   if(record2.data.foreignTable==''||record2.data.foreignTable==null){
							   	  Ext.ux.Toast.msg('信息提示','从表的关联表不能为空！');
							      return false;
							   }
							   var key=this.configPanel.getCmpByName('formTable.tableKey').getValue();
							   if(record2.data.foreignTable!=key){
							      Ext.ux.Toast.msg('信息提示','从表的关联表与主表名不同，请修改！');
							      return false;
							   }
							}
							record2=this.changeData(record2);
							storeParam.push(record2.data);
						}
						if(record2.data.isPrimary==1){
						    haveSubPrimaryKey++;
						}
				 }
				 if(e>1){
				    Ext.ux.Toast.msg('信息提示','从表只能有一个外键！');
	        	    return false;
				 }
				 if(haveSubPrimaryKey==0&&this.haveSub){
				    Ext.ux.Toast.msg('信息提示','从表缺少主键，请选择主键！');
	        	    return false;
				 }
				 
				 if(haveSubPrimaryKey>1){
				    Ext.ux.Toast.msg('信息提示','从表只能有一个主键！');
	        	    return false;
				 }
	        	 
	        	 
	        	 if(this.haveSub){
	        	    if(store2.getCount()==0){
	        	       Ext.ux.Toast.msg('信息提示','从表不能没有字段！');
	        	       return false;
	        	    }
	        	 }
	        	 
	        	 this.storeParam2.add(key1+'ad',Ext.encode(storeParam));
	        	 return true;
        },
        publishRecord:function(){//正式发布
            this.designPanel.getCmpByName('formDef.status').setValue(1);
        	this.saveRecord();
        },
        saveDrafRecord:function(){
            this.designPanel.getCmpByName('formDef.status').setValue(0);
        	this.saveRecord();
        },
        changeData:function(record){//把TRUE修改成１
            if(record.data.isPrimary){
				 record.set('isPrimary',1);
			}else{
			     record.set('isPrimary',0);
			}
			if(record.data.isList){
			     record.set('isList',1);
			}else{
			     record.set('isList',0);
			}
			if(record.data.isQuery){
			     record.set('isQuery',1);
			}else{
			     record.set('isQuery',0);
			}
			if(record.data.isRequired){
			     record.set('isRequired',1);
			}else{
			     record.set('isRequired',0);
			}	
			if(record.data.isFlowTitle){
			     record.set('isFlowTitle',1);
			}else{
			     record.set('isFlowTitle',0);
			}
			if(isNaN(record.data.fieldSize)||record.data.fieldSize==''){
			     record.set('fieldSize',null);
			}
			if(record.data.fieldId==0){
				record.set('fieldId',null);
			}
			record.commit();
			return record;
        }
});
/**
 * 预览 窗口
 * @class DesignerWin
 * @extends Ext.Window
 */
DesignerWin=Ext.extend(Ext.Window,{
        constructor:function(_cfg){
        	Ext.applyIf(this,_cfg);
        	this.initUI();
            DesignerWin.superclass.constructor.call(this,{
	              title:'表单预览',
		          iconCls:'menu-find-doc',
		          width : 800,
				  height : 430,
				  modal : true,
				  autoScroll:true,
				  maximizable:true,
				  layout : 'vbox',
				  defaults:{
				     padding:'6'
				  },
				  layoutConfig:{
            	    align:'stretch'
            	  },
		          items:[this.formPanel],
		          buttonAlign : 'center',
			      buttons:this.buttons
            });
        },
        initUI:function(){
        	this.formTable=new Ext.form.FieldSet({
        	     title:'业务表单',
				 //layout:'table',
				 html:this.defHtml
        	});
        	
        	this.formPanel=new Ext.FormPanel({
        		 layout:'table',
        	     border:false,
        	     frame:false,
        	     flex:1,
        	     autoScroll:true,
			     defaults:{
			        anchor:'98%,98%'
			     },
        	     items:this.formTable
        	});
            this.formTable.on("afterrender",this.getFormHtmlCallback,this);
        	this.buttons=[{
        	    xtype:'button',
        	    text:'关闭',
        	    iconCls:'btn-close',
        	    scope:this,
        	    handler:function(){
        	    try{
        	    if(this.officePanel){
        	       this.officePanel.closeDoc();
        	    
        	    }
        	    }catch(e){}
        	    this.close();}
        	}]
        },
        getFormHtmlCallback:function(){
			try{
				$ImportSimpleJs([
        	     __ctxPath+'/js/core/ntkoffice/NtkOfficePanel.js',
        	     __ctxPath + '/js/selector/SealSelector.js',
	             __ctxPath + '/js/selector/PaintTemplateSelector.js'
        	 
        	 ],function(){
        	    $converDetail.call(this,null,null);
        	 },this);
			  
			}catch(e){
				alert(e);
			}
        }
});

/**
 * grid拖拽
 */
Ext.ns('Ext.ux.dd');

Ext.ux.dd.GridDragZone=function(grid,config){
    this.view = grid.getView();
    Ext.ux.dd.GridDragZone.superclass.constructor.call(this, this.view.mainBody.dom, config);
    this.scroll = false;
    this.grid = grid;
    this.ddel = document.createElement('div');
    this.ddel.className = 'x-grid-dd-wrap';
};

Ext.extend(Ext.ux.dd.GridDragZone,Ext.dd.DragZone,{
            getDragData: function(e) {
		        var sm=this.grid.selModel;
		        var t = Ext.lib.Event.getTarget(e);
		        var rowIndex = this.view.findRowIndex(t);
		        if(rowIndex !== false){
		             if(!sm.isSelected(rowIndex) || e.hasModifier()){
			            sm.handleMouseDown(this.grid, rowIndex, e);
			         }

		        }
            	if(sm.getSelections().length>0){
            	    return {grid: this.grid, ddel: this.ddel, selections:sm.getSelections()};
            	}
		        return false;

	        },
	        onInitDrag : function(e){
		        var data = this.dragData;
		        this.ddel.innerHTML = this.grid.getDragDropText();
		        this.proxy.update(this.ddel);
		    },
	        getRepairXY: function() {
	            return this.dragData.repairXY;
	        }
});

Ext.ux.dd.GridDropZone=function(grid,config){
    this.view = grid.getView();
    Ext.ux.dd.GridDropZone.superclass.constructor.call(this, this.view.scroller, config);
    this.scroll = false;
    this.grid = grid;
};

Ext.extend(Ext.ux.dd.GridDropZone,Ext.dd.DropZone,{
        getTargetFromEvent: function(e) {
	    	return this.view.scroller;
	    },
	    onNodeEnter : function(target, dd, e, data){ 
	        Ext.fly(target).addClass('my-row-highlight-class');
	    },
	    onNodeOut : function(target, dd, e, data){ 
	        Ext.fly(target).removeClass('my-row-highlight-class');
	    },
	    onNodeOver : function(target, dd, e, data){ 
	        return Ext.dd.DropZone.prototype.dropAllowed;
	    },
	    onNodeDrop : function(target, dd, e, data){
	    	var grid=data.grid;
	    	var grid2=this.grid;
	    	var selections=data.selections;
	    	var store=grid.getStore();
	    	for(var i=0;i<selections.length;i++){
	    		store.remove(selections[i]);
	    	}
	    	grid.getView().refresh();
	    	var store2=this.grid.getStore();
	    	var index=store2.getCount();
	    	store2.insert(index,selections);
	    	this.grid.getView().refresh();
	        return true;
	    }
});