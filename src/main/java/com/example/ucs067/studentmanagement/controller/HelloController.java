package com.example.ucs067.studentmanagement.controller;

import com.example.ucs067.studentmanagement.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {

    private List<Student> students = new ArrayList<>(
            List.of(
                    new Student(1, "Rahul", "rahul@example.com"),
                    new Student(2, "Priya", "priya@example.com"),
                    new Student(3, "Amit", "amit@example.com")
            )
    );

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Spring Boot!";
    }

    @GetMapping("/student")
    public Student getStudent() {
        return new Student(
                1,
                "Rahul",
                "rahul@example.com"
        );
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return students;
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        students.add(student);
        return student;
    }
}