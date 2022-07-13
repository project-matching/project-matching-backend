package com.matching.project.dto.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProjectPositionDetailDto {
    private Long no;
    private String positionName;
    private boolean state;
    private UserDetailDto userDetailDto;
}
