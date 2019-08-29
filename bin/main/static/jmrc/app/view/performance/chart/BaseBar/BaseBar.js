
Ext.define('jmrc.view.performance.chart.BaseBar.BaseBar',{
    extend: 'Ext.panel.Panel',

    requires: [
        'jmrc.view.performance.chart.BaseBar.BaseBarController',
        'jmrc.view.performance.chart.BaseBar.BaseBarModel'
    ],

    controller: 'performance-chart-basebar-basebar',
    viewModel: {
        type: 'performance-chart-basebar-basebar'
    },

    html: 'Hello, World!!'
});
