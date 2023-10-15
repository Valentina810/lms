package com.valentinakole.lms.controller;

import com.valentinakole.lms.dto.lesson.FullLessonDto;
import com.valentinakole.lms.dto.lesson.LessonCreateDto;
import com.valentinakole.lms.dto.lesson.ShortLessonDto;
import com.valentinakole.lms.service.LessonService;
import com.valentinakole.lms.util.validate.ErrorsValidationChecker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/lessons")
@ResponseStatus(HttpStatus.OK)
public class LessonController {

    private final LessonService lessonService;
    private static final String formatDate = "yyyy-MM-dd";

    @GetMapping("{lessonId}")
    @Operation(summary = "Получение урока пользователя")
    public FullLessonDto getLesson(@PathVariable @Parameter(description = "Идентификатор пользователя") long userId,
                                   @PathVariable @Parameter(description = "Идентификатор урока") long lessonId) {
        return lessonService.getLesson(userId, lessonId);
    }

    @GetMapping
    @Operation(summary = "Получение уроков пользователя")
    public List<ShortLessonDto> getLessons(@PathVariable @Parameter(description = "Идентификатор пользователя") long userId,
                                           @RequestParam(name = "from", required = false) @Parameter(description = "Дата начала (включительно, например 2023-09-08)")
                                           @DateTimeFormat(pattern = formatDate) LocalDate from,
                                           @RequestParam(name = "to", required = false) @Parameter(description = "Дата окончания (включительно, например 2020-09-31)")
                                           @DateTimeFormat(pattern = formatDate) LocalDate to) {
        return lessonService.getLessons(userId, from, to);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание нового урока для пользователя")
    public FullLessonDto addLesson(@PathVariable long userId,
                                   @Valid @RequestBody LessonCreateDto lessonCreateDto, BindingResult bindingResult) {
        ErrorsValidationChecker.checkValidationErrors(bindingResult);
        return lessonService.addLesson(userId, lessonCreateDto);
    }

    @PatchMapping("{lessonId}")
    @Operation(summary = "Изменение урока")
    public FullLessonDto updateLesson(@PathVariable long userId,
                                      @PathVariable long lessonId,
                                      @Valid @RequestBody LessonCreateDto lessonCreateDto, BindingResult bindingResult) {
        ErrorsValidationChecker.checkValidationErrors(bindingResult);
        return lessonService.updateLesson(userId, lessonId, lessonCreateDto);
    }

    @DeleteMapping("{lessonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление урока")
    public void deleteLesson(@PathVariable long userId,
                             @PathVariable long lessonId) {
        lessonService.deleteLesson(userId, lessonId);
    }
}