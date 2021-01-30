package com.m.edmsbackend.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.m.edmsbackend.dto.ElderDto;
import com.m.edmsbackend.enums.StdStatus;
import com.m.edmsbackend.exception.AuthorizationException;
import com.m.edmsbackend.exception.LoginException;
import com.m.edmsbackend.service.ElderService;
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
public class ElderController {
    @Resource
    private ElderService elderService;

    @Transactional
    @RequestMapping(value = "/elder", method = RequestMethod.POST)
    public JSONObject addElder(@RequestBody JSONObject dataIn) {
        JSONObject elderJson = dataIn.getJSONObject("elder");
        ElderDto elder = JSON.toJavaObject(elderJson, ElderDto.class);
        ElderDto res = elderService.addElder(elder);
        JSONObject json = new JSONObject();
        json.put("elder", res);
        return StdResult.genResult(true, json);
    }

    @RequestMapping(value = "/elder", method = RequestMethod.GET)
    public JSONObject getElder(
        @RequestParam(required = false) String name, 
        @RequestParam(required = false) Integer gender,
        @RequestParam(required = false) Integer nursingLevel,
        @RequestParam(required = false) Integer startAge,
        @RequestParam(required = false) Integer endAge,
        @RequestParam(required = false) Integer offset,
        @RequestParam(required = false) Integer limit
    ) {

        List<ElderDto> result = elderService.getElders(
            name, 
            gender, 
            nursingLevel, 
            startAge, 
            endAge, 
            offset, 
            limit);
        JSONObject json = new JSONObject();
        json.put("elderList", result);
        return StdResult.genResult(true, json);
    }

    @Transactional
    @RequestMapping(value = "/elder/{elderId}", method = RequestMethod.PUT)
    public JSONObject modifyElder(@PathVariable Long elderId, @RequestBody JSONObject dataIn) {
        JSONObject elderJson = dataIn.getJSONObject("elder");
        ElderDto elder = JSON.toJavaObject(elderJson, ElderDto.class);
        ElderDto result = elderService.updateElder(elderId, elder);
        JSONObject json = new JSONObject();
        json.put("elder", result);
        return StdResult.genResult(true, json);
    }

    @Transactional
    @RequestMapping(value = "/elder/{elderId}", method = RequestMethod.DELETE)
    public JSONObject modifyElder(@PathVariable Long elderId) {
        try {
            elderService.removeElder(elderId);
            return StdResult.genResult(true, new JSONObject());
        } catch (Exception e) {
            System.out.println(e);
            return StdResult.genResult(false, new JSONObject());
        }
    }

}
