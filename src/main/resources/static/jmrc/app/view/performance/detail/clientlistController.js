Ext.define('jmrc.view.performance.detail.clientlistController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.performance-detail-clientlist',
    /* 

    */
    afterRender: function() {
        let me = this;
        let view = me.getView();
        let viewModel=me.getViewModel();
        let data=view.config.data;
        let store=viewModel.getStore(data.st);
   
    
        
        let grid=Ext.create({
        	xtype:"grid",
        	layout:"fit",
            html:"gird panel"
        });
       
        store.load({
            params: {
              start: data.startDay,
              end: data.endDay,
              unitType: "unit",
              name: data.unit.name,
              uid: data.unit.id
            }
          });
        
        let fields=store.getFields();
        let cos=[];
//         for (let n = 0, len = fields.length; n < len; n++) {
//         	 cos.push({
//                  text: fields[n].name,
//                 // width: (0.8 / (len - 1)) * 100 + "%",
//                  dataIndex: fields[n].name,
// //                 renderer: function(value) {
// //                   return Ext.util.Format.number(value, "0,000.00");
// //                 }
//                });
//         }
        grid.setColumns(cos);
        grid.bindStore(store);
        view.add(grid);
       
        
    

        console.log(fields);
        console.log(cos);
//        console.log(data);
    }

});
