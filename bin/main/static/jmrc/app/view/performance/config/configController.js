Ext.define("jmrc.view.performance.config.configController", {
  extend: "Ext.app.ViewController",
  alias: "controller.performance-config-config",

  AllProducts: [],
  RangeProducts: [],

  // 保存选定的国际结算量产品统计口径
  saveRange: function() {
    let me = this;
    let view = me.getView();
    let checkgroup = view.query("checkboxgroup")[0];
    let fs = checkgroup.items.items;
    let productRange = [];
  },

  afterRender: function() {
    let me = this;
    let view = me.getView();
    let p1 = new Promise(function(resolve, reject) {
      me.getAllProducts(resolve);
    });
    let p2 = new Promise(function(resolve, reject) {
      me.getRangeProducts(resolve);
    });

    Promise.all([p1, p2]).then(function(resolve, reject) {
      console.log(me.AllProducts);
      console.log(me.RangeProducts);
      let fc = Ext.create("Ext.form.FieldContainer", {
        xtype: "fieldcontainer",
        width: "100%",
        fieldLabel: "结算量统计口径",
        labelWidth: 300,
        defaultType: "checkboxfield",
        layout: {
          type: "table",
          // The total column count must be specified here
          columns: 4,
          margin:30
        }
      });
      view.add(fc);
     // view.add({xtype:"button",text:"保存",handler:"saveRangeProducts"});
      for (let n = 0, len = me.AllProducts.length; n < len; n++) {
        let item = {
          boxLabel: me.AllProducts[n].data,
          name: "topping",
          inputValue: me.AllProducts[n].data,
          margin:20,
        };
        //历遍存量的统计口径
        for (let i = 0, len1 = me.RangeProducts.length; i < len1; i++) {
          if (me.AllProducts[n].data == me.RangeProducts[i].data.name) {
            item.checked = true;
          }
        }
        fc.add(item);
      }
    });
  },
  getAllProducts: function(resolve) {
    let me = this;
    let allProductStore = me.getViewModel().getStore("allProductStore");
    me.AllProducts=[];
    allProductStore.load(function(records, operation, success) {
      if (success) {
        let m = this;
        for (let n = 0, len = records.length; n < len; n++) {
          me.AllProducts.push(records[n]);
        }
        resolve();
      }
    });
  },

  getRangeProducts: function(resolve) {
    let me = this;
    let rangeProductStore = me.getViewModel().getStore("rangeProductStore");
    me.RangeProducts=[];
    rangeProductStore.load(function(records, operation, success) {
      if (success) {
        for (let n = 0, len = records.length; n < len; n++) {
          me.RangeProducts.push(records[n]);
        }
        console.log("range function is done");
        resolve();
      }
    });
  },
  refreshFields: function() {},

  saveRangeProducts: function() {
    let me = this;
    let view = me.getView();
    let fieldContainer = view.query("fieldcontainer")[0].items.items;
    let item = [];
    for (let r in fieldContainer) {
      item.push({
        name: fieldContainer[r].boxLabel,
        range: fieldContainer[r].checked
      });
    }
    console.log(item);
    Ext.Ajax.request({
      url:"/settlerecord/saveRangeProducts",
      params:{obj:JSON.stringify(item)},
      success:function(result){
        if(result.responseText!=null){
        	Ext.Msg.alert('Status', '更新数据成功');
        }
      }
    });
  }
});
