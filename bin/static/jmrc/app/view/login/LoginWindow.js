Ext.define('jmrc.view.login.LoginWindow', {
	extend : 'Ext.window.Window',
	xtype : 'loginwindow',
	title : '江门农商行国际业务信息系统',
	reference : 'loginwin',
	title : CFG.getAppName(),
	iconCls : 'x-fa fa-tv',
	width : 320,
	height : 250,
	margin : '5 5 5 5',
	layout : "auto",
	x : 700,
	y : 160,
	autoShow : true,
	// border: true,
	style : {
		border : '4px solid #5fa2dd',
		borderRadius : '5px'
	},

	items : [ {
		xtype : 'combo',
		// xtype: 'textfield',
		store : Ext.create('jmrc.store.User'),
		Width : 290,
		minWidth : 290,
		labelAlign : 'right',
		labelWidth : 80,
		valueField : 'id',
		// pageSize: 2,
		// listConfig: { minWidth: 300, },
		displayField : 'name',
		emptyText : "用户名称",
		blankText : '用户名称不能为空！',
		margin : '20 10 10 10',
		name : 'id',
		allowBlank : false
	}, {

		Width : 290,
		labelAlign : 'right',
		xtype : 'textfield',
		minWidth : 290,
		emptyText : "用户密码",
		blankText : '用户密码不能为空！',
		margin : '10 10 10 10',
		inputType : "password",
		name : 'password',
		id : 'password',
		allowBlank : false
	}, {
		Width : 250,
		margin : '10 10 10 10',
		labelWidth : 250,
		columnWidth : 1,
		hidden : true,
		border : 0,
		xtype : 'label',
		id : 'tip',
	},

	],
	

	buttons : [
		
		{xtype:"tbfill"},
	// Reset and Submit buttons
	{
		// xtype: 'button',
		text : '重置',
		width : 80,
		iconCls : 'x-fa fa-remove',
		margin : '10 25 5 10',
		handler : "onreset"
	}, {
		// xtype: 'button',
		text : '登录',
		iconCls : 'x-fa fa-home',
		width : 80,
		formBind : true, // only enabled once the form is valid
		disabled : true,
		margin : '10 25 5 10',
		handler : "onlogin"
	} ]

});