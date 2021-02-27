package com.m.edmsbackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.m.edmsbackend.dao.ITaskRepository;
import com.m.edmsbackend.dto.TaskDto;
import com.m.edmsbackend.exception.LoginException;
import com.m.edmsbackend.exception.ServerErrorException;
import com.m.edmsbackend.model.Task;
import com.m.edmsbackend.utils.DataUtils;
import com.m.edmsbackend.utils.JwtUtils;
import com.m.edmsbackend.utils.RedisUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class TaskService {
    @Resource
    private ITaskRepository taskRepository;

    public TaskDto addTask(TaskDto newTask) {
        Task task = new Task();
        DataUtils.copyProperties(newTask, task);
        Task taskRs = taskRepository.save(task);
        Long taskId = taskRs.getId();
        newTask.setId(taskId);
        return newTask;
    }

    public TaskDto updateTask(Long taskId, TaskDto taskDto) {
        Task task = taskRepository.findTaskById(taskId);
        DataUtils.copyProperties(taskDto, task);
        task.setId(taskId);
        taskRepository.save(task);
        return taskDto;
    }

    public List<TaskDto> findTasks(List<Long> workerIdList) {
        List<TaskDto> taskDtos = new ArrayList<>();
        List<Task> tasks = taskRepository.findTasksByWorkerIdList(workerIdList);
        for (Task task : tasks) {
            TaskDto taskDto = new TaskDto();
            DataUtils.copyProperties(task, taskDto);
            taskDtos.add(taskDto);
        }
        return taskDtos;
    }
    
}
