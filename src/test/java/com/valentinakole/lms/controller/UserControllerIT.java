package com.valentinakole.lms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valentinakole.lms.dto.user.UserResponseDto;
import com.valentinakole.lms.model.User;
import com.valentinakole.lms.service.SubjectService;
import com.valentinakole.lms.service.UserService;
import com.valentinakole.lms.service.impl.LessonServiceImpl;
import com.valentinakole.lms.util.validate.ValidatePathVariable;
import com.valentinakole.lms.util.validate.ValidateUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerIT {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private LessonServiceImpl lessonService;

    @MockBean
    private SubjectService subjectService;

    @MockBean
    private BindingResult bindingResult;

    @MockBean
    private ValidateUser validateUser;

    @MockBean
    private ValidatePathVariable validatePathVariable;

    @Test
    void findById() throws Exception {
        Long id = 1L;
        User user = new User(id, "Ivan", "Ivanov", "login", "password", null,
                "ivan@mail.com", LocalDate.of(2000, 01, 01), null, "url");
        when(userService.findById(id)).thenReturn(user);

        mockMvc.perform(get("/users/{id}", id)).andDo(print()).andExpect(status().isOk());
        verify(userService).findById(id);
    }

    @Test
    void create() throws Exception {

        User user = new User();
        UserResponseDto userResponseDto = new ModelMapper().map(user, UserResponseDto.class);
        when(validateUser.validateUser(user, bindingResult)).thenReturn(user);
        when(userService.create(user)).thenReturn(user);

        String result =
                mockMvc.perform(
                                post("/users")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsBytes(userResponseDto)))
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        Assertions.assertEquals(result, objectMapper.writeValueAsString(userResponseDto));
    }

    @Test
    void update() throws Exception {
        Long id = 0L;
        User user = new User(id, "Petr", "Petrov", "login", "password", "123456789",
                "ivan@mail.com", LocalDate.of(2000, 01, 01), LocalDate.now(), "url");
//        User user = new User();
        UserResponseDto userResponseDto = new ModelMapper().map(user, UserResponseDto.class);
        System.out.println(userResponseDto);
//        when(validateUser.validateUser(user, bindingResult)).thenReturn(user);
//        when(userService.create(user)).thenReturn(user);

        String result =
                mockMvc.perform(
                                patch("/users/{id}",id)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsBytes(userResponseDto)))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
        System.out.println(result);
        Assertions.assertEquals(result, objectMapper.writeValueAsString(userResponseDto));
    }
}