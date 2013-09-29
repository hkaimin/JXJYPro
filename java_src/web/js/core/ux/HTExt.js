/**
 * 用于扩展一些常用的ExtJs，以简化大量重复性的代码
 */
Ext.ns('HT');
Ext.ns('HT.ux.plugins');
/**
 * 查找
 * @param {} grid
 * @param {} idName
 * @return {}
 */
function $getGdSelectedIds(grid,idName){
	var selRs = grid.getSelectionModel().getSelections();
	var ids = Array();
	for (var i = 0; i < selRs.length; i++) {
		ids.push(eval('selRs[i].data.'+idName));
	}
	return ids;
}

function $postDel(conf){
	Ext.Msg.confirm('信息确认', '您确认要删除所选记录吗？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url :conf.url,
					params : {ids : conf.ids},method : 'POST',
					success : function(response,options) {
						Ext.ux.Toast.msg('操作信息','成功删除该记录！');
						if(conf.callback){
							conf.callback.call(this);
							return;
						}
						if(conf.grid){
							conf.grid.getStore().reload();
						}
					},
					failure : function(response,options) {
						Ext.ux.Toast.msg('操作信息','操作出错，请联系管理员！');
					}
				});
			}
	});
}
/**
 * 提交表单
 * @param {} conf
 */
function $postForm(conf){
		if(conf.formPanel.getForm().isValid()){
			var scope=conf.scope?conf.scope:this;
			conf.formPanel.getForm().submit({
					scope:scope,
					url : conf.url,
					method : 'post',
					params:conf.params,
					waitMsg : '正在提交数据...',
					success : function(fp, action) {
						Ext.ux.Toast.msg('操作信息', '成功信息保存！');
						if(conf.callback){
							conf.callback.call(scope,fp,action);
						}
					},
					failure : function(fp, action) {
						Ext.MessageBox.show({
									title : '操作信息',
									msg : '信息保存出错，请联系管理员！',
									buttons : Ext.MessageBox.OK,
									icon : 'ext-mb-error'
								});
						if(conf.callback){
							conf.callback.call(scope);
						}
					}
				});
		}
}
/**
 * 
 * @param {} conf
 */
function $delGridRs(conf){
	var ids=$getGdSelectedIds(conf.grid,conf.idName);
	if (ids.length == 0) {
		Ext.ux.Toast.msg("操作信息", "请选择要删除的记录！");
		return;
	}
	var params={
		url:conf.url,
		ids:ids,
		grid:conf.grid
	};
	$postDel(params);
}
/**
 * 搜索，把查询的Panel提交，并且更新gridPanel的数据，
 * 使用以下所示：
 *              $search({
 *					searchPanel:this.searchPanel,
 *					gridPanel:this.gridPanel
 *				});
 * @param {} conf
 */
function $search(conf){
	var searchPanel=conf.searchPanel;
	var gridPanel=conf.gridPanel;
	if (searchPanel.getForm().isValid()) {// 如果合法
		var store = gridPanel.getStore();
		var baseParam = Ext.Ajax.serializeForm(searchPanel.getForm().getEl());
		var deParams = Ext.urlDecode(baseParam);
		deParams.start = 0;
		deParams.limit = store.baseParams.limit;
		store.baseParams = deParams;
		gridPanel.getBottomToolbar().moveFirst();
	}
}

/**
 * 搜索Panel
 * @class HT.SearchPanel
 * @extends Ext.form.FormPanel
 */
HT.SearchPanel = Ext.extend(Ext.form.FormPanel, {
	constructor : function(conf) {
		// 查看其是否允许多行
		var colNums = conf.colNums ? conf.colNums : 1;
		Ext.apply(this, conf);
		
		if (colNums > 1 && conf.items) {
			this.items = [];
			var row = null;
			var validCnt = 0;
			for (var i = 0; i < conf.items.length; i++) {
				var cmp = conf.items[i];
				if (cmp.xtype != 'hidden') {
					if (validCnt % colNums == 0) {
						row = {
							xtype : 'compositefield',
							fieldLabel : cmp.fieldLabel,
							items : [],
							defaults:{
								style:'margin:0 0 0 0'
							}
						};
						this.items.push(row);
					} else {
						//设置分隔符
						var sepr = ":";
						if(this.superclass.labelSeparator){
							sepr = this.superclass.labelSeparator;
						};
						//设置label长度
						var width = 100;
						if(this.labelWidth){
							width = this.labelWidth;
						};
						if(cmp.labelWidth){
							width = cmp.labelWidth;
						};
						//设置label左右位置
						var textAlign = 'text-align:left';
						if('right'==this.labelAlign){
							textAlign = 'text-align:right';
						}
						
						if(cmp.fieldLabel){
							row.items.push({
								xtype : 'label',
								width :width,
								style : textAlign,
								text : cmp.fieldLabel+sepr
							});
						}
					}
					row.items.push(cmp);
					validCnt++;
				} else {
					this.items.push(cmp);
				}
			}
		}
		HT.SearchPanel.superclass.constructor.call(this, {
					autoHeight : true,
					border : false,
					style : 'padding:6px;background-color: white',
					buttonAlign : 'center'
				});
	}
});


