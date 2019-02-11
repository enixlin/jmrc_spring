
Ext.define('jmrc.view.report.unit.Unitreport',{
    extend: 'Ext.panel.Panel',
    xtype:"unitreport",

    requires: [
        'jmrc.view.report.unit.UnitreportController',
        'jmrc.view.report.unit.UnitreportModel'
    ],

    controller: 'report-unit-unitreport',
    viewModel: {
        type: 'report-unit-unitreport'
    },

    html: 'unit report'
});
