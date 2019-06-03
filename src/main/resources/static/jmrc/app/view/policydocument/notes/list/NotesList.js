Ext.define('jmrc.view.policydocument.notes.list.NotesList', {
	extend : 'Ext.window.Window',
	xtype : "noteslist",

	requires : [ 'jmrc.view.policydocument.notes.list.NotesListController',
			'jmrc.view.policydocument.notes.list.NotesListModel' ],

	controller : 'policydocument-notes-list-noteslist',
	viewModel : {
		type : 'policydocument-notes-list-noteslist'
	},

	closable:true,
	items : [ {
		xtype:"grid",
		id:"notelistgrid",
		 bind: { store: "{notesListStore}" },//注意Store名称要用大括号括起来
         autoShow: true,
         columns: [
             {text: "编号", dataIndex: 'dId', width: "10%", align: "left", editor: "textfield"},
             {text: "文号", dataIndex: 'docNum', width: "20%", align: "left", editor: "textfield"},
             { text: "标题", dataIndex: 'title', width: "50%", cellWrap: true, align: "left" },
             { text: "笔记人", dataIndex: 'name', width: "20%", align: "center" },
             //{ text: "备注", width: "10%", align: "center" },
         ],
         listeners:{
             itemclick:function ( thiss, record, item, index, e, eOpts ){
                 this.up().up().controller.showFile(record);
                }
        }
		
	} ]
});
