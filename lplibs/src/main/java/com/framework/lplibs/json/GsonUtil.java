package com.framework.lplibs.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @version :1
 * @description : Gson使用工具类 所有的Gson 的使用 全部通过这个工具类来进行调用
 */
public class GsonUtil {
    private Gson gons;

    public Gson getGson() {
        if (gons == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gons = gsonBuilder.create();
        }
        return gons;
    }

    /**
     * 解析一个数组
     *
     * @param json 要解析的json
     * @return
     * @type 要解析成的类型
     */
    public <T> T getJsonList(String json, GsonType type) {
        if (!isEmpty(json)) {
            Gson gson = getGson();
            try {
                return gson.fromJson(json, type.type);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 解析一个对象
     *
     * @param json
     * @param classs
     * @return
     */
    public <T> T getJsonObject(String json, Class<T> classs) {
        if (!isEmpty(json)) {
            Gson gson = getGson();
            try {
                return gson.fromJson(json, classs);
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 将对象转换成json 对象
     *
     * @param object
     * @return
     */
    public JsonElement getJsonElement(Object object) {
        Gson gson = getGson();
        JsonParser parser = new JsonParser();
        return parser.parse(gson.toJson(object));
    }

    /**
     * 判断给定字符串是否空白串, 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input)) {
            return true;
        }
        int length = input.length();
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\n' && c != '\r') {
                return false;
            }
        }
        return true;
    }


}
