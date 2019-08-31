Ext.define("jmrc.view.performance.detail.detailController", {
  extend: "Ext.app.ViewController",
  alias: "controller.performance-detail-detail",

  afterRender: function() {
    let me = this;
    let view = me.getView();
    let startDay = me.getView()["config"]["data"]["start"];
    let endDay = me.getView()["config"]["data"]["end"];
    let st = me.getView()["config"]["data"]["st"];
    let bindStore = me.getViewModel().getStore(st);
    let grid = Ext.create("Ext.grid.Panel", {
      width: window.innerWidth*0.8,
      
      height: window.innerHeight*0.65,
     border:2,
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
        unitType: "经营单位",
      },
      callback: function(records, options, success) {
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
              renderer: function(value) {
              
                  if (value >= 100) {
                    return (
                      "<font color=green></font><span style='color:green;'>" +
                      Ext.util.Format.number(value, "0,000.00")  +
                      "%</font>"
                    );
                  } else if (value < 50) {
                    return (
                      "<font color=red></font><span style='color:red;'>" +
                      Ext.util.Format.number(value, "0,000.00")  +
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
                width:120,
                sortable: true,
                dataIndex: fields[n].dataIndex,
                renderer: function(value) {
                  return value.replace("（一级支行）", "").replace("本部", "");
                }
              });
            } else {
              cos.push({
                header: fields[n].name,
                width:80,
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
                  renderer: function(value) {
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
                  width:80,
                  sortable: true,
                  dataIndex: fields[n].dataIndex,
                  renderer: function(value) {
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
                  renderer: function(value) {
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
                  renderer: function(value) {
                    return Ext.util.Format.number(value, "0,000.00");
                  }
                });
              }
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
              iconCls: "x-fa fa-bar-chart",
              text: " 1",
              tooltip: "分月明细",
              handler: function(view, rowIndex, colIndex, item, e, record) {
                let unit = {
                  name: record.data.branchName,
                  id: record.data.branchCode
                };
                let start = view.up().up()["config"]["data"]["start"];
                let end = view.up().up()["config"]["data"]["end"];
                console.log(
                  view
                    .up()
                    .up()
                    .controller.showUnitMonthBarChart(unit, start, end)
                );
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
              iconCls: "x-fa fa-users",
              tooltip: "客户明细",
              text: " 1"
            }
          ]
        });

        grid.setColumns(cos);
        msgTip.hide(); // 加载完成，关闭提示框
      }
    });
  },

  showUnitMonthBarChart: function(unit, start, end) {
    let view = this.getView();
    let win = Ext.create("Ext.window.Window", {
      width: window.innerWidth*0.6,
      height: window.innerHeight*0.7,
      maximizable: true,
      scrollable: true
    });
    view.add(win);
    win.show();
    let bar = Ext.create("jmrc.view.performance.chart.bar.bar", {
      width: window.innerWidth * 0.5,
      data: {
        title: unit.name.replace("(一级支行)", "").replace("本部","") + "国际结算量分月明细",
        st: "unitMonthBarStore",
        name: unit.name,
        uid: unit.id,
        unitType: "经营单位",
        start: start,
        end: end
      }
    });
    win.add(bar);
  },
  
  exportExcel:function(){
	    let me = this;
	    let view = me.getView();
	    let startDay = me.getView()["config"]["data"]["start"];
	    let endDay = me.getView()["config"]["data"]["end"];
	    let url="/settlerecord/exportAllUnitPerformance?start="+startDay+"&end="+endDay+"&clientType=c";
	    window.open(url);
  }
});
