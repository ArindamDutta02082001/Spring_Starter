package com.example.project.onlinelibrarywithsecurity.models.securitymodels;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @OneToOne
    @JoinColumn
    @JsonIgnoreProperties(value = {"admin"})
    private SecuredUser securedUser;
}