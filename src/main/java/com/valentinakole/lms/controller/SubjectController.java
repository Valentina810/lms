package com.valentinakole.lms.controller;

import com.valentinakole.lms.dto.subject.SubjectDto;
import com.valentinakole.lms.service.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping
    public List<SubjectDto> getSubjects() {
        return subjectService.getSubjects();
    }
}