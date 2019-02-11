Ext.define('jmrc.view.policydocument.PolicydocumentController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.policydocument-policydocument',

    query:function(){
        let state=Ext.getCmp("effectFileTag").value;
        let keyWord=Ext.ComponentQuery.query("[fieldLabel='请输入查询关键字']" )[0].value;
        let container=this.getView();
  
        console.log(state);
        let store=this.getViewModel().getStore("policydocumentStore");
        store.load({params:{"keyWord":keyWord,"state":state}});
        
    },
    showFile:function(record){
        console.log("title 发彩信22");
        console.log(record.data["title"]);

        this.add({xtype:"notes"});
        
    }



});
