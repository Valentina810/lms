package com.valentinakole.lms.dto.lesson;

import com.valentinakole.lms.model.Subject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность 'Урок' (полная информация)")
public class FullLessonDto {
    @Schema(description = "id", example = "1")
    private Long idLesson;

    private Subject subject;

    @Schema(description = "Тема урока", example = "Предлоги")
    private String topic;

    @Schema(description = "Дата урока", example = "2023-09-04")
    private LocalDate date;

    @Schema(description = "Время начала урока", example = "09:00:00")
    private LocalTime timeStart;

    @Schema(description = "Время окончания урока", example = "09:40:00")
    private LocalTime timeEnd;

    @Schema(description = "Ссылка на теорию к уроку", example = "https://habr.com/ru/theoryUrl.htm")
    private String theoryUrl;

    @Schema(description = "Ссылка на практику к уроку", example = "https://habr.com/ru/practiceUrl.htm")
    private String practiceUrl;

    @Schema(description = "Ссылка на домашнюю работу к уроку", example = "https://habr.com/ru/homeworkUrl.htm")
    private String homeworkUrl;

    @Schema(description = "Прогресс выполнения в % от 1 до 100", example = "10")
    private Integer progress;

    @Schema(description = "Отметка о выполнении урока", example = "false")
    private Boolean checkSuccessfully;
}