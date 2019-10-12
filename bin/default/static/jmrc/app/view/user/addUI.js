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
			height : 350,
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
				layout : "form",

				items : [ {
					xtype : "form",
					width : "100%",

					layout : "form",
					buttons : [ {
						text : "关闭",
						handler : "hideAddUI",
						style : {
							color : "red"
						}
					}, {
						text : "保存用户",
						id:"saveUserButton",
						handler : "saveUser"
					} ],
					defaults : {
						labelSeparator : ": "
					},
					items : [
							{
								xtype : "textfield",
								fieldLabel : '用户名',
								name : "name",
								listeners : {
									change : function(thiss, newValue,
											oldValue, eOpts) {
										let addUI = Ext.ComponentQuery
												.query("addUI")[0];
										addUI.controller.isExist(newValue)
											
									

									}
								}
							//15924150208  18058738020   15905728568
							}, {
								xtype : "textfield",
								fieldLabel : '密码',
								name : "password",
							}, {
								fieldLabel : '确认密码',
								name : "passwordConfirm",
								xtype : "textfield"
							}, {
								xtype : "label",
								id : "tips",
								columnWidth : 2,
								text : "tips:"
							} ]
				} ]
			} ]

		});
