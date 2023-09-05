package com.valentinakole.lms.service;

import com.valentinakole.lms.dto.lesson.LessonDto;

import java.time.LocalDate;
import java.util.List;

public interface LessonService {
    void getLesson(long lessonId);

    List<LessonDto> getLessons(long userId, LocalDate from, LocalDate to);
}