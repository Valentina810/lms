package com.valentinakole.lms.service;

import com.valentinakole.lms.dto.lesson.FullLessonDto;
import com.valentinakole.lms.dto.lesson.LessonCreateDto;
import com.valentinakole.lms.dto.lesson.ShortLessonDto;

import java.time.LocalDate;
import java.util.List;

public interface LessonService {
    FullLessonDto getLesson(long userId, long lessonId);

    List<ShortLessonDto> getLessons(long userId, LocalDate from, LocalDate to);

    FullLessonDto addLesson(long userId, LessonCreateDto lessonCreateDto);

    FullLessonDto updateLesson(long userId, long lessonId, LessonCreateDto lessonCreateDto);

    void deleteLesson(long userId, long lessonId);
}