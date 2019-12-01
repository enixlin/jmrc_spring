Ext.define("jmrc.view.performance.deposit.depositController", {
    extend: "Ext.app.ViewController",
    alias: "controller.performance-deposit-deposit",

    afterRender: function() {
        // 页面渲染后，格式化日期数据，填充当前日期
        let me = this;
        let view = me.getView();

        let tbar = Ext.create({
            xtype: "toolbar",
            width: 500,
            items: [
                // begin using the right-justified button container
                "->", // same as { xtype: 'tbfill' }
                {
                    xtype: "textfield",
                    name: "field1",
                    emptyText: "enter search term"
                },
                {
                    // xtype: 'button', // default for Toolbars
                    text: "Button",
                    handler: "query"
                }
            ]
        });
        let grid = Ext.create({
            xtype: "grid",
            width: "100%",
            height: 500,
            store: Ext.create("Ext.data.Store", {
                fields: [
                    "subject",
                    "currency",
                    "credit_now",
                    "avg_credit_now",
                    "credit_lastyear",
                    "avg_credit_lastyear"
                ],
                proxy: {
                    url: "/subject/getDepositSubjects",
                    type: "ajax"
                }
            }),
            columns: [
                { header: "科目号", dataIndex: "subject" },
                { header: "币种", dataIndex: "currency" },
                {
                    header: "时点余额",
                    dataIndex: "credit_now",
                    renderer: function(v) {
                        return Ext.util.Format.number(v / 10000, '0,000.00');
                    }
                },
                {
                    header: "日均余额",
                    dataIndex: "avg_credit_now",
                    renderer: function(v) {
                        return Ext.util.Format.number(v / 10000, '0,000.00');
                    }
                },
                {
                    header: "时点余额<br>同比",
                    dataIndex: "credit_lastyear",
                    renderer: function(v) {
                        return Ext.util.Format.number(v / 10000, '0,000.00');
                    }
                },
                {
                    header: "日均余额<br>同比",
                    dataIndex: "avg_credit_lastyear",
                    renderer: function(v) {
                        return Ext.util.Format.number(v / 10000, '0,000.00');
                    }
                }
            ]
        });
        view.add(tbar);
        view.add(grid);
    },

    query: function() {
        console.log("click log");

        //清除exchange页面上的已有view,但保留工具条
        let me = this;
        let view = me.getView();
        //  view.removeAll();
        //从工具条上获取查询参数
        let textfield = view.query("textfield");
        console.log(textfield);

        let date = textfield[0].getValue().replace(/-/g, "");
        console.log(date);
        let grid = view.query("grid")[0];

        grid.getStore().load({
            params: {
                date: date,
                agent: "true"
            }
        });
    }
});