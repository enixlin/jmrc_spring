Ext.define('jmrc.view.performance.chart.bar.bar', {
	extend : 'Ext.panel.Panel',
	xtype : "barChart",

	requires : [ 'jmrc.view.performance.chart.bar.barController',
			'jmrc.view.performance.chart.bar.barModel' ],

	controller : 'performance-chart-bar-bar',
	viewModel : {
		type : 'performance-chart-bar-bar'
	},
	width : "100%",
	height : window.innerHeight - 600,

	tbar : [

	{
		text : 'Preview',
		platformConfig : {
			desktop : {
				text : 'Download'
			}
		},
		handler : 'onDownload'
	}, ],
	items : [ {
		xtype : 'cartesian',
		renderTo : document.body,
		reference : 'chart',
		width : "100%",
		height : 300,

		insetPadding : '5 10 10 10',
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
			}
		},

		animation : Ext.isIE8 ? false : true,
	},

	// 以下是柱形图下方的表格
	{
		xtype : "grid",
		bind : {
			store : "{monthBarStore}"
		},
		width : "100%",
		height : 300,

		columns : [ {
			text : "月份",
			dataIndex : "name",
		},

		{
			text : "业务笔数",
			dataIndex : "times"
		},

		{
			text : "金额",
			dataIndex : "performance",
			renderer : function(value) {
				return Ext.util.Format.number(value, "0,000.00");;
				}
		} ]
	}

	]

});
