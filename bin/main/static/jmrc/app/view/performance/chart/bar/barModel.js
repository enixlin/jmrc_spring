Ext.define('jmrc.view.performance.chart.bar.barModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.performance-chart-bar-bar',
    data: {
        name: 'jmrc'
    },
    stores:{
    	barStore:{
    		fields : [ 'name', 'date', 'performance' ],
		
	
			proxy : {
				url : "/settlerecord/getAllBusyTypeProformance",
				type : "ajax",
			

			}
    	}
    }

});
