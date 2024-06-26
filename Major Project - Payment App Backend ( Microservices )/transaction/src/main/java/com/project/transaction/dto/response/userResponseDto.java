package com.project.transaction.dto.response;

import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class userResponseDto {

    private int userId;

    private String name;

    @Column(nullable = false, unique = true)
    private String mobile;

    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    private List<Authority> authorities;

}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class Authority implements GrantedAuthority {
    private String authority;
}
