package com.m.edmsbackend.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.m.edmsbackend.dto.TaskDto;
import com.m.edmsbackend.enums.StdStatus;
import com.m.edmsbackend.exception.AuthorizationException;
import com.m.edmsbackend.exception.LoginException;
import com.m.edmsbackend.service.TaskService;
import com.m.edmsbackend.utils.StdResult;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;

@RestController
public class TaskController {
    @Resource
    private TaskService taskService;

    @Transactional
    @RequestMapping(value = "/task", method = RequestMethod.POST)
    public JSONObject addTask(@RequestBody JSONObject dataIn) {
        JSONObject taskJson = dataIn.getJSONObject("task");
        TaskDto task = JSON.toJavaObject(taskJson, TaskDto.class);
        TaskDto res = taskService.addTask(task);
        JSONObject json = new JSONObject();
        json.put("task", res);
        return StdResult.genResult(true, json);
    }

    @RequestMapping(value = "/task", method = RequestMethod.GET)
    public JSONObject getTasks(@RequestParam List<Long> workerIds) {

        List<TaskDto> result = taskService.findTasks(workerIds);
        JSONObject json = new JSONObject();
        json.put("taskList", result);
        return StdResult.genResult(true, json);
    }

    @Transactional
    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.PUT)
    public JSONObject modifyTask(@PathVariable Long taskId, @RequestBody JSONObject dataIn) {
        JSONObject taskJson = dataIn.getJSONObject("task");
        TaskDto task = JSON.toJavaObject(taskJson, TaskDto.class);
        TaskDto result = taskService.updateTask(taskId, task);
        JSONObject json = new JSONObject();
        json.put("task", result);
        return StdResult.genResult(true, json);
    }
}
