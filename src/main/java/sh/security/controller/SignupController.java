package sh.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import sh.security.UserDetailsManagerImpl;
import sh.security.entity.CustomUserDetails;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SignupController {

    private final UserDetailsManagerImpl userDetailsManager;

    // 회원가입
    @PostMapping("/signup")
    public String signup(@RequestParam String loginId,
                         @RequestParam String password,
                         @RequestParam String username,
                         @RequestParam String email,
                         @RequestParam String phone) {

        // 로그인 아이디가 중복이면 회원가입 불가
        if(userDetailsManager.userExists(loginId)) {
            log.info("로그인 아이디 중복");
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        // 정상 회원가입 로직
        CustomUserDetails userDetails = CustomUserDetails.builder()
                .loginId(loginId)
                .password(password)
                .username(username)
                .email(email)
                .phone(phone)
                .build();

        userDetailsManager.createUser(userDetails);
        log.info("회원가입 성공");

        return "success";
    }
}
