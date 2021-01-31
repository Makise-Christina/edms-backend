package com.m.edmsbackend.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity(name = "nursing_record")
@Data
@DynamicUpdate
@DynamicInsert
public class NursingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date gmtCreate;
    
    private Long elderId;
    private Long employeeId;
    private Double bloodGlucose;
    private Integer sbp;
    private Integer dbp;
    private Integer heartRate;
}