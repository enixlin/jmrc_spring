Ext
    .define(
        'jmrc.view.performance.chart.customerpiechart.piechartController', {
            extend: 'Ext.app.ViewController',
            alias: 'controller.performance-chart-customerpiechart-piechart',
            afterRender: function() {
                let me = this;
                let view = me.getView();
                // 获取配置参数对象
                let config = view.config.data;
                let store = config.store;
                let width = config.width;
                let height = config.height;
                let layout = config.layout;

                let container = Ext.create({
                    xtype: "panel",
                    width: width,
                    height: height,
                    layout: layout,
                    scrollable: true,

                });

                let chart = Ext.create({
                    xtype: 'polar',
                    reference: "chart",
                    border:2,
                    margin:5,
                    // renderTo :document.body,
                    columnWidth: config.chartWidth,
                    height: height * .7,
                    innerPadding:20,
                    insetPadding:10,
                    interactions: ['rotate', 'itemhighlight'],
                    store: store,
                    tbar: [{
                        text: "生成图像",
                        platformConfig: {
                            desktop: {
                                text: "生成图像"
                            }
                        },
                        handler: "onPreview"
                    }],
                    captions: {
                        // 饼图的标题
                        title: config.title.name,
                        // 饼图数据来源
                        credits: {
                            text: config.title.creditsName,
                            align: config.title.creditsAlign, // 左对齐还是右对齐
                        }
                    },
                    // 图例
                    legend: {
                        docked: config.legend.docked,
                        margin:config.legend.margin
                        
                    },
                    // 饼图主体
                    series: [{
                        type: 'pie',
                        angleField: config.angleField,
                        label: {
                            field: config.labelField,
                            calloutLine: {
                                length: 30,
                                width: 3,
                                // color: "red"
                                // specifying 'color' is also possible here
                            }
                        },
                        highlight: true,
                        tooltip: {
                            trackMouse: true,
                            renderer: 'onSeriesTooltipRender'
                        },
                        donut:20
                    }]

                });

                let fields = store.getModel().getFields();
				let cos = [];

				for (let n = 0, len = fields.length; n < len; n++) {
					if (fields[n].type == "number") {
						cos.push({
							text : fields[n].name,
							// width: (0.8 / (len - 1)) * 100 + "%",
							dataIndex : fields[n].name,
							renderer : function(value) {
								return Ext.util.Format.number(value,
										"0,000.00");
							}
						});
					}
					if (fields[n].type == "string") {
						cos
								.push({
									text : fields[n].name,
									// width: (0.8 / (len - 1)) * 100 +
									// "%",
									dataIndex : fields[n].name,
									renderer : function(value) {
										return "<center>" + value
												+ "</center>";
									}
								});
					}
				}
				let grid = Ext.create({
					xtype : "grid",
					store : store,
					columnWidth : config.gridWidth,
					height : height * .8,
					scrollable : true

				});
				grid.setColumns(cos);
				container.add(chart);
				container.add(grid);
				view.add(container);
				
//                container.add(chart);
//                chart.show();
                store.load({
                    params: config.params
                });

            },

                

              
            onPreview: function() {
                if (Ext.isIE8) {
                    Ext.Msg
                        .alert('Unsupported Operation',
                            'This operation requires a newer version of Internet Explorer.');
                    return;
                }
                var chart = this.lookup('chart');
                chart.preview();
            },

            onDataRender: function(v) {
                return Ext.util.Format.number(v,"0,000.00") + '%';
            },

            onSeriesTooltipRender: function(tooltip, record, item) {
                tooltip.setHtml(record.get('产品') + ': ' +
                		Ext.util.Format.number(record.get('占比'),"0,000.00")  + '%');
            }

        });