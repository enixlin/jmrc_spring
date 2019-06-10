Ext.define('jmrc.view.performance.settle.settleController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.performance-settle-settle',

	// 页面渲染后，格式化日期数据，填充当前日期
	afterRender : function() {
		let me = this;
		let view = me.getView();
		let textfield = view.query("textfield");
		textfield[1].setValue("2019-01-01");
	
		let reportType = textfield[0].getValue();
		let start = textfield[1].getValue();
		let date = new Date();
		let year = date.getFullYear();
		let month = date.getMonth() + 1; // 返回是０-１１,所以要加一
		let day = date.getDate();// getDay()函数是返回星期几
		// 10以下的数在前面补充零
		month < 10 ? (month = "0" + month) : month;
		day < 10 ? (day = ("0" + day)) : day;
		let end = textfield[2].setValue(year+"-" + month+"-" + day);

	},

	query : function() {
		let me = this;
		let view = me.getView();
		let textfield = view.query("textfield");
		let reportTypeName = textfield[0].getDisplayValue();
		let reportTypeId = textfield[0].getValue();
		let start = textfield[1].getValue().replace(/-/g,"");
		let end = textfield[2].getValue().replace(/-/g,"");
		let store = me.getViewModel().getStore("allTypeBusyPerformanceStore");
		console.log("store");
		console.log(store);
		let bar = null || view.query("barChart")[0];
		if (bar != null) {
			let barStore=view.query("cartesian")[0].getStore();
			barStore.load({
				params : {
					start : start,
					end : end
				}
			});
		
		} else {
			bar = Ext.create("jmrc.view.performance.chart.bar.bar", {
				width : "100%",
				height : 500,
			});
			view.add(bar);
			//要先插入组件，后续才能找到
			let barStore=view.query("cartesian")[0].getStore();
			barStore.load({
				params : {
					start : start,
					end : end
				}
			});
			
		}

		me.makeReport(me, reportTypeId, start, end);

	},

	makeReport : function(me, reportType, start, end) {
		console.log(reportType + start + end);
		console.log("barChart");
		// me.getView().query();

		// let store =
		// me.getViewModel().getStore("allTypeBusyPerformanceStore");
		// console.log(store);
		// store.load({
		// params : {
		// "start" : start,
		// "end" : end
		// }
		// });
	},

});
