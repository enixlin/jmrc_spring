Ext.define("jmrc.view.performance.detail.detailController", {
  extend: "Ext.app.ViewController",
  alias: "controller.performance-detail-detail",

  afterRender: function () {
    let me = this;
    let view = me.getView();
    let startDay = me.getView()["config"]["data"]["start"];
    let endDay = me.getView()["config"]["data"]["end"];
    let st = me.getView()["config"]["data"]["st"];
    let bindStore = me.getViewModel().getStore(st);
    let grid = Ext.create("Ext.grid.Panel", {
      width: window.innerWidth * 0.8,

      height: window.innerHeight * 0.65,
      border: 2,
      scrollable: true,
      listeners: {}
    });
    grid.bindStore(bindStore);

    view.add(grid);

    // 载入数据
    grid.getStore(st).load({
      params: {
        start: startDay,
        end: endDay,
        unitType: "经营单位"
      },
      callback: function (records, options, success) {
        // 根据数据集的filed数组，确定表格的列
        let fields = bindStore.getModel().getFields();
        let cos = []; // 表格列表
        for (let n = 0, len = fields.length; n < len; n++) {
          if (fields[n].name == "id") {
            continue;
          }
          if (
            fields[n].name == "<center>完成率<br>（全年）</center>" ||
            fields[n].name == "<center>完成率<br>（季度）</center>"
          ) {
            cos.push({
              header: fields[n].name,
              sortable: true,
              dataIndex: fields[n].dataIndex,
              renderer: function (value) {
                if (value >= 100) {
                  return (
                    "<font color=green></font><span style='color:green;'>" +
                    Ext.util.Format.number(value, "0,000.00") +
                    "%</font>"
                  );
                } else if (value < 50) {
                  return (
                    "<font color=red></font><span style='color:red;'>" +
                    Ext.util.Format.number(value, "0,000.00") +
                    "%</font>"
                  );
                } else {
                  return (
                    "<font color=blue></font><span style='color:blue;'>" +
                    Ext.util.Format.number(value, "0,000.00") +
                    "%</font>"
                  );
                }
              }
            });

            continue;
          }
          if (fields[n].type != "number") {
            if (fields[n].name == "<center>机构名称</center>") {
              cos.push({
                header: fields[n].name,
                width: 120,
                sortable: true,
                dataIndex: fields[n].dataIndex,
                renderer: function (value) {
                  return value.replace("（一级支行）", "").replace("本部", "");
                }
              });
            } else {
              cos.push({
                header: fields[n].name,
                width: 80,
                sortable: true,
                dataIndex: fields[n].dataIndex
              });
            }
          }
          if (fields[n].type == "number") {
            if (new String(fields[n].name).indexOf("金额")) {
              if (fields[n].name.indexOf("同比") != -1) {
                cos.push({
                  header: fields[n].name,
                  sortable: true,

                  dataIndex: fields[n].dataIndex,
                  renderer: function (value) {
                    if (value < 0) {
                      return (
                        "<font color=blue></font><span style='color:red;'>" +
                        Ext.util.Format.number(value, "0,000") +
                        "</font>"
                      );
                      ("");
                    } else {
                      return (
                        "<font color=blue></font><span style='color:green;'>" +
                        Ext.util.Format.number(value, "0,000") +
                        "</font>"
                      );
                    }
                  }
                });
              } else {
                cos.push({
                  header: fields[n].name,
                  width: 80,
                  sortable: true,
                  dataIndex: fields[n].dataIndex,
                  renderer: function (value) {
                    return Ext.util.Format.number(value, "0,000");
                  }
                });
              }
            } else {
              if (fields[n].name.indexOf("同比") != -1) {
                cos.push({
                  header: fields[n].name,

                  sortable: true,
                  dataIndex: fields[n].dataIndex,
                  renderer: function (value) {
                    if (value < 0) {
                      return (
                        "<font color=blue></font><span style='color:red;'>" +
                        Ext.util.Format.number(value, "0,000.00") +
                        "</font>"
                      );
                      ("");
                    } else {
                      return (
                        "<font color=blue></font><span style='color:green;'>" +
                        Ext.util.Format.number(value, "0,000.00") +
                        "</font>"
                      );
                    }
                  }
                });
              } else {
                cos.push({
                  header: fields[n].name,

                  sortable: true,
                  dataIndex: fields[n].dataIndex,
                  renderer: function (value) {
                    return Ext.util.Format.number(value, "0,000.00");
                  }
                });
              }
            }
          }
        };
        // 为表格的columns数组添加操作列
        cos.push({
          width: 40,
          xtype: "actioncolumn",
          align: "center",
          items: [
            {
              iconCls: "x-fa fa-bar-chart",
              text: " 1",
              tooltip: "分月明细",
              handler: function (view, rowIndex, colIndex, item, e, record) {
                let unit = {
                  name: record.data["行名"],
                  id: record.data["行号"]
                };
                let start = view.up().up()["config"]["data"]["start"];
                let end = view.up().up()["config"]["data"]["end"];

                view
                  .up()
                  .up()
                  .controller.showUnitMonthBarChart(unit, start, end);
              }

              // handler:function(){alert("分月明细")}
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
              iconCls: "x-fa fa-pie-chart",
              tooltip: "产品明细",
              text: " 1",
              handler: function (view, rowIndex, colIndex, item, e, record) {
                let unit = {
                  name: record.data["行名"],
                  id: record.data["行号"]
                };
                let start = view.up().up()["config"]["data"]["start"];
                let end = view.up().up()["config"]["data"]["end"];

                view
                  .up()
                  .up()
                  .controller.showProductPieChart(unit, start, end);
              }
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
              iconCls: "x-fa fa-users",
              tooltip: "客户明细",
              text: " 1",
              handler: function (view, rowIndex, colIndex, item, e, record) {
                let unit = {
                  name: record.data["行名"],
                  id: record.data["行号"]
                };
                let start = view.up().up()["config"]["data"]["start"];
                let end = view.up().up()["config"]["data"]["end"];

                view
                  .up()
                  .up()
                  .controller.showUnitClientList(unit, start, end);
              }
            }
          ]
        });

        grid.setColumns(cos);
        msgTip.hide(); // 加载完成，关闭提示框
      }
    });
  },

  showUnitMonthBarChart: function (unit, start, end) {
    let me = this;
    let view = me.getView();

    let win = Ext.create("Ext.window.Window", {
      width: window.innerWidth * 0.8,
      height: window.innerHeight * 0.5
    });
    //测试的分月明细
    let chart = {
      xtype: "basebar",
      width: window.innerWidth * 0.8,
      //layout:"fit",
      scrollable: true,
      data: {
        st: "UnitSettleMonthPerformanceStore",
        //图表布局
        layout: "hbox",
        //图表的宽度
        width: 400,
        //图表的高度
        height: 500,
        //图表的标题
        title: "分月国际结算量统计表\r\n \t\t\t（单位：万美元）",
        xtitle: "月份",
        ytitle: "国际结算业务量",
        //横轴绑定的字段
        xAxis: "<center>月份</center>",
        //竖轴绑定的字段
        yAxis: "<center>金额</center>",
        //柱状图的类型：分产品柱状图，分时柱状图
        barChartType: "timeRangeBarChart",
        exportUrl: "/settlerecord/exportUnitMonthPerformace",
        //柱状图显示的数据对象：全行、经营单位、客户、产品
        //对象的结构如下
        unit: { name: unit.name, code: unit.id, unitType: "unit" },
        start: start,
        end: end
      }
    };
    win.add(chart);
    view.add(win);
    win.show();
  },

  showProductPieChart: function (unit, start, end) {
    let me = this;
    let view = me.getView();

    let win = Ext.create("Ext.window.Window", {
      width: window.innerWidth * 0.8,
      height: window.innerHeight * 0.7,

    });
    //测试的产品分类明细
    let chart = {
      xtype: "basepie",
      width: window.innerWidth * 0.8,

      scrollable: true,
      data: {
        st: "UnitProductPerformanceStore",
        //图表布局
        layout: "hbox",
        //图表的宽度
        width: window.innerWidth * 0.4,
        //图表的高度
        height: window.innerHeight * 0.4,
        //图表的标题
        title: "产品业务量表\r\n \t\t\t（单位：万美元）",
        xtitle: "产品",
        ytitle: "国际结算产品业务量",
        //横轴绑定的字段
        xAxis: "产品",
        //竖轴绑定的字段
        yAxis: "金额",
        //柱状图的类型：分产品柱状图，分时柱状图
        pieChartType: "productPieChart",
        exportUrl: "/settlerecord/exportProductPieChart",
        //柱状图显示的数据对象：全行、经营单位、客户、产品
        //对象的结构如下
        unit: { name: unit.name, code: unit.id, unitType: "unit" },
        start: start,
        end: end
      }
    };
    win.add(chart);
    view.add(win);
    win.show();
  },

  showUnitClientList: function (unit, start, end) {
    let me = this;
    let view = me.getView();
    let startDay = me.getView()["config"]["data"]["start"];
    let endDay = me.getView()["config"]["data"]["end"];
    let st = me.getView()["config"]["data"]["st"];
    console.log(st);
    let bindStore = me.getViewModel().getStore(st);
    let grid = Ext.create("Ext.grid.Panel", {
      width: window.innerWidth * 0.8,
      height: window.innerHeight * 0.65,
      border: 2,
      scrollable: true,
      listeners: {}
    });

    let win = Ext.create("Ext.window.Window", {
      width: window.innerWidth * 0.8,
      height: window.innerHeight * 0.5
    });
    grid.bindStore(bindStore);
    win.add(grid);
    view.add(win);
    win.show();
  },






  /**
   * 导出表格到EXCEL文件并下载
   */
  exportExcel: function () {
    let me = this;
    let view = me.getView();
    let startDay = me.getView()["config"]["data"]["start"];
    let endDay = me.getView()["config"]["data"]["end"];
    let url =
      "/settlerecord/exportAllUnitPerformance?start=" +
      startDay +
      "&end=" +
      endDay +
      "&clientType=c";
    window.open(url);
  }
});
