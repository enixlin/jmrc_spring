package com.enixlin.jmrc.util;

public class Utils {
	
	

	public Utils() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 取得指日期的去年同一天
	 * @param date
	 * @return
	 */
	public String compareDate(String date) {
		String s = "";
		int year = Integer.parseInt(date.substring(0, 4));
		s = String.valueOf(year - 1) + date.substring(4);
		return s;
	}
	
	/**
	 * 取得去年年末一天
	 * @param date
	 * @return
	 */
	public String compareYearLastDate(String date) {
		String s = "";
		int year = Integer.parseInt(date.substring(0, 4));
		s = String.valueOf(year - 1) + "1231";
		return s;
	}
	
	/**
	 * 判定指定日期在第几个季度
	 * @param date
	 * @return
	 */
	public int getSeason(String date) {
		String s = "";
		int month = Integer.parseInt(date.substring(4, 6));
		if (month >= 1 && month <= 3) {
			return 1;
		}
		if (month >= 4 && month <= 6) {
			return 2;
		}
		if (month >= 7 && month <= 9) {
			return 3;
		}
		if (month >= 10 && month <= 12) {
			return 4;
		}
		return 0;

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
	
}
