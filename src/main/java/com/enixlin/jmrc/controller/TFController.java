

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
}
