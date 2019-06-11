Ext.define('jmrc.view.performance.chart.bar.barModel', {
	extend : 'Ext.app.ViewModel',
	alias : 'viewmodel.performance-chart-bar-bar',
	data : {
		title : '',
		xAxis : "",
		yAxis : ""
	},

	stores : {
		monthBarStore : {
			fields : [ 'name', 'date', 'performance' ],
			proxy : {
				url : "/settlerecord/getMonthPerformance",
				type : "ajax",
			}
		}
	}

});
