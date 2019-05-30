Ext.define('jmrc.view.main.navitree.NaviTree', {
	extend : 'Ext.tree.Panel',
	xtype : 'navitree',

	requires : [ 'jmrc.view.main.navitree.NaviTreeController',
			'jmrc.view.main.navitree.NaviTreeModel' ],

	controller : 'main-navitree-navitree',
	viewModel : {
		type : 'main-navitree-navitree'
	},

	store : Ext.create("jmrc.store.TreeStore"),
	displayField : "name",
//	iconCls :"icon",
	//title : "功能菜单",
	rootVisible : false,
	listeners : {
		itemclick : {
			// element:'el',
			fn : function(thiss, record, item, index, e, eOpts) {
			
				this.controller.addTab(record.data);
			}
		}

	},
	   renderer: function(v, metaData, record) {
           metaData.glyph = record.icon;
           return v;
       }
	

});
