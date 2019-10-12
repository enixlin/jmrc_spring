Ext.define("jmrc.view.performance.detail.totaldetailController", {
	extend : "Ext.app.ViewController",
	alias : "controller.performance-detail-totaldetail",

	afterRender : function() {
		let me = this;
		let view = me.getView();
		let data = view.config.data;
		let start = view.up().query("textfield")[2].getValue()
				.replace(/-/g, "");
		let end = view.up().query("textfield")[3].getValue().replace(/-/g, "");
		data.start = start;
		data.end = end;
		let updatedata = me.getLastUpdateDate();
		let totalPerformace = me.getTotalSettlePerformance(data);
		let totalTask=me.getTotalTask(data);
		Promise.all([ updatedata, totalPerformace,totalTask ]).then(function(result) {
			console.log(result);
			data.updatedate = result[0];
			data.totalsettle = result[1];
			data.totalTask = result[2];
			// msgTip.hide(); // 加载完成，关闭提示框
			me.makeTotalReport(me, view, data);
		});
	},

	/**
	 * 生成全行业务总览
	 */
	makeTotalReport : function(scope, view, data) {
		let summypanel = Ext.create("Ext.panel.Panel", {
			width : window.innerWidth * 0.6,
			// renderTo: Ext.getBody(),
			layout : {
				type : "table",
				columns : 2
			},
			margin : 20,

			items : [ {
				xtype : "panel",
				layout : "form",
				border : 2,
				width : window.innerWidth * 0.20,
				height : window.innerHeight * .5,
				margin : 5,
				html : "<h3>数据更新日期：<font color=red>" + data.updatedate
						+ "</font></h3><br><h3>国际结算业务量：<font color=blue >"
						+ Ext.util.Format.number(data.totalsettle, "0,000.00")
						+ " </font>万美元</h3>" 
						+"<br><h3>全年任务完成率: &nbsp;&nbsp;&nbsp;<font color=blue >" 
					+ Ext.util.Format.number((data.totalsettle/data.totalTask)*100,"0,000.00" )+"%</font></h3>",
				items : [ {
					xtype : "button",
					text : "经营单位分析",
					margin : 10,
					handler : "unitAnalyis"
				},

				{
					xtype : "button",
					text : "客户分析",
					margin : 10,
					handler : "clientAnalyis"
				}, ]
			} ]
		});

		let productGrid = Ext.create({
			xtype : "grid",
			layout : "form",
			border : 2,
			width : window.innerWidth * 0.32,
			height : window.innerHeight * .5,
			margin : 5,
			scrollable : true,
			// bind: { store: "{getSettleTypeProformanceByDate}" },
			tbar : [ "->", {
				text : "导出表格",
				xtype : "button"
			} ],
			columns : [ {
				header : "产品名称",
				dataIndex : "name",
				width : 200
			}, {
				header : "业务笔数",
				dataIndex : "times",
				columnWidth : 0.2
			}, {
				header : "业务金额",
				dataIndex : "performance",
				columnWidth : 0.2,
				renderer : function(value) {
					return Ext.util.Format.number(value, "0,000.00");
				}
			}, 
			{
		          width: 40,
		          xtype: "actioncolumn",
		          align: "center",
		          items: [
		            {
		            	 iconCls: "x-fa fa-bar-chart",
	                        tooltip: "分月明细",
// handler:"getProductMonthDetail"
	                        	   handler:function(view, rowIndex, colIndex, item, e, record){
	   	                                let start = view.up().up().up().config.data.start;
	   	                                let end = view.up().up().up().config.data.end;
	   	                                let product=record.data.name;
	   	                                view
	   	                                    .up()
	   	                                    .up()
	   	                                    .up()
	   	                                    .controller.getProductMonthDetail(product,start, end);
	   	                        }
		            }
		          ]
			},
			{
		          width: 40,
		          xtype: "actioncolumn",
		          align: "center",
		          items: [
		            {
		            	  iconCls: "x-fa fa-users",
	                        tooltip: "客户明细",
	                        handler:function(view, rowIndex, colIndex, item, e, record){
	                              let start = view.up().up().up().config.data.start;
 	                                let end = view.up().up().up().config.data.end;
 	                                let product=record.data.name;
 	                                view
 	                                    .up()
 	                                    .up()
 	                                    .up()
 	                                    .controller.getProductClientDetail(product,start, end);
	                        	
	                        }
		            }]
		          
			},
			{
		          width: 40,
		          xtype: "actioncolumn",
		          align: "center",
		          items: [
		            {
		              iconCls: "x-fa fa-list",
		              tooltip: "业务流水",
		            		    handler:function(view, rowIndex, colIndex, item, e, record){
		                              let start = view.up().up().up().config.data.start;
	   	                                let end = view.up().up().up().config.data.end;
	   	                                let product=record.data.name;
	   	                                view
	   	                                    .up()
	   	                                    .up()
	   	                                    .up()
	   	                                    .controller.getProductDetail(product,start, end);
		                        	
		                        }
		            }
		          ]
			}
			
			
			
			
			],
		});
		productGrid.bindStore(scope.getViewModel().getStore(
				"getSettleTypeProformanceByDate"));
		console.log(productGrid.getStore());
		productGrid.getStore().load({
			params : {
				start : data.start,
				end : data.end,
			},
		});
		summypanel.add(productGrid);
		view.add(summypanel);
	},
	
	getProductMonthDetail:function(product,start,end){
		// alert("getProductMonthDetail");
		alert(product);
		let me=this;
		let view=me.getView();
		let win=Ext.create("Ext.window.Window",{
			width:window.innerWidth*.7,
			height:window.innerHeight*.4,
			scrollable:true,
			layout:{
				type:"table",
				columns:2
			},
			
		});
//		let barChart=Ext.create("",{
//			
//		});
		
		let grid=Ext.create("Ext.grid.Panel",{
			width:"100%",
			
			scrollable:true,
			columns:[
				{header:"月份",dataIndex:"month"},
				{header:"笔数",dataIndex:"times",renderer:function(value){return Ext.util.Format.number(value, "0,000")}},
				{header:"金额",dataIndex:"amount",renderer:function(value){return Ext.util.Format.number(value, "0,000.00")}},
			],
			store:{
				fields:["month","times","amount"],
				
				proxy:{
					url:"/settlerecord/getProductMonthPerformance",
					type:"ajax",
				}
			}
		});
		grid.getStore().load({
			params:{product,start,end},
		});
		win.add(grid);
		view.add(win);
		win.show();
		
	},
	getProductClientDetail:function(start,end){
		alert("getProductClientDetail");
	},
	getProductDetail:function(start,end){
		alert("getProductDetail");
	},

	getLastUpdateDate : function() {
		return new Promise(function(resolve, reject) {
			Ext.Ajax.request({
				url : "/settlerecord/getLastUpdateDate",
				success : function(result) {
					resolve(result.responseText);
				}
			});
		});
	},
	
	getTotalTask : function(data) {
		return new Promise(function(resolve, reject) {
			Ext.Ajax.request({
				url : "/settlerecord/getTotalTask",
				params : {
					start : data.start,
					end : data.end
				},
				success : function(result) {
					resolve(result.responseText);
				}
			});
		});
	},
	getTotalSettlePerformance : function(data) {
		return new Promise(function(resolve, reject) {
			Ext.Ajax.request({
				url : "/settlerecord/getTotalSettlePerformance",
				params : {
					start : data.start,
					end : data.end
				},
				listeners : {
					beforerequest : function() {
						msgTip = Ext.MessageBox.show({
							title : '提示',
							msg : '信息刷新中,请稍候......'
						});
					},
					requestcomplete : function() {
						 msgTip.hide(); // 加载完成，关闭提示框
					},
				},
				success : function(result) {
					
					resolve(result.responseText);
				}
			});
		});
	},
	unitAnalyis : function() {
// alert("unitAnalyis");
		let settle = this.getView().up();
		let settleController = settle.controller;
		let level = settle.query("textfield")[1];
		level.setValue("经营单位");
		settleController.query();
	},
	clientAnalyis : function() {
// alert("unitAnalyis");
		let settle = this.getView().up();
		let settleController = settle.controller;
		let level = settle.query("textfield")[1];
		level.setValue("客户");
		settleController.query();
	}
	
});