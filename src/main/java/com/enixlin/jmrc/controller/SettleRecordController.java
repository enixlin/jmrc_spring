
package com.enixlin.jmrc.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enixlin.jmrc.entity.IndexPerformance;
import com.enixlin.jmrc.entity.MonthPerformace;
import com.enixlin.jmrc.entity.Product;
import com.enixlin.jmrc.entity.SettleRange;
import com.enixlin.jmrc.entity.SettleRecord;
import com.enixlin.jmrc.entity.Task;
import com.enixlin.jmrc.entity.Unit;
import com.enixlin.jmrc.entity.UnitPerformance;
import com.enixlin.jmrc.service.SettleRecordService;
import com.enixlin.jmrc.smartbi.ODS;
import com.enixlin.jmrc.util.ExcelTool;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@RestController
@RequestMapping("settlerecord")
public class SettleRecordController {
	@Autowired
	SettleRecordService srs;

	/**
	 * 从自助分析抓取数据，并写入本地数据库
	 */
	@RequestMapping("/batchInsert")
	public void add(HttpServletRequest req, HttpServletResponse res) {

		String start = req.getParameter("start");
		String end = req.getParameter("end");
		String getMax = req.getParameter("getMax");
		String StartDayChn = changeChineseDateFormat(start);
		String endDayChn = changeChineseDateFormat(end);
		String startDayNum = changeLineDateFormat(start);
		String endDayNum = changeLineDateFormat(end);
		ODS ods = new ODS();

		JsonArray ja = ods.getAllSettleRecord(startDayNum, StartDayChn, endDayNum, endDayChn, getMax);

		for (int i = 0; i < ja.size(); i++) {
			SettleRecord sr = new SettleRecord();
			sr.setDataDate(ja.get(i).getAsJsonArray().get(0).getAsString());
			sr.setCustNumber(ja.get(i).getAsJsonArray().get(1).getAsString());
			sr.setCustName(ja.get(i).getAsJsonArray().get(2).getAsString());
			sr.setCustType(ja.get(i).getAsJsonArray().get(3).getAsString());
			sr.setBusyNumber(ja.get(i).getAsJsonArray().get(4).getAsString());
			sr.setProductName(ja.get(i).getAsJsonArray().get(6).getAsString());
			sr.setBusyType(ja.get(i).getAsJsonArray().get(5).getAsString());
			sr.setBusyCurrency(ja.get(i).getAsJsonArray().get(7).getAsString());
			// 字符串中有逗号，所以要先将符号进行替换
			sr.setBusyAmount(new BigDecimal(ja.get(i).getAsJsonArray().get(8).getAsString().replace(",", "")));
			sr.setBusyDate(ja.get(i).getAsJsonArray().get(9).getAsString());
			sr.setForeignCountry(ja.get(i).getAsJsonArray().get(10).getAsString());
			sr.setForeignBankCode(ja.get(i).getAsJsonArray().get(11).getAsString());
			sr.setForeignBankName(ja.get(i).getAsJsonArray().get(12).getAsString());
			sr.setPayerAccount(ja.get(i).getAsJsonArray().get(13).getAsString());
			sr.setPayerName(ja.get(i).getAsJsonArray().get(14).getAsString());
			sr.setReceiveAccount(ja.get(i).getAsJsonArray().get(15).getAsString());
			sr.setReceiveName(ja.get(i).getAsJsonArray().get(16).getAsString());
			sr.setBranchCode(ja.get(i).getAsJsonArray().get(17).getAsString());
			sr.setBranchName(ja.get(i).getAsJsonArray().get(18).getAsString());
			sr.setSubBranchCode(ja.get(i).getAsJsonArray().get(19).getAsString());
			sr.setSubBranchName(ja.get(i).getAsJsonArray().get(20).getAsString());
			sr.setBelongBranchCode(ja.get(i).getAsJsonArray().get(21).getAsString());
			sr.setBelongBranchName(ja.get(i).getAsJsonArray().get(22).getAsString());
			sr.setBelongSubBranchCode(ja.get(i).getAsJsonArray().get(23).getAsString());
			sr.setBelongSubBranchName(ja.get(i).getAsJsonArray().get(24).getAsString());
			sr.setOperator(ja.get(i).getAsJsonArray().get(25).getAsString());
			sr.setConfirmer(ja.get(i).getAsJsonArray().get(26).getAsString());
			// 字符串中有逗号，所以要先将符号进行替换
			String rate = ja.get(i).getAsJsonArray().get(27).getAsString().replace(",", "");
			String currency = ja.get(i).getAsJsonArray().get(7).getAsString();
			if (rate.equals("")) {
				sr.setUsdRate(new BigDecimal("0.0"));
			} else {
				sr.setUsdRate(new BigDecimal(rate));
			}
			// 插入记录
			srs.add(sr);

		}

		System.out.println(" 所有的记录插入完成");
		fixedSettleRecord();
	}

