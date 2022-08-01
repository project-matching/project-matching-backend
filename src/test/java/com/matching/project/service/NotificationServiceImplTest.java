package com.matching.project.service;

import com.matching.project.dto.enumerate.Role;
import com.matching.project.dto.enumerate.Type;
import com.matching.project.dto.notification.NotificationDto;
import com.matching.project.dto.notification.NotificationSimpleInfoDto;
import com.matching.project.entity.Comment;
import com.matching.project.entity.Notification;
import com.matching.project.entity.User;
import com.matching.project.error.CustomException;
import com.matching.project.repository.NotificationRepository;
import com.matching.project.repository.UserRepository;
import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @DisplayName("알림 전송 실패 : 받는 사용자가 없는 경우")
    @Test
    void sendNotificationFail1() {
        //given
        Long notificationNo = 2L;
        Type type = Type.PROJECT_PARTICIPATION_REFUSE;
        String receiver = "leeworld9@gmail.com";
        String title = "테스트 알림";
        String content = "상세내역";

        Long userNo = 1L;
        String userName = "관리자";
        String userEmail = "admin@admin.com";
        Role userRole = Role.ROLE_ADMIN;

        User user = User.builder()
                .no(userNo)
                .name(userName)
                .email(userEmail)
                .permission(userRole)
                .build();

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getEmail(), user.getAuthorities()));

        given(userRepository.findByEmail(receiver)).willReturn(Optional.empty());

        //when
        CustomException e = Assertions.assertThrows(CustomException.class, () -> {
            Notification notification = notificationService.sendNotification(type, receiver, title, content);
        });


        //then
        assertThat(e.getErrorCode().getDetail()).isEqualTo("This is not a registered email");
    }

    @DisplayName("알림 전송 성공")
    @Test
    void sendNotificationSuccess() {
        //given
        Long notificationNo = 2L;
        Type type = Type.PROJECT_PARTICIPATION_REFUSE;
        String receiver = "leeworld9@gmail.com";
        String title = "테스트 알림";
        String content = "상세내역";

        Long userNo = 1L;
        String userName = "관리자";
        String userEmail = "admin@admin.com";
        Role userRole = Role.ROLE_ADMIN;

        User user = User.builder()
                .no(userNo)
                .name(userName)
                .email(userEmail)
                .permission(userRole)
                .build();

        Long receiverUserNo = 2L;
        String receiverUserName = "테스터";
        String receiverUserEmail = "leeworld9@gmail.com";
        Role receiverUserRole = Role.ROLE_USER;

        User receiverUser = User.builder()
                .no(receiverUserNo)
                .name(receiverUserName)
                .email(receiverUserEmail)
                .permission(receiverUserRole)
                .build();

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getEmail(), user.getAuthorities()));

        given(userRepository.findByEmail(receiver)).willReturn(Optional.ofNullable(receiverUser));

        //when
        Notification notification = notificationService.sendNotification(type, receiver, title, content);

        //then
        assertThat(notification.getType()).isEqualTo(type);
        assertThat(notification.getUser().getEmail()).isEqualTo(receiver);
        assertThat(notification.getTitle()).isEqualTo(title);
        assertThat(notification.getContent()).isEqualTo(content);
    }
    
    @DisplayName("알림 리스트 조회 성공")
    @Test
    void notificationListSuccess() {
        Long userNo = 1L;
        String userName = "테스터";
        String userEmail = "leeworld9@gmail.com";
        Role userRole = Role.ROLE_USER;

        User user = User.builder()
                .no(userNo)
                .name(userName)
                .email(userEmail)
                .permission(userRole)
                .build();

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getEmail(), user.getAuthorities()));

        List<Notification> notificationList = new ArrayList<>();
        for (int i = 0 ; i < 10 ; i++) {
            notificationList.add(Notification.builder()
                            .no(Integer.toUnsignedLong(i))
                            .type(Type.NOTICE)
                            .title("title" + i)
                            .content("content" + i)
                            .read(false)
                            .user(null)
                            .build()
            );
        }

        int page = 1;
        int size = 3;
        Pageable pageable = PageRequest.of(page, size, Sort.by("no").ascending());
        int start = (int)pageable.getOffset();
        int end = (start + pageable.getPageSize()) > notificationList.size() ? notificationList.size() : (start + pageable.getPageSize());
        Page<Notification> notifications = new PageImpl<>(notificationList.subList(start, end), pageable, notificationList.size());

        given(notificationRepository.findByUserOrUserIsNullOrderByNoDesc(user, pageable)).willReturn(notifications.stream().collect(Collectors.toList()));

        //when
        List<NotificationSimpleInfoDto> dtos = notificationService.notificationList(pageable);

        //then
        assertThat(dtos.get(0).getNo()).isEqualTo(Integer.toUnsignedLong(3));
        assertThat(dtos.get(0).getTitle()).isEqualTo("title" + 3);
        assertThat(dtos.get(1).getNo()).isEqualTo(Integer.toUnsignedLong(4));
        assertThat(dtos.get(1).getTitle()).isEqualTo("title" + 4);
        assertThat(dtos.get(2).getNo()).isEqualTo(Integer.toUnsignedLong(5));
        assertThat(dtos.get(2).getTitle()).isEqualTo("title" + 5);
    }

    @DisplayName("알림 상세 조회 실패 : 다른 사용자의 알림내역에 접근(공지사항 제외)")
    @Test
    void notificationInfoFail1() {
        //given
        Long notificationNo = 2L;
        Type type = Type.PROJECT_PARTICIPATION_REFUSE;
        String receiver = "leeworld9@gmail.com";
        String title = "테스트 알림";
        String content = "상세내역";


        Long userNo = 1L;
        String userName = "테스터1";
        String userEmail = "leeworld9@github.com";
        Role userRole = Role.ROLE_USER;

        User user = User.builder()
                .no(userNo)
                .name(userName)
                .email(userEmail)
                .permission(userRole)
                .build();

        Long receiverUserNo = 2L;
        String receiverUserName = "테스터2";
        String receiverUserEmail = "leeworld9@gmail.com";
        Role receiverUserRole = Role.ROLE_USER;

        User receiverUser = User.builder()
                .no(receiverUserNo)
                .name(receiverUserName)
                .email(receiverUserEmail)
                .permission(receiverUserRole)
                .build();

        Notification notification =  Notification.builder()
                .no(notificationNo)
                .type(type)
                .title(title)
                .content(content)
                .user(receiverUser)
                .read(false)
                .build();

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getEmail(), user.getAuthorities()));

        given(notificationRepository.findByNoWithUserUsingLeftFetchJoin(notificationNo)).willReturn(Optional.ofNullable(notification));

        //when
        CustomException e = Assertions.assertThrows(CustomException.class, () -> {
            NotificationDto dto = notificationService.notificationInfo(notificationNo);
        });

        //then
        assertThat(e.getErrorCode().getDetail()).isEqualTo("Unauthorized User Access");

    }

    @DisplayName("알림 상세 조회 성공")
    @Test
    void notificationInfoSuccess() {
        //given
        Long notificationNo = 2L;
        Type type = Type.PROJECT_PARTICIPATION_REFUSE;
        String receiver = "leeworld9@gmail.com";
        String title = "테스트 알림";
        String content = "상세내역";

        Long userNo = 1L;
        String userName = "테스터";
        String userEmail = "leeworld9@gmail.com";
        Role userRole = Role.ROLE_USER;

        User user = User.builder()
                .no(userNo)
                .name(userName)
                .email(userEmail)
                .permission(userRole)
                .build();

        Notification notification =  Notification.builder()
                .no(notificationNo)
                .type(type)
                .title(title)
                .content(content)
                .user(user)
                .read(false)
                .build();

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getEmail(), user.getAuthorities()));

        given(notificationRepository.findByNoWithUserUsingLeftFetchJoin(notificationNo)).willReturn(Optional.ofNullable(notification));

        //when
        NotificationDto dto = notificationService.notificationInfo(notificationNo);

        //then
        assertThat(dto.getType()).isEqualTo(type);
        assertThat(dto.getTitle()).isEqualTo(title);
        assertThat(dto.getContent()).isEqualTo(content);
    }
}