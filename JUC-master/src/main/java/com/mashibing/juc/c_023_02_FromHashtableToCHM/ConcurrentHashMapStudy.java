package com.mashibing.juc.c_023_02_FromHashtableToCHM;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liulei
 * @Description
 * @create 2022/2/14 21:46
 */
public class ConcurrentHashMapStudy {

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            int spread = ConcurrentHashMapStudy.spread(i);
            System.out.println(spread);
            System.out.println(" i "+(i ^ i )+" h "+(i ^ i >>> 16));
        }

    }

    public static final int spread(int h) {
        return (h ^ h >>> 16) & 2147483647;
    }
}
