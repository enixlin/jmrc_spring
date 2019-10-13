Ext.define("jmrc.view.performance.chart.BaseBar.BaseBarController", {
  extend: "Ext.app.ViewController",
  alias: "controller.performance-chart-basebar-basebar",

  init: function() {
    let me = this;
    let view = me.getView();
    // 获取配置参数对象
    let config = view.config.data;
    let barChartType = config["barChartType"];

    switch (barChartType) {
      case "timeRangeBarChart":
        this.showTimeRangeBarChart();
      case "unitBarChart":
        this.showProductBarChart();
    }
  },

  showTimeRangeBarChart: function() {
    let me = this;
    let view = me.getView();
    let config = me.getView().config.data;
    view.setLayout(config["layout"]);

    //插入柱状图的框架
    view.add({
      xtype: "cartesian",
      width: window.innerWidth * 0.4,
      height: window.innerHeight * 0.4,
      reference: "chart",
      border: 2,
      margin: 20,
      innerPadding: "50 0 0 10",
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
      ]
    });

    // 取得图型对象，绑定数据集
    let cartesian = view.query("cartesian")[0];
    console.log(cartesian);
    // 设定图表内图型中的标题
    let sprites = {
      type: "text",
      text:
        config["unit"]["name"].replace("（一级支行）", "").replace("本部", "") +
        config["title"],
      fontSize: 15,
      width: 100,
      height: 30,

      x: 100,
      y: 30
    };
    let axes = [
      {
        type: "numeric",
        position: "left",
        title: {
          text: config["ytitle"],
          fontSize: 15
        },
        fields: config["yAxis"],
        //margin:"60 5 5 5 ",
        minimum: 0
      },
      {
        type: "category",
        position: "bottom",
        margin: 10,
        title: {
          text: config["xtitle"],
          fontSize: 12
        },
        fields: config["xAxis"]
      }
    ];
    let series = {
      type: "bar",
      subStyle: {
        fill: ["#abcdef"],
        stroke: "#1F6D91"
      },
      xField: config["xAxis"],
      yField: config["yAxis"],
      yValue: config["yAxis"],
      highlight: {
        strokeStyle: "light",
        fillStyle: "gold"
      },
      label: {
        field: config["yAxis"],
        display: "insideEnd",
        renderer: "onSeriesLabelRender"
      },
      tooltip: {
        trackMouse: true,
        renderer: "onTooltipRender"
      }
    };
    // 加入轴
    cartesian.setAxes(axes);
    // 设定轴的标题
    cartesian.setSeries(series);

    cartesian.setSprites(sprites);
    // 设定bar图的store
    let st = config["st"];
    let bindStore = me.getViewModel().getStore(st);

    // 设定柱型图的数据集
    cartesian.bindStore(bindStore);

    //插入数据明细表格
    view.add({
      xtype: "grid",
      width: window.innerWidth * 0.3,
      height: window.innerHeight * 0.4,
      margin: 20,
      border: 2,
      tbar: [
        {
          text: "保存表格",
          platformConfig: {
            desktop: {
              text: "保存表格"
            }
          },
          handler: "exportExcel"
        }
      ]
    });
    let grid = view.query("grid")[0];
    // 根据数据集的filed数组，确定表格的列
    let fields = bindStore.getModel().getFields();
    let cos = [];
    for (let n = 0, len = fields.length; n < len; n++) {
      if (fields[n].name != "id") {
        if (
          fields[n].type == "number" &&
          fields[n].name.indexOf("笔数") == -1
        ) {
          cos.push({
            text: fields[n].name,
            width: (0.8 / (len - 1)) * 100 + "%",
            dataIndex: fields[n].name,
            renderer: function(value) {
              return Ext.util.Format.number(value, "0,000.00");
            }
          });
        } else if (
          fields[n].type == "number" &&
          fields[n].name.indexOf("笔数") != -1
        ) {
          cos.push({
            text: fields[n].name,
            width: (0.8 / (len - 1)) * 100 + "%",
            dataIndex: fields[n].name,
            renderer: function(value) {
              return Ext.util.Format.number(value, "0,000");
            }
          });
        }

        if (fields[n].type != "number") {
          cos.push({
            text: fields[n].name,
            width: (0.8 / (len - 1)) * 100 + "%",
            dataIndex: fields[n].name
          });
        }
      }
    }
    // 为表格的columns数组添加操作列
    cos.push({
      width: 40,
      xtype: "actioncolumn",
      align: "center",
      items: [
        {
          iconCls: "x-fa fa-pie-chart",
          tooltip: "产品明细",
          text: " 1"
        }
      ]
    });
    // 为表格的columns数组添加操作列
    cos.push({
      width: 40,
      xtype: "actioncolumn",
      align: "center",
      items: [
        {
          iconCls: "x-fa fa-list",
          tooltip: "业务流水"
          // text: " 1"
        }
      ]
    });
    grid.setColumns(cos);
    grid.bindStore(bindStore);
    view.add(grid);

    // 载入数据
    cartesian.getStore(st).load({
      params: {
        start: config["start"],
        end: config["end"],
        unitType: config["unit"]["unitType"],
        name: config["unit"]["name"],
        uid: config["unit"]["code"]
      }
    });

    console.log(cartesian);
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
    let perform = Ext.util.Format.number(record.get(config["yAxis"]), "0,000");
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

  /**
   * 导出表格到EXCEL文件并下载
   */
  exportExcel: function() {
    let me = this;
    let view = me.getView();
    let config = view.config.data;
    let unit = config["unit"];
    let startDay = me.getView()["config"]["data"]["start"];
    let endDay = me.getView()["config"]["data"]["end"];
    let url =
      config["exportUrl"] +
      "?start=" +
      startDay +
      "&end=" +
      endDay +
      "&uid="+
      unit.code+
      "&unitType=unit"+
      "&unit=" +
      JSON.stringify(unit);
    window.open(encodeURI(url));
  },

  showProductBarChart: function() {}
});
