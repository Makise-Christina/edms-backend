package com.m.edmsbackend.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity(name = "elder")
@Data
@DynamicUpdate
@DynamicInsert
public class Elder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;
    private Integer gender;
    private Integer maritalStatus;
    private Integer education;
    private String ethnicity;
    private String job;
    private String company;
    private String address;
    private String idCardNumber;
    private String medicalHistory;
    private Integer healthInsurance;
    private String hospital;
    private Integer nursingLevel;

    private String demand;
    private String records;
    private String picture;

    private Long buildingId;
    private Long floorId;
    private Long roomId;
    private Long bedId;
}
