package com.m.edmsbackend.utils;

import com.alibaba.fastjson.JSONObject;
import com.m.edmsbackend.enums.StdStatus;

import java.io.Serializable;


/**
 * Json的标准输出格式
 * @param <T>
 */
public class StdResult implements Serializable
{

    private static final long serialVersionUID = -3373951637251223076L;
    private boolean success;
    private String message;
    private JSONObject data;

    public static JSONObject genResult(boolean success, JSONObject data)
    {
        data.put("success", success);
        data.put("message", null);
        return data;
    }

    public StdResult(boolean success, JSONObject data)
    {
        this.success = success;
        this.message = null;
        this.data = data;
    }

    public String toJsonString(){
        JSONObject json = data;
        json.put("success", true);
        json.put("message", message);
        return json.toJSONString();
    }

    public Object toJson(){
        JSONObject json = data;
        json.put("success", true);
        json.put("message", message);
        return json;
    }

    public boolean getSuccss() {
        return success;
    }

    public void setStatus(boolean success) {
        this.success = success;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public JSONObject getData()
    {
        return data;
    }

    public void setData(JSONObject data)
    {
        this.data = data;
    }
}
