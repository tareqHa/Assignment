package com.assignment.database.assignmentdemo.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;


public class Note {


	private String title;
	private String content;
	private Timestamp created;
	private Timestamp modified;
	private int version;
	
	public Note() {
		// TODO Auto-generated constructor stub
	}
	public Note(String title, String content, int version) {
		// TODO Auto-generated constructor stub		
		this.title = title;
		this.content = content;
		this.version = version;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	 
	public String getContent() {
		return content;
	}
	public Timestamp getCreated() {
		return created;
	}
	public Timestamp getModified() {
		return modified;
	}
	public String getTitle() {
		return title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	public void setModified(Timestamp modified) {
		this.modified = modified;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	 
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("\nNote [title=%s, content=%s, created=%s, modified=%s]", title, content, created, modified);
	}
}


