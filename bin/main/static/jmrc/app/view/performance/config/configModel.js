Ext.define('jmrc.view.performance.config.configModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.performance-config-config',
    data: {
        name: 'jmrc'
    },
    
    stores:{
    	allProductStore:{
    		fields : [ 'name', 'id', 'IsRange' ],
			proxy : {
				url : "/settlerecord/getAllProduct",
				type : "ajax",
			}
    	}
    }

});
