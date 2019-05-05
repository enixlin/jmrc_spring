package com.enixlin.jmrc.service.found.forward;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enixlin.jmrc.entity.ForwardOrder;
import com.enixlin.jmrc.mapper.ForwardOrderMapper;
import com.enixlin.jmrc.service.impl.found.forward.BaseServiceImpl;

@Service
public class ForwardOrderServiceImpl extends BaseServiceImpl<ForwardOrder> implements ForwardOrderService {

	@Autowired ForwardOrderMapper forwardOrderMapper;
	

}
