Ext.define('jmrc.view.performance.detail.ClientDetailModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.performance-detail-clientdetail',
    data: {
        name: 'jmrc'
    },
    
    
    stores:{
    	allClientPerformanceStore:{
    		fields:[
    			{name:"<center>机构号</center>",type:"string",dataIndex:"branchCode"},
    			{name:"<center>机构名称</center>",type:"string",dataIndex:"branchName"},
    			{name:"<center>客户号</center>",type:"string",dataIndex:"custCode"},
    			{name:"<center>客户名称</center>",type:"string",dataIndex:"custName"},
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
				url : "/settlerecord/getAllClientPerformance",
				type : "ajax",
			},
    	}
    }



    
    
    

});
