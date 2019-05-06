Ext.define('jmrc.view.user.UserController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.user-user',
    
    afterRender:function(){
//    	alert("after");
    	this.getView().add({xtype:"addUI",reference:"addUI"});
    },
    
    showAddUI:function(){
    	//alert("show");
    	console.log(Ext.query("addUI"));
    	//this.getView().getReference("addUI").show();
    	
    }

});
