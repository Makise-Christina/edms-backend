package com.m.edmsbackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.m.edmsbackend.dao.INursingRecordRepository;
import com.m.edmsbackend.dto.NursingRecordDto;
import com.m.edmsbackend.dao.IFloorRepository;
import com.m.edmsbackend.dto.FloorDto;
import com.m.edmsbackend.dao.IRoomRepository;
import com.m.edmsbackend.dto.RoomDto;
import com.m.edmsbackend.dao.IBedRepository;
import com.m.edmsbackend.dto.BedDto;
import com.m.edmsbackend.exception.LoginException;
import com.m.edmsbackend.exception.ServerErrorException;
import com.m.edmsbackend.model.NursingRecord;
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
public class NursingRecordService {
    @Resource
    private INursingRecordRepository nursingRecordRepository;

    public NursingRecordDto addNursingRecord(NursingRecordDto nursingRecordDto){
        NursingRecord dataIn = new NursingRecord();
        DataUtils.copyProperties(nursingRecordDto, dataIn);
        NursingRecord dataRs = nursingRecordRepository.save(dataIn);
        NursingRecordDto result = new NursingRecordDto();
        DataUtils.copyProperties(dataRs, result);
        return result;
    }

    public NursingRecordDto getNursingRecord(Long nursingRecordId) {
        NursingRecord nursingRecord = nursingRecordRepository.findNursingRecordById(nursingRecordId);
        NursingRecordDto nursingRecordDto = new NursingRecordDto();
        DataUtils.copyProperties(nursingRecord, nursingRecordDto);
        return nursingRecordDto;
    }

    public List<NursingRecordDto> getNursingRecordsByElderId(Long elderId)
    {
        List<NursingRecordDto> nursingRecordDtos = new ArrayList<>();
        List<NursingRecord> nursingRecords = new ArrayList<>();
        if (elderId != null) {
            nursingRecords = nursingRecordRepository.findNursingRecordsByElderId(elderId);
        } else {
            nursingRecords = nursingRecordRepository.findAll();
        }
        for (NursingRecord nursingRecord : nursingRecords) {
            NursingRecordDto nursingRecordDto = new NursingRecordDto();
            DataUtils.copyProperties(nursingRecord, nursingRecordDto);
            nursingRecordDtos.add(nursingRecordDto);
        }
        return nursingRecordDtos;
    }
}

