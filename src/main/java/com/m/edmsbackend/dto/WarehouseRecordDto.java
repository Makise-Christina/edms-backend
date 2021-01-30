package com.m.edmsbackend.dto;

import java.util.Date;
import java.util.List;

import com.m.edmsbackend.model.WarehouseRecord;

import lombok.Data;

@Data
public class WarehouseRecordDto extends WarehouseRecord {
    private Date time;
    private Integer event;
}