package com.example.cache.manager.models.requests;

import com.example.cache.manager.models.entities.UserInfo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequest {

    @NotBlank
    String name;
    @Positive
    Long phoneNumber;
    @Email // this would pass for an empty string as well
    String emailId;


    public UserInfo toUserInfo(){
        return UserInfo.builder()
                .emailId(emailId)
                .name(name)
                .phoneNumber(phoneNumber)
                .build();
    }




}
