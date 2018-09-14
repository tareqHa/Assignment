package com.assignment.database.assignmentdemo.jdbc;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.coyote.Response;
import org.hsqldb.cmdline.SqlToolError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.database.assignmentdemo.entity.*;

@RestController
public class MainRESTController {
	
	
	@Autowired
	private NoteJdbcDao noteJdbcDao;
	
	
	@RequestMapping(value = "/note",
			method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public List<Note> getNotes() throws SqlToolError, IOException, SQLException{
		return noteJdbcDao.findAll();
	}
	
	@RequestMapping(value = "/history",
			method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public List<History> getAllHistory() throws SqlToolError, IOException, SQLException{
		return noteJdbcDao.getAllHistory();
	}
	
	@RequestMapping(value = "/note/{noteTitle}",
			method=RequestMethod.GET,
			produces= {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<Note> getNote(@PathVariable("noteTitle") String noteTitle) throws SqlToolError, IOException, SQLException {
		System.out.println("noteTitle = " + noteTitle);
		return new ResponseEntity<>(noteJdbcDao.getNote(noteTitle), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/history/{historyNote}",
			method=RequestMethod.GET,
			produces= {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<List<History>> getHistory(@PathVariable("historyNote") String historyNote) throws SqlToolError, IOException, SQLException {
		return new ResponseEntity<>(noteJdbcDao.getHistory(historyNote), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/note",
			method=RequestMethod.POST,
			produces= {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<List<Note>> addNote(@RequestBody Note note) throws SqlToolError, IOException, SQLException {
		
		// check if title and content are filled
		if (note.getTitle().isEmpty() || note.getContent().isEmpty())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	// return 400 error
		
		noteJdbcDao.addNote(note);
		return new ResponseEntity<>(noteJdbcDao.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/note",
			method=RequestMethod.PUT,
			produces= {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public void updateNote(@RequestBody Note note) throws SqlToolError, IOException, SQLException {
		noteJdbcDao.updateNote(note);
	}
	
	@RequestMapping(value = "/note",
			method=RequestMethod.DELETE,
			produces= {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<List<Note>> deleteNote(@RequestBody Note note) throws SqlToolError, IOException, SQLException {
		noteJdbcDao.deleteNote(note.getTitle());
		return new ResponseEntity<>(noteJdbcDao.findAll(), HttpStatus.OK);
	}
	
}
