Ext.define("jmrc.view.performance.settle.settleController", {
  extend: "Ext.app.ViewController",
  alias: "controller.performance-settle-settle",

  // 页面渲染后，格式化日期数据，填充当前日期
  afterRender: function() {
    let me = this;
    let view = me.getView();
    let textfield = view.query("textfield");
    textfield[2].setValue("2019-01-01");

    let reportType = textfield[0].getValue();
    let start = textfield[1].getValue();
    let date = new Date();
    let year = date.getFullYear();
    let month = date.getMonth() + 1; // 返回是０-１１,所以要加一
    let day = date.getDate(); // getDay()函数是返回星期几
    // 10以下的数在前面补充零
    month < 10 ? (month = "0" + month) : month;
    day < 10 ? (day = "0" + day) : day;
    let end = textfield[3].setValue(year + "-" + month + "-" + day);
  },

  query: function() {
    let me = this;
    let view = me.getView();
    view.removeAll();
    let textfield = view.query("textfield");
    let reportTypeName = textfield[0].getDisplayValue();
    let reportTypeId = textfield[0].getValue();
    let level = textfield[1].getDisplayValue();
    let start = textfield[2].getValue().replace(/-/g, "");
    let end = textfield[3].getValue().replace(/-/g, "");

    if (reportTypeName == "") {
      alert("请选择分析的类型");
      textfield[0].focus();
      return;
    }
    if (level == "") {
      alert("请选择分析的层次");
      textfield[1].focus();
      return;
    }

    me.makeAnalysis(reportTypeName, level, start, end);

    // eval("me."+reportTypeName + "(view,start,end)");
  },

  makeReport: function(me, reportType, start, end) {
    console.log(reportType + start + end);
    console.log("barChart");
  },


  makeAnalysis: function(reportTypeName, level, start, end) {
    if (reportTypeName == "国际结算分析") {
      if (level == "全行") {
        this.analysis_settle_total(start, end);
      }
      if (level == "经营单位") {
        console.log("unit");
        this.analysis_settle_unit(start, end);
      }
      if (level == "客户") {
          console.log("unit");
          this.analysis_settle_client(start, end);
        }
    }
  },

  /**
   * 显示全行总体国际结算量
   *
   * @param start
   * @param end
   * @returns
   */
  analysis_settle_total: function(start, end) {
    let view = this.getView();
    let bar = Ext.create("jmrc.view.performance.chart.bar.bar", {
      width: window.innerWidth ,
      data: {
        title: "国际结算业务量分月统计图",
        xAxis: "月份",
        yAxis: "业务量(万美元)",
        st: "monthBarStore",
        mapping: {
          name: "月份",
          times: "业务笔数",
          performance: "金额"
        },
        start: start,
        end: end
      }
    });
    view.add(bar);

    let pie = Ext.create("jmrc.view.performance.chart.pie.pie", {
      width: window.innerWidth,
      data: {
        title: "国际结算产品分类统计图",
        st: "getAllBusySettleTypeProformance",
        start: start,
        end: end
      }
    });
    view.add(pie);
  },

  /**
   * 显示全部经营单位国际结算量列表
   *
   * @param start
   * @param end
   * @returns
   */
  analysis_settle_unit: function(start, end) {
    console.log("unit inside");
    let view = this.getView();
    console.log(view);
    let grid = {
      xtype: "recordDetailGrid",
      width:"100%",
     // layout:"fit",
      scrollable:true,
      data: {
          st: "allUnitPerformanceStore",
          start: start,
          end: end
        }
    };
    view.add(grid);
  },
  /**
   * 显示全部客户国际结算量列表
   *
   * @param start
   * @param end
   * @returns
   */
  analysis_settle_client: function(start, end) {
    console.log("client inside");
    let view = this.getView();
    console.log(view);
    let grid = {
      xtype: "clientdetail",
      width:"100%",
      //layout:"fit",
      scrollable:true,
      data: {
          st: "allClientPerformanceStore",
          start: start,
          end: end
        }
    };
    view.add(grid);
  }
});
