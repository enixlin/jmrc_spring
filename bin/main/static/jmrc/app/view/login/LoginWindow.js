Ext.define('jmrc.view.login.LoginWindow', {

	extend : 'Ext.panel.Panel',
	xtype : 'loginwindow',

	reference : 'loginwin',

	autoShow : true,
	
	controller : "login-login",

	margin : '5 5 5 5',
	layout : {type:"hbox",pack:"end"},

	
	// border: true,

	items : [ 
		
		{
			xtype:"combo",
			emptyText : "选择动画",
			displayField:"name",
			margin : '5 5 5 5',
			store:{
					data:[
							{id:"流浪星际",name:"流浪星际"},
							{id:"旋转几何",name:"旋转几何"},
							{id:"流浪星际",name:"流浪星际"},
							
						]
				  },
				  listeners:{
					  beforeselect :function( combo, record, index, eOpts ) { 
						  						let panel=Ext.ComponentQuery.query("bg_animation_panel");
						  						
						  						let div=Ext.dom.Element.query("div[id=div1]");
						  						//div.empty();
						  						//div.setHtml("");
						  						div[0].innerHTML="";
						  						console.log("div");
						  						console.log(div);
						  						panel[0].controller[record.data["id"]]();
//						  						if(record.data["id"]==1){
//						  							// this 是这个combo
//						  							console.log(record.data["id"]);
//						  							console.log(panel);
//						  							panel[0].controller.流浪星际();
//
//						  							
//						  						}else{
//						  							console.log(record.data["id"]);
//						  							panel[0].controller.旋转几何();
//						  						}
						  			}
				  }
		},
		
		{
		xtype : 'combo',
		// xtype: 'textfield',
		store : Ext.create('jmrc.store.User'),
		Width : 250,
		minWidth : 150,
		labelAlign : 'right',
		labelWidth : 80,
		valueField : 'id',
		// pageSize: 2,
		// listConfig: { minWidth: 300, },
		displayField : 'name',
		emptyText : "用户名称",
		blankText : '用户名称不能为空！',
		margin : '5 5 5 5',
		name : 'id',
		allowBlank : false
	}, {

		Width : 250,
		labelAlign : 'right',
		xtype : 'textfield',
		minWidth : 150,
		emptyText : "用户密码",
		blankText : '用户密码不能为空！',
		margin : '5 5 5 5',
		inputType : "password",
		name : 'password',
		id : 'password',
		allowBlank : false
	}, {
		Width : 300,
		minWidth : 20,
		margin : '15 5 5 5',
		labelWidth : 300,
		columnWidth : 1,
		hidden : false,
		border : 0,
		xtype : 'label',
		id : 'tip',
	}, {
		xtype : "button",
		text : '重置',
		width : 80,
		iconCls : 'x-fa fa-remove',
		margin : '5 5 5 5',
		handler : "onreset"
	}, {
		// xtype: 'button',
		xtype : "button",
		align : "right",
		text : '登录',
		iconCls : 'x-fa fa-home',
		width : 80,
		formBind : true, // only enabled once the form is valid
		disabled : true,
		margin : '5 5 5 5',
		handler : "onlogin" 
	}

	],

// buttons : [
//		
// //{xtype:"tbfill"},
// // Reset and Submit buttons
// {
// // xtype: 'button',
// text : '重置',
// width : 80,
// iconCls : 'x-fa fa-remove',
// margin : '5 5 5 5',
// handler : "onreset"
// }, {
// // xtype: 'button',
// text : '登录',
// iconCls : 'x-fa fa-home',
// width : 80,
// formBind : true, // only enabled once the form is valid
// disabled : true,
// margin : '5 5 5 5',
// handler : "onlogin"
// } ]



});