Ext.define('jmrc.view.user.UserViewController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.user-userview',

	userRolerList : null,
	allRolerList : null,
	defaultListenerScope : true,
	init : function() {
		let me = this;
		// //视图没有渲染,就可以取得viewModel
		var ref = this.getReferences();
		ref.usergrid.setStore(Ext.create("jmrc.store.UserStore"));
		// 取得所有的角色
		Ext.Ajax.request({
			url : "/roler/getAllRolers",
			success : function(result) {
				console.log("result");
				console.log(result);
				me.allRolerList = eval("(" + result.responseText + ")");
			}
		});

	},

	showRolerSelect : function(event, record) {
		let me = this;

		if (event.target.tagName == "INPUT") {

			let user_id = record.data["id"];
			// 取得当你用户的所有的角色
			Ext.Ajax.request({
				url : "/user/getRolerByUser",
				params : {
					user_id : user_id
				},
				success : function(result) {
					me.userRolerList = eval("(" + result.responseText + ")");

					me.getView().add({
						xtype:"window",
						width : 400,
						height : 400,
						autoShow:true,
						layout : "form",
						id : "rolerwindow" + record.data["id"],
						title : "当前用户: 编号:" + record.data["id"] + " 用户名:"
								+ record.data["name"],

						defaultListenerScope : true,

						items : [ {
							xtype : 'fieldset',
							flex : 1,
							title : '可选角色',
							// checkboxToggle : true,
							reference : "rolerscheckbox",
							id : "rolerscheckbox",
							defaultType : 'checkbox', // each item will be a
							layout : 'form',
							defaults : {
								anchor : '100%',
								hideEmptyLabel : false
							},

						} ],

						buttons : [ {
							text : '保存',
							width : 80,
							iconCls : 'x-fa fa-save',
							margin : '10 25 5 10',
							listeners : {
								click : function() {
									let checkboxGroup = Ext
											.getCmp("rolerscheckbox");
									let userId = record.data["id"];
									let rolerList = [];
									console.log(user_id);
									// 历遍所有的角色选择框，将勾选了的角色对应的编号写入roler[]数组
									checkboxGroup.items.each(function(item) {
										item.checked ? rolerList
												.push(item.inputValue) : "";
									});
									// 将用户编号和角色编号列表发送到服务器
									Ext.Ajax.request({
										url : "/user/addRolersToUser",
										params : {
											userID : user_id,
											rolers : rolerList
										},
										success : function(result) {

											console.log(result.responseText);
											alert("更新用户角色成功："
													+ result.responseText);

										}
									});
								}
							},
						} ]
					});

			
					// 显示所有的系统角色,并将用户的已有角色打勾
					let RolerList = me.allRolerList;
					let userRoler = me.userRolerList;
					let rolerscheckbox = Ext.getCmp("rolerscheckbox");
					for ( let e in RolerList) {
						let flag = false;
						for ( let en in userRoler) {
							if (RolerList[e]["id"] == userRoler[en]["id"]) {
								flag = true;
							}
						}
						if (flag == true) {
							rolerscheckbox.add({
								boxLabel : RolerList[e]["name"],
								checked : true,
								name : RolerList[e]["name"],
								inputValue : RolerList[e]["id"]
							});
						} else {
							rolerscheckbox.add({
								boxLabel : RolerList[e]["name"],
								name : RolerList[e]["name"],
								inputValue : RolerList[e]["id"]
							});
						}
					}

				}
			});

		}
	},

	deleteUser : function(event, record) {
		console.log(record.data["id"]);
		
		// alert("确定要删除用户");
		if (confirm("确定要删除用户")) {
		}
	},

	afterRender : function() {

	},

	showAddUser : function() {
		let me = this;
		me.getView().add({
			xtype : 'register',
			autoShow:true
		});

	

	},
	hidderAllWin : function() {
		console.log(Ext.WindowManager);
	},

	refresh : function() {
		// alert("refresh");
		var ref = this.getReferences();
		ref.usergrid.store.reload();
	},
	freezing : function() {
		var gp = Ext.ComponentQuery.query("grid");

		console.log(gp[1]);
	},
	post : function() {
		var s = this.getViewModel().data['pp'];

	},



});