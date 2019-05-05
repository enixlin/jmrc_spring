Ext.define('jmrc.view.main.content.ContentController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.main-content-content',
    
    tabIsExist:function(tabName){
    	
    },
    
    addTab:function(tabId){
    	this.getView().tabManager.push(tabId);
    }

});
