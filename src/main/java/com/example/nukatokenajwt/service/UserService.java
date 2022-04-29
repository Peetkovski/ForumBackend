package com.example.nukatokenajwt.service;

import com.example.nukatokenajwt.dao.RoleDao;
import com.example.nukatokenajwt.dao.UserDao;
import com.example.nukatokenajwt.entity.Gender;
import com.example.nukatokenajwt.entity.Role;
import com.example.nukatokenajwt.entity.User;
import com.example.nukatokenajwt.entity.UserInfoResponse;
import com.example.nukatokenajwt.service.Exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private final UserDao userDao;

    private final RoleDao roleDao;

    private final PasswordEncoder passwordEncoder;

    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleDao.save(userRole);

        User adminUser = new User();
        adminUser.setUserName("admin123");
        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);

        User user = new User();
        user.setUserName("raj123");
        user.setUserPassword(getEncodedPassword("raj123"));
        user.setUserPic("https://upload.wikimedia.org/wikipedia/commons/thumb/7/7c/User_font_awesome.svg/2048px-User_font_awesome.svg.png");
        user.setUserEmail("raj@wp.pl");
        user.setGender(Gender.MALE);
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRole(userRoles);
        userDao.save(user);
    }

    public User registerNewUser(User user) {
//        Role role = roleDao.findById("User").get();
        Role role = new Role();
        role.setRoleName("User");
        role.setRoleDescription("Default role for user");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));

        return userDao.save(user);
    }

    public UserInfoResponse findUser(String username){

        Boolean exists = userDao.selectExistsUsername(username);
        if(!exists){

            throw new UserNotFoundException(
                    "Username not found!"
            );

        }
        else {
            UserInfoResponse userInfoResponse = new UserInfoResponse();
            User user = userDao.findUserByUserName(username);
            userInfoResponse.setUserName(user.getUserName());
            userInfoResponse.setUserPic(user.getUserPic());
            userInfoResponse.setGender(user.getGender());

            return userInfoResponse;
        }
    }



    public String getEncodedPassword(String password){
        return passwordEncoder.encode(password);
    }


}
