package com.m.edmsbackend.dao;

import java.util.List;

import com.m.edmsbackend.model.Activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IActivityRepository extends JpaRepository<Activity, Long> {

    @Query(value = "select * from activity where id = ?1", nativeQuery = true)
    Activity findActivityById(Long activityId);

    @Query(value = "select * from activity where status in ?1", nativeQuery = true)
    List<Activity> findActivitiesByStatus(List<Integer> statusList);
}
