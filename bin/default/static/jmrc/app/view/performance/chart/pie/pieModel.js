Ext.define('jmrc.view.performance.chart.pie.pieModel', {
	extend : 'Ext.app.ViewModel',
	alias : 'viewmodel.performance-chart-pie-pie',
	data : {
		title : '',
		xAxis : "",
		yAxis : ""
	},

	stores : {

		getAllBusyTypeProformance : {

			fields : [ {
				name : '产品名称',
				type : 'string',
				mapping : "name",
			}, {
				name : '业务笔数',
				type : 'number',
				mapping : "times",
			}, {
				name : '业务金额',
				type : 'number',
				mapping : "performance",
			}, ],
			// autoLoad : true,
			proxy : {
				url : "/settlerecord/getAllBusyTypeProformance",
				type : "ajax",

			},
		},
		getAllBusySettleTypeProformance : {
			fields : [ {
				name : '产品名称',
				type : 'string',
				mapping : "name",
			}, {
				name : '业务笔数',
				type : 'number',
				mapping : "times",
			}, {
				name : '业务金额',
				type : 'number',
				mapping : "performance",
			}, ],
			// autoLoad : true,
			proxy : {
				url : "/settlerecord/getAllBusySettleTypeProformance",
				type : "ajax",

			}

		}
	}
});
