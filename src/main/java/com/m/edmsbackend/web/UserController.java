package com.m.edmsbackend.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.m.edmsbackend.dto.UserDto;
import com.m.edmsbackend.dto.UserConfigDto;
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
import com.alibaba.fastjson.JSON;

@RestController
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public JSONObject getUsers(
            @RequestParam(required = false) List<Long> idList) {
        List<UserDto> result = new ArrayList<>();
        for (Long userId: idList)
        {
            UserDto userDto = userService.getUser(userId);
            if (userDto.getId() != null)
            {
                result.add(userDto);
            }
        }
        JSONObject json = new JSONObject();
        json.put("userList", result);
        return StdResult.genResult(true, json);
    }

    @Transactional
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public JSONObject addUser(@RequestBody JSONObject dataIn) {
        JSONObject userJson = dataIn.getJSONObject("user");
        UserDto user = JSON.toJavaObject(userJson, UserDto.class);
        Long id = userService.addUser(user);
        user.setId(id);
        JSONObject json = new JSONObject();
        json.put("user", user);
        return StdResult.genResult(true, json);
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public JSONObject getUser(@PathVariable Long userId) {
        UserDto result = userService.getUser(userId);
        JSONObject json = new JSONObject();
        json.put("user", result);
        if (result.getId() != null){
            return StdResult.genResult(true, json);
        } 
        else {
            return StdResult.genResult(false, json);
        }
        
    }

    @Transactional
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.PUT)
    public JSONObject changeUser(@PathVariable Long userId, @RequestBody JSONObject dataIn) {
        JSONObject userJson = dataIn.getJSONObject("user");
        UserDto user = JSON.toJavaObject(userJson, UserDto.class);
        Boolean result = userService.changeUser(userId, user);
        JSONObject json = new JSONObject();
        json.put("user", user);
        return StdResult.genResult(result, json);
    }

    @Transactional
    @RequestMapping(value = "/user/{userId}/password", method = RequestMethod.PUT)
    public JSONObject changeUserPassword(@PathVariable Long userId, @RequestBody JSONObject dataIn) {
        String oldPassword = dataIn.getString("oldPassword");
        String newPassword = dataIn.getString("newPassword");
        Boolean result = userService.updateUserPassword(userId, oldPassword, newPassword);
        JSONObject json = new JSONObject();
        json.put("result", result);
        return StdResult.genResult(result, json);
    }

    

    // @Transactional
    // @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    // public JSONObject deleteUsers(@PathVariable Long userId) {
    //     Boolean result = userService.deleteUsers(userId);
    //     JSONObject json = new JSONObject();
    //     return StdResult.genResult(result, json);
    // }

    /**
     * 用户登录
     * 
     * @param user
     * @return
     */
    @Transactional
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public JSONObject userLogin(@RequestBody UserDto user) {
        // 查找用户
        if (user.getUsername() == null || user.getPassword() == null) {
            return StdResult.genResult(false, new JSONObject());
        }
        try{
            UserDto res = userService.getUserByLogin(user.getUsername(), user.getPassword());
            JSONObject json = new JSONObject();
            json.put("user", res);
            return StdResult.genResult(true, json);
        } catch (Exception e) {
            return StdResult.genResult(false, new JSONObject());
        }
    }

    @RequestMapping(value = "/user/{userId}/config", method = RequestMethod.GET)
    public JSONObject getUserConfig(@PathVariable Long userId) {
        UserConfigDto userConfigDto = userService.getUserConfig(userId);
        JSONObject json = new JSONObject();
        json.put("config", userConfigDto);
        return StdResult.genResult(true, json);
    }

    @Transactional
    @RequestMapping(value = "/user/{userId}/config", method = RequestMethod.PUT)
    public JSONObject changeUserConfig(@PathVariable Long userId, @RequestBody JSONObject dataIn) {
        JSONObject userConfigJson = dataIn.getJSONObject("config");
        UserConfigDto userConfig = JSON.toJavaObject(userConfigJson, UserConfigDto.class);
        Boolean result = userService.changeUserConfig(userId, userConfig);
        JSONObject json = new JSONObject();
        json.put("config", userConfig);
        return StdResult.genResult(result, json);
    }

    // /**
    //  * 验证是否登录
    //  */
    // @RequestMapping(value = "/users/check-login", method = RequestMethod.GET)
    // public JSONObject getUsersCheckLogin(HttpServletRequest request) {
    //     Long userId = userService.uuid2id((String) request.getAttribute("uuid"));
    //     if (userId == null) {
    //         return StdResult.genResult(false, new JSONObject());
    //     }
    //     UserDto result = userService.getUser(userId);
    //     if (result.getIsBlock()) {
    //         return StdResult.genResult(false, new JSONObject());
    //     }
    //     userService.updateUserLoginTime(userId);
    //     JSONObject json = new JSONObject();
    //     json.put("user", result);
    //     return StdResult.genResult(true, json);
    // }

    // /**
    //  * 根据原密码修改密码
    //  */
    // @Transactional
    // @RequestMapping(value = "/users/password", method = RequestMethod.POST)
    // public JSONObject modifyUserPassword(@RequestParam Long userId, @RequestParam String password,
    //         @RequestParam String new_password) {
    //     Boolean res = userService.updateUserPassword(userId, password, new_password);
    //     return StdResult.genResult(res, new JSONObject());
    // }

}
