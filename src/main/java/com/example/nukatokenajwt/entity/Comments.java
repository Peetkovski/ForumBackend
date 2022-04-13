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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @NotNull
    private String comment;
    private Integer voteCount;
    @CreationTimestamp
    private Date dateCommented;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userName", referencedColumnName = "userName")
    private User user;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private Post post;


   // private User user;
}
