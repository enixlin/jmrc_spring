Ext.define('jmrc.view.policydocument.notes.list.NotesListController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.policydocument-notes-list-noteslist',

	afterRender : function() {
		let me = this;
		let view = this.getView();
		//let model = view.getModel();
		console.log("view is ...");
		console.log(view.getStore());
		console.log(model);

	},

});
