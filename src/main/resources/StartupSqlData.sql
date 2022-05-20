DROP TABLE IF EXISTS Groups cascade;
DROP TABLE IF EXISTS Students cascade;
DROP TABLE IF EXISTS Courses cascade;
DROP TABLE IF EXISTS students_courses cascade;
DROP TABLE IF EXISTS StudentsCoursesData cascade;


CREATE TABLE Groups(
group_id SERIAL PRIMARY KEY,
group_name VARCHAR(50) NOT NULL
);

CREATE TABLE Students(
student_id serial PRIMARY KEY,
group_id INT CHECK(group_id > 0)  REFERENCES Groups(group_id) ON DELETE CASCADE,
first_name VARCHAR(25) NOT NULL,
last_name VARCHAR(25) NOT NULL
);

CREATE TABLE Courses(
course_id SERIAL PRIMARY KEY,
course_name VARCHAR(25) NOT NULL,
course_description VARCHAR(255) NOT NULL
);

CREATE TABLE Students_Courses(
student_id int NOT NULL ,
course_id int NOT NULL,
FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
FOREIGN KEY (course_id) REFERENCES Courses(course_id) ON DELETE CASCADE,
UNIQUE (student_id, course_id)
);




		
	
