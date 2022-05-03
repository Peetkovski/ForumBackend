package com.example.nukatokenajwt.service;

import com.example.nukatokenajwt.dao.RestorePasswordTokenRepository;
import com.example.nukatokenajwt.dao.RoleDao;
import com.example.nukatokenajwt.dao.UserDao;
import com.example.nukatokenajwt.entity.*;
import com.example.nukatokenajwt.entity.request.ChangePasswordRequest;
import com.example.nukatokenajwt.entity.request.EmailRequest;
import com.example.nukatokenajwt.entity.response.UserInfoResponse;
import com.example.nukatokenajwt.service.Exceptions.UserNotFoundException;
import com.example.nukatokenajwt.service.mail.MailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    UserService userService;

    @Mock
    UserDao userDao;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    RoleDao roleDao;

    @Mock
    MailService mailService;

    @Mock
    RestorePasswordTokenRepository restorePasswordTokenRepository;


    @BeforeEach
    void setUp() {



        userService = new UserService(userDao, roleDao, passwordEncoder, mailService, restorePasswordTokenRepository);

    }

    @Test
    void registerNewUser() {
        User user = new User();
        user.setUserName("Piotrek");
        user.setUserPassword("Haslo");
        Set<Role> userRoles = new HashSet<>();
        user.setRole(userRoles);
        userService.registerNewUser(user);
        ArgumentCaptor<User> argumentCaptor =
                ArgumentCaptor.forClass(User.class);
        Mockito.verify(userDao)
                .save(argumentCaptor.capture());
        User capturedUser = argumentCaptor.getValue();
        assertEquals(capturedUser, user);
    }

    @Test
    void sendMail(){
        User user = new User();
        user.setUserEmail("peet@wp.pl");
        userService.sendMail("Ess", user.getUserEmail(), "description");
    }


    @Test
    void notChangeUserPassword(){
        RestorePasswordToken restorePasswordToken = new RestorePasswordToken();
        User user = new User();
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();


        given(userDao.findUserByUserName(user.getUserName()))
                .willReturn(user);

        assertThatThrownBy(() -> userService.changeUserPassword(changePasswordRequest))
                .isInstanceOf(PasswordTokenIsExpiredException.class)
                .hasMessageContaining("Password token is expired!");
    }


    @Test
    void sendRestorePasswordCode(){
        User user = new User();
        user.setUserEmail("Tom@wp.pl");
        userDao.save(user);
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setUserEmail("Tom@wp.pl");
        when(userDao.selectExistsUserEmail(emailRequest.getUserEmail())).thenReturn(true);
        when(userDao.findUserByUserEmail(emailRequest.getUserEmail())).thenReturn(user);

        userService.sendRestorePasswordCode(emailRequest);

    }

    @Test
    void notSendRestorePasswordCode(){
        User user = new User();
        user.setUserEmail("Tom@wp.pl");
        userDao.save(user);
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setUserEmail(user.getUserEmail());

        given(userDao.selectExistsUserEmail(user.getUserEmail()))
                .willReturn(false);

        assertThatThrownBy(() -> userService.sendRestorePasswordCode(emailRequest))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("This email does not exists!");
    }


    @Test
    void findUser() {
        User user = new User();
        user.setUserName("Peet");
        userDao.save(user);
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setUserName(user.getUserName());
        userInfoResponse.setUserPic(user.getUserPic());
        userInfoResponse.setGender(user.getGender());

        UserInfoResponse userInfoResponse1 = new UserInfoResponse();
        userInfoResponse1.setUserName("Peet");
        userInfoResponse1.setGender(user.getGender());
        userInfoResponse1.setUserPic(user.getUserPic());

        Assertions.assertEquals(userInfoResponse, userInfoResponse1

        );

    }

    @Test
    void throwUsernameNotFoundException(){
        UserInfoResponse userInfoResponse = null;
        String username = "Peet";
        assertThatThrownBy(() -> userService.findUser("Peet"))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("Username not found!");
    }



    public String getEncodedPassword(String password){
        return passwordEncoder.encode(password);
    }

}


