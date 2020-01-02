/**
 * 数据更新控制器
 * 数据更新的项目：
 * 一、结算业务数据
 * 二、结售汇业务数据
 * 三、收入、支出类科目余额表
 * 四、存款类科目余额表
 * 五、信贷中间表
 */
package com.enixlin.jmrc.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enixlin.jmrc.entity.SettleRecord;
import com.enixlin.jmrc.service.SettleRecordService;
import com.enixlin.jmrc.service.SubjectService;
import com.enixlin.jmrc.service.TFService;
import com.enixlin.jmrc.service.UpdateService;
import com.enixlin.jmrc.smartbi.ODS;
import com.google.gson.JsonArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linzhenhuan
 *
 */
@RestController()
@EnableScheduling
@RequestMapping("update")
public class UpdateController {
	private static final String ExportNum = null;
	@Autowired
	SettleRecordService ss;
	@Autowired
	UpdateService us;
	@Autowired
	TFService tfs;
	@Autowired
	SubjectService subjectService;

	@RequestMapping("/test")
	public void updateTest(HttpServletRequest req, HttpServletResponse res) {
		//this.updateProcess();
		// 插入科目余额
		 String end = req.getParameter("date");
		 String getMax = req.getParameter("getMax");
		 this.addSubjectsBalance(end, getMax);

		// 插入贸易融资
//		 String end = req.getParameter("date");
//		 String getMax = req.getParameter("getMax");
//		 this.addTF(end, getMax);

		// 插入国际结算
		// String start = req.getParameter("start");
		// String end = req.getParameter("end");
		// String getMax = req.getParameter("getMax");
		// this.addSettle(start, end, getMax);
	}
	@RequestMapping("/test_tf")
	public void updateTestTF(HttpServletRequest req, HttpServletResponse res) {


		// 插入贸易融资
		 String end = req.getParameter("date");
		 String getMax = req.getParameter("getMax");
		 this.addTF(end, getMax);

	}
	@RequestMapping("/test_subject")
	public void updateTestSubject(HttpServletRequest req, HttpServletResponse res) {
		//this.updateProcess();
		// 插入科目余额
		 String end = req.getParameter("date");
		 String getMax = req.getParameter("getMax");
		 this.addSubjectsBalance(end, getMax);


	}
	@RequestMapping("/test_settle")
	public void updateTestSettle(HttpServletRequest req, HttpServletResponse res) {


		// 插入国际结算
		 String start = req.getParameter("start");
		 String end = req.getParameter("end");
		 String getMax = req.getParameter("getMax");
		 this.addSettle(start, end, getMax);
	}
	
	

	public void addTF(String end, String getMax) {
		ODS ods = new ODS();
		String dateLineFormat = this.changeLineDateFormat(end);
		String dateChineseFormat = this.changeChineseDateFormat(end);
		JsonArray ja = ods.getAllFTRecordFromMiddleTable(dateLineFormat, dateChineseFormat, getMax);
		System.out.println("共有融资记录：" + ja.size());

		if (ja.size() > 0) {
			us.addTF(ja);
		
		}

		System.out.println("tf");

	}

	/**
	 * 添加收入、存款科目
	 * 
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 * @param start  报表日期
	 * @param getMax void 创建时间：2019年11月12日
	 */
	public void addSubjectsBalance(String end, String getMax) {
		ODS ods = new ODS();
		ArrayList<LinkedHashMap<String, Object>> sb_date_exist = ss.isSubjectDateExist(end);
		if (sb_date_exist.size() == 0) {
			System.out.println("科目余额表还没有当天的数据，可以更新");
			JsonArray ja = ods.getSubjectsBalance(end, getMax);
			if (ja.size() > 0) {
				int recordInsertCount = us.addSubjects(ja);
				System.out.println("更新收入、存款科目完成");
				System.out.println("共插入记录：" + recordInsertCount + "条");
			}
		} else {
			System.out.println("科目余额表已有当天的数据，不用更新");
		}

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
	 * 从自助分析抓取国际结算和结售汇数据，并写入本地数据库
	 */

	public void addSettle(String start, String end, String getMax) {

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
			us.addSettle(sr);

		}

		System.out.println(" 所有的记录插入完成");
		fixedSettleRecord();
		

	}

	public void fixedSettleRecord() {
		us.fixedSettleRecord();
	}

