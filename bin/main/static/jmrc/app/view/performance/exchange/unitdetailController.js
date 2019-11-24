Ext
    .define(
        'jmrc.view.performance.exchange.unitdetailController', {
            extend: 'Ext.app.ViewController',
            alias: 'controller.performance-exchange-unitdetail',

            afterRender: function() {
                // 页面渲染后取得配置的数据
                let me = this;
                let view = me.getView();
                let data = view.config.data;
                let width = window.innerWidth;
                let height = window.innerHeight;

                let grid = Ext
                    .create({
                        xtype: "grid",
                        width: width * .7,
                        height: height * .7,
                        border: 2,
                        store: {
                            fields: ["branchId", "branchName",
                                "amount", "times",
                                "times_compare",
                                "amount_compare",
                            ],
                            proxy: {
                                url: "/exchange/getUnitDetail",
                                type: "ajax"
                            }
                        },
                        columns: [{
                                header: "<center>行号</center>",
                                dataIndex: "branchId"
                            },
                            {
                                header: "<center>行名</center>",
                                dataIndex: "branchName",
                                renderer: function(v) {
                                    return v.replace("本部", "")
                                        .replace("（一级支行）",
                                            "");
                                }

                            },
                            {
                                header: "<center>笔数</center>",
                                dataIndex: "times",
                                renderer: function(v) {
                                    return Ext.util.Format
                                        .number(v, '0,000');
                                }

                            },
                            {
                                header: "<center>金额<br>（万美元）</center>",
                                dataIndex: "amount",
                                renderer: function(v) {
                                    return Ext.util.Format
                                        .number(v,
                                            '0,000.00');
                                }
                            },
                            {
                                header: "<center>笔数<br>（同比）</center>",
                                dataIndex: "times_compare",
                                renderer: function(v) {
                                    if (v > 0) {
                                        return "<font color=green>" +
                                            Ext.util.Format
                                            .number(
                                                v,
                                                '0,000') +
                                            "</font>";
                                    } else {
                                        return "<font color=red>" +
                                            Ext.util.Format
                                            .number(
                                                v,
                                                '0,000') +
                                            "</font>";

                                    }
                                }

                            },
                            {
                                header: "<center>金额<br>（万美元）</center>",
                                dataIndex: "amount_compare",
                                renderer: function(v) {
                                    if (v > 0) {
                                        return "<font color=green>" +
                                            Ext.util.Format
                                            .number(
                                                v,
                                                '0,000.00') +
                                            "</font>";
                                    } else {
                                        return "<font color=red>" +
                                            Ext.util.Format
                                            .number(
                                                v,
                                                '0,000.00') +
                                            "</font>";

                                    }
                                }

                            },
                            {
                                width: 40,
                                xtype: "actioncolumn",
                                align: "center",
                                items: [{
                                    iconCls: "x-fa fa-bar-chart",
                                    text: " 1",
                                    tooltip: "分月明细",
                                    handler: function(view,
                                        rowIndex, colIndex,
                                        item, e, record) {
                                        let unit = {
                                            name: record.data.branchName,
                                            id: record.data.branchId
                                        };
                                        let start = view.up()
                                            .up()["config"]["data"]["start"];
                                        let end = view.up()
                                            .up()["config"]["data"]["end"];

                                        view.up().up().controller
                                            .showUnitMonthBarChart(
                                                unit,
                                                start,
                                                end);
                                    }

                                    // handler:function(){alert("分月明细")}
                                }]
                            },
                            {
                                width: 40,
                                xtype: "actioncolumn",
                                align: "center",
                                items: [{
                                    iconCls: "x-fa fa-pie-chart",
                                    tooltip: "产品明细",
                                    text: " 1",
                                    handler: function(view,
                                        rowIndex, colIndex,
                                        item, e, record) {
                                        let unit = {
                                            name: record.data.branchName,
                                            id: record.data.branchId
                                        };
                                        let start = view.up()
                                            .up()["config"]["data"]["start"];
                                        let end = view.up()
                                            .up()["config"]["data"]["end"];

                                        view.up().up().controller
                                            .showProductPieChart(
                                                unit,
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
                                    text: " 1",
                                    handler: function(view,
                                        rowIndex, colIndex,
                                        item, e, record) {
                                        let unit = {
                                            name: record.data.branchName,
                                            id: record.data.branchId
                                        };
                                        let start = view.up()
                                            .up()["config"]["data"]["start"];
                                        let end = view.up()
                                            .up()["config"]["data"]["end"];

                                        view.up().up().controller
                                            .showUnitClientList(
                                                unit,
                                                start,
                                                end);
                                    }
                                }]
                            }

                        ],
                    });

                grid.getStore().load({
                    params: {
                        start: data.start,
                        end: data.end
                    }
                });

                view.add(grid);
            },

            showUnitMonthBarChart: function(unit, start, end) {
                let me = this;
                let view = me.getView();
                // console.log(unit);

                let win = Ext.create("Ext.window.Window", {
                    width: window.innerWidth * 0.8,
                    height: window.innerHeight * 0.5,
                    title: unit.name + "结售汇量分月明细"
                });
                // 测试的分月明细
                let chart = Ext.create({
                    xtype: "mybarchart",
                    // layout:"fit",
                    scrollable: true,
                    data: {
                        store: Ext.create("Ext.data.Store", {
                            fields: [{
                                type: "string",
                                name: "月份",
                                mapping: "month"
                            }, {
                                type: "number",
                                name: "笔数",
                                mapping: "times"
                            }, {
                                type: "number",
                                name: "金额",
                                mapping: "amount"
                            }, ],
                            proxy: {
                                url: "/exchange/getUnitMonth",
                                type: "ajax"
                            }
                        }), //
                        // 图表布局
                        layout: "column",
                        // 图表的宽度
                        width: window.innerWidth * 0.8,
                        // 图表的高度
                        height: window.innerHeight * 0.5,

                        // 柱状图的宽度比例，全部为1
                        chartWidth: 0.7,
                        // 数据表格的宽度比例，全部为1
                        gridWidth: 0.3,
                        // 图表的标题
                        title: {
                            name: unit.name + "业务量分月统计表（单位：万美元）",
                            widht: 300,
                            height: 100,
                            x: 200,
                            y: 30
                        },
                        insetPadding: {
                            top: 50,
                            bottom: 40,
                            left: 20,
                            right: 40
                        },
                        xAxes: {
                            // numeric category
                            type: "category",
                            position: "bottom",
                            title: {
                                name: "月份",
                                fontSize: 15
                            },
                            field: "月份"
                        },
                        yAxes: {
                            // numeric category
                            type: "numeric",
                            position: "left",
                            title: {
                                name: "业务量(万美元)",
                                fontSize: 15
                            },
                            field: "金额"
                        },
                        subStyle: {
                            // 柱形填充着色
                            fill: "#0fc835",
                            storke: "blue"
                        },
                        highlight: {
                            strokeStyle: "light",
                            fillStyle: "gold",
                        },
                        // 显示数据表格
                        grid: {
                            show: true
                        },

                        params: {
                            start: start,
                            end: end,
                            unit: unit.id
                        }
                    }
                });
                win.add(chart);
                view.add(win);
                win.show();

            },
            showProductPieChart: function(unit, start, end) {

                // alert("showProductPieChart");
                let me = this;
                let view = me.getView();
                // console.log(unit);

                let win = Ext.create("Ext.window.Window", {
                    width: window.innerWidth * 0.8,
                    height: window.innerHeight * 0.5,
                    title: unit.name + "结售汇量产品占比明细"
                });
                // 测试的分月明细
                let chart = Ext.create({
                    xtype: "myPieChart",
                    // layout:"fit",
                    scrollable: true,
                    data: {
                        store: Ext.create("Ext.data.Store", {
                            fields: [{
                                type: "string",
                                name: "产品",
                                mapping: "product"
                            }, {
                                type: "number",
                                name: "笔数",
                                mapping: "times"
                            }, {
                                type: "number",
                                name: "金额",
                                mapping: "amount"
                            }, {
                                type: "number",
                                name: "占比",
                                mapping: "percent"
                            }, ],
                            proxy: {
                                url: "/exchange/getUnitProduct",
                                type: "ajax"
                            }
                        }), //
                        // 图表布局
                        layout: "column",
                        // 图表的宽度
                        width: window.innerWidth * 0.8,
                        // 图表的高度
                        height: window.innerHeight * 0.5,

                        // 柱状图的宽度比例，全部为1
                        chartWidth: 0.5,
                        // 数据表格的宽度比例，全部为1
                        gridWidth: 0.5,
                        // 图表的标题
                        title: {
                            name: unit.name + "结售汇量产品占比明细图（单位：百份比）",
                            creditsName: "统计口径:即期结售汇,合作远期结售汇",
                            creditsAlign: "left"

                        },
                        insetPadding: {
                            top: 50,
                            bottom: 40,
                            left: 20,
                            right: 40
                        },
                        legend: {
                            docked: "bottom",
                            margin: 3
                        },

                        angleField: "占比",
                        labelField: "产品",

                        // 显示数据表格
                        grid: {
                            show: true
                        },

                        params: {
                            start: start,
                            end: end,
                            unit: unit.id
                        }
                    }
                });
                win.add(chart);
                view.add(win);
                win.show();
            },

            showUnitClientList: function(unit, start, end) {
                // alert("showUnitClientList");
                let me = this;
                let view = me.getView();
                // console.log(unit);
                let width= window.innerWidth * 0.8;
                let height= window.innerHeight * 0.5;

                let win = Ext.create("Ext.window.Window", {
                  width:width,
                  height:height,
                    title: unit.name + "结售汇量产品占比明细"
                });

                let grid = Ext.create({
                    xtype: "grid",
                    width: width,
                    height: height,
                    store: Ext.create("Ext.data.Store",{
                        fields: [
                            { name: "客户号", type: "string", mapping: "clientId" },
                            { name: "客户名", type: "string", mapping: "clientName" },
                            { name: "产品名称", type: "string", mapping: "product" },
                            { name: "笔数", type: "number", mapping: "times" },
                            { name: "金额", type: "number", mapping: "amount" },
                            { name: "笔数同期", type: "number", mapping: "times_pre" },
                            { name: "金额同期", type: "number", mapping: "amount_pre" },
                            { name: "笔数同比", type: "number", mapping: "time_compare" },
                            { name: "金额同比", type: "number", mapping: "amount_compare" },
                        ],
                        proxy: {
                            url: "/exchange/getUnitClientList",
                            type: "ajax"
                        }
                    }),
                    columns: [
                        { text: "客户号", dataIndex: "客户号", renderer: function(v) { return "<center>" + v + "</center>" } },
                        { text: "客户名", dataIndex: "客户名", renderer: function(v) { return "<center>" + v + "</center>" } },
                        { text: "产品名称", dataIndex: "产品名称", renderer: function(v) { return "<center>" + v + "</center>" } },
                        { text: "笔数", dataIndex: "笔数", renderer: function(v) { return Ext.util.Format.number(v, "0,000") } },
                        { text: "金额", dataIndex: "金额", renderer: function(v) { return Ext.util.Format.number(v, "0,000.0000") } },
                        {
                            text: "笔数<br>同比",
                            dataIndex: "笔数同比",
                            renderer: function(v) {
                                if (v > 0) {
                                    return "<font color=green>" + Ext.util.Format.number(v, "0,000") + "</font>";
                                } else {
                                    return "<font color=red>" + Ext.util.Format.number(v, "0,000") + "</font>";
                                }
                            }
                        },
                        {
                            text: "金额<br>同比",
                            dataIndex: "金额同比",
                            renderer: function(v) {
                                if (v > 0) {
                                    return "<font color=green>" + Ext.util.Format.number(v, "0,000.00") + "</font>";
                                } else {
                                    return "<font color=red>" + Ext.util.Format.number(v, "0,000.00") + "</font>";
                                }

                            }
                        },
                    ],
                });

                win.add(grid);
                view.add(win);
                win.show();
                grid.getStore().load({
                    params: {
                        start: start,
                        end: end,
                        unit: unit.id
                    }
                });

            }

        });