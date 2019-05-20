Ext
		.define('jmrc.view.policydocument.PolicydocumentController',
				{
					extend : 'Ext.app.ViewController',
					alias : 'controller.policydocument-policydocument',

					query : function() {
						let state = Ext.getCmp("effectFileTag").value;
						let keyWord = Ext.ComponentQuery
								.query("[fieldLabel='请输入查询关键字']")[0].value;
						let container = this.getView();

						console.log(state);
						let store = this.getViewModel().getStore(
								"policydocumentStore");
						store.load({
							params : {
								"keyWord" : keyWord,
								"state" : state
							}
						});

					},
					showFile : function(record) {

						let me = this;
						console.log("documentlist view");
						let view = me.getView();
						console.log(view);
						let parentWidth = view.getWidth() * 0.7;
						let winHeight = view.getHeight();
						let newFile = Ext.create({
							xtype : "file",
							record : record,
							width : parentWidth,
							height : window.innerHeight * 0.9
						});
						// let
						// newFile=Ext.create({xtype:"documentwin",record:record,width:parentWidth,height:winHeight});
						view.add(newFile);
						newFile.show();

					}

				});
