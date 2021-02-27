package com.m.edmsbackend.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.m.edmsbackend.dto.AnnouncementDto;
import com.m.edmsbackend.enums.StdStatus;
import com.m.edmsbackend.exception.AuthorizationException;
import com.m.edmsbackend.exception.LoginException;
import com.m.edmsbackend.service.AnnouncementService;
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
public class AnnouncementController {
    @Resource
    private AnnouncementService announcementService;

    @Transactional
    @RequestMapping(value = "/announcement", method = RequestMethod.POST)
    public JSONObject addAnnouncement(@RequestBody JSONObject dataIn) {
        JSONObject announcementJson = dataIn.getJSONObject("announcement");
        AnnouncementDto announcement = JSON.toJavaObject(announcementJson, AnnouncementDto.class);
        AnnouncementDto res = announcementService.addAnnouncement(announcement);
        JSONObject json = new JSONObject();
        json.put("announcement", res);
        return StdResult.genResult(true, json);
    }

    @RequestMapping(value = "/announcement/{announcementId}", method = RequestMethod.GET)
    public JSONObject getAnnouncements(@PathVariable Long announcementId) {
        AnnouncementDto result = announcementService.findAnnouncement(announcementId);
        JSONObject json = new JSONObject();
        json.put("announcement", result);
        return StdResult.genResult(true, json);
    }

    @RequestMapping(value = "/announcement", method = RequestMethod.GET)
    public JSONObject getAnnouncements(@RequestParam List<Integer> typeList) {
        List<AnnouncementDto> result = announcementService.findAnnouncements(typeList);
        JSONObject json = new JSONObject();
        json.put("announcementList", result);
        return StdResult.genResult(true, json);
    }
}
