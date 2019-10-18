Ext.define("jmrc.view.performance.settle.settleController", {
    extend: "Ext.app.ViewController",
    alias: "controller.performance-settle-settle",

    // 页面渲染后，格式化日期数据，填充当前日期
    afterRender: function() {
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
        // alert("info");
        me.showHelpInformation(me);
    },

    query: function() {
    	//alert("query");
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

    },


    makeAnalysis: function(reportTypeName, level, start, end) {
        if (reportTypeName == "国际结算分析") {
            if (level == "全行") {
                this.analysis_settle("totaldetail", start, end);
            }
            if (level == "经营单位") {
                console.log("unit");
                this.analysis_settle("unitdetail", start, end);
            }
            if (level == "客户") {
                console.log("unit");
                this.analysis_settle("clientdetail", start, end);
            }
        }
    },


    analysis_settle: function(level, start, end) {

        let view = this.getView();
        console.log(view);
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

    showHelpInformation: function(scope) {
        // alert("info");
        let panel = Ext.create("Ext.panel.Panel", {
            width: 700,
            height: 500,
            layout: "fit",
            padding: 10,
            margin: 20,
            // border: 2,
            html: "<b>说明</b><br>可以从不同的层次分析国际业务量情况,主题分为:<br><b>国际结算量<b><br><b>结售汇量(开发中。。。)<b><br><b>贸易融资量(开发中。。。)<b><br><b>中间业务收入(开发中。。。)<b><br><b>外币存款量(开发中。。。)</b>" +
                "<br> 每一个分析主题下,都可以从全行,经营单位,客户,产品等四个层次进行分析" +
                "<br><br>使用帮助:<br><br> <li> 第一步:选择 [ 分析主题 ]<br> <li>第二步:选择 [ 分析层次 ]<br> <li>第三步:确定 [ 开始和结束日期 ]<br> <li>第四步:点击 [ 查询 ]",
            renderTo: Ext.getBody()
        });
        scope.getView().add(panel);
    }


});