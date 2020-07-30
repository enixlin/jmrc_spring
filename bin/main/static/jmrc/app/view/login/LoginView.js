Ext.define("jmrc.view.login.LoginView", {
	extend : "Ext.form.Panel",
	xtype : "login",

	requires : [ "jmrc.view.login.LoginController",
			"jmrc.view.login.LoginModel",
			// 'jmrc.view.main.Main',
			// "jmrc.view.register.RegisterView",
			"jmrc.view.login.Bg_animationPanel",
			"jmrc.view.login.Bg_animationPanelController",
			"jmrc.view.login.LoginWindow", 
			"jmrc.view.login.InformationBar",
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

		height : window.innerHeight,
		width : window.innerWidth,

		layout : "vbox",
		margin : '5 5 5 5',
	
		items : [ {
			xtype : "bg_animation_panel",
			// border : '5px solid gray',
			margin : '5 5 5 5',
			align : "center",
			height : window.innerHeight * 0.8,
		},

		{
			xtype : "container",
		
			layout:"hbox",
			width: "97%",
			margin : '5 5 5 5',
			height : window.innerHeight * 0.15,
//			style : {
//				border : '2px solid gray',
//				borderRadius : '5px'
//			},

			items : [ {
				xtype : "informationbar",
				//height : window.innerHeight * 0.2,
				width:	window.innerWidth*0.4,
				margin : '5 5 5 5',

			}, {
				xtype : "loginwindow",
				width:	window.innerWidth*0.55,
				//width:	630,
				margin : '15 5 5 5',
				layout:{
					type:'hbox',
					pack:"end"
				},
			},

			]

		} ],
	},

	]
});
