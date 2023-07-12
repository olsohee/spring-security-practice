package sh.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sh.security.entity.CustomUserDetails;
import sh.security.entity.Member;
import sh.security.repository.MemberRepository;

import java.util.Optional;

@Service
@Slf4j
public class UserDetailsManagerImpl implements UserDetailsManager {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDetailsManagerImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;

        // 테스트용 멤버 생성
        CustomUserDetails userDetails = CustomUserDetails.builder()
                .loginId("min")
                .password(passwordEncoder.encode("1111"))
                .username("민지")
                .phone("01011112222")
                .email("min@naver.com")
                .build();

        createUser(userDetails);
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        log.info("loadUserByUsername 실행");

        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);

        if(optionalMember.isEmpty()) {
            log.info("해당 로그인 아이디의 사용자는 존재하지 않음");
            throw new UsernameNotFoundException(loginId);
        }

        Member member = optionalMember.get();
        log.info("해당 로그인 아이디 사용자 정보 = {}", member.toString());

        return CustomUserDetails.fromEntity(member);
    }

    @Override
    public void createUser(UserDetails loginId) {
        // 같은 이름의 멤버가 존재하는지 확인
        if(userExists(loginId.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        // 존재하지 않으면 회원가입 진행
        memberRepository.save(((CustomUserDetails)loginId).createEntity());
    }

    @Override
    public boolean userExists(String loginId) {
        return memberRepository.existsUserByLoginId(loginId);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }
}
