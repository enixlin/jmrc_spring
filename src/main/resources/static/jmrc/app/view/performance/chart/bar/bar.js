Ext.define('jmrc.view.performance.chart.bar.bar', {
	extend : 'Ext.panel.Panel',
	xtype : "barChart",

	requires : [ 'jmrc.view.performance.chart.bar.barController',
			'jmrc.view.performance.chart.bar.barModel' ],

	controller : 'performance-chart-bar-bar',
	viewModel : {
		type : 'performance-chart-bar-bar'
	},
	width:"100%",
	height : window.innerHeight-600,
	

	items: {
		xtype : 'cartesian',
		renderTo : document.body,
		reference: 'chart',
		width : "40%",
		height: 300,
//		title:bind:{data.title},
		bind : {
			store : "{monthBarStore}"
		},
		axes : [
			
			{
			type : 'numeric',
			position : 'left',
			title : {
				text :"",
				fontSize : 15
			},
			fields : 'performance'
		}, {
			type : 'category',
			position : 'bottom',
			title : {
				text :"",
				fontSize : 15
			},
			fields : 'name'
		} 
		
		],
		series : {
			type : 'bar',
			subStyle : {
				fill : [ '#388FAD' ],
				stroke : '#1F6D91'
			},
			xField : 'name',
			yField : 'performance',
			yValue:"performance",
		    highlight: {
                strokeStyle: 'black',
                fillStyle: 'gold'
            },
			 label: {
	                field: 'performance',
	                display: 'insideEnd',
	                //renderer: 'onSeriesLabelRender'
	            }
		},

           
        
      
        animation: Ext.isIE8 ? false : true,
	}
    

});
