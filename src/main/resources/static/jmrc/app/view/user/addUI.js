Ext.define('jmrc.view.user.addUI',
		{
			extend : 'Ext.window.Window',
			xtype : "addUI",

			requires : [ 'jmrc.view.user.addUIController',
					'jmrc.view.user.addUIModel' ],

			controller : 'user-addui',
			viewModel : {
				type : 'user-addui'
			},

			width : 300,
			height : 300
		});
