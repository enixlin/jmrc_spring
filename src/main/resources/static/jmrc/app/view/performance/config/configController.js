Ext.define('jmrc.view.performance.config.configController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.performance-config-config',
    
    showProduct:function(){
    	let me=this;
    	
    	let store1=me.getView().getStore("allProductStore");
    	let data1=store1.getDate();
    	let count=data1.count();
    	let win=Ext.create("Ext.window.Window",{
    		model:true,
    		width:400,
    		height:300
    		
    		
    	});
    	
    	let cbs=Ext.create("Ext.form.FieldSet",{
    		flex: 1,
            title: 'Individual Radios',
            checkboxToggle: true,
            defaultType: 'radio', // each item will be a radio button
            layout: 'anchor',
            defaults: {
                anchor: '100%',
                hideEmptyLabel: false
            },

    	});
    	
    	for(let i=0;i<count;i++){
    		cbs.add({
                xtype: 'textfield',
                name: 'products',
                fieldLabel: 'Align'
            }, );
    	}
    	
    	}
 
    
    

});
