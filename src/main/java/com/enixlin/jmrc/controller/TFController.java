

/**
 * 贸易融资业务控制器

 */
package com.enixlin.jmrc.controller;

import com.enixlin.jmrc.service.TFService;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linzhenhuan
 *
 */
@RestController()
@EnableScheduling
@RequestMapping("TF")
public class TFController {
    @Autowired TFService tfs;
    
    public int getRecordCount(){
        return tfs.getRecordCount();
    }
    
    @RequestMapping("/getTFBalance")
    public ArrayList<LinkedHashMap<String, Object>> getTFBalance(HttpServletRequest req,HttpServletResponse res){
    	String date=req.getParameter("date");
    	return tfs.getTFBalance(date);
    }
    
    
    //按统计每一个客户的贸易融资余额
    @RequestMapping("/getClientTFBalance")
    public ArrayList<LinkedHashMap<String, Object>> getClientTFBalance(HttpServletRequest req,HttpServletResponse res){
    	String date=req.getParameter("date");
    	System.out.println("getClientTFBalance");
    	return tfs.getClientTFBalance(date);
    }
    
    
    
  //按业务品种统计每一个客户的贸易融资余额
    @RequestMapping("/getClientTFBalance")
    public ArrayList<LinkedHashMap<String, Object>> getProductClientTFBalance(HttpServletRequest req,HttpServletResponse res){
    	String date=req.getParameter("date");
    	String type=req.getParameter("type");
    	System.out.println("getClientTFBalance");
    	return tfs.getProductClientTFBalance(date,type);
    }
    
}
