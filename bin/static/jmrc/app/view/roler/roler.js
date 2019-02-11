
Ext.define('jmrc.view.roler.roler',{
    extend: 'Ext.panel.Panel',

    requires: [
        'jmrc.view.roler.rolerController',
        'jmrc.view.roler.rolerModel'
    ],

    controller: 'roler-roler',
    viewModel: {
        type: 'roler-roler'
    },

    html: 'Hello, World!!'
});
