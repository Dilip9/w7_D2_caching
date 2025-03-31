package com.example.cache.manager.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(indexes = {@Index(name = "UNIQUE_EMAIL", columnList = "emailId", unique = true)})
// @Index(name = "UNIQUE_PHONE_NUMBER", columnList = "phoneNumber", unique = true)
public class UserInfo implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;

    @Column(length = 15, unique = true)
    Long phoneNumber;

    String emailId;


}
