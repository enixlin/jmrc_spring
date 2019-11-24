Ext.define('jmrc.view.performance.exchange.clientdetail', {
    extend: 'Ext.panel.Panel',
    xtype: "exchangeclientdetail",
    requires: [
        'jmrc.view.performance.exchange.clientdetailController',
        'jmrc.view.performance.exchange.clientdetailModel'
    ],

    controller: 'performance-exchange-clientdetail',
    viewModel: {
        type: 'performance-exchange-clientdetail'
    },

    tbar: [{
        xtype: "textfield",
        fieldLabel: "查找客户：",
        listeners: {
            change: function(me, newValue, oldValue, eOpts) {

                let unit = me.up().up().query("textfield")[0].value;
                let client = me.value;
                console.log("unit is " + unit + "  client is :" + client);
                me.up().up().controller.filterclient(unit, newValue);
            }
        }
    }, "->", {
        xtype: "button",
        text: "导出表格",
        handler: "exportExcel"
    }],

});