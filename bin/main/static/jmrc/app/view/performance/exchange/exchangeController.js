Ext.define('jmrc.view.performance.exchange.exchangeController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.performance-exchange-exchange',
    
    
    afterRender:function(){
    	// 页面渲染后，格式化日期数据，填充当前日期
        let me = this;
        let view = me.getView();
        let textfield = view.query("textfield");
        let reportType = textfield[0].getValue();
        let date = new Date();
        let year = date.getFullYear();
        let month = date.getMonth() + 1; // 返回是０-１１,所以要加一
        let day = date.getDate(); // getDay()函数是返回星期几
        // 10以下的数在前面补充零
        month < 10 ? (month = "0" + month) : month;
        day < 10 ? (day = "0" + day) : day;
        let end = textfield[3].setValue(year + "-" + month + "-" + day);
        let start = textfield[2].setValue(year + "-01-01" );
    },
    
    
    
    query:function(){
    	//清除exchange页面上的已有view,但保留工具条
    	  let me = this;
          let view = me.getView();
          view.removeAll();
          //从工具条上获取查询参数
          let textfield = view.query("textfield");
          let reportTypeName = textfield[0].getDisplayValue();
          let reportTypeId = textfield[0].getValue();
          let level = textfield[1].getDisplayValue();
          let start = textfield[2].getValue().replace(/-/g, "");
          let end = textfield[3].getValue().replace(/-/g, "");
          
     
          if (level == "") {
              alert("请选择分析的层次");
              textfield[1].focus();
              return;
          }

          me.makeAnalysis(reportTypeName, level, start, end);
          
    },
    
    makeAnalysis:function(reportTypeName, level, start, end){
//    	alert("makeAnalysis");
        if (level == "全行") {
            this.analysis_settle("exchangetotaldetail", start, end);
        }
        if (level == "经营单位") {
            console.log("unit");
            this.analysis_settle("exchangeunitdetail", start, end);
        }
        if (level == "客户") {
            console.log("unit");
            this.analysis_settle("exchangeclientdetail", start, end);
        }
    },
    
    analysis_settle: function(level, start, end) {
        let view = this.getView();
        let grid = {
            xtype: level,
            width: window.innerWidth*.7,
            scrollable: true,
            margin:20,
            data: {
                start: start,
                end: end
            }
        };
        view.add(grid);
    },
    
    
    
    
    

});
