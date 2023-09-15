package com.valentinakole.lms.dto.lesson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.valentinakole.lms.model.Subject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "Сущность 'Урок' (создание/редактирование)")
public class LessonCreateDto {

    @NotNull(message = "Id предмета обязательно должно быть указано для создания урока")
    private Subject subject;

    @NotBlank(message = "Тема урока обязательно должна быть указана для создания урока")
    @Schema(description = "Тема урока", example = "Предлоги")
    private String topic;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Дата урока обязательно должна быть указана для создания урока")
    @Schema(description = "Дата урока", example = "2023-09-14")
    private LocalDate date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @NotNull(message = "Время начала урока обязательно должно быть указано для создания урока")
    @Schema(description = "Время начала урока", example = "09:00:00")
    private LocalTime timeStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @NotNull(message = "Время окончания урока обязательно должно быть указано для создания урока")
    @Schema(description = "Время окончания урока", example = "09:40:00")
    private LocalTime timeEnd;

    @Schema(description = "Ссылка на теорию к уроку", example = "https://habr.com/ru/theoryUrl.htm")
    private String theoryUrl;

    @Schema(description = "Ссылка на практику к уроку", example = "https://habr.com/ru/practiceUrl.htm")
    private String practiceUrl;

    @Schema(description = "Ссылка на домашнюю работу к уроку", example = "https://habr.com/ru/homeworkUrl.htm")
    private String homeworkUrl;

    @Schema(description = "Прогресс выполнения в % от 1 до 100", example = "10")
    @Max(100)
    @Min(0)
    private Integer progress;

    @Schema(description = "Отметка о выполнении урока", example = "false")
    private Boolean checkSuccessfully;
}