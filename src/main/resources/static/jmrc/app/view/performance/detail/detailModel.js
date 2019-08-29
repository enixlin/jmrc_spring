Ext.define('jmrc.view.performance.detail.detailModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.performance-detail-detail',
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
    			{name:"<center>机构号</center>",type:"string",dataIndex:"branchCode"},
    			{name:"<center>机构名称</center>",type:"string",dataIndex:"branchName"},
    			{name:"<center>任务数<br></center>",type:"number",dataIndex:"year_task"},
    			{name:"<center>完成率<br>（全年）</center>",type:"number",dataIndex:"taskCompleteYear"},
    			{name:"<center>完成率<br>（季度）</center>",type:"number",dataIndex:"taskCompleteSeason"},
    			{name:"<center>笔数</center>",type:"number",dataIndex:"times"},
    			{name:"<center>金额</center>",type:"number",dataIndex:"amount"},
    			{name:"<center>笔数<br>（去年）</center>",type:"number",dataIndex:"times_pre"},
    			{name:"<center>金额<br>（去年）</center>",type:"number",dataIndex:"amount_pre"},
    			{name:"<center>笔数<br>（同比）</center>",type:"number",dataIndex:"times_compare"},
    			{name:"<center>金额<br>（同比）</center>",type:"number",dataIndex:"amount_compare"},
    	
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
