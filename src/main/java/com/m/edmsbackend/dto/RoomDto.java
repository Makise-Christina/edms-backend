package com.m.edmsbackend.dto;

import java.util.Date;
import java.util.List;

import com.m.edmsbackend.model.Room;

import lombok.Data;

@Data
public class RoomDto extends Room {
    List<BedDto> bedList;
}
