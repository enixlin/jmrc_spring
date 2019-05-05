
Ext.define('jmrc.view.found.FoundList',{
    extend: 'Ext.panel.Panel',
    xtype:'found_list',

    requires: [
        'jmrc.view.found.FoundListController',
        'jmrc.view.found.FoundListModel'
    ],

    controller: 'found-foundlist',
    viewModel: {
        type: 'found-foundlist'
    },

    html: 'Hello, found_list!!'
});
