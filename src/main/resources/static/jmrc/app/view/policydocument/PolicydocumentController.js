Ext
		.define(
				'jmrc.view.policydocument.PolicydocumentController',
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

					getNotesBydId : function(dId) {
						// url:"/policydocument/getPolicyNoteBydId",
						let me = this;
						let view = me.getView();
						let parentWidth = view.getWidth() * 0.7;
						let winHeight = view.getHeight();
						let noteList = Ext.create({
							xtype : "noteslist",
							dId : dId,
							width : parentWidth,
							height : winHeight * 0.9
						});
						// let
						// newFile=Ext.create({xtype:"documentwin",record:record,width:parentWidth,height:winHeight});
						view.add(noteList);
						noteList.show();

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
							modal:true,
							height : window.innerHeight * 0.9
						});
						// let
						// newFile=Ext.create({xtype:"documentwin",record:record,width:parentWidth,height:winHeight});
						view.add(newFile);
						newFile.show();

					},

					// 打开一个用户笔记记录，
					openUserNote : function(dId, userId,record) {
						let me = this;
						let view = me.getView();
				
						// 构造一个查询对象
						let note = {
							userId : userId,
							dId : dId,
							overWrite : false,
						};

						Ext.Ajax
								.request({
									url : "/policydocument/openNotes",
									method : "post",
									params : {
										"dId" : note.dId,
										"userId" : note.userId,
										"overWrite" : note.overWrite,
									},
									success : function(result) {

										let parentWidth = view.getWidth() * 0.7;
										let winHeight = view.getHeight();
										let note = eval("("
												+ result.responseText + ")");
										record.data['note'] = note;
										let noteWindow = Ext.create({
											xtype : "notes",
											record : record,
											width : parentWidth,
											height : window.innerHeight * 0.9,
										});
										view.add(noteWindow);
										noteWindow.show();

									}
								});
					}

				});
