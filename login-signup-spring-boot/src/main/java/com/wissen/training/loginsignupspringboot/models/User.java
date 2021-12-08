package com.wissen.training.loginsignupspringboot.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class User implements Serializable {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "provider_user_id")
    private String providerUserId;
    @Column(name="email")
    private String email;
    @Column(name = "enabled")
    private boolean enabled;
    @Column(name = "display_name")
    private String display_name;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "password")
    private String password;
    @Column(name = "provider")
    private String provider;

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;
}
