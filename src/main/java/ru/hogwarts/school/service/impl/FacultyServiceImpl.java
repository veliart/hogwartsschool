package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    private static final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        logger.info("Log info: Method addFaculty is invoke.");
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFacultyInfo(Long id) {
        logger.info("Log info: Method getFacultyInfo is invoke.");
        return facultyRepository.findById(id).orElse(null);
    }

    @Override
    public List<Faculty> getAllFaculties() {
        logger.info("Log info: Method getAllFaculties is invoke.");
        return facultyRepository.findAll();
    }

    @Override
    public Faculty editFaculty(Long id, Faculty faculty) {
        logger.info("Log info: Method editFaculty is invoke.");
       return facultyRepository.findById(id).map(editableFaculty -> {
           editableFaculty.setName(faculty.getName());
           editableFaculty.setColor(faculty.getColor());
           return facultyRepository.save(editableFaculty);
       }).orElse(null);
    }

    @Override
    public void deleteFaculty(Long id) {
        logger.info("Log info: Method deleteFaculty is invoke.");
        facultyRepository.deleteById(id);
    }

    @Override
    public List<Faculty> getFacultiesByColor(String color) {
        logger.info("Log info: Method getFacultiesByColor is invoke.");
        return facultyRepository.findAll()
                .stream()
                .filter(f -> f.getColor().toLowerCase().equals(color.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Faculty> getFacultiesByColorOrName(String name, String color) {
        logger.info("Log info: Method getFacultiesByColorOrName is invoke.");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    @Override
    public List<Student> getStudentsByFaculty(long id) {
        logger.info("Log info: Method getStudentsByFaculty is invoke.");
        return facultyRepository.findById(id)
                .map(faculty -> faculty.getStudents())
                .orElse(null);
    }

    @Override
    public String getLongFacultyName() {
        return facultyRepository.findAll()
                .stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse("No faculty");
    }
}
