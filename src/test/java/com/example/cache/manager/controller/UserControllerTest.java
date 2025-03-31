package com.example.cache.manager.controller;

import com.example.cache.manager.controller.advice.GlobalControllerAdvice;
import com.example.cache.manager.exceptions.ExceptionCode;
import com.example.cache.manager.exceptions.UserException;
import com.example.cache.manager.models.entities.UserInfo;
import com.example.cache.manager.models.requests.CreateUserRequest;
import com.example.cache.manager.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    MockMvc mockMvc;

    private GlobalControllerAdvice controllerAdvice = new GlobalControllerAdvice();
    private ObjectMapper objectMapper = new ObjectMapper();
    private  CreateUserRequest userRequest;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(controllerAdvice).build();
        userRequest = CreateUserRequest.builder()
                .phoneNumber(9911991199L)
                .name("Joey")
                .emailId("actor@gmail.com")
                .build();
    }



    @Test
    public void createUser_DuplicateUser_UserExceptionThrown() throws Exception {
        // when
        Mockito.when(userService.createNewUser(userRequest))
                .thenThrow(new UserException(ExceptionCode.USER_ALREADY_EXISTS));

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest());
//                .andExpect(jsonPath("$.errorCode").value("LM_USER_001"));
    }

    @Test
    public void createUser_BadRequest_ExceptionThrown() throws Exception {
        userRequest.setEmailId("invalidEmail");
        mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest());
        ;
    }


        @Test
    public void createUser_ValidRequest_UserReturned() throws Exception {
        UserInfo userInfo = userRequest.toUserInfo();
        Mockito.when(userService.createNewUser(userRequest)).thenReturn(userInfo);
        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void createUser_ValidRequest_UserIsCreated(){
        // given
        UserInfo userInfo = userRequest.toUserInfo();
        // when
        Mockito.when(userService.createNewUser(userRequest)).thenReturn(userInfo);
        ResponseEntity<UserInfo> userResponse = userController.createUser(userRequest);
        // then
        Assertions.assertEquals(HttpStatus.CREATED, userResponse.getStatusCode());
        Assertions.assertEquals(userInfo, userResponse.getBody());
    }
}
