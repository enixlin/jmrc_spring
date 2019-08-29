Ext.define('jmrc.view.performance.chart.BaseBar.BaseBarController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.performance-chart-basebar-basebar',


    afterRender:function(){
        //BAR图的基类的初化要实现以下功能
        /*
         一、定义图表的大小,布局方式
         二、获取图表的构造参数（如：图表的名称，类型(分时图或是对比图)，数据集store等）
         三、自定义方法的加载
          
         */
    }

});
