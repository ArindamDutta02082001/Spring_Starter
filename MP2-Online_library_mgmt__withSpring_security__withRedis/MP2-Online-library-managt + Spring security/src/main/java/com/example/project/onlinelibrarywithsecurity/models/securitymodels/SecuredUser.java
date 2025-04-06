package com.example.project.onlinelibrarywithsecurity.models.securitymodels;

import com.example.project.onlinelibrarywithsecurity.models.libraryModels.Student;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

// userdetails come default from security spring coming from spring-security
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SecuredUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private int id;

    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private String authorities; // login_faculty::access_library

    // joining admin to secured user
    @OneToOne(mappedBy = "securedUser")
    @JsonIgnoreProperties(value = {"securedUser"})
    private Admin admin;

    @OneToOne(mappedBy = "securedUser")
    @JsonIgnoreProperties(value = {"securedUser"})
    private Student student;




    // not using | to be used when a single user to be given multiple roles
    private final static String AUTHORITIES_DELIMITER = "::";
    @Override
    public Collection<? extends GrantedAuthority > getAuthorities() {
        String[] authoritiesList = this.authorities.split(AUTHORITIES_DELIMITER);
        return Arrays.stream(authoritiesList)
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());


    }

    // using this
    public String getAuthoritiess()
    {
        return authorities;
    }
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    // the below 4 functions let you  to enable the account , set expiration and all
    // etc according to the custom logic you write

    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked  = true ;
    private boolean isCredentialsNonExpired  = true ;
    private boolean isEnabled  = true ;

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
