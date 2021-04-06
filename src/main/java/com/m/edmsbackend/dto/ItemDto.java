package com.m.edmsbackend.dto;

import java.util.Date;
import java.util.List;

import com.m.edmsbackend.model.Item;

import lombok.Data;

@Data
public class ItemDto extends Item {
    private Item item;
    private List<WarehouseRecordDto> recordList;
}
