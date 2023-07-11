package sh.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ViewController {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 화면
    @GetMapping("/signup")
    public String signupForm() {
        return "signupForm";
    }

    // 회원가입
    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam("password-check") String passwordCheck) {

        log.info(username);
        log.info(password);
        log.info(passwordCheck);
        // 정상 회원가입 로직
        if(password.equals(passwordCheck)) {
            log.info("패스워드 일치, 회원가입 진행");
            userDetailsManager.createUser(User.withUsername(username)
                    .password(passwordEncoder.encode(password))
                    .build());
            return "redirect:/login";
        }

        // 회원가입 실패
        log.info("패스워드 불일치, 회원가입 실패");
        return "redirect:/signup?error";
    }

    // 로그인 화면
    @GetMapping("/login")
    public String loginForm() {
        return "loginForm";
    }

    // 로그인 후 이동하는 메인화면
    @GetMapping("/main")
    public String main() {
        return "main";
    }
}
