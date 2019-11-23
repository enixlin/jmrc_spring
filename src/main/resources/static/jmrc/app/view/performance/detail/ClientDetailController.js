Ext.define("jmrc.view.performance.detail.ClientDetailController", {
    extend: "Ext.app.ViewController",
    alias: "controller.performance-detail-clientdetail",

    afterRender: function() {
        let me = this;
        let view = me.getView();
        let startDay = me.getView()["config"]["data"]["start"];
        let endDay = me.getView()["config"]["data"]["end"];
        let st = "allClientPerformanceStore";
        let bindStore = me.getViewModel().getStore(st);
        let grid = Ext.create("Ext.grid.Panel", {
            width: window.innerWidth * 0.8,
            height: window.innerHeight * 0.65,
            plugins: "gridfilters",
            // plugins: {
            // gridexporter: true
            // },

            border: 2,
            scrollable: true,
            listeners: {}
        });
        grid.bindStore(bindStore);

        view.add(grid);

        // 载入数据
        grid.getStore(st).load({
            params: {
                start: startDay,
                end: endDay,
                clientType: "c"
            },
            callback: function(records, options, success) {
                // 根据数据集的filed数组，确定表格的列
                let fields = bindStore.getModel().getFields();
                let cos = []; // 表格列表

                // 现在开始历遍store的所有字段
                for (let n = 0, len = fields.length; n < len; n++) {
                    // 跳过id 和xuhao
                    // if (fields[n].name ==
                    // "id") {
                    // continue;
                    // }
                    // if (fields[n].name ==
                    // "xuhao") {
                    // continue;
                    // }
                    // 选从字段的数据类型开始分类：字符类和数字类
                    // 一、字符类
                    if (fields[n].type == "string") {
                        if (fields[n].name == "<center>机构号</center>") {
                            cos.push({
                                header: fields[n].name,
                                width: 80,
                                sortable: true,
                                dataIndex: fields[n].dataIndex,
                                filter: {
                                    type: "string"
                                }
                            });
                            Ext.create({
                                xtype: ""
                            })
                            continue;
                        }
                        if (fields[n].name == "<center>机构名称</center>") {
                            cos.push({
                                header: fields[n].name,
                                width: 100,
                                sortable: true,
                                dataIndex: fields[n].dataIndex,
                                renderer: function(value) {
                                    return value.replace("（一级支行）", "").replace("本部", "");
                                },
                                filter: {
                                    type: "string"
                                }
                            });
                            continue;
                        }
                        if (fields[n].name == "<center>客户号</center>") {
                            cos.push({
                                header: fields[n].name,
                                width: 80,
                                sortable: true,
                                dataIndex: fields[n].dataIndex,
                                filter: {
                                    type: "string"
                                }
                            });
                            continue;
                        }
                        if (fields[n].name == "<center>客户名称</center>") {
                            cos.push({
                                header: fields[n].name,
                                width: 250,
                                sortable: true,
                                dataIndex: fields[n].dataIndex,
                                filter: {
                                    type: "string",
                                    id: "clientName"
                                }
                            });
                            continue;
                        }
                    }

                    // 二、数字类
                    if (fields[n].type == "number") {
                        if (fields[n].name.indexOf("金额") != -1) {
                            if (fields[n].name.indexOf("同比") != -1) {
                                cos.push({
                                    header: fields[n].name,
                                    sortable: true,
                                    width: 80,
                                    dataIndex: fields[n].dataIndex,
                                    renderer: function(value) {
                                        if (value < 0) {
                                            return (
                                                "<font color=blue></font><span style='color:red;'>" +
                                                Ext.util.Format.number(value, "0,000.00") +
                                                "</font>"
                                            );
                                        } else {
                                            return (
                                                "<font color=blue></font><span style='color:green;'>" +
                                                Ext.util.Format.number(value, "0,000.00") +
                                                "</font>"
                                            );
                                        }
                                    }
                                });
                            } else {
                                cos.push({
                                    header: fields[n].name,
                                    sortable: true,
                                    width: 80,
                                    dataIndex: fields[n].dataIndex,
                                    renderer: function(value) {
                                        return Ext.util.Format.number(value, "0,000.00");
                                    }
                                });
                            }
                        } else {
                            // 这个if判断的是笔数
                            if (fields[n].name.indexOf("同比") != -1) {
                                cos.push({
                                    header: fields[n].name,
                                    width: 60,
                                    sortable: true,
                                    dataIndex: fields[n].dataIndex,
                                    renderer: function(value) {
                                        if (value < 0) {
                                            return (
                                                "<font color=blue></font><span style='color:red;'>" +
                                                Ext.util.Format.number(value, "0,000") +
                                                "</font>"
                                            );
                                        } else {
                                            return (
                                                "<font color=blue></font><span style='color:green;'>" +
                                                Ext.util.Format.number(value, "0,000") +
                                                "</font>"
                                            );
                                        }
                                    }
                                });
                            } else {
                                cos.push({
                                    header: fields[n].name,
                                    sortable: true,
                                    width: 60,
                                    dataIndex: fields[n].dataIndex,
                                    renderer: function(value) {
                                        return Ext.util.Format.number(value, "0,000");
                                    }
                                });
                            }
                        }
                    }
                }
                // 为表格的columns数组添加操作列
                cos.push({
                    width: 40,
                    xtype: "actioncolumn",
                    align: "center",
                    items: [{
                        iconCls: "x-fa fa-bar-chart",
                        text: " 1",
                        tooltip: "分月明细",
                        handler: function(view, rowIndex, colIndex, item, e, record) {
                            let unit = {
                                name: record.data.custName,
                                id: record.data.custCode
                            };
                            let start = view.up().up()["config"]["data"]["start"];
                            let end = view.up().up()["config"]["data"]["end"];
                            console.log(
                                view
                                .up()
                                .up()
                                .controller.showClientMonthBarChart(unit, start, end)
                            );
                        }
                    }]
                });

                // 为表格的columns数组添加操作列
                cos.push({
                    width: 40,
                    xtype: "actioncolumn",
                    align: "center",
                    items: [{
                        iconCls: "x-fa fa-pie-chart",
                        tooltip: "产品明细",
                        text: " 1",
                        handler: function(view, rowIndex, colIndex, item, e, record) {
                            let unit = {
                                name: record.data.custName,
                                id: record.data.custCode
                            };
                            let start = view.up().up()["config"]["data"]["start"];
                            let end = view.up().up()["config"]["data"]["end"];
                            console.log(
                                view
                                .up()
                                .up()
                                .controller.showClientProductPieChart(unit, start, end)
                            );
                        }
                    }]
                });
                // 为表格的columns数组添加操作列
                cos.push({
                    width: 40,
                    xtype: "actioncolumn",
                    align: "center",
                    handler: function(view, rowIndex, colIndex, item, e, record) {
                        let client = record.data.custCode;
                        let start = view.up().up()["config"]["data"]["start"];
                        let end = view.up().up()["config"]["data"]["end"];
                        view
                            .up()
                            .up()
                            .controller.showClientdetail(client, start, end);
                    },
                    items: [{
                        iconCls: "x-fa fa-list",
                        tooltip: "业务流水"
                            // text: " 1"
                    }]
                });

                grid.setColumns(cos);
                msgTip.hide(); // 加载完成，关闭提示框
                console.log("done");
            }
        });
    },


    showClientdetail: function(client, start, end) {
        let me = this;
        let view = me.getView();
        let win = Ext.create("Ext.window.Window", {
            width: window.innerWidth * .8,
            height: window.innerHeight * .66,
            title: client + "业务流水表,单位:万美元,统计时间(" + start + "-" + end + ")",
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
                url: "/settlerecord/getClientDetail",
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
                    let detailType = "exportClientDetailExcel";
                    me.up().up().up().up().controller.exportdetailExcel(client, start, end, detailType);
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
            params: { client, start, end },
        });
        win.add(grid);
        view.add(win);
        win.show();
    },


    showClientProductPieChart: function(unit, start, end) {
        let me = this;
        let view = me.getView();

        let win = Ext.create("Ext.window.Window", {
            width: window.innerWidth * 0.8,
            height: window.innerHeight * 0.7,

        });
        // 测试的产品分类明细
        let chart = {
            xtype: "basepie",
            width: window.innerWidth * 0.8,

            scrollable: true,
            data: {
                st: "ClientProductPerformanceStore",
                // 图表布局
                layout: "hbox",
                // 图表的宽度
                width: window.innerWidth * 0.4,
                // 图表的高度
                height: window.innerHeight * 0.4,
                // 图表的标题
                title: "产品业务量表\r\n \t\t\t（单位：万美元）",
                xtitle: "产品",
                ytitle: "国际结算产品业务量",
                // 横轴绑定的字段
                xAxis: "产品",
                // 竖轴绑定的字段
                yAxis: "金额",
                // 柱状图的类型：分产品柱状图，分时柱状图
                pieChartType: "productPieChart",
                exportUrl: "/settlerecord/exportClientProductPerformance",
                // 柱状图显示的数据对象：全行、经营单位、客户、产品
                // 对象的结构如下
                unit: { name: unit.name, code: unit.id, unitType: "client" },
                start: start,
                end: end
            }
        };
        win.add(chart);
        view.add(win);
        win.show();
    },

    // 导出表格
    exportExcel: function() {
        let me = this;
        let view = me.getView();
        let startDay = me.getView()["config"]["data"]["start"];
        let endDay = me.getView()["config"]["data"]["end"];
        // let clientId=me.getView()["config"]["data"]["clientId"];
        let url =
            "/settlerecord/exportAllClientPerformance?start=" +
            startDay +
            "&end=" +
            endDay +
            "&clientType=c";

        window.open(url);
    },


    // 导出表格
    exportdetailExcel: function(client, start, end, detailType) {

        let url =
            "/settlerecord/" + detailType + "?start=" +
            start +
            "&end=" +
            end +
            "&client=" +
            client;

        window.open(url);
    },

    showClientMonthBarChart: function(unit, start, end) {
        let me = this;
        let view = me.getView();

        let win = Ext.create("Ext.window.Window", {
            width: window.innerWidth * 0.8,
            height: window.innerHeight * 0.5
        });
        // 测试的分月明细
        let chart = {
            xtype: "basebar",
            width: window.innerWidth * 0.8,
            // layout:"fit",
            scrollable: true,
            data: {
                st: "ClientSettleMonthPerformanceStore",
                // 图表布局
                layout: "hbox",
                // 图表的宽度
                width: 400,
                // 图表的高度
                height: 500,
                // 图表的标题
                title: "分月国际结算量统计表\r\n \t\t\t（单位：万美元）",
                xtitle: "月份",
                ytitle: "国际结算业务量",
                // 横轴绑定的字段
                xAxis: "<center>月份</center>",
                // 竖轴绑定的字段
                yAxis: "<center>金额</center>",
                // 柱状图的类型：分产品柱状图，分时柱状图
                barChartType: "timeRangeBarChart",
                // 柱状图显示的数据对象：全行、经营单位、客户、产品
                // 对象的结构如下
                unit: { name: unit.name, code: unit.id, unitType: "unit" },
                start: start,
                end: end
            }
        };
        win.add(chart);
        view.add(win);
        win.show();
    },

    filterclient: function(client) {

        let me = this;
        let view = me.getView();
        let grid = view.query("grid")[0];
        let store = grid.getStore();
        if (client == "") {
            console.log("emptyu");
            store.clearFilter();

            console.log(store.getFilters());
            return;
        }
        store.setFilters({
            "operator": "like",
            "value": client,
            "property": "custName"
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


});