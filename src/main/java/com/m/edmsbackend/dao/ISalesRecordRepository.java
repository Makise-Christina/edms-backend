package com.m.edmsbackend.dao;

import java.util.List;

import com.m.edmsbackend.model.SalesRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ISalesRecordRepository extends JpaRepository<SalesRecord, Long> {
    @Query(value = "select * from sales_record where elder_name like %?1%", nativeQuery = true)
    List<SalesRecord> findSalesRecordsByElderName(String elderName);

    @Query(value = "select * from sales_record where sales_person_id = ?1", nativeQuery = true)
    List<SalesRecord> findSalesRecordsBySalesPersonId(Long salesPersonId);

    @Query(value = "select * from sales_record where elder_name like %?1% and sales_person_id = ?2", nativeQuery = true)
    List<SalesRecord> findSalesRecordsByElderNameAndSalesPersonId(String elderName, Long salesPersonId);

    @Query(value = "select * from sales_record where id = ?1", nativeQuery = true)
    SalesRecord findSalesRecordById(Long salesRecordId);

}
