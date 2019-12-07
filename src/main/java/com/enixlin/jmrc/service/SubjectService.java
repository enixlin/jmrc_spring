package com.enixlin.jmrc.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface SubjectService {

	String getLastReportDate();

	ArrayList<LinkedHashMap<String, Object>> getDepositSubjects(String date);
	ArrayList<LinkedHashMap<String, Object>> getIncomeSubject(String date,String currency);

    

}