/**
 * 取到某项目的数据字典
 * @class DicCombo
 * @extends Ext.form.ComboBox
 */
DicCombo=Ext.extend(Ext.form.ComboBox,{
	constructor:function(config){
		Ext.apply(this,config);
		var itemName=this.itemName;
		var isDisplayItemName=this.isDisplayItemName;
		DicCombo.superclass.constructor.call(this,{
			triggerAction : 'all',
			store : new Ext.data.ArrayStore({
							autoLoad : true,
							baseParams:{itemName:itemName},
							url : __ctxPath + '/system/loadItemDictionary.do',
							fields : ['itemId', 'itemName']
						}),
			displayField : 'itemName',
			valueField :isDisplayItemName?'itemName':'itemId'
		});
	}
});

Ext.reg('diccombo',DicCombo);

/**
 * 在分页栏中的导出插件
 * @class HT.ux.plugins.Export
 * @extends Object
 */
HT.ux.plugins.Export=Ext.extend(Object, {
  constructor: function(config){
    Ext.apply(this, config);
    HT.ux.plugins.Export.superclass.constructor.call(this, config);
  },

  init : function(pagingToolbar) {
  	var excelBtn=new Ext.SplitButton({
  		text:'导出',
  		iconCls:'btn-export',
  		menu: new Ext.menu.Menu({
  			items:[
  			{
  				text:'导出当前页EXCEL',
  				iconCls:'btn-export-excel',
  				listeners: {click: function(){
  					var gp = pagingToolbar.findParentBy (
            			function (ct, cmp) {;return (ct instanceof Ext.grid.GridPanel) ? true : false;});
  					CommonExport(gp,false,'xls');
  				}}
  			},{
  				text:'导出全部记录EXCEL',
  				iconCls:'btn-export-excel',
  				listeners: {click: function(){
  					var gp = pagingToolbar.findParentBy (
            		function (ct, cmp) {;return (ct instanceof Ext.grid.GridPanel) ? true : false;});
  					CommonExport(gp,true,'xls');
  				}}
  			},'-',{
  				text:'导出当前页PDF',
  				iconCls:'btn-export-pdf',
  				listeners: {click: function(){
  					var gp = pagingToolbar.findParentBy (
            		function (ct, cmp) {;return (ct instanceof Ext.grid.GridPanel) ? true : false;});
  					CommonExport(gp,false,'pdf');
  				}}
  			},{
  				text:'导出全部记录PDF',
  				iconCls:'btn-export-pdf',
  				listeners: {click: function(){
  					var gp = pagingToolbar.findParentBy (
            		function (ct, cmp) {;return (ct instanceof Ext.grid.GridPanel) ? true : false;});
  					CommonExport(gp,true,'pdf');
  				}}
  			}
  			]
  		})
  	});
  	pagingToolbar.add('->');
  	pagingToolbar.add('-');
  	pagingToolbar.add(excelBtn);
  	pagingToolbar.add('-');
  	
  	pagingToolbar.on({
      beforedestroy: function(){
        excelBtn.destroy();
      }
    });
  }
});

/**
 * 导出公共方法，在本页下载，不打开另一页
 * param: gridObj 取得grid
 * 		  isExportAll 是否导出所有
 * 		  exportType 导出的类型
 */
