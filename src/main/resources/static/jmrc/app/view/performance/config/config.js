Ext.define('jmrc.view.performance.config.config', {
	extend : 'Ext.panel.Panel',
	xtype : "performanceConfig",

	requires : [ 'jmrc.view.performance.config.configController',
			'jmrc.view.performance.config.configModel' ],

	controller : 'performance-config-config',
	viewModel : {
		type : 'performance-config-config'
	},
	// tbar : [ "->"

	width : "100%",
	height : 600,
	tbar : [ "->", {
		type : "button",
		text : "保存",
		handler : "saveRange"
	} ],
	


});
