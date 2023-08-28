package com.valentinakole.lms.controller;

import com.valentinakole.lms.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/lesson")
public class LessonController {

    private final LessonService lessonService;

    @GetMapping("/{lessonId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получение урока по id")
    public void getLesson(@PathVariable @Parameter(description = "Идентификатор урока") long lessonId) {
        lessonService.getLesson(lessonId);
    }
}