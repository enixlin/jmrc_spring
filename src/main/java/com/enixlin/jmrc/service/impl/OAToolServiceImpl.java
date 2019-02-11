package com.enixlin.jmrc.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enixlin.jmrc.entity.OATool;
import com.enixlin.jmrc.mapper.OAToolMapper;
import com.enixlin.jmrc.service.OAToolService;
@Service
public class OAToolServiceImpl implements OAToolService {

	@Autowired
	private OAToolMapper oAToolMapper;

	@Override
	public ArrayList<OATool> getOAToolList() {
		// TODO Auto-generated method st
		return oAToolMapper.getOAToolList();
	}

	@Override
	public OATool getOAToolById(int id) {
		return oAToolMapper.getOAToolById(id);
	}

	@Override
	public boolean addOATool(OATool oATool) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modifyOATool(int id, OATool newOATool) {
		return oAToolMapper.updateOATool(id, newOATool);
	}

	@Override
	public boolean deleteOATool(int id) {
		// TODO Auto-generated method stub
		return oAToolMapper.deleteOATool(id);
	}

}
