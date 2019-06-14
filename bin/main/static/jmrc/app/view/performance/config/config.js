Ext.define('jmrc.view.performance.config.config', {
	extend : 'Ext.panel.Panel',
	xtype : "performanceConfig",

	requires : [ 'jmrc.view.performance.config.configController',
			'jmrc.view.performance.config.configModel' ],

	controller : 'performance-config-config',
	viewModel : {
		type : 'performance-config-config'
	},
	tbar : [ "->", {
		xtype : "combo",
		bind : {
			store : "{settleRangeStore}"
		},
		displayField : "name"
	}, {
		xtype : "button",
		text : "打开设置界面"
	} ],
	items : [ {
		xtype : "grid",
		margin : "5 5 5 5",
		
		width : "100%",
		columns : [ {
			text : "指标名称：",
			dataIndex : "name"
		} ],
	} ]

});
