package com.enixlin.jmrc.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enixlin.jmrc.entity.OATool;
import com.enixlin.jmrc.service.OAToolService;
@RestController
@RequestMapping("oatools")
public class OAToolsController {
	
	@Autowired
	private OAToolService oAToolService;

		@RequestMapping("/showTools")
	    public ArrayList<String> authLogin(HttpServletRequest req, HttpServletResponse res, HttpSession session) {
		
		ArrayList<String> toolsList=new ArrayList<>();
		
		return toolsList;
	    }
		
		
		
		@RequestMapping("/getAllTools")
		public ArrayList<OATool> getAllOATools(){
			return  oAToolService.getOAToolList();
		}
}
