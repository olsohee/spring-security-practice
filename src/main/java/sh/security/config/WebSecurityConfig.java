package sh.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

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
                    .defaultSuccessUrl("/main")
                    .failureUrl("/login?fail")
                    .permitAll())

            // 로그아웃 필터 설정
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login"));

        return http.build();
    }

    @Bean
    public UserDetailsManager userDetailsManager() {

        // 테스터용 회원 생성
        UserDetails user = User.withUsername("sohee")
                .password(passwordEncoder().encode("1111"))
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
