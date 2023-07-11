package sh.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

            // 보안 관련 필터 설정
        http.csrf(AbstractHttpConfigurer::disable)

            // 권한 관련 설정
            .authorizeHttpRequests(authHttp -> authHttp
                    .requestMatchers("/main").permitAll()
                    .requestMatchers("/signup").anonymous()
                    .anyRequest().authenticated())

            // form을 이용한 로그인 필터 설정
            .formLogin(formLogin -> formLogin
                    .loginPage("/login")
                    .defaultSuccessUrl("/home")
                    .failureUrl("/login?fail")
                    .permitAll())

            // 로그아웃 필터 설정
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login"));

        return http.build();
    }
}
