package com.m.edmsbackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.m.edmsbackend.dao.IActivityRegisterRepository;
import com.m.edmsbackend.dao.IActivityRepository;
import com.m.edmsbackend.dao.IElderRepository;
import com.m.edmsbackend.dto.ActivityDto;
import com.m.edmsbackend.dto.ElderDto;
import com.m.edmsbackend.dao.IFloorRepository;
import com.m.edmsbackend.dto.FloorDto;
import com.m.edmsbackend.dao.IRoomRepository;
import com.m.edmsbackend.dto.RoomDto;
import com.m.edmsbackend.dao.IBedRepository;
import com.m.edmsbackend.dto.BedDto;
import com.m.edmsbackend.exception.LoginException;
import com.m.edmsbackend.exception.ServerErrorException;
import com.m.edmsbackend.model.Activity;
import com.m.edmsbackend.model.Elder;
import com.m.edmsbackend.model.ActivityRegister;
import com.m.edmsbackend.model.Floor;
import com.m.edmsbackend.model.Room;
import com.m.edmsbackend.model.Bed;
import com.m.edmsbackend.utils.DataUtils;
import com.m.edmsbackend.utils.JwtUtils;
import com.m.edmsbackend.utils.RedisUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class ActivityService {
    @Resource
    private IActivityRepository activityRepository;
    @Resource
    private IActivityRegisterRepository activityRegisterRepository;

    public ActivityDto addActivity(ActivityDto activityDto) {
        Activity dataIn = new Activity();
        DataUtils.copyProperties(activityDto, dataIn);
        Activity dataRs = activityRepository.save(dataIn);
        ActivityDto result = new ActivityDto();
        DataUtils.copyProperties(dataRs, result);
        return result;
    }

    public ActivityDto updateActivity(Long activityId, ActivityDto activityDto) {
        Activity activity = activityRepository.findActivityById(activityId);
        DataUtils.copyProperties(activityDto, activity);
        activity.setId(activityId);
        activityRepository.save(activity);
        return activityDto;
    }

    public void registerActivity(Long activityId, Long elderId) {
        ActivityRegister dataIn = new ActivityRegister();
        dataIn.setActivityId(activityId);
        dataIn.setParticipantId(elderId);
        ActivityRegister dataRs = activityRegisterRepository.save(dataIn);
    }

    public void unregisterActivity(Long activityId, Long elderId) {
        activityRegisterRepository.deleteRegisterByActivityIdAndElderId(activityId, elderId);
    }

    public List<Long> getActivityRegisteredElderIds(Long activityId) {
        List<Long> result =activityRegisterRepository.findRegisterElderByActivityId(activityId);
        return result;
    }

    public List<ActivityDto> getActivitiesByStatus(List<Integer> statusList) {
        List<ActivityDto> activityDtos = new ArrayList<>();
        List<Activity> activities = new ArrayList<>();
        if (statusList.size() != 0) {
            activities = activityRepository.findActivitiesByStatus(statusList);
        } else {
            activities = activityRepository.findAll();
        }

        for (Activity activity : activities) {
            ActivityDto activityDto = new ActivityDto();
            DataUtils.copyProperties(activity, activityDto);
            activityDtos.add(activityDto);
        }
        return activityDtos;
    }
}
