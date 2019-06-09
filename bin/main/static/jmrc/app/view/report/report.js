Ext.define('jmrc.view.report.report', {
	extend : 'Ext.panel.Panel',
	xtype : "report",
	requires : [ 'jmrc.view.report.reportController',
			'jmrc.view.report.reportModel' ],

	controller : 'report-report',
	viewModel : {
		type : 'report-report'
	},

	html : 'Hello, report!!'
});
