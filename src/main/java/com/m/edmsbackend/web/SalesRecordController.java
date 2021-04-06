package com.m.edmsbackend.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.m.edmsbackend.dto.SalesRecordDto;
import com.m.edmsbackend.enums.StdStatus;
import com.m.edmsbackend.exception.AuthorizationException;
import com.m.edmsbackend.exception.LoginException;
import com.m.edmsbackend.service.SalesRecordService;
import com.m.edmsbackend.utils.StdResult;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSON;

@RestController
public class SalesRecordController {
    @Resource
    private SalesRecordService salesRecordService;

    @Transactional
    @RequestMapping(value = "/sales", method = RequestMethod.POST)
    public JSONObject addSalesRecord(@RequestBody JSONObject dataIn) {
        JSONObject salesRecordJson = dataIn.getJSONObject("record");
        SalesRecordDto salesRecord = JSON.toJavaObject(salesRecordJson, SalesRecordDto.class);
        SalesRecordDto res = salesRecordService.addSalesRecord(salesRecord);
        JSONObject json = new JSONObject();
        json.put("record", res);
        return StdResult.genResult(true, json);
    }

    @RequestMapping(value = "/sales", method = RequestMethod.GET)
    public JSONObject getSalesRecords(
        @RequestParam(required = false) String elderName,
        @RequestParam(required = false) Long salesPersonId) {
        List<SalesRecordDto> result = salesRecordService.getSalesRecords(elderName,salesPersonId);
        JSONObject json = new JSONObject();
        json.put("recordList", result);
        return StdResult.genResult(true, json);
    }

    @Transactional
    @RequestMapping(value = "/sales/{salesRecordId}", method = RequestMethod.PUT)
    public JSONObject modifySalesRecord(@PathVariable Long salesRecordId, @RequestBody JSONObject dataIn) {
        JSONObject salesRecordJson = dataIn.getJSONObject("record");
        SalesRecordDto salesRecord = JSON.toJavaObject(salesRecordJson, SalesRecordDto.class);
        SalesRecordDto result = salesRecordService.updateSalesRecord(salesRecordId, salesRecord);
        JSONObject json = new JSONObject();
        json.put("record", result);
        return StdResult.genResult(true, json);
    }

    @Transactional
    @RequestMapping(value = "/sales/{salesRecordId}", method = RequestMethod.DELETE)
    public JSONObject deleteSalesRecord(@PathVariable Long salesRecordId) {
        salesRecordService.removeSalesRecord(salesRecordId);
        JSONObject json = new JSONObject();
        return StdResult.genResult(true, json);
    }

}
