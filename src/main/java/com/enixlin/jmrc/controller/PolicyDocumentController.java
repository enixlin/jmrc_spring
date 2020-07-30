
package com.enixlin.jmrc.controller;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enixlin.jmrc.entity.PolicyDocumentNote;
import com.enixlin.jmrc.policydocument.HtmlFile;
import com.enixlin.jmrc.policydocument.PolicyFileEntity;
import com.enixlin.jmrc.service.PolicyDocumentService;

@RestController
@RequestMapping("/policydocument")
public class PolicyDocumentController {
	
	@Autowired
	private PolicyDocumentService DocumentService;
	
	@RequestMapping("/query")
	public java.util.ArrayList<PolicyFileEntity> getPolicyFileByKeyWord(HttpServletRequest req, HttpServletResponse res) {
		String keyword=req.getParameter("keyWord");
		String state=req.getParameter("state");
		System.out.println(state);
		if(state.equals("true")) {
			state="0"; //只查有效文件
			System.out.println("只查有效文件");
		}else {
			state="2"; //所有文件
			System.out.println("只查所有文件");
		}
		return DocumentService.getPolicyFileByKeyWord(keyword,state);
	}
	
	
	@RequestMapping("/queryAll")
	public java.util.ArrayList<PolicyFileEntity> getAllPolicyFile(HttpServletRequest req, HttpServletResponse res) {
		return DocumentService.getAllPolicyFile();
	}
	 
	
	@RequestMapping("/queryBydId")
	public java.util.ArrayList<HtmlFile>  getPolicyFileHtmlBydId(HttpServletRequest req, HttpServletResponse res) {
		String dId=req.getParameter("dId");
		return DocumentService.getPolicyFileHtmlBydId(dId);
		
	}
	
	
	@RequestMapping("/queryNoteBydId")
	public PolicyDocumentNote getPolicyNoteBydId(HttpServletRequest req, HttpServletResponse res) {
		int id=Integer.parseInt(req.getParameter("id"));
		return DocumentService.getNotesById(id);
		
	}
	
	
	@RequestMapping("/queryUserNoteBydId")
	public PolicyDocumentNote queryUserNoteBydId(HttpServletRequest req, HttpServletResponse res) {
		int dId=Integer.parseInt(req.getParameter("dId"));
		int userId=Integer.parseInt(req.getParameter("userId"));
		PolicyDocumentNote note=new PolicyDocumentNote();
		note.setdId(dId);
		note.setUserId(userId);
		return DocumentService.getUserNoteByNoteId(note);
		
	}
	
	@RequestMapping("/addNotes")
	public PolicyDocumentNote addNote(HttpServletRequest req, HttpServletResponse res, HttpSession session) {
		int dId=Integer.parseInt(req.getParameter("dId"));
		int userId=Integer.parseInt(req.getParameter("userId"));
		boolean overWrite=Boolean.parseBoolean(req.getParameter("overWrite"));
		PolicyDocumentNote note=new PolicyDocumentNote() ;
		note.setdId(dId);
		note.setUserId(userId);
		note.setOverWrite(overWrite);

		return DocumentService.addPolicyDocumentNote(note);
		
	}
	
	@RequestMapping("/openNotes")
	public PolicyDocumentNote openNotes(HttpServletRequest req, HttpServletResponse res, HttpSession session) {
		int dId=Integer.parseInt(req.getParameter("dId"));
		int userId=Integer.parseInt(req.getParameter("userId"));
		boolean overWrite=Boolean.parseBoolean(req.getParameter("overWrite"));
		PolicyDocumentNote note=new PolicyDocumentNote() ;
		note.setdId(dId);
		note.setUserId(userId);
		note.setOverWrite(overWrite);

		return DocumentService.openPolicyDocumentNote(note);
		
	}
	
	
	@RequestMapping("/saveNotes")
	public PolicyDocumentNote saveNotes(HttpServletRequest req, HttpServletResponse res, HttpSession session) {
		int dId=Integer.parseInt(req.getParameter("dId"));
		int id=Integer.parseInt(req.getParameter("id"));
		int userId=Integer.parseInt(req.getParameter("userId"));
		String content=req.getParameter("content");
		Timestamp modify=new Timestamp(new Date().getTime());
		PolicyDocumentNote note=new PolicyDocumentNote() ;
		note.setdId(dId);
		note.setUserId(userId);
		note.setModify(modify);
		note.setContent(content);
		note.setId(id);
		return DocumentService.savePolicyDocumentNote(note);
		
	}
	
	
	@RequestMapping("/getPolicyNoteBydId")
	public ArrayList<PolicyDocumentNote> getPolicyNoteBydId(HttpServletRequest req, HttpServletResponse res, HttpSession session){
		String dId=req.getParameter("dId");
	return	DocumentService.getPolicyNoteBydId(dId);
		 
		
	} 
	
	
	

}
