Ext.define('jmrc.view.user.UserController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.user-user',

	afterRender : function() {
		// alert("after");
		this.getView().add({
			xtype : "addUI",
			reference : "addUI",
			modal:true,
			closable:false,
			id : "addUI"
		});
	},

	showAddUI : function() {
		// alert("show");
		let win = Ext.getCmp("addUI"); 
		let form=win.query("form")[0].getForm().getFields();
		console.log("form is ");
		console.log(form);

		win.show();

	},

	hideAddUI : function() {
		// alert("show");
		let win = Ext.getCmp("addUI");
		console.log(win);

		win.hide();

	}

});
