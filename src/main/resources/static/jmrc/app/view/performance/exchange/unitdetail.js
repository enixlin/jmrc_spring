Ext.define('jmrc.view.performance.exchange.unitdetail', {
	extend : 'Ext.panel.Panel',
	xtype : "exchangeunitdetail",
	requires : [ 'jmrc.view.performance.exchange.unitdetailController',
			'jmrc.view.performance.exchange.unitdetailModel' ],

	controller : 'performance-exchange-unitdetail',
	viewModel : {
		type : 'performance-exchange-unitdetail'
	},
	//
	html : 'Hello, exchangeunitdetail!!'
});
