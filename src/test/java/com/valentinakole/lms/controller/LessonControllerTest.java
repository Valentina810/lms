package com.valentinakole.lms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valentinakole.lms.dto.lesson.FullLessonDto;
import com.valentinakole.lms.dto.lesson.LessonCreateDto;
import com.valentinakole.lms.dto.lesson.ShortLessonDto;
import com.valentinakole.lms.exception.errors.BadRequestException;
import com.valentinakole.lms.exception.errors.NotFoundException;
import com.valentinakole.lms.model.Subject;
import com.valentinakole.lms.service.LessonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LessonController.class)
public class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LessonService lessonService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final LocalDate dateLesson = LocalDate.now();
    private static final LocalTime timeStart = LocalTime.of(8, 0, 1);
    private static final LocalTime timeEnd = LocalTime.of(9, 0, 1);

    private final FullLessonDto fullLessonDto = FullLessonDto.builder()
            .idLesson(1L)
            .subject(new Subject())
            .topic("Topic")
            .date(dateLesson)
            .timeStart(timeStart)
            .timeEnd(timeEnd)
            .theoryUrl("https://theoryUrl.com")
            .practiceUrl("https://practiceUrl.com")
            .homeworkUrl("https://homeworkUrl.com")
            .progress(50)
            .checkSuccessfully(false)
            .build();

    private final ShortLessonDto shortLessonDto = ShortLessonDto.builder()
            .idLesson(1L)
            .subject(new Subject())
            .topic("Topic")
            .date(dateLesson)
            .timeStart(timeStart)
            .timeEnd(timeEnd)
            .progress(50)
            .checkSuccessfully(false)
            .build();

    private final LessonCreateDto lessonCreateDto = LessonCreateDto.builder()
            .subject(new Subject())
            .topic("Topic")
            .date("2023-09-16")
            .timeStart(timeStart)
            .timeEnd(timeEnd)
            .theoryUrl("https://theoryUrl.com")
            .practiceUrl("https://practiceUrl.com")
            .homeworkUrl("https://homeworkUrl.com")
            .build();

    @Test
    public void getLesson_whenLessonExists_thenReturnLesson() throws Exception {
        when(lessonService.getLesson(anyLong(), anyLong())).thenReturn(fullLessonDto);

        mockMvc.perform(get("/users/{userId}/lessons/{lessonId}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idLesson").value(fullLessonDto.getIdLesson()))
                .andExpect(jsonPath("$.subject").value(fullLessonDto.getSubject()))
                .andExpect(jsonPath("$.topic").value(fullLessonDto.getTopic()))
                .andExpect(jsonPath("$.date").value(fullLessonDto.getDate().toString()))
                .andExpect(jsonPath("$.timeStart").value(fullLessonDto.getTimeStart().toString()))
                .andExpect(jsonPath("$.timeEnd").value(fullLessonDto.getTimeEnd().toString()))
                .andExpect(jsonPath("$.theoryUrl").value(fullLessonDto.getTheoryUrl()))
                .andExpect(jsonPath("$.practiceUrl").value(fullLessonDto.getPracticeUrl()))
                .andExpect(jsonPath("$.homeworkUrl").value(fullLessonDto.getHomeworkUrl()))
                .andExpect(jsonPath("$.progress").value(fullLessonDto.getProgress()))
                .andExpect(jsonPath("$.checkSuccessfully").value(fullLessonDto.getCheckSuccessfully()));

        verify(lessonService, times(1)).getLesson(anyLong(), anyLong());
    }

    @Test
    public void getLesson_whenLessonDoesNotExist_thenThrowNotFoundException() throws Exception {
        when(lessonService.getLesson(anyLong(), anyLong())).thenThrow(new NotFoundException("Lesson not found"));

        mockMvc.perform(get("/users/{userId}/lessons/{lessonId}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(lessonService, times(1)).getLesson(anyLong(), anyLong());
    }

    @Test
    public void getLesson_whenLessonIdIsInvalid_thenThrowBadRequestException() throws Exception {
        mockMvc.perform(get("/users/{userId}/lessons/{lessonId}", 1L, "invalid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(lessonService, never()).getLesson(anyLong(), anyLong());
    }

    @Test
    public void getLessons_whenLessonsExist_thenReturnLessons() throws Exception {
        when(lessonService.getLessons(anyLong(), any(), any())).thenReturn(Collections.singletonList(shortLessonDto));

        mockMvc.perform(get("/users/{userId}/lessons", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idLesson").value(shortLessonDto.getIdLesson()))
                .andExpect(jsonPath("$[0].subject").value(shortLessonDto.getSubject()))
                .andExpect(jsonPath("$[0].topic").value(shortLessonDto.getTopic()))
                .andExpect(jsonPath("$[0].date").value(shortLessonDto.getDate().toString()))
                .andExpect(jsonPath("$[0].timeStart").value(shortLessonDto.getTimeStart().toString()))
                .andExpect(jsonPath("$[0].timeEnd").value(shortLessonDto.getTimeEnd().toString()))
                .andExpect(jsonPath("$[0].progress").value(shortLessonDto.getProgress()))
                .andExpect(jsonPath("$[0].checkSuccessfully").value(shortLessonDto.getCheckSuccessfully()));

        verify(lessonService, times(1)).getLessons(anyLong(), any(), any());
    }

    @Test
    public void getLessons_whenLessonsDoNotExist_thenThrowNotFoundException() throws Exception {
        when(lessonService.getLessons(anyLong(), any(), any())).thenThrow(new NotFoundException("Lessons not found"));

        mockMvc.perform(get("/users/{userId}/lessons", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(lessonService, times(1)).getLessons(anyLong(), any(), any());
    }

    @Test
    public void getLessons_whenUserIdIsInvalid_thenThrowBadRequestException() throws Exception {
        mockMvc.perform(get("/users/{userId}/lessons", "invalid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(lessonService, never()).getLessons(anyLong(), any(), any());
    }

    @Test
    public void addLesson_whenLessonDataIsValid_thenReturnLesson() throws Exception {
        when(lessonService.addLesson(anyLong(), any(LessonCreateDto.class))).thenReturn(fullLessonDto);

        mockMvc.perform(post("/users/{userId}/lessons", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idLesson").value(fullLessonDto.getIdLesson()))
                .andExpect(jsonPath("$.subject").value(fullLessonDto.getSubject()))
                .andExpect(jsonPath("$.topic").value(fullLessonDto.getTopic()))
                .andExpect(jsonPath("$.date").value(fullLessonDto.getDate().toString()))
                .andExpect(jsonPath("$.timeStart").value(fullLessonDto.getTimeStart().toString()))
                .andExpect(jsonPath("$.timeEnd").value(fullLessonDto.getTimeEnd().toString()))
                .andExpect(jsonPath("$.progress").value(fullLessonDto.getProgress()))
                .andExpect(jsonPath("$.checkSuccessfully").value(fullLessonDto.getCheckSuccessfully()));

        verify(lessonService, times(1)).addLesson(anyLong(), any(LessonCreateDto.class));
    }

    @Test
    public void addLesson_whenLessonDataIsInvalid_thenThrowBadRequestException() throws Exception {
        when(lessonService.addLesson(anyLong(), any(LessonCreateDto.class))).thenThrow(new BadRequestException("Invalid lesson data"));

        mockMvc.perform(post("/users/{userId}/lessons", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonCreateDto)))
                .andExpect(status().isBadRequest());

        verify(lessonService, times(1)).addLesson(anyLong(), any(LessonCreateDto.class));
    }

    @Test
    public void addLesson_whenUserIdIsInvalid_thenThrowBadRequestException() throws Exception {
        mockMvc.perform(post("/users/{userId}/lessons", "invalid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonCreateDto)))
                .andExpect(status().isBadRequest());

        verify(lessonService, never()).addLesson(anyLong(), any(LessonCreateDto.class));
    }

    @Test
    public void updateLesson_whenLessonDataIsValid_thenReturnLesson() throws Exception {
        when(lessonService.updateLesson(anyLong(), anyLong(), any(LessonCreateDto.class))).thenReturn(fullLessonDto);

        mockMvc.perform(patch("/users/{userId}/lessons/{lessonId}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idLesson").value(fullLessonDto.getIdLesson()))
                .andExpect(jsonPath("$.subject").value(fullLessonDto.getSubject()))
                .andExpect(jsonPath("$.topic").value(fullLessonDto.getTopic()))
                .andExpect(jsonPath("$.date").value(fullLessonDto.getDate().toString()))
                .andExpect(jsonPath("$.timeStart").value(fullLessonDto.getTimeStart().toString()))
                .andExpect(jsonPath("$.timeEnd").value(fullLessonDto.getTimeEnd().toString()))
                .andExpect(jsonPath("$.progress").value(fullLessonDto.getProgress()))
                .andExpect(jsonPath("$.checkSuccessfully").value(fullLessonDto.getCheckSuccessfully()));

        verify(lessonService, times(1)).updateLesson(anyLong(), anyLong(), any(LessonCreateDto.class));
    }

    @Test
    public void updateLesson_whenLessonDataIsInvalid_thenThrowBadRequestException() throws Exception {
        when(lessonService.updateLesson(anyLong(), anyLong(), any(LessonCreateDto.class))).thenThrow(new BadRequestException("Invalid lesson data"));

        mockMvc.perform(patch("/users/{userId}/lessons/{lessonId}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonCreateDto)))
                .andExpect(status().isBadRequest());

        verify(lessonService, times(1)).updateLesson(anyLong(), anyLong(), any(LessonCreateDto.class));
    }

    @Test
    public void updateLesson_whenLessonDoesNotExist_thenThrowNotFoundException() throws Exception {
        when(lessonService.updateLesson(anyLong(), anyLong(), any(LessonCreateDto.class))).thenThrow(new NotFoundException("Lesson not found"));

        mockMvc.perform(patch("/users/{userId}/lessons/{lessonId}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonCreateDto)))
                .andExpect(status().isNotFound());

        verify(lessonService, times(1)).updateLesson(anyLong(), anyLong(), any(LessonCreateDto.class));
    }

    @Test
    public void deleteLesson_whenLessonExists_thenNoContent() throws Exception {
        doNothing().when(lessonService).deleteLesson(anyLong(), anyLong());

        mockMvc.perform(delete("/users/{userId}/lessons/{lessonId}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(lessonService, times(1)).deleteLesson(anyLong(), anyLong());
    }

    @Test
    public void deleteLesson_whenLessonDoesNotExist_thenThrowNotFoundException() throws Exception {
        doThrow(new NotFoundException("Lesson not found")).when(lessonService).deleteLesson(anyLong(), anyLong());

        mockMvc.perform(delete("/users/{userId}/lessons/{lessonId}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(lessonService, times(1)).deleteLesson(anyLong(), anyLong());
    }

    @Test
    public void deleteLesson_whenLessonIdIsInvalid_thenThrowBadRequestException() throws Exception {
        mockMvc.perform(delete("/users/{userId}/lessons/{lessonId}", 1L, "invalid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(lessonService, times(0)).deleteLesson(anyLong(), anyLong());
    }
}