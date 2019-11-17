Ext
		.define(
				"jmrc.view.performance.exchange.totaldetailController",
				{
					extend : "Ext.app.ViewController",
					alias : "controller.performance-exchange-totaldetail",

					afterRender : function() {
						let me = this;
						let view = me.getView();
						let data = view.config.data;
						let start = view.up().query("textfield")[2].getValue()
								.replace(/-/g, "");
						let end = view.up().query("textfield")[3].getValue()
								.replace(/-/g, "");
						data.start = start;
						data.end = end;
						let updatedata = me.getLastUpdateDate();
						let totalPerformace = me
								.getTotalExchangePerformance(data);

						Promise.all([ updatedata, totalPerformace ]).then(
								function(result) {
									data.updatedate = result[0];
									data.totalexchange = Ext.JSON
											.decode(result[1])[0].amount;
									me.makeTotalReport(me, view, data);
								});
					},
					makeTotalReport : function(me, view, data) {
						let width = window.innerWidth * 0.65;
						let height = window.innerHeight * 0.7;
						let summypanel = Ext.create({
							xtype : "panel",
							width : width,
							height : height,
							layout : "column",
						// border:2,
						});
						let information = Ext
								.create({
									xtype : "panel",
									width : width * 0.4,
									height : height * 0.4,
									columnWidth : 0.3,
									margin : "25 0 0 25",
									html : "<center><h3>数据更新日期：<font size=3px color=red>"
											+ data.updatedate.substring(0, 10)
											+ "</font></h3></center>"
											+ "<center><h3>结售汇总量：<font size=3px color=red>"
											+ Ext.util.Format.number(
													data.totalexchange / 10000,
													"0,000.00")
											+ "万美元</font></h3></center>"
								});
						let grid = Ext
								.create({
									xtype : "grid",
									width : width * 0.5,
									height : height * 0.5,
									border : 2,
									columnWidth : 0.7,
									scrollable : true,
									margin : "25 0 0 25",
									columns : [
											{
												text : "<center>结售汇种类</center>",
												dataIndex : "product_name"
											},
											{
												text : "<center>笔数</center>",
												dataIndex : "times",
												renderer : function(v) {
													return Ext.util.Format
															.number(v, "0,000");
												}
											},
											{
												text : "<center>金额<br>(万美元)</center>",
												dataIndex : "amount",
												renderer : function(v) {
													return Ext.util.Format
															.number(v / 10000,
																	"0,000.00");
												}
											},
											{
												text : "<center>笔数<br>（同比）</center>",
												dataIndex : "times_compare",
												renderer : function(v) {
													if (v > 0) {
														return ("<font color=green>"
																+ Ext.util.Format
																		.number(
																				v,
																				"0,000") + "</font>");
													} else {
														return ("<font color=red>"
																+ Ext.util.Format
																		.number(
																				v,
																				"0,000") + "</font>");
													}
												}
											},
											{
												text : "<center>金额<br>（同比）</center>",
												dataIndex : "amount_compare",
												renderer : function(v) {
													if (v > 0) {
														return ("<font color=green>"
																+ Ext.util.Format
																		.number(
																				v / 10000,
																				"0,000.00") + "</font>");
													} else {
														return ("<font color=red>"
																+ Ext.util.Format
																		.number(
																				v / 10000,
																				"0,000.00") + "</font>");
													}
												}
											},
											{
												width : 40,
												xtype : "actioncolumn",
												align : "center",
												items : [ {
													iconCls : "x-fa fa-bar-chart",
													tooltip : "分月明细",
													handler : function(view,
															rowIndex, colIndex,
															item, e, record) {
														let start = view.up()
																.up().up().config.data.start;
														let end = view.up()
																.up().up().config.data.end;
														let product = record.data.product_name;
														view.up().up().up().controller
																.getProductMonthDetail(
																		product,
																		start,
																		end);
													}
												} ]
											},
											{
												width : 40,
												xtype : "actioncolumn",
												align : "center",
												items : [ {
													iconCls : "x-fa fa-users",
													tooltip : "客户明细",
													handler : function(view,
															rowIndex, colIndex,
															item, e, record) {
														let start = view.up()
																.up().up().config.data.start;
														let end = view.up()
																.up().up().config.data.end;
														let product = record.data.product_name;
														view.up().up().up().controller
																.getProductClientDetail(
																		product,
																		start,
																		end);
													}
												} ]
											},
											{
												width : 40,
												xtype : "actioncolumn",
												align : "center",
												items : [ {
													iconCls : "x-fa fa-list",
													tooltip : "业务流水",
													handler : function(view,
															rowIndex, colIndex,
															item, e, record) {
														let start = view.up()
																.up().up().config.data.start;
														let end = view.up()
																.up().up().config.data.end;
														let product = record.data.product_name;
														view.up().up().up().controller
																.getProductDetail(
																		product,
																		start,
																		end);
													}
												} ]
											} ],
									store : Ext.create("Ext.data.Store", {
										fields : [ "product_name", "amount",
												"times" ],

										proxy : {
											url : "/exchange/getTypeTotal",
											type : "ajax"
										}
									})
								});

						/**
						 * 分月明细的柱状图
						 */
						let chart = Ext.create({
							xtype : "cartesian",
							width : width *0.95,
							height : height * 0.4,
							scrollable : true,
						
//							border : 2,
							
							// title: '全行结售汇业务分月明细图',
							sprites : [ {
								type : 'text',
								fillStyle : '#abcdef',
								text : "全行结售汇业务分月明细图",
								fontSize : 25,
								x : width * 0.3,
								y : 30

							} ],
							store : {
								fields : [ "month", "amount", "times" ],
								proxy : {
									url : "/exchange/getTypeTotalMonth",
									type : "ajax"
								}
							},

							axes : [ {
								type : "numeric",
								position : "left",
								title : {
									text : "结售汇业务量（万美元）",
									fontSize : 15
								},
								fields : "amount"
							}, {
								type : "category",
								position : "bottom",
								title : {
									text : "月份",
									fontSize : 15
								},
								fields : "month"
							} ],
							innerPadding : {
								top : 50,
								left : 5,
								right : 5,
								bottom : 0
							},
							insetPadding:{
							    top: 10,
							    left: 10,
							    right: 10,
							    bottom: 10
							},
							series : {
								type : 'bar',
								// margin:60,
								subStyle : {
									fill : [ '#abcdef' ],
									stroke : '#1F6D91'
								},
								xField : 'month',
								yField : 'amount'
							}

						});

						let chartPanel = Ext.create("Ext.panel.Panel", {
							width : "100%",
							scrollable : true,
							height : height * 0.42,
							columnWidth : 1,
							margin:"20 0 0 0" ,
							border:5,

						});
						chartPanel.add(chart);

						summypanel.add(information);
						summypanel.add(grid);
						summypanel.add(chartPanel);
						view.add(summypanel);
						chart.getStore().load({
							params : {
								start : data.start,
								end : data.end
							}
						});

						grid.getStore().load({
							params : {
								start : data.start,
								end : data.end
							}
						});
						//						
						chart.show();

					},

					getLastUpdateDate : function() {
						return new Promise(function(resolve, reject) {
							Ext.Ajax.request({
								url : "/settlerecord/getLastUpdateDate",
								success : function(result) {
									resolve(result.responseText);
								}
							});
						});
					},
					getTotalExchangePerformance : function(data) {
						return new Promise(function(resolve, reject) {
							Ext.Ajax.request({
								url : "/exchange/getTotalExchangePerformance",
								params : {
									start : data.start,
									end : data.end
								},
								listeners : {
									beforerequest : function() {
										msgTip = Ext.MessageBox.show({
											title : "提示",
											msg : "信息刷新中,请稍候......"
										});
									},
									requestcomplete : function() {
										msgTip.hide(); // 加载完成，关闭提示框
									}
								},
								success : function(result) {
									resolve(result.responseText);
								}
							});
						});
					}
				});
