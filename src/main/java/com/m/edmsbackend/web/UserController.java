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

@RestController
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public JSONObject getUsers(
            @RequestParam(required = false) Integer type) {
        List<UserDto> result = userService.findUsers(type);
        JSONObject json = new JSONObject();
        json.put("users", result);
        return StdResult.genResult(true, json);
    }

    @Transactional
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public JSONObject addUser(@RequestBody UserDto user) {
        Long id = userService.addUser(user);
        JSONObject json = new JSONObject();
        json.put("user_id", id);
        return StdResult.genResult(true, json);
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public JSONObject getUser(@PathVariable Long userId) {
        UserDto result = userService.getUser(userId);
        JSONObject json = new JSONObject();
        json.put("user", result);
        return StdResult.genResult(true, json);
    }

    @Transactional
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
    public JSONObject changeUser(@PathVariable Long userId, @RequestBody UserDto user) {
        Boolean result = userService.changeUser(userId, user);
        JSONObject json = new JSONObject();
        return StdResult.genResult(result, json);
    }

    @Transactional
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    public JSONObject deleteUsers(@PathVariable Long userId) {
        Boolean result = userService.deleteUsers(userId);
        JSONObject json = new JSONObject();
        return StdResult.genResult(result, json);
    }

    /**
     * 用户登录
     * 
     * @param user
     * @return
     */
    @Transactional
    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public JSONObject userLogin(@RequestBody UserDto user) {
        // 查找用户
        if (user.getUsername() == null || user.getPassword() == null) {
            return StdResult.genResult(false, new JSONObject());
        }
        UserDto res = userService.getUserByLogin(user.getUsername(), user.getPassword());
        JSONObject json = new JSONObject();
        json.put("user", res);
        return StdResult.genResult(true, json);
    }

    /**
     * 验证是否登录
     */
    @RequestMapping(value = "/users/check-login", method = RequestMethod.GET)
    public JSONObject getUsersCheckLogin(HttpServletRequest request) {
        Long userId = userService.uuid2id((String) request.getAttribute("uuid"));
        if (userId == null) {
            return StdResult.genResult(false, new JSONObject());
        }
        UserDto result = userService.getUser(userId);
        if (result.getIsBlock()) {
            return StdResult.genResult(false, new JSONObject());
        }
        userService.updateUserLoginTime(userId);
        JSONObject json = new JSONObject();
        json.put("user", result);
        return StdResult.genResult(true, json);
    }

    /**
     * 根据原密码修改密码
     */
    @Transactional
    @RequestMapping(value = "/users/password", method = RequestMethod.POST)
    public JSONObject modifyUserPassword(@RequestParam Long userId, @RequestParam String password,
            @RequestParam String new_password) {
        Boolean res = userService.updateUserPassword(userId, password, new_password);
        return StdResult.genResult(res, new JSONObject());
    }

}
