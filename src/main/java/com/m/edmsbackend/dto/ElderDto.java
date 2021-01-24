package com.m.edmsbackend.dto;

import java.util.Date;
import java.util.List;


import com.m.edmsbackend.model.Elder;

import lombok.Data;

@Data
public class ElderDto extends Elder {
    private List<ContactDto> contactList;
}