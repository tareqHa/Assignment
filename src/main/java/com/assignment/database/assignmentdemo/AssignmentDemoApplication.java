package com.assignment.database.assignmentdemo;




import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.catalina.core.ApplicationContext;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.assignment.database.assignmentdemo.entity.Note;

import com.assignment.database.assignmentdemo.jdbc.NoteJdbcDao;
 


@SpringBootApplication
public class AssignmentDemoApplication {


	public static void main(String[] args) throws SQLException, SqlToolError, IOException {
		 SpringApplication.run(AssignmentDemoApplication.class, args);
		 
	} 
 
}

