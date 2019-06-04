package com.enixlin.jmrc.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enixlin.jmrc.entity.PolicyDocumentNote;
import com.enixlin.jmrc.mapper.PolicyDocumentMapper;
import com.enixlin.jmrc.policydocument.HtmlFile;
import com.enixlin.jmrc.policydocument.PolicyFileEntity;
import com.enixlin.jmrc.service.PolicyDocumentService;

@Service
public class PolicyDocumentServiceImpl implements PolicyDocumentService {

	@Autowired
	private PolicyDocumentMapper policyDocumentMapper;

	@Override
	public java.util.ArrayList<PolicyFileEntity> getAllPolicyFile() {
		// TODO Auto-generated method stub
		return policyDocumentMapper.getAllPolicyFile();
	}
	@Override
	public java.util.ArrayList<HtmlFile> getPolicyFileHtmlBydId(String dId) {
		// TODO Auto-generated method stub
		return policyDocumentMapper.getPolicyFileHtmlBydId(dId);
	}
	@Override
	public java.util.ArrayList<PolicyFileEntity> getPolicyFileByKeyWord(String keyword, String state) {
		// TODO Auto-generated method stub
		if(state.equals("2") ){
			System.out.println("do all");
			return policyDocumentMapper.getPolicyFileByKeyWordAll(keyword,state);
		}else {
			
			System.out.println("do sigle");
			return policyDocumentMapper.getPolicyFileByKeyWord(keyword,state);
//			System.out.println("do sigle");
		}
	}
	@Override
	public boolean fetchUpdatePackage(String url) {
		// TODO Auto-generated method stub
		System.out.println("file path is ...");
	
		
		return false;
	}
	@Override
	public ArrayList<PolicyDocumentNote> getNotesByUserId(int userId) {
		return policyDocumentMapper.getNotesByUserId(userId);
		
	}
	@Override
	public PolicyDocumentNote getNotesById(int Id) {
		
		return policyDocumentMapper.getNotesById(Id);
	}
	@Override
	public PolicyDocumentNote addPolicyDocumentNote(PolicyDocumentNote note) {

			if(policyDocumentMapper.addPolicyDocumentNote(note)>0) {
				return policyDocumentMapper.getUserNoteByNoteId(note);
			}else {
				return null;
			}

	}
	@Override
	public PolicyDocumentNote getUserNoteByNoteId(PolicyDocumentNote note) {
		// TODO Auto-generated method stub
		return policyDocumentMapper.getUserNoteByNoteId(note);
	}
	@Override
	public int removePolicyDocumentNote(PolicyDocumentNote note) {
		return policyDocumentMapper.removePolicyDocumentNote(note);
	}
	@Override
	public PolicyDocumentNote savePolicyDocumentNote(PolicyDocumentNote note) {
		if(policyDocumentMapper.savePolicyDocumentNote(note)>0) {
			return policyDocumentMapper.getUserNoteByNoteId(note);
		}else {
			return null;
		}
	}
	@Override
	public PolicyDocumentNote openPolicyDocumentNote(PolicyDocumentNote note) {
		// TODO Auto-generated method stub
		//先判断是否有一篇旧笔记,如果有的话，先将原业的note返回控制器
				PolicyDocumentNote old=policyDocumentMapper.getUserNoteByNoteId(note);
				
				if(old==null) {
					//没有旧笔记，先查询文件原文
					ArrayList<HtmlFile> al_htmlFile=policyDocumentMapper.getPolicyFileHtmlBydId(String.valueOf(note.getdId()));
					Timestamp modify=new Timestamp(new Date().getTime());
					note.setContent(al_htmlFile.get(0).getHtmlContent());
					note.setModify( modify);
					return this.addPolicyDocumentNote(note);
					
				}else{
					return policyDocumentMapper.getUserNoteByNoteId(note);
				
				}
		
	}
	
	@Override
	public ArrayList<PolicyDocumentNote> getPolicyNoteBydId(String dId) {
		System.out.println("did in getNotesBydId function:");
		System.out.println(dId);
		System.out.println(policyDocumentMapper.getPolicyNoteBydId(dId));
		return policyDocumentMapper.getPolicyNoteBydId(dId);
		// TODO Auto-generated method stub
		
	}
	@Override
	public PolicyDocumentNote queryNoteById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
