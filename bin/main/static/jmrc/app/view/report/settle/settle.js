Ext.define('jmrc.view.report.settle.settle', {
	extend : 'Ext.panel.Panel',
	xtype : "settle",

	requires : [ 'jmrc.view.report.settle.settleController',
			'jmrc.view.report.settle.settleModel' ,
			
			   
			
			
			
			],

	controller : 'report-settle-settle',
	viewModel : {
		type : 'report-settle-settle'
	},

	tbar : [ {
		xtype : "combo",
		margin : '5 5 5 5',
		id : "reportTypeCombo",
		fieldLabel : "查询分析类型",
		displayField : "name",
		store : {
			data : [ {
				id : "totalReport",
				name : "全行业绩分析"
			}, {
				id : "unitReport",
				name : "经营单位分析"
			}, {
				id : "clientReport",
				name : "客户分析"
			} ]
		}
	}, {
		labelAlign : 'right',
		fieldLabel : "开始日期",
		xtype : 'textfield',
		minWidth : 150,
		emptyText : "开始日期",
		blankText : '日期格式：20190531',
		margin : '5 5 5 5',
		inputType : "text",
		value : "20190101",
		name : 'start',
		id : 'start',
		allowBlank : false
	}, {
		labelAlign : 'right',
		fieldLabel : "结束日期",
		xtype : 'textfield',
		minWidth : 150,
		emptyText : "结束日期",
		blankText : '日期格式：20190531',
		value : "20190531",
		margin : '5 5 5 5',
		inputType : "text",
		name : 'end',
		id : 'end',
		allowBlank : false
	}, {
		xtype : "button",
		text : "查询",
		handler : "query",
		margin : '5 5 5 5',
		iconCls : 'x-fa fa-search',
	} ],

	// 内容展示区，是一个container

	items : [ 
		{
			   xtype: 'chart',
			   renderTo: document.body,
			   width: 400,
			   height: 400,
			   theme: 'green',
			   interactions: ['rotate', 'itemhighlight'],
			   store: {
			       fields: ['name', 'data1'],
			       data: [{
			           name: 'metric one',
			           data1: 14
			       }, {
			           name: 'metric two',
			           data1: 16
			       }, {
			           name: 'metric three',
			           data1: 14
			       }, {
			           name: 'metric four',
			           data1: 6
			       }, {
			           name: 'metric five',
			           data1: 36
			       }]
			   },
			   
			   axes:[{
				   type:'numeric',
				   position:'left',
				   dashSize:5,
				   title:"axes",
				   fields:['data1']
			   }],
			   series: {
			       type: 'pie',
			       highlight: true,
			       angleField: 'performance',
			       label: {
			           field: 'name',
			           display: 'rotate'
			       },
			       donut: 30
			   }
			}
		
		
		]

});
