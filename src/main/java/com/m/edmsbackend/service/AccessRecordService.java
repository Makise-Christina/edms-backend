package com.m.edmsbackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.m.edmsbackend.dao.IAccessRecordRepository;
import com.m.edmsbackend.dto.AccessRecordDto;
import com.m.edmsbackend.dao.IFloorRepository;
import com.m.edmsbackend.dto.FloorDto;
import com.m.edmsbackend.dao.IRoomRepository;
import com.m.edmsbackend.dto.RoomDto;
import com.m.edmsbackend.dao.IBedRepository;
import com.m.edmsbackend.dto.BedDto;
import com.m.edmsbackend.exception.LoginException;
import com.m.edmsbackend.exception.ServerErrorException;
import com.m.edmsbackend.model.AccessRecord;
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
public class AccessRecordService {
    @Resource
    private IAccessRecordRepository accessRecordRepository;

    public AccessRecordDto addAccessRecord(AccessRecordDto accessRecordDto){
        AccessRecord dataIn = new AccessRecord();
        DataUtils.copyProperties(accessRecordDto, dataIn);
        AccessRecord dataRs = accessRecordRepository.save(dataIn);
        AccessRecordDto result = new AccessRecordDto();
        DataUtils.copyProperties(dataRs, result);
        return result;
    }

    public AccessRecordDto updateAccessRecord(Long accessRecordId, AccessRecordDto accessRecordDto) {
        AccessRecord accessRecord = accessRecordRepository.findAccessRecordById(accessRecordId);
        DataUtils.copyProperties(accessRecordDto, accessRecord);
        accessRecord.setId(accessRecordId);
        accessRecordRepository.save(accessRecord);
        return accessRecordDto;
    }

    public AccessRecordDto getAccessRecord(Long accessRecordId) {
        AccessRecord accessRecord = accessRecordRepository.findAccessRecordById(accessRecordId);
        AccessRecordDto accessRecordDto = new AccessRecordDto();
        DataUtils.copyProperties(accessRecord, accessRecordDto);
        return accessRecordDto;
    }

    public List<AccessRecordDto> getAccessRecordsByStatus(List<Integer> statusList)
    {
        List<AccessRecordDto> accessRecordDtos = new ArrayList<>();
        List<AccessRecord> accessRecords = new ArrayList<>();
        if (statusList.size() != 0) {
            accessRecords = accessRecordRepository.findAccessRecordsByStatus(statusList);
        } else {
            accessRecords = accessRecordRepository.findAll();
        }
        for (AccessRecord accessRecord : accessRecords) {
            AccessRecordDto accessRecordDto = new AccessRecordDto();
            DataUtils.copyProperties(accessRecord, accessRecordDto);
            accessRecordDtos.add(accessRecordDto);
        }
        return accessRecordDtos;
    }
}

