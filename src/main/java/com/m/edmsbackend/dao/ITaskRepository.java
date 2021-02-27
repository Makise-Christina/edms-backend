package com.m.edmsbackend.dao;

import java.util.List;

import com.m.edmsbackend.model.Task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ITaskRepository extends JpaRepository<Task, Long> {
    Task findTaskById(Long id);

    @Query(value = "select * from task where worker_id in ?1", nativeQuery = true)
    List<Task> findTasksByWorkerIdList(List<Long> workerIds);
}
