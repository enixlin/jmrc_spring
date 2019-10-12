Ext.define('jmrc.view.performance.detail.totaldetailController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.performance-detail-totaldetail',

    afterRender: function() {
        let me = this;
        let view = me.getView();
        let data = view.config.data;
        let start = view.up().query("textfield")[0];
        let end = view.up().query("textfield")[1];
        data.start = start;
        data.end = end;

        let updatedata = me.getLastUpdateDate();
        let totalPerformace = me.getTotalSettlePerformance(data);
        Promise.all(updatedata, totalPerformace).then(function(result) {
            console.log(result);
            me.makeTotalReport(me, view, data)
        });


    },


    /**
     * 生成全行业务总览
     */
    makeTotalReport: function(scope, view, data) {

        let summypanel = Ext.create("Ext.panel.Panel", {
            width: "100%",
            // renderTo: Ext.getBody(),
            layout: {
                type: "table",
                columns: 2,
            },
            items: [
                { xtype: "panel", width: 300, height: 300, html: "数据日期：" + data.updatedate }, { xtype: "panel", width: 300, height: 300, html: "分月明细：" }
            ],

        });
        view.add(summypanel);

    },


    getLastUpdateDate: function() {
        return new Promise(function(resolve, reject) {
            Ext.Ajax.request({
                url: "/settlerecord/getLastUpdateDate",
                success: function(result) {
                    resolve(result.responseText);
                }
            });
        });
    },
    getTotalSettlePerformance: function(data) {

        return new Promise(function(resolve, reject) {
            Ext.Ajax.request({
                url: "/settlerecord/getTotalSettlePerformance",
                param: data,
                success: function(result) {
                    resolve(result.responseText);
                }
            });
        });
    }


});