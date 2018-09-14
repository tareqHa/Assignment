package com.assignment.database.assignmentdemo.entity;

import javax.persistence.Entity;


public class History extends Note{

	private int id;
	private String change;
	public History(String title, String content, int version, String change) {
		super(title, content, version);
		this.change = change;
	}
	public History() {
		// TODO Auto-generated constructor stub
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setChange(String change) {
		this.change = change;
	}
	public String getChange() {
		return change;
	}
	
}
