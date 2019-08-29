Ext.define('jmrc.view.performance.config.configController',
		{
			extend : 'Ext.app.ViewController',
			alias : 'controller.performance-config-config',

			AllProducts : [],
			RangeProducts : [],

			// 保存选定的国际结算量产品统计口径
			saveRange : function() {
				let me = this;
				let view = me.getView();
				let checkgroup = view.query("checkboxgroup")[0];
				let fs = checkgroup.items.items;
				let productRange = [];

			},

			afterRender : function() {
				let me = this;
				let view = me.getView();
				// let AllProducts=me.getAllProducts();
				// let RangeProducts=me.getRangeProducts();
				let p = new Promise(me.getAllProducts(me,resolve)).then(me.getRangeProducts(me,resolve))
						.then(function(resolve, reject) {
							let rp = me.RangeProducts.length;
							console.log(rp);
						});

			},

			// 取得数据库中结算量统计口径的产品
			getRangeProducts : function(me,resolve) {

				let rangeProductStore = me.getViewModel().getStore(
						"rangeProductStore");
				rangeProductStore.load(function(records, operation, success) {

					for (let n = 0, len = records.length; n < len; n++) {
						me.RangeProducts.push(records[n]);
					}
					let rp = me.RangeProducts.length;
					console.log(rp);

					resolve();
					
					// return resolve();
				});
			},

			// 取得数据库中所有的国际业务产品
			getAllProducts : function(me,resolve) {
				let allProductStore = me.getViewModel().getStore(
						"allProductStore");
				allProductStore.load(function(records, operation, success) {

					for (let n = 0, len = records.length; n < len; n++) {
						me.AllProducts.push(records[n]);
					}
					let ap = me.AllProducts.length;
					console.log(ap);

					resolve();

				});
			},

			refreshFields : function() {

			}

		});
