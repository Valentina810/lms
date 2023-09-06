package com.valentinakole.lms.service;

import com.valentinakole.lms.dto.lesson.FullLessonDto;
import com.valentinakole.lms.dto.lesson.ShortLessonDto;

import java.time.LocalDate;
import java.util.List;

public interface LessonService {
    FullLessonDto getLesson(long userId, long lessonId);

    List<ShortLessonDto> getLessons(long userId, LocalDate from, LocalDate to);
}