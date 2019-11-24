Ext
		.define(
				'jmrc.view.performance.chart.customerbarchart.barchartController',
				{
					extend : 'Ext.app.ViewController',
					alias : 'controller.performance-chart-customerbarchart-barchart',

					afterRender : function() {
						let me = this;
						let view = me.getView();
						// 获取配置参数对象
						let config = view.config.data;
						let store = config.store;
						let width = config.width;
						let height = config.height;
						let layout = config.layout;

						let container = Ext.create({
							xtype : "panel",
							width : width,
							height : height,
							layout : layout,
							scrollable : true,
						});

						let chart = Ext.create({
							xtype : "cartesian",
							reference : "chart",
							renderTo :document.body,
							columnWidth : config.chartWidth,
							height : height * .9,
							store : store,
							tbar : [ {
								text : "生成图像",
								platformConfig : {
									desktop : {
										text : "生成图像"
									}
								},
								handler : "onPreview"
							} ],
							// 设定图表内图型中的标题
							sprites : {
								type : "text",
								text : config.title.name,
								fontSize : 15,
								width : config.title.width,
								height : config.title.height,
								x : config.title.x,
								y : config.title.y,
							},
							insetPadding : {
								top : config.insetPadding.top,
								bottom : config.insetPadding.bottom,
								left : config.insetPadding.left,
								right : config.insetPadding.right
							},

							axes : [ {
								type : config.xAxes.type,
								position : config.xAxes.position,
								title : {
									text : config.xAxes.title.name,
									fontSize : config.xAxes.title.fontSize,
								},
								grid : true,
								fields : config.xAxes.field,
							}, {
								type : config.yAxes.type,
								position : config.yAxes.position,
								title : {
									text : config.yAxes.title.name,
									fontSize : config.yAxes.title.fontSize,
								},
								grid : true,
								fields : config.yAxes.field,
							}, ],
							series : {
								type : "bar",
								subStyle : {
									fill : config.subStyle.fill,
									stroke : config.subStyle.stroke
								},
								xField : config.xAxes.field,
								yField : config.yAxes.field,
								// yValue: config.yAxes.field,
								highlight : {
									strokeStyle : config.highlight.strokeStyle,
									fillStyle : config.highlight.fillStyle,
								},
								label : {
									field : config.yAxes.field,
									display : "insideEnd",
									renderer : "onSeriesLabelRender"
								},
								tooltip : {
									trackMouse : true,
									renderer : "onTooltipRender"
								}
							}

						});

						let fields = store.getModel().getFields();
						let cos = [];

						for (let n = 0, len = fields.length; n < len; n++) {
							if (fields[n].type == "number") {
								cos.push({
									text : fields[n].name,
									// width: (0.8 / (len - 1)) * 100 + "%",
									dataIndex : fields[n].name,
									renderer : function(value) {
										return Ext.util.Format.number(value,
												"0,000.00");
									}
								});
							}
							if (fields[n].type == "string") {
								cos
										.push({
											text : fields[n].name,
											// width: (0.8 / (len - 1)) * 100 +
											// "%",
											dataIndex : fields[n].name,
											renderer : function(value) {
												return "<center>" + value
														+ "</center>";
											}
										});
							}
						}
						let grid = Ext.create({
							xtype : "grid",
							store : store,
							columnWidth : config.gridWidth,
							height : height * .8,
							scrollable : true

						});
						grid.setColumns(cos);
						if (config.grid.show == true) {
							container.add(chart);
							container.add(grid);
							view.add(container);
						} else {

							container.add(chart);
							view.add(container);
						}

						store.load({
							params : config.params
						});
					},

					onPreview : function() {
						if (Ext.isIE8) {
							Ext.Msg
									.alert("Unsupported Operation",
											"This operation requires a newer version of Internet Explorer.");
							return;
						}
						var chart = this.lookupReference("chart");
						chart.preview();
					},

					onDownload : function() {
						if (Ext.isIE8) {
							Ext.Msg
									.alert("Unsupported Operation",
											"This operation requires a newer version of Internet Explorer.");
							return;
						}
						var chart = this.lookupReference("chart");
						if (Ext.os.is.Desktop) {
							chart.download({
								filename : "Redwood City Climate Data Chart"
							});
						} else {
							chart.preview();
						}
					},
					// 格式化柱形图的数据标签
					onSeriesLabelRender : function(v) {
						return Ext.util.Format.number(v, "0,000");
					},

					onTooltipRender : function(tooltip, record, item) {
						let me = this;
						let view = me.getView();
						let config = view.config.data;
						let perform = Ext.util.Format.number(record
								.get("amount"), "0,000");
						tooltip.setHtml(record.get("month") + "业务量为：" + perform
								+ "万美元,业务笔数为：" + record.get("times"));
					},
					onAxisLabelRender : function(axis, label, layoutContext) {
						return Ext.util.Format.number(layoutContext
								.renderer(label) / 1000, "0,000");
					},

				});