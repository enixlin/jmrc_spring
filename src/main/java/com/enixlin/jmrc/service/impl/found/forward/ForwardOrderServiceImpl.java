package com.enixlin.jmrc.service.impl.found.forward;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enixlin.jmrc.entity.ForwardOrder;
import com.enixlin.jmrc.mapper.ForwardOrderMapper;
import com.enixlin.jmrc.service.found.forward.ForwardOrderService;
import com.enixlin.jmrc.service.impl.BaseServiceImpl;

@Service
public class ForwardOrderServiceImpl extends BaseServiceImpl<ForwardOrder> implements ForwardOrderService {

	@Autowired 
	private ForwardOrderMapper forwardOrderMapper;
	

}
