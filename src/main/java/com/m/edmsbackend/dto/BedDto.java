package com.m.edmsbackend.dto;

import java.util.Date;
import java.util.List;

import com.m.edmsbackend.model.Bed;

import lombok.Data;

@Data
public class BedDto extends Bed {
    private Long elderId;
    private Long workerId;
}
