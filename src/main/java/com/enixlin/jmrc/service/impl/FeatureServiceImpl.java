package com.enixlin.jmrc.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enixlin.jmrc.entity.Feature;
import com.enixlin.jmrc.mapper.FeatureMapper;
import com.enixlin.jmrc.service.FeatureService;
@Service
public class FeatureServiceImpl implements FeatureService {
@Autowired
private FeatureMapper featureMapper;

//private RiskMapper riskMapper;
	
	@Override
	public ArrayList<Feature> getAllFeaturs() {
		// TODO Auto-generated method stub
		
		return featureMapper.getAllFeature();
	}

}
