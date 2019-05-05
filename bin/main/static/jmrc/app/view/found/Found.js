
Ext.define('jmrc.view.found.Found',{
    extend: 'Ext.panel.Panel',
    xtype:"found",

    requires: [
        'jmrc.view.found.FoundController',
        'jmrc.view.found.FoundModel'
    ],

    controller: 'found-found',
    viewModel: {
        type: 'found-found'
    },

    html: 'Hello, found!!'
});
