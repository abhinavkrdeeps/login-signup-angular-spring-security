package com.wissen.training.loginsignupspringboot.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role(String name){
        this.name=name;
    }

}
