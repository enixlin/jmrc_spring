
Ext.define('jmrc.view.test.test',{
    extend: 'Ext.panel.Panel',
    xtype:"test",
    requires: [
        'jmrc.view.test.testController',
        'jmrc.view.test.testModel'
    ],

    controller: 'test-test',
    viewModel: {
        type: 'test-test'
    },

    html: '欢迎使用'
});
