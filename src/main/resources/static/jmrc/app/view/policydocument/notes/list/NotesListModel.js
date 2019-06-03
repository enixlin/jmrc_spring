Ext.define('jmrc.view.policydocument.notes.list.NotesListModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.policydocument-notes-list-noteslist',
    data: {
        name: 'jmrc'
    },
    stores:{
        notesListStore:{
            fields:['docNum','title','name',"dId"],
            autoLoad:true,
            params:{},
            proxy:{
                url:"/policydocument/getPolicyNoteBydId",
                type:"ajax",
                extraParams: {
                    dId: 0
                }
            },
           
        }
    }

});
