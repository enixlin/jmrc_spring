Ext.define('jmrc.view.report.ReportController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.report-report',
    	
    	
    	init:function(){
    		let ref=this.getReferences();
    		ref.reporttabpanel.add({xtype:"unitreport",title:"unitreport"});
    		ref.reporttabpanel.add({xtype:"user",title:"用户管理"});
    		//this.getView().add({xtype:"unitreport"});
    	},



	getDateRange:function(){
//		let startDay=Ext.ComponentQuery.query('#myform textfield');
	}

});
