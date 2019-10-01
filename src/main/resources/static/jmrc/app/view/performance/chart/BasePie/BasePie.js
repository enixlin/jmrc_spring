
Ext.define('jmrc.view.performance.chart.BasePie.BasePie',{
    extend: 'Ext.panel.Panel',
    xtype:'basepie',

    requires: [
        'jmrc.view.performance.chart.BasePie.BasePieController',
        'jmrc.view.performance.chart.BasePie.BasePieModel'
    ],

    controller: 'performance-chart-basepie-basepie',
    viewModel: {
        type: 'performance-chart-basepie-basepie'
    },

});
