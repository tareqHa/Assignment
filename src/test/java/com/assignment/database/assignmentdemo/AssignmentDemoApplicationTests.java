package com.assignment.database.assignmentdemo;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.assignment.database.assignmentdemo.entity.Note;
import com.assignment.database.assignmentdemo.jdbc.MainRESTController;
import com.assignment.database.assignmentdemo.jdbc.NoteJdbcDao;

import org.springframework.test.web.servlet.MockMvc;
 
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
 
@SpringBootTest(classes=AssignmentDemoApplication.class)

@RunWith(SpringRunner.class)

@WebAppConfiguration
 
public class AssignmentDemoApplicationTests {

    @Autowired
	private WebApplicationContext wac;
    private MockMvc mockMvc;
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        
    }
    
	@Autowired
	JdbcTemplate jdbcTemplate;
	

	@Test 
	public void startTesting() throws Exception{
		setUp();
		isAddingOk();
		isUpdatingOk();
		isDeleteOk();
	}
	

	public void isAddingOk() throws Exception {
		// add one note
		mockMvc.perform(post("http://localhost:8080/note")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content("{\"title\":\"Tareq\", \"content\":\"Good Evening\"}"))
		.andExpect(status().isOk());
		
		// add another note
		mockMvc.perform(post("http://localhost:8080/note")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content("{\"title\":\"Rami\", \"content\":\"Good afternoon\"}"))
		.andExpect(status().isOk());
		
		// test get for notes added
		mockMvc.perform(get("http://localhost:8080/note")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$[0].title", is("Rami")))
		.andExpect(jsonPath("$[1].title", is("Tareq")));
		
		
		mockMvc.perform(get("http://localhost:8080/history")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$[0].title", is("Tareq")))
		.andExpect(jsonPath("$[0].change", is("Note created")));
		
		
	}
	
	public void isUpdatingOk() throws Exception {
		
		// update the second note
		mockMvc.perform(put("http://localhost:8080/note")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content("{\"title\":\"Rami\", \"content\":\"Good morning\"}"))
		.andExpect(status().isOk());
		
		// test the result
		mockMvc.perform(get("http://localhost:8080/note")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].title", is("Rami")))
		.andExpect(jsonPath("$[0].content", is("Good morning")));
		
		// test the history record of the second note
		mockMvc.perform(get("http://localhost:8080/history/Rami")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].content", is("Good afternoon")))
		.andExpect(jsonPath("$[1].content", is("Good morning")));
		
	}
	
	
	public void isDeleteOk() throws Exception {
		
		// delete the second record
		mockMvc.perform(delete("http://localhost:8080/note")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content("{\"title\":\"Rami\"}"))
		.andExpect(status().isOk());
		
		// test the notes database for deletion
		mockMvc.perform(get("http://localhost:8080/note")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].title", is("Tareq")));
		
		// test the history of deleted note in history database
		mockMvc.perform(get("http://localhost:8080/history/Rami")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(3)))
		.andExpect(jsonPath("$[0].change", is("Note created")))
		.andExpect(jsonPath("$[1].change", is("Note updated")))
		.andExpect(jsonPath("$[2].change", is("Note deleted")));
		
	}
	/**
	 * creating two tables, note and history
	 */
	public void setUp() {
		
		String sql1 = "create table NOTEE " + 
				"(  " + 
				"title varchar(50) not null, " + 
				"content varchar(255) not null, " + 
				"created timestamp default CURRENT_TIMESTAMP," + 
				"modified timestamp default CURRENT_TIMESTAMP," + 
				"version integer, " + 
				"primary key(title)" + 
				"); ";
		String sql2 = "create table HISTORY " + 
				"( " + 
				"id integer not null IDENTITY," + 
				"title varchar(50) not null," + 
				"content varchar(255) not null, " + 
				"created timestamp default CURRENT_TIMESTAMP," + 
				"modified timestamp default CURRENT_TIMESTAMP," + 
				"version integer," + 
				"change varchar(50) not null," + 
				"primary key(id)" + 
				");";
		
		jdbcTemplate.execute(sql1);
		jdbcTemplate.execute(sql2);
	}
}
