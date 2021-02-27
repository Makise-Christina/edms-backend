package com.m.edmsbackend.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity(name = "announcement")
@Data
@DynamicUpdate
@DynamicInsert
public class Announcement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    private String title;
    private String content;
    private Integer type;
    private Integer workerId;
}

