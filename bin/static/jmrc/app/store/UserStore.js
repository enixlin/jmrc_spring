Ext.define('jmrc.store.UserStore', {
    extend: 'Ext.data.Store',

    alias: 'store.userStore',
    autoLoad: true,

    fields: [
        'name', 'id', 'password', 'status'
    ],
    proxy: {
        type: 'ajax',
        url: '/user/getAllUserInformation',
       

    }
});