Ext
		.define(
				'jmrc.view.performance.chart.bar.bar',
				{
					extend : 'Ext.panel.Panel',
					xtype : "barChart",

					requires : [
							'jmrc.view.performance.chart.bar.barController',
							'jmrc.view.performance.chart.bar.barModel' ],

					controller : 'performance-chart-bar-bar',
					viewModel : {
						type : 'performance-chart-bar-bar'
					},

					items : [ {
						xtype : 'cartesian',
						reference : 'chart',
						width : '100%',
						height : 500,
						insetPadding : 40,
						flipXY : true,
						interactions : {
							type : 'itemedit',
							style : {
								lineWidth : 2
							},
							tooltip : {
								renderer : 'onItemEditTooltipRender'
							}
						},
						animation : {
							easing : 'easeOut',
							duration : 500
						},
						store : {
							fields : [ 'performance', 'name', 'date' ],
							//autoLoad:true,
							proxy : {
								url : "/settlerecord/getAllBusyTypeProformance",
								type : "ajax",
							
							}

						},
						axes : [ {
							type : 'numeric',
							position : 'bottom',
							fields : 'performance',
							grid : true,
							maximum : 4000000,
							majorTickSteps : 10,
							title : 'Billions of USD',
							renderer : 'onAxisLabelRender'
						}, {
							type : 'category',
							position : 'left',
							fields : 'name',
							grid : true
						} ],
						series : [ {
							type : 'bar',
							xField : 'name',
							yField : 'performance',
							style : {
								opacity : 0.80,
								minGapWidth : 10
							},
							highlightCfg : {
								strokeStyle : 'black',
								radius : 10
							},
							label : {
								field : 'name',
								display : 'insideEnd',
								renderer : 'onSeriesLabelRender'
							},
							tooltip : {
								trackMouse : true,
								renderer : 'onSeriesTooltipRender'
							}
						} ],
						sprites : [
								{
									type : 'text',
									text : 'Industry size in major economies (2011)',
									fontSize : 22,
									width : 100,
									height : 30,
									x : 40, // the sprite x position
									y : 20
								// the sprite y position
								},
								{
									type : 'text',
									text : 'Source: http://en.wikipedia.org/wiki/List_of_countries_by_GDP_sector_composition',
									fontSize : 10,
									x : 12,
									y : 490
								} ]
					} ],

			

				});
