Ext.define("jmrc.view.performance.detail.totaldetailController", {
    extend: "Ext.app.ViewController",
    alias: "controller.performance-detail-totaldetail",

    afterRender: function() {
        let me = this;
        let view = me.getView();
        let data = view.config.data;
        let start = view.up().query("textfield")[2].getValue()
            .replace(/-/g, "");
        let end = view.up().query("textfield")[3].getValue().replace(/-/g, "");
        data.start = start;
        data.end = end;
        let updatedata = me.getLastUpdateDate();
        let totalPerformace = me.getTotalSettlePerformance(data);
        let totalTask = me.getTotalTask(data);
        Promise.all([updatedata, totalPerformace, totalTask]).then(function(result) {
            console.log(result);
            data.updatedate = result[0];
            data.totalsettle = result[1];
            data.totalTask = result[2];
            // msgTip.hide(); // 加载完成，关闭提示框
            me.makeTotalReport(me, view, data);
        });
    },

    /**
     * 生成全行业务总览
     */
    makeTotalReport: function(scope, view, data) {

        let height = window.innerHeight * 0.9;
        let width = window.innerWidth;
        let summypanel = Ext.create("Ext.panel.Panel", {
            width: width * 0.65,
            height: height,
            // renderTo: Ext.getBody(),

            layout: "column",
            margin: 5,
            scrollable: true,

            items: [{
                xtype: "panel",
                layout: "form",
                columnWidth: 0.3,

                width: width * 0.2,
                height: height * 0.5,
                // margin: 5,
                html: "<h3>数据更新日期：<font color=red>" + data.updatedate +
                    "</font></h3><br><h3>国际结算业务量：<font color=blue >" +
                    Ext.util.Format.number(data.totalsettle, "0,000.00") +
                    " </font>万美元</h3>" +
                    "<br><h3>全年任务完成率: &nbsp;&nbsp;&nbsp;<font color=blue >" +
                    Ext.util.Format.number((data.totalsettle / data.totalTask) * 100, "0,000.00") + "%</font></h3>",
                items: [{
                        xtype: "button",
                        text: "经营单位分析",
                        margin: 10,
                        handler: "unitAnalyis"
                    },

                    {
                        xtype: "button",
                        text: "客户分析",
                        margin: 10,
                        handler: "clientAnalyis"
                    },
                    {
                        xtype: "button",
                        text: "分月明细",
                        margin: 10,
                        handler: "getAllMonthDetail"
                    },
                ]
            }]
        });

        let productGrid = Ext.create({
            xtype: "grid",
            layout: "form",
            float: "right",
            columnWidth: 0.7,
            border: 2,
            grid: true,
            width: width * 0.4,
            height: height * 0.5,
            margin: 5,
            scrollable: true,
            store:{
            	   fields: ["product_name", "amount", "times", "amount_compare", "times_compare"],
                   proxy: {
                       url: "/settlerecord/getAllProductDetail",
                       type: "ajax"
                   }
            },
            // bind: { store: "{getSettleTypeProformanceByDate}" },
            tbar: ["->", {
                text: "导出表格",
                xtype: "button",
                handler: function() {
                    let me = this;
                    let detailType = "exportAllProductDetailExcel";
                    let product = "";
                    me.up().up().up().up().controller.exportExcel(product, data.start, data.end, detailType);
                }

            }],
            columns: [{
                    header: "产品<br>名称",
                    dataIndex: "product_name",
                    width: 150
                }, {
                    header: "笔数",
                    dataIndex: "times",
                    width: 50
                }, {
                    header: "金额",
                    dataIndex: "amount",
                    width: 100,
                    renderer: function(value) {
                        return Ext.util.Format.number(value, "0,000.00");
                    }
                }, {
                    header: "笔数<br>同比",
                    dataIndex: "times_compare",
                    width: 50,
                    renderer: function(value) {
                        if (value < 0) {
                            return "<font color='red'>" + Ext.util.Format.number(value, "0,000") + "</font>";
                        } else {
                            return "<font color='green'>" + Ext.util.Format.number(value, "0,000") + "</font>";
                        }

                    }
                }, {
                    header: "金额<br>同比",
                    dataIndex: "amount_compare",
                    width: 100,
                    renderer: function(value) {
                        if (value < 0) {
                            return "<font color='red'>" + Ext.util.Format.number(value, "0,000.00") + "</font>";
                        } else {
                            return "<font color='green'>" + Ext.util.Format.number(value, "0,000.00") + "</font>";
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
                        // handler:"getProductMonthDetail"
                        handler: function(view, rowIndex, colIndex, item, e, record) {
                            let start = view.up().up().up().config.data.start;
                            let end = view.up().up().up().config.data.end;
                            let product = record.data.product_name;
                            view
                                .up()
                                .up()
                                .up()
                                .controller.getProductMonthDetail(product, start, end);
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
                        handler: function(view, rowIndex, colIndex, item, e, record) {
                            let start = view.up().up().up().config.data.start;
                            let end = view.up().up().up().config.data.end;
                            let product = record.data.product_name;
                            view
                                .up()
                                .up()
                                .up()
                                .controller.getProductClientDetail(product, start, end);

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
                        handler: function(view, rowIndex, colIndex, item, e, record) {
                            let start = view.up().up().up().config.data.start;
                            let end = view.up().up().up().config.data.end;
                            let product = record.data.product_name;
                            view
                                .up()
                                .up()
                                .up()
                                .controller.getProductDetail(product, start, end);

                        }
                    }]
                }




            ],
        });
        
        let store=productGrid.getStore();

        store.load({
            params: {
                start: data.start,
                end: data.end,
            },
        });
        summypanel.add(productGrid);



        let chart = Ext.create({
            xtype: 'cartesian',
            renderTo: document.body,
            width: "100%",
            height: 400,
            reference: "chart",
            tbar: [{
                text: "生成图像",
                platformConfig: {
                    desktop: {
                        text: "生成图像"
                    }
                },
                handler: "onPreview"
            }],
            insetPadding: {
                top: 50,
                bottom: 40,
                left: 20,
                right: 40
            },


            store: {
                fields: ['month', 'amount', 'times'],
                proxy: {
                    url: "/settlerecord/getMonthPerformance",
                    type: "ajax"
                }
            },
            sprites: {
                type: 'text',
                text: '全行国际结算量分月明细图（' + data.start + "-" + data.end + ')',
                fontSize: 26,
                width: 300,
                height: 30,
                x: 240, // the sprite x position
                y: 25 // the sprite y position
            },
            axes: [{
                type: 'numeric',
                position: 'left',
                grid: true,
                title: {
                    text: '国际结算量（万美元）',
                    fontSize: 15
                },
                grid: true,
                fields: 'amount'
            }, {
                type: 'category',
                position: 'bottom',
                grid: true,
                title: {
                    text: '月份',
                    fontSize: 15
                },
                fields: 'month'
            }],
            series: {
                type: 'bar',
                subStyle: {
                    fill: ['#388FAD'],
                    stroke: '#1F6D91'
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
        chart.getStore().load({
            params: {
                start: data.start,
                end: data.end
            }
        });


        let chartPanel = Ext.create({
            xtype: "panel",
            border: 2,
            width: 1000,
            height: 400,
            colspan: 2,
            columnWidth: 1,
            margin: 5
        });
        chart.show();

        chartPanel.add(chart);
        summypanel.add(chartPanel);



        view.add(summypanel);




    },

    getProductMonthDetail: function(product, start, end) {
        // alert("getProductMonthDetail");
        // alert(product);
        let me = this;
        let view = me.getView();
        let win = Ext.create("Ext.window.Window", {
            width: window.innerWidth * .7,
            height: window.innerHeight * .6,
            scrollable: true,
            layout: {
                type: "table",
                columns: 2
            },

        });
        let store = Ext.create("Ext.data.Store", {

            fields: ["month", "times", "amount"],
            proxy: {
                url: "/settlerecord/getProductMonthPerformance",
                type: "ajax",
            }
        });

        let grid = Ext.create("Ext.grid.Panel", {
            width: 400,
            store: store,
            scrollable: true,
            tbar: ["->", {
                xtype: "button",
                text: "导出表格",
                handler: function() {
                    let me = this;
                    let detailType = "exportProductMonthDetailExcel";
                    me.up().up().up().up().controller.exportExcel(product, start, end, detailType);
                }
            }],
            columns: [
                { header: "月份", dataIndex: "month" },
                { header: "笔数", dataIndex: "times", renderer: function(value) { return Ext.util.Format.number(value, "0,000") } },
                { header: "金额", dataIndex: "amount", renderer: function(value) { return Ext.util.Format.number(value, "0,000.00") } },
            ],

        });


        // 插入柱状图的框架
        win.add({
            xtype: "cartesian",
            width: window.innerWidth * 0.4,
            height: window.innerHeight * 0.4,
            reference: "chart",
            border: 2,
            margin: 20,
            innerPadding: "50 0 0 10",
            tbar: [{
                text: "生成图像",
                platformConfig: {
                    desktop: {
                        text: "生成图像"
                    }
                },
                handler: "onPreview"
            }]
        });

        // 取得图型对象，绑定数据集
        let cartesian = win.query("cartesian")[0];

        // 设定图表内图型中的标题
        let sprites = {
            type: "text",
            text: product + "业务量分月明细图",
            fontSize: 15,
            width: 100,
            height: 30,

            x: 100,
            y: 30
        };
        let axes = [{
                type: "numeric",
                position: "left",
                title: {
                    text: "业务量",
                    fontSize: 15
                },
                fields: "amount",
                // margin:"60 5 5 5 ",
                minimum: 0
            },
            {
                type: "category",
                position: "bottom",
                margin: 10,
                title: {
                    text: "月份",
                    fontSize: 12
                },
                fields: "month"
            }
        ];
        let series = {
            type: "bar",
            subStyle: {
                fill: ["#abcdef"],
                stroke: "#1F6D91"
            },
            xField: "month",
            yField: "amount",
            yValue: "amount",
            highlight: {
                strokeStyle: "light",
                fillStyle: "gold"
            },
            label: {
                field: "amount",
                display: "insideEnd",
                renderer: function(v) { return Ext.util.Format.number(v, "0,000") }
            },
            tooltip: {
                trackMouse: true,
                renderer: "onTooltipRender"
            }
        };
        // 加入轴
        cartesian.setAxes(axes);
        // 设定轴的标题
        cartesian.setSeries(series);

        cartesian.setSprites(sprites);
        // 设定bar图的store

        // 设定柱型图的数据集
        cartesian.bindStore(store);


        store.load({
            params: { product, start, end },
        });
        win.add(grid);
        view.add(win);
        win.show();

    },

    getAllMonthDetail: function() {
        // alert("getProductMonthDetail");
        // alert(product);
        let me = this;
        let view = me.getView();
        let data = view.config.data;
        let start = view.up().query("textfield")[2].getValue()
            .replace(/-/g, "");
        let end = view.up().query("textfield")[3].getValue().replace(/-/g, "");
        data.start = start;
        data.end = end;
        let container = view.query("panel")[2];
        let win = Ext.create("Ext.window.Window", {
            width: window.innerWidth * .7,
            height: window.innerHeight * .6,
            scrollable: true,
            layout: {
                type: "table",
                columns: 2
            },

        });

        let store = Ext.create("Ext.data.Store", {

            fields: ["month", "times", "amount"],
            proxy: {
                url: "/settlerecord/getMonthPerformance",
                type: "ajax",
            }
        });

        let grid = Ext.create("Ext.grid.Panel", {
            width: 400,
            store: store,
            scrollable: true,
            tbar: ["->", {
                xtype: "button",
                text: "导出表格",
                handler: function() {
                    let me = this;
                    let detailType = "exportProductMonthDetailExcel";
                    me.up().up().up().up().controller.exportExcel(product, start, end, detailType);
                }
            }],
            columns: [
                { header: "月份", dataIndex: "month" },
                { header: "笔数", dataIndex: "times", renderer: function(value) { return Ext.util.Format.number(value, "0,000") } },
                { header: "金额", dataIndex: "amount", renderer: function(value) { return Ext.util.Format.number(value, "0,000.00") } },
            ],

        });


        // 插入柱状图的框架
        win.add({
            xtype: "cartesian",
            width: window.innerWidth * 0.4,
            height: window.innerHeight * 0.4,
            reference: "chart",
            border: 2,
            margin: 20,
            innerPadding: "50 0 0 10",
            tbar: [{
                text: "生成图像",
                platformConfig: {
                    desktop: {
                        text: "生成图像"
                    }
                },
                handler: "onPreview"
            }]
        });

        // 取得图型对象，绑定数据集
        let cartesian = win.query("cartesian")[0];

        // 设定图表内图型中的标题
        let sprites = {
            type: "text",
            text: "业务量分月明细图",
            fontSize: 15,
            width: 100,
            height: 30,

            x: 100,
            y: 30
        };
        let axes = [{
                type: "numeric",
                position: "left",
                title: {
                    text: "业务量",
                    fontSize: 15
                },
                fields: ["amount", "amount"],
                // margin:"60 5 5 5 ",
                minimum: 0
            },
            {
                type: "category",
                position: "bottom",
                margin: 10,
                title: {
                    text: "月份",
                    fontSize: 12
                },
                fields: "month"
            }
        ];
        let series = {
            type: "bar",
            subStyle: {
                fill: ["#abcdef"],
                stroke: "#1F6D91"
            },
            xField: "month",
            yField: "amount",
            yValue: "amount",
            highlight: {
                strokeStyle: "light",
                fillStyle: "gold"
            },
            label: {
                field: "amount",
                display: "insideEnd",
                renderer: function(v) { return Ext.util.Format.number(v, "0,000") }
            },
            tooltip: {
                trackMouse: true,
                renderer: "onTooltipRender"
            }
        };
        // 加入轴
        cartesian.setAxes(axes);
        // 设定轴的标题
        cartesian.setSeries(series);

        cartesian.setSprites(sprites);
        // 设定bar图的store

        // 设定柱型图的数据集
        cartesian.bindStore(store);


        store.load({
            params: { start, end },
        });
        win.add(grid);
        container.add(win);
        win.show();



    },


    getProductClientDetail: function(product, start, end) {

        let me = this;
        let view = me.getView();
        let win = Ext.create("Ext.window.Window", {
            width: window.innerWidth * .7,
            height: window.innerHeight * .66,
            title: product + "业务量统计表,单位:万美元,统计时间(" + start + "-" + end + ")",
            scrollable: true,
            layout: {
                type: "table",
                columns: 2
            },

        });
        let store = Ext.create("Ext.data.Store", {

            fields: ["clientId", "times", "amount", "clientName", "amount_compare", "times_compare"],
            proxy: {
                url: "/settlerecord/getProductClientDetail",
                type: "ajax",
            }
        });

        let grid = Ext.create("Ext.grid.Panel", {
            width: window.innerWidth * .6,
            height: window.innerHeight * .6,
            plugins: "gridfilters",
            tbar: [{
                xtype: "textfield",
                fieldLabel: "查找客户：",
                listeners: {
                    change: function(me, newValue, oldValue, eOpts) {

                        let grid = me.up().up();
                        me.up().up().up().up().controller.filterclient(grid, newValue);
                    }
                }
            }, "->", {
                xtype: "button",
                text: "导出表格",
                handler: function() {
                    let me = this;
                    let detailType = "exportProductClientDetailExcel";
                    me.up().up().up().up().controller.exportExcel(product, start, end, detailType);
                }
            }],
            store: store,
            scrollable: true,
            columns: [
                { header: "客户号", dataIndex: "clientId" },
                {
                    header: "客户名",
                    dataIndex: "clientName",
                    width: 300,
                    filter: {
                        type: "string"
                    }
                },
                { header: "笔数", dataIndex: "times", renderer: function(value) { return Ext.util.Format.number(value, "0,000") } },
                { header: "金额", dataIndex: "amount", renderer: function(value) { return Ext.util.Format.number(value, "0,000.00") } },
                {
                    header: "笔数<br>同比",
                    dataIndex: "times_compare",
                    renderer: function(value) {
                        if (value < 0) {
                            return "<font color='red'>" + Ext.util.Format.number(value, "0,000") + "</font>";
                        } else {
                            return "<font color='green'>" + Ext.util.Format.number(value, "0,000") + "</font>";
                        }

                    }
                },
                {
                    header: "金额<br>同比",
                    dataIndex: "amount_compare",
                    renderer: function(value) {


                        if (value < 0) {
                            return "<font color='red'>" + Ext.util.Format.number(value, "0,000.00") + "</font>";
                        } else {
                            return "<font color='green'>" + Ext.util.Format.number(value, "0,000.00") + "</font>";
                        }



                    }
                },
            ],

        });



        store.load({
            params: { product, start, end },
        });
        win.add(grid);
        view.add(win);
        win.show();
    },

    /**
     * 生成单项业务产品的流水
     */
    getProductDetail: function(product, start, end) {
        let me = this;
        let view = me.getView();
        let win = Ext.create("Ext.window.Window", {
            width: window.innerWidth * .8,
            height: window.innerHeight * .66,
            title: product + "业务流水表,单位:万美元,统计时间(" + start + "-" + end + ")",
            scrollable: true,
            layout: {
                type: "table",
                columns: 2
            },

        });
        let store = Ext.create("Ext.data.Store", {

            fields: [
                "belong_branch_number",
                "belong_branch_name",
                "cust_number",
                "cust_name",
                "product_name",
                "busy_date",
                "busy_currency",
                "busy_amount",
                "usd_rate",
            ],
            proxy: {
                url: "/settlerecord/getProductDetail",
                type: "ajax",
            }
        });

        let grid = Ext.create("Ext.grid.Panel", {
            width: window.innerWidth * .77,
            height: window.innerHeight * .6,
            plugins: "gridfilters",
            tbar: [{
                xtype: "textfield",
                fieldLabel: "业务日期：",
                listeners: {
                    change: function(me, newValue, oldValue, eOpts) {
                        let grid = me.up().up();
                        console.log(grid);
                        me.up().up().up().up().controller.filterdate(grid, newValue);
                    }
                }
            }, "->", {
                xtype: "button",
                text: "导出表格",
                handler: function() {
                    let me = this;
                    let detailType = "exportProductDetailExcel";
                    me.up().up().up().up().controller.exportExcel(product, start, end, detailType);
                }
            }],
            store: store,
            scrollable: true,

            columns: [
                { header: "行号", dataIndex: "belong_branch_number" },
                { header: "行名", dataIndex: "belong_branch_name" },
                { header: "客户号", dataIndex: "cust_number" },
                {
                    header: "客户名",
                    dataIndex: "cust_name",
                    width: 300,
                    filter: {
                        type: "string"
                    }
                },
                { header: "业务种类", dataIndex: "product_name" },
                {
                    header: "业务日期",
                    dataIndex: "busy_date",
                    filter: {
                        type: "string"
                    }
                },
                { header: "币种", dataIndex: "busy_currency" },
                { header: "金额", dataIndex: "busy_amount", renderer: function(value) { return Ext.util.Format.number(value, "0,000.00") } },
                { header: "美元折算率", dataIndex: "usd_rate", renderer: function(value) { return Ext.util.Format.number(value, "0,000.00") } },
            ],

        });



        store.load({
            params: { product, start, end },
        });
        win.add(grid);
        view.add(win);
        win.show();

    },

    getLastUpdateDate: function() {
        return new Promise(function(resolve, reject) {
            Ext.Ajax.request({
                url: "/update/getLastUpdateDate",
                params: {
                    type: "settle"
                },
                success: function(result) {
                    resolve(result.responseText);
                }
            });
        });
    },

    getTotalTask: function(data) {
        return new Promise(function(resolve, reject) {
            Ext.Ajax.request({
                url: "/settlerecord/getTotalTask",
                params: {
                    start: data.start,
                    end: data.end
                },
                success: function(result) {
                    resolve(result.responseText);
                }
            });
        });
    },
    getTotalSettlePerformance: function(data) {
        return new Promise(function(resolve, reject) {
            Ext.Ajax.request({
                url: "/settlerecord/getTotalSettlePerformance",
                params: {
                    start: data.start,
                    end: data.end
                },
                listeners: {
                    beforerequest: function() {
                        msgTip = Ext.MessageBox.show({
                            title: '提示',
                            msg: '信息刷新中,请稍候......'
                        });
                    },
                    requestcomplete: function() {
                        msgTip.hide(); // 加载完成，关闭提示框
                    },
                },
                success: function(result) {

                    resolve(result.responseText);
                }
            });
        });
    },
    unitAnalyis: function() {
        // alert("unitAnalyis");
        let settle = this.getView().up();
        let settleController = settle.controller;
        let level = settle.query("textfield")[1];
        level.setValue("经营单位");
        settleController.query();
    },
    clientAnalyis: function() {
        // alert("unitAnalyis");
        let settle = this.getView().up();
        let settleController = settle.controller;
        let level = settle.query("textfield")[1];
        level.setValue("客户");
        settleController.query();
    },
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
            layoutContext.renderer(label) / 1000,
            "0,000"
        );
    },
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

    // 导出表格
    exportExcel: function(product, start, end, detailType) {

        let url =
            "/settlerecord/" + detailType + "?start=" +
            start +
            "&end=" +
            end +
            "&product=" +
            product;

        window.open(url);
    },


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
            layoutContext.renderer(label) / 1000,
            "0,000"
        );
    },


});