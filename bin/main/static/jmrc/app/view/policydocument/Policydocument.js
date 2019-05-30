Ext.define('jmrc.view.policydocument.Policydocument', {
	extend : 'Ext.panel.Panel',
	xtype : "policydocument",

	requires : [ 'jmrc.view.policydocument.PolicydocumentController',
			'jmrc.view.policydocument.PolicydocumentModel' ],

	controller : 'policydocument-policydocument',
	viewModel : {
		type : 'policydocument-policydocument'
	},

	items : [ {
		xtype : "container",
		layout : "vbox",
		height : window.innerHeight * 0.8,
		items : [
				{
					xtype : "container",
					layout : {
						type : "hbox",
						pack : "end"
					},
					width : "100%",
					itemId : "querycondition",
					items : [ {
						xtype : "textfield",
						width : 600,
						fieldLabel : "请输入查询关键字",
						labelWidth : 150,
						margin : "5 5 5 5",
						itemId : "keyWord"
					}, {
						xtype : "checkbox",
						width : 200,
						fieldLabel : "过滤失效文件",
						id : "effectFileTag",
						labelWidth : 100,
						checked : true,
						margin : "5 5 5 5"
					}, {
						xtype : "button",
						width : 100,
						text : "查询",
						iconCls : 'x-fa fa-search-plus',
						handler : "query",
						margin : "5 5 5 5"
					}, {
						xtype : "button",
						width : 100,
						iconCls : 'x-fa fa-pencil-square-o',
						text : "笔记本",
						margin : "5 5 5 5"
					}, ]
				},
				{
					xtype : "grid",
					id:"filelistgrid",
					width : "100%",
					margin : "5 5 5 5",
					border : 2,
					height : window.innerHeight * 0.73,
					// plugins: "gridcellediting ",
					scrollable : true,
					bind : {
						store : "{policydocumentStore}"
					},// 注意Store名称要用大括号括起来
					autoShow : true,
					// selModel:"column",
					columns : [
							{
								text : "生效日期",
								dataIndex : 'effTime',
								width : "10%",
								align : "left",
								
							}, {
								text : "文件号",
								dataIndex : 'docNum',
								width : "20%",
								align : "left",
								editor : "textfield"
							}, {
								text : "标题",
								dataIndex : 'title',
								width : "50%",
								cellWrap : true,
								align : "left"
							}, {
								text : "状态<br>(2为失效，0为有效)",
								dataIndex : 'state',
								width : "10%",
								align : "center",
								filter : {
									type : 'boolean',
									value : true,
									yesText : 'True',
									noText : 'False'
								}
							},
							{
								text : "操作",
								xtype: 

									'actioncolumn', 
									items : [{ 
									iconCls : 'x-fa fa-pencil-square-o',
									tooltip : '打开该文件的笔记', 
									//text:"编辑",
									handler : function(grid, rowIndex, colIndex) { 
									var record = grid.getStore().getAt(rowIndex); 
									console.log("record is ...");
									console.log(record['data']['dId']);
									console.log("grid is ...");
									console.log(Ext.getCmp("filelistgrid"));
										let dId=record['data']['dId'];
										//文件的编号record['data']['dId']
										Ext.getCmp("filelistgrid").up().up().controller.getNotesBydId(dId);
									} 
									}]
								
							}
							
							
							
							
							],
					listeners : {
						itemclick : function(thiss, record, item, index, e,
								eOpts) {
							//if(thiss.){
								
							//	this.up().up().controller.showFile(record);
							//}
//							console.log("item is ");
//							console.log(item);
//							console.log("record['data']['dId'] is ");
//							console.log(record['data']['dId']);
//							console.log("index is ");
//							console.log(index);
//							console.log("eOpts is ");
//							console.log(eOpts);
						}
					}
				} ]
	} ]

});
