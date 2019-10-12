Ext.define('jmrc.view.policydocument.notes.Notes', {
	extend : 'Ext.window.Window',
	xtype : "notes",

	requires : [ 'jmrc.view.policydocument.notes.NotesController',
			'jmrc.view.policydocument.notes.NotesModel' ],

	controller : 'policydocument-notes-notes',
	viewModel : {
		type : 'policydocument-notes-notes'
	},

	autoShow : true,
	
	x : 40,
	y : 100,
	script : true,
	defaults : {
		margin : "5 5 5 5"
	},
	listeners : {
		resize : function(el, width, height, eOpts) {
			let htmleditor=this.query("htmleditor");
			if(htmleditor[0]){
				htmleditor[0].setWidth(width);
				htmleditor[0].setHeight(height);
				
			}
		},

	},
	items : [ {
		xtype : 'container',

		layout : {
			type : 'hbox',
			pack : "end"
		},
		defaults:{
			width:100,
			margin:'5 5 5 5 ',
		},
		items : [ 
			
			{
				xtype : "button",
				text : "删除笔记",
				style : {
					backgroundColor : 'red',
					fontWeight : 'bold',
					color : '#000000'
				},
				handler : "deleteNote"
			} ,
			{
			xtype : "button",
			text : "打印",
			style : {
				backgroundColor : 'green',
				fontWeight : 'bold',
				color : '#000000'
			},
			handler : "printFile"
		} ,{
			xtype : "button",
			text : "保存笔记",
			style : {
				//backgroundColor : 'orange',
				fontWeight : 'bold',
				color : '#000000'
			},
			handler : "saveCustomerNote"
		} ]
	} ],
	maximizable : true,

});
