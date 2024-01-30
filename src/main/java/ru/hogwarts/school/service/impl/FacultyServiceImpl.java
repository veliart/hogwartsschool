package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final Map<Long, Faculty> faculties = new HashMap<>();
    private static long currentId = 1;

    @Override
    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(currentId++);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty getFacultyInfo(Long id) {
        return faculties.get(id);
    }

    @Override
    public List<Faculty> getAllFaculties() {
        return faculties.values().stream().toList();
    }

    @Override
    public Faculty editFaculty(Long id, Faculty faculty) {
        Faculty editableFaculty = faculties.get(id);
        editableFaculty.setName(faculty.getName());
        editableFaculty.setColor(faculty.getColor());
        return editableFaculty;
    }

    @Override
    public Faculty deleteFaculty(Long id) {
        return faculties.remove(id);
    }

    @Override
    public List<Faculty> getFacultiesByColor(String color) {
        return faculties.values()
                .stream()
                .filter(f -> f.getColor().toLowerCase().equals(color.toLowerCase()))
                .collect(Collectors.toList());
    }
}
