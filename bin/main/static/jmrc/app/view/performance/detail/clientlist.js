
Ext.define('jmrc.view.performance.detail.clientlist', {
    extend: 'Ext.panel.Panel',
    xtype: "clientlist",
    requires: [
        'jmrc.view.performance.detail.clientlistController',
        'jmrc.view.performance.detail.clientlistModel'
    ],

    controller: 'performance-detail-clientlist',
    viewModel: {
        type: 'performance-detail-clientlist'
    },

});