	public void fixedSettleRecord() {
		srs.fixedSettleRecord();
	}

	/**
	 * 取得所有的国际业务产品笔数和金额
	 *
	 * @param req
	 * @param res
	 * @return ArrayList<IndexPerformance> 创建时间：2019年7月27日
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 */
	@RequestMapping("/getAllBusyTypeProformance")
	public ArrayList<IndexPerformance> getAllBusyTypeProformance(HttpServletRequest req, HttpServletResponse res) {

		String start = req.getParameter("start");
		String end = req.getParameter("end");
		return srs.getAllBusyTypeProformance(start, end);
	}

	/**
	 * 取得所有的国际结算统计口径产品的业务笔数和金额
	 *
	 * @param req
	 * @param res
	 * @return ArrayList<IndexPerformance> 创建时间：2019年7月27日
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 */
	@RequestMapping("/getAllBusySettleTypeProformance")
	public ArrayList<IndexPerformance> getAllBusySettleTypeProformance(HttpServletRequest req,
			HttpServletResponse res) {

		String start = req.getParameter("start");
		String end = req.getParameter("end");
		return srs.getAllBusySettleTypeProformance(start, end);
	}

	/**
	 * 将日期格式转换为中文格式
	 *
	 * @param day //格式：20190930
	 * @return
	 */
	public String changeChineseDateFormat(String day) {
		return day.substring(0, 4) + "年" + day.substring(4, 6) + "月" + day.substring(6, 8) + "日";

	}

	/**
	 * 将日期格式转换为横线格式
	 *
	 * @param day //格式：20190930
	 * @return
	 */
	public String changeLineDateFormat(String day) {
		return day.substring(0, 4) + "-" + day.substring(4, 6) + "-" + day.substring(6, 8);

	}

	/**
	 * 取得每月的结算量
	 *
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/getMonthPerformance")
	public List<IndexPerformance> getMonthPerformance(HttpServletRequest req, HttpServletResponse res) {
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		return srs.getMonthPerformance(start, end);

	}

	/**
	 * 取得统计口径的所有记录
	 *
	 * @param req
	 * @param res
	 * @return List<SettleRange> 创建时间：2019年6月13日
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 */
	@RequestMapping("/getsettleRange")
	public List<SettleRange> getsettleRange(HttpServletRequest req, HttpServletResponse res) {
		return srs.getsettleRange();

	}

	@RequestMapping("/getAllProductFromSettleRecord")
	public ArrayList<String> getAllProductFromSettleRecord(HttpServletRequest req, HttpServletResponse res) {
		ArrayList<String> products = srs.getAllProductFromSettleRecord();
		return products;
	}

	@RequestMapping("/getSettleRangeProduct")
	public ArrayList<Product> getSettleRangeProduct(HttpServletRequest req, HttpServletResponse res) {
		ArrayList<Product> products = srs.getSettleRangeProduct();
		return products;
	}

