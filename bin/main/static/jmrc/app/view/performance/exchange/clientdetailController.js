Ext.define("jmrc.view.performance.exchange.clientdetailController", {
    extend: "Ext.app.ViewController",
    alias: "controller.performance-exchange-clientdetail",
    afterRender: function() {
        // 页面渲染后取得配置的数据
        let me = this;
        let view = me.getView();
        let data = view.config.data;
        let width = window.innerWidth;
        let height = window.innerHeight;

        let grid = Ext.create({
            xtype: "grid",
            width: width * 0.7,
            height: height * 0.7,
            border: 2,
            plugins: "gridfilters",
            store: {
                fields: [
                    "branchId",
                    "branchName",
                    "clientId",
                    "clientName",
                    "amount",
                    "times",
                    "times_compare",
                    "amount_compare"
                ],
                proxy: {
                    url: "/exchange/getClientDetail",
                    type: "ajax"
                }
            },
            columns: [
                { header: "<center>行号</center>", dataIndex: "branchId" },
                {
                    header: "<center>行名</center>",
                    dataIndex: "branchName",
                    renderer: function(v) {
                        return v.replace("本部", "").replace("（一级支行）", "");
                    }
                },
                { header: "<center>客户号</center>", dataIndex: "clientId" },
                {
                    header: "<center>客户名</center>",
                    filter: {
                        type: "string"
                    },
                    dataIndex: "clientName",
                    renderer: function(v) {
                        return v.replace("本部", "").replace("（一级支行）", "");
                    }
                },
                {
                    header: "<center>笔数</center>",
                    dataIndex: "times",
                    renderer: function(v) {
                        return Ext.util.Format.number(v, "0,000");
                    }
                },
                {
                    header: "<center>金额<br>（万美元）</center>",
                    dataIndex: "amount",
                    renderer: function(v) {
                        return Ext.util.Format.number(v, "0,000.00");
                    }
                },
                {
                    header: "<center>笔数<br>（同比）</center>",
                    dataIndex: "times_compare",
                    renderer: function(v) {
                        if (v > 0) {
                            return (
                                "<font color=green>" +
                                Ext.util.Format.number(v, "0,000") +
                                "</font>"
                            );
                        } else {
                            return (
                                "<font color=red>" +
                                Ext.util.Format.number(v, "0,000") +
                                "</font>"
                            );
                        }
                    }
                },
                {
                    header: "<center>金额<br>（万美元）</center>",
                    dataIndex: "amount_compare",
                    renderer: function(v) {
                        if (v > 0) {
                            return (
                                "<font color=green>" +
                                Ext.util.Format.number(v, "0,000.00") +
                                "</font>"
                            );
                        } else {
                            return (
                                "<font color=red>" +
                                Ext.util.Format.number(v, "0,000.00") +
                                "</font>"
                            );
                        }
                    }
                }
            ]
        });

        grid.getStore().load({
            params: {
                start: data.start,
                end: data.end
            }
        });

        view.add(grid);
    },



    filterclient: function(client) {
        // alert("filter run");
        console.log(client);
        let me = this;
        let view = me.getView();
        let grid = view.query("grid")[0];
        let store = grid.getStore();
        console.log("grid is ");
        console.log(grid);
        console.log("store is ");
        console.log(store);

        if (client == "") {
            // console.log("emptyu");
            store.clearFilter();

            // console.log(store.getFilters());
            return;
        }
        store.setFilters({
            "operator": "like",
            "value": client,
            //注意这里要指定过滤的表格列名
            "property": "clientName"
        });
        console.log(store.getFilters());
    },
});