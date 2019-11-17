
Ext.define('jmrc.view.performance.exchange.exchange',{
    extend: 'Ext.panel.Panel',
    xtype: "exchange",
    
    requires: [
        'jmrc.view.performance.exchange.exchangeController',
        'jmrc.view.performance.exchange.exchangeModel'
    ],

    controller: 'performance-exchange-exchange',
    viewModel: {
        type: 'performance-exchange-exchange'
    },
    
    scrollable: true,
    layout: {
        type: 'table',
        // The total column count must be specified here
        columns: 1
    },

    // 查询工具栏
    tbar: [{
            xtype: "combo",
            fieldLabel: "分析主题：",
            name: "reportType",
            margin: "5 5 5 5 ",
            labelAlign: "right",
            labelWidth: 80,
            value: "结售汇分析",
            emptyText: "请选择分析报表类型",
            displayField: "name", // 显示的字段，用函数getDisplayValue()获取
            valueField: "name", // 取值的字段，用函数getValue()获取
            listeners: {
                change: function(me, newValue, oldValue, eOpts) {

                }
            }
        }, {
            xtype: "combo",
            emptyText: "请选择分析层次",
            width: 150,
            name: "level",
            displayField: "name", // 显示的字段，用函数getDisplayValue()获取
            valueField: "name", // 取值的字段，用函数getValue()获取
            value: "全行",
            store: {
                fields: ['code', 'name'],
                data: [{
                    "code": 1,
                    "name": "全行"
                }, {
                    "code": 2,
                    "name": "经营单位"
                }, {
                    "code": 3,
                    "name": "客户"
                }, ]
            },

        },

        {
            xtype: "textfield",
            fieldLabel: "开始日期：",
            labelWidth: 70,
            //value: "2019-01-01",
            inputType: "date",
            name: "start",
            margin: "5 5 5 5 ",
            labelAlign: "right"
        }, {
            xtype: "textfield",
            labelWidth: 70,
            fieldLabel: "结束日期：",
            name: "end",
            inputType: "date",
            margin: "5 5 5 5 ",
            labelAlign: "right"
        }, {
            xtype: "button",
            text: "查询",
            iconCls: 'x-fa fa-search',
            style: {
                // background : "#123456",
                fontColor: "red",
            },
            handler: "query"
        }
    ],

    

    
});