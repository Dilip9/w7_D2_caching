package com.example.cache.manager.service;

import com.example.cache.manager.exceptions.UserException;
import com.example.cache.manager.models.entities.UserInfo;
import com.example.cache.manager.models.requests.CreateUserRequest;
import com.example.cache.manager.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {




    @Mock
    UserInfoRepository repository;

    @InjectMocks
    UserService underTest;

    private CreateUserRequest request;

    private static final String EMAIL = "emailId";


    // would be invoked before each test is run
    @BeforeAll
    public static void beforeAll(){
        log.info("this method is invoked before all the classes are added / inject it when mocking static");
    }



    @Test
    public void findUser_ValidEmail_UserReturned(){
        // given // valid EMAIL
        // input that is pre-requisite
        UserInfo persistedUser = request.toUserInfo();
        persistedUser.setId(UUID.randomUUID().toString());
        // when (calling that particular method)
        Mockito.when(repository.findByEmailId(EMAIL)).thenReturn(Optional.of(persistedUser));

        //
        UserInfo existingUser = underTest.findUser(EMAIL);

        // then --> assertions
        assertEquals(persistedUser, existingUser);
    }




    @Test
    public void findUser_InValidEmail_ExceptionThrown(){
        // given // valid EMAIL
        // input that is pre-requisite
        UserInfo persistedUser = request.toUserInfo();
        persistedUser.setId(UUID.randomUUID().toString());
        // when (calling that particular method)
        Mockito.when(repository.findByEmailId(EMAIL)).thenThrow(UserException.class);

        // then --> assertions
        assertThrows(UserException.class, () -> underTest.findUser(EMAIL));
    }



    @BeforeEach
    public void init(){
//        repository = Mockito.mock(UserInfoRepository.class);
//        userService = Mockito.mock(UserService.class);
        request = CreateUserRequest.builder()
                .emailId(EMAIL)
                .name("name")
                .phoneNumber(9911991199L).build();

    }


    @Test
    public void fetchOneById_ValidEmail_ReturnUser(){
        // given EMAIL
        UserInfo exitingUserInfo = request.toUserInfo();
        Mockito.when(repository.findByEmailId(EMAIL)).thenReturn(Optional.of(exitingUserInfo));
        Optional<UserInfo> gotUserInfo = underTest.fetchOneById(EMAIL);
        assertEquals(Optional.of(exitingUserInfo), gotUserInfo);
    }


    // integration
    // MethodName_StateUnderTest_ExpectedBehavior
    // method_given_when_then
    @Test
    public void createNewUser_ValidRequestReceived_UserCreated(){
        //given
        // when


        /**
         *                  @Mock
         *  Service         repository Method (instance)
         *          ---->
         *
         * @InjectMocks
         *
         */
        Mockito.when(repository.findByEmailIdOrPhoneNumber(request.getEmailId(), request.getPhoneNumber()))
                .thenReturn(Optional.empty());
        UserInfo tryingToPersist = request.toUserInfo();
        tryingToPersist.setId(UUID.randomUUID().toString());


        // no matter what input is.
        Mockito.when(repository.save(any())).thenReturn(tryingToPersist);

        /**
         * Understand difference between above and below
         *
         */
        // toUserInfo ---> creating a new instance
        // [memory block allocated  m1]  --> value associated
//        Mockito.when(repository.save(request.toUserInfo())).thenReturn(tryingToPersist);

        UserInfo persisted = underTest.createNewUser(request);
        // then
        // compare the expected output with the output generated
        assertEquals(tryingToPersist, persisted);
    }



    @Test
    public void createNewUser_ValidRequestButUserExists_ExceptionThrown(){
        log.info(" Inside test ");
        UserInfo testInfo = request.toUserInfo();
        // user is existing
        Mockito.when(repository.findByEmailIdOrPhoneNumber(anyString(), anyLong())).thenReturn(Optional.of(testInfo));

        Assertions.assertThrows(UserException.class, () -> underTest.createNewUser(request));


    }







}
