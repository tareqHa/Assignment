package com.assignment.database.assignmentdemo.jdbc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.assignment.database.assignmentdemo.entity.History;
import com.assignment.database.assignmentdemo.entity.HistoryRowMapper;
import com.assignment.database.assignmentdemo.entity.Note;



@Repository
@Service
public class NoteJdbcDao  {

	@Autowired
	JdbcTemplate jdbcTemplate;


	/**
	 * execute the sql file that creates the note and history databases
	 * @throws IOException
	 * @throws SqlToolError
	 * @throws SQLException
	 */
	public void setUp() throws IOException, SqlToolError, SQLException {
		String sql = "SELECT COUNT(*) from INFORMATION_SCHEMA.SYSTEM_TABLES Where TABLE_NAME = 'NOTEE'";
		int cnt = (int) jdbcTemplate.queryForObject(sql, Integer.class);
		if (cnt == 0) {	// if the table is not created, then create it
			try(InputStream inputStream = getClass().getResourceAsStream("/setUp.sql")) {
			    SqlFile sqlFile = new SqlFile(new InputStreamReader(inputStream), "init", System.out, "UTF-8", false, new File("."));
			    Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:testdb");
			    sqlFile.setConnection(connection);
			    sqlFile.execute();
			}
		}
	}
	 
	public List<Note> findAll() throws SqlToolError, IOException, SQLException{
		setUp();
		return jdbcTemplate.query("select * from NOTEE", new BeanPropertyRowMapper<Note>(Note.class));
	}

	
	public List<History> getAllHistory() throws SqlToolError, IOException, SQLException{
		setUp();
		return jdbcTemplate.query("SELECT * from HISTORY", new HistoryRowMapper());
	}
	
	public List<History> getHistory(String title) throws SqlToolError, IOException, SQLException {
		setUp();
		return jdbcTemplate.query("SELECT * from HISTORY WHERE title = '" + title + "'", new HistoryRowMapper());
	}
	 
	public Note getNote(String title) throws SqlToolError, IOException, SQLException {
		// TODO Auto-generated method stub
		setUp();
		String sql = "select COUNT(*) from NOTEE where title = '" + title + "'";
		int cnt = (int) jdbcTemplate.queryForObject(sql, Integer.class);
		if (cnt > 0)
		return (Note) jdbcTemplate.queryForObject("select * from NOTEE where title = '" + title + "'", new BeanPropertyRowMapper<Note>(Note.class));
		return null;
	}

	public void addNote(Note note) throws SqlToolError, IOException, SQLException {
		setUp();
		String sql = "SELECT COUNT(*) from NOTEE WHERE title = '" + note.getTitle() + "'";
		int cnt = (int) jdbcTemplate.queryForObject(sql, Integer.class);
		if (cnt == 0) {
			sql = "INSERT INTO NOTEE (title, content, version) VALUES(?, ?, ?)";
			jdbcTemplate.update(sql, note.getTitle(), note.getContent(), 1);
			sql = "INSERT INTO HISTORY (title, content, version, change) VALUES(?, ?, ?, ?)";
			jdbcTemplate.update(sql, note.getTitle(), note.getContent(), 1, "Note created");
		}
	}

	public void updateNote(Note note) throws SqlToolError, IOException, SQLException {
		setUp();
		String sql = "SELECT * from NOTEE WHERE title = '" + note.getTitle() + "'";
		Note noteStored = (Note) jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Note>(Note.class));
		sql = "INSERT INTO HISTORY (title, content, created, version, change) VALUES(?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, noteStored.getTitle(), note.getContent(), noteStored.getCreated(), noteStored.getVersion() + 1, "Note updated");
		sql = "UPDATE NOTEE SET content = ? , modified = ? , version = ?";
		jdbcTemplate.update(sql, note.getContent(), Timestamp.valueOf(LocalDateTime.now()), noteStored.getVersion() + 1);
	}


	public void deleteNote(String noteTitle) throws SqlToolError, IOException, SQLException {
		setUp();
		Note note = (Note) jdbcTemplate.queryForObject("SELECT * FROM NOTEE WHERE title = '" + noteTitle + "'", new BeanPropertyRowMapper<Note>(Note.class));
		String sql = "INSERT INTO HISTORY (title, content, created, version, change) VALUES(?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, noteTitle, note.getContent(), note.getCreated(), note.getVersion(), "Note deleted");
		sql = "DELETE from NOTEE where title = '" + noteTitle + "'";
		jdbcTemplate.update(sql);
	}
	
}
