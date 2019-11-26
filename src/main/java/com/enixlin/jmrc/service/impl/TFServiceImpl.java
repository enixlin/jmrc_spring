/**
 * 
 */
package com.enixlin.jmrc.service.impl;

import com.enixlin.jmrc.mapper.TFMapper;
import com.enixlin.jmrc.service.TFService;

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

	
	
	
}