function CommonExport(gridObj,isExportAll,exportType){
	
	var cols = gridObj.getColumnModel().columns;
	var colName = '';
	var colId = '';
	for(var index=0;index<cols.length;index++){
		if(index>2 && cols[index].isExp == true){
			colName += cols[index].header + ',';
			if(cols[index].javaRenderer!=null){
				colId += "javaRenderer"+cols[index].javaRenderer+",";
			}else{
				colId += cols[index].dataIndex + ',';
			}
		}
	}
	
	if(colName.length>0){
		colName = colName.substring(0,colName.length-1);
		colId = colId.substring(0,colId.length-1);
	}
	
	var parasArr = {
		isExport: true,
		isExportAll: isExportAll,
		exportType: exportType,
		colId: colId,
		colName: colName
	};
	
	Ext.apply(parasArr,gridObj.store.baseParams);
	
	var elemIF=document.getElementById('downloadFrame');
	if(!elemIF){
		elemIF= document.createElement("iframe");
		elemIF.setAttribute('id','downloadFrame');
		document.body.appendChild(elemIF);
	}
	
	var ifrdoc;
	if(elemIF.contentDocument){
    	ifrdoc = elemIF.contentDocument;
	}else if(elemIF.contentWindow){
		ifrdoc = elemIF.contentWindow.document;
	}else{
		ifrdoc = elemIF.document;
	}
	
	if(ifrdoc.document){
		ifrdoc=ifrdoc.document;
	}
	var body=ifrdoc.body;
	if(!body){
		ifrdoc.write("<head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'></head>");
		body=ifrdoc.createElement('body');
		ifrdoc.appendChild(body);
	}
	
	var elemForm = ifrdoc.getElementById('downloadForm');
	
	//if exist then remove
	if(elemForm){
		ifrdoc.body.removeChild(elemForm);
	}
	
	//create new form and add new parameters
	elemForm=ifrdoc.createElement("form");
	elemForm.id='downloadForm';
	
	ifrdoc.body.appendChild(elemForm);
	
	var url=gridObj.store.proxy.url;
	for(var v in parasArr){
		var elmObj = ifrdoc.createElement("input");
		elmObj.type = "hidden";
    	elmObj.name = v;
    	elmObj.value = parasArr[v];
		elemForm.appendChild(elmObj);
	}
	
	elemForm.method = 'post';
	elemForm.action = url;
	elemForm.submit();
	
};

/**
 * 打印plugin
 * @class HT.ux.plugins.Print
 * @extends Object
 */
HT.ux.plugins.Print=Ext.extend(Object, {
  constructor: function(config){
    Ext.apply(this, config);
    HT.ux.plugins.Export.superclass.constructor.call(this, config);
  },

  init : function(pagingToolbar) {
  	var printBtn=new Ext.Button({
  		text:'打印',
  		iconCls:'btn-print',
  		listeners: {click: function(){
			var gp = pagingToolbar.findParentBy (
    		function (ct, cmp) {;return (ct instanceof Ext.grid.GridPanel) ? true : false;});
    		gpObj = document.getElementById(gp.id);
  			window.open(__ctxPath+'/js/printer/Print.jsp');
		}}
  	});
  	
  	pagingToolbar.add('->');
  	pagingToolbar.add('-');
  	pagingToolbar.add(printBtn);
  	
  	pagingToolbar.on({
      beforedestroy: function(){
        printBtn.destroy();
      }
    });
  }
});

HT.PagingBar=Ext.extend(Ext.PagingToolbar,{
	constructor:function(conf){
		var newConf={
			pageSize : conf.store.baseParams.limit?conf.store.baseParams.limit:25,
			displayInfo : true,
			displayMsg : '当前显示从{0}至{1}， 共{2}条记录',
			emptyMsg : '当前没有记录',
			plugins: [new Ext.ux.plugins.PageComboResizer()]
		};
		if(conf.exportable){
			newConf.plugins.push(new HT.ux.plugins.Export({store:conf.store}));
		}
		if(conf.printable){
			newConf.plugins.push(new HT.ux.plugins.Print());
		}
		
		Ext.apply(newConf,conf);
		HT.PagingBar.superclass.constructor.call(this,newConf);
	}
});

HT.JsonStore=Ext.extend(Ext.data.JsonStore,{
	constructor:function(conf){
		var baseParams=conf.baseParams?conf.baseParams:{};
		baseParams.start=0;
		baseParams.limit=25;
		var def = {
					baseParams:baseParams,
					root : 'result',
					totalProperty : 'totalCounts',
					remoteSort : true
				};
		Ext.applyIf(def,conf);
		HT.JsonStore.superclass.constructor.call(this,def);
	}
});
/**
 * 根据普通的配置，生成表格所需要的配置
 * @param {} conf
 * @return {}
 */
