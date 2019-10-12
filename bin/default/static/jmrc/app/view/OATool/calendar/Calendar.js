
Ext.define('jmrc.view.oatool.calendar.Calendar',{
    extend: 'Ext.panel.Panel',
    xtype:'calendar-panel',

    requires: [
        'jmrc.view.oatool.calendar.CalendarController',
        'jmrc.view.oatool.calendar.CalendarModel'
    ],

    controller: 'oatool-calendar-calendar',
    viewModel: {
        type: 'oatool-calendar-calendar'
    },

    html: 'Hello, calendar。。。。。。。。。。。。。。。。。。。。。。。。。'
});
