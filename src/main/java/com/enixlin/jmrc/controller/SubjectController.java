package com.enixlin.jmrc.controller;

import com.enixlin.jmrc.service.SubjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("subject")
public class SubjectController {

    @Autowired SubjectService ss;

    public String getLastReportDate(){
        return ss.getLastReportDate();
    }

}