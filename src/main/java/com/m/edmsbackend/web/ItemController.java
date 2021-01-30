package com.m.edmsbackend.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.m.edmsbackend.dto.ItemDto;
import com.m.edmsbackend.dto.WarehouseRecordDto;
import com.m.edmsbackend.enums.StdStatus;
import com.m.edmsbackend.exception.AuthorizationException;
import com.m.edmsbackend.exception.LoginException;
import com.m.edmsbackend.service.ItemService;
import com.m.edmsbackend.utils.StdResult;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;

@RestController
public class ItemController {
    @Resource
    private ItemService itemService;

    @RequestMapping(value = "/warehouse", method = RequestMethod.GET)
    public JSONObject getItemInfos(@RequestParam(required = false) String name) {
        List<ItemDto> result = itemService.findInventoryList(name);
        JSONObject json = new JSONObject();
        json.put("inventoryList", result);
        return StdResult.genResult(true, json);
    }

    @Transactional
    @RequestMapping(value = "/warehouse/item", method = RequestMethod.POST)
    public JSONObject addItem(@RequestBody JSONObject dataIn) {
        String name = dataIn.getString("name");
        String unit = dataIn.getString("unit");
        ItemDto item = new ItemDto();
        item.setName(name);
        item.setUnit(unit);
        item.setCount(0);
        ItemDto res = itemService.addItem(item);
        JSONObject json = new JSONObject();
        json.put("item", res);
        return StdResult.genResult(true, json);
    }

    @Transactional
    @RequestMapping(value = "/warehouse/record", method = RequestMethod.POST)
    public JSONObject changeItem(@RequestBody JSONObject dataIn) {
        JSONObject recordJson = dataIn.getJSONObject("record");
        WarehouseRecordDto recordDto = JSON.toJavaObject(recordJson, WarehouseRecordDto.class);
        JSONObject json = itemService.changeItem(recordDto);
        return StdResult.genResult(true, json);
    }
}