	@RequestMapping("/saveSettleRangeProduct")
	public int saveSettleRangeProduct(HttpServletRequest req, HttpServletResponse res) {
		int count = 0;
// String[] products=req.getParameter("array").split(",", 0);

		return count = 0;
	}

	/**
	 * 取得经营单位或客户的业务实绩
	 *
	 * @param req
	 * @param res
	 * @return ArrayList<UnitPerformance> 创建时间：2019年7月26日
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 */
	@RequestMapping("/getUnitPerformance")
	public ArrayList<UnitPerformance> getUnitPerformance(HttpServletRequest req, HttpServletResponse res) {
		return null;

	}

	/**
	 * @param req
	 * @param res
	 * @return ArrayList<Task> 创建时间：2019年7月26日
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 */
	@RequestMapping("/getAllUnitTask")
	public HashMap<String, ArrayList<Task>> getAllUnitTask(HttpServletRequest req, HttpServletResponse res) {

		ArrayList<String> allUnitNames = this.getAllUnitName();

		return null;

	}

	@RequestMapping("/getTaskByUnitName")
	public ArrayList<Task> getTaskByUnitName(HttpServletRequest req, HttpServletResponse res) {
		return null;

	}

	/**
	 * 取得所有的一级支行名称
	 *
	 * @return ArrayList<String> 创建时间：2019年7月26日
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 */
	@RequestMapping("/getAllUnitName")
	public ArrayList<String> getAllUnitName() {
		return srs.getAllUnitName();

	}

	/**
	 * 取得所有的一级支行名称
	 *
	 * @return ArrayList<String> 创建时间：2019年7月26日
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 */
	@RequestMapping("/getAllUnitInfo")
	public ArrayList<Unit> getAllUnitInfo() {
		return srs.getAllUnitInfo();

	}

	/**
	 * 查询出某一时间段内的全行国际结算量产品明细
	 *
	 * @param req
	 * @param res
	 * @return ArrayList<IndexPerformance> 创建时间：2019年7月28日
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 */
	@RequestMapping("/getSettleTypeProformanceByDate")
	public ArrayList<IndexPerformance> getSettleTypeProformanceByDate(HttpServletRequest req, HttpServletResponse res) {
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		return srs.getSettleTypeProformanceByDate(start, end);
	}

	/**
	 * 获得指定时间段的国际结算流水记录明细
	 *
	 * @param req
	 * @param res
	 * @return ArrayList<SettleRecord> 创建时间：2019年7月29日
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 */
	@RequestMapping("/getDateRangeDetail")
	public ArrayList<SettleRecord> getDateRangeDetail(HttpServletRequest req, HttpServletResponse res) {
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		String product = req.getParameter("product");
		return srs.getDateRangeDetail(start, end, product);
	}

	/**
	 * 取得机构或客户的业务指标数据
	 *
	 * @param req
	 * @param res
	 * @return ArrayList<IndexPerformance> 创建时间：2019年7月30日
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 */
	@RequestMapping("/getUnitIndexPerformance")
	public ArrayList<IndexPerformance> getUnitIndexPerformance(HttpServletRequest req, HttpServletResponse res) {
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		ArrayList<Product> products = srs.getSettleRangeProduct();
		String unitType = req.getParameter("unitType");
		Unit unit = new Unit();
		unit.setName(req.getParameter("name"));
		return srs.getUnitIndexPerformance(start, end, unit, products);
	}

	@RequestMapping("/getAllUnitPerformance")
	public ArrayList<LinkedHashMap<String, Object>> getAllUnitPerformance(HttpServletRequest req,
			HttpServletResponse res) {
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		return srs.getAllUnitPerformance(start, end);
	}

	@RequestMapping("/getAllClientPerformance")
	public ArrayList<LinkedHashMap<String, Object>> getAllClientPerformance(HttpServletRequest req,
			HttpServletResponse res) {
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		String clientType = req.getParameter("clientType");
		return srs.getAllClientPerformance(start, end, clientType);
	}

