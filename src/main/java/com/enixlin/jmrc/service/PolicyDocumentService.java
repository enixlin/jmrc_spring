package com.enixlin.jmrc.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.enixlin.jmrc.entity.PolicyDocumentNote;
import com.enixlin.jmrc.policydocument.HtmlFile;
import com.enixlin.jmrc.policydocument.PolicyFileEntity;;

@Service
public interface PolicyDocumentService {

	public java.util.ArrayList<PolicyFileEntity> getPolicyFileByKeyWord(String keyword, String state);

	public java.util.ArrayList<PolicyFileEntity> getAllPolicyFile();

	public java.util.ArrayList<HtmlFile> getPolicyFileHtmlBydId(String dId);

	public boolean fetchUpdatePackage(String url);

	public ArrayList<PolicyDocumentNote> getNotesByUserId(int userId);

	public PolicyDocumentNote getNotesById(int Id);

	public PolicyDocumentNote addPolicyDocumentNote(PolicyDocumentNote note);
	
	public PolicyDocumentNote openPolicyDocumentNote(PolicyDocumentNote note);

	public int removePolicyDocumentNote(PolicyDocumentNote note);

	public PolicyDocumentNote savePolicyDocumentNote(PolicyDocumentNote note);
	
	public PolicyDocumentNote getUserNoteByNoteId(PolicyDocumentNote note);

	public ArrayList<PolicyDocumentNote> getPolicyNoteBydId(String dId);

	public PolicyDocumentNote queryNoteById(int id);

}
