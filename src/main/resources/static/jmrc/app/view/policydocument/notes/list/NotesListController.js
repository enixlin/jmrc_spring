Ext.define('jmrc.view.policydocument.notes.list.NotesListController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.policydocument-notes-list-noteslist',

	afterRender : function() {
		let me = this;
		let view = this.getView();
		console.log("noteslist ...");
		console.log(me);
		console.log(view);
	
		view.setTitle("编　号"+view.dId);
		let store=me.getStore();
//		store.getProxy().extraParams = {dId:view.dId};
//		store.reLoad();
//		let model = view.getModel();
		console.log("store is ...");
		console.log(store);

	},

});
