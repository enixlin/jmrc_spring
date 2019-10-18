Ext.define('jmrc.view.login.InformationBar', {
	extend : 'Ext.panel.Panel',
	xtype : 'informationbar',

	requires : [ 'jmrc.view.login.InformationBarController',
			'jmrc.view.login.InformationBarModel' ],

	controller : 'login-informationbar',
	viewModel : {
		type : 'login-informationbar'
	},
	width : '100%',
	// layout: 'form',
	align : 'center',

	items : [ {
		xtype : "container",
		layout : "vbox",
		items : [ {
			xtype : 'label',
			text : '江门农商银行国际业务部信息查询系统',
			style:{font:"30px bold",color:"gray"}
		}, 
		{
			xtype : 'label',
			text : 'Jiangmen Rural Commercial Bank Internation Busy Information System',
			style:{font:"10px bold",color:"gray",align:"right"}
		},
		{
			xtype : 'label',
			text : 'ALL RIGHT RELEASE @2019',
			style:{font:"10px bold",color:"gray",align:"right"}
		} ]
	}

	],
// html: "<center><h1> <style='background:red'> 江门农商银行国际业务部信息处理系统
// </style></h1></center>",
});