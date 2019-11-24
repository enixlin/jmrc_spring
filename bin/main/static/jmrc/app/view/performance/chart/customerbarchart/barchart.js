
Ext.define('jmrc.view.performance.chart.customerbarchart.barchart',{
    extend: 'Ext.panel.Panel',
    xtype:"mybarchart",

    requires: [
        'jmrc.view.performance.chart.customerbarchart.barchartController',
        'jmrc.view.performance.chart.customerbarchart.barchartModel'
    ],

    controller: 'performance-chart-customerbarchart-barchart',
    viewModel: {
        type: 'performance-chart-customerbarchart-barchart'
    },

 
});
