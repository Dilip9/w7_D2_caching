package com.example.cache.manager.repository;

import com.example.cache.manager.models.entities.UserInfo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    // check if the user exists
    Optional<UserInfo> findByEmailIdOrPhoneNumber(String emailId, Long phoneNumber);

    @Cacheable(cacheNames = "userCache", unless = "#result== null")
    Optional<UserInfo> findByEmailId(String email);


}
