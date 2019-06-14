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
						
						
						let pieStore=this.getViewModel().storeInfo.getAllBusyTypeProformance;
						let summ=pieStore.sum("performance");
						let performance=record.get('performance');
						tooltip.setHtml(record.get('name')
								+ ': '
								+ Ext.util.Format.number(performance, '0,000') + '万美元 ,占比：'+Ext.util.Format.number(performance/summ*100,"0.00")+"%");
					},

					afterRender : function() {
						let me = this;
						let polar = me.getView().query("polar")[0];

						let bind = {
							store : "{" + me.getView()["config"]["data"]["st"]
									+ "}"
						};
						let sprites = {
							type : 'text',
							text : me.getView()["config"]["data"]["title"],
							fontSize : 14,
							width : 100,
							height : 30,
							x : 150,
							y : 15
						};
						// axes[0].setTitle(me.getView()["config"]["data"]["yAxis"]);
						// axes[1].setTitle(me.getView()["config"]["data"]["xAxis"]);
						// bar[0].setTitle(me.getView()["config"]["data"]["title"]);
						// 设定标题
						polar.setSprites(sprites);
						// 设定bar图的store
						polar.setBind(bind);

						
					}

				});
