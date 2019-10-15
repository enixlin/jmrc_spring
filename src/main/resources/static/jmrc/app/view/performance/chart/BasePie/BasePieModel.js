Ext.define('jmrc.view.performance.chart.BasePie.BasePieModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.performance-chart-basepie-basepie',
    data: {
        name: 'jmrc'
    },

    stores:{
    	UnitProductPerformanceStore:{
            fields : [ {
				name : '产品',
				type : 'string',
				mapping : "product_name",
			}, {
				name : '业务笔数',
				type : 'number',
				mapping : "times",
			}, {
				name : '金额',
				type : 'number',
				mapping : "amount",
			}, ],
			// autoLoad : true,
			proxy : {
				url : "/settlerecord/getUnitProductPerformance",
				type : "ajax",

			},
        },
        ClientProductPerformanceStore:{
            fields : [ {
				name : '产品',
				type : 'string',
				mapping : "product_name",
			}, {
				name : '业务笔数',
				type : 'number',
				mapping : "times",
			}, {
				name : '金额',
				type : 'number',
				mapping : "amount",
			}, ],
			// autoLoad : true,
			proxy : {
				url : "/settlerecord/getClientProductPerformance",
				type : "ajax",

			},
        	
        }
    },

});
