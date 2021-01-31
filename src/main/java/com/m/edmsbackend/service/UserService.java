package com.m.edmsbackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.m.edmsbackend.dao.IUserRepository;
import com.m.edmsbackend.dto.UserDto;
import com.m.edmsbackend.exception.LoginException;
import com.m.edmsbackend.exception.ServerErrorException;
import com.m.edmsbackend.model.User;
import com.m.edmsbackend.utils.DataUtils;
import com.m.edmsbackend.utils.JwtUtils;
import com.m.edmsbackend.utils.RedisUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserService {
    @Resource
    private IUserRepository userRepository;

    @Resource
    private RedisUtils redisUtils;
    
    private static final long VARIFICATION_NUMBER_TTL = 15 * 60;

    private static final String varificationPrefix = "m.user.varification.";

    public Long addUser(UserDto user) {
        User dataIn = new User();
        DataUtils.copyProperties(user, dataIn);
        // String dataRsem = userRepository.findEmail(user.getEmail());
        // if (dataRsem != null) {
        //     return -1L;
        // }
        // String dataMobile = userRepository.findMobile(user.getMobile());
        // if (dataMobile != null) {
        //     return -1L;
        // }
        String uuid = DataUtils.randUUID();
        dataIn.setUuid(uuid);
        String salt = DataUtils.randSALT();
        dataIn.setSalt(salt);
        dataIn.setPassword(DigestUtils.md5DigestAsHex((user.getPassword() + dataIn.getSalt()).getBytes()));
        dataIn.setUserName(user.getUsername());
        dataIn.setLocked(false);
        User dataRs = userRepository.save(dataIn);

        Long id = dataRs.getId();
        return id;
    }

    public UserDto getUser(Long userId) {
        User dataRs = userRepository.findUserById(userId);
        if (dataRs == null) {
            return new UserDto();
        }
        UserDto result = new UserDto();
        DataUtils.copyProperties(dataRs, result);
        result.setUsername(dataRs.getUserName());
        result.setIsBlock(dataRs.getLocked());
        result.setLastLoginTime(dataRs.getLoginTime());
        result.setPassword(null);
        result.setSalt(null);
        return result;
    }

    public List<UserDto> findUsers(Integer role) {
        List<UserDto> result = new ArrayList<>();
        List<User> dataRs = new ArrayList<>();
        User example = new User();
        example.setRole(role);
        dataRs = userRepository.findAll(Example.of(example));

        for (int i = 0; i < dataRs.size(); i++) {
            User user = dataRs.get(i);
            UserDto t = new UserDto();
            DataUtils.copyProperties(user, t);
            t.setUsername(user.getUserName());
            t.setIsBlock(user.getLocked());
            t.setLastLoginTime(user.getLoginTime());
            t.setSalt(null);
            t.setPassword(null);
            result.add(t);
        }
        return result;
    }

    public List<UserDto> findUsersByDepartmentId(Long departmentId) {
        List<UserDto> result = new ArrayList<>();
        List<User> dataRs = new ArrayList<>();
        User example = new User();
        example.setDepartmentId(departmentId);
        dataRs = userRepository.findAll(Example.of(example));

        for (int i = 0; i < dataRs.size(); i++) {
            User user = dataRs.get(i);
            UserDto t = new UserDto();
            DataUtils.copyProperties(user, t);
            t.setUsername(user.getUserName());
            t.setIsBlock(user.getLocked());
            t.setLastLoginTime(user.getLoginTime());
            t.setSalt(null);
            t.setPassword(null);
            result.add(t);
        }
        return result;
    }

    public Boolean changeUser(Long userId, UserDto user) {
        User dataIn = userRepository.findUserById(userId);
        if (dataIn == null) {
            return false;
        }
        if (user.getName() != null) {
            dataIn.setName(user.getName());
        }
        if (user.getUserName() != null) {
            dataIn.setUserName(user.getUserName());
        }
        if (user.getRole() != null) {
            dataIn.setRole(user.getRole());
        }
        if (user.getMobile() != null) {
            dataIn.setMobile(user.getMobile());
        }
        if (user.getEmail() != null) {
            dataIn.setEmail(user.getEmail());
        }
        if (user.getIsBlock() != null) {
            dataIn.setLocked(user.getIsBlock());
        }
        User dataRs = userRepository.save(dataIn);
        return true;
    }

    public Boolean deleteUsers(Long userId) {
        User dataIn = userRepository.findUserById(userId);
        userRepository.deleteById(userId);
        return true;
    }

    public Long uuid2id(String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            return null;
        }
        return userRepository.findIdByUUID(uuid);
    }

    public UserDto getUserByLogin(String username, String password) {
        User user = userRepository.findByUserName(username);
        // 判断用户存在
        if (user == null) {
            throw new LoginException();
        }
        // 验证密码
        String passwordRs = DigestUtils.md5DigestAsHex((password + user.getSalt()).getBytes());
        if (!passwordRs.equals(user.getPassword())) {
            throw new LoginException();
        }
        // 验证用户是否被冻结
        if (user.getLocked() != null && user.getLocked()) {
            throw new LoginException();
        }

        UserDto result = new UserDto();
        DataUtils.copyProperties(user, result);
        result.setUsername(user.getUserName());
        result.setIsBlock(user.getLocked());
        result.setLastLoginTime(user.getLoginTime());

        // 更新token
        String token = JwtUtils.createJWT(user.getUuid(), user.getRole());
        result.setToken(token);
        this.setToken(user.getUuid(), token);

        // 更新上次登录时间
        user.setLoginTime(new Date());
        userRepository.save(user);

        return result;
    }

    public Boolean updateUserPassword(Long userId, String password, String newPassword) {
        User res = userRepository.findUserById(userId);
        if (res == null) {
            return false;
        }
        // 验证原密码
        String passwordOrigin = DigestUtils.md5DigestAsHex((password + res.getSalt()).getBytes());
        if (!passwordOrigin.equals(res.getPassword())) {
            return false;
        }
        String newPasswordOrigin = DigestUtils.md5DigestAsHex((newPassword + res.getSalt()).getBytes());
        res.setPassword(newPasswordOrigin);
        userRepository.save(res);
        return true;
    }

    public void updateUserLoginTime(Long userId) {
        User res = userRepository.findUserById(userId);
        res.setLoginTime(new Date());
        userRepository.save(res);
    }

    /**
     * 设置口令
     * @param uuid
     * @param token
     * @return
     */
    public boolean setToken(String uuid, String token){
        try {
            return redisUtils.set(varificationPrefix + uuid, token, JwtUtils.JWT_TTL / 1000);
        }catch (Exception ex){
            throw new ServerErrorException();
        }
    }

    /**
     * 根据uuid获取口令
     * 
     * @param uuid
     * @return
     */
    public String getToken(String uuid) {
        try {
            String token = (String) redisUtils.get(varificationPrefix + uuid);
            return token;
        } catch (Exception ex) {
            throw new ServerErrorException();
        }

    }

    /**
     * 删除token
     * 
     * @param uuid
     */
    public void delToken(String uuid) {
        // 清空jwt验证缓存，重新登录
        try {
            redisUtils.del(varificationPrefix + uuid);
        } catch (Exception ex) {
            throw new ServerErrorException();
        }
    }
}
