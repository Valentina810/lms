package com.valentinakole.lms.dto.lesson;

import com.valentinakole.lms.model.Subject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность 'Урок'")
public class LessonDto {
    @Schema(description = "id", example = "1")
    private Long idLesson;

    private Subject subject;

    @Schema(description = "Дата урока", example = "2023-09-04")
    private LocalDate date;

    @Schema(description = "Время начала урока", example = "09:00:00")
    private LocalTime timeStart;

    @Schema(description = "Время окончания урока", example = "09:40:00")
    private LocalTime timeEnd;

    @Schema(description = "Прогресс выполнения в % от 1 до 100", example = "10")
    private Integer progress;

    @Schema(description = "ОТметка о выполнении урока", example = "false")
    private Boolean checkSuccessfully;
}