HT.initGridConfig=function(conf){
	if(!conf.store){
		conf.store=new HT.JsonStore({
			url : conf.url,
			fields:conf.fields,
			baseParams:conf.baseParams
		});
		if(conf.url){
			conf.store.load();
		}
	}
	
	//--start grid cell 内容提示展示
	for(var i=0;i<conf.columns.length;i++){
		if(!conf.columns[i].renderer){
			conf.columns[i].renderer = function (data, metadata, record, rowIndex, columnIndex, store) {    
			    metadata.attr = ' ext:qtip="' + data + '"';
			    return data;        
			}
		}
	}
	//--end 
	
	conf.sm = new Ext.grid.CheckboxSelectionModel({
		singleSelect: conf.singleSelect?conf.singleSelect:false
	});

	if(conf.columns){
		conf.columns.unshift(conf.sm);
		conf.columns.unshift(new Ext.grid.RowNumberer());
	}else{
		conf.columns=[conf.sm,new Ext.grid.RowNumberer()];	
	}
	
	if(!conf.tbar && conf.isShowTbar!=false){
		conf.tbar=new Ext.Toolbar();
	}
	
	if(conf.addTool){
		conf.tbar.add(new Ext.Button({
			text:'添加记录',
			iconCls:'btn-add',
			scope:this,
			handler:function(){
				var recordType=conf.store.recordType;
				conf.store.add(new recordType());
			}
		}));
	}
	
	this.cm=new Ext.grid.ColumnModel({
		columns:this.columns,
		defaults : {
			sortable : true,
			menuDisabled : false,
			width : 100
		}
	});
	conf.plugins=[];
	//加上rowActions
	if(conf.rowActions){
		var rowActionCol=conf.columns[conf.columns.length-1];
		conf.plugins.push(rowActionCol);
	}
	//导出
	if(conf.exportable){
		conf.plugins.push(HT.ux.plugins.Export({store:conf.store}));
	}
	//打印
	if(conf.printable){
		conf.plugins.push(HT.ux.plugins.Print);
	}
	var def={
				shim : true,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				stripeRows : true,
				viewConfig : {
					forceFit : true,
					enableRowBody : false,
					showPreview : false
				},
				bbar : (conf.showPaging==null || conf.showPaging)?new HT.PagingBar({store : conf.store}):null
	};
	Ext.apply(def,conf);
	
	return def;
}

/**
 * 
 * 
 * 表格管理控件，包括高级查询，数据导出，分页，排序等
 * @class HT.GridPanel
 * @extends Ext.grid.GridPanel
 */
HT.GridPanel=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(conf){
		var def=HT.initGridConfig(conf);
		HT.GridPanel.superclass.constructor.call(this,def);		
	}
});

/**
 * 
 * 
 * 表格编辑管理控件，包括高级查询，数据导出，分页，排序等
 * @class HT.GridPanel
 * @extends Ext.grid.GridPanel
 */
HT.EditorGridPanel=Ext.extend(Ext.grid.EditorGridPanel,{
	constructor:function(conf){
		var def=HT.initGridConfig(conf);
		HT.GridPanel.superclass.constructor.call(this,def);		
	}
});

Ext.reg("htgrid",HT.GridPanel);
Ext.reg("hteditorgrid",HT.EditorGridPanel);

HT.FormPanel=Ext.extend(Ext.form.FormPanel,{
	constructor:function(conf){
		var def={
					layout : 'form',
					bodyStyle:'padding:5px',
					defaults : {
						anchor : '96%,96%'
					},
					defaultType : 'textfield',
					border:false
			};
		Ext.apply(def,conf);
		HT.FormPanel.superclass.constructor.call(this,def);
	}
});

/*
 * 为Form表单设置加载数据,使用方式如下：
 * this.formPanel.loadData({
 *				url:__ctxPath + '/system/getAppRole.do?roleId=' + this.roleId,
 *				preName:'AppRole',
 *				root:'data'
 *			});
 */
Ext.override(Ext.Panel, {
	loadData:function(conf){
		if(!conf.root){
			conf.root='data';
		}
		var ct=this;
		//遍历该表单下所有的子项控件，并且为它赋值	
		var setByName=function(container,data){
			var items=container.items;
			if(items!=null&&items!=undefined&&items.getCount){
				for(var i=0;i<items.getCount();i++){
					var comp=items.get(i);
					if(comp.items){
						setByName(comp,data);
						continue;
					}
					//判断组件的类型，并且根据组件的名称进行json数据的自动匹配
					var xtype=comp.getXType();
					try{
						if(xtype=='textfield' || xtype=='textarea' || xtype=='radio' || xtype=='checkbox' 
							|| xtype=='datefield' || xtype=='combo' || xtype=='hidden' || xtype=='datetimefield'
							||xtype=='htmleditor'||xtype=='displayfield'||xtype=='diccombo'
							|| xtype=='fckeditor'||xtype=='numberfield'
							){
							var name=comp.getName();
							if(name){
								if(conf.preName){
									if(name.indexOf(conf.preName)!=-1){
										name=name.substring(conf.preName.length+1);
									}
								}
								var val=eval(conf.root+'.'+name);
								if(val!=null && val!=undefined){
									comp.setValue(val);
								}
							}
						}
					}catch(e){
						//alert(e);
					}
				}
			}
		};
		if (!ct.loadMask) {
			ct.loadMask = new Ext.LoadMask(Ext.getBody());
			ct.loadMask.show();
		}
		var scope=conf.scope?conf.scope:ct;
		var params=conf.params?conf.params:{};
		Ext.Ajax.request({
			method:'POST',
			url:conf.url,
			scope:scope,
			params:params,
			success:function(response,options){
				var json=Ext.util.JSON.decode(response.responseText);
				var data=null;
				if(conf.root){
					data=eval('json.'+conf.root);
				}else{
					data=json;
				}
				setByName(ct,data);
				if(ct.loadMask){
					ct.loadMask.hide();
					ct.loadMask = null;
				}
				if(conf.success){
				    conf.success.call(scope,response,options);
				}
			},//end of success
			failure:function(response,options){
				if(ct.loadMask){
					ct.loadMask.hide();
					ct.loadMask = null;
				}
				if(conf.failure){
				    conf.failure.call(scope,response,options);
				}
			}
		});
	}
});
/**
 * 修正表单的字段的长度限制的问题
 */
