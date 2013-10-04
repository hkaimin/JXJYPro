/**
 * 配网工单选择器
 * author:hjay
 */
var CreditSelector = {
	/**
	 * @param callback
	 *            回调函数
	 * @param isSingle
	 *            是否单选
	 */
	getView : function(callback, isSingle, config) {
		// ---------------------------------start grid
		// panel--------------------------------
		var sm = null;
		if (isSingle) {
			var sm = new Ext.grid.CheckboxSelectionModel({
						singleSelect : true,
						dataIndex:'kxId'
					});
		} else {
			sm = new Ext.grid.CheckboxSelectionModel({
			  dataIndex:'kxId'
			});
		}
		//已经选择的馈线Ids
		var kxIds = "";
		var cm = new Ext.grid.ColumnModel({
			columns : [sm, new Ext.grid.RowNumberer()
			,{
				header : '学分类别名称',
				dataIndex : 'mc',
				renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var xfbz = record.data.xfbz;
						if(xfbz != null && xfbz != "" || xfbz == "0") {
							return "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" + value;
						} else {
							return value;
						}
					}
			}]
		});

		var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + "/project/selectListCredit.do?tjlb=" + config.tjlb
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							fields : [ 'xflbid', 'id', 'mc', 'xfmc', 'xfmcid', 'sfsh', 'xfbz']
						}),
				remoteSort : true
				,
				listeners : {   
                    load : function(store,records,options) {  
                    	var arr=[];   
					    for(var i=0;i<records.length;i++){   
					      var record = records[i];   
					      var isMatch = kxIds.indexOf(record.get('kxId'))!=-1;   
					      if(isMatch){   
					       arr.push(record);   
					      }   
					    }   
					   sm.selectRecords(arr);    
                    }   
                } 
			});
	     //store.setDefaultSort('wsId', 'desc');

		var gridPanel = new Ext.grid.GridPanel({
					id : 'SelectPwInstenceGrid',
					width : 500,
					height : 300,
					region : 'center',
//					title : '馈线列表',
					store : store,
					shim : true,
					rowActions : true,
					trackMouseOver : true,
					disableSelection : false,
					loadMask : true,
					cm : cm,
					sm : sm,
					viewConfig : {
						forceFit : true,
						enableRowBody : false,
						showPreview : false
					},
					// paging bar on the bottom
					bbar : new HT.PagingBar({store : store})
				});

		       store.load({
					params : {
						start : 0,
						limit : 15
					}
				});
				
		
		if(config.tjlb == "2") {
			gridPanel.getSelectionModel().addListener('beforerowselect', function(model, rowIndex, flage, record) {
					var xflbid = record.data.xflbid;
					if(xflbid == null || xflbid == "") {
						Ext.ux.Toast.msg("提示信息", "您已选择条件为最高分限制，请选择具体的学分类别");
						return false;
					}
				}
			);
		}
		       
		// --------------------------------end grid		
		//----searchPanel--begin----
		var formPanel = new Ext.FormPanel({
			width : 400,
			region : 'north',
			id : 'pwInstenceSearchForm',
			height : 40,
			frame : false,
			border : false,
			layout : 'hbox',
			layoutConfig : {
				padding : '5',
				align : 'middle'
			},
			defaults : {
				xtype : 'label',
				margins : {
					top : 0,
					right : 4,
					bottom : 4,
					left : 4
				}
			},
			items : [{
						text : '馈线名称:'
					},{
						xtype : 'textfield',
						width : 150,
						name : 'kxmc'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('pwInstenceSearchForm');
							var grid = Ext.getCmp('SelectPwInstenceGrid');
							var formVars = searchPanel.getForm().getFieldValues();
							formVars.kxId = "";
							var store = grid.getStore();
							store.baseParams = formVars;
							store.load({
								params : {
									start : 0,
									limit : 15
								}
							});
						}
					}]
		});	
	    //----searchPanel--end----
		var window = new Ext.Window({
			title : '学分选择',
			iconCls:'menu-flow',
			width : 850,
			height : 500,
			layout : 'border',
			border : false,
			items : [gridPanel],
			modal : true,
			buttonAlign : 'center',
			buttons : [{
						iconCls : 'btn-ok',
						text : '确定',
						handler : function() {
							var grid = Ext.getCmp('SelectPwInstenceGrid');
							var rows = grid.getSelectionModel().getSelections();
							var rowTotal = rows.length;
							var ids = new Array();
							var kxNames = "";
							for(var i=0; i<rowTotal; i++) {
								if(i>0){
									kxNames += ","
								}
								ids.push(rows[i].data.xflbid);
								kxNames += rows[i].data.mc;
							}
							if (callback != null) {
								
								callback.call(this, ids, kxNames);
							}
							window.close();
						}
					}, {
						text : '取消',
						iconCls : 'btn-cancel',
						handler : function() {
							window.close();
						}
					}]
		});
		return window;
	}

};