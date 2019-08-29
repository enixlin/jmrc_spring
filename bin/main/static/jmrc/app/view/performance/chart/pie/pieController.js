Ext
		.define(
				'jmrc.view.performance.chart.pie.pieController',
				{
					extend : 'Ext.app.ViewController',
					alias : 'controller.performance-chart-pie-pie',

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

						let pieStore = this.getViewModel().storeInfo.getAllBusyTypeProformance;
						let summ = pieStore.sum("performance");
						let timesSum = pieStore.sum("times");
						let performance = record.get('performance');
						let times = record.get('times');
						tooltip.setHtml(record.get('name') + ': '
								+ Ext.util.Format.number(performance, '0,000')
								+ '万美元 ,笔数：'
								+ Ext.util.Format.number(times, "0,000") + "笔");
					},

					afterRender : function() {
						let me = this;
						// let polar = me.getView().query("polar")[0];
						// let polar = Ext.getCmp("piechart");
						let polar=me.getView().query("polar")[0];

						let sprites = {
							type : 'text',
							text : me.getView()["config"]["data"]["title"],
							fontSize : 24,
							width : 100,
							height : 30,
							x : 150,
							y : 30
						};

						// 设定标题
						polar.setSprites(sprites);

						let startDay = me.getView()["config"]["data"]["start"];
						let endDay = me.getView()["config"]["data"]["end"];
						let st = me.getView()["config"]["data"]["st"];
						let grid = me.getView().query("grid")[0];
						let bindStore = me.getViewModel().getStore(st);

						polar.bindStore(bindStore);
						grid.bindStore(bindStore);

						// 根据数据集的filed数组，确定表格的列
						let fields = bindStore.getModel().getFields();
						console.log(fields);
						let columns = grid.getColumns();
						let cos = [];
						for (let n = 0, len = fields.length; n < len; n++) {
							if (fields[n].name != "id") {
								if (fields[n].type == "number"  && fields[n].name != "业务笔数") {
									cos.push({
										text : fields[n].name,
										width : (1 / (len - 1) * 100) + "%",
										dataIndex : fields[n].name,
										renderer : function(value) {
											return Ext.util.Format.number(
													value, "0,000.00");
											;
										}

									});
								}

								else if(fields[n].type == "number" && fields[n].name == "业务笔数") {
									cos.push({
										text : fields[n].name,
										width : (1 / (len - 1) * 100) + "%",
										dataIndex : fields[n].name,
										renderer : function(value) {
											return Ext.util.Format.number(
													value, "0,000");
											;
										}

									});
								}
								
								if(fields[n].type != "number"){
									cos.push({
										text : fields[n].name,
										width : (1 / (len - 1) * 100) + "%",
										dataIndex : fields[n].name,
									

									});
								}
							}
						}
						grid.setColumns(cos);

						polar.getStore(st).load({
							params : {
								start : startDay,
								end : endDay
							}
						});

					},

					showPie : function(store) {
						return new Promise(function(resolve, reject) {
							store.load(function(records) {
								resolve(records);
							});
						});
					},
					
					
					showdetail:function(record){
						let me=this;
						let view=me.getView();
						let startDay = me.getView()["config"]["data"]["start"];
						let endDay = me.getView()["config"]["data"]["end"];
						let product=record.data["name"];
						
						let win=Ext.create("Ext.window.Window",{
							width:1000,
							height:600,
							
						});
						let grid=Ext.create('jmrc.view.performance.detail.detail',{
							data : {
								title : "国际结算流水清单",
								st : "dateRangeDetailStore",
								start : startDay,
								end : endDay

							}
						});
						let st=grid["config"]["data"]["st"];
						let store=grid.getViewModel().getStore(st);
						win.add(grid);
						view.add(win);
						win.show();
						store.load({
							params : {
								start : startDay,
								end : endDay,
								product:product
							}
						});
					}

				});
