package com.m.edmsbackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.m.edmsbackend.dao.IItemRepository;
import com.m.edmsbackend.dto.ItemDto;
import com.m.edmsbackend.dao.IWarehouseRecordRepository;
import com.m.edmsbackend.dto.WarehouseRecordDto;
import com.m.edmsbackend.exception.LoginException;
import com.m.edmsbackend.exception.ServerErrorException;
import com.m.edmsbackend.model.Item;
import com.m.edmsbackend.model.WarehouseRecord;
import com.m.edmsbackend.utils.DataUtils;
import com.m.edmsbackend.utils.JwtUtils;
import com.m.edmsbackend.utils.RedisUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class ItemService {
    @Resource
    private IItemRepository itemRepository;
    @Resource
    private IWarehouseRecordRepository warehouseRecordRepository;

    public ItemDto addItem(ItemDto newItem) {
        Item item = new Item();
        DataUtils.copyProperties(newItem, item);
        Item itemRs = itemRepository.save(item);
        Long itemId = itemRs.getId();
        newItem.setId(itemId);
        if (newItem.getCount() == null) {
            newItem.setCount(0);
        }
        return newItem;
    }

    public List<ItemDto> findInventoryList(String name) {
        List<ItemDto> itemDtos = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        if (name != null) {
            items = itemRepository.findItemsByName(name);
        } 
        else {
            items = itemRepository.findAll();
        }

        for (Item item : items) {
            Long itemId = item.getId();
            ItemDto itemDto = new ItemDto();
            DataUtils.copyProperties(item, itemDto);
            // add warehouse records
            List<WarehouseRecordDto> recordDtos = new ArrayList<>();
            List<WarehouseRecord> records = warehouseRecordRepository.findRecordByItemId(itemId);
            for (WarehouseRecord record : records) {
                WarehouseRecordDto recordDto = new WarehouseRecordDto();
                DataUtils.copyProperties(record, recordDto);
                recordDto.setTime(record.getGmtCreate());
                recordDto.setEvent(record.getType());
                recordDtos.add(recordDto);
            }
            itemDto.setRecordList(recordDtos);
            itemDtos.add(itemDto);
        }
        
        return itemDtos;
    }

    public JSONObject changeItem(WarehouseRecordDto recordDto) {
        WarehouseRecord record = new WarehouseRecord();
        DataUtils.copyProperties(recordDto, record);
        record.setType(recordDto.getEvent());
        warehouseRecordRepository.save(record);
        Long itemId = record.getItemId();
        Item item = itemRepository.findItemById(itemId);
        Integer count = item.getCount();
        if (record.getType() == 1) {
            
            count = count + record.getQuantity();     
        }
        else if (record.getType() == 2 || record.getType() == 3) {
            count = count - record.getQuantity();
        }
        item.setCount(count);
        itemRepository.save(item);

        recordDto = new WarehouseRecordDto();
        DataUtils.copyProperties(record, recordDto);
        recordDto.setTime(record.getGmtCreate());
        recordDto.setEvent(record.getType());

        JSONObject result = new JSONObject();
        result.put("item", item);
        result.put("record", recordDto);
        return result;
    }
}
