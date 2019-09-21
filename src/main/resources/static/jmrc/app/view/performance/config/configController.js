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
    let promise = new Promise(function(resolve, reject) {
      let allProductStore = me.getViewModel().getStore("allProductStore");
      allProductStore.load(function(records, operation, success) {
        if (success) {
        	let m=this;
          for (let n = 0, len = records.length; n < len; n++) {
            me.AllProducts.push(records[n]);
          }
          console.log("all function is done");
        resolve();
        }
      });
    });
    promise.then(function(resolve, reject) {
      let rangeProductStore = me.getViewModel().getStore("rangeProductStore");
      rangeProductStore.load(function(records, operation, success) {
        if (success) {
          for (let n = 0, len = records.length; n < len; n++) {
            me.RangeProducts.push(records[n]);
          }
          console.log("range function is done");
          resolve();
        }
      });
    });
    promise.then(function(resolve, reject) {

      let container = Ext.create("Ext.form.CheckboxGroup");


      let fc=Ext.create("Ext.form.FieldContainer",{
        xtype: 'fieldcontainer',
        fieldLabel: '国际结算量统计口径',
        defaultType: 'checkboxfield',
        layout: {
          type: 'table',
          // The total column count must be specified here
          columns: 3
      },
      });
      view.add(fc);
      for (let n = 0, len = me.AllProducts.length; n < len; n++) {
       //历遍存量的统计口径
        for(let i=0,len1=me.RangeProducts.length;i<len1;i++){
        	console.log("run n is"+n);
          if(me.AllProducts[n].data==me.RangeProducts[i].data.name){
            fc.add({
              boxLabel  : me.AllProducts[n].data,
              name      : 'topping',
              inputValue: me.AllProducts[n].data,
              checked:true,
             
            });
            console.log("t");
          }else{
            fc.add({
              boxLabel  : me.AllProducts[n].data,
              name      : 'topping',
              inputValue: me.AllProducts[n].data,
            });
            console.log("f");
          }
        } 
        //console.log(me.AllProducts[n]);
        console.log("combine function is done");
    
      }


 
    });
  },

  refreshFields: function() {}
});
