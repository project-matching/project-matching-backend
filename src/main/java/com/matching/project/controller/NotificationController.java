package com.matching.project.controller;

import com.matching.project.dto.notification.NotificationDto;
import com.matching.project.dto.notification.NotificationSimpleInfoDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/notification")
public class NotificationController {

    @PostMapping
    @ApiOperation(value = "알림 전송")
    public ResponseEntity notificationSend(NotificationDto notificationSendRequestDto) {
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "알림 목록 조회")
    public ResponseEntity notificationList() {
        List<NotificationSimpleInfoDto> notificationSimpleInfoDtoList = new ArrayList<>();
        return new ResponseEntity(notificationSimpleInfoDtoList, HttpStatus.OK);
    }

    @GetMapping("/{notificationNo}")
    @ApiOperation(value = "알림 상세 조회")
    public ResponseEntity notificationInfo(@PathVariable Long notificationNo) {
        return new ResponseEntity(new NotificationDto(), HttpStatus.OK);
    }
}
