Ext.define('jmrc.view.performance.detail.unitdetailModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.performance-detail-unitdetail',
    data: {
        name: 'jmrc'
    },
    stores:{
    	dateRangeDetailStore:{
    		fields:[
    			{name:"",type:"",mapping:""},
    			],
    			proxy : {
    				url : "/settlerecord/getDateRangeDetail",
    				type : "ajax",
    			},
    		
    	},

    	allUnitPerformanceStore:{
    		fields:[
    			{name:"<center>机构号</center>",type:"string",dataIndex:"行号"},
    			{name:"<center>机构名称</center>",type:"string",dataIndex:"行名"},
    			{name:"<center>任务数<br></center>",type:"number",dataIndex:"年度任务"},
    			{name:"<center>完成率<br>（全年）</center>",type:"number",dataIndex:"任务完成率（年）"},
    			{name:"<center>完成率<br>（季度）</center>",type:"number",dataIndex:"任务完成率（季）"},
    			{name:"<center>笔数</center>",type:"number",dataIndex:"笔数"},
    			{name:"<center>金额</center>",type:"number",dataIndex:"金额"},
    			{name:"<center>笔数<br>（去年）</center>",type:"number",dataIndex:"笔数（去年）"},
    			{name:"<center>金额<br>（去年）</center>",type:"number",dataIndex:"金额（去年）"},
    			{name:"<center>笔数<br>（同比）</center>",type:"number",dataIndex:"笔数同比"},
    			{name:"<center>金额<br>（同比）</center>",type:"number",dataIndex:"金额同比"},
    	
    		],
    		listeners : {
				beforeload : function() {
					msgTip = Ext.MessageBox.show({
						title : '提示',
						msg : '报表统计信息刷新中,请稍候......'
					});
				},
			},
    		proxy : {
				url : "/settlerecord/getAllUnitPerformance",
				type : "ajax",
			},
    	}
    	
    	
    	
    }

});
