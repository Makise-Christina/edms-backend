package com.m.edmsbackend.dao;

import java.util.List;

import com.m.edmsbackend.model.Bed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IBedRepository extends JpaRepository<Bed, Long> {
    @Query(value = "select * from bed where room_id = ?1 and id not in (select bed_id from elder group by bed_id)", nativeQuery = true)
    List<Bed> findBedNotUsedByRoomId(Long roomId);

    @Query(value = "select * from bed where id not in (select bed_id from elder group by bed_id)", nativeQuery = true)
    List<Bed> findBedNotUsed();

    @Query(value = "select * from bed where id = ?1", nativeQuery = true)
    Bed findBedById(Long bedId);
}
