
Ext.define('jmrc.view.user.User',{
    extend: 'Ext.grid.Panel',
    xtype:"user",

    requires: [
        'jmrc.view.user.UserController',
        'jmrc.view.user.UserModel'
    ],

    controller: 'user-user',
    viewModel: {
        type: 'user-user'
    },
    
    layout:"fit",


});
