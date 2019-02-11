Ext.define("jmrc.view.tab.tablayout", {
  extend: "Ext.tab.Panel",
  xtype: "tablayout",

  requires: [
    "jmrc.view.tab.tablayoutController",
    "jmrc.view.tab.tablayoutModel"
  ],

  //   ui: "navigation",

  controller: "tab-tablayout",
  viewModel: {
    type: "tab-tablayout"
  },

  items: [{ title: "tab1" }, { title: "tab2" }]
});
