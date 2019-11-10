Ext.define('jmrc.view.performance.exchange.totaldetailController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.performance-exchange-totaldetail',

	afterRender : function() {
		let me = this;
		let view = me.getView();
		let data = view.config.data;
		let start = view.up().query("textfield")[2].getValue()
				.replace(/-/g, "");
		let end = view.up().query("textfield")[3].getValue().replace(/-/g, "");
		data.start = start;
		data.end = end;
		let updatedata = me.getLastUpdateDate();
		let totalPerformace = me.getTotalExchangePerformance(data);

		Promise.all([ updatedata, totalPerformace ]).then(function(result) {

			data.updatedate = result[0];
			data.totalexchange =Ext.JSON.decode( result[1])[0].amount;
			me.makeTotalReport(me, view, data);
		});

	},
	makeTotalReport : function(me, view, data) {
		let width = window.innerWidth * 0.65;
		let height = window.innerHeight * 0.7;
		let summypanel = Ext.create({
			xtype : 'panel',
			width : width,
			height : height,
			layout : {
				type : "table",
				columns : 2
			},
		// border:2,
		});
		let information = Ext.create({
			xtype : 'panel',
			width : width * 0.4,
			height : height * 0.4,
			// border:2,
			margin : "25 0 0 25",
			html : "<center><h3>数据更新日期：<font size=3px color=red>"+ data.updatedate.substring(0, 10)+"</font></h3></center>" 
					+ "<center><h3>结售汇总量：<font size=3px color=red>"+Ext.util.Format.number(data.totalexchange/10000, "0,000.00") + "万美元</font></h3></center>"
					
		});
		let grid = Ext.create({
			xtype : 'grid',
			width : width * 0.5,
			height : height * 0.5,
			border : 2,
			margin : "25 0 0 25",
			columns:[
				{text:"<center>结售汇种类</center>",dataIndex:"product_name"},
				{text:"<center>笔数</center>",dataIndex:"times"},
				{text:"<center>金额</center>",dataIndex:"amount"},
				{text:"<center>笔数<br>（同比）</center>",dataIndex:"times_compare"},
				{text:"<center>金额<br>（同比）</center>",dataIndex:"amount_compare"},
				],
			store:Ext.create("Ext.data.Store",{
				fields:['product_name',"amount","times"],
				proxy:{
					url:"exchange/getTypeTotal",
					type:"ajax"
				},
			
			})
		});

		let chart = Ext.create({
			xtype : "panel",
			width : width * 0.96,
			height : height * 0.4,
			colspan : 2,
			border : 2,
			margin : "25 0 0 35",
		});

		summypanel.add(information);
		summypanel.add(grid);
		summypanel.add(chart);
		view.add(summypanel);

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
							title : '提示',
							msg : '信息刷新中,请稍候......'
						});
					},
					requestcomplete : function() {
						msgTip.hide(); // 加载完成，关闭提示框
					},
				},
				success : function(result) {

					resolve(result.responseText);
				}
			});
		});
	},

});
