package com.valentinakole.lms.controller;

import com.valentinakole.lms.dto.subject.SubjectDto;
import com.valentinakole.lms.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/subjects")
@Tag(name = "Получение списка учебных предметов")
@ResponseStatus(HttpStatus.OK)
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping
    @Operation(summary = "Возвращает список учебных предметов")
    public List<SubjectDto> getSubjects() {
        return subjectService.getSubjects();
    }
}