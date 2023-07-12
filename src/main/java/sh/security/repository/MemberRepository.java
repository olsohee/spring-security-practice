package sh.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sh.security.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public Optional<Member> findByLoginId(String loginId);
    public boolean existsUserByLoginId(String loginId);
}
