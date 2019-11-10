Ext.define('jmrc.view.performance.exchange.totaldetail', {
	extend : 'Ext.panel.Panel',
	xtype : "exchangetotaldetail",
	requires : [ 'jmrc.view.performance.exchange.totaldetailController',
			'jmrc.view.performance.exchange.totaldetailModel' ],

	controller : 'performance-exchange-totaldetail',
	viewModel : {
		type : 'performance-exchange-totaldetail'
	},

	html : 'Hello, exchangetotaldetail!!'
});
