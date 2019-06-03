Ext.define('jmrc.view.policydocument.notes.list.NotesListController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.policydocument-notes-list-noteslist',

	afterRender : function() {
		let me = this;
		let view = this.getView();
		let store=me.getStore();
		//store.getProxy().extraParams = {dId:"5022"};
		//store.reLoad();
		//let model = view.getModel();
		console.log("store is ...");
		console.log(store);

	},

});
