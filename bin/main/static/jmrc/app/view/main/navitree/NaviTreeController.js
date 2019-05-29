Ext.define('jmrc.view.main.navitree.NaviTreeController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.main-navitree-navitree',

	// 添加功能页面
	addTab : function(item) {
		// 如果不是叶节点的话就返回
		if (item["leaf"]) {
			var ref = this.getView().up().query("content");
			// 如果叶节页面已创建，就激活跳转，否则才创建
			var obj = Ext.getCmp(item['panel']);// 获取tab
			// 如果已经存在tab
			if (obj) {
				ref[0].setActiveTab(item['panel']);// 激活要跳转的tab
			} else {
				ref[0].add([ {
					id : item['panel'],
					xtype : item['panel'],
					title : item["name"],
					closable : true, 
				} ]);
				ref[0].setActiveTab(item['panel']);// 激活要跳转的tab
			}
		}
	}

});
