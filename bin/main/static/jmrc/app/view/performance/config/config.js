Ext.define('jmrc.view.performance.config.config', {
	extend : 'Ext.panel.Panel',
	xtype : "performanceConfig",

	requires : [ 'jmrc.view.performance.config.configController',
			'jmrc.view.performance.config.configModel' ],

	controller : 'performance-config-config',
	viewModel : {
		type : 'performance-config-config'
	},
	// tbar : [ "->", {
	// xtype : "combo",
	// bind : {
	// store : "{settleRangeStore}"
	// },
	//
	// }, {
	// xtype : "button",
	// text : "打开设置界面"
	// } ],

	width : "100%",
	height : 600,
	tbar : [ "->", {
		type : "button",
		text : "保存",
		handler : "saveRange"
	} ],
	

	items : [ {
		xtype : 'fieldset',
		title : '国际结算量统计口径设置',
		layout : 'anchor',
		defaults : {
			anchor : '100%',
			hideEmptyLabel : false
		},
		collapsible : true,
		collapsed : false,
		
		items : [ {
			// Use the default, automatic layout to distribute the controls
			// evenly
			// across a single row
			xtype : 'checkboxgroup',
			margin : "10 10 10 10",
		
			labelPad : 20,
			layout : {
				type : 'table',
				columns : 2
			},
			// fieldLabel : '纳入国际结算量统计口径的产品',
			cls : 'x-check-group-alt',

		} ]

	} ]

});
