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
	private String date;

	public String getLastReportDate() {
		// TODO Auto-generated method stub
		return sm.getLastReportDate();
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getDepositSubjects(final String date) {
		// TODO Auto-generated method stub
		final Utils utils = new Utils();
		final String date_pre = utils.compareYearLastDate(date);

		final ArrayList<String> subjects = new ArrayList<String>();

		subjects.add("2001");
		subjects.add("2014");
		subjects.add("2003");
		subjects.add("2004");
		final ArrayList<LinkedHashMap<String, Object>> arr_agent = sm.getDepositSubjectsAgent(date, subjects);
		final ArrayList<LinkedHashMap<String, Object>> arr_self = sm.getDepositSubjectsSelf(date, subjects);
		for (int j = 0; j < arr_self.size(); j++) {
			arr_self.get(j).put("range", "self");
		}
		for (int i = 0; i < arr_agent.size(); i++) {
			arr_agent.get(i).put("range", "agent");
			arr_self.add(arr_agent.get(i));
		}
		return arr_self;

	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getIncomeSubject(final String date, final String currency) {
		this.date = date;
		// TODO Auto-generated method stub
		final ArrayList<String> subjects = new ArrayList<>();
		subjects.add("3301");
		subjects.add("6411");
		subjects.add("6012");
		subjects.add("6412");
		subjects.add("64210301");
		subjects.add("64210401");
		return sm.getIncomeSubject(date, subjects,currency);
	
	}

}