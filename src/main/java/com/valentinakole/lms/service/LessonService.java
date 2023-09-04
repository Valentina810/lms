package com.valentinakole.lms.service;

import java.time.LocalDateTime;

public interface LessonService {
    void getLesson(long lessonId);

    void getLessons(long userId, LocalDateTime from, LocalDateTime to);

}