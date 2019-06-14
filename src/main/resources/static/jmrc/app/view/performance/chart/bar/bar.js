Ext.define('jmrc.view.performance.chart.bar.bar', {
	extend : 'Ext.panel.Panel',
	xtype : "barChart",

	requires : [ 'jmrc.view.performance.chart.bar.barController',
			'jmrc.view.performance.chart.bar.barModel' ],

	controller : 'performance-chart-bar-bar',
	viewModel : {
		type : 'performance-chart-bar-bar'
	},
	width:"40%",
	height : window.innerHeight-600,
	
	  tbar: [
	        
	        {
	            text: 'Preview',
	            platformConfig: {
	                desktop: {
	                    text: 'Download'
	                }
	            },
	            handler: 'onDownload'
	        },
	    ],
	items: {
		xtype : 'cartesian',
		renderTo : document.body,
		reference: 'chart',
		width : "40%",
		height: 300,

		insetPadding:'5 10 10 10',
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
				fill : [ '#abcdef' ],
				stroke : '#1F6D91'
			},
			xField : 'name',
			yField : 'performance',
			yValue:"performance",
		    highlight: {
                strokeStyle: 'light',
                fillStyle: 'gold'
            },
			 label: {
	                field: 'performance',
	                display: 'insideEnd',
	                renderer: 'onSeriesLabelRender'
	            },
	            tooltip:{
	            	trackMouse:true,
	            	renderer:"onTooltipRender"
	            }
		},

           
        
      
        animation: Ext.isIE8 ? false : true,
	}
    

});
