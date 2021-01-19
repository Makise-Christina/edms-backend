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

@RestController
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public StdResult<List<UserDto>> getUsers(
            @RequestParam(required = false) Integer type) {
        List<UserDto> result = userService.findUsers(type);
        return new StdResult<List<UserDto>>(StdStatus.STATUS_200, result);
    }

    @Transactional
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public StdResult<Long> addUser(@RequestBody UserDto user) {
        Long id = userService.addUser(user);
        return new StdResult<Long>(StdStatus.STATUS_200, id);
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public StdResult<UserDto> getUser(@PathVariable Long userId) {
        UserDto result = userService.getUser(userId);
        return new StdResult<UserDto>(StdStatus.STATUS_200, result);
    }

    @Transactional
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
    public StdResult<Boolean> changeUser(@PathVariable Long userId, @RequestBody UserDto user) {
        Boolean result = userService.changeUser(userId, user);
        return new StdResult<Boolean>(StdStatus.STATUS_200, result);
    }

    @Transactional
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    public StdResult<Boolean> deleteUsers(@PathVariable Long userId) {
        Boolean result = userService.deleteUsers(userId);
        return new StdResult<Boolean>(StdStatus.STATUS_200, result);
    }

    /**
     * 用户登录
     * 
     * @param user
     * @return
     */
    @Transactional
    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public StdResult<UserDto> userLogin(@RequestBody UserDto user) {
        // 查找用户
        if (user.getUsername() == null || user.getPassword() == null) {
            throw new LoginException();
        }
        UserDto res = userService.getUserByLogin(user.getUsername(), user.getPassword());
        return new StdResult<UserDto>(StdStatus.STATUS_200, res);
    }

    /**
     * 验证是否登录
     */
    @RequestMapping(value = "/users/check-login", method = RequestMethod.GET)
    public StdResult<UserDto> getUsersCheckLogin(HttpServletRequest request) {
        Long userId = userService.uuid2id((String) request.getAttribute("uuid"));
        if (userId == null) {
            throw new AuthorizationException();
        }
        UserDto result = userService.getUser(userId);
        if (result.getIsBlock()) {
            throw new AuthorizationException();
        }
        userService.updateUserLoginTime(userId);
        return new StdResult<UserDto>(StdStatus.STATUS_200, result);
    }

    /**
     * 根据原密码修改密码
     */
    @Transactional
    @RequestMapping(value = "/users/password", method = RequestMethod.POST)
    public StdResult<Boolean> modifyUserPassword(@RequestParam Long userId, @RequestParam String password,
            @RequestParam String new_password) {
        Boolean res = userService.updateUserPassword(userId, password, new_password);
        return new StdResult<Boolean>(StdStatus.STATUS_200, res);
    }

}
