package com.enixlin.jmrc.service;

import java.util.ArrayList;

import com.enixlin.jmrc.entity.Roler;
import com.enixlin.jmrc.entity.Rule;

public interface RolerService {

    public ArrayList<Roler> getAllRolers();
    public Roler addRoler(Roler roler);
    public Roler updateRoler(Roler roler);
    public int deleteRoler(Roler roler);
    public Roler getRolerById(Roler roler);
    public ArrayList<Rule> getAllRuleByRoler(Roler roler);
    
    
    public int addRolerRule(Roler roler ,Rule rule);
    public int deleteRolerRule(Roler roler,Rule rule);
	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param userId
	 * @return
	 *ArrayList<Roler>
	 * 创建时间：2019年10月17日
	 */
	public ArrayList<Roler> getRolerByUserId(int userId);
}
