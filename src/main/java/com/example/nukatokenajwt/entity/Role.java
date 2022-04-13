package com.example.nukatokenajwt.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Role {

    @Id
    private String roleName;
    private String roleDescription;




}
