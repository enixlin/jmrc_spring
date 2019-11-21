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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enixlin.jmrc.entity.SettleRecord;
import com.enixlin.jmrc.service.SettleRecordService;
import com.enixlin.jmrc.service.impl.UpdateService;
import com.enixlin.jmrc.smartbi.ODS;
import com.google.gson.JsonArray;

/**
 * @author linzhenhuan
 *
 */
@RestController()
@EnableScheduling
@RequestMapping("update")
public class UpdateController {
	@Autowired
	SettleRecordService ss;
	@Autowired
	UpdateService us;
	

	@RequestMapping("/test")
	public void updateTest(HttpServletRequest req, HttpServletResponse res) {
		String end = req.getParameter("date");
		String getMax = req.getParameter("getMax");
		this.addSubjectsBalance(end, getMax);
	}

	public void addTF(String end, String getMax) {
		ODS ods = new ODS();
		String dateLineFormat = this.changeLineDateFormat(end);
		String dateChineseFormat = this.changeChineseDateFormat(end);
		JsonArray ja = ods.getAllFTRecordFromMiddleTable(dateLineFormat,
				dateChineseFormat,
				getMax);
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
		ArrayList<LinkedHashMap<String, Object>> sb_date_exist = ss
				.isSubjectDateExist(end);
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
		return day.substring(0, 4) + "年" + day.substring(4, 6) + "月"
				+ day.substring(6, 8) + "日";

	}

	/**
	 * 将日期格式转换为横线格式
	 *
	 * @param day //格式：20190930
	 * @return
	 */
	public String changeLineDateFormat(String day) {
		return day.substring(0, 4) + "-" + day.substring(4, 6) + "-"
				+ day.substring(6, 8);

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

		JsonArray ja = ods.getAllSettleRecord(startDayNum, StartDayChn,
				endDayNum, endDayChn, getMax);

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
			sr.setBusyAmount(new BigDecimal(ja.get(i).getAsJsonArray().get(8)
					.getAsString().replace(",", "")));
			sr.setBusyDate(ja.get(i).getAsJsonArray().get(9).getAsString());
			sr.setForeignCountry(
					ja.get(i).getAsJsonArray().get(10).getAsString());
			sr.setForeignBankCode(
					ja.get(i).getAsJsonArray().get(11).getAsString());
			sr.setForeignBankName(
					ja.get(i).getAsJsonArray().get(12).getAsString());
			sr.setPayerAccount(
					ja.get(i).getAsJsonArray().get(13).getAsString());
			sr.setPayerName(ja.get(i).getAsJsonArray().get(14).getAsString());
			sr.setReceiveAccount(
					ja.get(i).getAsJsonArray().get(15).getAsString());
			sr.setReceiveName(ja.get(i).getAsJsonArray().get(16).getAsString());
			sr.setBranchCode(ja.get(i).getAsJsonArray().get(17).getAsString());
			sr.setBranchName(ja.get(i).getAsJsonArray().get(18).getAsString());
			sr.setSubBranchCode(
					ja.get(i).getAsJsonArray().get(19).getAsString());
			sr.setSubBranchName(
					ja.get(i).getAsJsonArray().get(20).getAsString());
			sr.setBelongBranchCode(
					ja.get(i).getAsJsonArray().get(21).getAsString());
			sr.setBelongBranchName(
					ja.get(i).getAsJsonArray().get(22).getAsString());
			sr.setBelongSubBranchCode(
					ja.get(i).getAsJsonArray().get(23).getAsString());
			sr.setBelongSubBranchName(
					ja.get(i).getAsJsonArray().get(24).getAsString());
			sr.setOperator(ja.get(i).getAsJsonArray().get(25).getAsString());
			sr.setConfirmer(ja.get(i).getAsJsonArray().get(26).getAsString());
			// 字符串中有逗号，所以要先将符号进行替换
			String rate = ja.get(i).getAsJsonArray().get(27).getAsString()
					.replace(",", "");
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
	// @Scheduled(fixedRate = 600000)
	public void updateProcess() {
		// 取得当前的日期
		Date date_current = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date_current_str = sdf.format(date_current);
		// ODS的业务数据一般为
		String tf_date = sdf
				.format(new Date(date_current.getTime() - 24 * 3600 * 1000));
		String subject_date = sdf
				.format(new Date(date_current.getTime() - 24 * 3600 * 1000));
		int date_current_int = Integer.parseInt(date_current_str);

		// 取得最近的业务日期
		String date_log_str = this.getLastUpdateDate("settle").replace("-", "");
		String date_log_tf = this.getLastUpdateDate("tf").replace("-", "");
		String date_log_subject = this.getLastUpdateDate("subject").replace("-",
				"");
		Date date_log = null;
		try {
			date_log = sdf.parse(date_log_str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int date_log_settle_int = Integer.parseInt(date_log_str);
		int date_log_tf_int = Integer.parseInt(date_log_tf);
		int date_log_subject_int = Integer.parseInt(date_log_subject);

		// 执行结算和结售汇记录更新
		if (date_current_int > date_log_settle_int) {
			String start = sdf
					.format(new Date(date_log.getTime() + 24 * 3600 * 1000));
			String end = date_current_str;
			String getMax = "50000";
			// 添加国际结算和结售汇业务到数据库
			this.addSettle(start, end, getMax);
			String type = "settle";
			String datatime = this.getLastBusyDate(type);
			this.updatelog(datatime, type);
		} else {
			System.out.println("结算和结售汇记录无需更新");
		}
		// 执行贸易融资记录更新
		if (date_current_int > date_log_tf_int) {
			String start = sdf
					.format(new Date(date_log.getTime() + 24 * 3600 * 1000));
			String end = date_current_str;
			String getMax = "5000";
			// 添加国际结算和结售汇业务到数据库
			this.addTF(end, getMax);
			String type = "tf";
			String datatime = this.getLastBusyDate(type);
			this.updatelog(datatime, type);
		} else {
			System.out.println("贸易融资记录无需更新");
		}
		// 执行科目余额记录更新
		if (date_current_int > date_log_tf_int) {
			String start = sdf
					.format(new Date(date_log.getTime() + 24 * 3600 * 1000));
			String end = date_current_str;
			String getMax = "5000";
			// 添加国际结算和结售汇业务到数据库
			this.addSubjectsBalance(end, getMax);
			String type = "subject";
			String datatime = this.getLastBusyDate(type);
			this.updatelog(datatime, type);
		} else {
			System.out.println("贸易融资记录无需更新");
		}

	}

	@RequestMapping("/getLastUpdateDate")
	public String getLastUpdateDate(String type) {
		return us.getLastUpdateDate(type);
	}

	public String getLastBusyDate(String type) {
		//TODO Auto-generated catch block
		
		return ss.getLastBusyDate();
	}

	public void updatelog(String datatime, String type) {
		us.updatelog(datatime, type);
	}
}