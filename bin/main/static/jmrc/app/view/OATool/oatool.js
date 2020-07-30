/**
 * 办公小工具
 */
Ext.define('jmrc.view.oatool.oatool', {
	extend : 'Ext.panel.Panel',
	xtype:'oatool',

	requires : [ 'jmrc.view.oatool.oatoolController',
			'jmrc.view.oatool.oatoolModel' ],

	controller : 'oatool-oatool',
	viewModel : {
		type : 'oatool-oatool'
	},

	items : [ {
		xtype : 'container',
		layout : 'vbox',

		items : [ {
			xtype : 'combo',
			id : 'toolselectbox',
			displayField:'name',
			handler:'showselect'
			
		}, {
			xtype : 'container',
			layout : 'hbox'
		} ]
	} ]

});