Ext.form.TextField.prototype.size = 20;
Ext.form.TextField.prototype.initValue = function(){
    if(this.value !== undefined){
        this.setValue(this.value);
    }else if(this.el.dom.value.length > 0){
        this.setValue(this.el.dom.value);
    }
    this.el.dom.size = this.size;
    if (!isNaN(this.maxLength) && (this.maxLength *1) > 0 && (this.maxLength != Number.MAX_VALUE)) {
        this.el.dom.maxLength = this.maxLength *1;
    }
};

Ext.override(Ext.Container, {
	getCmpByName : function(name) {
		var getByName = function(container, name) {
			var items = container.items;
			if (items && items.getCount!=undefined) {
				for (var i = 0; i < items.getCount(); i++) {
					var comp = items.get(i);
					if (name==comp.name || (comp.getName && name == comp.getName())) {
						return comp;
						break;
					}
					var cp = getByName(comp, name);
					if (cp != null)
						return cp;
				}
			}
			return null;
		};
		return getByName(this, name);
	},
	onResize : function(adjWidth, adjHeight, rawWidth, rawHeight) {
		Ext.Container.superclass.onResize.apply(this, arguments);
		if ((this.rendered && this.layout && this.layout.monitorResize)
				&& !this.suspendLayoutResize) {
			this.layout.onResize();
		}
	},

	canLayout : function() {
		var el = this.getVisibilityEl();
		return el && !el.isStyle("display", "none");
	},

	/**
	 * Force this container's layout to be recalculated. A call to this function
	 * is required after adding a new component to an already rendered
	 * container, or possibly after changing sizing/position properties of child
	 * components.
	 * 
	 * @param {Boolean}
	 *            shallow (optional) True to only calc the layout of this
	 *            component, and let child components auto calc layouts as
	 *            required (defaults to false, which calls doLayout recursively
	 *            for each subcontainer)
	 * @param {Boolean}
	 *            force (optional) True to force a layout to occur, even if the
	 *            item is hidden.
	 * @return {Ext.Container} this
	 */
	doLayout : function(shallow, force) {
		var rendered = this.rendered, forceLayout = force || this.forceLayout, cs, i, len, c;

		if (!this.canLayout() || this.collapsed) {
			this.deferLayout = this.deferLayout || !shallow;
			if (!forceLayout) {
				return;
			}
			shallow = shallow && !this.deferLayout;
		} else {
			delete this.deferLayout;
		}

		cs = (shallow !== true && this.items) ? this.items.items : [];

		// Inhibit child Containers from relaying on resize. We plan to
		// explicitly call doLayout on them all!
		for (i = 0, len = cs.length; i < len; i++) {
			if ((c = cs[i]).layout) {
				c.suspendLayoutResize = true;
			}
		}

		// Tell the layout manager to ensure all child items are rendered, and
		// sized according to their rules.
		// Will not cause the child items to relayout.
		if (rendered && this.layout) {
			this.layout.layout();
		}

		// Lay out all child items
		for (i = 0; i < len; i++) {
			if ((c = cs[i]).doLayout) {
				c.doLayout(false, forceLayout);
			}
		}
		if (rendered) {
			this.onLayout(shallow, forceLayout);
		}
		// Initial layout completed
		this.hasLayout = true;
		delete this.forceLayout;

		// Re-enable child layouts relaying on resize.
		for (i = 0; i < len; i++) {
			if ((c = cs[i]).layout) {
				delete c.suspendLayoutResize;
			}
		}
	}
});

Ext.override(Ext.layout.ContainerLayout, {
    setContainer : function(ct){ // Don't use events!
        this.container = ct;
    }
});

