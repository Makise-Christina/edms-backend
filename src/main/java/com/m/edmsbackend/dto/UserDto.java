package com.m.edmsbackend.dto;

import java.util.Date;

import com.m.edmsbackend.model.User;

import lombok.Data;

@Data
public class UserDto extends User {
    private String username;
    private Boolean isBlock;
    private String token;
    private String code;
    private Date lastLoginTime;
}
