Ext.define('jmrc.view.performance.chart.customerpiechart.piechart', {
    extend: 'Ext.panel.Panel',
    xtype: "myPieChart",

    requires: [
        'jmrc.view.performance.chart.customerpiechart.piechartController',
        'jmrc.view.performance.chart.customerpiechart.piechartModel'
    ],

    controller: 'performance-chart-customerpiechart-piechart',
    viewModel: {
        type: 'performance-chart-customerpiechart-piechart'
    },

    html: 'Hello, World!!'
});