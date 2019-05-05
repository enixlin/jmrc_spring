package com.enixlin.jmrc.service.found.forward;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("forward")
public class ForwardOrderController {

	@Autowired
	private ForwardOrderService forwardOrderService;
	
	
	@RequestMapping("/add")
	public ForwardOrder add() {
		
	}
}
