package com.m.edmsbackend.dao;

import com.m.edmsbackend.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);

    // List<User> findUserByEnterpriseId(Long enterpriseId);

    @Query(value = "select id from user where uuid = ?1", nativeQuery = true)
    Long findIdByUUID(String uuid);

    @Query(value = "select * from user where uuid = ?1", nativeQuery = true)
    User findUserByUUID(String uuid);

    @Query(value = "select email from user where email = ?1", nativeQuery = true)
    String findEmail(String email);

    @Query(value = "select mobile from user where mobile = ?1", nativeQuery = true)
    String findMobile(String mobile);

    @Query(value = "select * from user where user_name = ?1", nativeQuery = true)
    User findByUserName(String userName);
}
