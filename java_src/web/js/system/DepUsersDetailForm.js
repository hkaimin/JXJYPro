Ext.ns('DepUsersDetailForm');

/**
 * @description 根据userId查询该用户所有的部门信息
 * @class DepUsersDetailForm
 * @author 宏天软件
 * @updater YHZ
 * @company www.jee-soft.cn
 * @createtime 2011-1-15PM
 */
DepUsersDetailForm.show = function(userId,username){
	var win = new Ext.Window({
		title : '员工['+username+']部门信息',
		layout : 'form',
		iconCls : 'menu-department',
		width : 500,
		minWidth : 500,
		height : 200,
		minHeight : 200,
		maximizable : true,
		modal : true,
		autoLoad : {
			url : __ctxPath + '/pages/system/DepUsersDetailView.jsp?userId='+userId
		},
		buttonAlign : 'center',
		buttons : [{
			text : '取消',
			iconCls : 'btn-close',
			handler : function(){
				win.close();
			}
		}],
		keys : {
			key : Ext.EventObject.ENTER,
			fn : function(){
				win.close();
			},
			scope : this
		}
	});
	win.show();
};