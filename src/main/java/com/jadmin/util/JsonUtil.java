package com.jadmin.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/** 
 * @Title:web框架
 * @Description:json工具类
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
public class JsonUtil {
	
    /**
     * json 转 map
     */
    public static Map<String, Object> getMapByJson(String json){
        try{
            Gson gson = new GsonBuilder().enableComplexMapKeySerialization() .create();
            Map<String, Object> retMap2 = gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
            return retMap2;
        }catch (Exception e){
            e.printStackTrace();
            return new HashMap<String, Object>();
        }
    }

    /**
     * json 转 list
     */
    public static List<Map<String, Object>> getListByJson(String json){
        try{
            Gson gson = new GsonBuilder().enableComplexMapKeySerialization() .create();
            List<Map<String, Object>> retMap2 = gson.fromJson(json, new TypeToken<List<Map<String, Object>>>() {}.getType());
            return retMap2;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<Map<String, Object>>();
        }
    }
    

    /**
     * list 转 json
     */
    public static String getJsonByList(List<Map<String, Object>> list){
        try{
            Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
            return gson.toJson(list);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * map 转 json
     */
    public static String getJsonByMap(Map<String, Object> map){
        try{
            Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
            return gson.toJson(map);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json字符串转成对象
     * @param str
     * @param type
     * @return
     */
    public static <T> T fromJson(String str, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(str, type);
    }
}
