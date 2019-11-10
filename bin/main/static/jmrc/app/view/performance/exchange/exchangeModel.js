Ext.define('jmrc.view.performance.exchange.exchangeModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.performance-exchange-exchange',
    data: {
        name: 'jmrc'
    },
    stores : {
		reportTypeBoxStore : {
			fields : [ 'name', 'id' ],
			autoLoad:true,
			proxy : {
				url : "/report/getExchangeReportType",
				type : "ajax"
			}
		},
		allTypeBusyPerformanceStore : {
			fields : [ 'name', 'date', 'performance' ],
			autoLoad : false,
			proxy : {
				url : "/settlerecord/getAllBusyTypeProformance",
				type : "ajax",

			}
		},
		allUnitSettlePerformance:{
			fields : [ 'name', 'date', 'performance' ],
			autoLoad : false,
			proxy : {
				url : "/settlerecord/allUnitSettlePerformance",
				type : "ajax",
	
			}
		}
	}

});
