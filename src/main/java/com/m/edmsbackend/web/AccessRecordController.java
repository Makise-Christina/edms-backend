package com.m.edmsbackend.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.m.edmsbackend.dto.AccessRecordDto;
import com.m.edmsbackend.enums.StdStatus;
import com.m.edmsbackend.exception.AuthorizationException;
import com.m.edmsbackend.exception.LoginException;
import com.m.edmsbackend.service.AccessRecordService;
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
public class AccessRecordController {
    @Resource
    private AccessRecordService accessRecordService;

    @Transactional
    @RequestMapping(value = "/access-record", method = RequestMethod.POST)
    public JSONObject addAccessRecord(@RequestBody JSONObject dataIn) {
        JSONObject accessRecordJson = dataIn.getJSONObject("record");
        AccessRecordDto accessRecord = JSON.toJavaObject(accessRecordJson, AccessRecordDto.class);
        AccessRecordDto res = accessRecordService.addAccessRecord(accessRecord);
        JSONObject json = new JSONObject();
        json.put("record", res);
        return StdResult.genResult(true, json);
    }

    @RequestMapping(value = "/access-record/{accessRecordId}", method = RequestMethod.GET)
    public JSONObject getAccessRecord(@PathVariable Long accessRecordId) {
        AccessRecordDto result = accessRecordService.getAccessRecord(accessRecordId);
        JSONObject json = new JSONObject();
        json.put("record", result);
        return StdResult.genResult(true, json);
    }

    @Transactional
    @RequestMapping(value = "/access-record/{accessRecordId}", method = RequestMethod.PUT)
    public JSONObject modifyAccessRecord(@PathVariable Long accessRecordId, @RequestBody JSONObject dataIn) {
        JSONObject accessRecordJson = dataIn.getJSONObject("record");
        AccessRecordDto accessRecord = JSON.toJavaObject(accessRecordJson, AccessRecordDto.class);
        AccessRecordDto result = accessRecordService.updateAccessRecord(accessRecordId, accessRecord);
        JSONObject json = new JSONObject();
        json.put("record", result);
        return StdResult.genResult(true, json);
    }

    @RequestMapping(value = "/access-record", method = RequestMethod.GET)
    public JSONObject getAccessRecordByStatus(@RequestParam(required = false) List<Integer> statusList) {
        List<AccessRecordDto> result = accessRecordService.getAccessRecordsByStatus(statusList);
        JSONObject json = new JSONObject();
        json.put("recordList", result);
        return StdResult.genResult(true, json);
    }

}
