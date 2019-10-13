Ext.define('jmrc.view.performance.detail.clientlistModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.performance-detail-clientlist',
    data: {
        name: 'jmrc'
    },
    
    stores:{
    	getUnitClientPerformanceStore:{
    		field:[
    			{name:"cust_name",type:"string",dataIndex:"cust_name"},
    			{name:"cust_code",type:"string",dataIndex:"cust_number"},
    			{name:"amount",type:"number",dataIndex:"amount"},
    			{name:"times",type:"number",dataIndex:"times"},
    		],
    		proxy:{
    			url:"/settlerecord/getUnitClientPerformance",
    			type : "ajax",
    		}
    	}
    }

});
