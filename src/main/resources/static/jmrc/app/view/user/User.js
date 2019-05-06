
Ext.define('jmrc.view.user.User',{
    extend: 'Ext.panel.Panel',
    xtype:"user",

    requires: [
        'jmrc.view.user.UserController',
        'jmrc.view.user.UserModel'
    ],

    controller: 'user-user',
    viewModel: {
        type: 'user-user'
    },
    tbar:[{text:"create user"}],
    items:[{xtype:"grid"}]

});
