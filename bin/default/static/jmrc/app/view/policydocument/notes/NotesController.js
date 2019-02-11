Ext.define('jmrc.view.policydocument.notes.NotesController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.policydocument-notes-notes',
    afterRender:function(){
        console.log("afterrender1");
        // let me=this;
        // let record=this.up().record;
        // let windowTitle=record.data["title"];
        // this.getView().setTitle(windowTitle);
        // this.getHtmlContentBydId(record.data["dId"],function(result){
            
        //     me.getView().add({xtype:"htmleditor",width:"100%",height:400,scrollable:true,html:record.data["content"]});
        // });

        
    },
    getHtmlContentBydId:function(dId,cb){
        Ext.Ajax.request({
           url:"/policydocument/queryBydId",
           params:{"dId":dId},
           success:function(result){
            cb(result);
           }
        });
    }

});
