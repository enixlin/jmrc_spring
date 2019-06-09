/**
 * 
 */
package com.enixlin.jmrc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enixlin.jmrc.entity.Report;
import com.enixlin.jmrc.service.ReportService;

/**
 * 这是报表控制器
 * @author linzhenhuan
 *
 */
@RequestMapping("/report")
@RestController
public class ReportController {

	@Autowired
	private ReportService reportService;
	
	
	@RequestMapping("/getReportType")
	public List<Report>  getReportType(HttpServletRequest req,HttpServletResponse res){
		return reportService.getAll();
		
	}
	
}
