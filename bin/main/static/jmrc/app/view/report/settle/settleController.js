Ext.define('jmrc.view.report.settle.settleController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.report-settle-settle',

	query : function() {
		let me=this;
		let view=me.getView();
		let params=me.getParams(me);
		
		 var chart = this.lookupReference('chart');
		 console.log(chart);
	        chart.preview();
       // chart.preview();

	},

	// 取得查询参数
	getParams : function(me) {
		let queryFields = me.getView().query("textfield");
		let reportType = queryFields[0].getValue();
		let start = queryFields[1].getValue();
		let end = queryFields[2].getValue();
		return {
			reportType : reportType,
			start : start,
			end : end
		}
	}

});
