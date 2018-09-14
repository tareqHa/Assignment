The project was developed in Java, Spring framework.

# Requirements

Java jdk 1.8
Apache Maven Tool

# Steps

1- clone the project from repository: git clone https://github.com/tareqHa/Assignment.git

2- navigate to the local directory and execute the commands:
  * mvn compile
  * mvn package: this will compile the code, run the tests and package the code up in a JAR file in target directory.

Junit framework was used to write unit tests, it uses HSQLDB in-memory database and test each htttp verb and their functionality.

We can run mvn test to run the tests.

Navigate to target folder and run the project: 

java -jar assignment-demo-0.0.1-SNAPSHOT.jar

The database and tables will be setup automatically when running the project, two tables are created, note and history.

The project uses HSQLDB, the data are persistent on disk storage, in the same directory where the project exists.

The controller convert the data to/from json format.

Now the project is running, we can test is with curl.


# Reading

To read all notes: curl -X GET http://localhost:8080/note .
To read all history of all notes: curl -X GET http://localhost:8080/history .
To read particular note: curl -X GET http://localhost:8080/note/{noteTitle} , noteTitle variable is the title of the note.
To read particular note history: curl -X GET http://localhost:8080/history/{noteTitle} .

# Creating

To create a note: curl -d '{"title":"Hello", "content":"Good Evening"}' -H "Content-Type: application/json" -X POST http://localhost:8080/note .

This will create note with "Hello" title and "Good Evening" content.
The created, modified date and version number will be created automatically.
If the either of the fields is not filled, the service will return http 400 error code.

# Updating

To update a note: curl -d '{"title":"Hello", "content":"Good Morning"}' -H "Content-Type: application/json" -X PUT http://localhost:8080/note .

This will update the note which has "Hello" as title and put the new content.

# Deleting

To delete a note: curl -d '{"title":"Hello"}' -H "Content-Type: application/json" -X DELETE http://localhost:8080/note .

This will delete the note which has "Hello" as title.


# Example

Add a note: curl -d '{"title":"Tareq", "content":"Good Evening"}' -H "Content-Type: application/json" -X POST http://localhost:8080/note .

Add another: curl -d '{"title":"Rami", "content":"Good Afternoon"}' -H "Content-Type: application/json" -X POST http://localhost:8080/note .

Update the second note: curl -d '{"title":"Rami", "content":"Good Morning"}' -H "Content-Type: application/json" -X PUT http://localhost:8080/note .

Delete the second note: curl -d '{"title":"Rami"}' -H "Content-Type: application/json" -X DELETE http://localhost:8080/note .

Check the history of the second note: curl -X GET http://localhost:8080/history/Rami .
Check the note table: curl -X GET http://localhost:8080/note .



