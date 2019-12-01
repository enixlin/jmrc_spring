Ext
    .define(
        "jmrc.view.performance.exchange.totaldetailController", {
            extend: "Ext.app.ViewController",
            alias: "controller.performance-exchange-totaldetail",

            afterRender: function() {
                let me = this;
                let view = me.getView();
                let data = view.config.data;
                let start = view.up().query("textfield")[2].getValue()
                    .replace(/-/g, "");
                let end = view.up().query("textfield")[3].getValue()
                    .replace(/-/g, "");
                data.start = start;
                data.end = end;
                let updatedata = me.getLastUpdateDate();
                let totalPerformace = me
                    .getTotalExchangePerformance(data);
                Promise.all([updatedata, totalPerformace]).then(
                    function(result) {
                        data.updatedate = result[0];
                        data.totalexchange = Ext.JSON
                            .decode(result[1])[0].amount;
                        me.makeTotalReport(me, view, data);
                    });
            },
            makeTotalReport: function(me, view, data) {
                let width = window.innerWidth * 0.65;
                let height = window.innerHeight * 0.7;
                let summypanel = Ext.create({
                    xtype: "panel",
                    width: width,
                    height: height,

                    layout: "column",
                    // border:2,
                });
                let information = Ext
                    .create({
                        xtype: "panel",
                        width: width * 0.4,
                        height: height * 0.4,
                        columnWidth: 0.3,

                        margin: "25 0 0 25",
                        html: "<center><h3>数据更新日期：<font size=3px color=red>" +
                            data.updatedate.substring(0, 10) +
                            "</font></h3></center>" +
                            "<center><h3>结售汇总量：<font size=3px color=red>" +
                            Ext.util.Format.number(
                                data.totalexchange / 10000,
                                "0,000.00") +
                            "万美元</font></h3></center>"
                    });
                let grid = Ext
                    .create({
                        xtype: "grid",
                        width: width * 0.5,
                        height: height * 0.5,
                        border: 2,
                        columnWidth: 0.7,
                        scrollable: true,
                        margin: "25 0 0 25",
                        columns: [{
                                text: "<center>结售汇种类</center>",
                                dataIndex: "product_name"
                            },
                            {
                                text: "<center>笔数</center>",
                                dataIndex: "times",
                                renderer: function(v) {
                                    return Ext.util.Format
                                        .number(v, "0,000");
                                }
                            },
                            {
                                text: "<center>金额<br>(万美元)</center>",
                                dataIndex: "amount",
                                renderer: function(v) {
                                    return Ext.util.Format
                                        .number(v / 10000,
                                            "0,000.00");
                                }
                            },
                            {
                                text: "<center>笔数<br>（同比）</center>",
                                dataIndex: "times_compare",
                                renderer: function(v) {
                                    if (v > 0) {
                                        return ("<font color=green>" +
                                            Ext.util.Format
                                            .number(
                                                v,
                                                "0,000") + "</font>");
                                    } else {
                                        return ("<font color=red>" +
                                            Ext.util.Format
                                            .number(
                                                v,
                                                "0,000") + "</font>");
                                    }
                                }
                            },
                            {
                                text: "<center>金额<br>（同比）</center>",
                                dataIndex: "amount_compare",
                                renderer: function(v) {
                                    if (v > 0) {
                                        return ("<font color=green>" +
                                            Ext.util.Format
                                            .number(
                                                v / 10000,
                                                "0,000.00") + "</font>");
                                    } else {
                                        return ("<font color=red>" +
                                            Ext.util.Format
                                            .number(
                                                v / 10000,
                                                "0,000.00") + "</font>");
                                    }
                                }
                            },
                            {
                                width: 40,
                                xtype: "actioncolumn",
                                align: "center",
                                items: [{
                                    iconCls: "x-fa fa-bar-chart",
                                    tooltip: "分月明细",
                                    handler: function(view,
                                        rowIndex, colIndex,
                                        item, e, record) {
                                        let start = view.up()
                                            .up().up().config.data.start;
                                        let end = view.up()
                                            .up().up().config.data.end;
                                        let product = record.data.product_name;
                                        let container = view.up().up().up();
                                        view.up().up().up().controller
                                            .getProductMonthDetail(
                                                container,
                                                product,
                                                start,
                                                end);
                                    }
                                }]
                            },
                            {
                                width: 40,
                                xtype: "actioncolumn",
                                align: "center",
                                items: [{
                                    iconCls: "x-fa fa-users",
                                    tooltip: "客户明细",
                                    handler: function(view,
                                        rowIndex, colIndex,
                                        item, e, record) {
                                        let start = view.up()
                                            .up().up().config.data.start;
                                        let end = view.up()
                                            .up().up().config.data.end;
                                        let product = record.data.product_name;
                                        let container = view.up().up().up();
                                        view.up().up().up().controller
                                            .getProductClientDetail(
                                                container,
                                                product,
                                                start,
                                                end);
                                    }
                                }]
                            },
                            {
                                width: 40,
                                xtype: "actioncolumn",
                                align: "center",
                                items: [{
                                    iconCls: "x-fa fa-list",
                                    tooltip: "业务流水",
                                    handler: function(view,
                                        rowIndex, colIndex,
                                        item, e, record) {
                                        let start = view.up()
                                            .up().up().config.data.start;
                                        let end = view.up()
                                            .up().up().config.data.end;
                                        let product = record.data.product_name;
                                        view.up().up().up().controller
                                            .getProductDetail(
                                                product,
                                                start,
                                                end);
                                    }
                                }]
                            }
                        ],
                        store: Ext.create("Ext.data.Store", {
                            fields: ["product_name", "amount",
                                "times"
                            ],

                            proxy: {
                                url: "/exchange/getTypeTotal",
                                type: "ajax"
                            }
                        })
                    });

                /**
                 * 分月明细的柱状图
                 */
                let chart = Ext.create({
                    xtype: "cartesian",
                    width: width * 0.95,
                    height: height * 0.6,
                    reference: "chart",
                    scrollable: true,
                    tbar: ["->",
                        {
                            text: "保存图表",
                            platformConfig: {
                                desktop: {
                                    text: "保存图表"
                                }
                            },
                            handler: "onPreview"
                        }
                    ],


                    sprites: [{
                        type: 'text',
                        fillStyle: '#abcdef',
                        text: "全行结售汇业务分月明细图",
                        fontSize: 25,
                        x: width * 0.3,
                        y: 30

                    }],
                    store: {
                        fields: ["month", "amount", "times"],
                        proxy: {
                            url: "/exchange/getTypeTotalMonth",
                            type: "ajax"
                        }
                    },

                    axes: [{
                        type: "numeric",
                        position: "left",
                        grid: true,
                        title: {
                            text: "结售汇业务量（万美元）",
                            fontSize: 15
                        },
                        fields: "amount",
                        // renderer:function(v){return
                        // Ext.util.Format.number(v/10000,"0,000");}
                    }, {
                        type: "category",
                        position: "bottom",
                        grid: true,
                        title: {
                            text: "月份",
                            fontSize: 15
                        },
                        fields: "month"
                    }],
                    innerPadding: {
                        top: 50,
                        left: 5,
                        right: 5,
                        bottom: 0
                    },
                    insetPadding: {
                        top: 40,
                        left: 10,
                        right: 10,
                        bottom: 10
                    },
                    series: {
                        type: 'bar',
                        // margin:60,

                        subStyle: {
                            fill: ['#0fc835'],
                            stroke: 'blue'
                        },
                        xField: 'month',
                        yField: 'amount',
                        highlight: {
                            strokeStyle: "light",
                            fillStyle: "gold"
                        },
                        label: {
                            field: "amount",
                            display: "insideEnd",
                            renderer: "onSeriesLabelRender"
                        },
                        tooltip: {
                            trackMouse: true,
                            renderer: "onTooltipRender"
                        }
                    }

                });

                let chartPanel = Ext.create("Ext.panel.Panel", {
                    width: "100%",
                    scrollable: true,
                    height: height * 0.42,
                    columnWidth: 1,
                    margin: "20 0 0 0",
                    border: 5,

                });
                chartPanel.add(chart);

                summypanel.add(information);
                summypanel.add(grid);
                summypanel.add(chartPanel);
                view.add(summypanel);
                chart.getStore().load({
                    params: {
                        start: data.start,
                        end: data.end
                    }
                });

                grid.getStore().load({
                    params: {
                        start: data.start,
                        end: data.end
                    }
                });
                //						
                chart.show();

            },

            getLastUpdateDate: function() {
                return new Promise(function(resolve, reject) {
                    Ext.Ajax.request({
                        url: "/update/getLastUpdateDate",
                        params: { type: "settle" },
                        success: function(result) {
                            resolve(result.responseText);
                        }
                    });
                });
            },
            getTotalExchangePerformance: function(data) {
                return new Promise(function(resolve, reject) {
                    Ext.Ajax.request({
                        url: "/exchange/getTotalExchangePerformance",
                        params: {
                            start: data.start,
                            end: data.end
                        },
                        listeners: {
                            beforerequest: function() {
                                msgTip = Ext.MessageBox.show({
                                    title: "提示",
                                    msg: "信息刷新中,请稍候......"
                                });
                            },
                            requestcomplete: function() {
                                msgTip.hide(); // 加载完成，关闭提示框
                            }
                        },
                        success: function(result) {
                            resolve(result.responseText);
                        }
                    });
                });
            },

            getProductMonthDetail: function(
                container, product,
                start,
                end) {

                let me = this;
                let view = me.getView();
                let width = window.innerWidth;
                let height = window.innerHeight;
                let win = Ext.create("Ext.window.Window", {
                    width: width * 0.7,
                    height: height * 0.5,
                    title: product + "业务量分月明细图"
                });

                let chart = Ext.create({
                    xtype: "cartesian",
                    width: width * 0.65,
                    height: height * 0.35,
                    reference: "chart",

                    innerPadding: {
                        top: 50,
                        left: 5,
                        right: 5,
                        bottom: 0
                    },
                    insetPadding: {
                        top: 10,
                        left: 10,
                        right: 10,
                        bottom: 10
                    },
                    store: Ext.create("Ext.data.Store", {
                        fields: ["month", "times", "amount"],
                        proxy: {
                            url: "/exchange/getProductMonthPerformance",
                            type: "ajax"
                        }
                    }),
                    tbar: ["->",
                        {
                            text: "保存图表",
                            platformConfig: {
                                desktop: {
                                    text: "保存表格"
                                }
                            },
                            handler: "onPreview"
                        }
                    ],
                    axes: [{
                            type: "numeric",
                            title: product + "业务量(万美元)",
                            position: "left",
                            fields: "amount",
                            grid: true,
                            listeners: {

                            }
                        },
                        {
                            type: "category",
                            title: "月份",
                            position: "bottom",
                            grid: true,
                            fields: "month",
                        }
                    ],

                    series: {
                        type: "bar",
                        subStyle: {
                            fill: ["#abcdef"],
                            stroke: "1F6D91"
                        },
                        xField: "month",
                        yField: "amount",

                        tbar: ["->",
                            {
                                text: "保存图表",
                                platformConfig: {
                                    desktop: {
                                        text: "保存图表"
                                    }
                                },
                                handler: "onPreview"
                            }
                        ],
                        highlight: {
                            strokeStyle: "light",
                            fillStyle: "gold"
                        },
                        label: {
                            field: "amount",
                            display: "insideEnd",
                            renderer: "onSeriesLabelRender"
                        },
                        tooltip: {
                            trackMouse: true,
                            renderer: "onTooltipRender"
                        }
                    },
                    listeners: {
                        // afterrender: 'onAfterRender',
                        // beginitemedit: 'onBeginItemEdit',
                        // enditemedit: 'onEndItemEdit'
                    }

                });
                chart.getStore().load({
                    params: {
                        start,
                        end,
                        product_name: product
                    }
                });


                win.add(chart);
                container.add(win);
                win.show();

            },

            getProductClientDetail: function(container, product, start, end) {
                let width = window.innerWidth;
                let height = window.innerHeight;
                let win = Ext.create("Ext.window.Window", {
                    width: width * 0.7,
                    height: height * 0.5,
                    title: product + "业务客户明细表"
                });

                let grid = Ext.create({
                    xtype: "grid",
                    width: width * 0.7,
                    height: height * 0.4,
                    tbar: ["->", {
                            xtype: "textfield",
                            fieldLabel: "查找客户：",
                            listeners: {
                                change: function(me, newValue, oldValue, eOpts) {
                                    let grid = me.up().up();
                                    me.up().up().up().up().controller.filterclient(grid, newValue);
                                }
                            }
                        },
                        {
                            text: "保存表格",
                            platformConfig: {
                                desktop: {
                                    text: "保存表格"
                                }
                            },
                            handler: "onPreview"
                        }
                    ],
                    store: Ext.create("Ext.data.Store", {
                        fields: ["branchId", "branchName", "clientId", "clientName", "times", "amount", "times_compare", "amount_compare"],
                        proxy: {
                            url: "/exchange/getProductClientDetail",
                            type: "ajax"
                        }
                    }),
                    columns: [
                        { text: '行号', dataIndex: "branchId", width: 80 },
                        { text: '行名', dataIndex: "branchName", width: 200 },
                        { text: '客户号', dataIndex: "clientId", width: 50 },
                        { text: '客户名称', dataIndex: "clientName", width: 250 },
                        { text: '笔数', dataIndex: "times", width: 80, renderer: function(v) { return Ext.util.Format.number(v, '0,000') } },
                        { text: '金额', dataIndex: "amount", width: 150, renderer: function(v) { return Ext.util.Format.number(v, '0,000.00') } },
                        {
                            text: '笔数<br>同比',
                            dataIndex: "times_compare",
                            width: 80,
                            renderer: function(v) {
                                if (v < 0) {
                                    return "<font color=red>" + Ext.util.Format.number(v, '0,000') + "</font>";

                                } else {
                                    return "<font color=green>" + Ext.util.Format.number(v, '0,000') + "</font>";
                                }
                            }
                        },
                        {
                            text: '金额<br>同比',
                            dataIndex: "amount_compare",
                            width: 150,
                            renderer: function(v) {
                                if (v < 0) {
                                    return "<font color=red>" + Ext.util.Format.number(v, '0,000.00') + "</font>";
                                } else {
                                    return "<font color=green>" + Ext.util.Format.number(v, '0,000.00') + "</font>";

                                }
                            }
                        },
                    ],

                });
                grid.getStore().load({
                    params: {
                        start,
                        end,
                        product_name: product
                    }
                });
                win.add(grid);
                container.add(win);
                win.show();


            },

            /**
             * 客户筛选
             */
            filterclient: function(view, newClient) {
                let store = view.getStore();
                if (newClient == "") {
                    store.clearFilter();
                    console.log(store.getFilters());
                    return;
                }
                store.setFilters({
                    "operator": "like",
                    "value": newClient,
                    "property": "clientName"
                });
                console.log(store.getFilters());
            },


            /**
             * 日期筛选
             */
            filterdate: function(view, newDate) {


                let store = view.getStore();
                if (newDate == "") {
                    store.clearFilter();

                    console.log(store.getFilters());
                    return;
                }
                store.setFilters({
                    "operator": "like",
                    "value": newDate,
                    "property": "busy_date"
                });
                console.log(store.getFilters());
            },




            /*
             * 
             * 以下是图形的提示等渲染函数
             */
            onPreview: function() {
                if (Ext.isIE8) {
                    Ext.Msg.alert(
                        "Unsupported Operation",
                        "This operation requires a newer version of Internet Explorer."
                    );
                    return;
                }
                var chart = this.lookupReference("chart");
                chart.preview();
            },

            onDownload: function() {
                if (Ext.isIE8) {
                    Ext.Msg.alert(
                        "Unsupported Operation",
                        "This operation requires a newer version of Internet Explorer."
                    );
                    return;
                }
                var chart = this.lookupReference("chart");
                if (Ext.os.is.Desktop) {
                    chart.download({
                        filename: "Redwood City Climate Data Chart"
                    });
                } else {
                    chart.preview();
                }
            },
            // 格式化柱形图的数据标签
            onSeriesLabelRender: function(v) {
                return Ext.util.Format.number(v, "0,000");
            },

            onTooltipRender: function(tooltip, record, item) {
                let me = this;
                let view = me.getView();
                let config = view.config.data;
                let perform = Ext.util.Format.number(record.get("amount"), "0,000");
                tooltip.setHtml(
                    record.get("month") +
                    "业务量为：" +
                    perform +
                    "万美元,业务笔数为：" +
                    record.get("times")
                );
            },
            onAxisLabelRender: function(axis, label, layoutContext) {
                return Ext.util.Format.number(
                    layoutContext.renderer(label),
                    "0,000"
                );
            },




        });