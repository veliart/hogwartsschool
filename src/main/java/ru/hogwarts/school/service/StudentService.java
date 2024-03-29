package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {


    Student addStudent(Student student); //CREATE
    Student getStudentInfo(Long id); //READ
    List<Student> getAllStudent(); //READ
    Student editStudent(Long id, Student student); //UPDATE
    void deleteStudent(Long id); //DELETE
    List<Student> getStudentsByAge(int age);

    List<Student> getStudentsAgeBetween(int ageFrom, int ageTo);
    Faculty getStudentFaculty(long id);

    int getAmountStudents();

    Long getAverageAgeStudents();

    List<Student> getLastFiveStudents();
}
