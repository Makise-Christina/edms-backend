package com.m.edmsbackend.dao;

import java.util.List;

import com.m.edmsbackend.model.Announcement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IAnnouncementRepository extends JpaRepository<Announcement, Long> {
    Announcement findAnnouncementById(Long id);

    @Query(value = "select * from announcement where type in ?1", nativeQuery = true)
    List<Announcement> findAnnouncementsByTypeList(List<Integer> typeList);
}
