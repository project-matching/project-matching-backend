package com.matching.project.service;

import com.matching.project.dto.enumerate.Position;
import com.matching.project.dto.enumerate.Role;
import com.matching.project.dto.project.ProjectPositionDto;
import com.matching.project.dto.project.ProjectRegisterRequestDto;
import com.matching.project.dto.project.ProjectRegisterResponseDto;
import com.matching.project.entity.*;
import com.matching.project.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {
    @Mock
    ProjectPositionRepository projectPositionRepository;

    @Mock
    ProjectTechnicalStackRepository projectTechnicalStackRepository;

    @Mock
    ProjectRepository projectRepository;

    @Mock
    ProjectUserRepository projectUserRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    ProjectServiceImpl projectService;

    @Test
    public void 프로젝트_등록_성공_테스트() {
        // given

        // 객체 생성
        String testName = "testName";
        String testIntroduction = "testIntroduction";
        Integer testMaxPeople = 10;

        LocalDateTime createDate = LocalDateTime.of(2022, 06, 24, 10, 10, 10);
        LocalDate startDate = LocalDate.of(2022, 06, 24);
        LocalDate endDate = LocalDate.of(2022, 06, 28);

        List<String> technicalStack = new ArrayList<>();
        technicalStack.add("SPRING");
        technicalStack.add("JAVA");
        ProjectPositionDto projectPositionDto = new ProjectPositionDto(Position.BACKEND, technicalStack);
        List<ProjectPositionDto> projectPositionDtoList = new ArrayList<>();
        projectPositionDtoList.add(projectPositionDto);

        ProjectRegisterRequestDto projectRegisterRequestDto = ProjectRegisterRequestDto.builder()
                .name(testName)
                .profile(null)
                .createDate(createDate)
                .startDate(startDate)
                .endDate(endDate)
                .introduction(testIntroduction)
                .maxPeople(testMaxPeople)
                .projectPosition(projectPositionDtoList)
                .build();

        Project project = Project.builder()
                .no(1L)
                .name(projectRegisterRequestDto.getName())
                .createDate(projectRegisterRequestDto.getCreateDate())
                .startDate(projectRegisterRequestDto.getStartDate())
                .endDate(projectRegisterRequestDto.getEndDate())
                .state(true)
                .introduction(projectRegisterRequestDto.getIntroduction())
                .maxPeople(projectRegisterRequestDto.getMaxPeople())
                .delete(false)
                .deleteReason(null)
                .count(0)
                .image(null)
                .build();
        ProjectPosition projectPosition1 = ProjectPosition.of(projectRegisterRequestDto.getProjectPosition().get(0));
        ProjectTechnicalStack projectTechnicalStack1 = ProjectTechnicalStack.of(projectRegisterRequestDto.getProjectPosition().get(0).getTechnicalStack().get(0));
        ProjectTechnicalStack projectTechnicalStack2 = ProjectTechnicalStack.of(projectRegisterRequestDto.getProjectPosition().get(0).getTechnicalStack().get(1));

        projectPosition1.getProjectTechnicalStack().add(projectTechnicalStack1);
        projectPosition1.getProjectTechnicalStack().add(projectTechnicalStack2);
        project.getProjectPosition().add(projectPosition1);

        User user = User.builder()
                .no(1L)
                .name("testName")
                .sex('M')
                .email("testEmail")
                .github("testGithub")
                .selfIntroduction("testSelfIntroduction")
                .block(false)
                .block_reason(null)
                .permission(Role.ROLE_USER)
                .image(null)
                .userPosition(null)
                .build();

        ProjectUser projectUser = ProjectUser.builder()
                .no(1L)
                .projectNo(project)
                .userNo(user)
                .creator(true)
                .build();

        given(projectRepository.save(any(Project.class))).willReturn(project);
        given(projectPositionRepository.save(any(ProjectPosition.class))).willReturn(projectPosition1);
        given(projectTechnicalStackRepository.save(any(ProjectTechnicalStack.class))).willReturn(projectTechnicalStack1);

        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));
        given(projectUserRepository.save(any(ProjectUser.class))).willReturn(projectUser);

        ProjectRegisterResponseDto projectRegisterResponseDto = null;

        //when
        try {
            projectRegisterResponseDto = projectService.projectRegister(projectRegisterRequestDto);
        } catch (Exception e) {

        }

        // then
        verify(userRepository).findById(any());
        verify(projectRepository).save(any());
        verify(projectPositionRepository).save(any());
        verify(projectTechnicalStackRepository, times(2)).save(any());
        verify(projectUserRepository).save(any());


        Assertions.assertEquals(projectRegisterResponseDto.getName(), testName);
        Assertions.assertEquals(projectRegisterResponseDto.getCreateDate(), createDate);
        Assertions.assertEquals(projectRegisterResponseDto.getStartDate(), startDate);
        Assertions.assertEquals(projectRegisterResponseDto.getEndDate(), endDate);
        Assertions.assertEquals(projectRegisterResponseDto.isState(), true);
        Assertions.assertEquals(projectRegisterResponseDto.getIntroduction(), testIntroduction);
        Assertions.assertEquals(projectRegisterResponseDto.getMaxPeople(), testMaxPeople);
        Assertions.assertEquals(projectRegisterResponseDto.getProjectPosition().get(0).getPosition(), Position.BACKEND);
        Assertions.assertEquals(projectRegisterResponseDto.getProjectPosition().get(0).getTechnicalStack().get(0), "SPRING");
        Assertions.assertEquals(projectRegisterResponseDto.getProjectPosition().get(0).getTechnicalStack().get(1), "JAVA");
    }

    @Test
    public void 프로젝트_목록_조회_성공_테스트() {

    }
}