package com.matching.project.controller;

import com.matching.project.dto.ResponseDto;
import com.matching.project.dto.user.*;
import com.matching.project.entity.EmailAuth;
import com.matching.project.entity.User;
import com.matching.project.service.EmailServiceImpl;
import com.matching.project.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;
    private final EmailServiceImpl emailService;

    @PostMapping
    @ApiOperation(value = "회원가입")
    public ResponseEntity signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        try {
            User user = userService.userSignUp(signUpRequestDto);
            // 클라이언트에서 dto 정보가 추가적으로 더 필요하면 수정 필요
            SignUpResponseDto signUpResponseDto = SignUpResponseDto.builder()
                    .no(user.getNo())
                    .name(user.getName())
                    .sex(user.getSex())
                    .email(user.getEmail())
                    .build();
            EmailAuth emailAuth = emailService.emailAuthSave(user.getEmail());
            emailService.sendConfirmEmail(user.getEmail(), emailAuth.getAuthToken());
            return ResponseEntity.ok().body(signUpResponseDto);
        } catch (Exception e) {
            ResponseDto responseDto = ResponseDto.builder()
                    .error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @ApiOperation(value = "이메일 인증")
    @GetMapping("/confirm")
    public ResponseEntity confirmEmail(EmailAuthRequestDto requestDto) {
        try {
            emailService.confirmEmail(requestDto);
            ResponseDto responseDto = ResponseDto.builder()
                    .data("Email Authentication Completed").build();
            return ResponseEntity.ok().body(responseDto);
        } catch (Exception e) {
            ResponseDto responseDto = ResponseDto.builder()
                    .error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @ApiOperation(value = "이메일 인증 코드 재발송")
    @PostMapping("/reissue")
    public ResponseEntity reSendEmailAuth(@RequestBody Map<String, String> req) {
        try {
            emailService.emailAuthReSend(req.get("email"));
            ResponseDto responseDto = ResponseDto.builder()
                    .data("Email Authentication Code Resend").build();
            return ResponseEntity.ok().body(responseDto);
        } catch (Exception e) {
            ResponseDto responseDto = ResponseDto.builder()
                    .error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }
    
    @GetMapping("/{no}")
    @ApiOperation(value = "회원 정보 조회")
    public ResponseEntity<UserInfoResponseDto> userInfo(@PathVariable Long no) {

        return new ResponseEntity(new UserInfoResponseDto(), HttpStatus.OK);
    }
    
    @GetMapping
    @ApiOperation(value = "회원 목록 조회")
    public ResponseEntity<List<UserSimpleInfoDto>> userInfoList() {
        List<UserSimpleInfoDto> userSimpleInfoList = new ArrayList<>();
        
        return new ResponseEntity(userSimpleInfoList, HttpStatus.OK);
    }
    
    @PatchMapping("/{no}")
    @ApiOperation(value = "회원 정보 수정")
    public ResponseEntity<String> userUpdate(@PathVariable Long no, UserUpdateRequestDto userUpdateRequestDto) {
        return new ResponseEntity("수정 완료", HttpStatus.OK);
    }

    @DeleteMapping("/{no}")
    @ApiOperation(value = "회원 탈퇴")
    public ResponseEntity<String> userDelete(@PathVariable Long no) {
        return new ResponseEntity("삭제 완료", HttpStatus.OK);
    }

    @GetMapping("/block/{no}")
    @ApiOperation(value = "회원 차단")
    public ResponseEntity<String> userBlock(@PathVariable Long no) {
        return new ResponseEntity("차단 완료", HttpStatus.OK);
    }

    @PostMapping("/search")
    @ApiOperation(value = "회원 검색")
    public ResponseEntity<List<UserSimpleInfoDto>> userSearch(UserSearchRequestDto userSearchRequestDto) {
        List<UserSimpleInfoDto> userSimpleInfoDtos = new ArrayList<>();
        return new ResponseEntity(userSimpleInfoDtos, HttpStatus.OK);
    }
}
