package com.example.cache.manager.repository;

import com.example.cache.manager.models.entities.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

/**
 * Annotation required in case of JPA repository
 */
@DataJpaTest
@Slf4j
public class UserInfoRepositoryTest {


    @Autowired
    UserInfoRepository repository;


    UserInfo userInfo;

    private static final String EMAIL = "hey@gmail.com";

    private static final Long PHONE_NUMBER= 9911991199L;

    @BeforeEach
    public void init(){
        repository.deleteAll();
        userInfo = UserInfo.builder()
                .emailId(EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .name("testUserName")
                .build();
    }




    @Test
    public void findByEmailId_ValidEmail_ReturnExistingUser(){
        // saving the user
        repository.save(userInfo);
        // check if it exists by email;
        Optional<UserInfo> persistedUserInfo = repository.findByEmailId(EMAIL);
        log.info("got existing user {} ", persistedUserInfo);

        Assertions.assertNotNull(persistedUserInfo);
    }


    @Test
    public void findByEmailId_InValidEmail_ReturnEmpty(){
        // saving the user
        // check if it exists by email;
        Optional<UserInfo> persistedUserInfo = repository.findByEmailId(EMAIL);
        log.info("got existing user {} ", persistedUserInfo);
        Assertions.assertTrue(persistedUserInfo.isEmpty());
    }



    @Test
    public void findByEmailIdOrPhoneNumber_ValidEmail_ReturnExistingUser(){
        // saving the user
        repository.save(userInfo);
        // check if it exists by email;
        Optional<UserInfo> persistedUserInfo = repository.findByEmailIdOrPhoneNumber(EMAIL, null);
        log.info("got existing user {} ", persistedUserInfo);
        Assertions.assertNotNull(persistedUserInfo);
    }


    @Test
    public void findByEmailIdOrPhoneNumber_ValidPhoneNumnber_ReturnExistingUser(){
        // saving the user
        repository.save(userInfo);
        // check if it exists by email;
        Optional<UserInfo> persistedUserInfo = repository.findByEmailIdOrPhoneNumber(null, PHONE_NUMBER);
        log.info("got existing user {} ", persistedUserInfo);
        Assertions.assertNotNull(persistedUserInfo);
    }



    @Test
    public void findByEmailIdOrPhoneNumber_NoUserExists_ReturnEmpty(){
        // saving the user
        // check if it exists by email;
        Optional<UserInfo> persistedUserInfo = repository.findByEmailIdOrPhoneNumber(EMAIL, PHONE_NUMBER);
        log.info("got existing user {} ", persistedUserInfo);
        Assertions.assertTrue(persistedUserInfo.isEmpty());
    }


}
