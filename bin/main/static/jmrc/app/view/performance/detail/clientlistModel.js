Ext.define('jmrc.view.performance.detail.clientlistModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.performance-detail-clientlist',
    data: {
        name: 'jmrc'
    },

    stores: {
        getUnitClientPerformanceStore: {
            field: [
                { name: "cust_name", type: "string", mapping: function(data) { return data.cust_name; } },
                // { name: "cust_number", type: "string", dataIndex: "cust_number" },
                {
                    name: "times",
                    type: "number",
                    mapping: function(data) { return data.times; }
                },
                {
                    name: "amount",
                    type: "number",
                    mapping: function(data) { return data.amount; }
                },

            ],
            proxy: {
                url: "/settlerecord/getUnitClientPerformance",
                type: "ajax",
            }
        }
    }

});