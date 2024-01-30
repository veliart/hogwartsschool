package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final Map<Long, Student> students = new HashMap<>();

    private static long currentId = 1;

    @Override
    public Student addStudent(Student student) {
        student.setId(currentId++);
        students.put(student.getId(), student);
        return student;
    }

    @Override
    public Student getStudentInfo(Long id) {
        return students.get(id);
    }

    @Override
    public List<Student> getAllStudent() {
        return students.values().stream().toList();
    }

    @Override
    public Student editStudent(Long id, Student student) {
        Student editableStudent = students.get(id);
        editableStudent.setName(student.getName());
        editableStudent.setAge(student.getAge());

        return editableStudent;
    }

    @Override
    public Student deleteStudent(Long id) {
        return students.remove(id);
    }

    @Override
    public List<Student> getStudentsByAge(int age) {
        return students.values()
                .stream()
                .filter(s -> s.getAge() == age)
                .collect(Collectors.toList());
    }
}
