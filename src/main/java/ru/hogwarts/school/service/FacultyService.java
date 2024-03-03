package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty); //CREATE
    Faculty getFacultyInfo(Long id); // READ
    List<Faculty> getAllFaculties(); //READ
    Faculty editFaculty(Long id, Faculty faculty); //UPDATE
    void deleteFaculty(Long id); //DELETE
    List<Faculty> getFacultiesByColor(String color);

    List<Faculty> getFacultiesByColorOrName(String name, String color);

    List<Student> getStudentsByFaculty(long id);

    String getLongFacultyName();

}
