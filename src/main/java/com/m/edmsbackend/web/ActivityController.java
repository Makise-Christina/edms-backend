package com.m.edmsbackend.web;

import java.util.List;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.m.edmsbackend.dto.ActivityDto;
import com.m.edmsbackend.dto.ElderDto;
import com.m.edmsbackend.enums.StdStatus;
import com.m.edmsbackend.exception.AuthorizationException;
import com.m.edmsbackend.exception.LoginException;
import com.m.edmsbackend.service.ActivityService;
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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSON;

@RestController
public class ActivityController {
    @Resource
    private ActivityService activityService;
    @Resource
    private ElderService elderService;

    @Transactional
    @RequestMapping(value = "/activity", method = RequestMethod.POST)
    public JSONObject addActivity(@RequestBody JSONObject dataIn) {
        JSONObject activityJson = dataIn.getJSONObject("activity");
        ActivityDto activity = JSON.toJavaObject(activityJson, ActivityDto.class);
        ActivityDto res = activityService.addActivity(activity);
        JSONObject json = new JSONObject();
        json.put("activity", res);
        return StdResult.genResult(true, json);
    }

    @Transactional
    @RequestMapping(value = "/activity/{activityId}/elder/{elderId}", method = RequestMethod.POST)
    public JSONObject registerActivity(@PathVariable Long activityId, @PathVariable Long elderId) {
        activityService.registerActivity(activityId, elderId);
        JSONObject json = new JSONObject();
        return StdResult.genResult(true, json);
    }

    @Transactional
    @RequestMapping(value = "/activity/{activityId}/elder/{elderId}", method = RequestMethod.DELETE)
    public JSONObject unregisterActivity(@PathVariable Long activityId, @PathVariable Long elderId) {
        activityService.unregisterActivity(activityId, elderId);
        JSONObject json = new JSONObject();
        return StdResult.genResult(true, json);
    }

    @Transactional
    @RequestMapping(value = "/activity/{activityId}", method = RequestMethod.PUT)
    public JSONObject modifyActivity(@PathVariable Long activityId, @RequestBody JSONObject dataIn) {
        JSONObject activityJson = dataIn.getJSONObject("activity");
        ActivityDto activity = JSON.toJavaObject(activityJson, ActivityDto.class);
        ActivityDto res = activityService.updateActivity(activityId, activity);
        JSONObject json = new JSONObject();
        json.put("activity", res);
        return StdResult.genResult(true, json);
    }

    @RequestMapping(value = "/activity/{activityId}/elder", method = RequestMethod.GET)
    public JSONObject getRegisteredElders(@PathVariable Long activityId) {
        List<Long> elderIds = activityService.getActivityRegisteredElderIds(activityId);
        List<ElderDto> elderDtos = new ArrayList<>();
        for (Long elderId : elderIds) {
            ElderDto elderDto = elderService.getElder(elderId);
            elderDtos.add(elderDto);
        }
        JSONObject json = new JSONObject();
        json.put("elderList", elderDtos);
        return StdResult.genResult(true, json);
    }

    @RequestMapping(value = "/activity/{activityId}/elder/count", method = RequestMethod.GET)
    public JSONObject getRegisteredEldersCount(@PathVariable Long activityId) {
        List<Long> elderIds = activityService.getActivityRegisteredElderIds(activityId);
        JSONObject json = new JSONObject();
        json.put("count", elderIds.size());
        return StdResult.genResult(true, json);
    }

    @RequestMapping(value = "/activity", method = RequestMethod.GET)
    public JSONObject getActivities(@RequestParam List<Integer> statusList) {
        List<ActivityDto> activityDtos = activityService.getActivitiesByStatus(statusList);
        JSONObject json = new JSONObject();
        json.put("activityList", activityDtos);
        return StdResult.genResult(true, json);
    }
}
