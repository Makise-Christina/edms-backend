package com.m.edmsbackend.dto;

import java.util.Date;
import java.util.List;

import com.m.edmsbackend.model.Floor;

import lombok.Data;

@Data
public class FloorDto extends Floor {
    List<RoomDto> roomList;
}