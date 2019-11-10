
Ext.define('jmrc.view.performance.exchange.clientdetail',{
    extend: 'Ext.panel.Panel',
    xtype:"exchangeclientdetail",
    requires: [
        'jmrc.view.performance.exchange.clientdetailController',
        'jmrc.view.performance.exchange.clientdetailModel'
    ],

    controller: 'performance-exchange-clientdetail',
    viewModel: {
        type: 'performance-exchange-clientdetail'
    },

    html: 'Hello, exchangeclientdetail!!'
});
