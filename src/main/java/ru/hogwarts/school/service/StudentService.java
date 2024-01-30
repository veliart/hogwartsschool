package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {
    Student addStudent(Student student); //CREATE
    Student getStudentInfo(Long id); //READ
    List<Student> getAllStudent(); //READ
    Student editStudent(Long id, Student student); //UPDATE
    Student deleteStudent(Long id); //DELETE
    List<Student> getStudentsByAge(int age);
}
