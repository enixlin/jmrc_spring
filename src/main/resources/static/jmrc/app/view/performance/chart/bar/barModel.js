Ext.define('jmrc.view.performance.chart.bar.barModel', {
	extend : 'Ext.app.ViewModel',
	alias : 'viewmodel.performance-chart-bar-bar',

	stores : {


		unitSettleMonthStore:{
			fields : [ {
				name : '月份',
				type : 'string',
				mapping : "month",
			}, {
				name : '业务笔数',
				type : 'number',
				mapping : "times",
			}, {
				name : '业务金额<br>(万美元)',
				type : 'number',
				mapping : "amount",
			}, ],
			autoLoad : true,
			proxy : {
				url : "/settlerecord/getUnitSettleMonthPerformace",
				type : "ajax",

			},
		},
		
		unitMonthBarStore : {
			fields : [ {
				name : '月份',
				type : 'string',
				mapping : "name",
			}, {
				name : '业务笔数',
				type : 'number',
				mapping : "times",
			}, {
				name : '业务金额<br>(万美元)',
				type : 'number',
				mapping : "performance",
			}, ],
			autoLoad : true,
			proxy : {
				url : "/settlerecord/getUnitMonthPerformace",
				type : "ajax",

			},
		},
		monthBarStore : {

			fields : [ {
				name : '月份',
				type : 'string',
				mapping : "name",
			}, {
				name : '业务笔数',
				type : 'number',
				mapping : "times",
			}, {
				name : '业务金额<br>(万美元)',
				type : 'number',
				mapping : "performance",
			},

			],
			autoLoad : true,
			proxy : {
				url : "/settlerecord/getMonthPerformance",
				type : "ajax",

			},

		},
		getAllBusyTypeProformance : {
			autoLoad : true,
			fields : [ {
				name : '产品名称',
				type : 'string',
				mapping : "name",
			}, {
				name : '业务笔数',
				type : 'number',
				mapping : "times",
			}, {
				name : '业务金额<br>(万美元)',
				type : 'number',
				mapping : "performance",
			}, ],
			proxy : {
				url : "/settlerecord/getAllBusyTypeProformance",
				type : "ajax",
			}
		}
	}

});
