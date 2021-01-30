package com.m.edmsbackend.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity(name = "warehouse_record")
@Data
@DynamicUpdate
@DynamicInsert
public class WarehouseRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date gmtCreate;

    private Long itemId;
    private Integer type;
    private Integer quantity;
    private Long workerId;
}
