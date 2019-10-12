
Ext.define('jmrc.view.policydocument.law.law',{
    extend: 'Ext.panel.Panel',
    xtype:"law",

    requires: [
        'jmrc.view.policydocument.law.lawController',
        'jmrc.view.policydocument.law.lawModel'
    ],

    controller: 'policydocument-law-law',
    viewModel: {
        type: 'policydocument-law-law'
    },

//    html: 'Hello, World!!'
});
