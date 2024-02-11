package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FacultyService facultyService;
    @MockBean
    private AvatarService avatarService;



    @InjectMocks
    private FacultyController facultyController;

    @Test //test GET mapping
    void shouldGetFaculty() throws Exception {
        Long facultyId = 1L;
        Faculty faculty = new Faculty("testName", "testColor");


        when(facultyService.getFacultyInfo(facultyId)).thenReturn(faculty);
        mockMvc.perform(get("/faculty/{id}", facultyId))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());
    }

    @Test //test POST mapping
    void shouldAddFaculty() throws Exception {
        Long facultyId = 1L;
        Faculty faculty = new Faculty("testName", "testColor");
        Faculty savedFaculty = new Faculty("testName", "testColor");
        savedFaculty.setId(facultyId);

        when(facultyService.addFaculty(faculty)).thenReturn(savedFaculty);

        ResultActions perform = mockMvc.perform(post("/faculty")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(faculty)));

        perform
                .andExpect(jsonPath("$.id").value(savedFaculty.getId()))
                .andExpect((jsonPath("$.name").value(savedFaculty.getName())))
                .andExpect(jsonPath("$.color").value(savedFaculty.getColor()))
                .andDo(print());
    }

    @Test // test PUT mapping
    void shouldUpdateStudent() throws Exception {
        Long facultyId = 1L;
        Faculty faculty = new Faculty("testName", "testColor");
        when(facultyService.editFaculty(facultyId, faculty)).thenReturn(faculty);

        ResultActions perform = mockMvc.perform(put("/faculty/{id}", facultyId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(faculty)));

        perform
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());

    }


}
