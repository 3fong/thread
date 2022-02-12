package com.ll.aqs;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class SelfWeakHashMap {
    public static void main(String[] args) {
        WeakHashMap map = new WeakHashMap();
        String k1 = new String("1");
        String k2 = new String("2");
        map.put(k1,"1");
        map.put(k2,"2");
        System.out.println(map.get("1"));
        map.remove(k1);
        k2 = null;
        System.gc();
        System.out.println(map.get("1"));
        System.out.println(map.get("2"));


    }
}
