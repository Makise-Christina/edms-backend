package com.m.edmsbackend.dao;

import java.util.List;

import com.m.edmsbackend.model.Elder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IElderRepository extends JpaRepository<Elder, Long> {
    Elder findElderById(Long id);

    @Query(value = "select * from elder", nativeQuery = true)
    List<Elder> findElders();

    @Query(value = "select * from elder where name like %?1%", nativeQuery = true)
    List<Elder> findEldersByName(String name);

    @Query(value = "select * from elder where name like %?1% and age >= ?2", nativeQuery = true)
    List<Elder> findEldersByNameAndStartAge(String name, Integer startAge);
    @Query(value = "select * from elder where name like %?1% and age <= ?2", nativeQuery = true)
    List<Elder> findEldersByNameAndEndAge(String name, Integer endAge);

    @Query(value = "select * from elder where name like %?1% and age >= ?2 and age <= ?3", nativeQuery = true)
    List<Elder> findEldersByNameAndAge(String name, Integer startAge, Integer endAge);

    @Query(value = "select * from elder where age >= ?1", nativeQuery = true)
    List<Elder> findEldersByStartAge(Integer startAge);
    @Query(value = "select * from elder where age <= ?1", nativeQuery = true)
    List<Elder> findEldersByEndAge(Integer endAge);

    @Query(value = "select * from elder where age >= ?1 and age <= ?2", nativeQuery = true)
    List<Elder> findEldersByAge(Integer startAge, Integer endAge);

    @Query(value = "select * from elder where building_id = ?1", nativeQuery = true)
    List<Elder> findEldersByBuildingId(Long buildingId);
}
