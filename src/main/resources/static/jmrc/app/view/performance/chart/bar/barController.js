Ext
		.define(
				'jmrc.view.performance.chart.bar.barController',
				{
					extend : 'Ext.app.ViewController',
					alias : 'controller.performance-chart-bar-bar',
					
					afterRender:function(){
						let me=this;
						let polar=me.getView().query("cartesian")[0];
						let axes=polar.getAxes();
						let bar=polar.getSeries();
						axes[0].setTitle(me.getView()["config"]["data"]["yAxis"]);
						axes[1].setTitle(me.getView()["config"]["data"]["xAxis"]);
						bar[0].setTitle(me.getView()["config"]["data"]["title"]);
						//bar[0].setLabel(me.getView()["config"]["data"]["title"]);
						
					}

		

				});
