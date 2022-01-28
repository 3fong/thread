package com.ll.interview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MonitorLatch {



    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(i +"start---------------------------------------- ");
            latch(i);
            System.out.println(i +"end---------------------------------------- ");
        }
    }
    // 必须要保证
    public static void latch(int i) {
        CountDownLatch latch = new CountDownLatch(1);
        // 这里需要是线程安全的类,实现操作原子性;同时多线程环境,减少静态变量的使用
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        Thread factory = new Thread(() -> {
            System.out.println(i + "factory start");
            for (int num = 0; num < 10; num++) {
                list.add(num);
                System.out.println(i + "add " + num);

                if (list.size() == 5) {
                    latch.countDown();
                    System.out.println("factory await end");
                    try {
                        // 释放cpu资源,保证计数线程可以正常获取计数结果
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(i + "factory end");
        }, i + "factory");

        new Thread(() -> {
            System.out.println(i +"monitor start");
            try {
                factory.start();
                latch.await();
                System.out.println(i +"monitor size: "+list.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("monitor release lock");
        },i +"monitor").start();


    }
}
