package com.m.edmsbackend.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity(name = "sales_record")
@Data
@DynamicUpdate
@DynamicInsert
public class SalesRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date gmtCreate;

    private String elderName;
    private Date date; 
    private String phoneNumber;
    private String demand;
    private Integer moveIn;
    private String returnVisitRecord;

    private Long salesPersonId;
}
