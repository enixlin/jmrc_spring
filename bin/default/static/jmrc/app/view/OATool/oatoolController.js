Ext.define('jmrc.view.oatool.oatoolController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.oatool-oatool',
    	
    	// 在渲染后执行的操作,加载数据
    	afterRender:function(){
  		let toollist=Ext.getCmp('toolselectbox');
  		console.log(toollist);
  		toollist.store=Ext.create('Ext.data.Store',{
  			proxy:{
  				type:'ajax',
  				url:'/oatools/getAllTools',
  				autoLoad: true
  			}
  		});
  		
  	
  	},
  	showselect:function(){alert("selelct")}

});
