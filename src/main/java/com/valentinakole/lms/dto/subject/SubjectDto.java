package com.valentinakole.lms.dto.subject;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность 'Предмет'")
public class SubjectDto {

    @Schema(description = "id", example = "1")
    private Long idSubject;
    @Schema(description = "Название", example = "Математика")
    private String name;
}