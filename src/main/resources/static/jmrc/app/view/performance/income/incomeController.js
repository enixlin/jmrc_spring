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
                { name: "科目号", mapping: "subjectId" },
                { name: "科目名称", mapping: "subjectName" },
                { name: "期末贷方", mapping: "credit_end" },
                { name: "期末借方", mapping: "debit_end" },
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
                { text: "科目名称", dataIndex: "科目名称" },
                { text: "期末贷方", dataIndex: "期末贷方" },
                { text: "期末借方", dataIndex: "期末借方" },
                { text: "贷方增减", dataIndex: "贷方增减" },
                { text: "借方增减", dataIndex: "借方增减" },
                { text: "贷方同比", dataIndex: "贷方同比" },
                { text: "借方同比", dataIndex: "借方同比" },
            ],
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

        // let store = grid.getStore();
        // store.load({
        //     params: {
        //         date: data
        //     }
        // });
        // alert(date);


    }
});