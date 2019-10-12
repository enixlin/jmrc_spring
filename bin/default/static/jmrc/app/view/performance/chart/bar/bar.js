Ext.define('jmrc.view.performance.chart.bar.bar', {
	extend : 'Ext.panel.Panel',
	xtype : "barChart",

	requires : [ 'jmrc.view.performance.chart.bar.barController',
			'jmrc.view.performance.chart.bar.barModel',

	],

	controller : 'performance-chart-bar-bar',
	viewModel : {
		type : 'performance-chart-bar-bar'
	},
	layout : "hbox",
	width : "100%",
	// margin:"30 30 30 60",
	height : window.innerHeight * 0.5,
	scrollable : true,
	tbar : [

	{
		text : 'Preview',
		platformConfig : {
			desktop : {
				text : '下载柱状图表'
			}
		},
		handler : 'onPreview'
	}, {
		text : 'Preview',
		platformConfig : {
			desktop : {
				text : '保存表格'
			}
		},
		handler : "exportToXlsx"
	}, ],

	items : [ {
		xtype : 'cartesian',
		// id : "barchart",
		renderTo : document.body,
		reference : 'chart',
		width : "40%",
		height : window.innerHeight * 0.4,
		margin : 10,
		border : 2,
		// padding : "0 5 0 5 ",
		// 这个图型在整个bar组件内部的边界
		insetPadding : '60 10 0 10',
		axes : [

		{
			type : 'numeric',
			position : 'left',
			title : {
				text : "",
				fontSize : 15
			},
			fields : 'performance',
			minimum : 0
		}, {
			type : 'category',
			position : 'bottom',
			title : {
				text : "",
				fontSize : 15
			},
			fields : 'name'
		}

		],

		plugins : {
			ptype : 'chartitemevents',
			moveEvents : true
		},
		series : {
			type : 'bar',
			subStyle : {
				fill : [ '#abcdef' ],
				stroke : '#1F6D91'
			},
			xField : 'name',
			yField : 'performance',
			yValue : "performance",
			highlight : {
				strokeStyle : 'light',
				fillStyle : 'gold'
			},
			label : {
				field : 'performance',
				display : 'insideEnd',
				renderer : 'onSeriesLabelRender'
			},
			tooltip : {
				trackMouse : true,
				renderer : "onTooltipRender"
			},

			listeners : {
				itemclick : function(series, item, event, eOpts) {

					console.log(item.record.data.name);
					let view = Ext.ComponentQuery.query("barChart")[0];
					let record = item.record;
					console.log(view);
					// console.log(this.getViewContrller().controller);
					// series.up();
					// onRowclick();
					view.controller.showwindow(record);

				}
			}
		},

		animation : Ext.isIE8 ? false : true,
	},

	// 以下是柱形图下方的表格
	{
		xtype : "grid",
		margin : 10,
	
//		bbar:["->",
//			{xtype:"label",text:"笔数合计"},
//			{xtype:"label",text:"金额合计"},
//			
//			],
		border : 2,

		width : "40%",
		height : window.innerHeight * 0.4,
		scrollable : true,
		columnLines : true,
		listeners : {
			rowclick : function(me, record, element, rowIndex, e, eOpts) {
				let view = Ext.ComponentQuery.query("barChart")[0];
				view.controller.showwindow(record);

			}
		}

	}

	]

});
