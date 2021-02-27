package com.m.edmsbackend.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity(name = "task")
@Data
@DynamicUpdate
@DynamicInsert
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    private Long workerId;
    private Long elderId;

    private String name;
    private String description;
    private Integer status;
    private Integer type;

    private Date finishedTimes;
    private Date expectedTimes;
}
