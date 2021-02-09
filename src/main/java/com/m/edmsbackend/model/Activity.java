package com.m.edmsbackend.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity(name = "activity")
@Data
@DynamicUpdate
@DynamicInsert
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date gmtCreate;
    private String name;
    private String place;
    private String brief;
    private Integer status;

    private Integer needApply;
    private Integer maxPerson;
    private Date startTime;
    private Date endTime;
    
    private Long workerId;
}
