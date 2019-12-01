Ext.define('jmrc.view.performance.deposit.deposit', {
    extend: 'Ext.panel.Panel',
    xtype: "deposit",

    requires: [
        'jmrc.view.performance.deposit.depositController',
        'jmrc.view.performance.deposit.depositModel'
    ],

    controller: 'performance-deposit-deposit',
    viewModel: {
        type: 'performance-deposit-deposit'
    },


});