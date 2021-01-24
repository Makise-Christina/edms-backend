package com.m.edmsbackend.dao;

import java.util.List;

import com.m.edmsbackend.model.Building;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IBuildingRepository extends JpaRepository<Building, Long> {
    @Query(value = "select * from building where id = ?1", nativeQuery = true)
    Building findBuildingById(Long building);
}
