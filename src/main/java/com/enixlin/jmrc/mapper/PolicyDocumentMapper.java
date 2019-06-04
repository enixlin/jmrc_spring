package com.enixlin.jmrc.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.enixlin.jmrc.entity.PolicyDocumentNote;
import com.enixlin.jmrc.policydocument.HtmlFile;
import com.enixlin.jmrc.policydocument.PolicyFileEntity;

@Mapper
public interface PolicyDocumentMapper {

	@Select("select docNum,title,tid,did,cid,state  from policydocument_list   where ( policydocument_list.content like CONCAT('%',#{param1},'%')  or policydocument_list.title like CONCAT('%',#{param1},'%') ) and policydocument_list.state<>2 ")
	public java.util.ArrayList<PolicyFileEntity> getPolicyFileByKeyWord(String keyword, String state);

	@Select("select docNum,title,tid,did,cid,state from policydocument_list where content like CONCAT('%',#{param1},'%') or title like CONCAT('%',#{param1},'%') ")
	public java.util.ArrayList<PolicyFileEntity> getPolicyFileByKeyWordAll(String keyword, String state);

	@Select("select docNum ,title ,dId from policydocument_list limit 30 ")
	public java.util.ArrayList<PolicyFileEntity> getAllPolicyFile();

	@Select("select * from policydocument_html where dId=#{dId}")
	public java.util.ArrayList<HtmlFile> getPolicyFileHtmlBydId(String dId);

	@Select("select * from policydocument_notes where user_id=#{userId}")
	public java.util.ArrayList<PolicyDocumentNote> getNotesByUserId(int userId);

	@Select("select * from policydocument_notes where id=#{Id}")
	public PolicyDocumentNote getNotesById(int Id);

	@Insert("insert into policydocument_notes (id,dId,content,user_id,modify_time) value(#{id},#{dId},#{content},#{userId},#{modify})")
	public int addPolicyDocumentNote(PolicyDocumentNote note);

	@Delete("delete from policydocument_notes where id=#{id}")
	public int removePolicyDocumentNote(PolicyDocumentNote note);

	@Update("update policydocument_notes set dId=#{dId},content=#{content} ,user_id=#{userId},modify_time=#{modify} where id=#{id}")
	public int savePolicyDocumentNote(PolicyDocumentNote note);

	@Select("select * from policydocument_notes where user_id=#{userId} and dId=#{dId}")
	public PolicyDocumentNote getUserNoteByNoteId(PolicyDocumentNote note);

	@Select("select * from policydocument_notes left join policydocument_list on  policydocument_notes.dId=policydocument_list.dId left join user on  policydocument_notes.user_id=user.id  where policydocument_notes.dId=#{dId}")
	public ArrayList<PolicyDocumentNote> getPolicyNoteBydId(String dId);

}
