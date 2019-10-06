Ext.define("jmrc.view.performance.detail.ClientDetail", {
	extend : "Ext.panel.Panel",
	// extend : 'Ext.pivot.Grid ',
	xtype : "clientdetail",

	requires : [ "jmrc.view.performance.detail.ClientDetailController",
			"jmrc.view.performance.detail.ClientDetailModel" ],

	controller : "performance-detail-clientdetail",
	viewModel : {
		type : "performance-detail-clientdetail"
	},
	// plugins: {
	// gridexporter: true
	// },
	// mixins : [ 'jmrc.view.performance.detail.GridExportExcelMixin' ],

	tbar : [ {
		xtype : "textfield",
		fieldLabel : "查找客户：",
		listeners : {
			change : function(me, newValue, oldValue, eOpts) {
				
				let unit = me.up().up().query("textfield")[0].value;
				let client = me.value;
				console.log("unit is "+unit+"  client is :"+client);
				me.up().up().controller.filterclient(unit, newValue);
			}
		}
	}, "->", {
		xtype : "button",
		text : "导出表格",
		handler : "exportExcel"
	} ],

	margin : 5,

	scrollable : true
});
