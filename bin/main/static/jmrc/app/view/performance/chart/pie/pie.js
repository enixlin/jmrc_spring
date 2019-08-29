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
	height : window.innerHeight - 100,
	tbar : [  {
		text : "下载饼图",
		handler : "onPreview"
	} ,
	{
		text : "下载饼图表格",
		handler : "onPreview"
	} ],
	margin:10,
	layout:"hbox",

	items : [ {
		
		xtype : 'polar',
		reference : 'chart',
//		id:"piechart",
		// theme: 'default-gradients',
		renderTo : document.body,
		width : "45%",
		height : window.innerHeight*0.5,
		margin:10,
		border:2,
		innerPadding:40,
		
//		legend : {
//			docked : 'bottom',
//			height:300,
//			resizable:true,
//			scrollable:true,
//			innerPadding:10,
//		},
		interactions : [ 'rotate' ],
		sprites : [ {
			type : 'text',
			text : 'Pie Charts - Basic',
			scrollable:true,
			fontSize : 22,
			width : 200,
			height : 30,
			x : 10, // the sprite x position
			y :10
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
		} ],
		
	} ,
	
	
	// 以下是柱形图下方的表格
	{
		xtype : "grid",
		
		width : "35%",
		height : window.innerHeight*0.5,
		margin:10,
		border:2,
		scrollable:true,
		columnLines: true, 
		
		listeners : {
			rowclick : function(me, record, element, rowIndex, e, eOpts) {
				let view=me.up().up();
//				let view = Ext.ComponentQuery.query("barChart")[0];
//				view.controller.showwindow(record);
//				let view=me.getView();
//				console.log(view);
				view.controller.showdetail(record);
				console.log(me);

			}
		}

	}

	],
	
	

});
