Ext.define('jmrc.view.register.RegisterView', {
    extend: 'Ext.window.Window',
    xtype: 'register',

    requires: [
        'jmrc.view.register.RegisterViewController',
        'jmrc.view.register.RegisterViewModel',
        // 'jmrc.view.login.LoginView'

    ],
  
    controller: 'register-registerview',
    viewModel: {
        type: 'register-registerview'
    },

    url: '/user/addUser',

    layout: 'form',
    title: '新用户注册',
    width: 400,
    height: 300,
    autoShow: true,
 
    items: [
            {
                xtype: 'textfield',
                fieldLabel: '用户名',
                name: 'name',
                id: 'userNamebox',
                listeners : {
                	// click:function(){alert("click");},
                    change: function(){
                    	// alert("fds");
                    	 Ext.Ajax.request({
                             url: "/user/checkName",
                             params: { name: Ext.getCmp('userNamebox').getValue() },
                             success: function(data) {
                                 let label = Ext.getCmp('nameStatus');
                                 let btn_save = Ext.getCmp('btn_save');
                                 if (data.responseText == "true") {
                                     label.setText("用户名可以使用");
                                     label.setStyle('color', 'green');
                                     btn_save.setDisabled(false);
                                 } else {
                                     Ext.getCmp('nameStatus').setText("用户名不能使用");
                                     label.setStyle('color', 'red');
                                     btn_save.setDisabled(true);

                                 }
                             }
                         });
                    }
                	
                }

            },
            { xtype: 'textfield', fieldLabel: '密码', name: 'password', id: 'password'  },
            {
                xtype: 'textfield',
                fieldLabel: '确认密码',
                name: 'confirmPassword',
                id: 'confirmPassword',
            },
            {
                xtype: 'textfield',
                hidden: true,
                id:"hidenPassword",
                name: 'hidenPassword',
            },
            { xtype: 'label', id: 'nameStatus' }
            ],
            buttons:[{text:'test function',handler:'test22'}]
        
//        tbar:{
//        	
//        	layout:{pack:'center'},
//        	
//        	items:[
//        		{
//                // margin: '10 10 10 10',
//                text: '保存',
//                id: "btn_save",
//                disabled: true,
//                handler: function(){
//                     let name = Ext.getCmp("userName").getValue();
//                     let password = Ext.getCmp("password").getValue();
//                     let confirmPassword = Ext.getCmp("confirmPassword").getValue();
//                     let hidenPassword = Ext.getCmp("hidenPassword").getValue();
//                     let url=this.up().up().up().url;
//                     let grid=this.up().up().up().up();
//
//                     if (name == "" || password == "") {
//                         alert("用户名或密码不能为空");
//                         return;
//                     }
//                     if (password != confirmPassword) {
//                         alert('两次输入的密码不一致');
//                         return;
//                     }
//                     let passwordEncrypt = b64_md5(password);
//                     // 重新设置用户注册信息,避免网络明文传播用户密码
//                     Ext.Ajax.request({
//                         url: url,
//                         params: { name: name, password: passwordEncrypt, status: 1 },
//                         success: function(data) {
//                             alert("提交成功:" + data.responseText);
//                             console.log(grid);
//
//                         },
//                         fail: function(err) { alert("提交失败:" + err.responseText); }
//                     });
//                
//                },
//        		},
//
//			            {
//			              // margin: '10 10 10 10',
//			                text: '退出',
//			                handler: 'logout'
//			            }
//                ]
//        	},

});