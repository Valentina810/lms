package com.valentinakole.lms.mapper;

import com.valentinakole.lms.dto.lesson.LessonDto;
import com.valentinakole.lms.model.Lesson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    LessonDto toLessonDto(Lesson lesson);
}