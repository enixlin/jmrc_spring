Ext.define("jmrc.view.performance.deposit.depositController", {
    extend: "Ext.app.ViewController",
    alias: "controller.performance-deposit-deposit",

    afterRender: function() {
        // 页面渲染后，格式化日期数据，填充当前日期
        let me = this;
        let view = me.getView();
        let date = new Date();
        let year = date.getUTCFullYear();
        let month = date.getUTCMonth() + 1;
        let day = date.getUTCDate();
        let logDate=me.getSubjectLogDate("subject");
        Promise.resolve(logDate).then(function(result) {
            // alert(result);
            tbar.query("textfield")[1].setValue(result.replace(/-/g, ""));
        });
        // 10以下的数在前面补充零
        month < 10 ? (month = "0" + month) : month;
        day < 10 ? (day = "0" + day) : day;


        let store = Ext.create("Ext.data.Store", {
            fields: [
                "range",
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
        });
        let bbar = Ext.create({
            xtype: "toolbar",
            width: "100%",
            docked: 'bottom',
            style: { "background": "#abcdef" },
            items: [
                // begin using the right-justified button container

                // same as { xtype: 'tbfill' }
                {
                    xtype: "textfield",
                    fieldLabel: "时点余额合计"

                },
                {
                    xtype: "textfield",
                    fieldLabel: "日均余额合计"

                },
                {
                    xtype: "textfield",
                    fieldLabel: "上年时点合计"

                },
                {
                    xtype: "textfield",

                    fieldLabel: "上年日均合计"

                },

            ]
        });

        let tbar = Ext.create({
            xtype: "toolbar",
            width: "100%",
            items: [
                // begin using the right-justified button container

                // same as { xtype: 'tbfill' }
                {
                    xtype: "textfield",
                    fieldLabel: "币种",
                    disabled: true,
                    listeners: {
                        change: function(me, newValue, oldValue, eOpts) {
                            let deposit = me.up().up();
                            let grid = deposit.query('grid')[0];
                            console.log(grid);
                            deposit.controller.filterRange(grid, newValue);
                        }
                    }
                },
                {
                    xtype: "textfield",
                    fieldLabel: "日期",
                    emptyText: "请输入日期（20190625）",
                    value:""

                },
                {
                    // xtype: 'button', // default for Toolbars
                    text: "查询",

                    handler: "query"
                }
            ]
        });
        let grid = Ext.create({
            xtype: "grid",
            width: "100%",
            height: 500,
            border: 2,
            plugins: "gridfilters",
            store: store,
            columns: [

                {
                    header: "范围",
                    dataIndex: "range",
                    renderer: function(v) {
                        if (v == "self") { return "自营" };
                        if (v == "agent") { return "代理" };
                    },
                    filter: {
                        type: "string"
                    }
                },
                {
                    header: "科目号",
                    dataIndex: "subject",
                    renderer: function(v) {
                        if (v == "2001") { return "单位活期" };
                        if (v == "2003") { return "个人活期" };
                        if (v == "2004") { return "个人定期" };
                        if (v == "2014") { return "保证金" };
                    }
                },
                {
                    header: "币种",
                    dataIndex: "currency",
                    filter: {
                        type: "string"
                    },
                },
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
                    header: "时点余额<br>年初",
                    dataIndex: "credit_lastyear",
                    renderer: function(v) {
                        return Ext.util.Format.number(v / 10000, '0,000.00');
                    }
                },
                {
                    header: "日均余额<br>年初",
                    dataIndex: "avg_credit_lastyear",
                    renderer: function(v) {
                        return Ext.util.Format.number(v / 10000, '0,000.00');
                    }
                }
            ]
        });
        view.add(tbar);
        view.add(grid);
        view.add(bbar);
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

        let currency = textfield[0];

        let date = textfield[1].getValue().replace(/-/g, "");
        console.log(date);
        let grid = view.query("grid")[0];

        grid.getStore().load({
            params: {
                date: date,
                agent: "false"
            },
            callback: function(records, operation, success) {
                // the operation object
                // contains all of the details of the load operation
                currency.setDisabled(false);

                let store = grid.getStore();
                let fields = grid.up().query("toolbar")[1].query('textfield');
                fields[0].setValue(Ext.util.Format.number(store.sum("credit_now") / 10000, '0,000.00'));
                fields[1].setValue(Ext.util.Format.number(store.sum("avg_credit_now") / 10000, '0,000.00'));
                fields[2].setValue(Ext.util.Format.number(store.sum("credit_lastyear") / 10000, '0,000.00'));
                fields[3].setValue(Ext.util.Format.number(store.sum("avg_credit_lastyear") / 10000, '0,000.00'));
            }

        });
    },



    filterRange: function(view, newValue) {


        let store = view.getStore();
        if (newValue == "") {
            store.clearFilter();

            console.log(store.getFilters());
            return;
        }
        store.setFilters({
            "operator": "like",
            "value": newValue,
            "property": "currency"
        });
        let credit = store.sum('credit_now');
        let fields = view.up().query("toolbar")[1].query('textfield');
        fields[0].setValue(Ext.util.Format.number(store.sum("credit_now") / 10000, '0,000.00'));
        fields[1].setValue(Ext.util.Format.number(store.sum("avg_credit_now") / 10000, '0,000.00'));
        fields[2].setValue(Ext.util.Format.number(store.sum("credit_lastyear") / 10000, '0,000.00'));
        fields[3].setValue(Ext.util.Format.number(store.sum("avg_credit_lastyear") / 10000, '0,000.00'));
        console.log(store.getFilters());
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
});