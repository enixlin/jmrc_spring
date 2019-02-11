Ext.define("jmrc.view.login.LoginView", {
	extend : "Ext.form.Panel",
	xtype : "login",

	requires : [ "jmrc.view.login.LoginController",
			"jmrc.view.login.LoginModel",
			// 'jmrc.view.main.Main',
			// "jmrc.view.register.RegisterView",
			"jmrc.view.login.Bg_animationPanel",
			"jmrc.view.login.Bg_animationPanelController",
			"jmrc.view.login.LoginWindow", "jmrc.view.login.InformationBar",
			"jmrc.view.login.InformationBarController" ],

	plugins : "viewport",

	controller : "login-login",
	viewModel : {
		type : "login-login"
	},
	// Fields will be arranged vertically, stretched to full width

	url : "/auth/authLogin",
	items : [ {
		xtype : "container",
		height : 800,
		layout : "vbox",
		margin : '5 10 0 100',
		items : [ {
			xtype : "bg_animation_panel",
			//border : '5px solid gray',
			margin : '5 50 0 5',
			height : 500,

		}, {
			xtype : "loginwindow",

		}, {
			xtype : "informationbar",
			margin : '5 20 0 10',

		} ],
	},

	]
});
