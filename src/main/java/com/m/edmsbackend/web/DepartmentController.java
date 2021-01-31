package com.m.edmsbackend.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.m.edmsbackend.dto.UserDto;
import com.m.edmsbackend.enums.StdStatus;
import com.m.edmsbackend.exception.AuthorizationException;
import com.m.edmsbackend.exception.LoginException;
import com.m.edmsbackend.service.UserService;
import com.m.edmsbackend.utils.StdResult;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;

@RestController
public class DepartmentController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/department/{departmentId}/workers", method = RequestMethod.GET)
    public JSONObject getDepartmentWorkers(@PathVariable Long departmentId) {
        List<UserDto> result = userService.findUsersByDepartmentId(departmentId);
        JSONObject json = new JSONObject();
        json.put("userList", result);
        return StdResult.genResult(true, json);
    }
}
