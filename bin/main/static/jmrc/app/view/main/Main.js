/**
 * This class is the main view for the application. It is specified in app.js as
 * the "mainView" property. That setting automatically applies the "viewport"
 * plugin causing this view to become the body element (i.e., the viewport).
 * 
 * TODO - Replace this content of this view to suite the needs of your
 * application.
 */
Ext.define("jmrc.view.main.Main", {
	extend : "Ext.tab.Panel",
	xtype : "app-main",

	/**
	 * 需要引入的 类型/自定义组件，通过自动加载机制，引入时只需要引入这个组件的控件view就可以 其他的controller 和 model
	 * 都是自动引入的
	 */
	requires : [ //
	"Ext.plugin.Viewport", //
	"Ext.window.MessageBox",//
	"jmrc.view.main.MainController", //
	"jmrc.view.main.MainModel",//
	"jmrc.view.main.List", //
	"jmrc.view.user.UserView", //
	"jmrc.view.tab.tablayout", //
	"jmrc.view.oatool.oatool",//
	"jmrc.view.oatool.calendar.Calendar", //
	"jmrc.view.oatool.oatool",//
	"jmrc.view.policydocument.Policydocument",//
	"jmrc.view.user.UserViewController",//,
	"jmrc.view.policydocument.notes.Notes",
	"jmrc.view.policydocument.file.file"
	],

	controller : "main",
	viewModel : "main",
	plugins : "viewport",
	ui : "navigation",

	tabBarHeaderPosition : 1,
	titleRotation : 0,
	tabRotation : 0,

	header : {
		height : 60,
		layout : {

			align : "top",

		},

		title : {
			bind : {
				text : CFG.getTitleBanner() + " - " + CFG.getVersion(),
			},

			style : {
				font : "24px bold",
				color : "white",
				margin : "16px 3px 0px 20px"
			},
			flex : 0.5,

		},
		iconCls : "fa-university",
	// icon:"/jmrc_logo.jpg",
	},
	// header : false,

	tabBar : {
		height : 60,
		scrollable : true,
		flex : 1,

		layout : {
			align : "stretch",
			overflowHandler : "none"
		},

		style : {
			font : "20px bold",
			color : "white",

		}
	// items: [{ type: 'button', handler: 'logout', text: '退出登录' }]
	},

	responsiveConfig : {
		tall : {
			headerPosition : "left"
		},
		wide : {
			headerPosition : "top"
		}
	},

	// tabStretchMax:true,

	defaults : {
		bodyPadding : 5,

		tabConfig : {
			padding : "10px 10px 10px 10px",
			plugins : "responsive",

			responsiveConfig : {
				wide : {
					iconAlign : "left",
					textAlign : "left"
				},
				tall : {
					iconAlign : "top",
					textAlign : "center"
				}
			}
		}
	},

	dockedItems : [ {
		xtype : "container",
		layout : "hbox",
		items : [ {
			xtype : 'textfield',
			value : "this is a textfield...",
			width : "30%",
			margin : "5 10 10 10"
		}, {
			xtype : "container",
			layout : {
				type : 'hbox',
				pack : "center"
			},
			width : "70%",
			defaults : {
				width : 120,
				xtype : "button",
				margin : "5 10 10 10"
			},
			items : [ {
				xtype : "tbfill"
			}, {
				text : "",
				reference : "user_name"
			}, {
				text : "角色转换",
				handler : "showRolerListWindow",
				defaultAlign : 'right'
			}, {
				text : "退出登录",
				handler : "logout"
			}, ]
		} ]
	}

	],

// defaults:{
// width:"100%",
// },
// items : [ {
// xtype : "container",
// layout : "hbox",
// width:'100%',
// //renderTo:Ext.getBody(),
// items : [ {
// xtype : "textfield",
//			
// value : "textfield"
// }, {
// xtype : "container",
//			
// layout : {type:"hbox",pack:"center"},
// defaults:{
// margin:"5 5 5 5",
// width:80
// },
// items : [ {
// xtype : "tbfill"
// }, {
// xtype:'button',
// value : "user",
// //id:"user_name1",
// reference : "user_name"
// }, {
// xtype:'button',
// value : "角色转换",
// handler : "showRolerListWindow",
// defaultAlign : 'right'
// } ]
// } ]
// } ]

});