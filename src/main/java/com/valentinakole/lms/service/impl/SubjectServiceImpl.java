package com.valentinakole.lms.service.impl;

import com.valentinakole.lms.dto.subject.SubjectDto;
import com.valentinakole.lms.mapper.SubjectMapper;
import com.valentinakole.lms.repository.SubjectRepository;
import com.valentinakole.lms.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Override
    public List<SubjectDto> getSubjects() {
        List<SubjectDto> subject = subjectRepository
                .findAll()
                .stream().map(subjectMapper::toSubjectDto)
                .collect(Collectors.toList());
        log.info("Получен список предметов {}", subject);
        return subject;
    }
}