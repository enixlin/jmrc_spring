Ext.define('jmrc.view.performance.income.income', {
    extend: 'Ext.panel.Panel',
    xtype: "income",

    requires: [
        'jmrc.view.performance.income.incomeController',
        'jmrc.view.performance.income.incomeModel'
    ],

    controller: 'performance-income-income',
    viewModel: {
        type: 'performance-income-income'
    },

});