package com.valentinakole.lms.service.impl;

import com.valentinakole.lms.dto.lesson.FullLessonDto;
import com.valentinakole.lms.dto.lesson.LessonCreateDto;
import com.valentinakole.lms.dto.lesson.ShortLessonDto;
import com.valentinakole.lms.exception.errors.NotFoundException;
import com.valentinakole.lms.mapper.LessonMapper;
import com.valentinakole.lms.model.Lesson;
import com.valentinakole.lms.model.Subject;
import com.valentinakole.lms.model.User;
import com.valentinakole.lms.repository.LessonRepository;
import com.valentinakole.lms.repository.SubjectRepository;
import com.valentinakole.lms.repository.UserRepository;
import com.valentinakole.lms.service.LessonService;
import com.valentinakole.lms.util.validate.Checker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final SubjectRepository subjectRepository;
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

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public FullLessonDto addLesson(long userId, LessonCreateDto lessonCreateDto, BindingResult bindingResult) {
        Checker.checkValidationErrors(bindingResult);
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь", userId));
        Long idSubject = lessonCreateDto.getSubject().getIdSubject();
        Subject subject = subjectRepository.findById(idSubject)
                .orElseThrow(() -> new NotFoundException("Предмет", idSubject));
        Lesson lesson = lessonMapper.toLesson(lessonCreateDto);
        lesson.setUser(user);
        lesson.setSubject(subject);
        return lessonMapper.toFullLessonDto(lessonRepository.save(lesson));
    }
}