	/**
	 * 取得客户或机构的国际结算量分月明细
	 *
	 * @param req
	 * @param res
	 * @return ArrayList<MonthPerformace> 创建时间：2019年8月3日
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 */
	@RequestMapping("/getUnitMonthPerformace")
	public ArrayList<MonthPerformace> getUnitMonthPerformace(HttpServletRequest req, HttpServletResponse res) {
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		ArrayList<Product> products = srs.getSettleRangeProduct();
		String unitType = req.getParameter("unitType");
		Unit unit = new Unit();
		unit.setName(req.getParameter("name"));
		unit.setId(req.getParameter("uid"));
		unit.setType(unitType);
		return srs.getUnitMonthPerformace(unit, start, end);
	}

	/**
	 * 取得客户或机构的国际结算量分月明细
	 *
	 * @param req
	 * @param res
	 * @return ArrayList<MonthPerformace> 创建时间：2019年8月3日
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 */
	@RequestMapping("/getUnitSettleMonthPerformance")
	public ArrayList<LinkedHashMap<String, Object>> getUnitSettleMonthPerformance(HttpServletRequest req,
			HttpServletResponse res) {
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		ArrayList<Product> products = srs.getSettleRangeProduct();
		String unitType = req.getParameter("unitType");
		Unit unit = new Unit();
		unit.setId(req.getParameter("uid"));
		unit.setType(unitType);
		return srs.getUnitSettleMonthPerformance(unit, start, end);
	}

//	getClientSettleMonthPerformance

	/**
	 * 取得客户国际结算量分月明细
	 *
	 * @param req
	 * @param res
	 * @return ArrayList<MonthPerformace> 创建时间：2019年8月3日
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 */
	@RequestMapping("/getClientSettleMonthPerformance")
	public ArrayList<LinkedHashMap<String, Object>> getClientSettleMonthPerformance(HttpServletRequest req,
			HttpServletResponse res) {
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		ArrayList<Product> products = srs.getSettleRangeProduct();
		String unitType = req.getParameter("unitType");
		Unit unit = new Unit();
		unit.setId(req.getParameter("uid"));
		unit.setType(unitType);
		return srs.getClientSettleMonthPerformance(unit, start, end);
	}

