/**
 * This class is the controller for the main view for the application. It is
 * specified as the "controller" of the Main view class.
 * 
 * TODO - Replace this content of this view to suite the needs of your
 * application.
 */

// 应用程序主界面
Ext.define("jmrc.view.main.MainController", {
	extend : "Ext.app.ViewController",

	alias : "controller.main",

	afterRender : function() {
		let me = this;
		let view = me.getView();
		console.log(view);
		console.log("cfg.user information");
		console.log(CFG.getUserInfo());
		// 选择角色
		let user_id = CFG.getUserInfo().id;
		let user_name = CFG.getUserInfo().name;
		let roler_id = CFG.getUserInfo().rolerId;
		let roler_name = CFG.getUserInfo().rolerName;

		console.log("cfg.user information roler name is :");
		console.log(CFG.getUserInfo().rolerName);
		console.log(user_id);
		me.choiceRoler();
	

	},

	// 选择用户角色
	choiceRoler : function() {
		let me = this;
		let userId = CFG.getUserInfo().id;
		let topPart = Ext.getCmp("topPart");
		let view=me.getView();
		let rolerArray = [];
		let win = Ext.create("Ext.window.Window", {
			title : "请选择你用使用的用户角色",
			width : "500",
			modal:true,
			height : 400,
			x : 300,
			y : 300,

			draggable : true,
			items : [ {
				xtype : 'fieldcontainer',
				fieldLabel : '可选择的角色',
				defaultType : 'radiofield',
				margin : 20,
				defaults : {
					flex : 1
				},
				layout : 'vbox',

			}, ],
			buttons : [ {
				text : "选择",
				handler:"createFeatureByRoler"
			} ]
		});
		let rolerStore = Ext.create("Ext.data.Store", {
			fields : [ "id", "name" ],
			proxy : {
				url : "/roler/getRolerByUserId",
				type : "ajax"
			}
		});
		rolerStore.load({
			params : {
				userId : userId
			},
			callback : function(records, operation, success) {
				if (records.length > 1) {
					for ( let e in records) {
						win.query("fieldcontainer")[0].add({
							boxLabel : records[e].data.name,
							name : 'name',
							inputValue : records[e].data.id,
						// id : 'radio1'
						});
					}
					win.show();
				}else{
					win.hide();
					topPart.query("button")[1].setDisabled(true);
				}
			}
		});

		view.add(win);
		win.show();
	},
	
	
	createFeatureByRoler:function(rolerId){
		alert("choice");
	},

	
	
	// 注销用户登录状态
	logout : function() {
		if (confirm("是否要退出?")) {
			// Remove the localStorage key/value
			Ext.Ajax.request({
				url : "/auth/doLogout"
			});
			CFG.setUserInfo(null);
			var view = this.getView();
			view.destroy();
			Ext.create({
				xtype : "login"
			});
		}
	},

});