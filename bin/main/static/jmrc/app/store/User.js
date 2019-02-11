Ext.define('jmrc.store.User', {
    extend: 'Ext.data.Store',

    //alias: 'store.user',
    autoLoad: true,

    fields: [
        'name', 'id'
    ],
    proxy: {
        type: 'ajax',
        url: '/user/getUsers',

    }
});