package com.enixlin.jmrc.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enixlin.jmrc.entity.Roler;
import com.enixlin.jmrc.entity.Rule;
import com.enixlin.jmrc.mapper.RolerMapper;
import com.enixlin.jmrc.service.RolerService;
@Service
public class RolerServiceImpl implements RolerService {
    
    @Autowired
    private RolerMapper rolerMapper;
    

    @Override
    public ArrayList<Roler> getAllRolers() {
	return rolerMapper.getAllRolers();
    }

    @Override
    public Roler addRoler(Roler roler) {
	return rolerMapper.addRoler(roler);
    }

    @Override
    public Roler updateRoler(Roler roler) {
	return rolerMapper.updateRoler(roler);
    }

    @Override
    public int deleteRoler(Roler roler) {
	return rolerMapper.deleteRoler(roler);
    }

    @Override
    public Roler getRolerById(Roler roler) {
	return rolerMapper.getRolerById(roler);
    }

    @Override
    public ArrayList<Rule> getAllRuleByRoler(Roler roler) {
	return rolerMapper.getAllRuleByRoler(roler);
    }

    @Override
    public int addRolerRule(Roler roler, Rule rule) {
	//System.out.println(roler);
	//System.out.println(rule);
	return rolerMapper.addRolerRule(roler, rule);
    }

    @Override
    public int deleteRolerRule(Roler roler, Rule rule) {
	return rolerMapper.deleteRolerRule(roler, rule) ;
    }

}
