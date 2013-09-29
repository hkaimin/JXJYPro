/**
 * @author
 * @createtime
 * @class DepartmentForm
 * @extends Ext.Window
 * @description DepartmentForm表单
 * @company 宏天软件
 */
DepartmentForm = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		DepartmentForm.superclass.constructor.call(this, {
			id : 'DepartmentFormWin',
			title : '部门信息',
	        iconCls : 'menu-department',
	        layout : 'fit',
	        width: 400,
	        height : 170,
			minWidth : 399,
			minHeight : 169,
			items : this.formPanel,
			border : false,
			modal : true,
			plain : true,
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.save,
				scope : this
			},
			buttonAlign : 'center',
			buttons : [{
	            text : '保存',
	            iconCls : 'btn-save',
	            handler : this.save,
	            scope : this
	        },{
	            text : '取消',
	            iconCls :'btn-cancel',
	            handler : function(){
	    			Ext.getCmp('DepartmentFormWin').close(); 
	    		}
	        }]
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.form.FormPanel({
			frame : false,
	        id:'departmentForm',
	        bodyStyle : 'padding : 5px;',
	        layout:'form',
	        defaultType: 'textfield',
	        url:__ctxPath+'/system/addDepartment.do',      
	        defaultType: 'textfield',
	        reader: new Ext.data.JsonReader(
		        	{
		        	root:'data'
		        	},
		        	[
		        		 {name:'depId',mapping:'depId'}
		        		,{name:'depName',mapping:'depName'}
		        		,{name:'depDesc',mapping:'depDesc'}
		        		,{name:'parentId',mapping:'parentId'}
		        	]
		        	),
	        
	        defaults: {
	            anchor: '95%,95%',
	            allowBlank: false,
	            selectOnFocus: true,
	            msgTarget: 'side'
	        },
	        items:[{
		        	xtype:'hidden'
		        	,name:'department.depId'
		        	,id:'depId'
		        },{
	            	xtype:'hidden',
	            	name:'department.parentId',
	            	id:'parentId',
	            	value:this.nodeId
	            },
	            {
	            	xtype:'hidden',
	            	name:'department.path',
	            	id:'path'
	            }, {
	            	fieldLabel : '部门名',
	            	name:'department.depName',
	            	blankText: '部门名为必填!',
	            	id:'depName'
	            }, {
					fieldLabel : '部门描述',
	            	xtype:'textarea',
	            	name:'department.depDesc',
	            	blankText: '部门描述为必填!',
	            	id:'depDesc'
	            }
	            ]
	    });
	},
	
	/**
	 * 保存
	 */
	save : function(){
 	   var tree = Ext.getCmp('departmentTreePanel');
	   if(Ext.getCmp('departmentForm').getForm().isValid()){
    		Ext.getCmp('departmentForm').getForm().submit({
    			waitMsg:'正在提交部门信息',
	            success: function(formPanel, o){
    				Ext.ux.Toast.msg('操作信息','数据操作成功！');
    				if(tree != null)
    					tree.root.reload();
    				Ext.getCmp('DepartmentFormWin').close();            				
    			}
    		});
    	}
	}
	
});