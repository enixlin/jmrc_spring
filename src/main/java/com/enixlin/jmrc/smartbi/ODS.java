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

	public JsonArray getAllFTRecord(String queryDate1, String queryDate2, String ExportNum) {
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
		String param_struction = jo.get(0).getAsJsonObject().get("id").toString();
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
		map.put("params", "[" + ClientId + "," + param_date + "," + queryDate1 + "," + queryDate2 + " ]");
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
		// System.out.println(result);

		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "getReportDataWithFuture");
		map.put("params", "[" + ClientId + ",0]");
		result = ns.HttpPost(url_query, map, encoding);
		js = new JsonService(result);
		JsonObject objRusult = js.getJsonObject();
		JsonArray array_result = objRusult.get("result").getAsJsonArray().get(0).getAsJsonArray();

		System.out.println("result array size is:" + array_result.size());
		System.out.println("result to object");
		return array_result;

	}

	/**
	 * 取得所有的国际业务流水
	 * 
	 * @param startDayNum
	 *            格式： "2019-05-01"
	 * @param StarDayChn
	 *            格式： "2019年5月1日"
	 * @param endDayNum
	 *            格式： "2019-05-10"
	 * @param endDayChn
	 *            格式： "2019年5月10日"
	 * @param ExportNum
	 *            返回的最大记录数
	 * @return 国际业务流水记录的数组
	 */
	public JsonArray getAllSettleRecord(String startDayNum, String StarDayChn, String endDayNum, String endDayChn,
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
		BizViewOutField = jss.getJsonObject().get("gridProp").getAsJsonObject().get("fieldProps").getAsJsonObject();
		JsonArray opParameter = jss.getJsonObject().get("paramSetting").getAsJsonObject().get("applyDefaultValueParams")
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
		map.put("params", "[" + reportID1 + "," + clientId + "," + reportID2 + ",true]");
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
		map.put("params", "[" + reportID1 + "," + clientId + "," + reportID2 + ",true]");
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
		map.put("params", "[" + parameterPanelId + "," + StartDay + ",\"2019-01-01\",\"2019年1月1日\"]");
		result = ns.HttpPost(url_query, map, encoding);
		map.clear();
		map.put("className", "CompositeService");
		map.put("methodName", "setParamValuesWithRelated");
		map.put("params", "[" + parameterPanelId + "," + EndDay + ",\"2019-05-20\",\"2019年5月20日\"]");
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
		JsonArray array_result = objRusult.get("result").getAsJsonArray().get(0).getAsJsonArray();
		System.out.println("result array size is:" + array_result.size());
		System.out.println("result to object");

		return array_result;

	}

}
