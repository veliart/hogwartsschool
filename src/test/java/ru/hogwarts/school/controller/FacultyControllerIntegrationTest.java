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
import ru.hogwarts.school.repository.FacultyRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FacultyRepository facultyRepository;

    @BeforeEach
    public void clearDataBase() {
        facultyRepository.deleteAll();
    }

    @Test  // test POST mapping
    void shouldCreateFaculty() {
        Faculty faculty = new Faculty("testName", "testColor");

        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/faculty",
                faculty,
                Faculty.class
        );
        Assertions.assertNotNull(facultyResponseEntity);
        Assertions.assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        Faculty actualFaculty = facultyResponseEntity.getBody();
        Assertions.assertNotNull(actualFaculty.getId());
        Assertions.assertEquals(actualFaculty.getName(), faculty.getName());
        Assertions.assertEquals(actualFaculty.getColor(), faculty.getColor());
    }

    @Test  // test PUT mapping
    void shouldUpdateFaculty() {
        Faculty faculty = new Faculty("testName", "testColor");
        faculty = facultyRepository.save(faculty);

        Faculty facultyForUpdate = new Faculty("newName", "newColor");

        HttpEntity<Faculty> entity = new HttpEntity<>(facultyForUpdate);

        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + faculty.getId(),
                HttpMethod.PUT,
                entity,
                Faculty.class
        );
        Assertions.assertNotNull(facultyResponseEntity);
        Assertions.assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Faculty actualFaculty = facultyResponseEntity.getBody();
        Assertions.assertEquals(actualFaculty.getId(), faculty.getId());
        Assertions.assertEquals(actualFaculty.getColor(), facultyForUpdate.getColor());
        Assertions.assertEquals(actualFaculty.getName(), facultyForUpdate.getName());
    }

    @Test  //test GET Mapping
    void shouldGetFaculty() {
        Faculty faculty = new Faculty("testName", "testColor");
        faculty = facultyRepository.save(faculty);

        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/" + faculty.getId(),
                Faculty.class
        );
        Assertions.assertNotNull(facultyResponseEntity);
        Assertions.assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Faculty actualFaculty = facultyResponseEntity.getBody();
        Assertions.assertEquals(actualFaculty.getId(), faculty.getId());
        Assertions.assertEquals(actualFaculty.getColor(), faculty.getColor());
        Assertions.assertEquals(actualFaculty.getName(), faculty.getName());
    }

    @Test //test DELETE mapping
    void shouldDeleteFaculty() {
        Faculty faculty = new Faculty("testName", "testColor");
        faculty = facultyRepository.save(faculty);

        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + faculty.getId(),
                HttpMethod.DELETE,
                null,
                Faculty.class
        );
        Assertions.assertNotNull(facultyResponseEntity);
        Assertions.assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        org.assertj.core.api.Assertions.assertThat(facultyRepository.findById(faculty.getId())).isNotPresent();
    }
}
