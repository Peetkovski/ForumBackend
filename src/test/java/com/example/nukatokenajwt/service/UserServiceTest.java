package com.example.nukatokenajwt.service;

import com.example.nukatokenajwt.dao.RoleDao;
import com.example.nukatokenajwt.dao.UserDao;
import com.example.nukatokenajwt.entity.*;
import com.example.nukatokenajwt.service.Exceptions.UserNotFoundException;
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


    @BeforeEach
    void setUp() {



        userService = new UserService(userDao, roleDao, passwordEncoder);

    }

    @Test
    void registerNewUser() {

//        User user = new User(
//                "Piter",
//                "haslo",
//                "email",
//                "zdjecie",
//                Gender.MALE,
//                 new HashSet<>(),
//                new Post()
//        );

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


