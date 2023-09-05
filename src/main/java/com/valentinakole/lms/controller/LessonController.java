package com.valentinakole.lms.controller;

import com.valentinakole.lms.dto.lesson.LessonDto;
import com.valentinakole.lms.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/lessons")
public class LessonController {

    private final LessonService lessonService;
    private static final String formatDate = "yyyy-MM-dd";

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получение уроков пользователя")
    public List<LessonDto> getLessons(@PathVariable @Parameter(description = "Идентификатор пользователя") long userId,
                                      @RequestParam(name = "from", required = false) @Parameter(description = "Дата начала (включительно)")
                                      @DateTimeFormat(pattern = formatDate) LocalDate from,
                                      @RequestParam(name = "to", required = false) @Parameter(description = "Дата окончания (включительно)")
                                      @DateTimeFormat(pattern = formatDate) LocalDate to) {
        return lessonService.getLessons(userId, from, to);
    }
}