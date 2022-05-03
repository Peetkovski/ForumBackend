package com.example.nukatokenajwt.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token")
public class RestorePasswordToken {
    @Id
    @GeneratedValue
    private Integer id;
    private String token;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userName", referencedColumnName = "userName")
    private User user;
    @CreationTimestamp
    private Date dateCreated;
    private LocalTime timeDate;
    private LocalTime validUntil;
    private boolean isUsed;
}
