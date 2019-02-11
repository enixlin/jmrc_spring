//this is an animation in login page
Ext.define('jmrc.view.login.Bg_animationPanel', {
    extend: 'Ext.panel.Panel',
    xtype: 'bg_animation_panel',
    controller: 'login-bganimation',

    width: "97%",
    
    margin: '5 15 5 5',
    //border: 15,
    layout: 'fit',
    style : {
		border : '5px solid gray',
		borderRadius : '5px'
	},

    html:"<div id='div1'></div>"



});

