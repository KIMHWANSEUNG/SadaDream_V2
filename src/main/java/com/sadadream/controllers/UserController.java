package com.sadadream.controllers;

import com.sadadream.application.EmailService;
import com.sadadream.application.UserService;
import com.sadadream.domain.User;
import com.sadadream.dto.UserModificationData;
import com.sadadream.dto.UserRegistrationData;
import com.sadadream.dto.UserResultData;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin
@Slf4j
public class UserController {
    private final UserService userService;

    private final EmailService emailService;

    public UserController(UserService userService,  EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
	UserResultData create(@RequestBody @Valid UserRegistrationData registrationData) {
        User user = userService.registerUser(registrationData);
        return getUserResultData(user);
    }

    @PatchMapping("{id}")
    UserResultData update(
            @PathVariable Long id,
            @RequestBody @Valid UserModificationData modificationData
    ) {
        User user = userService.updateUser(id, modificationData);
        return getUserResultData(user);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void destroy(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    private UserResultData getUserResultData(User user) {
        return UserResultData.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }



    @PostMapping("/email") // 이메일 인증 코드 보내기
    public String emailAuth(@RequestParam String email) throws Exception {
        String email_code=emailService.sendSimpleMessage(email);
        log.info(("email에서 이메일 코드는 : "+email_code));

        return "";
    }

    @PostMapping("/emailcheck")//이메일 코드 확인하기
    public boolean emailcheck(@RequestParam String email_code){

        if(EmailService.ePw.equals(email_code)){
            log.info("emailcheck에서 이메일 코드:"+EmailService.ePw);
            return true;
        }else{
            log.info("잘못된 이메일 코드 입력");
            return false;
        }
    }
}
