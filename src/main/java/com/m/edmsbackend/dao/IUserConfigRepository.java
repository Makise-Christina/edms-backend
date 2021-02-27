package com.m.edmsbackend.dao;

import com.m.edmsbackend.model.UserConfig;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUserConfigRepository extends JpaRepository<UserConfig, Long> {
    @Query(value = "select * from user_config where user_id = ?1", nativeQuery = true)
    UserConfig findUserConfigByUserId(Long userId);
}