	/**
	 * 导出经营单位国际结算量统计表格
	 *
	 * @param req
	 * @param res
	 * @return ArrayList<MonthPerformace> 创建时间：2019年8月3日
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 */
	@RequestMapping("/exportAllUnitPerformance")
	public ArrayList<LinkedHashMap<String, Object>> exportAllUnitPerformance(HttpServletRequest req,
			HttpServletResponse res) {
		ArrayList<LinkedHashMap<String, Object>> allUnitPerformance = this.getAllUnitPerformance(req, res);
		String start = req.getParameter("start");
		String end = req.getParameter("end");

		String file = "经营单位国际结算统计表-" + start + "-" + end + ".xls";
		String unit = "单位：万美元,笔,百分比";
		String title = "经营单位国际结算统计表";
		ExcelTool et = new ExcelTool();
//		et.exportToexcel(allUnitPerformance, file);
		et.exportToexcel(allUnitPerformance, file, start, end, unit, title);

		try {
			et.downloadFileByOutputStream(file, res);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return allUnitPerformance;
	}

	/**
	 * 导出经营单位国际结算量统计表格
	 *
	 * @param req
	 * @param res
	 * @return ArrayList<MonthPerformace> 创建时间：2019年8月3日
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 */
	@RequestMapping("/exportUnitPerformance")
	public ArrayList<LinkedHashMap<String, Object>> exportUnitPerformance(HttpServletRequest req,
			HttpServletResponse res) {
		ArrayList<LinkedHashMap<String, Object>> allUnitPerformance = this.getUnitSettleMonthPerformance(req, res);
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		String unitName = req.getParameter("name");
		String unitCode = req.getParameter("code");

		String file = unitName + "国际结算统计表-" + start + "-" + end + ".xls";
		String unit = "单位：万美元,笔";
		String title = unitName + "国际结算统计表";
		ExcelTool et = new ExcelTool();
//		et.exportToexcel(allUnitPerformance, file);
		et.exportToexcel(allUnitPerformance, file, start, end, unit, title);

		try {
			et.downloadFileByOutputStream(file, res);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return allUnitPerformance;
	}

//    exportgetUnitMonthPerformace
	@RequestMapping("/exportUnitMonthPerformace")
	public ArrayList<LinkedHashMap<String, Object>> exportUnitMonthPerformace(HttpServletRequest req,
			HttpServletResponse res) {
		ArrayList<LinkedHashMap<String, Object>> allUnitPerformance = this.getUnitSettleMonthPerformance(req, res);
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		String unitName = req.getParameter("name");
		String unitCode = req.getParameter("code");

		String file = unitName + "国际结算统计表-" + start + "-" + end + ".xls";
		String unit = "单位：万美元,笔";
		String title = unitName + "国际结算统计表";
		ExcelTool et = new ExcelTool();
//		et.exportToexcel(allUnitPerformance, file);
		et.exportToexcel(allUnitPerformance, file, start, end, unit, title);

		try {
			et.downloadFileByOutputStream(file, res);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return allUnitPerformance;
	}

	@RequestMapping("/exportProductPieChart")
	public ArrayList<LinkedHashMap<String, Object>> exportProductPieChart(HttpServletRequest req,
			HttpServletResponse res) {
		ArrayList<LinkedHashMap<String, Object>> ProductPerformance = this.getUnitProductPerformance(req, res);
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		String unitName = req.getParameter("name");
		String unitCode = req.getParameter("code");

		String file = unitName + "国际结算产品统计表-" + start + "-" + end + ".xls";
		String unit = "单位：万美元,笔";
		String title = unitName + "国际结算统计表";
		ExcelTool et = new ExcelTool();
//		et.exportToexcel(allUnitPerformance, file);
		et.exportToexcel(ProductPerformance, file, start, end, unit, title);
//fds
		try {
			et.downloadFileByOutputStream(file, res);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ProductPerformance;
	}
	/**/

	@RequestMapping("/getAllProductsFromSettleRecord")
	public ArrayList<LinkedHashMap<String, Object>> getAllProductsFromSettleRecord() {
		return srs.getAllProductsFromSettleRecord();

	}

	@RequestMapping("/saveRangeProducts")
	public int saveRangeProducts(HttpServletRequest req, HttpServletResponse res) {
		ArrayList<Product> rangeProducts = new ArrayList<>();
		String jsonString = req.getParameter("obj");
		JsonArray jo = (JsonArray) new JsonParser().parse(jsonString);
		for (JsonElement obj : jo) {
			String name = obj.getAsJsonObject().get("name").toString();
			String range = obj.getAsJsonObject().get("range").toString();
			Product product = new Product();
			product.setName(name);
			product.setSettleRange(Boolean.valueOf(range));
			rangeProducts.add(product);
		}
		return srs.saveRangeProducts(rangeProducts);
		// return null;
	}

	@RequestMapping("/getUnitProductPerformance")
	public ArrayList<LinkedHashMap<String, Object>> getUnitProductPerformance(HttpServletRequest req,
			HttpServletResponse res) {
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		ArrayList<Product> products = srs.getSettleRangeProduct();
		String unitType = req.getParameter("unitType");
		Unit unit = new Unit();
		unit.setId(req.getParameter("uid"));
		unit.setType(unitType);
		return srs.getUnitProductPerformance(unit, start, end);

	}

	@RequestMapping("/getUnitClientPerformance")
	public ArrayList<LinkedHashMap<String, Object>> getUnitClientPerformance(HttpServletRequest req,
			HttpServletResponse res) {
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		ArrayList<Product> products = srs.getSettleRangeProduct();
		String unitType = req.getParameter("unitType");
		Unit unit = new Unit();
		unit.setId(req.getParameter("uid"));
		unit.setType(unitType);
		return srs.getUnitClientPerformance(unit, start, end);

	}

	@RequestMapping("/exportAllClientPerformance")
	public ArrayList<LinkedHashMap<String, Object>> exportAllClientPerformance(HttpServletRequest req,
			HttpServletResponse res) {
		ArrayList<LinkedHashMap<String, Object>> allClientPerformance = this.getAllClientPerformance(req, res);
		String start = req.getParameter("start");
		String end = req.getParameter("end");

		String file = "客户国际结算统计表-" + start + "-" + end + ".xls";
		String unit = "单位：万美元,笔";
		String title = "客户国际结算统计表";
		ExcelTool et = new ExcelTool();
		et.exportToexcel(allClientPerformance, file, start, end, unit, title);

		try {
			et.downloadFileByOutputStream(file, res);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return allClientPerformance;
	}

	@RequestMapping("/getLastUpdateDate")
	public String getLastUpdateDate() {
		return srs.getLastUpdateDate();
	}
	
	
	//getTotalSettlePerformance
	
	@RequestMapping("/getTotalSettlePerformance")
	public String getTotalSettlePerformance(HttpServletRequest req,HttpServletResponse res) {
//		String start=
		String start=req.getParameter("start");
		String end= req.getParameter("end");
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		this.updatelog(ft.format(date));
		return srs.getTotalSettlePerformance(start,end);
	} 
	
	
//	getTotalTask
	@RequestMapping("/getTotalTask")
	public String getTotalTask(HttpServletRequest req,HttpServletResponse res) {

		String end= req.getParameter("end");

		return srs.getTotalTask(end);
	} 
	public void updatelog(String datatime) {
		srs.updatelog(datatime);
	}
	
	@RequestMapping("/getProductMonthPerformance")
	public ArrayList<LinkedHashMap<String, Object>> getProductMonthPerformance(HttpServletRequest req,HttpServletResponse res){
		String product=req.getParameter("product");
		String start=req.getParameter("start");
		String end=req.getParameter("end");
		return srs.getProductMonthPerformance(product,start,end);
	}
	
//	getProductClientDetail
	@RequestMapping("/getProductClientDetail")
	public ArrayList<LinkedHashMap<String, Object>> getProductClientDetail(HttpServletRequest req,HttpServletResponse res){
		String product=req.getParameter("product");
		String start=req.getParameter("start");
		String end=req.getParameter("end");
		return srs.getProductClientDetail(product,start,end);
	}
	
	
//	getProductDetail
	
	@RequestMapping("/getProductDetail")
	public ArrayList<LinkedHashMap<String, Object>> getProductDetail(HttpServletRequest req,HttpServletResponse res){
		String product=req.getParameter("product");
		String start=req.getParameter("start");
		String end=req.getParameter("end");
		return srs.getProductDetail(product,start,end);
	}
	
	/**
	 * 生成指定时间段内所有结算产品的业务量统计,
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/getAllProductDetail")
	public ArrayList<LinkedHashMap<String, Object>> getAllProductDetail(HttpServletRequest req,HttpServletResponse res){
		String product=req.getParameter("product");
		String start=req.getParameter("start");
		String end=req.getParameter("end");
		return srs.getAllProductDetail(start,end);
	}
	
	
	
//	exportProductClientDetailExcel
	@RequestMapping("/exportProductClientDetailExcel")
	public ArrayList<LinkedHashMap<String, Object>> exportProductClientDetailExcel(HttpServletRequest req,HttpServletResponse res){
		String product=req.getParameter("product");
		String start=req.getParameter("start");
		String end=req.getParameter("end");
		ArrayList<LinkedHashMap<String, Object>> ProductClientDetail = this.getProductClientDetail(req, res);
	

		String file = product + "客户结算统计表-" + start + "-" + end + ".xls";
		String unit = "单位：万美元,笔";
		String title = product + "客户结算统计表";
		ExcelTool et = new ExcelTool();
//		et.exportToexcel(allUnitPerformance, file);
		et.exportToexcel(ProductClientDetail, file, start, end, unit, title);

		try {
			et.downloadFileByOutputStream(file, res);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ProductClientDetail;
	}
	
//	exportProductDetailExcel
	@RequestMapping("/exportProductDetailExcel")
	public ArrayList<LinkedHashMap<String, Object>> exportProductDetailExcel(HttpServletRequest req,HttpServletResponse res){
		String product=req.getParameter("product");
		String start=req.getParameter("start");
		String end=req.getParameter("end");
		ArrayList<LinkedHashMap<String, Object>> ProductDetail = this.getProductDetail(req, res);
	

		String file = product + "结算流水记录表-" + start + "-" + end + ".xls";
		String unit = "单位：万美元,笔";
		String title = product + "结算流水记录表";
		ExcelTool et = new ExcelTool();
//		et.exportToexcel(allUnitPerformance, file);
		et.exportToexcel(ProductDetail, file, start, end, unit, title);

		try {
			et.downloadFileByOutputStream(file, res);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ProductDetail;
	}
	
	
//	exportProductMonthDetailExcel
	@RequestMapping("/exportProductMonthDetailExcel")
	public ArrayList<LinkedHashMap<String, Object>> exportProductMonthDetailExcel(HttpServletRequest req,HttpServletResponse res){
		String product=req.getParameter("product");
		String start=req.getParameter("start");
		String end=req.getParameter("end");
		ArrayList<LinkedHashMap<String, Object>> ProductMonthDetailExcel = this.getProductMonthPerformance(req, res);
		String file = product + "分明统计表-" + start + "-" + end + ".xls";
		String unit = "单位：万美元,笔";
		String title = product + "分明统计表";
		ExcelTool et = new ExcelTool();
//		et.exportToexcel(allUnitPerformance, file);
		et.exportToexcel(ProductMonthDetailExcel, file, start, end, unit, title);
		try {
			et.downloadFileByOutputStream(file, res);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ProductMonthDetailExcel;
	}
	
//	exportAllProductDetailExcel
	@RequestMapping("/exportAllProductDetailExcel")
	public ArrayList<LinkedHashMap<String, Object>> exportAllProductDetailExcel(HttpServletRequest req,HttpServletResponse res){
		
		String start=req.getParameter("start");
		String end=req.getParameter("end");
		ArrayList<LinkedHashMap<String, Object>> ProductDetailExcel = this.getAllProductDetail(req, res);
		String file =  "国际结算产品分类统计表-" + start + "-" + end + ".xls";
		String unit = "单位：万美元,笔";
		String title =  "国际结算产品分类统计表";
		ExcelTool et = new ExcelTool();
//		et.exportToexcel(allUnitPerformance, file);
		et.exportToexcel(ProductDetailExcel, file, start, end, unit, title);
		try {
			et.downloadFileByOutputStream(file, res);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ProductDetailExcel;
	}
	
//	getClientProductPerformance
	@RequestMapping("/getClientProductPerformance")
	public ArrayList<LinkedHashMap<String, Object>> getClientProductPerformance(HttpServletRequest req,
			HttpServletResponse res) {
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		ArrayList<Product> products = srs.getSettleRangeProduct();
		String unitType = req.getParameter("unitType");
		Unit unit = new Unit();
		unit.setId(req.getParameter("uid"));
		unit.setType(unitType);
		return srs.getClientProductPerformance(unit, start, end);

	}
	
	
}
