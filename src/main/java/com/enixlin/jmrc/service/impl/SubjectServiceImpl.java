package com.enixlin.jmrc.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enixlin.jmrc.mapper.SubjectMapper;
import com.enixlin.jmrc.service.SubjectService;
import com.enixlin.jmrc.util.Utils;

@Service
public class SubjectServiceImpl implements SubjectService {
@Autowired SubjectMapper sm;

    public String getLastReportDate() {
        // TODO Auto-generated method stub
        return sm.getLastReportDate();
    }

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getDepositSubjects(String date) {
		// TODO Auto-generated method stub
		Utils utils=new Utils();
		String date_pre=utils.compareYearLastDate(date);
		
		
		
		ArrayList<String> subjects=new ArrayList<String>();
		
		subjects.add("2001");
		subjects.add("2014");
		subjects.add("2003");
		subjects.add("2004");
		ArrayList<LinkedHashMap<String, Object>> arr_agent=sm.getDepositSubjectsAgent(date, subjects);
		ArrayList<LinkedHashMap<String, Object>> arr_self=sm.getDepositSubjectsSelf(date, subjects);
		for (int j = 0; j < arr_self.size(); j++) {
			arr_self.get(j).put("range","self");
		}
		for (int i = 0; i < arr_agent.size(); i++) {
			arr_agent.get(i).put("range","agent");
			arr_self.add(arr_agent.get(i));
		}
		return arr_self;
		

		
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getIncomeSubject(String date) {
		// TODO Auto-generated method stub
		sm.getIncomeSubject(date);
		return null;
	}

}