Ext.define('jmrc.view.report.Report', {
	extend : 'Ext.panel.Panel',
	xtype : "report",

	requires : [ 'jmrc.view.report.ReportController',
			'jmrc.view.report.ReportModel' ],

	controller : 'report-report',
	viewModel : {
		type : 'report-report'
	},

	tbar : [ {
		fieldLabel : '报表类型:',
		labelWidth : 70,
		xtype : "combo",
		width : 300,
		data:[{text:"经营单位任务完成情况表"}]
	}, {
		xtype : 'datefield',
		anchor : '100%',
		fieldLabel : '日期区间',
		labelWidth : 70,
		name : 'from_date',
		format:"Ymd",
		 style: {
		        color: '#FFFFFF',
		        backgroundColor:'#'
		    }
		//maxValue : new Date()
	// limited to the current date or prior
	},

	{
		xtype : 'datefield',
		anchor : '100%',
		//fieldLabel : '-',
		labelWidth : 30,
		align:"center",
		name : 'to_date',
		format:"Ymd",
		showTodyday:true,
		//maxValue : new Date()
	// limited to the current date or prior
	},
	{
		xtype : "button",
		text : "查询",
		 style: {
		        color: '#000000',
		        font:"10px bold",
		        backgroundColor:'orange'
		    }
	} ],
	
	items:[{
		xtype:"tabpanel",
		reference:"reporttabpanel",
		tabPosition:"top",
		tabRotation :0,
		scrollable:true,
		minHeight:500,
		maxHeight:500,
//		layout:"auto",
		border:1
		
	}]
});
