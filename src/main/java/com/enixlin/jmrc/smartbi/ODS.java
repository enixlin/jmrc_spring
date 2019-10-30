package com.enixlin.jmrc.smartbi;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ODS {

	private JsonArray columns;

	public ODS() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 查询所有的贸易融资业务(从贷款中间表)
	 * 
	 * @param queryDate1 格式： "2019-05-01"
	 * @param queryDate2 格式： "2019年5月1日"
	 * @param ExportNum  单次最大提取记录数，建议不要大于5000笔每/次
	 * @return
	 */
	public JsonArray getAllFTRecordFromMiddleTable(String queryDate1,
			String queryDate2, String ExportNum) {
		NetService ns = new NetService();

		ns.createHttpClient();

		String url_query = "http://110.0.170.88:9083/smartbi/vision/RMIServlet?debug=true";
		Map<String, String> map = new HashMap<String, String>();
		String encoding = "utf8";
		String result = "";

		/**
		 * 用户登录，传入用户编号和密码 返回的结果是用户信息，是否已登录等内容
		 */
		map.clear();
		map.put("className", "CompositeService");
		map.put("methodName", "compositeLogin");
		map.put("params", "[\"32311\",\"123\"]");
		result = ns.HttpPost(url_query, map, encoding);

		// 打开贷款报表查询
		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementById");
		map.put("params", "[Iee801fbd037361f4014688c9cb301d4a]");
		result = ns.HttpPost(url_query, map, encoding);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "isCatalogElementAccessible");
		map.put("params", "[\"Iee801fbd037361f4014688c9cb301d4a\",\"WRITE\"]");
		result = ns.HttpPost(url_query, map, encoding);

		String urlString = "http://110.0.170.88:9083/smartbi/vision/QueryView.jsp?queryId=Iee801fbd037361f4014688c9cb301d4a&browserType=chrome";
		String temp = ns.HttpGet(urlString);
		int start = temp.indexOf("clientId=") + 10;
		String ClientId = temp.substring(start, start + 41);

		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "setAndGetDefaultParamValuesWithFuture");
		map.put("params", "[" + ClientId + "]");
		result = ns.HttpPost(url_query, map, encoding);
		JsonService js = new JsonService(result);
		JsonArray jo = js.getJsonArray("result");
		String param_struction = jo.get(0).getAsJsonObject().get("id")
				.toString();
		String param_date = jo.get(1).getAsJsonObject().get("id").toString();
		// String param_date =
		// jo.getAsJsonArray().get(1).getAsJsonObject().get("id").toString();

		map.clear();
		map.put("className", "ConfigClientService");
		map.put("methodName", "getSystemConfig");
		map.put("params", "[REPORT_BROWSE_AUTO_REFRESH]");
		result = ns.HttpPost(url_query, map, encoding);

		map.clear();
		map.put("className", "CompositeService");
		map.put("methodName", "setParamValuesWithRelated");
		map.put("params", "[" + ClientId + "," + param_date + "," + queryDate1
				+ "," + queryDate2 + " ]");
		result = ns.HttpPost(url_query, map, encoding);

		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "setRowsPerPage");
		map.put("params", "[" + ClientId + "," + ExportNum + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "getTotalRowsCountWithFuture");
		map.put("params", "[" + ClientId + ",0]");
		result = ns.HttpPost(url_query, map, encoding);
		js = new JsonService(result);
		JsonObject objResult = js.getJsonObject();

		int recordCount = objResult.get("result").getAsInt();
		System.out.println("中间表的总记录数：" + result);
		int round = recordCount / Integer.parseInt(ExportNum);
		System.out.println(round);
		System.out.println("需要提交请求的次数：" + round);

		JsonArray fixedArray = new JsonArray();
		for (int i = 0, len = round; i < len; i++) {
			map.clear();
			map.put("className", "ClientReportService");
			map.put("methodName", "getReportDataWithFuture");
			map.put("params", "[" + ClientId + "," + i + "]");
			result = ns.HttpPost(url_query, map, encoding);
			js = new JsonService(result);
			JsonObject objRusult = js.getJsonObject();
			JsonArray array_result = objRusult.get("result").getAsJsonArray()
					.get(0).getAsJsonArray();
			// System.out.println("");
			// 提取贸易融资、和出口退税、保函、信用证等业务,按科目提取

			

//			科目号	科目名称
//			1050101	即期信用证
//			1050201	远期信用证
//			1110101	应收即期信用证出口款项
//			1120101	跟单托收付款交单
//			1140101	信用证通知
//			13040301	外汇流贷
//			13070101	进口押汇
//			13070201	出口商业发票融资
//			13070301	打包贷款
//			13079901	出口信用保险贷款
//			9999999	配平表外业务
//			13040301 
			for (int j = 0, len1 = array_result.size(); j < len1; j++) {
				String subject = array_result.get(j).getAsJsonArray().get(21)
						.getAsString();
				String sub_type = array_result.get(j).getAsJsonArray().get(128)
						.getAsString();
				String currency = array_result.get(j).getAsJsonArray().get(88)
						.getAsString();
				if (subject.equals("0105") ||
						subject.equals("1050201") ||
						subject.equals("1110101") ||
						subject.equals("1120101") ||
						subject.equals("1140101") ||
						(subject.equals("13040301") && !currency.equals("CNY")) ||
						subject.equals("13070101") ||
						subject.equals("13070201") ||
						subject.equals("13070301") ||
						subject.equals("13079901") ||
						(subject.equals("13040301")
								&& sub_type.equals("0749-“退税贷”出口退税应收款融资"))

				) {
					fixedArray.add(array_result.get(j));
				}
			}
			System.out.println("第："+i +"次请求完成");

		}

		return fixedArray;

	}

	/**
	 * 查询所有的贸易融资业务
	 * 
	 * @param queryDate1 格式： "2019-05-01"
	 * @param queryDate2 格式： "2019年5月1日"
	 * @param ExportNum
	 * @return
	 */
	public JsonArray getAllFTRecord(String queryDate1, String queryDate2,
			String ExportNum) {
		NetService ns = new NetService();

		ns.createHttpClient();

		String url_query = "http://110.0.170.88:9083/smartbi/vision/RMIServlet?debug=true";
		Map<String, String> map = new HashMap<String, String>();
		String encoding = "utf8";
		String result = "";

		/**
		 * 用户登录，传入用户编号和密码 返回的结果是用户信息，是否已登录等内容
		 */
		map.clear();
		map.put("className", "CompositeService");
		map.put("methodName", "compositeLogin");
		map.put("params", "[\"32311\",\"123\"]");
		result = ns.HttpPost(url_query, map, encoding);

		// 打开贷款报表查询
		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementById");
		map.put("params", "[Iee80208b016d87498749b23f016e01ff8c151208]");
		result = ns.HttpPost(url_query, map, encoding);

		// 打开组合查询
		map.clear();
		map.put("className", "CombinedQueryService");
		map.put("methodName", "openCombinedQuery");
		map.put("params", "[ Iee80208b016d87498749b23f016e01ff8c151208,null ]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		JsonService js = new JsonService(result);
		JsonArray ja = js.getJsonArray("result");
		// 返回的查询字段
		JsonArray ja_BIZATTR = (JsonArray) js.getJsonArray("result").get(2);
		JsonArray ja_filter = (JsonArray) js.getJsonArray("result").get(3);
		String reportID1 = ja.get(0).toString();
		String reportID2 = ja.get(1).toString();
		String reportID3 = ja.get(8).toString();
		String BizTheme = ja.get(4).toString();

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[ Iee80208b016d87498749b23f016e01ff8c151208]");
		result = ns.HttpPost(url_query, map, encoding);

		map.clear();
		map.put("className", "CombinedQueryService");
		map.put("methodName", "createSimpleReport");
		map.put("params", "[" + reportID1 + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		js = new JsonService(result);
		JsonObject jo = js.getJsonObject("result");
		String clientId = jo.get("clientId").toString();
		String parameterPanelId = jo.get("parameterPanelId").toString();

		// BizViewOutField= jo.getAsJsonObject("reportBean");
		JsonObject reportBean = jo.getAsJsonObject("reportBean");
		String clientConfig = reportBean.get("clientConfig").getAsString();

		JsonService jss = new JsonService(clientConfig);
		JsonObject BizViewOutField = jss.getJsonObject().get("gridProp")
				.getAsJsonObject().get("fieldProps").getAsJsonObject();
		JsonArray opParameter = jss.getJsonObject().get("paramSetting")
				.getAsJsonObject().get("applyDefaultValueParams")
				.getAsJsonArray();
		String tf_type = opParameter.get(0).toString();
		String date = opParameter.get(2).toString();
//		String StartDay = opParameter.get(0).toString();
//		String EndDay = opParameter.get(1).toString();

		map.clear();
		map.put("className", "CombinedQueryService");
		map.put("methodName", "getLocalFilterElements");
		map.put("params", "[" + reportID1 + "]");
		result = ns.HttpPost(url_query, map, encoding);

		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "getFunctionValue");
		map.put("params", "[ " + clientId + ",CurrentReportName()]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(reportID1);
		// System.out.println(clientId);
		// System.out.println(result);

		map.clear();
		map.put("className", "CombinedQueryService");
		map.put("methodName", "initFromBizViewEx");
		map.put("params",
				"[" + reportID1 + "," + clientId + "," + reportID2 + ",true]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CombinedQueryService");
		map.put("methodName", "setSimpleReportClientId");
		map.put("params", "[" + reportID1 + "," + clientId + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "ConfigClientService");
		map.put("methodName", "getSystemConfig");
		map.put("params", "[REPORT_BROWSE_AUTO_REFRESH]");
		result = ns.HttpPost(url_query, map, encoding);

		/**
		 * 设定数据日期
		 */
		map.clear();
		map.put("className", "CompositeService");
		map.put("methodName", "setParamValuesWithRelated");
		map.put("params", "[" + parameterPanelId + "," + date + "," + queryDate1
				+ "," + queryDate2 + " ]");
		result = ns.HttpPost(url_query, map, encoding);

		/**
		 * 设定贷款产品
		 */
//		className:CompositeService
//		methodName:setParamValuesWithRelated
//		params:[
//		"Iee80208b016d87498749b23f016e022cc80f1ad4",
//		"OutputParameter.Iee80208b016d87498749b23f016e022cc76f1ab3.p_sa_lon_acct_info_type",
//		"公司类贷款(不含票据贴现),贸易融资","公司类贷款(不含票据贴现),贸易融资"]
		map.clear();
		map.put("className", "CompositeService");
		map.put("methodName", "setParamValuesWithRelated");
		map.put("params", "[" + parameterPanelId + "," + tf_type
				+ ",'公司类贷款(不含票据贴现),贸易融资','公司类贷款(不含票据贴现),贸易融资' ]");
		result = ns.HttpPost(url_query, map, encoding);
		System.out.println("设定参数");

//		map.clear();
//		map.put("className", "CatalogService");
//		map.put("methodName", "isCatalogElementAccessible");
//		map.put("params", "[\"Iee801fbd037361f4014688c9cb301d4a\",\"WRITE\"]");
//		result = ns.HttpPost(url_query, map, encoding);

		/*
		 * 以下是报表输出
		 * 
		 */

		// System.out.println("reportID1 is:" + reportID1);
		// System.out.println("reportID2 is:" + reportID2);
		// System.out.println("clientId is" + clientId);
		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "clearSQLResultStore");
		map.put("params", "[" + clientId + "]");
		result = ns.HttpPost(url_query, map, encoding);

		// System.out.println("clean sql");
		// System.out.println(result);

		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "setRowsPerPage");
		map.put("params", "[" + clientId + ",200000]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "getTotalRowsCountWithFuture");
		map.put("params", "[" + clientId + ",0]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "getReportDataWithFuture");
		map.put("params", "[" + clientId + ",0]");
		result = ns.HttpPost(url_query, map, encoding);
		js = new JsonService(result);
		JsonObject objRusult = js.getJsonObject();
		JsonArray array_result = objRusult.get("result").getAsJsonArray().get(0)
				.getAsJsonArray();
		System.out.println("result array size is:" + array_result.size());
		System.out.println("result to object");

		return array_result;

	}

	/**
	 * 取得所有的国际业务流水
	 * 
	 * @param startDayNum 格式： "2019-05-01"
	 * @param StarDayChn  格式： "2019年5月1日"
	 * @param endDayNum   格式： "2019-05-10"
	 * @param endDayChn   格式： "2019年5月10日"
	 * @param ExportNum   返回的最大记录数
	 * @return 国际业务流水记录的数组
	 */
	public JsonArray getAllSettleRecord(String startDayNum, String startDayChn,
			String endDayNum, String endDayChn,
			String ExportNum) {

		NetService ns = new NetService();

		ns.createHttpClient();

		String reportID1 = "";
		String reportID2 = "";
		String reportID3 = "";
		String clientId = "";
		String parameterPanelId = "";
		String url_query = "http://110.0.170.88:9083/smartbi/vision/RMIServlet?debug=true";
		Map<String, String> map = new HashMap<String, String>();
		String encoding = "utf8";
		String result = "";
		String BizTheme = "";
		JsonArray ja_BIZATTR;
		JsonArray ja_filter;
		JsonObject BizViewOutField;

		/**
		 * 用户登录，传入用户编号和密码 返回的结果是用户信息，是否已登录等内容
		 */
		map.clear();
		map.put("className", "CompositeService");
		map.put("methodName", "compositeLogin");
		map.put("params", "[\"32311\",\"123\"]");
		result = ns.HttpPost(url_query, map, encoding);

		// 打开自助分析中的国际业务
		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementById");
		map.put("params", "[Iee801fbd227e43eb01583d989ca32e84]");
		result = ns.HttpPost(url_query, map, encoding);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementById");
		map.put("params", "[Iee801fbd227e43eb01583d989ca32e84]");
		result = ns.HttpPost(url_query, map, encoding);

		// 打开组合查询
		map.clear();
		map.put("className", "CombinedQueryService");
		map.put("methodName", "openCombinedQuery");
		map.put("params", "[ Iee801fbd227e43eb01583d989ca32e84,null ]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		JsonService js = new JsonService(result);
		JsonArray ja = js.getJsonArray("result");
		// 返回的查询字段
		ja_BIZATTR = (JsonArray) js.getJsonArray("result").get(2);
		ja_filter = (JsonArray) js.getJsonArray("result").get(3);
		reportID1 = ja.get(0).toString();
		reportID2 = ja.get(1).toString();
		reportID3 = ja.get(8).toString();
		BizTheme = ja.get(4).toString();

		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[ Iee801fbd227e43eb01583d989ca32e84]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CombinedQueryService");
		map.put("methodName", "createSimpleReport");
		map.put("params", "[" + reportID1 + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		js = new JsonService(result);
		JsonObject jo = js.getJsonObject("result");
		clientId = jo.get("clientId").toString();
		parameterPanelId = jo.get("parameterPanelId").toString();

		// BizViewOutField= jo.getAsJsonObject("reportBean");
		JsonObject reportBean = jo.getAsJsonObject("reportBean");
		String clientConfig = reportBean.get("clientConfig").getAsString();

		JsonService jss = new JsonService(clientConfig);
		BizViewOutField = jss.getJsonObject().get("gridProp").getAsJsonObject()
				.get("fieldProps").getAsJsonObject();
		JsonArray opParameter = jss.getJsonObject().get("paramSetting")
				.getAsJsonObject().get("applyDefaultValueParams")
				.getAsJsonArray();
		String StartDay = opParameter.get(0).toString();
		String EndDay = opParameter.get(1).toString();

		map.clear();
		map.put("className", "BusinessViewService");
		map.put("methodName", "getBusinessViewByBizThemeCatalogTree");
		map.put("params", "[" + BizTheme + "]");
		result = ns.HttpPost(url_query, map, encoding);

		// System.out.println(BizTheme);
		// System.out.println(result);

		map.clear();
		map.put("className", "CombinedQueryService");
		map.put("methodName", "getLocalFilterElements");
		map.put("params", "[" + reportID1 + "]");
		result = ns.HttpPost(url_query, map, encoding);

		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "getFunctionValue");
		map.put("params", "[ " + clientId + ",CurrentReportName()]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(reportID1);
		// System.out.println(clientId);
		// System.out.println(result);

		map.clear();
		map.put("className", "CombinedQueryService");
		map.put("methodName", "initFromBizViewEx");
		map.put("params",
				"[" + reportID1 + "," + clientId + "," + reportID2 + ",true]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CombinedQueryService");
		map.put("methodName", "setSimpleReportClientId");
		map.put("params", "[" + reportID1 + "," + clientId + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "ConfigClientService");
		map.put("methodName", "getSystemConfig");
		map.put("params", "[REPORT_BROWSE_AUTO_REFRESH]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		/*
		 * 设定兑美元的汇率字段
		 * 
		 */
		map.clear();
		map.put("className", "CombinedQueryService");
		map.put("methodName", "updateSelectPart");
		String param = new String("[" + reportID1
				+ ",[\"BIZATTR.新会特色报表.国际业务交易自助分析主题.数据日期\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.客户号\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.客户名称\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.客户类型\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.业务号\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.产品名称\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.业务类型\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.业务币种\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.业务金额\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.业务办理日期\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.对方国别\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.对方银行编号\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.对方银行名称\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.付款人账户\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.付款人名称\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.收款人账户\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.收款人名称\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.经办上级机构号\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.经办上级机构名称\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.经办网点号\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.经办网点名称\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.计价归属上级机构号\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.计价归属上级机构名称\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.计价归属网点号\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.计价归属网点名称\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.经办人员\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.复核人员\",\"BIZATTR.新会特色报表.国际业务交易自助分析主题.兑美元汇率\"],[\"FILTER.新会特色报表.国际业务交易自助分析主题.业务起始日期\",\"FILTER.新会特色报表.国际业务交易自助分析主题.p_sa_iss_end_date\",\"FILTER.新会特色报表.国际业务交易自助分析主题.p_sa_iss_busi_type\",\"FILTER.新会特色报表.国际业务交易自助分析主题.p_sa_iss_cust_no\",\"FILTER.新会特色报表.国际业务交易自助分析主题.p_sa_iss_curr_cd\",\"FILTER.新会特色报表.国际业务交易自助分析主题.p_sa_iss_trn_amt_end\",\"FILTER.新会特色报表.国际业务交易自助分析主题.p_sa_iss_trn_amt_start\",\"FILTER.新会特色报表.国际业务交易自助分析主题.p_sa_iss_inst_no_gs\",\"FILTER.新会特色报表.国际业务交易自助分析主题.p_sa_iss_cust_type\"],[]]");
		map.put("params", param);
		result = ns.HttpPost(url_query, map, encoding);

		// System.out.println(result);

		/*
		 * 添加字段后重新生成一个新的报表
		 */
		map.clear();
		map.put("className", "CombinedQueryService");
		map.put("methodName", "createSimpleReport");
		map.put("params", "[" + reportID1 + "]");
		result = ns.HttpPost(url_query, map, encoding);
		js = new JsonService(result);
		jo = js.getJsonObject("result");
		clientId = jo.get("clientId").toString();
		parameterPanelId = jo.get("parameterPanelId").toString();

		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "getFunctionValue");
		map.put("params", "[ " + clientId + ",CurrentReportName()]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(reportID1);
		// System.out.println(clientId);
		// System.out.println(result);

		map.clear();
		map.put("className", "CombinedQueryService");
		map.put("methodName", "initFromBizViewEx");
		map.put("params",
				"[" + reportID1 + "," + clientId + "," + reportID2 + ",true]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CombinedQueryService");
		map.put("methodName", "setSimpleReportClientId");
		map.put("params", "[" + reportID1 + "," + clientId + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "ConfigClientService");
		map.put("methodName", "getSystemConfig");
		map.put("params", "[REPORT_BROWSE_AUTO_REFRESH]");
		result = ns.HttpPost(url_query, map, encoding);

		//

		map.clear();
		map.put("className", "CompositeService");
		map.put("methodName", "setParamValuesWithRelated");
		map.put("params", "[" + parameterPanelId + "," + StartDay + ","
				+ startDayNum + "," + startDayChn + " ]");
		result = ns.HttpPost(url_query, map, encoding);
		map.clear();
		map.put("className", "CompositeService");
		map.put("methodName", "setParamValuesWithRelated");
		map.put("params", "[" + parameterPanelId + "," + EndDay + ","
				+ endDayNum + "," + endDayChn + " ]");
		result = ns.HttpPost(url_query, map, encoding);
		System.out.println("设定参数");
		// System.out.println(result);

		/*
		 * 以下是报表输出
		 * 
		 */

		// System.out.println("reportID1 is:" + reportID1);
		// System.out.println("reportID2 is:" + reportID2);
		// System.out.println("clientId is" + clientId);
		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "clearSQLResultStore");
		map.put("params", "[" + clientId + "]");
		result = ns.HttpPost(url_query, map, encoding);

		// System.out.println("clean sql");
		// System.out.println(result);

		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "setRowsPerPage");
		map.put("params", "[" + clientId + ",50000]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "getTotalRowsCountWithFuture");
		map.put("params", "[" + clientId + ",0]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "getReportDataWithFuture");
		map.put("params", "[" + clientId + ",0]");
		result = ns.HttpPost(url_query, map, encoding);
		js = new JsonService(result);
		JsonObject objRusult = js.getJsonObject();
		JsonArray array_result = objRusult.get("result").getAsJsonArray().get(0)
				.getAsJsonArray();
		System.out.println("result array size is:" + array_result.size());
		System.out.println("result to object");

		return array_result;

	}

}
