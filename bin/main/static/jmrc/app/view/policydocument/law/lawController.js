Ext
		.define(
				'jmrc.view.policydocument.law.lawController',
				{
					extend : 'Ext.app.ViewController',
					alias : 'controller.policydocument-law-law',
					afterRender : function() {
						let me = this;

						// 建立一个iframe嵌入北大法宝，直接用用查询所有的法律法规
						me.getView().setHtml(
										'<iframe id="mediasArea" name="ifocus" src="http://96.0.32.183/" style="width:100%; height:100%;" frameborder="0"></iframe>');
					}
				})
