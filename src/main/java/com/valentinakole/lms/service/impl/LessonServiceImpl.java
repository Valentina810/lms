package com.valentinakole.lms.service.impl;

import com.valentinakole.lms.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(isolation = Isolation.SERIALIZABLE)
@RequiredArgsConstructor
@Slf4j
public class LessonServiceImpl implements LessonService {
    @Override
    public void getLesson(long lessonId) {

    }

    @Override
    public void getLessons(long userId, LocalDateTime from, LocalDateTime to) {

    }
}