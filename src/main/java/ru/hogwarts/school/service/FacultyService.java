package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.List;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty); //CREATE
    Faculty getFacultyInfo(Long id); // READ
    List<Faculty> getAllFaculties(); //READ
    Faculty editFaculty(Long id, Faculty faculty); //UPDATE
    Faculty deleteFaculty(Long id); //DELETE
    List<Faculty> getFacultiesByColor(String color);

}
