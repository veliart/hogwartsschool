package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        logger.info("Log info: Method addStudent is invoke.");
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentInfo(Long id) {
        logger.info("Log info: Method getStudentInfo is invoke.");
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Student> getAllStudent() {
        logger.info("Log info: Method getAllStudent is invoke.");
        return studentRepository.findAll();
    }

    @Override
    public Student editStudent(Long id, Student student) {
        logger.info("Log info: Method editStudent is invoke.");
        return studentRepository.findById(id)
                .map(editаbleStudent -> {
                    editаbleStudent.setName(student.getName());
                    editаbleStudent.setAge(student.getAge());
                    return studentRepository.save(editаbleStudent);
                }).orElse(null);
    }

    @Override
    public void deleteStudent(Long id) {
        logger.info("Log info: Method deleteStudent is invoke.");
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> getStudentsByAge(int age) {
        logger.info("Log info: Method getStudentsByAge is invoke.");
        return studentRepository.findAll().stream()
                .filter(s -> s.getAge() == age)
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> getStudentsAgeBetween(int ageFrom, int ageTo) {
        logger.info("Log info: Method getStudentsAgeBetween is invoke.");
        return studentRepository.findByAgeBetween(ageFrom, ageTo);
    }

    @Override
    public Faculty getStudentFaculty(long id) {
        logger.info("Log info: Method getStudentFaculty is invoke.");
        return studentRepository.findById(id)
                .map(student -> student.getFaculty())
                .orElse(null);

    }

    @Override
    public int getAmountStudents() {
        logger.info("Log info: Method getAmountStudents is invoke.");
        return studentRepository.getAmountStudents();
    }

    @Override
    public Long getAverageAgeStudents() {
        logger.info("Log info: Method getAverageAgeStudents is invoke.");
        return studentRepository.getAverageAgeStudents();
    }

    @Override
    public List<Student> getLastFiveStudents() {
        logger.info("Log info: Method getLastFiveStudents is invoke.");
        return studentRepository.getLastFiveStudents();
    }

    @Override
    public List<String> getAllStudentNameBeginLetterA() {
        return studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(it -> it.startsWith("A"))
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageAge() {
        return studentRepository.findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
    }
}
