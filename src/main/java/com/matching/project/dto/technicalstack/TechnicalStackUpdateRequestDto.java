package com.matching.project.dto.technicalstack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TechnicalStackUpdateRequestDto {
    @NotBlank
    private String technicalStackName;
}
