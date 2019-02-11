/**
 * This class is the controller for the main view for the application. It is
 * specified as the "controller" of the Main view class.
 * 
 * TODO - Replace this content of this view to suite the needs of your
 * application.
 */



// 应用程序主界面
Ext.define("jmrc.view.main.MainController", {
    extend: "Ext.app.ViewController",

    alias: "controller.main",


    test:function(){
    	Ext.create("jmrc.view.test.test");
    },
    onItemSelected: function(sender, record) {
        Ext.Msg.confirm("Confirm", "Are you sure?", "onConfirm", this);
    },
    logout: function() {
        if (confirm("是否要退出?")) {
            // Remove the localStorage key/value
            Ext.Ajax.request({ url: "/auth/doLogout" });
        	CFG.setUserInfo(null);
            var view = this.getView();
            view.destroy();
            Ext.create({
                xtype: "login"
            });
        }
    },



    showRolerListWindow: function() {
        var me = this;
        let choicerolerwindow = me.getReferences().choicerolerwindow;
        if (choicerolerwindow) {
            choicerolerwindow.show();
        } else {
            me.choicRoler(CFG.getUserInfo().id);
        }

    },

    // 根据用户角色加载不同的用户功能
    choicRoler: function(user_id) {
        var me = this;
        return new Promise(function(resolve, reject) {
            Ext.Ajax.request({
                url: "/user/getRolerByUser",
                params: { user_id: user_id },
                success: function(result) {
                    let data = eval("(" + result.responseText + ")");
                    
                    let win = Ext.create("Ext.window.Window", {
                        title: "请选择你要使用的角色",
                        reference: 'choicerolerwindow',
                        width: 300,
                        height: 300,
                        autoShow:true,
	                       closable:false,
	                       dockedItems: [{
	                    	    xtype: 'toolbar',
	                    	    dock: 'bottom',
	                    	    items: [
	                    	    	{xtype:'tbfill'},
	                    	    	{ xtype: "button", text: '取消', listeners:{click:function(){win.hide()}} },
	                    	    	{ xtype: "button", text: '选择', handler: "onChoiceRoler", layout: { type: 'hbox', align: 'right' } },

	                    	    ]
	                    	}]
                    });
                    let rolers = Ext.create('Ext.form.RadioGroup', { reference: 'rolerRadioGroup', layout: { type: "vbox", align: "left" }, margin: '10 10 10 10' });

                    win.add(rolers);
                    me.getView().add(win);
                    // 将角色数据写入全局设置中
                    CFG.setRoler_list(data);
                    for (let index in data) {
                        rolers.add({ boxLabel: data[index]["name"], inputValue: data[index]["id"] });
                    }
                   
                    if (data.length == 1) {
                    	// alert("当前用户只有一种角色,自动以唯一角色登录");
                        me.initAuthor(data[0].id,data[0].name);
                        win.hide();
                        return;
                    }
                    resolve(data);
                    // view.add({ title: element["name"] });
                }
            });
        });
    },
    onChoiceRoler: function() {
       let me = this;
        let ref =  me.getReferences();
        console.log("ref is ............");
        console.log(me.getView().down());
        if(ref.rolerRadioGroup.getChecked().length==0){
        	alert("还没有选择角色");
        	return;
        }
        let roler_id = ref.rolerRadioGroup.getChecked()[0].inputValue;
        let roler_name=ref.rolerRadioGroup.getChecked()[0].boxLabel;
        let user_id=CFG.getUserInfo().id;
        let user_name=CFG.getUserInfo().name;
        console.log("onchiceRoler function ");
       
        CFG.setUserInfo({id:user_id,name:user_name,rolerId:roler_id,password:null,status:null});
        console.log("mark roler");
        ref.user_name.setText(user_name+"["+roler_name+"]");
        console.log("ref.user_name.text is :"+ref.user_name.text);
        

        // 隐藏角色选择窗口
        ref.choicerolerwindow.hide();
        console.log("choicerolerwindow hide ");
        this.initAuthor(roler_id,roler_name);

        console.log(roler_id);
    },

    init: function() {
    	let me=this;
    	console.log("cfg.user information");
    	console.log(CFG.getUserInfo());
        // 选择角色
        let user_id = CFG.getUserInfo().id;
        let user_name = CFG.getUserInfo().name;
        let roler_id=CFG.getUserInfo().rolerId;
        let roler_name=CFG.getUserInfo().rolerName;
     
        console.log("cfg.user information roler name is :");
    	console.log(CFG.getUserInfo().rolerName);
        me.getReferences().user_name.text = user_name;

        if(roler_id==undefined || roler_id==0 || roler_name==undefined){
        
        	CFG.setUserInfo({id:user_id,name:user_name,rolerId:roler_id,password:null,status:null});
        	// CFG.setRolerInfo({rolerId:roler_id,rolerName:""});
        	
        	
        	this.choicRoler(user_id).then(function(result) {
        		
        	 });
        }else{
        	  
        	this.initAuthor(roler_id,roler_name);
        }
       
    },
    
    


    // 根据用户角色加载不同的用户功能
    initAuthor: function(roler_id,roler_name) {
        let me = this;
        me.getView().removeAll();
        let ref = me.getReferences();
        console.log("根据用户角色加载不同的用户功能");
        console.log(ref);
        let user_name = CFG.getUserInfo().name;
        ref.user_name.setText(user_name+"["+roler_name+"]");
        console.log(ref.user_name.text);
        let view = this.getView();
        Ext.Ajax.request({
            url: "/auth/getRuleByRoler",
            params: { id: roler_id,rolerName:roler_name },
            success: function(result) {
                let data = eval(result.responseText);
                data.forEach(element => {
                    me.getView().add({ title: element["name"], iconCls: element["icon"], items: [{ xtype: element['panel'] }] });
                });
            }
        });
    }


});