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

        getAllProductDetail: {
            fields: ["product_name", "amount", "times","amount_pre","times_pre"],
            proxy: {
                url: "/settlerecord/getAllProductDetail",
                type: "ajax"
            }
        }
    },



});