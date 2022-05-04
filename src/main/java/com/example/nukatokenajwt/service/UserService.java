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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
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
    private final MailService mailService;
    private final RestorePasswordTokenRepository tokenRepository;

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
        adminUser.setUserPassword(getEncodedPassword("admin"));
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);

        User user = new User();
        user.setUserName("peet");
        user.setUserEmail("peet");
        user.setUserPassword(getEncodedPassword("peet123"));
        user.setUserPic("https://upload.wikimedia.org/wikipedia/commons/thumb/7/7c/User_font_awesome.svg/2048px-User_font_awesome.svg.png");
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

    public User changeUserPassword(ChangePasswordRequest request){
        RestorePasswordToken restorePasswordToken = tokenRepository.findRestorePasswordTokenByToken(request.getToken());
        User user = userDao.findUserByUserName(restorePasswordToken.getUser().getUserName());

        if (checkIfPasswordTokenValid(restorePasswordToken.getTimeDate(), restorePasswordToken.getValidUntil()) && !restorePasswordToken.isUsed()) {
            restorePasswordToken.setUsed(true);
        user.setUserPassword(getEncodedPassword(request.getUserPassword()));
        return userDao.save(user);
        } else{
            throw new PasswordTokenIsExpiredException(
                   "Password token is expired!"
            );

      }

    }

    @Async
    public void sendRestorePasswordCode(EmailRequest request){
        //TODO TYLKO JEDEN KOD WERYFIKACYJNY
        Boolean exists = userDao.selectExistsUserEmail(request.getUserEmail());
        if(exists){
            LocalTime localTime = LocalTime.now();
            LocalTime validUntilTime = localTime.plusMinutes(15);
            User user = userDao.findUserByUserEmail(request.getUserEmail());
            String token = restorePasswordToken(5);
            RestorePasswordToken resetPassword = new RestorePasswordToken();
            resetPassword.setToken(token);
            resetPassword.setTimeDate(localTime);
            resetPassword.setValidUntil(validUntilTime);
            resetPassword.setUser(user);
            resetPassword.setUsed(false);
            System.out.println(user);
                sendMail("Password Reset",
                    user.getUserEmail(),
                    "This is your code to restore password" + request.getUserEmail() + "This is your code:  "  + token );
            tokenRepository.save(resetPassword);
        } else{
            throw new UserNotFoundException(
                    "This email does not exists!"
            );
        }

    }

    public String restorePasswordToken(int n) {

        String AlphaPassword= "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz"
                + "!@#$%^&*";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaPassword.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaPassword
                    .charAt(index));
        }

        return sb.toString();
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


    protected void sendMail(String subject, String userEmail, String content){

        mailService.sendMail(new NotificationEmail(subject, userEmail , content));

    }

    private boolean checkIfPasswordTokenValid(LocalTime timeDate, LocalTime validUntil){
        if(timeDate.isAfter(validUntil)){
            return false;
        }
        else
            return true;
    }



    public String getEncodedPassword(String password){
        return passwordEncoder.encode(password);
    }


}
