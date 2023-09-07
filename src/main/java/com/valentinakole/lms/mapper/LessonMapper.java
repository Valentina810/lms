package com.valentinakole.lms.mapper;

import com.valentinakole.lms.dto.lesson.FullLessonDto;
import com.valentinakole.lms.dto.lesson.LessonCreateDto;
import com.valentinakole.lms.dto.lesson.ShortLessonDto;
import com.valentinakole.lms.model.Lesson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    ShortLessonDto toShortLessonDto(Lesson lesson);

    FullLessonDto toFullLessonDto(Lesson lesson);

    Lesson toLesson(LessonCreateDto lessonCreateDto);
}