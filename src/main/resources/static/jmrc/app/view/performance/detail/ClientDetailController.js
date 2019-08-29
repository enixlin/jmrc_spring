Ext.define("jmrc.view.performance.detail.ClientDetailController", {
  extend: "Ext.app.ViewController",
  alias: "controller.performance-detail-clientdetail",

  afterRender: function() {
    let me = this;
    let view = me.getView();
    let startDay = me.getView()["config"]["data"]["start"];
    let endDay = me.getView()["config"]["data"]["end"];
    let st = me.getView()["config"]["data"]["st"];
    let bindStore = me.getViewModel().getStore(st);
    let grid = Ext.create("Ext.grid.Panel", {
      width: window.innerWidth * 0.8,
      height: window.innerHeight * 0.65,
      plugins: "gridfilters",
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
        clientType: "c"
      },
      callback: function(records, options, success) {
        // 根据数据集的filed数组，确定表格的列
        let fields = bindStore.getModel().getFields();
        let cos = []; // 表格列表

        // 现在开始历遍store的所有字段
        for (let n = 0, len = fields.length; n < len; n++) {
          //跳过id 和xuhao
          // if (fields[n].name == "id") {
          //   continue;
          // }
          // if (fields[n].name == "xuhao") {
          //   continue;
          // }
          //选从字段的数据类型开始分类：字符类和数字类
          //一、字符类
          if (fields[n].type == "string") {
            if (fields[n].name == "<center>机构号</center>") {
              cos.push({
                header: fields[n].name,
                width: 80,
                sortable: true,
                dataIndex: fields[n].dataIndex,
                filter: {
                  type: "string"
                }
              });
              continue;
            }
            if (fields[n].name == "<center>机构名称</center>") {
              cos.push({
                header: fields[n].name,
                width: 100,
                sortable: true,
                dataIndex: fields[n].dataIndex,
                renderer:function(value){return value.replace("（一级支行）","").replace("本部","")},
                filter: {
                  type: "string"
                }
              });
              continue;
            }
            if (fields[n].name == "<center>客户号</center>") {
              cos.push({
                header: fields[n].name,
                width: 80,
                sortable: true,
                dataIndex: fields[n].dataIndex,
                filter: {
                  type: "string"
                }
              });
              continue;
            }
            if (fields[n].name == "<center>客户名称</center>") {
              cos.push({
                header: fields[n].name,
                width: 250,
                sortable: true,
                dataIndex: fields[n].dataIndex,
                filter: {
                  type: "string"
                }
              });
              continue;
            }
          }

          //二、数字类
          if (fields[n].type == "number") {
            if (fields[n].name.indexOf("金额") != -1) {
              if (fields[n].name.indexOf("同比") != -1) {
                cos.push({
                  header: fields[n].name,
                  sortable: true,
                  width: 80,
                  dataIndex: fields[n].dataIndex,
                  renderer: function(value) {
                    if (value < 0) {
                      return (
                        "<font color=blue></font><span style='color:red;'>" +
                        Ext.util.Format.number(value, "0,000.00") +
                        "</font>"
                      );
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
                  width: 80,
                  dataIndex: fields[n].dataIndex,
                  renderer: function(value) {
                    return Ext.util.Format.number(value, "0,000.00");
                  }
                });
              }
            } else {
              //这个if判断的是笔数
              if (fields[n].name.indexOf("同比") != -1) {
                cos.push({
                  header: fields[n].name,
                  width: 60,
                  sortable: true,
                  dataIndex: fields[n].dataIndex,
                  renderer: function(value) {
                    if (value < 0) {
                      return (
                        "<font color=blue></font><span style='color:red;'>" +
                        Ext.util.Format.number(value, "0,000") +
                        "</font>"
                      );
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
                  sortable: true,
                  width: 60,
                  dataIndex: fields[n].dataIndex,
                  renderer: function(value) {
                    return Ext.util.Format.number(value, "0,000");
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
                iconCls: "x-fa fa-list",
                tooltip: "业务流水",
               // text: " 1"
              }
            ]
          });

        grid.setColumns(cos);
        msgTip.hide(); // 加载完成，关闭提示框
        console.log("done");
      }
    });
  },




  //导出表格
  exportExcel:function(){
      let me=this;
      let store=me.getViewModel().getStore("allClientPerformanceStore");
      console.log(store);
      
  }
});