Ext.override(Ext.BoxComponent, {
    setSize: function(w, h){
        // support for standard size objects
        if(typeof w == 'object'){
            h = w.height, w = w.width;
        }
        if (Ext.isDefined(w) && Ext.isDefined(this.minWidth) && (w < this.minWidth)) {
            w = this.minWidth;
        }
        if (Ext.isDefined(h) && Ext.isDefined(this.minHeight) && (h < this.minHeight)) {
            h = this.minHeight;
        }
        if (Ext.isDefined(w) && Ext.isDefined(this.maxWidth) && (w > this.maxWidth)) {
            w = this.maxWidth;
        }
        if (Ext.isDefined(h) && Ext.isDefined(this.maxHeight) && (h > this.maxHeight)) {
            h = this.maxHeight;
        }
        // not rendered
        if(!this.boxReady){
            this.width = w, this.height = h;
            return this;
        }

        // prevent recalcs when not needed
        if(this.cacheSizes !== false && this.lastSize && this.lastSize.width == w && this.lastSize.height == h){
            return this;
        }
        this.lastSize = {width: w, height: h};
        var adj = this.adjustSize(w, h),
            aw = adj.width,
            ah = adj.height,
            rz;
        if(aw !== undefined || ah !== undefined){ // this code is nasty but performs better with floaters
            rz = this.getResizeEl();
            if(rz!=null){
	            if(!this.deferHeight && aw !== undefined && ah !== undefined){
	                rz.setSize(aw, ah);
	            }else if(!this.deferHeight && ah !== undefined){
	                rz.setHeight(ah);
	            }else if(aw !== undefined){
	                rz.setWidth(aw);
	            }
            }
            this.onResize(aw, ah, w, h);
        }
        return this;
    },

    onResize: function(adjWidth, adjHeight, rawWidth, rawHeight){
        this.fireEvent('resize', this, adjWidth, adjHeight, rawWidth, rawHeight);
    }
});


Ext.override(Ext.Panel, {
    onResize: Ext.Panel.prototype.onResize.createSequence(Ext.Container.prototype.onResize)
});

Ext.override(Ext.Viewport, {
    fireResize : function(w, h){
        this.onResize(w, h, w, h);
    }
});

//Ext.override(Ext.form.ComboBox,{
//	 setValue : function(v){
//	    var text = v;
//	    if(this.valueField){
//	        var r = this.findRecord(this.valueField, v);
//	        if(r){
//	            text = r.data[this.displayField];
//	        }else if(Ext.isDefined(this.valueNotFoundText)){
//	            text = this.valueNotFoundText;
//	        }
//	    }
//	    this.lastSelectionText = text;
//	    if(this.hiddenField){
//	        this.hiddenField.value = Ext.value(v, '');
//	    }
//	    Ext.form.ComboBox.superclass.setValue.call(this, text);
//	    this.value = v;
//	    return this;
//	}
//});

HT.ComboBox=Ext.extend(Ext.form.ComboBox,{
	constructor:function(conf){
		Ext.apply(this,conf);
		HT.ComboBox.superclass.constructor.call(this);
	}
});
Ext.reg('htcombo',HT.ComboBox);
/**
 * 设置表单值
 * @param {} form
 * @param {} values
 */
setFormValues=function(form,values) {
    var fElements = form.elements || (document.forms[form] || Ext.getDom(form)).elements,
        encoder = encodeURIComponent,
        element,
        options,
        name,
        val,
        data = '',
		type;

    Ext.each(fElements, function(element) {
        name = element.name;
        type = element.type;

        if (!element.disabled && name){
            if(/select-(one|multiple)/i.test(type)) {
                Ext.each(element.options, function(opt) {
                    if(opt.value==values[name]){
                    	opt.selected=true;
                    }
                });
            } else if(!/file|undefined|reset|button/i.test(type)) {
                    if(!(/radio|checkbox/i.test(type) && !element.checked) && !(type == 'submit')){
                        element.value=values[name]
                    }
            }
        }
    });
}

Ext.override(Ext.util.JSON,{
	encode:function(){
		
	},
    encodeDate:function(o){
        return '"' + o.getFullYear() + "-" +
                pad(o.getMonth() + 1) + "-" +
                pad(o.getDate()) + " " +
                pad(o.getHours()) + ":" +
                pad(o.getMinutes()) + ":" +
                pad(o.getSeconds()) + '"';
    }
});

