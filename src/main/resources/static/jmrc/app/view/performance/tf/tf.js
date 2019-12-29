Ext.define("jmrc.view.performance.tf.tf", {
    extend: "Ext.panel.Panel",
    xtype: "tf",
    requires: [
        "jmrc.view.performance.tf.tfController",
        "jmrc.view.performance.tf.tfModel"
    ],

    controller: "performance-tf-tf",
    viewModel: {
        type: "performance-tf-tf"
    },

    tbar : Ext.create({
		xtype : "toolbar",
		items : [ 
			{
			xtype : "combo",
			fieldLabel:"查询层次",
			displayField: "name", // 显示的字段，用函数getDisplayValue()获取
            valueField: "name", // 取值的字段，用函数getValue()获取
            value: "全行",
            store: {
                fields: ['code', 'name'],
                data: [{
                    "code": 1,
                    "name": "全行"
                }, {
                    "code": 2,
                    "name": "经营单位"
                }, {
                    "code": 3,
                    "name": "客户"
                }, ]
            },
		}, {
			xtype : "textfield",
			fieldLabel : "日期",
			value :""
		}, {
			xtype : "button",
			text : "查询",
			handler : "query"
		} ]
	}),
	

});