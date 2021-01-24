package com.m.edmsbackend.dao;

import java.util.List;

import com.m.edmsbackend.model.Floor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IFloorRepository extends JpaRepository<Floor, Long> {
    @Query(value = "select * from floor where building_id = ?1", nativeQuery = true)
    List<Floor> findFloorByBuildingId(Long buildingId);

    @Query(value = "select * from floor where id = ?1", nativeQuery = true)
    Floor findFloorById(Long floorId);
}
