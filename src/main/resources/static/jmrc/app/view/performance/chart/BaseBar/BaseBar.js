
/**
 * 柱状图的基类，只定义了传入配置参数，显示柱装图和明细表格
 * 图表的点击事件在子类的控制器中实现
 */
Ext.define('jmrc.view.performance.chart.BaseBar.BaseBar',{
    extend: 'Ext.panel.Panel',
    xtype:"basebar",

    requires: [
        'jmrc.view.performance.chart.BaseBar.BaseBarController',
        'jmrc.view.performance.chart.BaseBar.BaseBarModel'
    ],

    controller: 'performance-chart-basebar-basebar',
    viewModel: {
        type: 'performance-chart-basebar-basebar'
    },


   
   
});
