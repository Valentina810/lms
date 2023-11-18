package com.valentinakole.lms.service;

import com.valentinakole.lms.dto.lesson.FullLessonDto;
import com.valentinakole.lms.dto.lesson.LessonCreateDto;
import com.valentinakole.lms.dto.lesson.ShortLessonDto;
import com.valentinakole.lms.exception.errors.NotFoundException;
import com.valentinakole.lms.mapper.LessonMapper;
import com.valentinakole.lms.model.Lesson;
import com.valentinakole.lms.model.Subject;
import com.valentinakole.lms.model.User;
import com.valentinakole.lms.repository.LessonRepository;
import com.valentinakole.lms.repository.SubjectRepository;
import com.valentinakole.lms.repository.UserRepository;
import com.valentinakole.lms.service.impl.LessonServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LessonServiceTest {

    @Spy
    private LessonMapper lessonMapper = Mappers.getMapper(LessonMapper.class);

    @Mock
    private LessonRepository lessonRepository;
    @Mock
    private UserRepository userRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private LessonServiceImpl lessonService;

    private final long userId = 1;
    private final long lessonId = 1;
    private final long subjectId = 1;

    private final Lesson lesson = Lesson.builder()
            .idLesson(lessonId)
            .user(new User())
            .subject(new Subject())
            .topic("Test Topic")
            .date(LocalDate.now())
            .timeStart(LocalTime.now())
            .timeEnd(LocalTime.now().plusHours(1))
            .theoryUrl("Test Theory URL")
            .practiceUrl("Test Practice URL")
            .homeworkUrl("Test Homework URL")
            .progress(50)
            .checkSuccessfully(true)
            .build();

    private final LessonCreateDto lessonCreateDto = LessonCreateDto.builder()
            .topic(lesson.getTopic())
            .date(lesson.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
            .timeStart(lesson.getTimeStart())
            .timeEnd(lesson.getTimeEnd())
            .theoryUrl(lesson.getTheoryUrl())
            .practiceUrl(lesson.getPracticeUrl())
            .homeworkUrl(lesson.getHomeworkUrl())
            .progress(lesson.getProgress())
            .checkSuccessfully(true).build();

    private final User user = new User();
    private final Subject subject = new Subject();

    @Test
    public void getLesson_whenLessonExists_thenReturnLesson() {
        // Arrange
        when(lessonRepository.findLessonByUserId(userId, lessonId)).thenReturn(Optional.of(lesson));

        // Act
        FullLessonDto result = lessonService.getLesson(userId, lessonId);

        // Assert
        assertNotNull(result);
        assertEquals(lessonId, result.getIdLesson());
        assertEquals(lesson.getTopic(), result.getTopic());
        assertEquals(lesson.getDate(), result.getDate());
        assertEquals(lesson.getTimeStart(), result.getTimeStart());
        assertEquals(lesson.getTimeEnd(), result.getTimeEnd());
        assertEquals(lesson.getTheoryUrl(), result.getTheoryUrl());
        assertEquals(lesson.getPracticeUrl(), result.getPracticeUrl());
        assertEquals(lesson.getHomeworkUrl(), result.getHomeworkUrl());
        assertEquals(lesson.getProgress(), result.getProgress());
        assertEquals(lesson.getCheckSuccessfully(), result.getCheckSuccessfully());

        verify(lessonRepository, times(1)).findLessonByUserId(userId, lessonId);
    }

    @Test
    public void getLesson_whenLessonNotFound_thenThrowNotFoundException() {
        // Arrange
        when(lessonRepository.findLessonByUserId(userId, lessonId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> lessonService.getLesson(userId, lessonId));
        verify(lessonRepository, times(1)).findLessonByUserId(userId, lessonId);
    }

    @Test
    public void getLessons_whenLessonsExist_thenReturnLessons() {
        // Arrange
        long userId = 1;
        LocalDate from = LocalDate.now().minusDays(1);
        LocalDate to = LocalDate.now().plusDays(1);
        Lesson lesson1 = Lesson.builder()
                .idLesson(1L)
                .user(new User())
                .subject(new Subject())
                .topic("Test Topic 1")
                .date(from)
                .timeStart(LocalTime.now())
                .timeEnd(LocalTime.now().plusHours(1))
                .theoryUrl("Test Theory URL 1")
                .practiceUrl("Test Practice URL 1")
                .homeworkUrl("Test Homework URL 1")
                .progress(50)
                .checkSuccessfully(true)
                .build();
        Lesson lesson2 = Lesson.builder()
                .idLesson(2L)
                .user(new User())
                .subject(new Subject())
                .topic("Test Topic 2")
                .date(to)
                .timeStart(LocalTime.now())
                .timeEnd(LocalTime.now().plusHours(1))
                .theoryUrl("Test Theory URL 2")
                .practiceUrl("Test Practice URL 2")
                .homeworkUrl("Test Homework URL 2")
                .progress(100)
                .checkSuccessfully(true)
                .build();
        when(lessonRepository.findLessonsByUserId(userId, from, to)).thenReturn(Arrays.asList(lesson1, lesson2));

        // Act
        List<ShortLessonDto> result = lessonService.getLessons(userId, from, to);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        ShortLessonDto resultLesson1 = result.get(0);
        ShortLessonDto resultLesson2 = result.get(1);
        assertEquals(lesson1.getIdLesson(), resultLesson1.getIdLesson());
        assertEquals(lesson1.getTopic(), resultLesson1.getTopic());
        assertEquals(lesson1.getDate(), resultLesson1.getDate());
        assertEquals(lesson1.getTimeStart(), resultLesson1.getTimeStart());
        assertEquals(lesson1.getTimeEnd(), resultLesson1.getTimeEnd());
        assertEquals(lesson1.getProgress(), resultLesson1.getProgress());
        assertEquals(lesson1.getCheckSuccessfully(), resultLesson1.getCheckSuccessfully());
        assertEquals(lesson2.getIdLesson(), resultLesson2.getIdLesson());
        assertEquals(lesson2.getTopic(), resultLesson2.getTopic());
        assertEquals(lesson2.getDate(), resultLesson2.getDate());
        assertEquals(lesson2.getTimeStart(), resultLesson2.getTimeStart());
        assertEquals(lesson2.getTimeEnd(), resultLesson2.getTimeEnd());
        assertEquals(lesson2.getProgress(), resultLesson2.getProgress());
        assertEquals(lesson2.getCheckSuccessfully(), resultLesson2.getCheckSuccessfully());

        verify(lessonRepository, times(1)).findLessonsByUserId(userId, from, to);
    }

    @Test
    public void getLessons_whenLessonsNotFound_thenThrowNotFoundException() {
        // Arrange
        long userId = 1;
        LocalDate from = LocalDate.now().minusDays(1);
        LocalDate to = LocalDate.now().plusDays(1);
        when(lessonRepository.findLessonsByUserId(userId, from, to)).thenReturn(List.of());
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> lessonService.getLessons(userId, from, to));
        verify(lessonRepository, times(1)).findLessonsByUserId(userId, from, to);
    }

    @Test
    public void getLessons_whenUserIdInvalid_thenThrowNotFoundException() {
        // Arrange
        long userId = -1;
        LocalDate from = LocalDate.now().minusDays(1);
        LocalDate to = LocalDate.now().plusDays(1);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> lessonService.getLessons(userId, from, to));
        verify(lessonRepository, times(1)).findLessonsByUserId(userId, from, to);
    }

    @Test
    public void addLesson_whenLessonDtoIsValid_thenReturnLesson() {
        // Arrange
        subject.setIdSubject(subjectId);
        lessonCreateDto.setSubject(subject);
        lesson.setSubject(subject);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);
        when(subjectRepository.findById(lessonCreateDto.getSubject().getIdSubject())).thenReturn(Optional.of(subject));

        // Act
        FullLessonDto result = lessonService.addLesson(userId, lessonCreateDto);

        // Assert
        assertNotNull(result);
        assertEquals(subject, result.getSubject());
        assertEquals(lessonCreateDto.getTopic(), result.getTopic());
        assertEquals(lessonCreateDto.getDate(), result.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        assertEquals(lessonCreateDto.getTimeStart(), result.getTimeStart());
        assertEquals(lessonCreateDto.getTimeEnd(), result.getTimeEnd());
        assertEquals(lessonCreateDto.getTheoryUrl(), result.getTheoryUrl());
        assertEquals(lessonCreateDto.getPracticeUrl(), result.getPracticeUrl());
        assertEquals(lessonCreateDto.getHomeworkUrl(), result.getHomeworkUrl());
        assertEquals(lessonCreateDto.getProgress(), result.getProgress());
        assertEquals(lessonCreateDto.getCheckSuccessfully(), result.getCheckSuccessfully());

        verify(userRepository, times(1)).findById(userId);
        verify(subjectRepository, times(1)).findById(subjectId);
        verify(lessonRepository, times(1)).save(any(Lesson.class));
    }

    @Test
    public void addLesson_whenUserNotFound_thenThrowNotFoundException() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> lessonService.addLesson(userId, lessonCreateDto));
        verify(userRepository, times(1)).findById(userId);
        verify(subjectRepository, times(0)).findById(subjectId);
        verify(lessonRepository, times(0)).save(any(Lesson.class));
    }

    @Test
    public void addLesson_whenSubjectNotFound_thenThrowNotFoundException() {
        // Arrange
        subject.setIdSubject(subjectId);
        lessonCreateDto.setSubject(subject);
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> lessonService.addLesson(userId, lessonCreateDto));
        verify(userRepository, times(1)).findById(userId);
        verify(subjectRepository, times(1)).findById(subjectId);
        verify(lessonRepository, times(0)).save(any(Lesson.class));
    }

    @Test
    public void updateLesson_whenLessonDtoIsValid_thenReturnLesson() {
        // Arrange
        subject.setIdSubject(subjectId);
        lessonCreateDto.setSubject(subject);
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));
        when(lessonRepository.findLessonByUserId(userId, lessonId)).thenReturn(Optional.of(lesson));
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);

        // Act
        FullLessonDto result = lessonService.updateLesson(userId, lessonId, lessonCreateDto);

        // Assert
        assertNotNull(result);
        assertEquals(lessonCreateDto.getTopic(), result.getTopic());
        assertEquals(lessonCreateDto.getDate(), result.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        assertEquals(lessonCreateDto.getTimeStart(), result.getTimeStart());
        assertEquals(lessonCreateDto.getTimeEnd(), result.getTimeEnd());
        assertEquals(lessonCreateDto.getTheoryUrl(), result.getTheoryUrl());
        assertEquals(lessonCreateDto.getPracticeUrl(), result.getPracticeUrl());
        assertEquals(lessonCreateDto.getHomeworkUrl(), result.getHomeworkUrl());
        assertEquals(lessonCreateDto.getProgress(), result.getProgress());
        assertEquals(lessonCreateDto.getCheckSuccessfully(), result.getCheckSuccessfully());

        verify(subjectRepository, times(1)).findById(subjectId);
        verify(lessonRepository, times(1)).findLessonByUserId(userId, lessonId);
        verify(lessonRepository, times(1)).save(any(Lesson.class));
    }

    @Test
    public void updateLesson_whenUserNotFound_thenThrowNotFoundException() {
        // Arrange
        when(lessonRepository.findLessonByUserId(userId, lessonId)).thenReturn(Optional.empty());
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> lessonService.updateLesson(userId, lessonId, lessonCreateDto));
        verify(lessonRepository, times(1)).findLessonByUserId(userId, lessonId);
        verify(userRepository, times(1)).findById(userId);
        verify(lessonRepository, times(0)).save(any(Lesson.class));
    }

    @Test
    public void updateLesson_whenSubjectNotFound_thenThrowNotFoundException() {
        // Arrange
        subject.setIdSubject(subjectId);
        lessonCreateDto.setSubject(subject);
        when(lessonRepository.findLessonByUserId(userId, lessonId)).thenReturn(Optional.of(lesson));
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> lessonService.updateLesson(userId, lessonId, lessonCreateDto));
        verify(lessonRepository, times(1)).findLessonByUserId(userId, lessonId);
        verify(subjectRepository, times(1)).findById(subjectId);
        verify(lessonRepository, times(0)).save(any(Lesson.class));
    }

    @Test
    public void updateLesson_whenLessonNotFound_thenThrowNotFoundException() {
        // Arrange
        when(lessonRepository.findLessonByUserId(userId, lessonId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> lessonService.updateLesson(userId, lessonId, lessonCreateDto));
        verify(lessonRepository, times(1)).findLessonByUserId(userId, lessonId);
        verify(lessonRepository, times(0)).save(any(Lesson.class));
    }

    @Test
    public void deleteLesson_whenLessonExists_thenDoNothing() {
        // Arrange
        when(lessonRepository.deleteLesson(userId, lessonId)).thenReturn(1);

        // Act
        lessonService.deleteLesson(userId, lessonId);

        // Assert
        verify(lessonRepository, times(1)).deleteLesson(userId, lessonId);
    }

    @Test
    public void deleteLesson_whenLessonNotFound_thenThrowNotFoundException() {
        // Arrange
        when(lessonRepository.deleteLesson(userId, lessonId)).thenReturn(0);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> lessonService.deleteLesson(userId, lessonId));
        verify(lessonRepository, times(1)).deleteLesson(userId, lessonId);
    }

    @Test
    public void deleteLesson_whenUserIdInvalid_thenThrowNotFoundException() {
        // Arrange
        when(lessonRepository.deleteLesson(userId, lessonId)).thenReturn(0);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> lessonService.deleteLesson(userId, lessonId));
        verify(lessonRepository, times(1)).deleteLesson(userId, lessonId);
    }
}