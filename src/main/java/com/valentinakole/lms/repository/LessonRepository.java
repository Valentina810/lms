package com.valentinakole.lms.repository;

import com.valentinakole.lms.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query(value = "from Lesson as les " +
            "join fetch les.user use " +
            "join fetch les.subject " +
            "where use.id_user = ?1 " +
            "and les.date>= ?2 and les.date<= ?3 " +
            "order by les.date asc,les.timeStart asc ")
    List<Lesson> findLessonBuUserId(long userId, LocalDate from, LocalDate to);
}
