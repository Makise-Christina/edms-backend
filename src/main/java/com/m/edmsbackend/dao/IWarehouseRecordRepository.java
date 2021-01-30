package com.m.edmsbackend.dao;

import java.util.List;

import com.m.edmsbackend.model.WarehouseRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IWarehouseRecordRepository extends JpaRepository<WarehouseRecord, Long> {

    @Query(value = "select * from warehouse_record where item_id = ?1 order by gmt_create desc", nativeQuery = true)
    List<WarehouseRecord> findRecordByItemId(Long itemId);
}
