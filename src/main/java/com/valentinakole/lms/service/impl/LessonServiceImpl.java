package com.valentinakole.lms.service.impl;

import com.valentinakole.lms.dto.lesson.FullLessonDto;
import com.valentinakole.lms.dto.lesson.LessonCreateDto;
import com.valentinakole.lms.dto.lesson.ShortLessonDto;
import com.valentinakole.lms.exception.errors.BadRequestException;
import com.valentinakole.lms.exception.errors.NotFoundException;
import com.valentinakole.lms.mapper.LessonMapper;
import com.valentinakole.lms.model.Lesson;
import com.valentinakole.lms.model.Subject;
import com.valentinakole.lms.model.User;
import com.valentinakole.lms.repository.LessonRepository;
import com.valentinakole.lms.repository.SubjectRepository;
import com.valentinakole.lms.repository.UserRepository;
import com.valentinakole.lms.service.LessonService;
import com.valentinakole.lms.util.validate.ErrorsValidationChecker;
import com.valentinakole.lms.util.validate.ValidationMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
                new NotFoundException("У пользователя с id " + userId + " не найден урок с id " + lessonId)));
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
        if (shortLessonDtos.isEmpty()) {
            userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь", userId));
        }
        log.info("Получены уроки пользователя c id {} за период с {} по {}: {}", userId, from, to, shortLessonDtos);
        return shortLessonDtos;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public FullLessonDto addLesson(long userId, LessonCreateDto lessonCreateDto, BindingResult bindingResult) {
        ErrorsValidationChecker.checkValidationErrors(bindingResult);
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь", userId));
        Long subjectId = lessonCreateDto.getSubject().getIdSubject();
        if (subjectId != null) {
            Subject subject = subjectRepository.findById(subjectId)
                    .orElseThrow(() -> new NotFoundException("Предмет", subjectId));
            Lesson lesson = lessonMapper.toLesson(lessonCreateDto);
            lesson.setUser(user);
            lesson.setSubject(subject);
            FullLessonDto fullLessonDto = lessonMapper.toFullLessonDto(lessonRepository.save(lesson));
            log.info("Добавлен новый урок: {}", fullLessonDto);
            return fullLessonDto;
        } else throw new BadRequestException(ValidationMessage.VALIDATION_INCORRECT_SUBJECT);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public FullLessonDto updateLesson(long userId, long lessonId, LessonCreateDto lessonCreateDto, BindingResult bindingResult) {
        ErrorsValidationChecker.checkValidationErrors(bindingResult);
        Optional<Lesson> lessonByUserId = lessonRepository.findLessonByUserId(userId, lessonId);
        if (lessonByUserId.isPresent()) {
            Lesson updateLesson = lessonMapper.toLesson(lessonCreateDto);
            updateLesson.setIdLesson(lessonId);
            updateLesson.setUser(lessonByUserId.get().getUser());
            Long subjectId = updateLesson.getSubject().getIdSubject();
            if (subjectId != null) {
                updateLesson.setSubject(subjectRepository.findById(subjectId)
                        .orElseThrow(() -> new NotFoundException("Предмет", updateLesson.getSubject().getIdSubject())));
                FullLessonDto fullLessonDto = lessonMapper.toFullLessonDto(lessonRepository.save(updateLesson));
                log.info("Обновлен урок c id {}: {}", fullLessonDto.getIdLesson(), fullLessonDto);
                return fullLessonDto;
            } else throw new BadRequestException(ValidationMessage.VALIDATION_INCORRECT_SUBJECT);
        } else {
            userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь", userId));
            throw new NotFoundException("Урок", lessonId);
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteLesson(long userId, long lessonId) {
        if (lessonRepository.deleteLesson(userId, lessonId) == 0) {
            userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь", userId));
            throw new NotFoundException("Урок", lessonId);
        } else log.info("Удален урок c id {}", lessonId);
    }
}