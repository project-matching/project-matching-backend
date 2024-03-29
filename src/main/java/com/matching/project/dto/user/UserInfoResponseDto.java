package com.matching.project.dto.user;

import com.matching.project.dto.enumerate.Role;
import com.matching.project.dto.technicalstack.TechnicalStackDto;
import com.matching.project.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class UserInfoResponseDto {
    private Long no;
    private Role role;
    private String name;
    private String email;
    private String image;
    private String position;
    private List<TechnicalStackDto> technicalStackDtoList;
}
