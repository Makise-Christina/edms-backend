package com.m.edmsbackend.dao;

import java.util.List;

import com.m.edmsbackend.model.ActivityRegister;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IActivityRegisterRepository extends JpaRepository<ActivityRegister, Long> {

    @Modifying
    @Transactional
    @Query(value = "delete from activity_register where activity_id = ?1 and participant_id = ?2", nativeQuery = true)
    void deleteRegisterByActivityIdAndElderId(Long activityId, Long participantId);

    @Query(value = "select participant_id from activity_register where activity_id = ?1", nativeQuery = true)
    List<Long> findRegisterElderByActivityId(Long activityId);
}
