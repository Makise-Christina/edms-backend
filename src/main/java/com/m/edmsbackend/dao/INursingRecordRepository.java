package com.m.edmsbackend.dao;

import java.util.List;

import com.m.edmsbackend.model.NursingRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface INursingRecordRepository extends JpaRepository<NursingRecord, Long> {
    @Query(value = "select * from nursing_record where elder_id = ?1", nativeQuery = true)
    List<NursingRecord> findNursingRecordsByElderId(Long elderId);

    @Query(value = "select * from nursing_record where id = ?1", nativeQuery = true)
    NursingRecord findNursingRecordById(Long nursingRecordId);
}
