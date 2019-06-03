Ext.define('jmrc.view.policydocument.PolicydocumentModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.policydocument-policydocument',
    data: {
        name: 'jmrc'
    },
    stores:{
        policydocumentStore:{
            fields:['docNum','title','state','effTime','dId'],
            params:{},
            proxy:{
                url:"/policydocument/query",
                type:"ajax"
            }
        } 
    }

});
