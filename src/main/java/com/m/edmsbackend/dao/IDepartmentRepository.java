package com.m.edmsbackend.dao;

import java.util.List;

import com.m.edmsbackend.model.Department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IDepartmentRepository extends JpaRepository<Department, Long> {

    @Query(value = "select * from department where id = ?1", nativeQuery = true)
    Department findDepartmentById(Long id);
}
