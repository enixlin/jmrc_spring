Ext.define('jmrc.view.login.Bg_animationPanelController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.login-bganimation',

    afterRender: function() {
    	let flag=new Date();
    	flag%2?showAnimation():showAnimation2();
        
        // let s = symbol();
        // console.log(s);
        console.log("this is afterRender funciton in bganimation controller run.... ");
    },







});