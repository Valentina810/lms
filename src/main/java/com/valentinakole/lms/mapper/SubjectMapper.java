package com.valentinakole.lms.mapper;

import com.valentinakole.lms.dto.subject.SubjectDto;
import com.valentinakole.lms.model.Subject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    SubjectDto toSubjectDto(Subject subject);
}