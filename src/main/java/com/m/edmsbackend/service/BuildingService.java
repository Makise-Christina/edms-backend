package com.m.edmsbackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.m.edmsbackend.dao.IBuildingRepository;
import com.m.edmsbackend.dto.BuildingDto;
import com.m.edmsbackend.dao.IFloorRepository;
import com.m.edmsbackend.dto.FloorDto;
import com.m.edmsbackend.dao.IRoomRepository;
import com.m.edmsbackend.dto.RoomDto;
import com.m.edmsbackend.dao.IBedRepository;
import com.m.edmsbackend.dto.BedDto;
import com.m.edmsbackend.exception.LoginException;
import com.m.edmsbackend.exception.ServerErrorException;
import com.m.edmsbackend.model.Building;
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
public class BuildingService {
    @Resource
    private IBuildingRepository buildingRepository;
    @Resource
    private IFloorRepository floorRepository;
    @Resource
    private IRoomRepository roomRepository;
    @Resource
    private IBedRepository bedRepository;

    public List<BuildingDto> findNotUsedBeds() {
        List<BuildingDto> buildingsResult = new ArrayList<>();
        // loop all buildings
        List<Building> buildings = buildingRepository.findAll();
        for (Building building : buildings) {
            Long buildingId = building.getId();
            List<FloorDto> floorDtos = new ArrayList<>();
            // loop all floor
            List<Floor> floors = floorRepository.findFloorByBuildingId(buildingId);
            for (Floor floor : floors) {
                Long floorId = floor.getId();
                List<RoomDto> roomDtos = new ArrayList<>();
                // loop all room
                List<Room> rooms = roomRepository.findRoomsByFloorId(floorId);
                for(Room room : rooms) {
                    Long roomId = room.getId();
                    List<BedDto> bedDtos = new ArrayList<>();
                    // get available beds
                    List<Bed> beds = bedRepository.findBedNotUsedByRoomId(roomId);
                    for (Bed bed : beds) {
                        BedDto bedDto = new BedDto();
                        DataUtils.copyProperties(bed, bedDto);
                        bedDtos.add(bedDto);
                    }
                    if (bedDtos.size() != 0) {
                        RoomDto roomDto = new RoomDto();
                        DataUtils.copyProperties(room, roomDto);
                        roomDto.setBedList(bedDtos);
                        roomDtos.add(roomDto);
                    }
                }
                if (roomDtos.size() != 0) {
                    FloorDto floorDto = new FloorDto();
                    DataUtils.copyProperties(floor, floorDto);
                    floorDto.setRoomList(roomDtos);
                    floorDtos.add(floorDto);
                }
            }
            if (floorDtos.size() != 0) {
                BuildingDto buildingDto = new BuildingDto();
                DataUtils.copyProperties(building, buildingDto);
                buildingDto.setFloorList(floorDtos);
                buildingsResult.add(buildingDto);
            }
        }
        return buildingsResult;
    }

    public String findBuildingName(Long buildingId) {
        Building building = buildingRepository.findBuildingById(buildingId);
        if (building != null){
            return building.getName();
        }
        return null;
        
    }

    public String findFloorName(Long floorId) {
        Floor floor = floorRepository.findFloorById(floorId);
        if (floor != null) {
            return floor.getName();
        }
        return null;
    }

    public String findRoomName(Long roomId) {
        Room room = roomRepository.findRoomById(roomId);
        if (room != null) {
            return room.getName();
        }
        return null;
    }

    public String findBedName(Long bedId) {
        Bed bed = bedRepository.findBedById(bedId);
        if (bed != null) {
            return bed.getName();
        }
        return null;
    }
}

