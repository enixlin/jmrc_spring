Ext.define('jmrc.view.performance.detail.clientlistController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.performance-detail-clientlist',
	/*
	 * 
	 */
	afterRender : function() {
		let me = this;
		let view = me.getView();

		let viewModel = me.getViewModel();
		let data = view.config.data;
		let store = viewModel.getStore(data.st);

		let grid = Ext.create({
			xtype : "grid",
			layout : "fit",
			width : "100%",

		});

		store.load({
			params : {
				start : data.start,
				end : data.end,
				unitType : "unit",
				name : data.unit.name,
				uid : data.unit.id
			}
		});

		let fields = store.field;
		let cos = [];
		for (let n = 0, len = fields.length; n < len; n++) {
			if (fields[n].name == "times") {
				cos.push({
					header : "笔数",
					width : 100,
					dataIndex : fields[n].name,
					renderer : function(value) {
						return Ext.util.Format.number(value, "0,000");
					}
				});
			}
			if (fields[n].name == "amount") {
				cos.push({
					header : "金额",
					width : 100,
					dataIndex : fields[n].name,
					renderer : function(value) {
						return Ext.util.Format.number(value, "0,000.00");
					}
				});
			}
			if (fields[n].name == "times_compare") {
				cos.push({
					header : "笔数<br>同比",
					width : 100,
					dataIndex : fields[n].name,
					renderer : function(value) {
						if (value < 0) {

							return "<font color='red'>"
									+ Ext.util.Format.number(value, "0,000")
									+ "</font>";
						} else {

							return "<font color='green'>"
									+ Ext.util.Format.number(value, "0,000")
									+ "</font>";
						}
					}
				});
			}
			if (fields[n].name == "amount_compare") {
				cos.push({
					header : "金额<br>同比",
					width : 100,
					dataIndex : fields[n].name,
					renderer : function(value) {
						if (value < 0) {

							return "<font color='red'>"
									+ Ext.util.Format.number(value, "0,000.00")
									+ "</font>";
						} else {
							return "<font color='green'>"
									+ Ext.util.Format.number(value, "0,000.00")
									+ "</font>";
						}
					}
				});
			}
			if (fields[n].name == "cust_name") {
				cos.push({
					header : "客户",
					dataIndex : fields[n].name,
					width : 350,
					renderer : function(value) {
						return value;
					}
				});
			}

		}

		grid.setColumns(cos);
		grid.bindStore(store);
		view.add(grid);

		console.log(fields);
		console.log(cos);
		// console.log(data);
	}

});