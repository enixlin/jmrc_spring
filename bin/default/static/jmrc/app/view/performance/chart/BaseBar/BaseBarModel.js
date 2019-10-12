Ext.define('jmrc.view.performance.chart.BaseBar.BaseBarModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.performance-chart-basebar-basebar',
    data: {
        name: 'jmrc'
    },
    stores:{
//    	getClientSettleMonthPerformance
    	
    	
        UnitSettleMonthPerformanceStore : {
			fields : [ {
				name : '<center>月份</center>',
				type : 'string',
				mapping : "month",
			}, {
				name : '<center>笔数</center>',
				type : 'number',
				mapping : "times",
			}, {
				name : '<center>金额</center>',
				type : 'number',
				mapping : "amount",
			},

			],
			proxy : {
				url : "/settlerecord/getUnitSettleMonthPerformance",
				type : "ajax",

			},

		},
	     ClientSettleMonthPerformanceStore : {
				fields : [ {
					name : '<center>月份</center>',
					type : 'string',
					mapping : "month",
				}, {
					name : '<center>笔数</center>',
					type : 'number',
					mapping : "times",
				}, {
					name : '<center>金额</center>',
					type : 'number',
					mapping : "amount",
				},

				],
				proxy : {
					url : "/settlerecord/getClientSettleMonthPerformance",
					type : "ajax",

				},

			},
    }

});
