/**
 * 
 */
package com.enixlin.jmrc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enixlin.jmrc.entity.Report;
import com.enixlin.jmrc.mapper.ReportMapper;
import com.enixlin.jmrc.service.ReportService;

/**
 * @author linzhenhuan
 *
 */
@Service
public class ReportServiceImpl implements ReportService  {

	@Autowired
	private ReportMapper reportMapper;
	
	
	/* (non-Javadoc)
	 * @see com.enixlin.jmrc.service.BaseService#add(java.lang.Object)
	 */
	@Override
	public Report add(Report obj) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.enixlin.jmrc.service.BaseService#update(java.lang.Object)
	 */
	@Override
	public Report update(Report obj) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.enixlin.jmrc.service.BaseService#delete(java.lang.Object)
	 */
	@Override
	public Report delete(Report obj) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.enixlin.jmrc.service.BaseService#getAll()
	 */
	@Override
	public List<Report> getAll() {
		// TODO Auto-generated method stub
		return reportMapper.selectAll();
	}

	/* (non-Javadoc)
	 * @see com.enixlin.jmrc.service.BaseService#getById(java.lang.Long)
	 */
	@Override
	public Report getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.enixlin.jmrc.service.BaseService#getByName(java.lang.String)
	 */
	@Override
	public Report getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.enixlin.jmrc.service.BaseService#isExist(java.lang.Object)
	 */
	@Override
	public Boolean isExist(Report obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
