Ext.define('jmrc.view.performance.config.configModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.performance-config-config',
    data: {
        name: 'jmrc'
    },
    
    stores:{
    	allProductStore:{
    		fields : [ 'name',"id" ],
			proxy : {
				url : "/settlerecord/getAllProductFromSettleRecord",
				type : "ajax",
			}
    	},
    	rangeProductStore:{
    		fields : [ 'name','id','description','isSettleRange' ],
			proxy : {
				url : "/settlerecord/getSettleRangeProduct",
				type : "ajax",
			}
    	}
    }

});
