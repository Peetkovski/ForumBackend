package com.example.nukatokenajwt.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long postId;
    @NotNull
    private String postTitle;
    private String postUrl;
    private Integer voteCount;
    @Lob
    @NotNull
    private String postDescription;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Categories category;
    @CreationTimestamp
    private Date dateCreated;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userName", referencedColumnName = "userName")
    private User user;


}
