package com.m.edmsbackend.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.m.edmsbackend.dto.BuildingDto;
import com.m.edmsbackend.dto.ElderDto;
import com.m.edmsbackend.dto.UserDto;
import com.m.edmsbackend.enums.StdStatus;
import com.m.edmsbackend.exception.AuthorizationException;
import com.m.edmsbackend.exception.LoginException;
import com.m.edmsbackend.service.BuildingService;
import com.m.edmsbackend.service.ElderService;
import com.m.edmsbackend.service.UserService;
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

@RestController
public class BuildingController {
    @Resource
    private BuildingService buildingService;
    @Resource
    private ElderService elderService;
    @Resource
    private UserService userService;

    @RequestMapping(value = "/building/available", method = RequestMethod.GET)
    public JSONObject getBuildingAvailableInfos() {
        List<BuildingDto> result = buildingService.findNotUsedBeds();
        JSONObject json = new JSONObject();
        json.put("buildingList", result);
        return StdResult.genResult(true, json);
    }

    @RequestMapping(value = "/building/names", method = RequestMethod.GET)
    public JSONObject getBuildingNames(
        @RequestParam(required = false) Long buildingId, 
        @RequestParam(required = false) Long floorId,
        @RequestParam(required = false) Long roomId,
        @RequestParam(required = false) Long bedId
    ) {
        String buildingName = null;
        String floorName = null;
        String roomName = null;
        String bedName = null;
        if (buildingId != null) {
            buildingName = buildingService.findBuildingName(buildingId);
        }
        if (floorId != null) {
            floorName = buildingService.findFloorName(floorId);
        }
        if (roomId != null) {
            roomName = buildingService.findRoomName(roomId);
        }
        if (bedId != null) {
            bedName = buildingService.findBedName(bedId);
        }
        JSONObject json = new JSONObject();
        json.put("buildingName", buildingName);
        json.put("floorName", floorName);
        json.put("roomName", roomName);
        json.put("bedName", bedName);
        return StdResult.genResult(true, json);
    }

    @RequestMapping(value = "/building/{buildingId}/elder", method = RequestMethod.GET)
    public JSONObject getBuildingElders(@PathVariable Long buildingId) {
        List<ElderDto> result = elderService.getEldersByBuildingId(buildingId);
        JSONObject json = new JSONObject();
        json.put("elderList", result);
        return StdResult.genResult(true, json);
    }

    @RequestMapping(value = "/building/statistics", method = RequestMethod.GET)
    public JSONObject getBuildingStatistics() {
        JSONArray buildingStatistics = new JSONArray();
        List<BuildingDto> result = buildingService.findBuildings();
        for (BuildingDto buildingDto : result)
        {
            JSONObject bs = new JSONObject();
            Long workerId = buildingDto.getWorkerId();
            Long buildingId = buildingDto.getId();
            UserDto userDto = userService.getUser(workerId);
            Integer totalBeds = buildingService.countTotalBeds(buildingId);
            Integer usedBeds = buildingService.countUsedBeds(buildingId);
            bs.put("buildingId", buildingDto.getId());
            bs.put("buildingName", buildingDto.getName());
            bs.put("totalBeds", totalBeds);
            bs.put("usedBeds", usedBeds);
            bs.put("workerId", workerId);
            bs.put("workerName", userDto.getName());
            buildingStatistics.add(bs);
        }
        JSONObject json = new JSONObject();
        json.put("buildingStatList", buildingStatistics);
        return StdResult.genResult(true, json);
    }
}
