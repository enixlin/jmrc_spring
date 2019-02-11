Ext.define('jmrc.view.register.RegisterViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.register-registerview',


    test22:function(){
    	alert("test2444442");
    },

    save: function() {
        let form = this.getView().getForm();
        let name = form.getFieldValues()['name'];
        let password = form.getFieldValues()['password'];
        let confirmPassword = form.getFieldValues()['confirmPassword'];
        let hidenPassword = form.getFieldValues()['hidenPassword'];

        if (name == "" || password == "") {
            alert("用户名或密码不能为空");
            return;
        }
        if (password != confirmPassword) {
            alert('两次输入的密码不一致');
            return;
        }
        let passwordEncrypt = b64_md5(password);
        //重新设置用户注册信息,避免网络明文传播用户密码
        form.setValues({ name: name, hidenPassword: passwordEncrypt, password: '', confirmPassword: '' });
        Ext.Ajax.request({
            url: form.url,
            params: { name: name, hidenPassword: passwordEncrypt, password: '', confirmPassword: '' },
            success: function(data) {
                alert("提交成功:" + data.responseText);
                console.log(data)
            },
            fail: function(err) { alert("提交失败:" + err.responseText); }
        });

    },


    checkName: function() {
        Ext.Ajax.request({
            url: "/users/checkName",
            params: { name: Ext.getCmp('userName').getValue() },
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
    },





    // 加密用户密码,引用/public/javascript/util/md5.js 中的b64_md5函数
    encryptPassword: function(password) {
        return b64_md5(password);
    },



    //退出新增用户界面,回到登录界面
    logout: function() {
        //this.close();
    }




})