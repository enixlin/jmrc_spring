Ext.define('jmrc.view.performance.chart.bar.barController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.performance-chart-bar-bar',

	
	onPreview:function(){
		 if (Ext.isIE8) {
	            Ext.Msg.alert('Unsupported Operation', 'This operation requires a newer version of Internet Explorer.');
	            return;
	        }
	        var chart = this.lookupReference('chart');
	        chart.preview();

	},
	
	onDownload: function() {
        if (Ext.isIE8) {
            Ext.Msg.alert('Unsupported Operation', 'This operation requires a newer version of Internet Explorer.');
            return;
        }
        var chart = this.lookupReference('chart');
        if (Ext.os.is.Desktop) {
            chart.download({
                filename: 'Redwood City Climate Data Chart'
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
		let bind = {
			store : "{" + me.getView()["config"]["data"]["st"] + "}"
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
		axes[0].setTitle(me.getView()["config"]["data"]["yAxis"]);
		axes[1].setTitle(me.getView()["config"]["data"]["xAxis"]);
		bar[0].setTitle(me.getView()["config"]["data"]["title"]);
		// 设定标题
		polar.setSprites(sprites);
		// 设定bar图的store
		polar.setBind(bind);
		// bar[0].setLabel(me.getView()["config"]["data"]["title"]);

	},
	//格式化柱形图的数据标签
	onSeriesLabelRender:function(v){
		return Ext.util.Format.number(v,'0,000');
	},
	
	onTooltipRender:function(tooltip,record,item){
		let me = this;
		let perform=Ext.util.Format.number(record.get("performance"),"0,000");
		tooltip.setHtml(record.get("name")+"业务量为："+perform+"万美元");
	},
	onAxisLabelRender:function(axis,label,layoutContext){
		return Ext.util.Format.number(layoutContext.renderer(label)/1000,'0,000')
	}


});
