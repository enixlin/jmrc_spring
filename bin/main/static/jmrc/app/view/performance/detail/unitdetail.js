
Ext.define('jmrc.view.performance.detail.unitdetail',{
    extend: 'Ext.panel.Panel',
    xtype:"unitdetail",

    requires: [
        'jmrc.view.performance.detail.unitdetailController',
        'jmrc.view.performance.detail.unitdetailModel'
    ],

    controller: 'performance-detail-unitdetail',
    viewModel: {
        type: 'performance-detail-unitdetail'
    },

});
