Ext.define('jmrc.view.user.addUIController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.user-addui',
	hideAddUI : function() {
		this.getView().up().controller.hideAddUI();
	},
	isExist:function(user){
		console.log("user check "+user);
		Ext.Ajax.request({
			url:"/user/getUsers",
			success:function(result){
				let object=eval("( "+result.responseText+" )");
				let tips=Ext.getCmp("tips");
				//console.log(result);
				console.log(object);
				for(let i=0;i<object.length;i++){
					console.log("user name is :"+object[i].name);
					if(object[i].name==user){
						console.log("user is exist");
						tips.setHtml("提示：用户名已存在");
						tips.setStyle({
							fontWeight : 'bold',
							color : 'red',
							border : 0,
						});
					}else{
						tips.setHtml("提示：用户名可以使用");
					}
					tips.setHtml("");
				}
			},
		});
	}
});
