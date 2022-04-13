package com.example.nukatokenajwt.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class User {

    @Id
    @NotNull
    private String userName;
    private String userFirstName;
    private String userLastName;
    @NotNull
    private String userPassword;
    @NotNull
    private String userEmail;
    private String userPic;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID")
            }
    )
    private Set<Role> role;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private Post post;
}
