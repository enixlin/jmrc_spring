Ext.define('jmrc.view.performance.chart.bar.barModel', {
	extend : 'Ext.app.ViewModel',
	alias : 'viewmodel.performance-chart-bar-bar',


	stores : {
		monthBarStore : {
			fields : [ 'name', 'date', 'performance' ,"times"],
			autoLoad : true,
			proxy : {
				url : "/settlerecord/getMonthPerformance",
				type : "ajax",
				extraParams:{
					start:"20190101",
					end:"20190531"
				}
			},
			
		},
		getAllBusyTypeProformance : {
			fields : [ 'name', 'date', 'performance' ],
			proxy : {
				url : "/settlerecord/getAllBusyTypeProformance",
				type : "ajax",
			}
		}
	}

});
