Ext.define('jmrc.view.user.addUIController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.user-addui',
	hideAddUI : function() {
		this.getView().up().controller.hideAddUI();
	},
	// 保存用户
	saveUser : function() {
		let me=this;
		let f=me.getView().query("form")[0].getForm();
		console.log(f);
		let form=Ext.getForm();
		console.log(form);
		return;
		//let user={name:};
		
		// 如果用户不存在，就保存用户
		if (!isExist(user)) {
			console.log("save user success");
			Ext.Ajax.request({
				url:"/user/addUser",
				params:user,
				success:function(result){
					console.log("保存用户成功");
				}
			});
		}
	},
	isExist : function(user) {
		console.log("user check " + user);
		Ext.Ajax.request({
			url : "/user/getUsers",
			success : function(result) {
				let object = eval("( " + result.responseText + " )");
				let tips = Ext.getCmp("tips");
				let saveUserButton=Ext.getCmp("saveUserButton");
				// console.log(result);
				console.log(object);
				console.log(object.lenght);
				for (let i = 0; i < object.length; i++) {
					console.log("object name is :" + object[i].name);
					console.log("i is :" + i);
					if (object[i].name == user) {
						console.log("user is exist");
						tips.setText("提示：用户名已存在");
						tips.setStyle({
							fontWeight : 'bold',
							color : 'red',
							border : 0,
						});
						saveUserButton.setDisabled(true);
						return true;
						break;
					} else {
						tips.setText("提示：用户名可以使用");
						tips.setStyle({
							fontWeight : 'bold',
							color : 'green',
							border : 0,
						});
						saveUserButton.setDisabled(false);
						
					}

				}
			},
		});
	}
});
