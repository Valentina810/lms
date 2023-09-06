package com.valentinakole.lms.service.impl;

import com.valentinakole.lms.dto.lesson.FullLessonDto;
import com.valentinakole.lms.dto.lesson.ShortLessonDto;
import com.valentinakole.lms.exception.errors.NotFoundException;
import com.valentinakole.lms.mapper.LessonMapper;
import com.valentinakole.lms.repository.LessonRepository;
import com.valentinakole.lms.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    @Override
    public FullLessonDto getLesson(long userId, long lessonId) {
        FullLessonDto fullLessonDto = lessonMapper.toFullLessonDto(lessonRepository.findLessonByUserId(userId, lessonId).orElseThrow(() ->
                new NotFoundException("Урок", lessonId)));
        log.info("Получен урок {}", fullLessonDto);
        return fullLessonDto;
    }

    @Override
    public List<ShortLessonDto> getLessons(long userId, LocalDate from, LocalDate to) {
        if (from == null) {
            from = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() - 1);
        }
        if (to == null) {
            to = from.plusDays(6);
        }
        List<ShortLessonDto> shortLessonDtos = lessonRepository.findLessonsByUserId(userId, from, to)
                .stream()
                .map(lessonMapper::toShortLessonDto)
                .collect(Collectors.toList());
        log.info("Получены уроки пользователя c id {} за период с {} по {}: {}", userId, from, to, shortLessonDtos);
        return shortLessonDtos;
    }
}