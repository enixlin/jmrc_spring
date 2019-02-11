
Ext.define('jmrc.view.policydocument.notes.Notes',{
    extend: 'Ext.window.Window',
    xtype:"notes",

    requires: [
        'jmrc.view.policydocument.notes.NotesController',
        'jmrc.view.policydocument.notes.NotesModel'
    ],

    controller: 'policydocument-notes-notes',
    viewModel: {
        type: 'policydocument-notes-notes'
    },

    autoShow:true,
    width:1000,
    height:500,
    x:400,
    y:100




});
