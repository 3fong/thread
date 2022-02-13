package com.ll.aqs;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class SelfWeakHashMap {
    public static void main(String[] args) {
//        gcTest();
        keyNullTest();
    }

    /**
     * 代码中显式的调用System.gc()之后，data中的数据被清空了吗？答案是没有
     *
     * 经过测试一次gc未必能完全回收所有的weakreference对象，这里是因为key是强引用,所以无法直接出发gc,而当强引用移除后,则会进行弱引用回收
     */
    public static void gcTest(){
        Map<String, String> data = new WeakHashMap<String, String>();
        data.put("123", "123");
        data.put("124", "124");
        data.put("125", "125");
        data.put("126", "126");
        System.out.println(data);
        System.gc();// 正常情况下只要有垃圾回收,弱引用就应该被回收;但是由于WeakHashMap的key是String进行引用的,它是强引用,所以不会触发gc
        System.out.println(data);
    }

    public static void keyNullTest() {
        WeakHashMap map = new WeakHashMap();
        String k1 = new String("1");
        String k2 = new String("2");
        map.put(k1,"1");
        map.put(k2,"2");
        System.out.println(map.get("1"));
        map.remove(k1);// 将
        k2 = null;
        System.gc();
        System.out.println(map.get("1"));
        System.out.println(map.get("2"));
    }
}
