package com.fruit.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 上下文参数
 */
public class Context extends HashMap {
    /**
     * 添加一个参数
     * @param key
     * @param value
     */
    public void addParam(String key,Object value){
        super.put(key,value);
    }

    /**
     * 添加参数到map
     * @param map
     * @param <K>
     * @param <V>
     */
    public <K,V> void addParams(Map<K,V> map){
        putAll(map);
    }

    /**
     * 返回指定参数
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getParam(String key){
        return (T)super.get(key);
    }

    /**
     * 返回所有参数
     * @param <K>
     * @param <V>
     * @return
     */
    public <K,V>Map<K,V> getParams(){
        Map<K,V> map=new HashMap<K,V>();
        Set<K> set=super.keySet();
        set.stream().forEach(o->{
            map.put(o,(V)super.get(o));
        });
        return map;
    }
}
