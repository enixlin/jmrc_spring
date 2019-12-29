Ext.define('jmrc.view.performance.tf.tfController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.performance-tf-tf',

	afterRender : function() {

		let me = this;
		let tbar=me.getView().query("toolbar")[0];
		 let subject = this.getSubjectLogDate("tf");
	        Promise.resolve(subject).then(function(result) {
	            // alert(result);
	            tbar.query("textfield")[1].setValue(result.replace(/-/g, ""));
	        });
		let view = me.getView();
		
		let store=Ext.create("Ext.data.Store",{
			fields:[
				{name:"name",mapping:"name"},
				{name:"amount_usx_c",mapping:"amount_usx_c"},
				{name:"amount_rmx_c",mapping:"amount_rmx_c"},
				{name:"amount_rmb_c",mapping:"amount_rmb_c"},
				{name:"amount_usx_p",mapping:"amount_usx_p"},
				{name:"amount_rmx_p",mapping:"amount_rmx_p"},
				{name:"amount_rmb_p",mapping:"amount_rmb_p"},
				{name:"rmb_to_usd_c",mapping:"rmb_to_usd_c"},
				{name:"rmb_to_usd_p",mapping:"rmb_to_usd_p"},
				],
				proxy:{
					url:"/TF/getTFBalance",
					type:"ajax"
				}
		});
		
		let grid=Ext.create({
			xtype:"grid",
			store:store,
			width:"100%",
			border:2,
			height:500,
			columns:[
				{text:"业务种类",dataIndex:"name",
				
				},
				{text:"外币余额",dataIndex:"amount_usx_c",
					 renderer: function(v) {
	                        return Ext.util.Format.number(v, "0,000.00");
	                    }
				},
				{text:"人民币余额",dataIndex:"amount_rmb_c",
					 renderer: function(v) {
	                        return Ext.util.Format.number(v, "0,000.00");
	                    }
				},
				{text:"人民币余额<br>折美元",dataIndex:"rmb_to_usd_c",
					 renderer: function(v) {
	                        return Ext.util.Format.number(v, "0,000.00");
	                    }
				},
				   {
                    width: 40,
                    xtype: "actioncolumn",
                    align: "center",
                    items: [{
                        iconCls: "x-fa fa-users",
                        tooltip: "c",
                        handler: function(view, rowIndex, colIndex, item, e, record) {
                        	let container=view.up().up().up();
                        	let controller=container.controller;
                        	let date=container.query("toolbar")[0].query("textfield")[1].getValue();
                   
                            let product = record.data.name;
                       console.log("支行明细");
                       console.log(product);

                        }
                    }]

                },
                
                
                {
                    width: 40,
                    xtype: "actioncolumn",
                    align: "center",
                    items: [{
                        iconCls: "x-fa fa-bank",
                        tooltip: "支行明细",
                        handler: function(view, rowIndex, colIndex, item, e, record) {
                           	let container=view.up().up().up();
                        	let controller=container.controller;
                        	let date=container.query("toolbar")[0].query("textfield")[1].getValue();
                   
                            let product = record.data.name;
                            console.log("支行明细");
                            console.log(product);

                        }
                    }]

                },
				
				
			]
		});
		
		
		view.add(grid);
	},
	
	
	
	query:function(){
		let me=this;
		let view=me.getView();
		let grid=view.query("grid")[0];
		let store=grid.getStore();
		let date=view.query("toolbar")[0].query("textfield")[1].getValue();
		store.load({
			params:{
				date
			}
		});
		
		console.log(date);
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
