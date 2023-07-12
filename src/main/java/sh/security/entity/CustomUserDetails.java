package sh.security.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

    private String loginId;
    private String password;
    private String username;
    private String email;
    private String phone;

    private String provider;
    private String providerId;

    // Member -> CustomUserDetails
    public static CustomUserDetails fromEntity(Member member) {
        CustomUserDetails details = new CustomUserDetails();
        details.setLoginId(member.getLoginId());
        details.setPassword(member.getPassword());
        details.setUsername(member.getUsername());
        details.setEmail(member.getEmail());
        details.setPhone(member.getPhone());
        details.setProvider(member.getProvider());
        details.setProviderId(member.getProviderId());

        return details;
    }

    // CustomUserDetails -> Member
    public Member createEntity() {
        Member member = new Member();
        member.setLoginId(loginId);
        member.setPassword(password);
        member.setUsername(username);
        member.setEmail(email);
        member.setPhone(phone);
        member.setProvider(provider);
        member.setProviderId(providerId);

        return member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