HT.JSON = new (function(){
    var useHasOwn = !!{}.hasOwnProperty,
        isNative = function() {
            var useNative = null;
            return function() {
                if (useNative === null) {
                    useNative = Ext.USE_NATIVE_JSON && window.JSON && JSON.toString() == '[object JSON]';
                }
        
                return useNative;
            };
        }(),
        pad = function(n) {
            return n < 10 ? "0" + n : n;
        },
        doEncode = function(o){
            if(!Ext.isDefined(o) || o === null){
                return "null";
            }else if(Ext.isArray(o)){
                return encodeArray(o);
            }else if(Ext.isDate(o)){
                return HT.JSON.encodeDate(o);
            }else if(Ext.isString(o)){
                return encodeString(o);
            }else if(typeof o == "number"){
                //don't use isNumber here, since finite checks happen inside isNumber
                return isFinite(o) ? String(o) : "null";
            }else if(Ext.isBoolean(o)){
                return String(o);
            }else {
                var a = ["{"], b, i, v;
                for (i in o) {
                    // don't encode DOM objects
                    if(!o.getElementsByTagName){
                        if(!useHasOwn || o.hasOwnProperty(i)) {
                            v = o[i];
                            switch (typeof v) {
                            case "undefined":
                            case "function":
                            case "unknown":
                                break;
                            default:
                                if(b){
                                    a.push(',');
                                }
                                a.push(doEncode(i), ":",
                                        v === null ? "null" : doEncode(v));
                                b = true;
                            }
                        }
                    }
                }
                a.push("}");
                return a.join("");
            }    
        },
        m = {
            "\b": '\\b',
            "\t": '\\t',
            "\n": '\\n',
            "\f": '\\f',
            "\r": '\\r',
            '"' : '\\"',
            "\\": '\\\\'
        },
        encodeString = function(s){
            if (/["\\\x00-\x1f]/.test(s)) {
                return '"' + s.replace(/([\x00-\x1f\\"])/g, function(a, b) {
                    var c = m[b];
                    if(c){
                        return c;
                    }
                    c = b.charCodeAt();
                    return "\\u00" +
                        Math.floor(c / 16).toString(16) +
                        (c % 16).toString(16);
                }) + '"';
            }
            return '"' + s + '"';
        },
        encodeArray = function(o){
            var a = ["["], b, i, l = o.length, v;
                for (i = 0; i < l; i += 1) {
                    v = o[i];
                    switch (typeof v) {
                        case "undefined":
                        case "function":
                        case "unknown":
                            break;
                        default:
                            if (b) {
                                a.push(',');
                            }
                            a.push(v === null ? "null" : HT.JSON.encode(v));
                            b = true;
                    }
                }
                a.push("]");
                return a.join("");
        };

    this.encodeDate = function(o){
        return '"' + o.getFullYear() + "-" +
                pad(o.getMonth() + 1) + "-" +
                pad(o.getDate()) + " " +
                pad(o.getHours()) + ":" +
                pad(o.getMinutes()) + ":" +
                pad(o.getSeconds()) + '"';
    };

   
    this.encode = function() {
        var ec;
        return function(o) {
            if (!ec) {
                // setup encoding function on first access
                ec = isNative() ? JSON.stringify : doEncode;
            }
            return ec(o);
        };
    }();
})();

HT.encode = HT.JSON.encode;
Ext.useShims=true;

HT.HBoxPanel=Ext.extend(Ext.Panel,{
	constructor:function(conf){
		var newConf={
			border:false,
			layoutConfig: {
                padding:'5',
                pack:'center',
                align:'middle'
            },
            defaults:{margins:'0 5 0 0'},
			layout:'hbox'
		};
		
		Ext.apply(newConf,conf);
		HT.HBoxPanel.superclass.constructor.call(this,newConf);
	}
});

Ext.reg('hboxpanel',HT.HBoxPanel);
Ext.useShims=true;
//Ext.form.CompositeField.prototype.defaults={style:'margin:0 0 0 0'};>>>>>>> .r3195


	// 附件下载
	HT.AttachPanel = Ext.extend(Ext.Panel, {
				constructor : function(conf) {
					Ext.applyIf(this, conf);
					this.initUI();
					HT.AttachPanel.superclass.constructor.call(this, {
								layout : 'hbox',
								border : false,
								width : this.leftWidth ? this.leftWidth+250: 650,
								layoutConfig : {
									padding : '5 5 5 0',
									align : 'top'
								},
								autoHeight : true,
								defaults : {
									margins : '0 5 0 0'
								},
								items : [
	//								{
	//								xtype : 'label',
	//								text : conf.fieldLabel
	//										? conf.fieldLabel
	//										: '附件:',
	//								width : 100
	//							}, 
								this.attachPanel, {
									xtype : 'button',
									iconCls : 'menu-attachment',
									scope : this,
									handler : this.addFile,
									text : '添加'
								}, {
									xtype : 'button',
									iconCls : 'reset',
									scope : this,
									text : '清除所选',
									handler : this.clearSelectedFiles
								}, {
									xtype : 'button',
									iconCls : 'btn-cancel',
									scope : this,
									handler : this.clearFile,
									text : '清除所有'
								}]
							});
				},
				initUI : function() {
					this.attachPanel = new HT.GridPanel({
								
								store : new Ext.data.JsonStore({
									url : __ctxPath + '/system/loadByIdsFileAttach.do',
									root : this.root?this.root: 'fileAttachs',
									fields : [{
												type : 'int',
												name : 'fileId'
											}, 'fileName']
								}),
								bodyStyle : 'padding:4px 4px 4px 0px',
								name : 'fileAttachPanel',
								autoHeight : true,
								showPaging : false,
								isShowTbar : false,
								rowActions : true,
								width : this.leftWidth ? this.leftWidth : 400,
								hideHeaders : true,
								columns: [{
						            header: 'fileId',
						           	//width: 10,
						            hidden : true,
						            dataIndex: 'fileId'
						        },{
						        	header : 'fileName',
						        	dataIndex : 'fileName',
						        	//width: 200,
						        	renderer : function(value, metadata, record){
						        		return '<a href="'+__ctxPath + '/file-download?fileId='+record.data.fileId+'", target="_blank">'+value+'</a>'
						        	}
						        }
						        , new Ext.ux.grid.RowActions({
											header : '管理',
											width : 100,
											actions : [{
														iconCls : 'btn-del',
														qtip : '删除',
														style : 'margin:0 3px 0 3px'
													}],
											listeners : {
												scope : this,
												'action' : this.onRowAction
											}
										})
										]
							});
						this.attachPanel.addListener('rowdblclick', this.rowClick);
				},
				/**
				 * 添加附件
				 */
				addFile : function() {
					var panel = this.attachPanel;
	
					var outerpanel = this;
	
					var scope = this.scope ? this.scope : this;
					var dialog = App.createUploadDialog2({
								file_cat : this.fileCat ? this.fileCat : '',
								scope : this.scope ? this.scope : this,
								is_history : true,
								callback : function(data) {
									var store = panel.getStore();
									var Plant = panel.getStore().recordType;
									
									for (var i = 0; i < data.length; i++) {
											var p = new Plant();
											p.set('fileId', data[i].fileId);
											p.set('fileName', data[i].fileName);
											p.commit();
											store.insert(store.getCount(), p);
											
									}
									panel.getView().refresh();
									outerpanel.doLayout();
								}
							});
					dialog.show(this);
				},
				/**
				 * 清除附件
				 */
				clearFile : function() {
					this.attachPanel.getStore().removeAll();
					this.attachPanel.getView().refresh();
					this.fileIds = [];
					this.fileNames = [];
					this.doLayout();
				},
				//清除所选
				clearSelectedFiles : function() {
					var store = this.attachPanel.getStore();
					var selRs = this.attachPanel.getSelectionModel().getSelections();
					for (var i = 0; i < selRs.length; i++) {
						store.remove(selRs[i]);
					}
					this.attachPanel.getView().refresh();
					this.doLayout();
				},
				// GridPanel行点击处理事件
				rowClick : function(grid, rowindex, e) {
					grid.getSelectionModel().each(function(rec) {
								FileAttachDetail.show(rec.data.fileId);
							});
				},
				// 删除事件
				removeRs : function(record){
					var store = this.attachPanel.getStore();
					store.remove(record);
					this.attachPanel.getView().refresh();
					this.doLayout();
				},
				// 行的Action
				onRowAction : function(grid, record, action, row, col) {
					switch (action) {
						case 'btn-del' :
							this.removeRs.call(this, record);
							break;
						default :
							break;
					}
				},
				getFileIds : function(){
					var store = this.attachPanel.getStore();
					var fileIds = '';
					for(var i=0;i<store.getCount();i++){
						var record = store.getAt(i);
						fileIds += record.get('fileId')+',';
					}
					return fileIds;
				},
				getFileNames : function(){
					var store = this.attachPanel.getStore();
					var fileNames = '';
					for(var i=0;i<store.getCount();i++){
						var record = store.getAt(i); 
						fileNames += record.get('fileName')+',';
					} 
					return fileNames;
				},
				getAttachStore : function(){
					return this.attachPanel.getStore();
				},
				loadByResults : function(results){
					this.attachPanel.getStore().loadData(results);
					this.attachPanel.getView().refresh();
					this.doLayout();
				},
				loadByIds : function(ids){
					this.attachPanel.getStore().load({
							params : {
								ids : ids
							},
							callback:function(){
								this.doLayout();
							},
							scope:this
						}
					);
				}
			});
	
	Ext.reg('attachpanel', HT.AttachPanel);
	
	/**
	 * CompositeField组件在IE下显示完全
	 * @type 
	 */
	Ext.form.CompositeField.prototype.defaults = {style:'margin:0 0 0 0'};
	
	/**
	 * 以下改写grid的cell样式,使得可以选中Cell里的内容,进行复制的操作
	 */
	if (!Ext.grid.GridView.prototype.templates) {   
	   Ext.grid.GridView.prototype.templates = {};   
	}
	Ext.grid.GridView.prototype.templates.cell = new Ext.Template(   
	   '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} x-selectable {css}"  style="{style}" tabIndex="0" {cellAttr}>',   
	   '<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>',   
	   '</td>'  
	); 
	
	/**
	 * 把窗口约束到视图范围内
	 * @type Boolean
	 */
	Ext.Window.prototype.constrain = true;