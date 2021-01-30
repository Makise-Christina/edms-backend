package com.m.edmsbackend.dao;

import java.util.List;

import com.m.edmsbackend.model.Item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IItemRepository extends JpaRepository<Item, Long> {
    @Query(value = "select * from item where name like %?1%", nativeQuery = true)
    List<Item> findItemsByName(String name);

    @Query(value = "select * from item where id = ?1", nativeQuery = true)
    Item findItemById(Long itemId);
}
