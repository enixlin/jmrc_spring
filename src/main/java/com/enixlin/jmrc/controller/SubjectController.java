package com.enixlin.jmrc.controller;

import com.enixlin.jmrc.service.SubjectService;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("subject")
public class SubjectController {

	@Autowired
	SubjectService ss;

	public String getLastReportDate() {
		return ss.getLastReportDate();
	}

	/**
	 * 取得存款的时点和日均余额
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/getDepositSubjects")
	public ArrayList<LinkedHashMap<String, Object>> getDepositSubjects(HttpServletRequest req,
			HttpServletResponse res) {
		String date = req.getParameter("date");
		return ss.getDepositSubjects(date);
	}

	/**
	 * 取得收入类科目的时点余额
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/getIncomeSubject")
	public ArrayList<LinkedHashMap<String, Object>> getIncomeSubject(HttpServletRequest req, HttpServletResponse res) {
		String date = req.getParameter("date");
		ArrayList<LinkedHashMap<String, Object>> arr = ss.getIncomeSubject(date);
		return arr;
	}

}