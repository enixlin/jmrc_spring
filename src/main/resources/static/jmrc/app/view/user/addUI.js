Ext.define('jmrc.view.user.addUI',
		{
			extend : 'Ext.window.Window',
			xtype : "addUI",

			requires : [ 'jmrc.view.user.addUIController',
					'jmrc.view.user.addUIModel' ],

			controller : 'user-addui',
			viewModel : {
				type : 'user-addui'
			},

			width : 400,
			height : 300,
			listeners : {

				close : {
					element : "el",
					fn : function() {
						console.log("close click");
						this.hide();
						return;
					}
				}
			},
			title : "添加系统用户",

			items : [ {
				xtype : "container",
				layout : "fit",

				items : [ {
					xtype : "form",
					width : "100%",

					layout : "form",
					buttons : [ {
						text : "关闭",
						handler : "hideAddUI",
						style:{color:"red"}
					}, {
						text : "保存用户",
						handler : "saveUser"
					} ],
					defaults : {
						labelSeparator : ": "
					},
					items : [ {
						xtype : "textfield",
						fieldLabel : '用户名',
						name : "name",
						listeners:{
							
							change:function( thiss, newValue, oldValue, eOpts ){
								let addUI=Ext.ComponentQuery.query("addUI")[0];
								console.log(addUI);
								addUI.controller.isExist(newValue);
							}
						}
					}, {
						xtype : "textfield",
						fieldLabel : '密码',
						name : "password",
					}, {
						fieldLabel : '确认密码',
						name : "passwordConfirm",
						xtype : "textfield"
					},{
						xtype:"label",
						id:"tips",
						columns:2,
						text:"tips:"
					} ]
				} ]
			} ]

		});
