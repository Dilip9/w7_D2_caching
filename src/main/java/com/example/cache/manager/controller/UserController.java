package com.example.cache.manager.controller;

import com.example.cache.manager.models.entities.UserInfo;
import com.example.cache.manager.models.requests.CreateUserRequest;
import com.example.cache.manager.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfo> createUser(@RequestBody @Valid CreateUserRequest request){
        return new ResponseEntity<>(userService.createNewUser(request), HttpStatus.CREATED);
    }


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfo> findUser(@RequestParam(required = false) String email){
        return new ResponseEntity<>(userService.findUser(email), HttpStatus.CREATED);
    }

    @GetMapping(value = "/user/{emailId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<UserInfo>> fetchUserByEmail(@PathVariable String emailId){
        log.info("Request Received for fetching user by {}  ", emailId);
        return new ResponseEntity<>(userService.fetchOneById(emailId), HttpStatus.OK);
    }
}
