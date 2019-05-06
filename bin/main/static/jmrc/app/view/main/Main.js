/**
 * This class is the main view for the application. It is specified in app.js as
 * the "mainView" property. That setting automatically applies the "viewport"
 * plugin causing this view to become the body element (i.e., the viewport).
 * 
 * TODO - Replace this content of this view to suite the needs of your
 * application.
 */
Ext.define("jmrc.view.main.Main", {
	extend : "Ext.panel.Panel",
	xtype : "app-main",

	/**
	 * 需要引入的 类型/自定义组件，通过自动加载机制，引入时只需要引入这个组件的控件view就可以 其他的controller 和 model
	 * 需要引入的 类型/自定义组件，通过自动加载机制，引入时只需要引入这个组件的控件view就可以 其他的controller 和 model
	 * 都是自动引入的
	 */
	requires : [ //
	"Ext.plugin.Viewport", //
	"Ext.window.MessageBox",//
	"jmrc.view.main.MainController", //
	"jmrc.view.main.MainModel",//
	// "jmrc.view.user.admin.User",//
	// "jmrc.view.found.Found",
	// "jmrc.view.found.FoundList",
	// "jmrc.view.user.user_add",
	// "jmrc.view.main.navitree.NaviTree",//
	// "jmrc.view.main.content.Content",//

	],

	controller : "main",

	viewModel : "main",
	// renderTo : Ext.getBody(),
	layout : "fit",
	
	plugins : "viewport",
	frame:true,
	items : [ {
		xtype : "container",
		layout : 'border',
		frame:true,
		width : window.innerWidth*0.98,
		height : window.innerHeight*0.95,
		
		items : [ {
			region : "north",
			height : 100,
			width : "100%",
		}, {
			region : "west",
			xtype : 'navitree',
			layout : 'auto',
			width : "20%",
			split : true
		}, {
			region : "center",
			width : "80%",
			xtype : 'content',
			layout : 'fit',
			id : "contentpanel",
			tabManager : []
		}

		]

	} ],

});