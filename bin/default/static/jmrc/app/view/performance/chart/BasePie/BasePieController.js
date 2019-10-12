Ext
		.define(
				"jmrc.view.performance.chart.BasePie.BasePieController",
				{
					extend : "Ext.app.ViewController",
					alias : "controller.performance-chart-basepie-basepie",
					init : function() {
						let me = this;
						let view = me.getView();
						// 获取配置参数对象
						let config = view.config.data;
						view.setLayout(config["layout"]);
						let pieChartType = config["pieChartType"];
						switch (pieChartType) {
						case "productPieChart":
							this.showProductPieChart();
						case "clientPieChart":
							this.showClientPieChart();
						}
					},

					showProductPieChart : function() {
						let me = this;
						let view = me.getView();
						let config = me.getView().config.data;
						//view.setLayout(config["layout"]);
						// 插入饼图的框架
						view.add({
							xtype : "polar",
							width : config["width"],
							height : config["height"],
							
							renderTo : document.body,
//							theme : 'default-gradients',/
							reference : "chart",
							border : 2,
							margin : 20,
innerPadding : 10,
					       // innerPadding:50,
							//scrollable : true,
							tbar : [ {
								text : "生成图像",
								platformConfig : {
									desktop : {
										text : "生成图像"
									}
								},
								
								handler : "onPreview"
							} ],
							legend : {
								docked : 'right',
								scrollable : true,
								 resizable : true,
								  draggable: {
								        delegate: 'h1'
								    },
								margin:5
							},
							interactions : [ 'rotate' ],
							sprites : [
									{
										type : 'text',
										text : config["unit"]["name"].replace(
												"（一级支行）", "").replace("本部", "")
												+ config["title"],
										fontSize : 18,
										width : 100,
										height : 40,
										x : 80, // the sprite x position
										y : 40
									// the sprite y position
									}, ],
							series : [ {
								type : 'pie',
								angleField : config['yAxis'],
								label : {
									field : config['xAxis'],
									calloutLine : {
										length : 30,
										width : 3
									// specifying 'color' is also possible here
									}
								},
								donut : 20,
								highlight : true,
								tooltip : {
									trackMouse : true,
									renderer : 'onSeriesTooltipRender'
								}
							} ]
						});
						
						// 设定pie图的store
						let st = config["st"];
						let bindStore = me.getViewModel().getStore(st);
						// 取得图型对象，绑定数据集
						let polar = view.query("polar")[0];
						// 设定柱型图的数据集
						polar.bindStore(bindStore);
					
						
						
						  //插入数据明细表格
					    view.add({
					      xtype: "grid",
					      width: window.innerWidth * 0.3,
					      height: window.innerHeight * 0.4,
					      margin: 20,
					      border: 2,
					      tbar: [
					        {
					          text: "保存表格",
					          platformConfig: {
					            desktop: {
					              text: "保存表格"
					            }
					          },
					          handler: "exportExcel"
					        }
					      ]
					    });
					    let grid = view.query("grid")[0];
					    // 根据数据集的filed数组，确定表格的列
					    let fields = bindStore.getModel().getFields();
					    let cos = [];
					    for (let n = 0, len = fields.length; n < len; n++) {
					      if (fields[n].name != "id") {
					        if (
					          fields[n].type == "number" &&
					          fields[n].name.indexOf("笔数") == -1
					        ) {
					          cos.push({
					            text: fields[n].name,
					            width: (0.8 / (len - 1)) * 100 + "%",
					            dataIndex: fields[n].name,
					            renderer: function(value) {
					              return Ext.util.Format.number(value, "0,000.00");
					            }
					          });
					        } else if (
					          fields[n].type == "number" &&
					          fields[n].name.indexOf("笔数") != -1
					        ) {
					          cos.push({
					            text: fields[n].name,
					            width: (0.8 / (len - 1)) * 100 + "%",
					            dataIndex: fields[n].name,
					            renderer: function(value) {
					              return Ext.util.Format.number(value, "0,000");
					            }
					          });
					        }

					        if (fields[n].type != "number") {
					          cos.push({
					            text: fields[n].name,
					            width: (0.8 / (len - 1)) * 100 + "%",
					            dataIndex: fields[n].name
					          });
					        }
					      }
					    }
					    // 为表格的columns数组添加操作列
					    cos.push({
					      width: 40,
					      xtype: "actioncolumn",
					      align: "center",
					      items: [
					        {
					          iconCls: "x-fa fa-pie-chart",
					          tooltip: "产品明细",
					          text: " 1"
					        }
					      ]
					    });
					    // 为表格的columns数组添加操作列
					    cos.push({
					      width: 40,
					      xtype: "actioncolumn",
					      align: "center",
					      items: [
					        {
					          iconCls: "x-fa fa-list",
					          tooltip: "业务流水"
					          // text: " 1"
					        }
					      ]
					    });
					    grid.setColumns(cos);
					    
					    view.add(grid);
						grid.bindStore(bindStore);
					

					
						// 载入数据
						polar.getStore(st).load({
							params : {
								start : config["start"],
								end : config["end"],
								unitType : config["unit"]["unitType"],
								name : config["unit"]["name"],
								uid : config["unit"]["code"]
							}
						});
					},

					showClientPieChart : function() {
					},

					onPreview : function() {
						if (Ext.isIE8) {
							Ext.Msg
									.alert('Unsupported Operation',
											'This operation requires a newer version of Internet Explorer.');
							return;
						}
						var chart = this.lookupReference('chart');
						chart.preview();
					},

					onDataRender : function(v) {
						return v + '%';
					},

					onSeriesTooltipRender : function(tooltip, record, item) {

						let pieStore = this.getViewModel().storeInfo.UnitProductPerformanceStore;
						let summ = pieStore.sum("金额");
						let timesSum = pieStore.sum("业务笔数");
						let performance = record.get('金额');
						let times = record.get('业务笔数');
						tooltip.setHtml(record.get('产品') + ': '
								+ Ext.util.Format.number(performance, '0,000')
								+ '万美元 ,笔数：'
								+ Ext.util.Format.number(times, "0,000") + "笔");
					},
					
					  /**
					   * 导出表格到EXCEL文件并下载
					   */
					  exportExcel: function() {
					    let me = this;
					    let view = me.getView();
					    let config = view.config.data;
					    let unit = config["unit"];
					    let startDay = me.getView()["config"]["data"]["start"];
					    let endDay = me.getView()["config"]["data"]["end"];
					    
					    let url =
					      config["exportUrl"] +
					      "?start=" +
					      startDay +
					      "&end=" +
					      endDay +
					      "&name="+
					      unit.name+
					      "&uid="+
					      unit.code+
					      "&unit=" +
					      JSON.stringify(unit);
					    window.open(encodeURI(url)  );
					  },
				});
