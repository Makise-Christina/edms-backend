package com.m.edmsbackend.dao;

import java.util.List;

import com.m.edmsbackend.model.AccessRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IAccessRecordRepository extends JpaRepository<AccessRecord, Long> {
    @Query(value = "select * from access_record where status in ?1", nativeQuery = true)
    List<AccessRecord> findAccessRecordsByStatus(List<Integer> statusList);

    @Query(value = "select * from access_record where id = ?1", nativeQuery = true)
    AccessRecord findAccessRecordById(Long accessRecordId);

}
