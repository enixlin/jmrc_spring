Ext.define('jmrc.view.performance.detail.detail', {
	extend : 'Ext.panel.Panel',
	xtype : "recordDetailGrid",

	requires : [ 'jmrc.view.performance.detail.detailController',
			'jmrc.view.performance.detail.detailModel' ],

	controller : 'performance-detail-detail',
	viewModel : {
		type : 'performance-detail-detail'
	},

	tbar : [ "->", {
		xtype : "button",
		text : "导出表格",
			handler:"exportExcel",
	} ],
	// width:window.innerWidth,
	// heigth:window.innerHeight*.5,
	// layout:"fit",
	margin : 5,

	// width:"100%",
	// layout:"fit",
	scrollable : true,

});
