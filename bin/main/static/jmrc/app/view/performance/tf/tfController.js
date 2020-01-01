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
				{name:"total_usx_c",mapping:"total_usx_c"},
				{name:"type",mapping:"type"},
				],
				proxy:{
					url:"/TF/getTFBalance",
					type:"ajax"
				},
				
				listeners:{
					filterchange:function  (store, filters, eOpts){
						
					let usx = store.sum("amount_usx_c");
			        let rmb = store.sum("amount_rmb_c");
			        let rmb_usd = store.sum("rmb_to_usd_c");
			        let total_usx = store.sum("total_usx_c");
			        view.query("toolbar")[1].query("textfield")[0].setValue(Ext.util.Format.number(usx, "0,000.00"));
			        view.query("toolbar")[1].query("textfield")[1].setValue(Ext.util.Format.number(rmb, "0,000.00"));
			        view.query("toolbar")[1].query("textfield")[2].setValue(Ext.util.Format.number(rmb_usd, "0,000.00"));
			        view.query("toolbar")[1].query("textfield")[3].setValue(Ext.util.Format.number(total_usx, "0,000.00"));
					}      
				}
		});
		
		let grid=Ext.create({
			xtype:"grid",
			store:store,
			width:"100%",
			  plugins: "gridfilters",
			border:2,
			height:500,
			columns:[
				{text:"业务种类",dataIndex:"name",
                    filter: {
                        type: "string"
                    }
				
				},
				{text:"表内外标记",dataIndex:"type",
                    filter: {
                        type: "string"
                    }
				
				},
				
				
				{text:"外币余额",dataIndex:"amount_usx_c",width:200,
					 renderer: function(v) {
	                        return Ext.util.Format.number(v, "0,000.00");
	                    }
				},
				{text:"人民币余额",dataIndex:"amount_rmb_c",width:200,
					 renderer: function(v) {
	                        return Ext.util.Format.number(v, "0,000.00");
	                    }
				},
				{text:"人民币余额<br>折美元",dataIndex:"rmb_to_usd_c",width:200,
					 renderer: function(v) {
	                        return Ext.util.Format.number(v, "0,000.00");
	                    }
				},
				{text:"余额综合<br>折美元",dataIndex:"total_usx_c",width:200,
					 renderer: function(v) {
	                        return Ext.util.Format.number(v, "0,000.00");
	                    }
				},
				/*
				   {
                    width: 40,
                    xtype: "actioncolumn",
                    align: "center",
                    items: [{
                        iconCls: "x-fa fa-users",
                        tooltip: "客户明细",
                        handler: function(view, rowIndex, colIndex, item, e, record) {
                        	let container=view.up().up().up();
                        	let controller=container.controller;
                        	let date=container.query("toolbar")[0].query("textfield")[1].getValue();
                   
                            let product = record.data.name;
                       console.log("客户明细");
                       console.log(product);
                       this.getView().up().up().controller.getClientTF(date,product);

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
                */
				
				
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
			},
			callback: function(records, operation, success) {
                let usx = grid.getStore().sum("amount_usx_c");
                let rmb = grid.getStore().sum("amount_rmb_c");
                let rmb_usd = grid.getStore().sum("rmb_to_usd_c");
                let total_usx = grid.getStore().sum("total_usx_c");
               
                let tbcount = view.query("toolbar").length;
                if (tbcount < 2) {
                    let bbar = Ext.create({
                        xtype: "toolbar",
                        dock: "bottom",
                        style: { 'background': '#ABCDEF' },
                        items: [
                        	
                        	{
                                xtype: "textfield",
                                fieldLabel: "<center>外币贷款<br>合计:</center>",
                                width:250,
                                fieldStyle: "text-align:right",
                                value: Ext.util.Format.number(usx, "0,000.00")
                            },
                            {
                                xtype: "textfield",
                                fieldLabel: "<center>人民币贷款<br>合计:</center>",
                                width:250,
                                fieldStyle: "text-align:right",
                                value: Ext.util.Format.number(rmb, "0,000.00")
                            },
                            {
                                xtype: "textfield",
                                width:250,
                                fieldLabel: "<center>人民币贷款<br>（折美元）:</center>",
                                fieldStyle: "text-align:right",
                                value: Ext.util.Format.number(rmb_usd, "0,000.00")
                            },
                            {
                                xtype: "textfield",
                                fieldLabel: "<center>贸易融资<br>折美元:</center>",
                                width:250,
                                fieldStyle: "text-align:right",
                                value: Ext.util.Format.number(total_usx, "0,000.00")


                            },
                            {
                        		xtype : "combo",
                    			fieldLabel:"表内外",
                    			width:250,
                    			displayField: "name", // 显示的字段，用函数getDisplayValue()获取
                                valueField: "name", // 取值的字段，用函数getValue()获取
                                value: "全部",
                                listeners: {
                                    change: function ( view, newValue, oldValue, eOpts ) {
                                    	let container=view.up().up();
                                    	let controller=container.controller;
                                    	let store=container.query('grid')[0].getStore();
                                    	controller.filterTypenction(store,newValue);
                                    	
                                    	
                                    	
                                    }
                                    
                                  
                                },
                                store: {
                                    fields: ['code', 'type'],
                                    data: [{
                                        "code": 1,
                                        "name": "表内"
                                    }, {
                                        "code": 2,
                                        "name": "表外"
                                    } 
                                    , {
                                        "code": 3,
                                        "name": "全部"
                                    } ]
                                },
                        		
                        	
                        	},
                          
                        ]
                    });
                    view.add(bbar);
                } else {
                    view.query("toolbar")[1].query("textfield")[0].setValue(Ext.util.Format.number(usx, "0,000.00"));
                    view.query("toolbar")[1].query("textfield")[1].setValue(Ext.util.Format.number(rmb, "0,000.00"));
                    view.query("toolbar")[1].query("textfield")[2].setValue(Ext.util.Format.number(rmb_usd, "0,000.00"));
                    view.query("toolbar")[1].query("textfield")[2].setValue(Ext.util.Format.number(total_usx, "0,000.00"));

                }
            }
		
		
		
		
		
		
		});
		
		console.log(date);
	},
	

	getClientTF:function(date,product){
		let me=this;
		let view=me.getView();
		let win=Ext.create({
			xtype:"window",
			width:700,
			height:500,
			
		});
		
		let store=Ext.create("Ext.data.Store",{
			fields:[
				{name:"",mapping:"clientCode"},
				{name:"",mapping:"clientName"},
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
					url:"/TF/getClientTFBalance",
					type:"ajax"
				}
		});
		
		let grid=Ext.create({
			xtype:"grid",
			width:"100%",
			height:400,
			border:2,
			store,
			
		});
		store.load({
			params:{
				date:date,
				type:product
			}
		});
		win.add(grid);
		view.add(win);
		win.show();
		
	},
	
	getBranchTF:function(date,product){
		let me=this;
		let view=me.getView();
		let win=Ext.create({
			xtype:"window",
			width:700,
			height:500,
			
		});
		view.add(win);
		win.show();
		
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
    
    

    filterTypenction(store, newDate) {
    	let me=this;
    	let view=me.getView();
        if (newDate == "全部") {
            store.clearFilter();
            console.log(store.getFilters());
            return;
        }
        store.setFilters({
            "operator": "like",
            "value": newDate,
            "property": "type"
        });
        let usx = store.sum("amount_usx_c");
        let rmb = store.sum("amount_rmb_c");
        let rmb_usd = store.sum("rmb_to_usd_c");
        
        let total_usx = store.sum("total_usx_c");
        view.query("toolbar")[1].query("textfield")[0].setValue(Ext.util.Format.number(usx, "0,000.00"));
        view.query("toolbar")[1].query("textfield")[1].setValue(Ext.util.Format.number(rmb, "0,000.00"));
        view.query("toolbar")[1].query("textfield")[2].setValue(Ext.util.Format.number(rmb_usd, "0,000.00"));
        view.query("toolbar")[1].query("textfield")[3].setValue(Ext.util.Format.number(total_usx, "0,000.00"));
    },


});
