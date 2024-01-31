package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFacultyInfo(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    @Override
    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    @Override
    public Faculty editFaculty(Long id, Faculty faculty) {
       return facultyRepository.findById(id).map(editableFaculty -> {
           editableFaculty.setName(faculty.getName());
           editableFaculty.setColor(faculty.getColor());
           return facultyRepository.save(editableFaculty);
       }).orElse(null);
    }

    @Override
    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    @Override
    public List<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findAll()
                .stream()
                .filter(f -> f.getColor().toLowerCase().equals(color.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Faculty> getFacultiesByColorOrName(String name, String color) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    @Override
    public List<Student> getStudentsByFaculty(long id) {
        return facultyRepository.findById(id)
                .map(faculty -> faculty.getStudents())
                .orElse(null);
    }
}
