package com.example.cache.manager.service;

import com.example.cache.manager.exceptions.ExceptionCode;
import com.example.cache.manager.exceptions.UserException;
import com.example.cache.manager.models.entities.UserInfo;
import com.example.cache.manager.models.requests.CreateUserRequest;
import com.example.cache.manager.repository.UserInfoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserInfoRepository repository;


//    @Autowired
//    RedisTemplate<String , Object> redisTemplate;

//    @Autowired
//    ObjectMapper objectMapper;




    private UserInfo saveOrUpdate(UserInfo userInfo){
        return repository.save(userInfo);
    }

    public UserInfo findUser(String emailId){
        log.info(" inside the service ");

//        UserInfo value = (UserInfo) redisTemplate.opsForValue().get("userCache::" + emailId);
//        if(Objects.nonNull(value)){
//            return value;
//        }


        return repository.findByEmailId(emailId)
                .orElseThrow(() -> new UserException(ExceptionCode.INVALID_USER));
//        redisTemplate.opsForValue().set("userCache::" + emailId, userInfo, Duration.ofMinutes(2));

//        return userInfo;
    }


    public Optional<UserInfo> fetchOneById(String email) {
        log.info(" finding user by {}", email);
        return repository.findByEmailId(email);
    }

    public UserInfo createNewUser(CreateUserRequest userRequest){
        //         // [memory block allocated  m2]  --> value associated
        var newUser = userRequest.toUserInfo();
        log.info(" trying to create new user {}  from request {} ", newUser, userRequest);
        // find existing user if exists
        var existingUser = repository.findByEmailIdOrPhoneNumber(newUser.getEmailId(), newUser.getPhoneNumber());
        log.info(" existing user {} ", existingUser);
        // if it does throw exception
        if(existingUser.isPresent()){
            log.error(" user found {} throwing exception ", existingUser);
            throw new UserException(ExceptionCode.USER_ALREADY_EXISTS);
        }
        // create new user
        // equals and hasCode comparison
       var userInfo = saveOrUpdate(newUser);
        log.info(" created new user {} ", userInfo);
        return userInfo;
    }

}
