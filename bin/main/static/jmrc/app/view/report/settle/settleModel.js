Ext.define('jmrc.view.report.settle.settleModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.report-settle-settle',
    data: {
        name: 'jmrc'
    },
    stores:{
        reportStore:{
            fields:['performance','times','name'],
            params:{},
            pageSize:50,
            proxy:{
                url:"/settlerecord/getAllBusyTypeProformance",
                type:"ajax"
            }
        } 
    }
    

});
