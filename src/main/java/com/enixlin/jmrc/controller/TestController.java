package com.enixlin.jmrc.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enixlin.jmrc.entity.Test;
import com.enixlin.jmrc.service.TestService;

@RestController
@RequestMapping("test")
public class TestController {

	@Autowired
	private TestService testService;
	
	@RequestMapping("/add")
	public void add() {
		Test obj=new Test();
		obj.setId(113);
		obj.setName("enixlin1");
		ArrayList<Test> al=(ArrayList<Test>) testService.getAll();
		System.out.println("al.size()...................");
		System.out.println(al.size());
		testService.add(obj);
	}
}
