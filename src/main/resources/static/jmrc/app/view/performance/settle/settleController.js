Ext.define('jmrc.view.performance.settle.settleController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.performance-settle-settle',

	// 页面渲染后，格式化日期数据，填充当前日期
	afterRender : function() {
		let me = this;
		let view = me.getView();
		let textfield = view.query("textfield");
		let reportType = textfield[0].getValue();
		let start = textfield[1].getValue();
		let date = new Date();
		let year = date.getFullYear();
		let month = date.getMonth();
		let day = date.getDay();
		// 10以下的数在前面补充零
		month < 10 ? (month = "0" + month) : month;
		day < 10 ? (day = "0" + day) : day;
		let end = textfield[2].setValue(year + month + day);

	},

	query : function() {
		let me = this;
		let view = me.getView();
		let textfield = view.query("textfield");
		let reportTypeName = textfield[0].getDisplayValue();
		let reportTypeId = textfield[0].getValue();
		let start = textfield[1].getValue();
		let end = textfield[2].getValue();
		view.add({
			xtype : "barChart",
			width : "100%",
			height : 300,
			title : "barChart",
			x : 10,
			y : 10

		});
		me.makeReport(me, reportTypeId, start, end);
		// Ext.create("Ext.window.Window",{
		// width : 500,
		// height:300,items:[{xtype:"barChart"}]}).show();

	},

	makeReport : function(me, reportType, start, end) {
		console.log(reportType + start + end);
		console.log("barChart");
		console.log(me.getView().query("barChart"));

		// let store =
		// me.getViewModel().getStore("allTypeBusyPerformanceStore");
		// console.log(store);
		// store.load({
		// params : {
		// "start" : start,
		// "end" : end
		// }
		// });
	}

});
