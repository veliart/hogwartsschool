package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    public void clearDataBase() {
        studentRepository.deleteAll();
    }

    @Test
        // test POST mapping
    void shouldCreateStudent() {
        Student student = new Student("testName", 20);

        ResponseEntity<Student> studentResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                student,
                Student.class
        );
        Assertions.assertNotNull(studentResponseEntity);
        Assertions.assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        Student actualStudent = studentResponseEntity.getBody();
        Assertions.assertNotNull(actualStudent.getId());
        Assertions.assertEquals(actualStudent.getName(), student.getName());
        Assertions.assertEquals(actualStudent.getAge(), student.getAge());
    }

    @Test  // test PUT mapping
    void shouldUpdateStudent() {
        Student student = new Student("testName", 20);
        student = studentRepository.save(student);

        Student studentForUpdate = new Student("newName", 10);

        HttpEntity<Student> entity = new HttpEntity<>(studentForUpdate);

        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student/" + student.getId(),
                HttpMethod.PUT,
                entity,
                Student.class
        );
        Assertions.assertNotNull(studentResponseEntity);
        Assertions.assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Student actualStudent = studentResponseEntity.getBody();
        Assertions.assertEquals(actualStudent.getId(), student.getId());
        Assertions.assertEquals(actualStudent.getAge(), studentForUpdate.getAge());
        Assertions.assertEquals(actualStudent.getName(), studentForUpdate.getName());
    }

    @Test  //test GET Mapping
    void shouldGetFaculty() {
        Student student = new Student("testName", 20);
        student = studentRepository.save(student);

        ResponseEntity<Student> studentResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + student.getId(),
                Student.class
        );
        Assertions.assertNotNull(studentResponseEntity);
        Assertions.assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Student actualStudent = studentResponseEntity.getBody();
        Assertions.assertEquals(actualStudent.getId(), student.getId());
        Assertions.assertEquals(actualStudent.getAge(), student.getAge());
        Assertions.assertEquals(actualStudent.getName(), student.getName());
    }

    @Test //test DELETE mapping
    void shouldDeleteFaculty() {
        Student student = new Student("testName", 20);
        student = studentRepository.save(student);

        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student/" + student.getId(),
                HttpMethod.DELETE,
                null,
                Student.class
        );
        Assertions.assertNotNull(studentResponseEntity);
        Assertions.assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        org.assertj.core.api.Assertions.assertThat(studentRepository.findById(student.getId())).isNotPresent();
    }
}
