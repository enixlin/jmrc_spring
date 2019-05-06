package com.enixlin.jmrc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enixlin.jmrc.entity.ForwardOrder;
import com.enixlin.jmrc.service.found.forward.ForwardService;

/**
 * 远期结售汇台账管理控制器
 * 
 * @author linzhenhuan
 *
 */
@RestController
@RequestMapping("forward")
public class ForwardController {

	@Autowired
	private ForwardOrderService fo;
	
	//添加远期结售汇签约记录
	@RequestMapping("/allOrder")
	public ForwardOrder addOrder(HttpServletRequest res) {
		
//		ForwardOrder obj=new ForwardOrder();
//		obj.set
//		fo.add(obj);
		return null;
		
	}
	
	
	@RequestMapping("/uploadfile")
	public void updatefile(HttpServletRequest res) {
		
	}
}
