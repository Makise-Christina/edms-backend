package com.m.edmsbackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.m.edmsbackend.dao.ISalesRecordRepository;
import com.m.edmsbackend.dto.SalesRecordDto;
import com.m.edmsbackend.dao.IFloorRepository;
import com.m.edmsbackend.dto.FloorDto;
import com.m.edmsbackend.dao.IRoomRepository;
import com.m.edmsbackend.dto.RoomDto;
import com.m.edmsbackend.dao.IBedRepository;
import com.m.edmsbackend.dto.BedDto;
import com.m.edmsbackend.exception.LoginException;
import com.m.edmsbackend.exception.ServerErrorException;
import com.m.edmsbackend.model.SalesRecord;
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
public class SalesRecordService {
    @Resource
    private ISalesRecordRepository salesRecordRepository;

    public SalesRecordDto addSalesRecord(SalesRecordDto salesRecordDto){
        SalesRecord dataIn = new SalesRecord();
        DataUtils.copyProperties(salesRecordDto, dataIn);
        SalesRecord dataRs = salesRecordRepository.save(dataIn);
        SalesRecordDto result = new SalesRecordDto();
        DataUtils.copyProperties(dataRs, result);
        return result;
    }

    public SalesRecordDto updateSalesRecord(Long salesRecordId, SalesRecordDto salesRecordDto) {
        SalesRecord salesRecord = salesRecordRepository.findSalesRecordById(salesRecordId);
        DataUtils.copyProperties(salesRecordDto, salesRecord);
        salesRecord.setId(salesRecordId);
        salesRecordRepository.save(salesRecord);
        return salesRecordDto;
    }

    public List<SalesRecordDto> getSalesRecords(String elderName, Long salesPersonId) {
        List<SalesRecord> salesRecords = new ArrayList<>();
        List<SalesRecordDto> salesRecordDtos = new ArrayList<>();
        if (elderName != null) {
            if (salesPersonId != null) {
                salesRecords = salesRecordRepository.findSalesRecordsByElderNameAndSalesPersonId(elderName, salesPersonId);
            } else {
                salesRecords = salesRecordRepository.findSalesRecordsByElderName(elderName);
            }
        } else {
            if (salesPersonId != null) {
                salesRecords = salesRecordRepository.findSalesRecordsBySalesPersonId(salesPersonId);
            } else {
                salesRecords = salesRecordRepository.findAll();
            }
        }

        for (SalesRecord salesRecord : salesRecords) {
            SalesRecordDto salesRecordDto = new SalesRecordDto();
            DataUtils.copyProperties(salesRecord, salesRecordDto);
            salesRecordDtos.add(salesRecordDto);
        }
        return salesRecordDtos;
    }

    public void removeSalesRecord(Long salesRecordId) {
        salesRecordRepository.deleteById(salesRecordId);
    }
}

