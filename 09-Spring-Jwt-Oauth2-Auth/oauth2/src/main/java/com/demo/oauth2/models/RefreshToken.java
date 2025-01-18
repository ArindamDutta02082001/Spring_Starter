package com.demo.oauth2.models;


import jakarta.persistence.*;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RefreshToken {


    public Integer id = GenerationType.AUTO.ordinal();

    @Id
    public String refreshToken;

    public String tokenType = "Bearer";

    public boolean revoked;

    public boolean expired;

    @ManyToOne
    @JoinColumn(name = "userId")
    public DemoUser user;
}