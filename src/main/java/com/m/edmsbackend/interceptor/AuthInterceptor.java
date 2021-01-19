package com.m.edmsbackend.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.m.edmsbackend.exception.AuthorizationException;
import com.m.edmsbackend.service.UserService;
import com.m.edmsbackend.utils.JwtUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Value("${spring.profiles.active}")
    private String env;
    @Resource
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断 yaml 文件中环境为 prod 还是 dev
        // log.info("auth-interceptor-prehandle");

        String token = request.getHeader("token");
        String uuid = "";
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("token");
        }
        if (StringUtils.isNotEmpty(token) && JwtUtils.checkToken(token)) {
            uuid = JwtUtils.parseJwtToUuid(token);
        } else {
            if (!"dev".equals(env)) {
                throw new AuthorizationException("无授权登录");
            }
        }
        String redisToken = userService.getToken(uuid);
        if (!"dev".equals(env) && !token.equals(redisToken)) {
            throw new AuthorizationException("无授权登录");
        }

        request.setAttribute("uuid", uuid);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
