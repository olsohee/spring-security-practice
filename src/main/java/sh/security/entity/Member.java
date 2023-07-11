package sh.security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Member {

    @Id @GeneratedValue
    private Long id;

    private String loginId;
    private String password;
    private String username;
    private String email;
    private String phone;

    private String provider;
    private String providerId;
}
