package com.m.edmsbackend.dao;

import java.util.List;

import com.m.edmsbackend.model.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IRoomRepository extends JpaRepository<Room, Long> {
    @Query(value = "select * from room where floor_id = ?1", nativeQuery = true)
    List<Room> findRoomsByFloorId(Long floorId);

    @Query(value = "select * from room where id = ?1", nativeQuery = true)
    Room findRoomById(Long roomId);
}
