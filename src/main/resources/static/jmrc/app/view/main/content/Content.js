
Ext.define('jmrc.view.main.content.Content',{
    extend: 'Ext.tab.Panel',
    xtype:"content",

    requires: [
        'jmrc.view.main.content.ContentController',
        'jmrc.view.main.content.ContentModel'
    ],

    controller: 'main-content-content',
    viewModel: {
        type: 'main-content-content'
    },
  
    

});
