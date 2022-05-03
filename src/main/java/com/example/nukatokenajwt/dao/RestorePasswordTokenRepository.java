package com.example.nukatokenajwt.dao;

import com.example.nukatokenajwt.entity.RestorePasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestorePasswordTokenRepository extends JpaRepository<RestorePasswordToken, Long> {

    RestorePasswordToken findRestorePasswordTokenByToken(String token);
}
