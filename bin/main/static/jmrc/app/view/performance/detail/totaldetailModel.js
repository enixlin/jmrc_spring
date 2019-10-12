Ext.define('jmrc.view.performance.detail.totaldetailModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.performance-detail-totaldetail',
    data: {
        name: 'jmrc'
    },

    stores: {
        getLastUpdateDate: {
            fields: ["date"],
            proxy: {
                url: "/settlerecord/getLastUpdateDate",
                type: "ajax",
            }
        },

        getSettleTypeProformanceByDate: {
            fields: ["name", "performance", "times"],
            proxy: {
                url: "/settlerecord/getSettleTypeProformanceByDate",
                type: "ajax"
            }
        }
    },



});