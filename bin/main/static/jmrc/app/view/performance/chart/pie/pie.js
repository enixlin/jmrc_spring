Ext.define('jmrc.view.performance.chart.pie.pie', {
	extend : 'Ext.panel.Panel',
	xtype : "pieChart",

	requires : [ 'jmrc.view.performance.chart.pie.pieController',
			'jmrc.view.performance.chart.pie.pieModel' ],

	controller : 'performance-chart-pie-pie',
	viewModel : {
		type : 'performance-chart-pie-pie'
	},
	width : "100%",
	height : window.innerHeight - 600,
	tbar : [ "<-", {
		text : "preview",
		handler : "onPreview"
	} ],

	items : [ {
		xtype : 'polar',
		reference : 'chart',
		// theme: 'default-gradients',
		renderTo : document.body,
		width : "100%",
		height : 500,
		insetPadding : 50,
		innerPadding : 20,
		legend : {
			docked : 'left',
			height:400,
			resizable:true,
			scrollable:true,
		},
		interactions : [ 'rotate' ],
		sprites : [ {
			type : 'text',
			text : 'Pie Charts - Basic',
			scrollable:true,
			fontSize : 22,
			width : 100,
			height : 30,
			x : 20, // the sprite x position
			y : 20
		// the sprite y position
		} ],
		series : [ {
			type : 'pie',
			width:"100%",
			angleField : 'performance',
			label : {
				field : 'name',
				calloutLine : {
					length : 60,
					width : 3
				// specifying 'color' is also possible here
				}
			},
			donut : 30,
			highlight : true,
			tooltip : {
				trackMouse : true,
				renderer : 'onSeriesTooltipRender'
			}
		} ]
	} ]

});
