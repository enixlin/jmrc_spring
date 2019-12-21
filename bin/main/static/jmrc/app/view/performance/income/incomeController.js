Ext.define("jmrc.view.performance.income.incomeController", {
    extend: "Ext.app.ViewController",
    alias: "controller.performance-income-income",
    afterRender: function() {
        let me = this;
        let date = "";
        let view = me.getView();
        let tbar = Ext.create({
            xtype: "toolbar",
            items: [
                { xtype: "textfield", fieldLabel: "日期", value: date },
                { xtype: "button", text: "查询", handler: "query" }
            ]
        });

        let store = Ext.create("Ext.data.Store", {
            fields: [
                { name: "科目号", mapping: "subject" },
                { name: "科目名称", mapping: "subjectName" },
                { name: "期末贷方", mapping: "credit_end_c" },
                { name: "期末借方", mapping: "debit_end_c" },
                { name: "贷方增减", mapping: "credit_compare" },
                { name: "借方增减", mapping: "debit_compare" },
                { name: "贷方同比", mapping: "credit_compare_precent" },
                { name: "借方同比", mapping: "debit_compare_precent" }
            ],
            proxy: {
                url: "/subject/getIncomeSubject",
                type: "ajax"
            }
        });

        let grid = Ext.create({
            xtype: "grid",
            width: "100%",
            store: store,
            columns: [
                { text: "科目号", dataIndex: "科目号" },
                {
                    text: "科目名称",
                    dataIndex: "科目名称",
                    width: 200
                },
                {
                    text: "期末贷方",
                    dataIndex: "期末贷方",
                    renderer: function(v) {
                        return Ext.util.Format.number(v, "0,000.00");
                    }
                },
                {
                    text: "期末借方",
                    dataIndex: "期末借方",
                    renderer: function(v) {
                        return Ext.util.Format.number(v, "0,000.00");
                    }
                },
                {
                    text: "贷方增减",
                    dataIndex: "贷方增减",
                    renderer: function(v) {
                        return Ext.util.Format.number(v, "0,000.00");
                    }
                },
                {
                    text: "借方增减",
                    dataIndex: "借方增减",
                    renderer: function(v) {
                        return Ext.util.Format.number(v, "0,000.00");
                    }
                },
                { text: "贷方同比", dataIndex: "贷方同比" },
                { text: "借方同比", dataIndex: "借方同比" }
            ]
        });

        view.add(tbar);
        view.add(grid);

        let subject = this.getSubjectLogDate("subject");
        Promise.resolve(subject).then(function(result) {
            // alert(result);
            tbar.query("textfield")[0].setValue(result.replace(/-/g, ""));
        });
    },

    getSubjectLogDate: function(type) {
        return new Promise(function(resolve, reject) {
            Ext.Ajax.request({
                url: "/update/getLastUpdateDate",
                params: {
                    type: type
                },
                success: function(result) {
                    resolve(result.responseText);
                }
            });
        });
    },

    query: function() {
        let me = this;
        let view = me.getView();


        let date = view.query("textfield")[0].getValue();
        console.log(date);

        let grid = view.query("grid")[0];
        console.log(grid);

        let store = grid.getStore();
        store.load({
            params: {
                date: date
            },
            callback: function(records, operation, success) {
                let earn = grid.getStore().sum("credit_end_c");
                let cost = grid.getStore().sum("debit_end_c");
                let profill = earn - cost;
                let tbcount = view.query("toolbar").length;
                if (tbcount < 2) {
                    let bbar = Ext.create({
                        xtype: "toolbar",
                        dock: "bottom",
                        items: [{
                                xtype: "textfield",
                                fieldLabel: "总收入:",
                                value: Ext.util.Format.number(earn, "0,000.00")
                            },
                            {
                                xtype: "textfield",
                                fieldLabel: "总支出:",
                                value: Ext.util.Format.number(cost, "0,000.00")
                            },
                            {
                                xtype: "textfield",
                                fieldLabel: "利润:",
                                value: Ext.util.Format.number(profill, "0,000.00")
                            }
                        ]
                    });
                    view.add(bbar);
                } else {
                    view.query("toolbar")[1].query("textfield")[0].setValue(Ext.util.Format.number(earn, "0,000.00"));
                    view.query("toolbar")[1].query("textfield")[1].setValue(Ext.util.Format.number(cost, "0,000.00"));
                    view.query("toolbar")[1].query("textfield")[2].setValue(Ext.util.Format.number(profill, "0,000.00"));

                }
            }
        });

        // alert(date);
    }
});