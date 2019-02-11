Ext.define('jmrc.view.policydocument.notes.NotesController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.policydocument-notes-notes',
    setContent:function(record){
        let me=this;
        let windowTitle=record.data["title"];
        this.getView().setTitle(windowTitle);
        this.getHtmlContentBydId(record.data["dId"],function(result){
            let contentEditor=Ext.create({xtype:"htmleditor",width:"100%",height:400,scrollable:true,html:record.data["content"]},);
            me.getView().add(contentEditor);
        });

        
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
