package com.m.edmsbackend.utils;

import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * DataUtils
 */
public class DataUtils {

    public static void copyProperties(Object source, Object target) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
                emptyNames.add(pd.getName());
        }
        String[] emptyNamesArray = new String[emptyNames.size()];
        emptyNames.toArray(emptyNamesArray);
        BeanUtils.copyProperties(source, target, emptyNamesArray);
    }

    public static String randUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String randSALT() {
        byte[] saltBytes = new byte[15];
        new SecureRandom().nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    /**
     * 验证手机格式
     * 
     * @param phoneNumber
     * @return
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        /**
         * 正则表达式，从开头到结尾匹配一整串， 对于前三位：13或者18开头的第三位可以是任意数 15开头的第三位除了不能是4之外其他数都可以
         * 对于后八位：只要是刚好8位数字就行
         */
        // 正则写法有多种，还可以是^(((13|18)[0-9])|(15[0-35-9]))\\d{8}$
        return phoneNumber.matches("^[1]([3-9])[0-9]{9}$");
    }

    /**
     * 验证邮箱格式
     * 
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.equals("")) {
            return true;
        }
        boolean tag = true;
        final String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }
}