package com.m.edmsbackend.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.m.edmsbackend.dto.NursingRecordDto;
import com.m.edmsbackend.enums.StdStatus;
import com.m.edmsbackend.exception.AuthorizationException;
import com.m.edmsbackend.exception.LoginException;
import com.m.edmsbackend.service.NursingRecordService;
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
public class NursingRecordController {
    @Resource
    private NursingRecordService nursingRecordService;

    @Transactional
    @RequestMapping(value = "/nursing-record", method = RequestMethod.POST)
    public JSONObject addNursingRecord(@RequestBody JSONObject dataIn) {
        JSONObject nursingRecordJson = dataIn.getJSONObject("record");
        NursingRecordDto nursingRecord = JSON.toJavaObject(nursingRecordJson, NursingRecordDto.class);
        NursingRecordDto res = nursingRecordService.addNursingRecord(nursingRecord);
        JSONObject json = new JSONObject();
        json.put("record", res);
        return StdResult.genResult(true, json);
    }

    @RequestMapping(value = "/nursing-record/{nursingRecordId}", method = RequestMethod.GET)
    public JSONObject getNursingRecord(@PathVariable Long nursingRecordId) {
        NursingRecordDto result = nursingRecordService.getNursingRecord(nursingRecordId);
        JSONObject json = new JSONObject();
        json.put("record", result);
        return StdResult.genResult(true, json);
    }

    @RequestMapping(value = "/nursing-record", method = RequestMethod.GET)
    public JSONObject getNursingRecordByElder(@RequestParam(required = false) Long elderId) {
        List<NursingRecordDto> result = nursingRecordService.getNursingRecordsByElderId(elderId);
        JSONObject json = new JSONObject();
        json.put("recordList", result);
        return StdResult.genResult(true, json);
    }

}
