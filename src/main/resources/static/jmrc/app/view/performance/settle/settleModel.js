Ext.define('jmrc.view.performance.settle.settleModel', {
	extend : 'Ext.app.ViewModel',
	alias : 'viewmodel.performance-settle-settle',
	data : {
		name : 'jmrc'
	},
	stores : {
		reportTypeBoxStore : {
			fields : [ 'name', 'id' ],
			params : {},
			proxy : {
				url : "/report/getReportType",
				type : "ajax"
			}
		},
		allTypeBusyPerformanceStore : {
			fields : [ 'name', 'date', 'performance' ],
			autoLoad : true,
			proxy : {
				url : "/settlerecord/getAllBusyTypeProformance",
				type : "ajax",

			}
		}
	}

});
