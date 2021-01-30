package com.m.edmsbackend.dto;

import java.util.Date;
import java.util.List;

import com.m.edmsbackend.model.Building;

import lombok.Data;

@Data
public class BuildingDto extends Building {
    private List<FloorDto> floorList;   
}
