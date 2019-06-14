Ext.define('jmrc.view.performance.chart.pie.pieModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.performance-chart-pie-pie',
    data : {
		title : '',
		xAxis : "",
		yAxis : ""
	},
    
	stores : {

		getAllBusyTypeProformance : {
			fields : [ 'name', 'date', 'performance' ],
			proxy : {
				url : "/settlerecord/getAllBusyTypeProformance",
				type : "ajax",
			}
		}
	}


});
