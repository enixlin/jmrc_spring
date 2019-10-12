Ext.define('jmrc.view.performance.settle.settleModel', {
	extend : 'Ext.app.ViewModel',
	alias : 'viewmodel.performance-settle-settle',
	data : {
		name : 'jmrc'
	},
	stores : {
		reportTypeBoxStore : {
			fields : [ 'name', 'id' ],
			autoLoad:true,
			proxy : {
				url : "/report/getReportType",
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
