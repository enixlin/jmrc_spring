package com.enixlin.jmrc.service;

import java.util.ArrayList;

import com.enixlin.jmrc.entity.OATool;

public interface OAToolService {

	
	public ArrayList<OATool> getOAToolList();
	public OATool getOAToolById(int id);
	public boolean addOATool(OATool oATool);
	public boolean modifyOATool(int id,OATool newOATool);
	public boolean deleteOATool(int id);
	
	
}
