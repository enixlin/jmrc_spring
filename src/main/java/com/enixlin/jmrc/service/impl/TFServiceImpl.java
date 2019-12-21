/**
 * 
 */
package com.enixlin.jmrc.service.impl;

import com.enixlin.jmrc.mapper.TFMapper;
import com.enixlin.jmrc.service.TFService;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linzhenhuan
 *
 */
@Service
public class TFServiceImpl implements TFService {
    @Autowired TFMapper tfm;

    @Override
    public int getRecordCount() {
        return tfm.getRecordCount();
    }

	@Override
	public String getLastReportDate() {
		// TODO Auto-generated method stub
		return tfm.getLastReportDate();
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getTFInt(String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getOrderInt(String date) {
		// TODO Auto-generated method stub
		return tfm.getOrderInt(date);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getRTInt(String date) {
		// TODO Auto-generated method stub
		return tfm.getRTInt(date);
	}

	
	
	
}
