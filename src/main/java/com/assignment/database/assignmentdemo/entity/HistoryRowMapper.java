package com.assignment.database.assignmentdemo.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class HistoryRowMapper implements RowMapper{

	@Override
	public Object  mapRow(ResultSet rs, int rowNum) throws SQLException {
		History history = new History();
		history.setId(rs.getInt("id"));
		history.setTitle(rs.getString("title"));
		history.setContent(rs.getString("content"));
		history.setCreated(rs.getTimestamp("created"));
		history.setModified(rs.getTimestamp("modified"));
		history.setChange(rs.getString("change"));
		history.setVersion(rs.getInt("version"));
		return history;
	}

	
	
}
