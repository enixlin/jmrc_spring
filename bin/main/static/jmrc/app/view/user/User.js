Ext.define('jmrc.view.user.User', {
	extend : 'Ext.panel.Panel',
	xtype : "user",

	requires : [ 'jmrc.view.user.UserController', 'jmrc.view.user.UserModel' ],

	controller : 'user-user',
	viewModel : {
		type : 'user-user'
	},

	frame : true,
	tbar : [ {
		text : "create user",handler:"showAddUI"
	} ],
	width : window.innerWidth * 70,
	items : [ {
		xtype : "grid",
		margin : 5,
		layout : "fit",
		frame : true,
		store : Ext.create("jmrc.store.UserStore"),
		bbar : {
			xtype : "pagingtoolbar",
			displayInfo : true
		},
		columns : [ {
			text : "用户编号",
			dataIndex : "id",
			columnWidth : 0.2
		}, {
			text : "用户名称",
			dataIndex : "name",
			columnWidth : 0.3
		}, {
			text : "密码",
			dataIndex : "password",
			columnWidth : 0.3
		}, {
			text : "用户状态",
			dataIndex : "status",
			columnWidth : 0.1
		}, {
			text : "操作",
			columnWidth : 0.1
		} ]
	} ]

});
