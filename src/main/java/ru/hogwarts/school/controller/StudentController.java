package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("{id}")
    public Student getStudentInfo(@PathVariable Long id) {
        return studentService.getStudentInfo(id);
    }

    @GetMapping
    public List<Student> getAllStudent() {
        return studentService.getAllStudent();
    }

    @PutMapping("{id}")
    public Student editStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.editStudent(id, student);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("age/{age}")
    public List<Student> getStudentsByAge(@PathVariable int age) {
        return studentService.getStudentsByAge(age);
    }
//
    @GetMapping("findStudentsBetweenAge")
    public List<Student> getStudentsAgeBetween(@RequestParam int ageFrom, @RequestParam int ageTo) {
        return studentService.getStudentsAgeBetween(ageFrom, ageTo);
    }
    @GetMapping("{id}/faculty")
    public Faculty getStudentFaculty(@PathVariable Long id) {
        return studentService.getStudentFaculty(id);
    }
    @GetMapping("getAmountStudents")
    public int getAmountStudents() {
        return studentService.getAmountStudents();
    };

    @GetMapping("getAverageAgeStudents")
    public Long getAverageAgeStudents() {
        return studentService.getAverageAgeStudents();

    };
    @GetMapping("getLastFiveStudents")
    public List<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    };

    @GetMapping("studentNameBeginLetterA")
    public List<String> getAllStudentNameBeginLetterA() {
        return studentService.getAllStudentNameBeginLetterA();
    }

    @GetMapping("averageAge")
    public Double getAverageAge() {
        return studentService.getAverageAge();
    }

}
