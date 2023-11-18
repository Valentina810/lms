package com.valentinakole.lms.repository;

import com.valentinakole.lms.model.Lesson;
import com.valentinakole.lms.model.Subject;
import com.valentinakole.lms.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LessonRepositoryTest {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    private User user = new User();
    private Lesson lesson;
    private Subject subject = new Subject();
    private final LocalTime timeStart = LocalTime.of(1, 1, 1);

    @BeforeAll
    private void createTestData() {
        user.setName("Test");
        user.setSurname("User");
        user.setLogin("TestLogin");
        user.setPassword("TestPassword");
        user.setEmail("test@test.com");
        user.setToken("token");
        user.setDateRegistration(LocalDate.now());
        user = userRepository.save(user);

        subject.setName("TestSubject");
        subject = subjectRepository.save(subject);
    }

    @Test
    public void findLessonByUserId_whenLessonExists_thenReturnLesson() {
        lesson = lessonRepository.save(Lesson.builder()
                .user(user)
                .subject(subject)
                .topic("Название")
                .date(LocalDate.now())
                .timeStart(timeStart)
                .timeEnd(timeStart.plusHours(1)).build());

        Optional<Lesson> foundLesson = lessonRepository.findLessonByUserId(user.getUserId(), lesson.getIdLesson());

        assertEquals(lesson, foundLesson.orElse(null));
    }

    @Test
    public void findLessonByUserId_whenLessonDoesNotExist_thenReturnEmpty() {
        Optional<Lesson> foundLesson = lessonRepository.findLessonByUserId(1L, 1000L);

        assertFalse(foundLesson.isPresent());
    }

    @Test
    public void findLessonByUserId_whenUserIdIsInvalid_thenReturnEmpty() {
        Optional<Lesson> foundLesson = lessonRepository.findLessonByUserId(-1L, 1L);

        assertFalse(foundLesson.isPresent());
    }

    @Test
    public void findLessonsByUserId_whenLessonsExist_thenReturnLessons() {
        Lesson lesson1 = new Lesson();
        lesson1.setUser(user);
        lesson1.setSubject(subject);
        lesson1.setTopic("Название1");
        lesson1.setDate(LocalDate.now());
        lesson1.setTimeStart(timeStart.plusHours(1));
        lesson1.setTimeEnd(timeStart.plusHours(2));
        lesson1 = lessonRepository.save(lesson1);

        Lesson lesson2 = new Lesson();
        lesson2.setUser(user);
        lesson2.setSubject(subject);
        lesson2.setTopic("Название2");
        lesson2.setDate(LocalDate.now().plusDays(1));
        lesson2.setTimeStart(timeStart.plusHours(3));
        lesson2.setTimeEnd(timeStart.plusHours(4));
        lesson2 = lessonRepository.save(lesson2);

        List<Lesson> foundLessons = lessonRepository.findLessonsByUserId(user.getUserId(), LocalDate.now(), LocalDate.now().plusDays(1));

        assertEquals(2, foundLessons.size());
        assertEquals(lesson1, foundLessons.get(0));
        assertEquals(lesson2, foundLessons.get(1));
    }

    @Test
    public void findLessonsByUserId_whenLessonsDoNotExist_thenReturnEmpty() {
        List<Lesson> foundLessons = lessonRepository.findLessonsByUserId(1L, LocalDate.now(), LocalDate.now().plusDays(1));

        assertTrue(foundLessons.isEmpty());
    }

    @Test
    public void findLessonsByUserId_whenUserIdIsInvalid_thenReturnEmpty() {
        List<Lesson> foundLessons = lessonRepository.findLessonsByUserId(-1L, LocalDate.now(), LocalDate.now().plusDays(1));

        assertTrue(foundLessons.isEmpty());
    }

    @Test
    @Transactional
    public void deleteLesson_whenLessonExists_thenDeleteLesson() {
        lesson = lessonRepository.save(Lesson.builder()
                .user(user)
                .subject(subject)
                .topic("Название")
                .date(LocalDate.now())
                .timeStart(timeStart)
                .timeEnd(timeStart.plusHours(1)).build());

        int deleteCount = lessonRepository.deleteLesson(user.getUserId(), lesson.getIdLesson());

        assertEquals(1, deleteCount);
        assertTrue(lessonRepository.findById(lesson.getIdLesson()).isEmpty());
    }

    @Test
    @Transactional
    public void deleteLesson_whenLessonDoesNotExist_thenDoNothing() {
        int deleteCount = lessonRepository.deleteLesson(1L, 1000L);

        assertEquals(0, deleteCount);
    }

    @Test
    @Transactional
    public void deleteLesson_whenUserIdIsInvalid_thenDoNothing() {
        int deleteCount = lessonRepository.deleteLesson(-1L, 1L);

        assertEquals(0, deleteCount);
    }
}