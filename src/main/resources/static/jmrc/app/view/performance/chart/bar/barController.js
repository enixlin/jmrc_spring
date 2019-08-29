Ext
		.define(
				"jmrc.view.performance.chart.bar.barController",
				{
					extend : "Ext.app.ViewController",
					alias : "controller.performance-chart-bar-bar",

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

					afterRender : function() {
						let me = this;
						let polar = me.getView().query("cartesian")[0];

						let axes = polar.getAxes();
						let bar = polar.getSeries();
						let sprites = {
							type : "text",
							text : me.getView()["config"]["data"]["title"]
									.replace("（一级支行）", "").replace("本部", ""),
							fontSize : 20,
							width : 100,
							height : 30,
							x : 250,
							y : 20
						};
						axes[0]
								.setTitle(me.getView()["config"]["data"]["yAxis"]);
						axes[1]
								.setTitle(me.getView()["config"]["data"]["xAxis"]);
						bar[0]
								.setTitle(me.getView()["config"]["data"]["title"]);
						// 设定标题
						polar.setSprites(sprites);
						// 设定bar图的store

						let startDay = me.getView()["config"]["data"]["start"];
						let endDay = me.getView()["config"]["data"]["end"];
						let unitType = me.getView()["config"]["data"]["unitType"];
						let branchCode = me.getView()["config"]["data"]["uid"];
						let branchName = me.getView()["config"]["data"]["name"];

						let st = me.getView()["config"]["data"]["st"];
						let configData = me.getView()["config"]["data"];
						let grid = me.getView().query("grid")[0];
						let bindStore = me.getViewModel().getStore(st);

						// 设定柱型图的数据集
						polar.bindStore(bindStore);
						// 设定表格的数据集
						grid.bindStore(bindStore);

						// 根据数据集的filed数组，确定表格的列
						let fields = bindStore.getModel().getFields();
						console.log(fields);
						let columns = grid.getColumns();
						let cos = [];
						for (let n = 0, len = fields.length; n < len; n++) {
							if (fields[n].name != "id") {
								if (fields[n].type == "number"
										&& fields[n].name != "业务笔数") {
									cos.push({
										text : fields[n].name,
										width : (1 / (len - 1)) * 100 + "%",
										dataIndex : fields[n].name,
										renderer : function(value) {
											return Ext.util.Format.number(
													value, "0,000.00");
										}
									});
								} else if (fields[n].type == "number"
										&& fields[n].name == "业务笔数") {
									cos.push({
										text : fields[n].name,
										width : (1 / (len - 1)) * 100 + "%",
										dataIndex : fields[n].name,
										renderer : function(value) {
											return Ext.util.Format.number(
													value, "0,000");
										}
									});
								}

								if (fields[n].type != "number") {
									cos.push({
										text : fields[n].name,
										width : (1 / (len - 1)) * 100 + "%",
										dataIndex : fields[n].name
									});
								}
							}
						}
						grid.setColumns(cos);

						// 载入数据
						polar.getStore(st).load({
							params : {
								start : startDay,
								end : endDay,
								unitType : unitType,
								name : branchName,
								uid : branchCode
							}
						});

						// 添加toolbar
						let bbar = Ext.create("Ext.toolbar.Toolbar", {
							dock : "top",
							items : [ "->", {
								xtype : "label",
								text : "a"
							}, {
								xtype : "label",
								text : "b"
							}, ]
						});

						grid.add(bbar);
						console.log(polar);
					},
					// 格式化柱形图的数据标签
					onSeriesLabelRender : function(v) {
						return Ext.util.Format.number(v, "0,000");
					},

					onTooltipRender : function(tooltip, record, item) {
						let me = this;
						let perform = Ext.util.Format.number(record
								.get("performance"), "0,000");
						tooltip.setHtml(record.get("name") + "业务量为：" + perform
								+ "万美元,业务笔数为：" + record.get("times"));
					},
					onAxisLabelRender : function(axis, label, layoutContext) {
						return Ext.util.Format.number(layoutContext
								.renderer(label) / 1000, "0,000");
					},

					onRowclick : function(me, record, element, rowIndex, e,
							eOpts) {
						alert(record.data["name"]);
					},
					showwindow : function(record) {
						let month = record.data["name"] || record.data["机构名称"];
						let view = this.getView();
						// let piechart =
						// me.up().up().up().query("pieChart")[0];
						let win = Ext.create("Ext.window.Window", {
							width : 800,
							height : 900,
							maximizable : true,
							// maximized:true,
							maxWidth : 1000,
							maxHeight : 1000,
							scrollable : true,
							title : month + "月份国际结算产品分类明细"
						});
						view.add(win);
						win.show();
						let pie = Ext.create(
								"jmrc.view.performance.chart.pie.pie", {
									width : window.innerWidth * 0.4,
									data : {
										title : "国际结算产品分类统计图",
										st : "getAllBusySettleTypeProformance",
										start : month + "01",
										end : month + "31"
									}
								});
						win.add(pie);
					},

					getDateRange : function() {
					},

					/**
					 * 
					 * 
					 * 
					 * 保存导出数据表格数据
					 * 
					 * 
					 * 
					 */
					exportToXlsx : function() {
					}
				});
