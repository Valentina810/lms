package com.valentinakole.lms.repository;

import com.valentinakole.lms.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query(value = "from Lesson as les " +
            "join fetch les.user use " +
            "join fetch les.subject " +
            "where use.userId = ?1 " +
            "and les.idLesson = ?2 ")
    Optional<Lesson> findLessonByUserId(long userId, long lessonId);

    @Query(value = "from Lesson as les " +
            "join fetch les.user use " +
            "join fetch les.subject " +
            "where use.userId = ?1 " +
            "and les.date>= ?2 and les.date<= ?3 " +
            "order by les.date asc,les.timeStart asc ")
    List<Lesson> findLessonsByUserId(long userId, LocalDate from, LocalDate to);

    @Query(value = "delete from lessons as les " +
            "where les.id_lesson = ?2 " +
            "and les.user_id = ?1 " +
            "returning les.id_lesson",nativeQuery = true)
    Integer deleteLesson(long userId, long lessonId);
}
