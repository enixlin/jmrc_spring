Ext.define('jmrc.view.performance.settle.settle', {
	extend : 'Ext.panel.Panel',
	xtype : "settle",

	requires : [ 'jmrc.view.performance.settle.settleController',
			'jmrc.view.performance.settle.settleModel' ],

	controller : 'performance-settle-settle',
	viewModel : {
		type : 'performance-settle-settle'
	},

	// 查询工具栏
	tbar : [ {
		xtype : "combo",
		fieldLabel : "分析主题：",
		name : "reportType",
		margin : "5 5 5 5 ",
		labelAlign : "right",
		bind : {
			store : "{reportTypeBoxStore}"
		},
		displayField : "name", // 显示的字段，用函数getDisplayValue()获取
		valueField : "id" // 取值的字段，用函数getValue()获取
	}, {
		xtype : "textfield",
		fieldLabel : "开始日期：",
		value : "20190101",
		name : "start",
		margin : "5 5 5 5 ",
		labelAlign : "right"
	}, {
		xtype : "textfield",
		fieldLabel : "结束日期：",
		name : "end",
		margin : "5 5 5 5 ",
		labelAlign : "right"
	}, {
		xtype : "button",
		text : "查询",
		iconCls : 'x-fa fa-search',
		style : {
			// background : "#123456",
			fontColor : "red",
		},
		handler : "query"
	} ],

	// chart内容表格显示区
	

});
