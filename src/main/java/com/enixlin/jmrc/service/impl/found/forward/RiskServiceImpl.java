package com.enixlin.jmrc.service.impl.found.forward;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enixlin.jmrc.entity.found.forward.Risk;
import com.enixlin.jmrc.mapper.RiskMapper;
import com.enixlin.jmrc.service.found.forward.RiskService;
import com.enixlin.jmrc.service.impl.BaseServiceImpl;
@Service
public class RiskServiceImpl extends BaseServiceImpl<Risk> implements RiskService {
 
	@Autowired
	private RiskMapper riskMapper;
	
	@Override
	public Risk add(Risk obj) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public Risk update(Risk obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Risk delete(Risk obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Risk> getAll() {
		// TODO Auto-generated method stub
		return riskMapper.selectAll();
	}

	@Override
	public Risk getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Risk getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isExist(Risk obj) {
		// TODO Auto-generated method stub
		java.util.ArrayList<Risk> all=(java.util.ArrayList<Risk>) riskMapper.selectAll();
		Boolean exist=false;
		for(Risk r:all) {
			if(r.getExpiry()==obj.getExpiry() && r.getMaincurencyrisk()==obj.getMaincurencyrisk()) {
				exist=true;
				return exist;
			}
		}
		return exist;
	}

}