	public Date getNextDays(Date start, int afterdays) {
		return new Date(start.getTime() + afterdays * 24 * 60 * 60 * 1000);
	}

	// 执行定时任务，每十分钟检查一次数据更新的日期与当前日期，如果当前日期先于数据库的日期，则执行更新
	// 更新的频率为每十分钟
	 @Scheduled(fixedRate = 600000)
	public void updateProcess() {
		// 取得当前的日期
		Date date_current = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date_current_str = sdf.format(date_current);
		long today = 0;
		try {
			today = sdf.parse(date_current_str).getTime();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// ODS的业务数据日期一般为T-
		long yesterday = (today - 24 * 3600 * 1000);
		String yesterday_str = sdf.format(yesterday);

		// 取得最近的业务日期
		String date_log_str = this.getLastUpdateDate("settle").replace("-", "");
		String date_log_tf = this.getLastUpdateDate("tf").replace("-", "");
		String date_log_subject = this.getLastUpdateDate("subject").replace("-", "");
		long date_log_settle_int = 0;
		long date_log_tf_int = 0;
		long date_log_subject_int = 0;
		String data_Start_settle = "";
		String data_Start_tf = "";
		String data_Start_subject = "";
		try {
			date_log_settle_int = sdf.parse(date_log_str).getTime();
			date_log_tf_int = sdf.parse(date_log_tf).getTime();
			date_log_subject_int = sdf.parse(date_log_subject).getTime();
			data_Start_settle = sdf.format(new Date(date_log_settle_int + 24 * 3600 * 1000));
			data_Start_tf = sdf.format(new Date(date_log_tf_int + 24 * 3600 * 1000));
			data_Start_subject = sdf.format(new Date(date_log_subject_int + 24 * 3600 * 1000));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 执行结算和结售汇记录更新
		if (yesterday > date_log_settle_int) {
			// 插入数据之前先清空相关日期段的数据，防止重复
			us.deleteSettleRecord(data_Start_settle, yesterday_str);
			// 添加国际结算和结售汇业务到数据库
			this.addSettle(data_Start_settle, yesterday_str, "50000");
			String update_log_settle = ss.getLastBusyDate();
			if (!update_log_settle.equals("")) {
				us.updatelog(update_log_settle, "settle");
			}
		} else {
			System.out.println(yesterday_str + "当天结算量结售汇业务已更新");
		}

		// 执行科目余额记录更新
		if (yesterday > date_log_subject_int) {
			// 插入数据之前先清空相关日期段的数据，防止重复
			us.deleteSubjectRecord(data_Start_subject, yesterday_str);
			// 添加国际结算和结售汇业务到数据库
			this.addSubjectsBalance(data_Start_subject, "5000");
			String lastday = us.getLastBusyDate("subject");
			if (!lastday.equals("")) {
				us.updatelog(lastday, "subject");
			}
		} else {
			System.out.println(yesterday_str + "当天科目余额表已更新");
		}

		// 执行贸易融资记录更新
		if (yesterday > date_log_tf_int) {
			// 插入数据之前先清空相关日期段的数据，防止重复
			ODS ods = new ODS();

			String dateLineFormat = ods.changeLineDateFormat(yesterday_str);
			String dateChineseFormat = ods.changeChineseDateFormat(yesterday_str);
			int count = ods.getTFRecordCount(dateLineFormat, dateChineseFormat, ExportNum);
			if (count > 0) {
				us.deleteTFRecord(data_Start_tf, yesterday_str);
				this.addTF(data_Start_tf, "4000");
			} else {
				System.out.println(yesterday_str + "当天没有数据，贸易融资记录无需更新");
			}
			// 添加贸易融资记录更新到数据库
			String date_update=tfs.getLastReportDate();
			if(date_update.equals("")) {
				us.updatelog(date_update, "tf");
				System.out.println( "贸易融资记录被更新");
			}
		} else {
			System.out.println(yesterday_str + "当天贸易融资已更新");
		}
	}

	@RequestMapping("/getLastUpdateDate")
	public String getLastUpdateDate(String type) {
		return us.getLastUpdateDate(type);
	}

	public String getLastBusyDate(String type) {
		// TODO Auto-generated catch block
		return us.getLastBusyDate(type);
	}

	public void updatelog(String datatime, String type) {
		us.updatelog(datatime, type);
	}
}
