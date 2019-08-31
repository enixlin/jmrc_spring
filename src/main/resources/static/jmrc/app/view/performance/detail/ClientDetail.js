Ext.define("jmrc.view.performance.detail.ClientDetail", {
  extend: "Ext.panel.Panel",
  //extend : 'Ext.pivot.Grid ',
  xtype: "clientdetail",

  requires: [
    "jmrc.view.performance.detail.ClientDetailController",
    "jmrc.view.performance.detail.ClientDetailModel"
  ],

  controller: "performance-detail-clientdetail",
  viewModel: {
    type: "performance-detail-clientdetail"
  },
  //   plugins: {
  //     gridexporter: true
  //   },
  //mixins : [ 'jmrc.view.performance.detail.GridExportExcelMixin' ],
 
  tbar: [
    {
      xtype: "textfield",
      fieldLabel: "支行筛选",
      listeners: {
        change: function() {
          alert("支行筛选");
        }
      }
    },
    {
      xtype: "textfield",
      fieldLabel: "客户筛选"
    },
    "->",
    {
      xtype: "button",
      text: "导出表格",
      handler: "exportExcel"
    }
  ],

  margin: 5,

  scrollable: true
});
