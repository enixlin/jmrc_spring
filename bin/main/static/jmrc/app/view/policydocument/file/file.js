/**
 * 这是政策文件的显示组件，组件是一个弹出式窗口
 */

Ext.define('jmrc.view.policydocument.file.file', {
	extend : 'Ext.window.Window',
	xtype : 'file',

	requires : [ 'jmrc.view.policydocument.file.fileController',
			'jmrc.view.policydocument.file.fileModel' ],

	controller : 'policydocument-file-file',
	viewModel : {
		type : 'policydocument-file-file'
	},
	autoShow : true,

	x : 40,
	y : 100,

	defaults : {
		margin : "5 5 5 5"
	},
	listeners : {
		resize : function(el, width, height, eOpts) {
			let htmleditor = this.query("htmleditor");
			if (htmleditor[0]) {
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
		defaults : {
			width : 100,
			margin : '5 5 5 5 ',
		},
		items : [ {
			xtype : "button",
			text : "打印",
			style : {
				backgroundColor : 'green',
				fontWeight : 'bold',
				color : '#000000'
			},
			handler : "printFile"
		}, {
			xtype : "button",
			text : "打开笔记本",
			style : {
				backgroundColor : 'orange',
				fontWeight : 'bold',
				color : '#000000'
			},
			handler : "openNoteBook"
		}

		]
	} ],
	maximizable : true,

});
