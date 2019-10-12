Ext.define('jmrc.view.performance.detail.totaldetail', {
    extend: 'Ext.panel.Panel',
    xtype: "totaldetail",

    requires: [
        'jmrc.view.performance.detail.totaldetailController',
        'jmrc.view.performance.detail.totaldetailModel'
    ],

    controller: 'performance-detail-totaldetail',
    viewModel: {
        type: 'performance-detail-totaldetail'
    },

});