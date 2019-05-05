package com.enixlin.jmrc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enixlin.jmrc.entity.Test;
import com.enixlin.jmrc.mapper.TestMapper;
import com.enixlin.jmrc.service.TestService;
@Service
public class TestServiceImpl extends BaseServiceImpl<Test>  implements TestService{

	@Autowired
	private TestMapper testMapper;
	
	@Transactional
	@Override
	public Test add(Test obj) {
		// TODO Auto-generated method stub
		testMapper.insert(obj);
		
//		String s=null;
//		s.indexOf("c");
		return null;
	}

	@Override
	public Test update(Test obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Test delete(Test obj) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Test getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Test getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isExist(Test obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
