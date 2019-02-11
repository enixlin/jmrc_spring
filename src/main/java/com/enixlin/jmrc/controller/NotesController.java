package com.enixlin.jmrc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enixlin.jmrc.service.PolicyDocumentService;

@RequestMapping("/notes")
public class NotesController {
	
	
	@Autowired
	private PolicyDocumentService policyDocumentService;
	
	


}
