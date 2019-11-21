Ext.define("jmrc.view.performance.exchange.totaldetailController", {
  extend: "Ext.app.ViewController",
  alias: "controller.performance-exchange-totaldetail",

  afterRender: function() {
    let me = this;
    let view = me.getView();
    let data = view.config.data;
    let start = view
      .up()
      .query("textfield")[2]
      .getValue()
      .replace(/-/g, "");
    let end = view
      .up()
      .query("textfield")[3]
      .getValue()
      .replace(/-/g, "");
    data.start = start;
    data.end = end;
    let updatedata = me.getLastUpdateDate();
    let totalPerformace = me.getTotalExchangePerformance(data);

    Promise.all([updatedata, totalPerformace]).then(function(result) {
      data.updatedate = result[0];
      data.totalexchange = Ext.JSON.decode(result[1])[0].amount;
      me.makeTotalReport(me, view, data);
    });
  },
  makeTotalReport: function(me, view, data) {
    let width = window.innerWidth * 0.65;
    let height = window.innerHeight * 0.8;
    let summypanel = Ext.create({
      xtype: "panel",
      width: width,
      height: height,
     layout:"column",
    });
    let information = Ext.create({
      xtype: "panel",
      width: width * 0.4,
      height: height * 0.4,
      columnWidth:0.3,
      margin: 5,
      html:
        "<center><h3>数据更新日期：<font size=3px color=red>" +
        data.updatedate.substring(0, 10) +
        "</font></h3></center>" +
        "<center><h3>结售汇总量：<font size=3px color=red>" +
        Ext.util.Format.number(data.totalexchange / 10000, "0,000.00") +
        "万美元</font></h3></center>"
    });
    let grid = Ext.create({
      xtype: "grid",
//      width: width * 0.5,
      height: height * 0.4,
      columnWidth:0.7,
      border: 2,
      scrollable: true,
      margin:5,
      columns: [
        { text: "<center>结售汇种类</center>", dataIndex: "product_name" },
        {
          text: "<center>笔数</center>",
          dataIndex: "times",
          renderer: function(v) {
            return Ext.util.Format.number(v, "0,000");
          }
        },
        {
          text: "<center>金额<br>(万美元)</center>",
          dataIndex: "amount",
          renderer: function(v) {
            return Ext.util.Format.number(v / 10000, "0,000.00");
          }
        },
        {
          text: "<center>笔数<br>（同比）</center>",
          dataIndex: "times_compare",
          renderer: function(v) {
            if (v > 0) {
              return (
                "<font color=green>" +
                Ext.util.Format.number(v, "0,000") +
                "</font>"
              );
            } else {
              return (
                "<font color=red>" +
                Ext.util.Format.number(v, "0,000") +
                "</font>"
              );
            }
          }
        },
        {
          text: "<center>金额<br>（同比）</center>",
          dataIndex: "amount_compare",
          renderer: function(v) {
            if (v > 0) {
              return (
                "<font color=green>" +
                Ext.util.Format.number(v / 10000, "0,000.00") +
                "</font>"
              );
            } else {
              return (
                "<font color=red>" +
                Ext.util.Format.number(v / 10000, "0,000.00") +
                "</font>"
              );
            }
          }
        },
        {
          width: 40,
          xtype: "actioncolumn",
          align: "center",
          items: [
            {
              iconCls: "x-fa fa-bar-chart",
              tooltip: "分月明细",
              handler: function(view, rowIndex, colIndex, item, e, record) {
                let start = view
                  .up()
                  .up()
                  .up().config.data.start;
                let end = view
                  .up()
                  .up()
                  .up().config.data.end;
                let product = record.data.product_name;
                view
                  .up()
                  .up()
                  .up()
                  .controller.getProductMonthDetail(product, start, end);
              }
            }
          ]
        },
        {
          width: 40,
          xtype: "actioncolumn",
          align: "center",
          items: [
            {
              iconCls: "x-fa fa-users",
              tooltip: "客户明细",
              handler: function(view, rowIndex, colIndex, item, e, record) {
                let start = view
                  .up()
                  .up()
                  .up().config.data.start;
                let end = view
                  .up()
                  .up()
                  .up().config.data.end;
                let product = record.data.product_name;
                view
                  .up()
                  .up()
                  .up()
                  .controller.getProductClientDetail(product, start, end);
              }
            }
          ]
        },
        {
          width: 40,
          xtype: "actioncolumn",
          align: "center",
          items: [
            {
              iconCls: "x-fa fa-list",
              tooltip: "业务流水",
              handler: function(view, rowIndex, colIndex, item, e, record) {
                let start = view
                  .up()
                  .up()
                  .up().config.data.start;
                let end = view
                  .up()
                  .up()
                  .up().config.data.end;
                let product = record.data.product_name;
                view
                  .up()
                  .up()
                  .up()
                  .controller.getProductDetail(product, start, end);
              }
            }
          ]
        }
      ],
      store: Ext.create("Ext.data.Store", {
        fields: ["product_name", "amount", "times"],

        proxy: {
          url: "/exchange/getTypeTotal",
          type: "ajax"
        }
      })
    });
    
   //取得各项结售汇产品的业务统计
    grid.getStore().load({
      params: {
        start: data.start,
        end: data.end
      }
    });

    /**
     * 分月明细的柱状图
     *   url:"/exchange/getTypeTotalMonth",
     */
    let chart=Ext.create({
    	xtype: 'cartesian',
    	   renderTo: document.body,
    	   width: "100%",
    	   height: 400,
    	   reference: "chart",
    	     tbar: [
    	         {
    	           text: "生成图像",
    	           platformConfig: {
    	             desktop: {
    	               text: "生成图像"
    	             }
    	           },
    	           handler: "onPreview"
    	         }
    	       ],
    	       insetPadding: {
    	            top: 50,
    	            bottom: 40,
    	            left: 20,
    	            right: 40
    	        },
    	    
    
    	   store: {
    	       fields: ['month', 'amount','times'],
    	       proxy:{
        		   url:"/exchange/getTypeTotalMonth",
        		   type:"ajax"
    	       }
    	   },
    	   sprites: {
               type: 'text',
               text: '全行结售汇量分月明细图（'+data.start+"-"+data.end+')',
               fontSize: 26,
               width: 300,
               height: 30,
               x: 240, // the sprite x position
               y: 25  // the sprite y position
           },
    	   axes: [{
    	       type: 'numeric',
    	       position: 'left',
    	       title: {
    	           text: '结售汇（万美元）',
    	           fontSize: 15
    	       },
    	       grid: true,
    	       fields: 'amount'
    	   }, {
    	       type: 'category',
    	       position: 'bottom',
    	       title: {
    	           text: '月份',
    	           fontSize: 15
    	       },
    	       fields: 'month'
    	   }],
    	   series: {
    	       type: 'bar',
    	       subStyle: {
    	           fill: ['#388FAD'],
    	           stroke: '#1F6D91'
    	       },
    	       xField: 'month',
    	       yField: 'amount',
    	       highlight: {
    	           strokeStyle: "light",
    	           fillStyle: "gold"
    	         },
    	         label: {
    	           field: "amount",
    	           display: "insideEnd",
    	           renderer: "onSeriesLabelRender"
    	         },
    	         tooltip: {
    	           trackMouse: true,
    	           renderer: "onTooltipRender"
    	         }
    	   }
    });
    chart.getStore().load({
    	params:{
    		start:data.start,
    		end:data.end
    	}
    });
    
    
    let chartPanel=Ext.create({
    	xtype:"panel",
    	border:2,
    	width:1000,
    	height:400,
    	columnWidth:1,
    	margin:5
    });
    chart.show();
    
    chartPanel.add(chart);

    summypanel.add(information);
    summypanel.add(grid);
    summypanel.add(chartPanel);
    
    view.add(summypanel);
   
    
    
 
  },

  getLastUpdateDate: function() {
    return new Promise(function(resolve, reject) {
      Ext.Ajax.request({
        url: "/settlerecord/getLastUpdateDate",
        success: function(result) {
          resolve(result.responseText);
        }
      });
    });
  },
  getTotalExchangePerformance: function(data) {
    return new Promise(function(resolve, reject) {
      Ext.Ajax.request({
        url: "/exchange/getTotalExchangePerformance",
        params: {
          start: data.start,
          end: data.end
        },
        listeners: {
          beforerequest: function() {
            msgTip = Ext.MessageBox.show({
              title: "提示",
              msg: "信息刷新中,请稍候......"
            });
          },
          requestcomplete: function() {
            msgTip.hide(); // 加载完成，关闭提示框
          }
        },
        success: function(result) {
          resolve(result.responseText);
        }
      });
    });
  },
  
  
  
  onPreview: function() {
      if (Ext.isIE8) {
        Ext.Msg.alert(
          "Unsupported Operation",
          "This operation requires a newer version of Internet Explorer."
        );
        return;
      }
      var chart = this.lookupReference("chart");
      chart.preview();
    },

    onDownload: function() {
      if (Ext.isIE8) {
        Ext.Msg.alert(
          "Unsupported Operation",
          "This operation requires a newer version of Internet Explorer."
        );
        return;
      }
      var chart = this.lookupReference("chart");
      if (Ext.os.is.Desktop) {
        chart.download({
          filename: "Redwood City Climate Data Chart"
        });
      } else {
        chart.preview();
      }
    },
    // 格式化柱形图的数据标签
    onSeriesLabelRender: function(v) {
      return Ext.util.Format.number(v, "0,000");
    },

    onTooltipRender: function(tooltip, record, item) {
      let me = this;
      let view = me.getView();
      let config = view.config.data;
      let perform = Ext.util.Format.number(record.get("amount"), "0,000");
      tooltip.setHtml(
        record.get("month") +
          "业务量为：" +
          perform +
          "万美元,业务笔数为：" +
          record.get("times")
      );
    },
    onAxisLabelRender: function(axis, label, layoutContext) {
      return Ext.util.Format.number(
        layoutContext.renderer(label) / 1000,
        "0,000"
      );
    },
  
  
  
  
  
  
});
