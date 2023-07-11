package sh.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ViewController {

    // 회원가입 화면
    @GetMapping("/signup")
    public String signupForm() {
        return "signupForm";
    }

    // 회원가입
   // @PostMapping("/signup")

    // 로그인 화면
    @GetMapping("/login")
    public String loginForm() {
        return "loginForm";
    }

    // 로그인
    //@PostMapping("/login")

    // 로그인 후 이동하는 메인화면
    @GetMapping("/main")
    public String main() {
        return "main";
    }
}
