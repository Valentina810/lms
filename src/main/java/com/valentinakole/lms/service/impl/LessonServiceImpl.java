package com.valentinakole.lms.service.impl;

import com.valentinakole.lms.dto.lesson.LessonDto;
import com.valentinakole.lms.mapper.LessonMapper;
import com.valentinakole.lms.repository.LessonRepository;
import com.valentinakole.lms.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(isolation = Isolation.SERIALIZABLE)
@RequiredArgsConstructor
@Slf4j
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    @Override
    public void getLesson(long lessonId) {

    }

    @Override
    public List<LessonDto> getLessons(long userId, LocalDate from, LocalDate to) {
        int value = LocalDate.now().getDayOfWeek().getValue();
        if (from == null) {
            from = LocalDate.now().minusDays(value - (value - 1));
        }
        if (to == null) {
            to = from.plusDays(6);
        }
        return lessonRepository.findLessonBuUserId(userId, from, to)
                .stream()
                .map(lessonMapper::toLessonDto).
                collect(Collectors.toList());
    }
}